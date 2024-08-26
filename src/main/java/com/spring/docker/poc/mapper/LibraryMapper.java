package com.spring.docker.poc.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.spring.docker.poc.entity.Library;
import com.spring.docker.poc.model.LibraryRecord;

@Mapper
public interface LibraryMapper {

	@Mapping(source = "title" , target = "bookTitle")
	@Mapping(source = "category" , target = "bookCategory")
	@Mapping(source = "author" , target = "bookAuthor")
	@Mapping(source = "email" , target = "emailId")
	@Mapping(source = "year" , target = "publishedYear")
	@Mapping(source = "price" , target = "publishedPrice")
	@Mapping(source = "insertedDate" , target = "publishedDate")
	@Mapping(source = "modifiedDate" , target = "modifiedDate")
	Library recordDataToEntity(LibraryRecord libraryRecord);
	
	@Mapping(source = "bookTitle" , target = "title")
	@Mapping(source = "bookCategory" , target = "category")
	@Mapping(source = "bookAuthor" , target = "author")
	@Mapping(source = "emailId" , target = "email")
	@Mapping(source = "publishedYear" , target = "year")
	@Mapping(source = "publishedPrice" , target = "price")
	@Mapping(source = "publishedDate" , target = "insertedDate")
	@Mapping(source = "modifiedDate" , target = "modifiedDate")
	LibraryRecord entityDataToRecord(Library library);
	
	
	@Mapping(source = "bookTitle" , target = "title")
	@Mapping(source = "bookCategory" , target = "category")
	@Mapping(source = "bookAuthor" , target = "author")
	@Mapping(source = "emailId" , target = "email")
	@Mapping(source = "publishedYear" , target = "year")
	@Mapping(source = "publishedPrice" , target = "price")
	@Mapping(source = "publishedDate" , target = "insertedDate")
	@Mapping(source = "modifiedDate" , target = "modifiedDate")
	List<LibraryRecord> entityListToRecordList(List<Library> library);
}
