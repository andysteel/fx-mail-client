package com.gmail.andersoninfonet.fxclientemail.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.gmail.andersoninfonet.fxclientemail.EmailManager;
import com.gmail.andersoninfonet.fxclientemail.model.EmailAccount;
import com.gmail.andersoninfonet.fxclientemail.model.enums.EmailSendingResult;
import com.gmail.andersoninfonet.fxclientemail.service.EmailSenderService;
import com.gmail.andersoninfonet.fxclientemail.view.ViewFactory;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

public class ComposeMessageController extends BaseController implements Initializable {

    @FXML
    private TextField recipientTextField;

    @FXML
    private TextField subjectTextField;

    @FXML
    private HTMLEditor htmlEditor;

    @FXML
    private Label errorLabel;

    @FXML
    private ChoiceBox<EmailAccount> accountChoice;

    public ComposeMessageController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }

    @FXML
    void sendButtonAction(ActionEvent event) {
        EmailSenderService senderService = 
            new EmailSenderService(this.accountChoice.getValue(), 
                                    this.subjectTextField.getText(), this.recipientTextField.getText(), 
                                    this.htmlEditor.getHtmlText());
        senderService.start();
        senderService.setOnSucceeded(e -> {
            EmailSendingResult sendingResult = senderService.getValue();

            switch(sendingResult) {
                case SUCCESS:
                    Stage stage = (Stage) this.recipientTextField.getScene().getWindow();
                    this.viewFactory.closeStage(stage);
                break;
                case FAILED_BY_PROVIDER:
                    this.errorLabel.setText("Provider error");
                break;
                case FAILED_BY_UNEXPECTED_ERROR:
                    this.errorLabel.setText("Unexpected error");
                break;
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpChoiceBox();
    }

    private void setUpChoiceBox() {
        this.accountChoice.setItems(this.emailManager.getAccounts());
        this.accountChoice.setValue(this.emailManager.getAccounts().get(0));
    }

}
