package com.spring.docker.poc.azure.blob;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.sas.BlobSasPermission;
import com.azure.storage.blob.sas.BlobServiceSasSignatureValues;

@Configuration
public class AzureStorageBolbConfig {

	   @Value("${azure.storage.connection.string}")
	   private String connectionString;

	   @Value("${azure.storage.container.name}")
	   private String containerName;
	   
	   @Bean
	   public BlobServiceClient clobServiceClient() {
		   
		   return new BlobServiceClientBuilder()
		         .connectionString(connectionString)
		         .buildClient();
	   }
	   
	   @Bean
	   public BlobContainerClient blobContainerClient() {
	      
	      return  clobServiceClient()
	      .getBlobContainerClient(containerName);
	   }
	   
	   
	   @Bean
	   public BlobSasPermission  blobContainerSasPermission() {
		   
		   return  new BlobSasPermission()
	               .setReadPermission(true)
	               .setWritePermission(true)
	               .setListPermission(true);
	   }
	   
	   @Bean
	   public BlobServiceSasSignatureValues getBlobServiceSasSignatureValues() {
		   
		   var expiryTime = OffsetDateTime.now().plus(1,ChronoUnit.DAYS); 
		   return  new BlobServiceSasSignatureValues(expiryTime, blobContainerSasPermission());
	   }
	    
	
}

