package com.stonks.Gram.utils;


import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;


@Service
public class S3util {
    
    @Autowired
    private AmazonS3 amazonS3;
     
    public void uploadImageToS3(String path,
                       String fileName,
                       ObjectMetadata objectMetaData,
                       InputStream inputStream)
    {

        ObjectMetadata objectMetadata2 = objectMetaData;

        try {
            amazonS3.putObject(path, fileName, inputStream, objectMetadata2);
        } 
        catch (AmazonServiceException e) 
        {   throw new IllegalStateException("Failed to upload the file", e);    }
        catch (SdkClientException e)
        {   e.printStackTrace();    }
    }
}
