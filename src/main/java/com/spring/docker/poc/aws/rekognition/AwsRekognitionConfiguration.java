package com.spring.docker.poc.aws.rekognition;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.translate.TranslateClient;

@Configuration
public class AwsRekognitionConfiguration {

	@Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

	@Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;
	
	@Value("${cloud.aws.region.static}")
	private String region;

    @Bean
    public AmazonRekognition amazonRekognition() {
        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        return AmazonRekognitionClientBuilder
                .standard()
                .withRegion(Regions.AP_SOUTH_1)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }

    @Bean
    public RekognitionClient rekognitionClient() {
        return RekognitionClient.builder()
                .region(Region.AP_SOUTH_1) // Choose your desired AWS region
                .build();
    }
    
    @Bean
    public TranslateClient translateClient() {
        return TranslateClient.builder()
                .region(Region.AP_SOUTH_1) // Specify your AWS region
                .build();
    }
}
