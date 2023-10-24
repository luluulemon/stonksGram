package com.stonks.Gram.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
// import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class S3Config {
    
    // add values for keys later
    @Value("${ACCESS_KEY}")
    String accessKey;

    @Value("${SECRET_KEY}")
    String secretKey;    

    @Bean
    public AmazonS3 createS3Client() {
        // Create a credential
        BasicAWSCredentials cred = new BasicAWSCredentials(accessKey, secretKey);

        return AmazonS3ClientBuilder.standard()
            .withRegion("ap-southeast-1")
            .withCredentials(new AWSStaticCredentialsProvider(cred))
            .build();
    }
}
