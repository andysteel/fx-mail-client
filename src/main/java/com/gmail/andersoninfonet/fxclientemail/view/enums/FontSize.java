package com.gmail.andersoninfonet.fxclientemail.view.enums;

public enum FontSize {

	SMALL,
	MEDIUM,
	LARGE;
	
	public static String getCssPath(FontSize fontSize) {
		switch (fontSize) {
		case SMALL:
			return "css/fontSmall.css";
		case MEDIUM:
			return "css/fontMedium.css";
		case LARGE:
			return "css/fontLarge.css";
		default:
			return null;
		}
	}
}
