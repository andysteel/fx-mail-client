package com.gmail.andersoninfonet.fxclientemail;

import com.gmail.andersoninfonet.fxclientemail.model.EmailAccount;
import com.gmail.andersoninfonet.fxclientemail.model.EmailTreeItem;
import com.gmail.andersoninfonet.fxclientemail.service.FetchFolderService;


public class EmailManager {

	private EmailTreeItem<String> foldersRoot = new EmailTreeItem<>("");
	
	public EmailTreeItem<String> getFoldersRoot() {
		return foldersRoot;
	}
	
	public void emailAccount(EmailAccount account) {
		EmailTreeItem<String> treeItem = new EmailTreeItem<>(account.getAddress());
		var folderService = new FetchFolderService(account.getStore(), treeItem);
		folderService.start();
		treeItem.setExpanded(true);
		this.foldersRoot.getChildren().add(treeItem);
	}
}
