package com.gmail.andersoninfonet.fxclientemail.service;

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

    private void handleFolders(Folder[] folders, EmailTreeItem<String> foldersRoot) throws MessagingException {
        for(Folder folder: folders){
            //folderList.add(folder);
            EmailTreeItem<String> emailTreeItem = new EmailTreeItem<String>(folder.getName());
            //emailTreeItem.setGraphic(iconResolver.getIconForFolder(folder.getName()));
            foldersRoot.getChildren().add((emailTreeItem));
            foldersRoot.setExpanded(true);
            fetchMessagesOnFolder(folder, emailTreeItem);
            //addMessageListenerToFolder(folder, emailTreeItem);
            if(folder.getType() == Folder.HOLDS_FOLDERS) {
                Folder[] subFolders =  folder.list();
                handleFolders(subFolders, emailTreeItem);
            }
        }

    }

	private void fetchMessagesOnFolder(Folder f, EmailTreeItem<String> treeItem) {
		Service fetchMessageService = new Service() {
			@Override
			protected Task createTask() {
				return new Task() {
					@Override
					protected Object call() throws Exception {
						if(f.getType() != Folder.HOLDS_FOLDERS) {
							f.open(Folder.READ_WRITE);
							int folderSize = f.getMessageCount();
							for(int i = folderSize; i > 0; i--) {
								treeItem.addEmail(f.getMessage(i));
							}
						}
						return null;
					}
				};
			}
		};
		fetchMessageService.start();
	}

}
