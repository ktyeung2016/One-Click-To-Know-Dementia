package hk.org.jccpa.dementia.sql;

import static android.provider.BaseColumns._ID;

import android.content.Context;

import android.database.sqlite.SQLiteDatabase;

import android.database.sqlite.SQLiteOpenHelper;



public class MyDBHelper_news extends SQLiteOpenHelper {

	//喧告公用常數(final)   

	//	public String name;
	//	public String phone;
	//	public String email;
	//	public String relationship;
	//	public String remarks;
	//	public String filepath;

	public static final String TABLE_NAME = "news";  //表格名稱

	public static final String title_zh = "title_zh";

	public static final String title_gb = "title_gb";
	public static final String html_content_zh = "html_content_zh";
	public static final String html_content_gb = "html_content_gb";
	
	private final static String DATABASE_NAME = "news.db";  //資料庫名稱

	private final static int DATABASE_VERSION = 1;  //資料庫版本

	public MyDBHelper_news(Context context) {

		super(context, DATABASE_NAME, null, DATABASE_VERSION);

	}



	@Override

	//建立table,有NAME、TEL、EMAIL 三個欄位

	public void onCreate(SQLiteDatabase db) {

		final String INIT_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + title_zh + " CHER, " 
				+ title_gb + " CHER, "+ html_content_zh+" CHER,"+ html_content_gb +" CHER);";

		db.execSQL(INIT_TABLE);

	}

	
	@Override

	//刪除table

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

		db.execSQL(DROP_TABLE);

		onCreate(db);

	}

}