package com.gmail.andersoninfonet.fxclientemail.service;

import java.io.IOException;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;

import com.gmail.andersoninfonet.fxclientemail.model.EmailMessage;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.web.WebEngine;

/**
 * @author andysteel
 * @since 0.0.1
 */
public class MessageRendererService extends Service {
	
	private EmailMessage emailMessage;
	private WebEngine webEngine; 
	private StringBuffer stringBuffer;
	
	
	
	public MessageRendererService(WebEngine webEngine) {
		super();
		this.webEngine = webEngine;
		this.stringBuffer = new StringBuffer();
		this.setOnSucceeded(event -> {
			this.displayMessage();
		});
	}

	public void setEmailMessage(EmailMessage emailMessage) {
		this.emailMessage = emailMessage;
	}
	
	private void displayMessage() {
		this.webEngine.loadContent(this.stringBuffer.toString());
	}

	@Override
	protected Task createTask() {
		
		return new Task() {
			@Override
			protected Object call() throws Exception {
				try {
					loadMessage();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
		};
	}
	
	private void loadMessage() throws MessagingException, IOException {
		this.stringBuffer.setLength(0);
		var  message = this.emailMessage.getMessage();
		final String contentType = message.getContentType();
		if(this.isSimpleType(contentType)) {
			this.stringBuffer.append(message.getContent().toString());
		} else if(this.isMultipartType(contentType)) {
			Multipart multipart = (Multipart) message.getContent();
			for(int i = 	multipart.getCount() -1; i>=0; i--) {
				BodyPart bodyPart = multipart.getBodyPart(i) ;
				String bodyPartContentType = bodyPart.getContentType();
				if(this.isSimpleType(bodyPartContentType)) {
					this.stringBuffer.append(bodyPart.getContent().toString());
				}
			}
		}
	}
	
	private boolean isSimpleType(final String contentType) {
		return  contentType.contains("TEXT/HTML") || 
				contentType.contains("mixed") ||
				contentType.contains("text");
	}
	
	private boolean isMultipartType(final String contentType) {
		return contentType.contains("multipart") ;
	}

}
