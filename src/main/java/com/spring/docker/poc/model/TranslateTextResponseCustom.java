package com.spring.docker.poc.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TranslateTextResponseCustom {

    private String detectedText;
    private String translatedText;
    private String sourceLanguage;
    private String targetLanguage;
    private Double confidence;


    public static TranslateTextResponseCustom create(String detectedText, String translatedText, String sourceLanguage, String targetLanguage, Double confidence) {
        return TranslateTextResponseCustom.builder()
                .detectedText(detectedText)
                .translatedText(translatedText)
                .sourceLanguage(sourceLanguage)
                .targetLanguage(targetLanguage)
                .confidence(confidence)
                .build();
    }
}
