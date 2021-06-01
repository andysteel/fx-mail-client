package com.gmail.andersoninfonet.fxclientemail.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.gmail.andersoninfonet.fxclientemail.EmailManager;
import com.gmail.andersoninfonet.fxclientemail.view.ViewFactory;
import com.gmail.andersoninfonet.fxclientemail.view.enums.ColorTheme;
import com.gmail.andersoninfonet.fxclientemail.view.enums.FontSize;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class OptionWindowController extends BaseController implements Initializable {
	
    @FXML
    private Slider fontSizePicker;

    @FXML
    private ChoiceBox<ColorTheme> colorThemePicker;

	public OptionWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
		super(emailManager, viewFactory, fxmlName);
	}

    @FXML
    void applyAction(ActionEvent event) {
    	this.viewFactory.setColorTheme(this.colorThemePicker.getValue());
    	this.viewFactory.setFontSize(FontSize.values()[(int) this.fontSizePicker.getValue()]);
    	this.viewFactory.updateStyles();
    }

    @FXML
    void cancelAction(ActionEvent event) {
    	var stage =  (Stage) this.colorThemePicker.getScene().getWindow();
    	stage.close();
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setUpThemePicker();
		setUpFontSizePicker();
	}

	private void setUpFontSizePicker() {
		this.fontSizePicker.setMin(0);
		this.fontSizePicker.setMax(FontSize.values().length - 1);
		this.fontSizePicker.setValue(this.viewFactory.getFontSize().ordinal());
		this.fontSizePicker.setMajorTickUnit(1);
		this.fontSizePicker.setMinorTickCount(0);
		this.fontSizePicker.setBlockIncrement(1);
		this.fontSizePicker.setSnapToTicks(true);
		this.fontSizePicker.setShowTickMarks(true);
		this.fontSizePicker.setShowTickLabels(true);
		this.fontSizePicker.setLabelFormatter(new StringConverter<Double>() {
			
			@Override
			public String toString(Double object) {
				int i = object.intValue();
				return FontSize.values()[i].toString();
			}
			
			@Override
			public Double fromString(String string) {
				return null;
			}
		});
		this.fontSizePicker.valueProperty().addListener((obs, oldValue, newValue) -> {
			this.fontSizePicker.setValue(newValue.intValue());
		});
	}

	private void setUpThemePicker() {
		this.colorThemePicker.setItems(FXCollections.observableArrayList(ColorTheme.values()));
		this.colorThemePicker.setValue(this.viewFactory.getColorTheme());
	}

}
