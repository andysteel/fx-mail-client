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

public class LoginService {

	private EmailAccount emailAccount;
	private EmailManager emailManager;
	
	public LoginService(EmailAccount emailAccount, EmailManager emailManager) {
		super();
		this.emailAccount = emailAccount;
		this.emailManager = emailManager;
	}
	
	public EmailLoginResult login() {
		Authenticator authenticator = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(emailAccount.getAddress(), emailAccount.getPassword());
			}
		};
		
		try {
			var session = Session.getInstance(emailAccount.getProperties(), authenticator);
			var store = session.getStore("imaps");
			store.connect(emailAccount.getProperties().getProperty("incomingHost"), emailAccount.getAddress(), emailAccount.getPassword());
		}  catch(MessagingException ex) {
			ex.printStackTrace();
			if(ex.getCause() instanceof AuthenticationFailedException) {
				return EmailLoginResult.FAILED_BY_CREDENTIALS;
			}
			if(ex.getCause() instanceof NoSuchProviderException) {
				return EmailLoginResult.FAILED_BY_NETWORK;
			}
			return EmailLoginResult.FAILED_BY_UNEXPECTED_ERROR;
		} catch (Exception ex) {
			ex.printStackTrace();
			if(ex.getCause() instanceof AuthenticationFailedException) {
				return EmailLoginResult.FAILED_BY_CREDENTIALS;
			}
			if(ex.getCause() instanceof NoSuchProviderException) {
				return EmailLoginResult.FAILED_BY_NETWORK;
			}
			return EmailLoginResult.FAILED_BY_UNEXPECTED_ERROR;
		}
		return EmailLoginResult.SUCCESS;
	}

}
