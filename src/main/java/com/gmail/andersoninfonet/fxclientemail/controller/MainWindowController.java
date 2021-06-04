package com.gmail.andersoninfonet.fxclientemail.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.gmail.andersoninfonet.fxclientemail.EmailManager;
import com.gmail.andersoninfonet.fxclientemail.view.ViewFactory;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.web.WebView;

public class MainWindowController extends BaseController implements Initializable {

	@FXML
    private TreeView<String> emailsTreeView;

    @FXML
    private TableView<?> emailsTableView;

    @FXML
    private WebView emailWebView;
    
    public MainWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
		super(emailManager, viewFactory, fxmlName);
	}

    @FXML
    void optionAction(ActionEvent event) {
    	this.viewFactory.showOptionWindow();
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		setUpemailsTreeView();
	}

	private void setUpemailsTreeView() {
		this.emailsTreeView.setRoot(this.emailManager.getFoldersRoot());
		this.emailsTreeView.setShowRoot(false);
	}

}
