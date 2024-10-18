package com.spring.docker.poc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.docker.poc.entity.Product;
import com.spring.docker.poc.mapper.ProductMapper;
import com.spring.docker.poc.model.ProductRecord;
import com.spring.docker.poc.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductMapper productMapper;
	
	public List<ProductRecord> getProductsList(){
		  List<Product> products = productRepository.findAll();
		  List<ProductRecord> productRecords = productMapper.entityListToRecordList(products);
		 return productRecords;
	}

	@Override
	public ProductRecord saveProduct(ProductRecord productRecord) {
		 Product productToSave = productMapper.recordDataToEntity(productRecord);
		 Product product = productRepository.save(productToSave);
		 ProductRecord productRecordSaved = productMapper.entityDataToRecord(product);
		 return productRecordSaved;
	}

	@Override
	public ProductRecord getProductById(String id) {
		 Product product = productRepository.findById(id).orElseThrow(()-> new RuntimeException("Product was not found: "+id));
		 ProductRecord productRecord = productMapper.entityDataToRecord(product);
		 return productRecord;
	}

	@Override
	public ProductRecord updateProduct(String id, ProductRecord productRecord) {
		Product product = productRepository.findById(id).orElseThrow(()-> new RuntimeException("Product was not found: "+id));
		Product productToUpdate = productMapper.recordDataToEntity(productRecord);
		if(product!=null)
		productToUpdate.setId(id);
		productRepository.save(productToUpdate);
		Product productAfterUpdate = productRepository.findById(id).orElseThrow(()-> new RuntimeException("Product was not found: "+id));
		ProductRecord productRecordUpdated = productMapper.entityDataToRecord(productAfterUpdate);
		return productRecordUpdated;
		
	}

	@Override
	public void deleteProduct(String id) {
		productRepository.findById(id).orElseThrow(()-> new RuntimeException("Product was not found: "+id));
		productRepository.deleteById(id);
	}

	@Override
	public List<ProductRecord> getProductByName(String productName) {
		List<Product> products = productRepository.findByNameContainingIgnoreCase(productName);
		List<ProductRecord> productRecords = productMapper.entityListToRecordList(products);
		return productRecords;
	}

	@Override
	public void deleteProducts() {
		productRepository.deleteAll();
	}
	
}
