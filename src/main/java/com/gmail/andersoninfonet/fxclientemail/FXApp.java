package com.gmail.andersoninfonet.fxclientemail;

import com.gmail.andersoninfonet.fxclientemail.view.ViewFactory;

import javafx.application.Application;
import javafx.stage.Stage;

public class FXApp extends Application  {

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		 var  viewFactory = new ViewFactory(new EmailManager());
	     viewFactory.showLoginWindow();
	}

}
