package com.spring.docker.poc.email;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum EmailTemplates {
	
	ONBOARD_BOOK_TEMPLATE("New Book Added to Library"),
	UPDATED_BOOK_TEMPLATE("Book Modified in Library"),
	LIBRARY_HEADER("LIBRARY_HEADER.jpg"),
	SIGNATURE_FOOTER("SIGNATURE_FOOTER.jpg");
	private final String description;

	@Override
	public String toString() {
		return this.description;
	}
}
