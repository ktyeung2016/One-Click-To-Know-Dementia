package hk.org.jccpa.dementia.game5;

import java.io.IOException;

import hk.org.jccpa.dementia.Global;
import hk.org.jccpa.dementia.Pages;
import hk.org.jccpa.dementia.R;
import hk.org.jccpa.dementia.game.UpdateGameData;
import hk.org.jccpa.dementia.sql.MyDBHelper_game5;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.basefamework.fontsize.Preferences;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Endgame5 extends Fragment {
	View v;
	ImageButton back,menu;
	MyDBHelper_game5 dbhelper;
	TextView right5,diff,marks,rank;
	Button cancel,ranks,emailshare,fbshare;
	
	public void onCreate(Bundle savedInstanceState) {
		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);

		super.onCreate(savedInstanceState);
		dbhelper = new MyDBHelper_game5(getActivity());


		addtodb();
		dbhelper.close();
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);
		v = inflater.inflate(R.layout.activity_endgame5, container, false);
		back=(ImageButton) v.findViewById(R.id.back);
		menu=(ImageButton) v.findViewById(R.id.menu);
		View holepage=v.findViewById(R.id.holepage);
		cancel=(Button) v.findViewById(R.id.delete);
		ranks=(Button) v.findViewById(R.id.ranks);
		Global.whichpage=Pages.Endgame5;
		ranks.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Global.onAirAct.pushFragment(Pages.game5Gamerankboard);
			}
		});
		cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Global.heart=5;
				Global.right5=0;
				Global.gameqnum=0;
				Global.game5budget=0;
                if(Global.onAirAct.isOnline()){
                    new UpdateGameData().execute(String.valueOf(Global.s.getgame5status()));
                }
				Global.onAirAct.pushFragment(Pages.Game_five_1);
			}
		});
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
				Global.onAirAct.pushFragment(Pages.Gamemenu);
			}
		});
		menu.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Global.onAirAct.mSlidingMenu.toggleRightDrawer();
			}
		});
		
		right5=(TextView) v.findViewById(R.id.right5);
		diff=(TextView) v.findViewById(R.id.diff);
		marks=(TextView) v.findViewById(R.id.marks);
		rank=(TextView) v.findViewById(R.id.rank);

		String temp123=getResources().getString(R.string.num2);
		right5.setText(String.valueOf(Global.right5)+temp123);
		marks.setText(String.valueOf(Global.totalscore));
		if(Global.s.getgame5status()==0){
			String temp=getResources().getString(R.string.diff1);
			diff.setText(temp);
		}else if(Global.s.getgame5status()==1){
			String temp=getResources().getString(R.string.diff2);
			diff.setText(temp);
		}else{
			String temp=getResources().getString(R.string.diff3);
			diff.setText(temp);
		}
		show();
		
		emailshare=(Button) v.findViewById(R.id.emailshare);
		emailshare.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_SEND);
				intent.setType("message/rfc822"); 
				intent.putExtra(Intent.EXTRA_EMAIL, "emailaddress@emailaddress.com"); 
				intent.putExtra(Intent.EXTRA_SUBJECT, "Subject"); 
				intent.putExtra(Intent.EXTRA_TEXT, "______：\n我在「腦退化一按知」健腦遊戲「"+getResources().getString(R.string.mindcal)+"」獲得 "+Global.totalscore+"分，你也快來挑戰吧。\n______\n謹啟"); 
				getActivity().startActivity(Intent.createChooser(intent, "Send Email"));
			}
		});
		fbshare=(Button) v.findViewById(R.id.fbshare);
		fbshare.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v1) {
				// TODO Auto-generated method stub
				LinearLayout view = (LinearLayout)v.findViewById(R.id.markslayout);
				view.setDrawingCacheEnabled(true);
				view.buildDrawingCache();
				 Global.bm = view.getDrawingCache();
				 Global.sharemsg="我在「腦退化一按知」健腦遊戲「"+getResources().getString(R.string.buyer)+"」獲得 "+Global.totalscore+"分，你也快來挑戰吧。";
					
				Intent myIntent = new Intent(getActivity(), hk.org.jccpa.dementia.Fbshare.class);
				getActivity().startActivity(myIntent);
				
//				
//				
//				byte[] data = null;
//				 
//				
//				ByteArrayOutputStream baos = new ByteArrayOutputStream();
//				Global.bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//				data = baos.toByteArray();
//				 
//				Bundle params = new Bundle();
//				params.putString("method", "photos.upload");
//				params.putByteArray("picture", data);
//				Facebook facebook
//				AsyncFacebookRunner mAsyncRunner = new AsyncFacebookRunner(facebook);
//				mAsyncRunner.request(null, params, "POST", new SampleUploadListener(), null);
				
			}
		});
		return v;
	}
	private void addtodb(){

		SQLiteDatabase db = dbhelper.getWritableDatabase();

		ContentValues values = new ContentValues();
		//	       name,phone,email,relationship,remarks
		values.put(MyDBHelper_game5.diff,  String.valueOf(Global.s.getgame5status()));
		if(Global.totalscore<0){
			Global.totalscore =0;
		}
		values.put(MyDBHelper_game5.marks,Global.totalscore);
		values.put(MyDBHelper_game5.numofq,Global.right5  );
		Long tsLong = System.currentTimeMillis()/1000;
		String ts = tsLong.toString();
		values.put(MyDBHelper_game5.date, ts );
		values.put(MyDBHelper_game5.userid,Global.s.userid());
		db.insert(MyDBHelper_game5.TABLE_NAME, null, values);

		if(Global.onAirAct.isOnline()){
			new UpdateGameData().execute(String.valueOf(Global.s.getgame5status()));
		}
		//		Global.onAirAct.clearOneBackStack();

	}
	private Cursor getCursor(){

		SQLiteDatabase db = dbhelper.getReadableDatabase();

		//		String[] columns = { android.provider.BaseColumns._ID,MyDBHelper_game5.diff, MyDBHelper_game5.marks, MyDBHelper_game5.numofq};
		Log.d("","select count(*) from "+MyDBHelper_game5.TABLE_NAME+" where '"+MyDBHelper_game5.marks+"' >= "+Global.totalscore+";");
		//		Cursor cursor = db.query(MyDBHelper_game5.TABLE_NAME, columns, null, null, null, null, MyDBHelper_game5.marks);
		Cursor cursor=db.rawQuery("select count(*) from "+MyDBHelper_game5.TABLE_NAME+" where "+MyDBHelper_game5.marks+" >= "+Global.totalscore+";",null);

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


}
