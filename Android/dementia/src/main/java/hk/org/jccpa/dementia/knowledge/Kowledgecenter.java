package hk.org.jccpa.dementia.knowledge;

import java.util.ArrayList;

import hk.org.jccpa.dementia.Global;
import hk.org.jccpa.dementia.Pages;
import hk.org.jccpa.dementia.R;
import hk.org.jccpa.dementia.adaptor.knowledgeAdapter;
import hk.org.jccpa.dementia.sql.MyDBHelper_knowledge;
import hk.org.jccpa.dementia.sql.MyDBHelper_news;

import com.example.basefamework.fontsize.Preferences;


import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

public class Kowledgecenter extends Fragment {
	View v;
	ImageButton back,menu;
	ListView list;
	knowledgeAdapter adaptor;
	ArrayList<hk.org.jccpa.dementia.object.knowledge> temp;
	MyDBHelper_knowledge knowledge;
	public void onCreate(Bundle savedInstanceState) {

		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);
		super.onCreate(savedInstanceState);
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);
		v = inflater.inflate(R.layout.activity_kowledgecenter, container, false);
		back=(ImageButton) v.findViewById(R.id.back);
		menu=(ImageButton) v.findViewById(R.id.menu);
		list=(ListView) v.findViewById(R.id.listView1);
		Global.whichpage=Pages.Kowledgecenter;
		View holepage=v.findViewById(R.id.holepage);
		holepage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Global.onAirAct.clearBackStack();
				Global.onAirAct.pushFragment(Pages.Homepage1);
			}
		});
		menu.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Global.onAirAct.mSlidingMenu.toggleRightDrawer();
			}
		});

		temp=new ArrayList<hk.org.jccpa.dementia.object.knowledge>();
		readdb();
		Global.knowledgelist=temp;
		adaptor=new knowledgeAdapter(getActivity(),temp);

		list.setAdapter(adaptor);
		if(temp.size()==0){
			new AlertDialog.Builder(getActivity())
			.setTitle("沒有資料")
			.setMessage("請連接網絡!／请连接网络!")
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int whichButton) {
					Global.onAirAct.clearBackStack();
					Global.onAirAct.pushFragment(Pages.Homepage1);
				}
			}).show();
		}

		return v;
	}
	private Cursor getCursor(){

		SQLiteDatabase db = knowledge.getReadableDatabase();

		//		String[] columns = { android.provider.BaseColumns._ID,MyDBHelper_game5.diff, MyDBHelper_game5.marks, MyDBHelper_game5.numofq};
		Log.d("","select * from "+MyDBHelper_news.TABLE_NAME+";");
		//		Cursor cursor = db.query(MyDBHelper_game5.TABLE_NAME, columns, null, null, null, null, MyDBHelper_game5.marks);
		Cursor cursor=db.rawQuery("select "+MyDBHelper_knowledge.dbid+" , "+MyDBHelper_knowledge.redirect+" , "+MyDBHelper_knowledge.title_zh+" , "+MyDBHelper_knowledge.title_gb+" , "+MyDBHelper_knowledge.html_content_zh+" , "+MyDBHelper_knowledge.html_content_gb+" from "+MyDBHelper_knowledge.TABLE_NAME+";",null);

		//db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy)
		//		getActivity().startManagingCursor(cursor);

		return cursor;

	}
	public void readdb(){
		knowledge = new MyDBHelper_knowledge(getActivity());
		Cursor cursor=getCursor();
		cursor.moveToFirst();

		for(int i =0 ;i<cursor.getCount();i++){

			temp.add(new hk.org.jccpa.dementia.object.knowledge(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3)
					,cursor.getString(4),cursor.getString(5)));
			cursor.moveToNext();
		}

		cursor.close();
		knowledge.close();
	}

}
