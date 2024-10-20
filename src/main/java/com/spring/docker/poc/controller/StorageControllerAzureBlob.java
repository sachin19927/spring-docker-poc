package com.spring.docker.poc.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spring.docker.poc.azure.blob.AzureStorageBlobService;

@RestController
@RequestMapping("/api/v3/azurestorage")
public class StorageControllerAzureBlob {

	@Autowired
	private AzureStorageBlobService azureStorageBlobService;
	
	@PostMapping("upload")
	public ResponseEntity<String> upload(@RequestParam MultipartFile file)
	               throws IOException {

	      String fileName = azureStorageBlobService.upload(file);
	      return ResponseEntity.ok(fileName);
	}
	
	@GetMapping
	public ResponseEntity<List<String>> getAllBlobs() {

	      List<String> items = azureStorageBlobService.listBlobs();
	      return ResponseEntity.ok(items);
	   }

	@DeleteMapping
	public ResponseEntity<Boolean> delete(@RequestParam String fileName) {

		   azureStorageBlobService.deleteBlob(fileName);
	      return ResponseEntity.ok().build();
	   }

	@GetMapping("/download")
	   public ResponseEntity<ByteArrayResource> getFile
	      (@RequestParam String fileName)
	         throws URISyntaxException {

	      ByteArrayResource resource =
	            new ByteArrayResource(azureStorageBlobService
	         .getFile(fileName));


	      return ResponseEntity.ok().contentLength(azureStorageBlobService
	 	         .getFile(fileName).length)
					.header("Content-type", "application/octet-stream")
					.header("Content-disposition", "attachment; filename=\"" +fileName+ "\"")
					.body(resource);
	   }
}
