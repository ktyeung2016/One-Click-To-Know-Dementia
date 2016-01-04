package com.example.basefamework.fontsize;

import hk.org.jccpa.dementia.R;



public enum FontStyle { 
	Small(R.style.FontStyle_Small, "Small"), 
	Medium(R.style.FontStyle_Medium, "Medium"), 
	Large(R.style.FontStyle_Large, "Large");

	private int resId;
	private String title;

	public int getResId() { 
		return resId;
	} 

	public String getTitle() {
		return title;
	} 

	FontStyle(int resId, String title) {
		this.resId = resId;
		this.title = title;
	} 
}

/*
<TextView android:textSize="?attr/font_large" />
Or I prefer using styles, in values/styles.xml add:

<style name="Label" parent="@android:style/Widget.TextView">
    <item name="android:textSize">?attr/font_medium</item>
    <item name="android:layout_width">wrap_content</item>
    <item name="android:layout_height">wrap_content</item>
</style>

<style name="Label.XLarge">
    <item name="android:textSize">?attr/font_xlarge</item>
</style>
And you can use it in this way:

<TextView style="@style/Label.XLarge" />
 */