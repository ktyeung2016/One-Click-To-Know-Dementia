package hk.org.jccpa.dementia.game;

import hk.org.jccpa.dementia.Global;
import hk.org.jccpa.dementia.Pages;
import hk.org.jccpa.dementia.R;
import hk.org.jccpa.dementia.sql.MyDBHelper_game1;
import hk.org.jccpa.dementia.sql.MyDBHelper_game2;
import hk.org.jccpa.dementia.sql.MyDBHelper_game3;
import hk.org.jccpa.dementia.sql.MyDBHelper_game4;
import hk.org.jccpa.dementia.sql.MyDBHelper_game5;

import com.example.basefamework.fontsize.Preferences;


import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class Gamerkmenu extends Fragment {
	View v;
	ImageButton back,menu;
	MyDBHelper_game1 dbhelper1;
	MyDBHelper_game2 dbhelper2;
	MyDBHelper_game3 dbhelper3;
	MyDBHelper_game4 dbhelper4;
	MyDBHelper_game5 dbhelper5;
	TextView marks1,marks2,marks3,marks4,marks5;
	Button buyer,puzzle,findroot,memory,mindcal;
	public void onCreate(Bundle savedInstanceState) {

		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);
		super.onCreate(savedInstanceState);
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);
		v = inflater.inflate(R.layout.activity_gamerkmenu, container, false);
		back=(ImageButton) v.findViewById(R.id.back);
		menu=(ImageButton) v.findViewById(R.id.menu);
		buyer=(Button) v.findViewById(R.id.buyer);
		dbhelper1 = new MyDBHelper_game1(getActivity());
		dbhelper2 = new MyDBHelper_game2(getActivity());
		dbhelper3 = new MyDBHelper_game3(getActivity());
		dbhelper4 = new MyDBHelper_game4(getActivity());
		dbhelper5 = new MyDBHelper_game5(getActivity());
		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//				Global.onAirAct.clearOneBackStack();
				Global.onAirAct.clearOneBackStack();
				//				Global.onAirAct.pushFragment(Pages.Homepage1);
			}
		});
		menu.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Global.onAirAct.mSlidingMenu.toggleRightDrawer();
			}
		});
		buyer.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Global.rankgamenum=1;
				if(!marks1.getText().toString().equals("0")){
					Global.onAirAct.pushFragment(Pages.Gamerabkboard);
				}
			}
		});
		marks1=(TextView) v.findViewById(R.id.marks1);
		marks1.setText(show1(1));
		puzzle=(Button) v.findViewById(R.id.puzzle);
		puzzle.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Global.rankgamenum=2;
				if(!marks2.getText().toString().equals("0")){
					Global.onAirAct.pushFragment(Pages.Gamerabkboard);
				}
			}
		});
		marks2=(TextView) v.findViewById(R.id.marks2);
		marks2.setText(show1(2));
		findroot=(Button) v.findViewById(R.id.findroot);
		findroot.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Global.rankgamenum=3;
				if(!marks3.getText().toString().equals("0")){
					Global.onAirAct.pushFragment(Pages.Gamerabkboard);
				}
			}
		});
		marks3=(TextView) v.findViewById(R.id.marks3);
		marks3.setText(show1(3));
		memory=(Button) v.findViewById(R.id.memory);
		memory.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Global.rankgamenum=4;
				if(!marks4.getText().toString().equals("0")){
					Global.onAirAct.pushFragment(Pages.Gamerabkboard);
				}
			}
		});
		marks4=(TextView) v.findViewById(R.id.marks4);
		marks4.setText(show1(4));
		mindcal=(Button) v.findViewById(R.id.mindcal);
		mindcal.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Global.rankgamenum=5;
				if(!marks5.getText().toString().equals("0")){
					Global.onAirAct.pushFragment(Pages.Gamerabkboard);
				}

			}
		});
		marks5=(TextView) v.findViewById(R.id.marks5);
		marks5.setText(show1(5));
		View holepage=v.findViewById(R.id.holepage);
		holepage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		return v;
	}
	private String show1(int game){
		//		final String INIT_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + name + " CHAR, " 
		//				+ relationship + " CHAR, "+ remarks + " CHAR, "+ filepath + " CHAR, "+ phone + " CHAR, " + email + " CHAR);";
		SQLiteDatabase db = null ;
		Cursor cursor = null;
		switch(game){
		case 1:
			db= dbhelper1.getReadableDatabase();
			cursor=db.rawQuery("select "+MyDBHelper_game1.marks+" from "+MyDBHelper_game1.TABLE_NAME+" ORDER BY "+MyDBHelper_game1.marks+" DESC "+"LIMIT 1;",null);
			break;
		case 2:
			db= dbhelper2.getReadableDatabase();
			cursor=db.rawQuery("select "+MyDBHelper_game2.marks+" from "+MyDBHelper_game2.TABLE_NAME+" ORDER BY "+MyDBHelper_game2.marks+" DESC "+"LIMIT 1;",null);
			break;
		case 3:
			db= dbhelper3.getReadableDatabase();
			cursor=db.rawQuery("select "+MyDBHelper_game3.marks+" from "+MyDBHelper_game3.TABLE_NAME+" ORDER BY "+MyDBHelper_game3.marks+" DESC "+"LIMIT 1;",null);
			break;
		case 4:
			db= dbhelper4.getReadableDatabase();
			cursor=db.rawQuery("select "+MyDBHelper_game4.marks+" from "+MyDBHelper_game4.TABLE_NAME+" ORDER BY "+MyDBHelper_game4.marks+" DESC "+"LIMIT 1;",null);
			break;
		case 5:
			db= dbhelper5.getReadableDatabase();
			cursor=db.rawQuery("select "+MyDBHelper_game5.marks+" from "+MyDBHelper_game5.TABLE_NAME+" ORDER BY "+MyDBHelper_game5.marks+" DESC "+"LIMIT 1;",null);
			break;
		}

		cursor.moveToFirst();
		//		for(int i =0 ;i<cursor.getCount();i++){
		try{
			String temp=String.valueOf(cursor.getInt(0));
			cursor.close();
			return temp;
		}catch(Exception e){
			e.printStackTrace();
			cursor.close();
			return "0";
		}

		//			marklist.add(cursor.getInt(0));
		//			daylist.add(cursor.getString(1));
		//			cursor.moveToNext();
		//		}


		//			list.add(temp);

	}
}
