package com.spring.docker.poc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spring.docker.poc.aws.s3.AWSFileStorageService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v2/awsstorage")
@Tag(name = "AWS S3 Storage", description = "AWS S3 Storage APIs")
public class StorageContollerAmazonS3 {

	@Autowired
	private AWSFileStorageService awsFileStorageService;
	
	@PostMapping("upload")
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file)
	{
		return new ResponseEntity<>(awsFileStorageService.saveFile(file),HttpStatus.OK); 
				
	}
	
	@GetMapping("download/{fileName}")
	public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable("fileName") String fileName)
	{
		byte[] data = awsFileStorageService.downloadFile(fileName);
		ByteArrayResource resource = new ByteArrayResource(data);
		return ResponseEntity
				.ok()
				.contentLength(data.length)
				.header("Content-type", "application/octet-stream")
				.header("Content-disposition", "attachment; filename=\"" +fileName+ "\"")
				.body(resource);
	}
	
	@GetMapping("getallfiles")
	public ResponseEntity<List<String>>  getAllFiles()
	{
		return new ResponseEntity<>(awsFileStorageService.listAllFiles(),HttpStatus.OK);
	}
	
	@DeleteMapping("delete/{fileName}")
	public ResponseEntity<String> deleteFile(@PathVariable("fileName") String fileName)
	{
		return new ResponseEntity<>(awsFileStorageService.deleteFile(fileName),HttpStatus.OK);
	}
}
