package com.gmail.andersoninfonet.fxclientemail.service;

import java.util.Arrays;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Store;

import com.gmail.andersoninfonet.fxclientemail.model.EmailTreeItem;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class FetchFolderService extends Service<Void> {
	
	private Store store;
	private EmailTreeItem<String> foldersRoot;

	public FetchFolderService(Store store, EmailTreeItem<String> foldersRoot) {
		super();
		this.store = store;
		this.foldersRoot = foldersRoot;
	}

	@Override
	protected Task<Void> createTask() {
		
		return new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				fetchFolders();
				return null;
			}
		};
	}
	
	private void fetchFolders() throws MessagingException {
		Folder[] folders = this.store.getDefaultFolder().list();
		handleFolders(folders, foldersRoot);
	}

	private void handleFolders(Folder[] folders, EmailTreeItem<String> foldersRoot)  {
		Arrays.stream(folders)
					.forEach(f -> {
						EmailTreeItem<String> treeItem = new EmailTreeItem<>(f.getName());
						foldersRoot.getChildren().add((treeItem));
							try {
								fetchMessageOnFolder(f, treeItem);
								if(f.getType() == Folder.HOLDS_FOLDERS) {
									handleFolders(f.list(), treeItem);
								}
							} catch (MessagingException e) {
								e.printStackTrace();
								throw new RuntimeException(e);
							}
					});		
	}

	private void fetchMessageOnFolder(Folder f, EmailTreeItem<String> treeItem) {
		//TODO
		
	}

}
