package com.gmail.andersoninfonet.fxclientemail.controller;

import com.gmail.andersoninfonet.fxclientemail.EmailManager;
import com.gmail.andersoninfonet.fxclientemail.model.EmailAccount;
import com.gmail.andersoninfonet.fxclientemail.service.LoginService;
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
    	if(fieldsAreValid()) {
    		var emailAcount = new EmailAccount(emailAddressField.getText(), passwordField.getText());
    		var result = new LoginService(emailAcount, emailManager).login();
    		switch (result) {
			case SUCCESS:
    			this.viewFactory.showMainWindow();
    			
    	    	//Workaround to close the actual Stage
    	    	var stage =  (Stage) this.errorLabel.getScene().getWindow();
    	    	this.viewFactory.closeStage(stage);
				break;
			case FAILED_BY_NETWORK:
				errorLabel.setText("No Provider Found");
				break;
			case FAILED_BY_CREDENTIALS:
				errorLabel.setText("Address or Password are invalid.");
				break;
			case FAILED_BY_UNEXPECTED_ERROR:
				errorLabel.setText("Internal Error Ocurred.");
				break;
			default:
				break;
			}
    	}
    	
    }

	private boolean fieldsAreValid() {
		if(emailAddressField.getText().isBlank()) {
			errorLabel.setText("Please fill the email field");
			return false;
		}
		if(passwordField.getText().isBlank()) {
			errorLabel.setText("Please fill the password field");
			return false;
		}
		return true;
	}

}
