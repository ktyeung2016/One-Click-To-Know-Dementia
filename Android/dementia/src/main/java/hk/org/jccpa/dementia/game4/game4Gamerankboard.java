package hk.org.jccpa.dementia.game4;

import java.util.ArrayList;

import hk.org.jccpa.dementia.Global;
import hk.org.jccpa.dementia.R;
import hk.org.jccpa.dementia.adaptor.rankAdapter;
import hk.org.jccpa.dementia.sql.MyDBHelper_game4;

import com.example.basefamework.fontsize.Preferences;


import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class game4Gamerankboard extends Fragment {
	View v;
	ImageButton back,menu;
	ListView list;
	rankAdapter adaptor;
	MyDBHelper_game4 dbhelper;
	TextView right5,diff,marks,rank,title;
	ArrayList<Integer> marklist ; 
	ArrayList<String> daylist ; 
	public void onCreate(Bundle savedInstanceState) {

		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);
		super.onCreate(savedInstanceState);
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);
		v = inflater.inflate(R.layout.activity_endgamerankboard, container, false);
		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);
		marklist=new ArrayList<Integer>();
		daylist=new ArrayList<String>();
		back=(ImageButton) v.findViewById(R.id.back);
		menu=(ImageButton) v.findViewById(R.id.menu);
		list=(ListView) v.findViewById(R.id.listView1);
		title=(TextView) v.findViewById(R.id.title);
		title.setText(getResources().getString(R.string.memory));
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

		dbhelper = new MyDBHelper_game4(getActivity());



		View holepage=v.findViewById(R.id.holepage);
		holepage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});


		right5=(TextView) v.findViewById(R.id.right5);
		diff=(TextView) v.findViewById(R.id.diff);
		marks=(TextView) v.findViewById(R.id.marks);
		rank=(TextView) v.findViewById(R.id.rank);

		String temp123=getResources().getString(R.string.num2);
		right5.setText(String.valueOf(Global.right5)+temp123);
		marks.setText(String.valueOf(Global.totalscore));
//		String temp123=getResources().getString(R.string.num2);
		Log.d("getgame2status", ""+Global.s.getgame4status());
		if(Global.s.getgame4status()==0){
			String temp=getResources().getString(R.string.diff1);
			diff.setText(temp);
		}else if(Global.s.getgame4status()==1){
			String temp=getResources().getString(R.string.diff2);
			diff.setText(temp);
		}else{
			String temp=getResources().getString(R.string.diff3);
			diff.setText(temp);
		}
		show();
		show2();
		adaptor=new rankAdapter(getActivity(),marklist,daylist);
		list.setAdapter(adaptor);
		return v;
	}
	
	private Cursor getCursor(){

		SQLiteDatabase db = dbhelper.getReadableDatabase();

		//		String[] columns = { android.provider.BaseColumns._ID,MyDBHelper_game5.diff, MyDBHelper_game5.marks, MyDBHelper_game5.numofq};
		Log.d("","select count(*) from "+MyDBHelper_game4.TABLE_NAME+" where '"+MyDBHelper_game4.marks+"' >= "+Global.totalscore+";");
		//		Cursor cursor = db.query(MyDBHelper_game5.TABLE_NAME, columns, null, null, null, null, MyDBHelper_game5.marks);
		Cursor cursor=db.rawQuery("select count(*) from "+MyDBHelper_game4.TABLE_NAME+" where "+MyDBHelper_game4.marks+" >= "+Global.totalscore+" and "+MyDBHelper_game4.userid+" = '"+Global.s.userid()+"';",null);

		//db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy)
		//		getActivity().startManagingCursor(cursor);

		return cursor;

	}
	private void show(){
		//		final String INIT_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + name + " CHAR, " 
		//				+ relationship + " CHAR, "+ remarks + " CHAR, "+ filepath + " CHAR, "+ phone + " CHAR, " + email + " CHAR);";
		Cursor cursor = getCursor();
		cursor.moveToFirst();
		int count= cursor.getInt(0);
		String temp1=getResources().getString(R.string.num1);
		String temp2=getResources().getString(R.string.num3);
		rank.setText(temp1+String.valueOf(count)+temp2);
		cursor.close();

		//			list.add(temp);

	}
	private void show2(){
		//		final String INIT_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + name + " CHAR, " 
		//				+ relationship + " CHAR, "+ remarks + " CHAR, "+ filepath + " CHAR, "+ phone + " CHAR, " + email + " CHAR);";
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		Cursor cursor=db.rawQuery("select "+MyDBHelper_game4.marks+","+MyDBHelper_game4.date+" from "+MyDBHelper_game4.TABLE_NAME+" ORDER BY "+MyDBHelper_game4.marks+" DESC "+"LIMIT 10;",null);
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
