package com.spring.docker.poc.email;

import static java.util.Locale.ENGLISH;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import com.spring.docker.poc.model.LibraryRecord;

@Service
public class EmailService {

	@Autowired
	private EmailSender emailSender;
	
	public void sendOnBoardMail(LibraryRecord libraryRecord) {
	
		var banners = Arrays.asList(EmailTemplates.LIBRARY_HEADER,EmailTemplates.SIGNATURE_FOOTER);
		var subject = "Subject: "+EmailTemplates.ONBOARD_BOOK_TEMPLATE+" - "+libraryRecord.title().toUpperCase();
		var context = getTemplateContext(libraryRecord);
		emailSender.sendEmail(Arrays.asList(libraryRecord.email()),subject, "html/library-onboard-template.html", context,banners);
		//emailSender.sendEmail(Arrays.asList(libraryRecord.email()),subject, "text/sample.txt", context);
	}

	private Context getTemplateContext(Object object) {
		var context = new Context(ENGLISH);
		if(object instanceof LibraryRecord libraryRecord) {
		context.setVariable("title", libraryRecord.title());
		//context.setVariable("createdDate", getDateFormLocalDateTime(libraryRecord.insertedDate()));
		context.setVariable("createdDate", getDateFormLocalDateTime(LocalDateTime.now()));
		context.setVariable("category", libraryRecord.category());
		context.setVariable("author", libraryRecord.author());
		context.setVariable("year", libraryRecord.year());
		context.setVariable("price", libraryRecord.price());
		}
		return context;
	}
	
	private String getDateFormLocalDateTime(LocalDateTime dateTime)
	{
		DateTimeFormatter formatter= DateTimeFormatter.ofPattern("dd-MMM-yyyy").withZone(ZoneId.systemDefault());
		return formatter.format(dateTime);
	}
	
}
