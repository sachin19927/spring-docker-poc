package com.spring.docker.poc.service;

import java.util.List;

import com.spring.docker.poc.entity.Product;
import com.spring.docker.poc.model.ProductRecord;

public interface ProductService {

	public List<ProductRecord> getProductsList();
	
	public ProductRecord saveProduct(ProductRecord productRecord);
	
	public ProductRecord getProductById(String id);
	
	public List<ProductRecord> getProductByName(String productName);
	
	public ProductRecord updateProduct(String id,ProductRecord productRecord);
	
	public void deleteProduct(String id);
	
	public void deleteProducts();
}
