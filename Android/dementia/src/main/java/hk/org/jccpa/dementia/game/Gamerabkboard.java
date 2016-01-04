package hk.org.jccpa.dementia.game;

import java.util.ArrayList;
import static android.provider.BaseColumns._ID;
import hk.org.jccpa.dementia.Global;
import hk.org.jccpa.dementia.R;
import hk.org.jccpa.dementia.adaptor.rankAdapter;
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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class Gamerabkboard extends Fragment {
	View v;
	ImageButton back,menu;
    TextView gameName;
	ListView list;
	rankAdapter adaptor;
	ArrayList<Integer> marklist ; 
	ArrayList<String> daylist ; 
	MyDBHelper_game1 dbhelper1;
	MyDBHelper_game2 dbhelper2;
	MyDBHelper_game3 dbhelper3;
	MyDBHelper_game4 dbhelper4;
	MyDBHelper_game5 dbhelper5;
	public void onCreate(Bundle savedInstanceState) {

		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);
		super.onCreate(savedInstanceState);
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);
		v = inflater.inflate(R.layout.activity_gamerabkboard, container, false);
		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);
		back=(ImageButton) v.findViewById(R.id.back);
		menu=(ImageButton) v.findViewById(R.id.menu);
        gameName = (TextView) v.findViewById(R.id.gameName);
		list=(ListView) v.findViewById(R.id.listView1);
		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Global.onAirAct.clearOneBackStack();
			}
		});
		menu.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Global.onAirAct.mSlidingMenu.toggleRightDrawer();
			}
		});
		//		adaptor=new rankAdapter(getActivity());
		View holepage=v.findViewById(R.id.holepage);
		holepage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		marklist=new ArrayList<Integer>();
		daylist=new ArrayList<String>();
		dbhelper1 = new MyDBHelper_game1(getActivity());
		dbhelper2 = new MyDBHelper_game2(getActivity());
		dbhelper3 = new MyDBHelper_game3(getActivity());
		dbhelper4 = new MyDBHelper_game4(getActivity());
		dbhelper5 = new MyDBHelper_game5(getActivity());
        setGameName(Global.rankgamenum);
        try {
            show1(Global.rankgamenum);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            show2(Global.rankgamenum);
        } catch (Exception e) {
            e.printStackTrace();
        }
        adaptor=new rankAdapter(getActivity(),marklist,daylist);
		list.setAdapter(adaptor);
		return v;
    }
    private void setGameName(int game){
        switch(game){
            case 1:
                gameName.setText(getActivity().getResources().getString(R.string.buyer));
                break;
            case 2:
                gameName.setText(getActivity().getResources().getString(R.string.puzzle));
                break;
            case 3:
                gameName.setText(getActivity().getResources().getString(R.string.findroot));
                break;
            case 4:
                gameName.setText(getActivity().getResources().getString(R.string.memory));
                break;
            case 5:
                gameName.setText(getActivity().getResources().getString(R.string.mindcal));
                break;
        }
    }
    private void show1(int game){
		//		final String INIT_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + name + " CHAR, "
		//				+ relationship + " CHAR, "+ remarks + " CHAR, "+ filepath + " CHAR, "+ phone + " CHAR, " + email + " CHAR);";
		SQLiteDatabase db = null ;
		Cursor cursor = null;
		switch(game){
		case 1:
			db= dbhelper1.getReadableDatabase();
			cursor=db.rawQuery("select "+MyDBHelper_game1.numofq+","+MyDBHelper_game1.marks+" from "+MyDBHelper_game1.TABLE_NAME
					+" where "+MyDBHelper_game1.userid+" = '"+Global.s.userid()+"' ORDER BY "+_ID+" DESC "+"LIMIT 1;",null);
			break;
		case 2:
			db= dbhelper2.getReadableDatabase();
			cursor=db.rawQuery("select "+MyDBHelper_game2.numofq+","+MyDBHelper_game2.marks+" from "+MyDBHelper_game2.TABLE_NAME+" where "+MyDBHelper_game2.userid+" = '"+Global.s.userid()+"' ORDER BY "+_ID+" DESC "+"LIMIT 1;",null);
			break;
		case 3:
			db= dbhelper3.getReadableDatabase();
			cursor=db.rawQuery("select "+MyDBHelper_game3.numofq+","+MyDBHelper_game3.marks+" from "+MyDBHelper_game3.TABLE_NAME+" where "+MyDBHelper_game3.userid+" = '"+Global.s.userid()+"' ORDER BY "+_ID+" DESC "+"LIMIT 1;",null);
			break;
		case 4:
			db= dbhelper4.getReadableDatabase();
			cursor=db.rawQuery("select "+MyDBHelper_game4.numofq+","+MyDBHelper_game4.marks+" from "+MyDBHelper_game4.TABLE_NAME+" where "+MyDBHelper_game4.userid+" = '"+Global.s.userid()+"' ORDER BY "+_ID+" DESC "+"LIMIT 1;",null);
			break;
		case 5:
			db= dbhelper5.getReadableDatabase();
			cursor=db.rawQuery("select "+MyDBHelper_game5.numofq+","+MyDBHelper_game5.marks+" from "+MyDBHelper_game5.TABLE_NAME+" where "+MyDBHelper_game5.userid+" = '"+Global.s.userid()+"' ORDER BY "+_ID+" DESC "+"LIMIT 1;",null);
			break;
		}
		
		cursor.moveToFirst();
		//		for(int i =0 ;i<cursor.getCount();i++){
		TextView marks=(TextView) v.findViewById(R.id.marks);
		marks.setText(String.valueOf(cursor.getInt(1)));
		TextView qdone=(TextView) v.findViewById(R.id.qdone);
		qdone.setText(String.valueOf(cursor.getInt(0)));
		//			marklist.add(cursor.getInt(0));
		//			daylist.add(cursor.getString(1));
		//			cursor.moveToNext();
		//		}

		cursor.close();

		//			list.add(temp);

	}
	private void show2(int game){
		//		final String INIT_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + name + " CHAR, " 
		//				+ relationship + " CHAR, "+ remarks + " CHAR, "+ filepath + " CHAR, "+ phone + " CHAR, " + email + " CHAR);";
		SQLiteDatabase db = null ;
		Cursor cursor=null;
		switch(game){
		case 1:
			db= dbhelper1.getReadableDatabase();
			cursor=db.rawQuery("select "+MyDBHelper_game1.marks+","+MyDBHelper_game1.date+" from "+MyDBHelper_game1.TABLE_NAME+" ORDER BY "+MyDBHelper_game1.marks+" DESC "+"LIMIT 10;",null);
			break;
		case 2:
			db= dbhelper2.getReadableDatabase();
			cursor=db.rawQuery("select "+MyDBHelper_game2.marks+","+MyDBHelper_game2.date+" from "+MyDBHelper_game2.TABLE_NAME+" ORDER BY "+MyDBHelper_game2.marks+" DESC "+"LIMIT 10;",null);
			break;
		case 3:
			db= dbhelper3.getReadableDatabase();
			cursor=db.rawQuery("select "+MyDBHelper_game3.marks+","+MyDBHelper_game3.date+" from "+MyDBHelper_game3.TABLE_NAME+" ORDER BY "+MyDBHelper_game3.marks+" DESC "+"LIMIT 10;",null);
			break;
		case 4:
			db= dbhelper4.getReadableDatabase();
			cursor=db.rawQuery("select "+MyDBHelper_game4.marks+","+MyDBHelper_game4.date+" from "+MyDBHelper_game4.TABLE_NAME+" ORDER BY "+MyDBHelper_game4.marks+" DESC "+"LIMIT 10;",null);
			break;
		case 5:
			db= dbhelper5.getReadableDatabase();
			cursor=db.rawQuery("select "+MyDBHelper_game5.marks+","+MyDBHelper_game5.date+" from "+MyDBHelper_game5.TABLE_NAME+" ORDER BY "+MyDBHelper_game5.marks+" DESC "+"LIMIT 10;",null);
			break;
		}
		
		cursor.moveToFirst();
		for(int i =0 ;i<cursor.getCount();i++){

			marklist.add(cursor.getInt(0));
			daylist.add(cursor.getString(1));
			cursor.moveToNext();
		}

		cursor.close();

		//			list.add(temp);

	}
}
