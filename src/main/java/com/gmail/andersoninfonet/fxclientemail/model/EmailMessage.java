package com.gmail.andersoninfonet.fxclientemail.model;

import java.util.Date;

import javax.mail.Message;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class EmailMessage {

	private SimpleStringProperty subject;
	private SimpleStringProperty sender;
	private SimpleStringProperty recipient;
	private SimpleIntegerProperty size;
	private SimpleObjectProperty<Date> date;
	private boolean isRead;
	private Message message;
	
	public EmailMessage(String subject, String sender, String recipient,
			int size, Date date, boolean isRead, Message message) {
		super();
		this.subject = new SimpleStringProperty(subject);
		this.sender = new SimpleStringProperty(sender);
		this.recipient = new SimpleStringProperty(recipient);
		this.size = new SimpleIntegerProperty(size);
		this.date = new SimpleObjectProperty<Date>(date);
		this.isRead = isRead;
		this.message = message;
	}

	public String getSubject() {
		return subject.get();
	}

	public String getSender() {
		return sender.get();
	}

	public String getRecipient() {
		return recipient.get();
	}

	public Integer getSize() {
		return size.get();
	}

	public Date getDate() {
		return date.get();
	}

	public boolean isRead() {
		return isRead;
	}
	
	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	public Message getMessage() {
		return message;
	}
	
	
}
