package com.spring.docker.poc.model;

import java.math.BigDecimal;
import java.util.Map;

public record ProductRecord(String productId,String productName,BigDecimal productPrice,Map<String,String> attributes) {

}
