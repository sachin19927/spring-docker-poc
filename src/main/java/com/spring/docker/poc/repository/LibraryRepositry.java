package com.spring.docker.poc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.docker.poc.entity.Library;

@Repository
public interface LibraryRepositry extends JpaRepository<Library, Long>{

	List<Library> findByBookTitleContainingIgnoreCase(String title);

}
