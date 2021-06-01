package com.gmail.andersoninfonet.fxclientemail.model;

import java.util.Properties;

import javax.mail.Store;

public class EmailAccount {

	private String address;
	private String password;
	private Properties properties;
	private Store store;
	
	public EmailAccount(String address, String password) {
		super();
		this.address = address;
		this.password = password;
		properties = new Properties();
		properties.put("incomingHost", "imap.gmail.com");
		properties.put("mail.store.protocol", "gimaps");
		properties.put("mail.imap.port", "993");
		properties.put("mail.imap.starttls.enable", "true");
		properties.put("mail.imap.ssl.trust", "imap.gmail.com");
		properties.put("mail.transport.protocol", "smtp");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.auth", "true");
		properties.put("outgoingHost", "smtp.gmail.com");
	}

	public String getAddress() {
		return address;
	}

	public String getPassword() {
		return password;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}
	
}
