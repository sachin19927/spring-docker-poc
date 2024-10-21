package com.spring.docker.poc.model;

import java.time.LocalDateTime;

public record LibraryRecord(Long id,String title,String category,String author,String email,int year,double price,int pages,
		LocalDateTime insertedDate,LocalDateTime modifiedDate) {

}
