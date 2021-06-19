package com.gmail.andersoninfonet.fxclientemail.service;

import java.util.List;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Store;
import javax.mail.event.MessageCountEvent;
import javax.mail.event.MessageCountListener;

import com.gmail.andersoninfonet.fxclientemail.model.EmailTreeItem;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class FetchFolderService extends Service<Void> {
	
	private Store store;
	private EmailTreeItem<String> foldersRoot;
	private List<Folder> foldersList;

	public FetchFolderService(Store store, EmailTreeItem<String> foldersRoot, List<Folder> foldersList) {
		super();
		this.store = store;
		this.foldersRoot = foldersRoot;
		this.foldersList = foldersList;
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
            foldersList.add(folder);
            EmailTreeItem<String> emailTreeItem = new EmailTreeItem<String>(folder.getName());
            //emailTreeItem.setGraphic(iconResolver.getIconForFolder(folder.getName()));
            foldersRoot.getChildren().add((emailTreeItem));
            foldersRoot.setExpanded(true);
            fetchMessagesOnFolder(folder, emailTreeItem);
            addMessageListenerToFolder(folder, emailTreeItem);
            if(folder.getType() == Folder.HOLDS_FOLDERS) {
                Folder[] subFolders =  folder.list();
                handleFolders(subFolders, emailTreeItem);
            }
        }

    }

	private void addMessageListenerToFolder(Folder folder, EmailTreeItem<String> emailTreeItem) {
		folder.addMessageCountListener(new MessageCountListener(){
			@Override
			public void messagesAdded(MessageCountEvent e) {
				for(int i = 0; i < e.getMessages().length; i++) {
					try {
						Message message = folder.getMessage(folder.getMessageCount() - 1);
						emailTreeItem.addEmailToTop(message);
					} catch (MessagingException ex) {
						ex.printStackTrace();
					}
				}
				
			}

			@Override
			public void messagesRemoved(MessageCountEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
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
