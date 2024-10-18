package com.spring.docker.poc.aws.s3;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.util.IOUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AWSFileStorageServiceImpl implements AWSFileStorageService{
	
	@Value("${application.bucket.name}")
	private String bucketName;
	
	private  final AmazonS3 amazonS3; 

	@Override
	public String saveFile(MultipartFile multiPartFile) {
		String FileName = System.currentTimeMillis()+"-"+multiPartFile.getOriginalFilename();
		try {
			File file = convertMultipartToFile(multiPartFile);
			PutObjectResult putObject = amazonS3.putObject(bucketName,FileName,file);
			file.delete();
			return "File Uploaded : "+FileName;
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public byte[] downloadFile(String fileName) {
		S3Object object = amazonS3.getObject(bucketName, fileName);
		S3ObjectInputStream objectContent = object.getObjectContent();
		try {
			return IOUtils.toByteArray(objectContent);
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String deleteFile(String fileName) {
		amazonS3.deleteObject(bucketName, fileName);
		return "File Deleted";
	}

	@Override
	public List<String> listAllFiles() {
		ListObjectsV2Result objectsV2Result = amazonS3.listObjectsV2(bucketName);
		return objectsV2Result.getObjectSummaries().stream().map(S3ObjectSummary::getKey).collect(Collectors.toList());
	}
	
	private File convertMultipartToFile(MultipartFile file) throws IOException {
		File convertFile = new File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convertFile);
		fos.write(file.getBytes());
		fos.close();
		return convertFile;
	}

}
