package com.stonks.Gram.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.ObjectMetadata;
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

    public void uploadToS3(MultipartFile file){
        
        String bucketName = "gramtest1";
        
        // add checker for image file

        //get file metadata
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());

        //Save Image in S3
        String path = bucketName;          //String.format("%s/%s", bucketName, UUID.randomUUID());
        String fileName = String.format("%s", file.getOriginalFilename());
        try {
            s3util.uploadImageToS3(path, fileName, objectMetadata, file.getInputStream());
        } catch (IOException e) {
            throw new IllegalStateException("Failed to upload file", e);
        }

        System.out.println("uploaded " + file.getOriginalFilename()); 

    }

}
