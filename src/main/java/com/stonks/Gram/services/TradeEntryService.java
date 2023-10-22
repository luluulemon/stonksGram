package com.stonks.Gram.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.stonks.Gram.entities.Trade;
import com.stonks.Gram.models.User;
import com.stonks.Gram.repos.TradeRepository;
import com.stonks.Gram.utils.S3util;

@Service
public class TradeEntryService {
    
    private final Path root = Paths.get("uploads");

    public void initFolder(){
        try {
            Files.createDirectory(root);
            System.out.println("created Folder");
        } catch (IOException e) 
        {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }


    public void uploadTrade(MultipartFile file){

        try {
            Files.copy(file.getInputStream(), root.resolve(file.getOriginalFilename()));
          } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
          }
    }

    @Autowired
    private S3util s3util;
    @Autowired
    private TradeRepository tradeRepo;

    public void uploadTrade(MultipartFile[] tradePics, Trade trade){
        
        String bucketName = "gramtest1";
        trade.setTradeId( UUID.randomUUID().toString().substring(0, 8));
        String tradePicsList = "";
        
        for(MultipartFile pic: tradePics){

            //get file metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(pic.getSize());
            objectMetadata.setContentType(pic.getContentType());

            //Save Image in S3
            String path = String.format("%s/%s", bucketName, trade.getTradeId());

            String fileName = String.format("%s", pic.getOriginalFilename());
            try {
                s3util.uploadImageToS3(path, fileName, objectMetadata, pic.getInputStream());
            } catch (IOException e) {
                throw new IllegalStateException("Failed to upload file", e);
            }

            tradePicsList += pic.getOriginalFilename() + ":";
            System.out.println("uploaded " + pic.getOriginalFilename() + " of type " + pic.getContentType()); 
        }

        // upload to trade table
        trade.setTradePicsList(tradePicsList);
        tradeRepo.saveTradeJpa(trade);
    }


    // Validation for trade details: Pic upload (fileType) and minimum required trade details
    public boolean checkTradeEntry(MultipartFile[] tradePics, Trade trade){

        // Check file type to be image, minimum one pic
        if(tradePics[0].isEmpty()){    return false;   }

        for(MultipartFile pic: tradePics){  // TODO: null content type?
            if(!pic.getContentType().equals("image/png")    &&
            !pic.getContentType().equals("image/jpg")   &&
            !pic.getContentType().equals("image/jpeg")
            )
            {   System.out.println("FIle type is wrong");
                return false;   }
        }

        // minimum: entryDate, entryPrice, entrySize
        if(trade.getEntryDate() == null ||
        trade.getEntryPrice() == 0 ||
        trade.getTradeSize() == 0)
        {   System.out.println("No minimum trade details");
            return false; 
        } 

        // check for incomplete exit details
        if((trade.getExitDate() == null && trade.getExitPrice() != 0) ||
        (trade.getExitDate()!= null && trade.getExitPrice() == 0))
        {   System.out.println("Improper exit details");
            return false;  
        }

        return true;
    }


    public List<Trade> loadTrades(User user){
        return tradeRepo.loadTradesJpa(user);
    }


    public void deleteTrade(Trade trade){
        s3util.deleteS3folder(trade, "gramtest1");
        tradeRepo.deleteTradeJpa(trade.getTradeId());
    }


    public void updateTrade(MultipartFile[] tradePics, Trade trade){
        
        String bucketName = "gramtest1";
        // validate tradeId exists
        if(tradeRepo.findTradeByIdJpa(trade.getTradeId()) == null)
        {   System.out.println("Id no exists");
            return;    }

        // delete old folder
        s3util.deleteS3folder(trade, bucketName);

        // update new pics
        String tradePicsList = "";  // For list of pics, to be saved
        for(MultipartFile pic: tradePics){

            //get file metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(pic.getSize());
            objectMetadata.setContentType(pic.getContentType());

            //Save Image in S3
            String path = String.format("%s/%s", bucketName, trade.getTradeId());
            String fileName = String.format("%s", pic.getOriginalFilename());
            try {
                s3util.uploadImageToS3(path, fileName, objectMetadata, pic.getInputStream());
            } catch (IOException e) {
                throw new IllegalStateException("Failed to upload file", e);
            }

            tradePicsList += pic.getOriginalFilename() + ":";
            System.out.println("uploaded " + pic.getOriginalFilename() + " of type " + pic.getContentType()); 
        }

        // upload to trade table
        trade.setTradePicsList(tradePicsList);
        tradeRepo.updateTradeJpa(trade);
    }

}
