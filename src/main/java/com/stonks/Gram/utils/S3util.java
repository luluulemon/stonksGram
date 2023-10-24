package com.stonks.Gram.utils;


import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.stonks.Gram.entities.Trade;


@Service
public class S3util {
    
    @Autowired
    private AmazonS3 amazonS3;
     
    public void uploadImageToS3(String path,
                       String fileName,
                       ObjectMetadata objectMetaData,
                       InputStream inputStream)
    {
        // TODO: check this one
        ObjectMetadata objectMetadata2 = objectMetaData;

        try {
            amazonS3.putObject(path, fileName, inputStream, objectMetadata2);
        } 
        catch (AmazonServiceException e) 
        {   throw new IllegalStateException("Failed to upload the file", e);    }
        catch (SdkClientException e)
        {   e.printStackTrace();    }
    }


    public void updateImageToS3(String path,
                       String fileName,
                       ObjectMetadata objectMetaData,
                       InputStream inputStream)
    {
        // TODO: check this one
        ObjectMetadata objectMetadata2 = objectMetaData;

        try {
            amazonS3.putObject(path, fileName, inputStream, objectMetadata2);
            
        } 
        catch (AmazonServiceException e) 
        {   throw new IllegalStateException("Failed to upload the file", e);    }
        catch (SdkClientException e)
        {   e.printStackTrace();    }
    }


    public void deleteS3folder(Trade trade, String bucketName){

                // delete old folder pics
        ListObjectsV2Request listObjectsRequest = new ListObjectsV2Request()
                                                    .withBucketName(bucketName)
                                                    .withPrefix(trade.getTradeId());

        ListObjectsV2Result objectListing = amazonS3.listObjectsV2(listObjectsRequest);

        // Iterate over the objects and delete them
        for (S3ObjectSummary summary : objectListing.getObjectSummaries()) {
            String key = summary.getKey();
            amazonS3.deleteObject(new DeleteObjectRequest(bucketName, key));
            System.out.println("Deleted object: " + key);
        }
    }

}
