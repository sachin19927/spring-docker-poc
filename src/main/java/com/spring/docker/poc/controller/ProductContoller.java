package com.spring.docker.poc.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.docker.poc.model.ProductRecord;
import com.spring.docker.poc.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1")
@Tag(name = "Product", description = "Product Management APIs")
public class ProductContoller {
	
	@Autowired
	private ProductService productService;
	
	
	@GetMapping(value="/product", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
            MediaType.TEXT_XML_VALUE})
	public ResponseEntity<List<ProductRecord>> getAllProducts(@RequestParam(required = false) String title) {
		try {
			List<ProductRecord> productRecords = new ArrayList<>();
			
			if (title == null)
				productService.getProductsList().forEach(productRecords::add);
			else
				productService.getProductByName(title).forEach(productRecords::add);
			
			if (productRecords.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(productRecords, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Operation(
		      summary = "Retrieve a Tutorial by Id",
		      description = "Get a Tutorial object by specifying its id. The response is Tutorial object with id, title, description and published status.",
		      tags = { "tutorials", "get" })
		  @ApiResponses({
		      @ApiResponse(responseCode = "200"),
		      @ApiResponse(responseCode = "204"),
		      @ApiResponse(responseCode = "404"),
		      @ApiResponse(responseCode = "500")})
	@GetMapping("/library/{id}")
	public ResponseEntity<ProductRecord> getTutorialById(@PathVariable("id") String id) {
		if(id!=null)
		{
			ProductRecord productRecord = productService.getProductById(id);
			if(productRecord!=null) {
				return new ResponseEntity<>(productRecord, HttpStatus.OK);
			}
		}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	

	@PostMapping("/product")
	public ResponseEntity<ProductRecord> createProduct(@RequestBody ProductRecord productRecord) {
		
		log.info("Storing Book");
		if(productRecord!=null) {
			ProductRecord productRecords = productService.saveProduct(productRecord);
			return new ResponseEntity<>(productRecords, HttpStatus.OK);
		}
		return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PutMapping("/product/{id}")
	public ResponseEntity<ProductRecord> updateProduct(@PathVariable("id") String id, @RequestBody ProductRecord productRecordUpdate) {
		
		ProductRecord result;
		if(id!=null) {
			ProductRecord existData=productService.updateProduct(id,productRecordUpdate);
			if(existData!=null) {
				
					return new ResponseEntity<>(existData, HttpStatus.OK);	
			}
			else
				{
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/product/{id}")
	public ResponseEntity<HttpStatus> deleteProductId(@PathVariable("id") String id) {
		if(id!=null)
		{
			productService.deleteProduct(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		else
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@DeleteMapping("/product")
	public ResponseEntity<HttpStatus> deleteAllProducts() {
		try {
			productService.deleteProducts();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
