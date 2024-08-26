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

import com.spring.docker.poc.model.LibraryRecord;
import com.spring.docker.poc.service.LibraryServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1")
@Tag(name = "Library", description = "Library Management APIs")
public class LibraryContoller {
	
	@Autowired
	private LibraryServiceImpl libraryService;
	
	
	@GetMapping(value="/library", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
            MediaType.TEXT_XML_VALUE})
	public ResponseEntity<List<LibraryRecord>> getAllBooks(@RequestParam(required = false) String title) {
		try {
			List<LibraryRecord> records = new ArrayList<>();
			
			if (title == null)
				libraryService.getAllBooksData().forEach(records::add);
			else
				libraryService.getAllBooksDataByName(title).forEach(records::add);
			
			if (records.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(records, HttpStatus.OK);
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
	public ResponseEntity<LibraryRecord> getTutorialById(@PathVariable("id") Long id) {
		if(id!=null)
		{
			LibraryRecord bookdetail = libraryService.getBookDetails(id);
			if(bookdetail!=null) {
				return new ResponseEntity<>(bookdetail, HttpStatus.OK);
			}
		}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	

	@PostMapping("/library")
	public ResponseEntity<LibraryRecord> createTutorial(@RequestBody LibraryRecord libRecord) {
		
		log.info("Storing Book");
		if(libRecord!=null) {
			LibraryRecord bookDetail = libraryService.saveBook(libRecord);
			return new ResponseEntity<>(bookDetail, HttpStatus.OK);
		}
		return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PutMapping("/library/{id}")
	public ResponseEntity<LibraryRecord> updateTutorial(@PathVariable("id") Long id, @RequestBody LibraryRecord updateData) {
		
		LibraryRecord result;
		if(id!=null) {
			LibraryRecord existData=libraryService.updateBook(id,updateData);
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

	@DeleteMapping("/library/{id}")
	public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") Long id) {
		if(id!=null)
		{
			libraryService.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		else
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@DeleteMapping("/library")
	public ResponseEntity<HttpStatus> deleteAllTutorials() {
		try {
			libraryService.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
