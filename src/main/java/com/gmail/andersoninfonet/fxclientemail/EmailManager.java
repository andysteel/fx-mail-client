package com.gmail.andersoninfonet.fxclientemail;

import java.util.ArrayList;
import java.util.List;

import javax.mail.Flags;
import javax.mail.Folder;

import com.gmail.andersoninfonet.fxclientemail.model.EmailAccount;
import com.gmail.andersoninfonet.fxclientemail.model.EmailMessage;
import com.gmail.andersoninfonet.fxclientemail.model.EmailTreeItem;
import com.gmail.andersoninfonet.fxclientemail.service.FetchFolderService;
import com.gmail.andersoninfonet.fxclientemail.service.FolderUpdaterService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class EmailManager {

	private FolderUpdaterService folderUpdaterService;

	private EmailTreeItem<String> foldersRoot = new EmailTreeItem<>("");

	private List<Folder> foldersList = new ArrayList<>();

	private EmailMessage selectedMessage;

	private EmailTreeItem<String> selectedFolder;

	private ObservableList<EmailAccount> accounts = FXCollections.observableArrayList();

	
	public EmailManager() {
		this.folderUpdaterService = new FolderUpdaterService(foldersList);
		this.folderUpdaterService.start();
	}

	public ObservableList<EmailAccount> getAccounts() {
		return accounts;
	}

	public List<Folder> getFoldersList() {
		return foldersList;
	}
	
	public EmailTreeItem<String> getFoldersRoot() {
		return foldersRoot;
	}

	/**
	 * @return the selectedMessage
	 */
	public EmailMessage getSelectedMessage() {
		return selectedMessage;
	}

	/**
	 * @param selectedMessage the selectedMessage to set
	 */
	public void setSelectedMessage(EmailMessage selectedMessage) {
		this.selectedMessage = selectedMessage;
	}

	/**
	 * @return the selectedFolder
	 */
	public EmailTreeItem<String> getSelectedFolder() {
		return selectedFolder;
	}

	/**
	 * @param selectedFolder the selectedFolder to set
	 */
	public void setSelectedFolder(EmailTreeItem<String> selectedFolder) {
		this.selectedFolder = selectedFolder;
	}
	
	public void emailAccount(EmailAccount account) {
		accounts.add(account);
		EmailTreeItem<String> treeItem = new EmailTreeItem<>(account.getAddress());
		var folderService = new FetchFolderService(account.getStore(), treeItem, foldersList);
		folderService.start();
		treeItem.setExpanded(true);
		this.foldersRoot.getChildren().add(treeItem);
	}

    public void setRead() {
		try {
			selectedMessage.setRead(true);
			selectedMessage.getMessage().setFlag(Flags.Flag.SEEN, true);
			selectedFolder.decrementMessagesCount();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    public void setUnRead() {
		try {
			selectedMessage.setRead(false);
			selectedMessage.getMessage().setFlag(Flags.Flag.SEEN, false);
			selectedFolder.incrementMessagesCount();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }


    public void deleteSelectedMessage() {
		try {
			selectedMessage.getMessage().setFlag(Flags.Flag.DELETED, true);
			selectedFolder.getEmailMessages().remove(selectedMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
