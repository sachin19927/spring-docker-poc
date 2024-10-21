package com.spring.docker.poc.email;

import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

import java.io.InputStream;
import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.mail.MessagingException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@RequiredArgsConstructor
public class EmailSender {

	private final SpringTemplateEngine templateEngine;
	private final JavaMailSender javaMailSender;
	
	private String from;
	private List<String> cc;
	private List<String> bcc;
	
	public void sendEmail(List<String> recipients,String subject,String template,Context dynamicProps, List<EmailTemplates> banners) {
		
		try {
			
	
		byte[] headerImage = getBannerBytes(banners.get(0).toString());
		byte[] footerImage = getBannerBytes(banners.get(1).toString());
		
		var message = javaMailSender.createMimeMessage();
		String[] receiverToList = recipients.toArray(new String[0]);
		String[] receiverCcList = cc.toArray(new String[0]);
		String[] receiverBccList = bcc.toArray(new String[0]);
		var html = templateEngine.process(template, dynamicProps);
		var header = new ByteArrayResource(headerImage);
		var footer = new ByteArrayResource(footerImage);
		
		var email = new MimeMessageHelper(message, true);
		email.setFrom(from);
		email.setTo(receiverToList);
		email.setCc(receiverCcList);
		email.setBcc(receiverBccList);
		email.setSubject(subject);
		email.setText(html,true);
		email.addInline("header", header,IMAGE_JPEG_VALUE);
		email.addInline("footer", footer,IMAGE_JPEG_VALUE);
			
		javaMailSender.send(message);
			
		} catch (MessagingException e) {
			throw new EmailServiceException("Error while sending mail",e);
		}
		
	}
	
	private byte[] getBannerBytes(String imageName)
	{
		try(InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(imageName)) {
			if(inputStream!=null)
				return inputStream.readAllBytes();
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return new byte[0];
	}
	
	private static class EmailServiceException extends RuntimeException{

		public EmailServiceException(String message,Exception exception) {
			super(message,exception);
		}
		
	}
}
