package com.gmail.andersoninfonet.fxclientemail.view.enums;

public enum ColorTheme {

	LIGHT,
	DEFAULT,
	DARK;
	
	public static String getCssPath(ColorTheme theme) {
		switch (theme) {
		case LIGHT:
			return "css/themeLight.css";
		case DEFAULT:
			return "css/themeDefault.css";
		case DARK:
			return "css/themeDark.css";
		default:
			return null;
		}
	}
	
}
