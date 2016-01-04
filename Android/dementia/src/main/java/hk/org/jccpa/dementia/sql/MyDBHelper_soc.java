package hk.org.jccpa.dementia.sql;

import static android.provider.BaseColumns._ID;

import android.content.Context;

import android.database.sqlite.SQLiteDatabase;

import android.database.sqlite.SQLiteOpenHelper;



public class MyDBHelper_soc extends SQLiteOpenHelper {

	//喧告公用常數(final)   

	//	public String name;
	//	public String phone;
	//	public String email;
	//	public String relationship;
	//	public String remarks;
	//	public String filepath;

	public static final String TABLE_NAME = "soc";  //表格名稱
	public static final String dbid = "dbid";
	public static final String title_zh = "title_zh";
	public static final String title_gb = "title_gb";
	public static final String organization_zh ="organization_zh";
	public static final String organization_gb ="organization_gb";
	public static final String service_name1_zh="service_name1_zh";
	public static final String service_name1_gb="service_name1_gb";
	public static final String service_name2_zh="service_name2_zh";
	public static final String service_name2_gb="service_name2_gb";
	public static final String district_zh="district_zh";
	public static final String district_gb="districtgb";
	public static final String first="first";
	public static final String second="second";
	public static final String third="third";
	public static final String four="four";
	public static final String family="family";
	public static final String helper="helper";
	public static final String remark_zh="remark_zh";
	public static final String remark_gb="remark_gb";
	public static final String phone="phone";
	public static final String website="website";
	public static final String temp = "temp";


	private final static String DATABASE_NAME = "soc.db";  //資料庫名稱

	private final static int DATABASE_VERSION = 1;  //資料庫版本

	public MyDBHelper_soc(Context context) {

		super(context, DATABASE_NAME, null, DATABASE_VERSION);

	}



	@Override

	//建立table,有NAME、TEL、EMAIL 三個欄位

	public void onCreate(SQLiteDatabase db) {

		final String INIT_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + dbid + " CHER, " 
				+ title_zh + " CHER, " 
				+ title_gb + " CHER, "+ organization_zh+" CHER,"+ organization_gb+" CHER,"+ service_name1_zh+" CHER,"+ service_name1_gb+" CHER,"+ service_name2_zh+" CHER,"
				+ service_name2_gb+" CHER,"+ district_zh+" CHER,"+ district_gb+" CHER,"
				+ first+" CHER,"+ second+" CHER,"+ third+" CHER,"+ four+" CHER,"+ family+" CHER,"+ helper+" CHER,"+ remark_zh+" CHER,"
				+ remark_gb+" CHER,"+ phone+" CHER,"+ website+" CHER,"
				+ temp +" CHER);";

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