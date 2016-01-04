package com.example.basefamework.fontsize;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Preferences { 
	private final static String FONT_STYLE = "FONT_STYLE";

	private final Context context;

	public Preferences(Context context) {
		this.context = context;
	} 

	protected SharedPreferences open() {
		return context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
	} 

	protected Editor edit() { 
		return open().edit();
	} 

	public FontStyle getFontStyle() { 
		return FontStyle.valueOf(open().getString(FONT_STYLE,
				FontStyle.Medium.name())); 
//		return FontStyle.valueOf(open().getString(FONT_STYLE,
//				FontStyle.Medium.name())); 
	} 

	public void setFontStyle(FontStyle style) {
		edit().putString(FONT_STYLE, style.name()).commit();
	} 
} 