package com.spring.docker.poc.azure.blob;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface AzureStorageBlobService {

	public String upload(MultipartFile multipartFile) throws IOException;
	
	public byte[] getFile(String fileName);
	
	public List<String> listBlobs();
	
	public Boolean deleteBlob(String blobName);
}
