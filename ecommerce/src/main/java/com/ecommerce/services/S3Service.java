package com.ecommerce.services;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class S3Service {

    private Logger logger = LoggerFactory.getLogger(S3Service.class);

    @Autowired
    private AmazonS3 s3Client;

    @Value("${s3.bucket}")
    private String bucketName;

    public void uploadFile(String filePath) {
        try {
            File file = new File(filePath);
            s3Client.putObject(new PutObjectRequest(bucketName, "fileName.jpeg", file));
            logger.info("Upload finished");
        }
        catch(AmazonServiceException e ) {
            logger.error("AmazonServiceException: " + e.getErrorMessage());
            logger.error("Status code: " + e.getErrorCode());
        }
        catch (AmazonClientException e ) {
            logger.error("AmazonClientException: " + e.getMessage());
        }
    }



}
