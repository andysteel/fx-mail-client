package com.gmail.andersoninfonet.fxclientemail.model;

import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

public class EmailTreeItem<String> extends TreeItem<String> {

	private String name;
	private ObservableList<EmailMessage> emailMessages;
	private int unreadMessageCount;

	public EmailTreeItem(String name) {
		super(name);
		this.name = name;
		this.emailMessages = FXCollections.observableArrayList();
	}
	
	public ObservableList<EmailMessage> getEmailMessages() {
		return this.emailMessages;
	}
	
	public void addEmail(Message message) throws MessagingException {
		boolean messageIsRead = message.getFlags().contains(Flags.Flag.SEEN);
		var emailMessage = new EmailMessage(
				message.getSubject(), 
				message.getFrom()[0].toString(), 
				message.getRecipients(MimeMessage.RecipientType.TO)[0].toString(), 
				message.getSize(), 
				message.getSentDate(), 
				messageIsRead, message);
		
		emailMessages.add(emailMessage);
		if(!messageIsRead) {
			this.incrementMessagesConut();
		}
	}
	
	public void incrementMessagesConut() {
		unreadMessageCount++;
		this.updateName();
	}
	
	private void updateName() {
		if(unreadMessageCount > 0) {
			this.setValue((String) (this.name + "(" + unreadMessageCount + ")"));
		} else {
			this.setValue(this.name);
		}
	}
	
}
