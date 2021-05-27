package com.gmail.andersoninfonet.fxclientemail.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginWindowController {

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField emailAddressField;

    @FXML
    private Label errorLabel;

    @FXML
    void loginAction(ActionEvent event) {
    	System.out.println("Login");
    }

}
