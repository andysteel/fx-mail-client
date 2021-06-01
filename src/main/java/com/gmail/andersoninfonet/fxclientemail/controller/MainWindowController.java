package com.gmail.andersoninfonet.fxclientemail.controller;

import com.gmail.andersoninfonet.fxclientemail.EmailManager;
import com.gmail.andersoninfonet.fxclientemail.view.ViewFactory;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.web.WebView;

public class MainWindowController extends BaseController {

	@FXML
    private TreeView<?> emailsTreeView;

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

}
