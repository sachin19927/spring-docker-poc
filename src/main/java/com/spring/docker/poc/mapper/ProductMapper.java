package com.spring.docker.poc.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.spring.docker.poc.entity.Product;
import com.spring.docker.poc.model.ProductRecord;

@Mapper
public interface ProductMapper {
	
	@Mapping(source = "productId" , target = "id")
	@Mapping(source = "productName" , target = "name")
	@Mapping(source = "productPrice" , target = "price")
	@Mapping(source = "attributes" , target = "attributes")
	Product recordDataToEntity(ProductRecord productRecord);
	
	@Mapping(source = "id" , target = "productId")
	@Mapping(source = "name" , target = "productName")
	@Mapping(source = "price" , target = "productPrice")
	@Mapping(source = "attributes" , target = "attributes")
	ProductRecord entityDataToRecord(Product product);
	
	@Mapping(source = "id" , target = "productId")
	@Mapping(source = "name" , target = "productName")
	@Mapping(source = "price" , target = "productPrice")
	@Mapping(source = "attributes" , target = "attributes")
	List<ProductRecord> entityListToRecordList(List<Product> products);
}
