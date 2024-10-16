package com.spring.docker.poc.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.spring.docker.poc.entity.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, String>{
	
	List<Product> findByNameContainingIgnoreCase(String productName);

}
