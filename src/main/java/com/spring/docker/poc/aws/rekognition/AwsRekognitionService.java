package com.spring.docker.poc.aws.rekognition;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spring.docker.poc.model.FaceDetailsCustom;
import com.spring.docker.poc.model.TextDetectionResponse;
import com.spring.docker.poc.model.TranslateTextResponseCustom;

import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.rekognition.model.DetectFacesRequest;
import software.amazon.awssdk.services.rekognition.model.DetectFacesResponse;
import software.amazon.awssdk.services.rekognition.model.DetectModerationLabelsRequest;
import software.amazon.awssdk.services.rekognition.model.DetectModerationLabelsResponse;
import software.amazon.awssdk.services.rekognition.model.DetectTextRequest;
import software.amazon.awssdk.services.rekognition.model.DetectTextResponse;
import software.amazon.awssdk.services.rekognition.model.FaceDetail;
import software.amazon.awssdk.services.rekognition.model.GetFaceDetectionRequest;
import software.amazon.awssdk.services.rekognition.model.GetFaceDetectionResponse;
import software.amazon.awssdk.services.rekognition.model.Image;
import software.amazon.awssdk.services.rekognition.model.S3Object;
import software.amazon.awssdk.services.rekognition.model.StartFaceDetectionRequest;
import software.amazon.awssdk.services.rekognition.model.StartFaceDetectionResponse;
import software.amazon.awssdk.services.rekognition.model.TextDetection;
import software.amazon.awssdk.services.rekognition.model.Video;
import software.amazon.awssdk.services.translate.TranslateClient;
import software.amazon.awssdk.services.translate.model.TranslateTextRequest;
import software.amazon.awssdk.services.translate.model.TranslateTextResponse;

@Service
public class AwsRekognitionService {

	@Autowired
	private RekognitionClient rekognitionClient;

	@Autowired
	private TranslateClient translateClient;

	public DetectModerationLabelsResponse detectModerationLabels(MultipartFile image) throws IOException {

		SdkBytes sdkBytes = SdkBytes.fromByteArray(image.getBytes());

		Image images = Image.builder().bytes(sdkBytes).build();

		// Build the moderation labels request
		DetectModerationLabelsRequest request = DetectModerationLabelsRequest.builder().image(images).minConfidence(70F) 
				.build();

		// Get the response from Rekognition
		DetectModerationLabelsResponse response = rekognitionClient.detectModerationLabels(request);

		return response;

	}

	public TranslateTextResponseCustom detectTextTranslate(MultipartFile image,String targetLanguageCode) throws IOException {

		SdkBytes sdkBytes = SdkBytes.fromByteArray(image.getBytes());
    	
    	Image images = Image.builder()
                .bytes(sdkBytes)
                .build();

		DetectTextRequest detectTextRequest = DetectTextRequest.builder().image(images).build();

		DetectTextResponse detectTextResponse = rekognitionClient.detectText(detectTextRequest);
		List<TextDetection> textDetections = detectTextResponse.textDetections();

		// Extract the detected text
		String detectedText = textDetections.stream().filter(text -> text.type().toString().equals("LINE")) 
				.map(TextDetection::detectedText).collect(Collectors.joining(" "));
		
		Double confidence = 
				textDetections.stream()
				.mapToDouble(TextDetection::confidence).min().orElse(0.0);

		TranslateTextRequest translateRequest = TranslateTextRequest.builder().text(detectedText)
				.sourceLanguageCode("auto") // Use 'auto' to automatically detect the source language
				.targetLanguageCode(targetLanguageCode).build();

		TranslateTextResponse translateResponse = translateClient.translateText(translateRequest);

		 return TranslateTextResponseCustom.create(detectedText, translateResponse.translatedText(), translateResponse.sourceLanguageCode(), translateResponse.targetLanguageCode(),confidence); 

	}

	public TextDetectionResponse detectText(MultipartFile image) throws IOException {

		SdkBytes sdkBytes = SdkBytes.fromByteArray(image.getBytes());
    	
    	Image images = Image.builder()
                .bytes(sdkBytes)
                .build();

		DetectTextRequest detectTextRequest = DetectTextRequest.builder().image(images).build();

		DetectTextResponse detectTextResponse = rekognitionClient.detectText(detectTextRequest);
		List<TextDetection> textDetections = detectTextResponse.textDetections();

		// Extract the detected text
		Optional<TextDetectionResponse> textResult = textDetections.stream()
				.map(text-> TextDetectionResponse.create(text.detectedText(),text.type().toString(),
						text.confidence(),text.geometry().boundingBox().toString())).findFirst();

		 return textResult.isPresent()? textResult.get():null; 

	}
	
	public List<FaceDetailsCustom> detectFaces(MultipartFile image) throws IOException {
    
		SdkBytes sdkBytes = SdkBytes.fromByteArray(image.getBytes());
    	
    	Image images = Image.builder()
                .bytes(sdkBytes)
                .build();


        // Create the DetectFacesRequest
        DetectFacesRequest request = DetectFacesRequest.builder()
                .image(images).attributesWithStrings("ALL")
                .build();

        // Detect faces in the image
        DetectFacesResponse response = rekognitionClient.detectFaces(request);
        return response.faceDetails().stream().map(this::convertToFaceDetailsCustom)
        .collect(Collectors.toList());
    }
	
	private FaceDetailsCustom convertToFaceDetailsCustom(FaceDetail faceDetail) {
	    FaceDetailsCustom faceDetailsCustom = new FaceDetailsCustom();
	    
	    FaceDetailsCustom.BoundingBox boundingBox = new FaceDetailsCustom.BoundingBox();
	    boundingBox.setWidth(faceDetail.boundingBox().width());
	    boundingBox.setHeight(faceDetail.boundingBox().height());
	    boundingBox.setLeft(faceDetail.boundingBox().left());
	    boundingBox.setTop(faceDetail.boundingBox().top());
	    faceDetailsCustom.setBoundingBox(boundingBox);
	    
	    FaceDetailsCustom.AgeRange ageRange = new FaceDetailsCustom.AgeRange();
	    ageRange.setLow(faceDetail.ageRange().low());
	    ageRange.setHigh(faceDetail.ageRange().high());
	    faceDetailsCustom.setAgeRange(ageRange);
	    
	    FaceDetailsCustom.Smile smile = new FaceDetailsCustom.Smile();
	    smile.setValue(faceDetail.smile().value());
	    smile.setConfidence(faceDetail.smile().confidence());
	    faceDetailsCustom.setSmile(smile);
	    
	    List<FaceDetailsCustom.Emotion> emotions = faceDetail.emotions().stream()
	            .map(emotion -> {
	                FaceDetailsCustom.Emotion serializableEmotion = new FaceDetailsCustom.Emotion();
	                serializableEmotion.setType(emotion.typeAsString());
	                serializableEmotion.setConfidence(emotion.confidence());
	                return serializableEmotion;
	            })
	            .collect(Collectors.toList());
	    faceDetailsCustom.setEmotions(emotions);
	    
	    return faceDetailsCustom;
	}

	
	public String videoAnalysis(String bucket, String videoKey) {
        Video video = Video.builder()
                .s3Object(S3Object.builder()
                        .bucket(bucket)
                        .name(videoKey)
                        .build())
                .build();

        StartFaceDetectionRequest request = StartFaceDetectionRequest.builder()
                .video(video)
                //.notificationChannel(null) // Set notification channel if using SNS
                .build();

        StartFaceDetectionResponse response = rekognitionClient.startFaceDetection(request);
        return response.jobId();
    }
	

    // Retrieve the face detection results
    public GetFaceDetectionResponse getFaceDetectionResults(String jobId) {
        GetFaceDetectionRequest request = GetFaceDetectionRequest.builder()
                .jobId(jobId)
                .build();

        return rekognitionClient.getFaceDetection(request);
    }

}