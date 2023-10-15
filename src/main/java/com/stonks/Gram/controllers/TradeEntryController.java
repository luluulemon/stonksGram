package com.stonks.Gram.controllers;


import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.stonks.Gram.models.Trade;
import com.stonks.Gram.services.TradeEntryService;

@RestController
@RequestMapping("/api/trade")
public class TradeEntryController {
    
    @Autowired
    private TradeEntryService tradeEntrySvc;

    @PostMapping(path="/upload", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
    public String postUpload( @RequestPart MultipartFile[] myfiles, @RequestPart String notes){

        // Set up folder - test upload
        tradeEntrySvc.initFolder();

        Arrays.asList(myfiles).stream().forEach(file -> {
            tradeEntrySvc.uploadTrade(file);  });

            //InputStream is = myfile.getInputStream();

        return null;
    }


    @PostMapping(path="/uploads3", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> postUploadToS3Test (@RequestPart MultipartFile[] tradePics, 
                                                    @RequestPart Trade trade){
        
        System.out.println(trade.toString());                                               
        if(!tradeEntrySvc.checkTradeEntry(tradePics, trade))
        {   return ResponseEntity.ok("Incomplete trade entry"); }                                                

        tradeEntrySvc.uploadTrade(tradePics, trade);  
        
        return ResponseEntity.ok("Upload Complete");
    }


    // end point for edit

    // end point for delete

}
