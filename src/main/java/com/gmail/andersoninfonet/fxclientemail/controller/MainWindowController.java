package com.gmail.andersoninfonet.fxclientemail.controller;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import com.gmail.andersoninfonet.fxclientemail.EmailManager;
import com.gmail.andersoninfonet.fxclientemail.model.EmailMessage;
import com.gmail.andersoninfonet.fxclientemail.model.EmailTreeItem;
import com.gmail.andersoninfonet.fxclientemail.model.SizeInteger;
import com.gmail.andersoninfonet.fxclientemail.service.MessageRendererService;
import com.gmail.andersoninfonet.fxclientemail.view.ViewFactory;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;
import javafx.util.Callback;

public class MainWindowController extends BaseController implements Initializable {

	private MenuItem markUnreadMenuItem = new MenuItem("mark as unread");

	private MenuItem deleteMessageMenuItem = new MenuItem("delete message");

	@FXML
    private TreeView<String> emailsTreeView;

    @FXML
    private TableView<EmailMessage> emailsTableView;
    
    @FXML
    private TableColumn<EmailMessage, String> senderCol;

    @FXML
    private TableColumn<EmailMessage, String> subjectCol;

    @FXML
    private TableColumn<EmailMessage, String> recipientCol;

    @FXML
    private TableColumn<EmailMessage, SizeInteger> sizeCol;

    @FXML
    private TableColumn<EmailMessage, Date> dateCol;

    @FXML
    private WebView emailWebView;
    
    private MessageRendererService rendererService;
    
    public MainWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
		super(emailManager, viewFactory, fxmlName);
	}

    @FXML
    void optionAction(ActionEvent event) {
		this.viewFactory.showOptionWindow();
    }

    @FXML
    void composeMessageAction(ActionEvent event) {
		this.viewFactory.showComposeMessageWindow();
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setUpEmailsTreeView();
		setUpEmailTableView();
		setUpFolderSelection();
		setUpBoldRows();
		setUpMessageRendererService();
		setUpMessageSelection();
		setUpContextMenus();
	}

	private void setUpContextMenus() {
		markUnreadMenuItem.setOnAction(event -> {
			emailManager.setUnRead();
		});

		deleteMessageMenuItem.setOnAction(event -> {
			emailManager.deleteSelectedMessage();
			emailWebView.getEngine().loadContent("");
		});

	}

	private void setUpMessageSelection() {
		emailsTableView.setOnMouseClicked(event -> {
			EmailMessage emailMessage = emailsTableView.getSelectionModel().getSelectedItem();
			if(emailMessage != null) {
				this.emailManager.setSelectedMessage(emailMessage);
				if(!emailMessage.isRead()) {
					this.emailManager.setRead();
				}
				this.rendererService.setEmailMessage(emailMessage);
				this.rendererService.restart();
			}
		});
		
	}

	private void setUpMessageRendererService() {
		this.rendererService = new MessageRendererService(this.emailWebView.getEngine());
	}

	private void setUpBoldRows() {
		emailsTableView.setRowFactory(new Callback<TableView<EmailMessage>, TableRow<EmailMessage>>() {
			
			@Override
			public TableRow<EmailMessage> call(TableView<EmailMessage> param) {
				return new TableRow<EmailMessage>() {
					@Override
					protected void updateItem(EmailMessage item, boolean empty) {
						super.updateItem(item, empty);
						if(item != null && !item.isRead()) {
								setStyle("-fx-font-weight: bold");
						}
					}
				};
			}
		});
	}

	private void setUpFolderSelection() {
		emailsTreeView.setOnMouseClicked(e -> {
			EmailTreeItem<String> item = (EmailTreeItem<String>) emailsTreeView.getSelectionModel().getSelectedItem();
			if(item != null) {
				emailManager.setSelectedFolder(item);
				emailsTableView.setItems(item.getEmailMessages());
			}
			
		});
	}

	private void setUpEmailTableView() {
		senderCol.setCellValueFactory((new PropertyValueFactory<EmailMessage, String>("sender")));
		subjectCol.setCellValueFactory((new PropertyValueFactory<EmailMessage, String>("subject")));
		recipientCol.setCellValueFactory((new PropertyValueFactory<EmailMessage, String>("recipient")));
		sizeCol.setCellValueFactory((new PropertyValueFactory<EmailMessage, SizeInteger>("size")));
		dateCol.setCellValueFactory((new PropertyValueFactory<EmailMessage, Date>("date")));
		
		emailsTableView.setContextMenu(new ContextMenu(markUnreadMenuItem, deleteMessageMenuItem));
	}

	private void setUpEmailsTreeView() {
		this.emailsTreeView.setRoot(this.emailManager.getFoldersRoot());
		this.emailsTreeView.setShowRoot(false);
	}

}
