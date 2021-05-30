package com.gmail.andersoninfonet.fxclientemail.controller;

import com.gmail.andersoninfonet.fxclientemail.EmailManager;
import com.gmail.andersoninfonet.fxclientemail.view.ViewFactory;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginWindowController extends BaseController {

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField emailAddressField;

    @FXML
    private Label errorLabel;
    
    public LoginWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
		super(emailManager, viewFactory, fxmlName);
	}

	@FXML
    void loginAction(ActionEvent event) {
    	System.out.println("Login");
    	this.viewFactory.showMainWindow();
    	
    	//Workaround to close the actual Stage
    	var stage =  (Stage) this.errorLabel.getScene().getWindow();
    	this.viewFactory.closeStage(stage);
    }

}
