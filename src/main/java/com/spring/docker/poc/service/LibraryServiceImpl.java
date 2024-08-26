package com.spring.docker.poc.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.docker.poc.entity.Library;
import com.spring.docker.poc.mapper.LibraryMapper;
import com.spring.docker.poc.model.LibraryRecord;
import com.spring.docker.poc.repository.LibraryRepositry;

@Service
public class LibraryServiceImpl {

	@Autowired
	private LibraryMapper mapper;
	
	@Autowired
	private LibraryRepositry libraryRepositry;
	
	
	public LibraryRecord saveBook(LibraryRecord bookRecord)
	{
		LibraryRecord savedBook = null;
		try {
			if(bookRecord!=null) {
			Library book = mapper.recordDataToEntity(bookRecord);
			libraryRepositry.save(book);
			savedBook= mapper.entityDataToRecord(book);
			}
		} catch (Exception e) {
			return null;
		}
		return savedBook;
	}
	
	public List<LibraryRecord> getAllBooksData() {
		return mapper.entityListToRecordList(libraryRepositry.findAll());
	}

	public List<LibraryRecord> getAllBooksDataByName(String title) {
		return mapper.entityListToRecordList(libraryRepositry.findByBookTitleContainingIgnoreCase(title));
	}
	
	public LibraryRecord getBookDetails(Long id)
	{
		LibraryRecord bookdetail = null;
		Optional<Library> book = libraryRepositry.findById(id);
		if (book.isPresent()) {
			bookdetail = mapper.entityDataToRecord(book.get());
			return bookdetail;
		}
		else
			return bookdetail;
	}
	
	public void deleteAll() {
		libraryRepositry.deleteAll();
	}

	public void deleteById(Long id) {
		libraryRepositry.deleteById(id);
	}

	public LibraryRecord updateBook(Long id, LibraryRecord updateData) {
		
		LibraryRecord bookdetail = null;
		Optional<Library> book = libraryRepositry.findById(id);
		if (book.isPresent()) {
			Library modifiedBook = book.get();
			modifiedBook.setBookTitle(updateData.title());
			modifiedBook.setBookAuthor(updateData.author());
			modifiedBook.setBookCategory(updateData.category());
			modifiedBook.setEmailId(updateData.email());
			libraryRepositry.save(modifiedBook);
			bookdetail = mapper.entityDataToRecord(modifiedBook);
			return bookdetail;
		}
		else
			return bookdetail;
	}
	
	
	
	
	
	
}
