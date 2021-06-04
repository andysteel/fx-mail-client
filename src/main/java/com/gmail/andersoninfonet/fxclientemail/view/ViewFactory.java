package com.gmail.andersoninfonet.fxclientemail.view;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.gmail.andersoninfonet.fxclientemail.EmailManager;
import com.gmail.andersoninfonet.fxclientemail.controller.BaseController;
import com.gmail.andersoninfonet.fxclientemail.controller.LoginWindowController;
import com.gmail.andersoninfonet.fxclientemail.controller.MainWindowController;
import com.gmail.andersoninfonet.fxclientemail.controller.OptionWindowController;
import com.gmail.andersoninfonet.fxclientemail.view.enums.ColorTheme;
import com.gmail.andersoninfonet.fxclientemail.view.enums.FontSize;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewFactory {

	private EmailManager emailManager;
	private ColorTheme colorTheme = ColorTheme.DEFAULT;
	private FontSize fontSize = FontSize.MEDIUM;
	private Set<Stage> activeStages;
	private boolean mainWindowInitialize = false;
	
    public ViewFactory(EmailManager emailManager) {
        this.emailManager = emailManager;
        this.activeStages = new HashSet<>();
    }
    
    public void showLoginWindow() {
        BaseController controller = new LoginWindowController(emailManager, this, "LoginWindow.fxml");
        this.initializeStage(controller, "Login");
    }
    
    public void showMainWindow() {
    	BaseController controller = new MainWindowController(emailManager, this, "MainWindow.fxml");
    	this.initializeStage(controller, "Main");
    	this.mainWindowInitialize = true;
    }
    
    public void showOptionWindow() {
    	BaseController controller = new OptionWindowController(emailManager, this, "OptionWindow.fxml");
    	this.initializeStage(controller, "Option");
    }
    
    private void initializeStage(BaseController baseController, String title){
        var fxmlLoader = new FXMLLoader(getClass().getResource(baseController.getFxmlName()));
        fxmlLoader.setController(baseController);
        Parent parent;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        var scene = new Scene(parent);
        var stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
        this.activeStages.add(stage);
    }
    
    public void closeStage(Stage stageToClose) {
    	stageToClose.close();
    	this.activeStages.remove(stageToClose);
    }

	public ColorTheme getColorTheme() {
		return colorTheme;
	}

	public void setColorTheme(ColorTheme colorTheme) {
		this.colorTheme = colorTheme;
	}

	public FontSize getFontSize() {
		return fontSize;
	}

	public void setFontSize(FontSize fontSize) {
		this.fontSize = fontSize;
	}

	public boolean isMainWindowInitialize() {
		return mainWindowInitialize;
	}
	
	public void updateStyles() {
		this.activeStages.forEach(stage -> {			
				var scene = stage.getScene();
				scene.getStylesheets().clear();
				scene.getStylesheets().add(getClass().getResource(ColorTheme.getCssPath(this.colorTheme)).toExternalForm());
				scene.getStylesheets().add(getClass().getResource(FontSize.getCssPath(this.fontSize)).toExternalForm());
		});
	}
    
}
