package com.gmail.andersoninfonet.fxclientemail.service;

import javax.mail.AuthenticationFailedException;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import com.gmail.andersoninfonet.fxclientemail.EmailManager;
import com.gmail.andersoninfonet.fxclientemail.model.EmailAccount;
import com.gmail.andersoninfonet.fxclientemail.model.enums.EmailLoginResult;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class LoginService extends Service<EmailLoginResult> {

	private EmailAccount emailAccount;
	private EmailManager emailManager;
	
	public LoginService(EmailAccount emailAccount, EmailManager emailManager) {
		super();
		this.emailAccount = emailAccount;
		this.emailManager = emailManager;
	}
	
	private EmailLoginResult login() {
		Authenticator authenticator = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(emailAccount.getAddress(), emailAccount.getPassword());
			}
		};
		
		try {
			var session = Session.getInstance(emailAccount.getProperties(), authenticator);
			emailAccount.setSession(session);
			var store = session.getStore("imaps");
			store.connect(emailAccount.getProperties().getProperty("incomingHost"), emailAccount.getAddress(), emailAccount.getPassword());
			emailAccount.setStore(store);
			this.emailManager.emailAccount(emailAccount);
		}  catch(MessagingException ex) {
			ex.printStackTrace();
			if(ex instanceof AuthenticationFailedException) {
				return EmailLoginResult.FAILED_BY_CREDENTIALS;
			}
			if(ex instanceof NoSuchProviderException) {
				return EmailLoginResult.FAILED_BY_NETWORK;
			}
			return EmailLoginResult.FAILED_BY_UNEXPECTED_ERROR;
		} catch (Exception ex) {
			ex.printStackTrace();
			return EmailLoginResult.FAILED_BY_UNEXPECTED_ERROR;
		}
		return EmailLoginResult.SUCCESS;
	}

	//using multithreading to not blocking the view
	@Override
	protected Task<EmailLoginResult> createTask() {
		
		return new Task<EmailLoginResult>() {
			@Override
			protected EmailLoginResult call() throws Exception {
				
				return login();
			}
		};
	}

}
