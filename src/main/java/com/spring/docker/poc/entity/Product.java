package com.spring.docker.poc.entity;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "product")
public class Product {

    @Id
    private String id;
    private String name;

    private BigDecimal price;
    private Map<String, String> attributes;
}
