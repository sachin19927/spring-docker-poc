package com.spring.docker.poc.model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import software.amazon.awssdk.services.rekognition.model.AgeRange;
import software.amazon.awssdk.services.rekognition.model.BoundingBox;
import software.amazon.awssdk.services.rekognition.model.Emotion;
import software.amazon.awssdk.services.rekognition.model.Smile;

@Data
public class FaceDetailsCustom implements Serializable {
    private static final long serialVersionUID = 1L;

    private BoundingBox boundingBox;
    private AgeRange ageRange;
    private Smile smile;
    private List<Emotion> emotions;
    

    // Inner classes for the nested types
    @Data
    public static class BoundingBox implements Serializable {
        private static final long serialVersionUID = 1L;
        private Float width;
        private Float height;
        private Float left;
        private Float top;

        // Getters and Setters
    }

    @Data
    public static class AgeRange implements Serializable {
        private static final long serialVersionUID = 1L;
        private Integer low;
        private Integer high;

        // Getters and Setters
    }

    @Data
    public static class Smile implements Serializable {
        private static final long serialVersionUID = 1L;
        private Boolean value;
        private Float confidence;

        // Getters and Setters
    }

    @Data
    public static class Emotion implements Serializable {
        private static final long serialVersionUID = 1L;
        private String type;
        private Float confidence;

        // Getters and Setters
    }
    
}
