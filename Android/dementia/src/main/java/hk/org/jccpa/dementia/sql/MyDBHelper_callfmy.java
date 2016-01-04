package hk.org.jccpa.dementia.sql;

import static android.provider.BaseColumns._ID;

import android.content.Context;

import android.database.sqlite.SQLiteDatabase;

import android.database.sqlite.SQLiteOpenHelper;



public class MyDBHelper_callfmy extends SQLiteOpenHelper {

	//喧告公用常數(final)   

	//	public String name;
	//	public String phone;
	//	public String email;
	//	public String relationship;
	//	public String remarks;
	//	public String filepath;

	public static final String TABLE_NAME = "familymember";  //表格名稱

	public static final String name = "name";

	public static final String phone = "phone";
	public static final String relationship = "relationship";
	public static final String email = "email";
	public static final String remarks = "remarks";
	public static final String filepath = "filepath";
	public static final String userid = "userid";
	private final static String DATABASE_NAME = "jcdb1.db";  //資料庫名稱

	private final static int DATABASE_VERSION = 1;  //資料庫版本

	public MyDBHelper_callfmy(Context context) {

		super(context, DATABASE_NAME, null, DATABASE_VERSION);

	}



	@Override

	//建立table,有NAME、TEL、EMAIL 三個欄位

	public void onCreate(SQLiteDatabase db) {

		final String INIT_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + userid + " CHAR, " + name + " CHAR, " 
				+ relationship + " CHAR, "+ remarks + " CHAR, "+ filepath + " CHAR, "+ phone + " CHAR, " + email + " CHAR);";

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