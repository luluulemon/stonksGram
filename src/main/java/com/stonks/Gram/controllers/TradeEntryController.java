package com.stonks.Gram.controllers;


import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.stonks.Gram.models.Trade;
import com.stonks.Gram.services.TradeEntryService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/trade")
public class TradeEntryController {
    
    @Autowired
    private TradeEntryService tradeSvc;

    // Test endpoint for pic upload
    @PostMapping(path="/upload", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
    public String postUpload( @RequestPart MultipartFile[] myfiles, @RequestPart String notes){

        // Set up folder - test upload
        tradeSvc.initFolder();

        Arrays.asList(myfiles).stream().forEach(file -> {
            tradeSvc.uploadTrade(file);  });

            //InputStream is = myfile.getInputStream();

        return null;
    }


    @PostMapping(path="/uploadTrade", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadTrade (@RequestPart MultipartFile[] tradePics, 
                                                    @RequestPart Trade trade){
        
        System.out.println(trade.toString());                                               
        if(!tradeSvc.checkTradeEntry(tradePics, trade))
        {   return ResponseEntity.ok("Incomplete trade entry"); }                                                

        tradeSvc.uploadTrade(tradePics, trade);  
        
        return ResponseEntity.ok("Upload Complete");
    }


    // end point for edit
    @PutMapping(path="updateTrade", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateTrade(@RequestPart MultipartFile[] tradePics, 
                                                    @RequestPart Trade trade){
        
        if(!tradeSvc.checkTradeEntry(tradePics, trade))
        {   return ResponseEntity.ok("Incomplete trade entry"); }    
        
        tradeSvc.updateTrade(tradePics, trade);  
        
        return ResponseEntity.ok("Upload Complete");
    }


    // end point for delete
    @DeleteMapping(path="deleteTrade")
    public ResponseEntity<String> deleteTrade(@RequestPart Trade trade){
        tradeSvc.deleteTrade(trade);
        return ResponseEntity.ok("Post deleted");
    }
}
