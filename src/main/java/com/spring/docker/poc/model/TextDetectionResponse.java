package com.spring.docker.poc.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TextDetectionResponse {

	  private String detectedText;
	  private String type;
	  private Float confidence;
	  private String boundingBox;
	    


	    public static TextDetectionResponse create(String detectedText, String type, Float confidence,String boundingBox ) {
	        return TextDetectionResponse.builder()
	                .detectedText(detectedText)
	                .type(type)
	                .confidence(confidence)
	                .boundingBox(boundingBox)
	                .build();
	    }
}
