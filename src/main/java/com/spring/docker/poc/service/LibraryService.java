package com.spring.docker.poc.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.spring.docker.poc.model.LibraryRecord;

@Service
public interface LibraryService {
	
	
	public LibraryRecord saveBook(LibraryRecord bookRecord);
	
	public List<LibraryRecord> getAllBooksData(); 

	public List<LibraryRecord> getAllBooksDataByName(String title); 
	
	public LibraryRecord getBookDetails(Long id);
	
	public void deleteAll();
	
	public void deleteById(Long id);

	public LibraryRecord updateBook(Long id, LibraryRecord updateData);
	
}
