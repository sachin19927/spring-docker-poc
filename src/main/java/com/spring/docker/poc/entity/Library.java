package com.spring.docker.poc.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "library")
public class Library {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String bookTitle;
	private String bookCategory;
	private String bookAuthor;
	private String emailId;
	private int publishedYear;
	private double publishedPrice;
	private int pages;
	@CreationTimestamp
	@Column(name="created_at", nullable=false, updatable=false)
	private LocalDateTime publishedDate;
	@UpdateTimestamp
	@Column(name="updated_at")
	private LocalDateTime modifiedDate;
	
}
