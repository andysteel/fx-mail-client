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
		EmailMessage emailMessage = fetchMessage(message);
		emailMessages.add(emailMessage);
	}

    public void addEmailToTop(Message message) throws MessagingException {
		EmailMessage emailMessage = fetchMessage(message);
		emailMessages.add(0, emailMessage);
    }

	private EmailMessage fetchMessage(Message message) throws MessagingException {
		boolean messageIsRead = message.getFlags().contains(Flags.Flag.SEEN);
		var emailMessage = new EmailMessage(
				message.getSubject(), 
				message.getFrom()[0].toString(), 
				message.getRecipients(MimeMessage.RecipientType.TO)[0].toString(), 
				message.getSize(), 
				message.getSentDate(), 
				messageIsRead, message);
		
		if(!messageIsRead) {
			this.incrementMessagesCount();
		}
		return emailMessage;
	}
	
	public void incrementMessagesCount() {
		unreadMessageCount++;
		this.updateName();
	}
	
	public void decrementMessagesCount() {
		unreadMessageCount--;
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
