package com.spring.docker.poc.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spring.docker.poc.aws.rekognition.AwsRekognitionService;
import com.spring.docker.poc.model.FaceDetailsCustom;
import com.spring.docker.poc.model.ModerationLabelResponse;
import com.spring.docker.poc.model.RecognitionLabel;
import com.spring.docker.poc.model.TextDetectionResponse;
import com.spring.docker.poc.model.TranslateTextResponseCustom;

import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.rekognition.model.DetectLabelsRequest;
import software.amazon.awssdk.services.rekognition.model.DetectLabelsResponse;
import software.amazon.awssdk.services.rekognition.model.DetectModerationLabelsResponse;
import software.amazon.awssdk.services.rekognition.model.FaceDetection;
import software.amazon.awssdk.services.rekognition.model.GetFaceDetectionResponse;
import software.amazon.awssdk.services.rekognition.model.Image;
import software.amazon.awssdk.services.rekognition.model.ModerationLabel;

@RestController
@RequestMapping("/api/v3/awsrekognition")
public class AwsRekognitionRestController {

	@Autowired
	private AwsRekognitionService awsRekognitionService;
	
	@Autowired
	private RekognitionClient rekognitionClient;
	
	
	 @PostMapping("/images/moderation-labels")
	    public Object detectModerationLabels(@RequestParam MultipartFile image) throws IOException {
		 
		 DetectModerationLabelsResponse response = awsRekognitionService.detectModerationLabels(image);
		 
		 List<ModerationLabel> moderationLabels = response.moderationLabels();
		 
		 List<ModerationLabelResponse> moderationLabel = moderationLabels.stream()
		   	.map((label) -> new ModerationLabelResponse(label.name(), label.confidence(),label.parentName()))
		 	.collect(Collectors.toList());
		 
	        return ResponseEntity.ok(moderationLabel);
	    }
	 
	 @PostMapping("/images/detect-labels")
	 public List<RecognitionLabel> detectLabels(@RequestParam MultipartFile image) throws IOException {
	 		
		 SdkBytes sdkBytes = SdkBytes.fromByteArray(image.getBytes());
	    	
	    	Image images = Image.builder()
	                .bytes(sdkBytes)
	                .build();
	   DetectLabelsResponse detectLabelsResponse = 
			   rekognitionClient.detectLabels(
	 	  DetectLabelsRequest.builder().image(images)
	 	  .maxLabels(10).build());
	 		
	   List<RecognitionLabel> labels = detectLabelsResponse.labels().stream()
	   	.map((label) -> new RecognitionLabel(label.name(), label.confidence()))
	 	.collect(Collectors.toList());

	   return labels;
	 		
	 }
	 
	 @PostMapping(value="/images/detect-translate", consumes = "multipart/form-data", produces = "application/json")
	    public ResponseEntity<TranslateTextResponseCustom> detectTranslateImageText(@RequestParam MultipartFile image,
	    		@RequestParam("targetLang") String targetLanguageCode) throws IOException {
		 
		 TranslateTextResponseCustom detectText = awsRekognitionService.detectTextTranslate(image,targetLanguageCode);
		 
	     return ResponseEntity.ok(detectText);
	  }
	 
	 
	 @PostMapping(value="/images/detect-text")
	    public ResponseEntity<TextDetectionResponse> detectText(@RequestBody MultipartFile image) throws IOException {
		 
		 TextDetectionResponse detectText = awsRekognitionService.detectText(image);
		 
	     return ResponseEntity.ok(detectText);
	    }
	 
	 @PostMapping(value="/images/detect-face")
	    public ResponseEntity<List<FaceDetailsCustom>> detectFace(@RequestBody MultipartFile image) throws IOException {
		 
		 List<FaceDetailsCustom> detectFace = awsRekognitionService.detectFaces(image);
		 
	     return ResponseEntity.ok(detectFace);
	    }
	 
	 @GetMapping(value="/images/video-analysis")
	  public ResponseEntity<String> videoAnalysis(@RequestParam String bucketName,@RequestParam String videoKey ) throws IOException {
		 
		 String detectFace = awsRekognitionService.videoAnalysis(bucketName,videoKey);
	     return ResponseEntity.ok(detectFace);
	   }
	 
	 @GetMapping("/images/get-face-detection")
	    public GetFaceDetectionResponse getFaceDetectionResults(@RequestParam String jobId) {
	        GetFaceDetectionResponse response = awsRekognitionService.getFaceDetectionResults(jobId);
	        return response;
	    }
}
