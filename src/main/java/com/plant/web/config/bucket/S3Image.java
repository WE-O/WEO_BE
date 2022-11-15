package com.plant.web.config.bucket;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class S3Image {
    final String endPoint = "https://kr.object.ncloudstorage.com";
    final String regionName = "kr-standard";
    final String accessKey = "iwEfqCHi4PHHw6QyLqRW";
    final String secretKey = "CcAN9tjYUkSMBqFJY1eFO35kFUfZih6HocJjQSiM";

    // S3 client
    final AmazonS3 s3 = AmazonS3ClientBuilder.standard()
            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endPoint, regionName))
            .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
            .build();

    String bucketName = "siksikbucket";
    String folderName = "/";

    public void s3Image(List<MultipartFile> multipartFile) {
        try {
            for (int i = 0; i < multipartFile.size(); i++) {
                ObjectMetadata objectMetadata = new ObjectMetadata();
                objectMetadata.setContentLength(multipartFile.get(i).getSize());
                objectMetadata.setContentType(multipartFile.get(i).getContentType());
                InputStream inputStream = multipartFile.get(i).getInputStream();

                PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, folderName, inputStream, objectMetadata);

                s3.putObject(putObjectRequest);
                System.out.format("Folder %s has been created.\n", folderName);
            }
        } catch (AmazonS3Exception e) {
            e.printStackTrace();
        } catch (SdkClientException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            for (int i = 0; i < multipartFile.size(); i++) {

                String objectName = multipartFile.get(i).getOriginalFilename();
                File file = multipartFileToFile(multipartFile.get(i));
                s3.putObject(bucketName, objectName, file);
            }
        } catch (AmazonS3Exception e) {
            e.printStackTrace();
        } catch (SdkClientException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private File multipartFileToFile(MultipartFile file) throws IOException {
            File convFile = new File(file.getOriginalFilename());
            convFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
        return convFile;
    }
}
