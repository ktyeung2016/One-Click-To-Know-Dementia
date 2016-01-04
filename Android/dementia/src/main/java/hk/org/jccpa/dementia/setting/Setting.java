package hk.org.jccpa.dementia.setting;

import java.io.IOException;


import hk.org.jccpa.dementia.Global;
import hk.org.jccpa.dementia.Pages;
import hk.org.jccpa.dementia.R;
import hk.org.jccpa.dementia.sql.MyDBHelper_callfmy;
import hk.org.jccpa.dementia.sql.MyDBHelper_game1;
import hk.org.jccpa.dementia.sql.MyDBHelper_game2;
import hk.org.jccpa.dementia.sql.MyDBHelper_game3;
import hk.org.jccpa.dementia.sql.MyDBHelper_game4;
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

import com.example.basefamework.fontsize.FontStyle;
import com.example.basefamework.fontsize.Preferences;



import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class Setting extends Fragment {
	View v;
	ImageButton back,menu;
	ImageView soundswitch,updateswitch;
	boolean sound=true,update=false;
	RadioGroup mode,lang,langsize;
	TextView peroid,cleargamerk,delfmy,logout,aboutApp, userGuide, accessibilityInfo;
	MyDBHelper_callfmy dbhelper;
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
		v = inflater.inflate(R.layout.activity_setting, container, false);
		back=(ImageButton) v.findViewById(R.id.back);
		menu=(ImageButton) v.findViewById(R.id.menu);
		peroid=(TextView) v.findViewById(R.id.peroid);
		dbhelper = new MyDBHelper_callfmy(getActivity());
		dbhelper1 = new MyDBHelper_game1(getActivity());
		dbhelper2 = new MyDBHelper_game2(getActivity());
		dbhelper3 = new MyDBHelper_game3(getActivity());
		dbhelper4 = new MyDBHelper_game4(getActivity());
		dbhelper5 = new MyDBHelper_game5(getActivity());
		View holepage=v.findViewById(R.id.holepage);
		holepage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		mode=(RadioGroup) v.findViewById(R.id.mode_radio_group);
		lang=(RadioGroup) v.findViewById(R.id.lang_radio_group);
		langsize=(RadioGroup) v.findViewById(R.id.langsize_radio_group);




		if(!Global.s.whichmode()){
			//			mode.getChildAt(mode.indexOfChild((RadioButton) v.findViewById(R.id.full)));
			mode.check(R.id.full);
		}else{
			mode.check(R.id.game);
		}

		mode.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				int p = group.indexOfChild((RadioButton) v.findViewById(checkedId));

				int count = group.getChildCount();
				switch (checkedId) {
				case R.id.full:
					//					Toast.makeText(getActivity(), " R.id.full", Toast.LENGTH_LONG).show();
					if(Global.onAirAct.isOnline() ||!Global.s.userid().equals("no_user")){
						Global.s.setmode(false);
						Global.onAirAct.chdrawer();	
						new updategamedata().execute();
					}else{
						Toast.makeText(getActivity(), getActivity().getString(R.string.plzlogin), Toast.LENGTH_LONG).show();
					}

					break;
				case R.id.game:
					//					Toast.makeText(getActivity(), "R.id.game", Toast.LENGTH_LONG).show();

					if(Global.onAirAct.isOnline() ||!Global.s.userid().equals("no_user") ){
						Global.s.setmode(true);
						Global.onAirAct.chdrawer();	
						new updategamedata().execute();
					}else{
						Toast.makeText(getActivity(), getActivity().getString(R.string.plzlogin), Toast.LENGTH_LONG).show();
					}
					break;

				}

			}

		});

		if(Global.s.whichlang()){
			//			mode.getChildAt(mode.indexOfChild((RadioButton) v.findViewById(R.id.full)));
			lang.check(R.id.zh);
		}else{
			lang.check(R.id.cn);
		}

		lang.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				int p = group.indexOfChild((RadioButton) v.findViewById(checkedId));

				int count = group.getChildCount();
				switch (checkedId) {
				case R.id.zh:
					//					Toast.makeText(getActivity(), " R.id.full", Toast.LENGTH_LONG).show();
					Global.s.setlang(true);

					Global.onAirAct.changeLocale("zh");
					Global.onAirAct.chdrawer();

					break;
				case R.id.cn:
					//					Toast.makeText(getActivity(), "R.id.game", Toast.LENGTH_LONG).show();
					Global.s.setlang(false);

					Global.onAirAct.changeLocale("cn");
					Global.onAirAct.chdrawer();
					break;

				}

			}

		});

		if(Global.s.whichsize()){
			//			mode.getChildAt(mode.indexOfChild((RadioButton) v.findViewById(R.id.full)));
			langsize.check(R.id.big);
		}else{
			langsize.check(R.id.small);
		}

		langsize.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				int p = group.indexOfChild((RadioButton) v.findViewById(checkedId));

				int count = group.getChildCount();
				switch (checkedId) {
				case R.id.big:
					//					Toast.makeText(getActivity(), " R.id.full", Toast.LENGTH_LONG).show();
					Global.s.setlangsize(true);
					new Preferences(getActivity()).setFontStyle(FontStyle.Medium);
					//					Global.onAirAct.changeLocale("zh");
					break;
				case R.id.small:
					//					Toast.makeText(getActivity(), "R.id.game", Toast.LENGTH_LONG).show();
					Global.s.setlangsize(false);
					new Preferences(getActivity()).setFontStyle(FontStyle.Small);
					//					Global.onAirAct.changeLocale("cn");
					break;

				}

			}

		});
		cleargamerk=(TextView) v.findViewById(R.id.cleargamerk);
		cleargamerk.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("clear", "clicked!!");
				del1();
				del2();
				del3();
				del4();
				del5();
				Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.finish), Toast.LENGTH_LONG).show();
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
		sound=Global.s.isplaymusic();
		soundswitch=(ImageView) v.findViewById(R.id.soundswitch);
		if(sound){
			//			if(Global.onAirAct.player.isPlaying()!=true){
			//				Global.onAirAct.player.start();
			//				player.setLooping(true);
			//			}
			soundswitch.setImageResource(R.drawable.btn_switchon);
            soundswitch.setContentDescription(getActivity().getResources().getString(R.string.soundStatus1)
                    + getActivity().getResources().getString(R.string.statusOn) + getActivity().getResources().getString(R.string.soundStatus2));
		}else{
			//			if(Global.onAirAct.player.isPlaying()==true){
			//				Global.onAirAct.player.pause();
			//			}

			soundswitch.setImageResource(R.drawable.btn_switchoff);
            soundswitch.setContentDescription(getActivity().getResources().getString(R.string.soundStatus1)
                    + getActivity().getResources().getString(R.string.statusOff) + getActivity().getResources().getString(R.string.soundStatus2));
		}
		updateswitch=(ImageView) v.findViewById(R.id.updateswitch);
		update=Global.s.isplaymusic();

		soundswitch.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(sound){
					sound=false;
					Global.s.setplaymusic(false);
					if(Global.onAirAct.player.isPlaying()==true){
						Global.onAirAct.player.pause();
					}
					soundswitch.setImageResource(R.drawable.btn_switchoff);
                    soundswitch.setContentDescription(getActivity().getResources().getString(R.string.soundStatus1)
                            + getActivity().getResources().getString(R.string.statusOff) + getActivity().getResources().getString(R.string.soundStatus2));
				}else{
					sound=true;	Global.s.setplaymusic(true);
					if(Global.onAirAct.player.isPlaying()!=true){
						Global.onAirAct.player.start();
						Global.onAirAct.player.setLooping(true);
					}
					soundswitch.setImageResource(R.drawable.btn_switchon);
                    soundswitch.setContentDescription(getActivity().getResources().getString(R.string.soundStatus1)
                            + getActivity().getResources().getString(R.string.statusOn) + getActivity().getResources().getString(R.string.soundStatus2));

				}
			}
		});
		update=Global.s.isupdate();
		if(update){
			//			if(Global.onAirAct.player.isPlaying()!=true){
			//				Global.onAirAct.player.start();
			//				player.setLooping(true);
			//			}
			updateswitch.setImageResource(R.drawable.btn_switchon);
            updateswitch.setContentDescription(getActivity().getResources().getString(R.string.updateStatus1)
                    + getActivity().getResources().getString(R.string.statusOn) + getActivity().getResources().getString(R.string.updateStatus2));
		}else{
			//			if(Global.onAirAct.player.isPlaying()==true){
			//				Global.onAirAct.player.pause();
			//			}

			updateswitch.setImageResource(R.drawable.btn_switchoff);
            updateswitch.setContentDescription(getActivity().getResources().getString(R.string.updateStatus1)
                    + getActivity().getResources().getString(R.string.statusOff) + getActivity().getResources().getString(R.string.updateStatus2));
		}
		updateswitch.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(update){
					update=false;	
					Global.s.setupdate(false);
					updateswitch.setImageResource(R.drawable.btn_switchoff);
                    updateswitch.setContentDescription(getActivity().getResources().getString(R.string.updateStatus1)
                            + getActivity().getResources().getString(R.string.statusOff) + getActivity().getResources().getString(R.string.updateStatus2));
				}else{
					update=true;
					Global.s.setupdate(true);
					updateswitch.setImageResource(R.drawable.btn_switchon);
                    updateswitch.setContentDescription(getActivity().getResources().getString(R.string.updateStatus1)
                            + getActivity().getResources().getString(R.string.statusOn) + getActivity().getResources().getString(R.string.updateStatus2));

				}
			}
		});
		peroid.setText(Global.s.updaterate()+getResources().getString(R.string.refreshUnit));
        peroid.setContentDescription(getActivity().getResources().getString(R.string.peroidStatus1)
                + Global.s.updaterate()+getResources().getString(R.string.refreshUnit) + getActivity().getResources().getString(R.string.peroidStatus2));
		peroid.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ShowAlertDialogAndList();
			}
		});
		delfmy=(TextView) v.findViewById(R.id.delfmy);
		delfmy.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				delfmy();
				Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.finish), Toast.LENGTH_LONG).show();
			}
		});
                logout=(TextView) v.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Global.s.setuserid("no_user");
                Global.afterlogin=false;
                Global.onAirAct.clearBackStack();
                Global.onAirAct.pushFragment(Pages.Reallogin);
            }
        });
        userGuide=(TextView) v.findViewById(R.id.userGuide);
        userGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = getResources().getString(R.string.userGuideLink);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        aboutApp=(TextView) v.findViewById(R.id.aboutApp);
        aboutApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageDialog(getString(R.string.aboutApp),getString(R.string.aboutAppContent));
            }
        });
        accessibilityInfo=(TextView) v.findViewById(R.id.accessibilityInfo);
        accessibilityInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageDialog(getString(R.string.accessibilityInfo),getString(R.string.accessibilityInfoContent));
            }
        });
        String refreshUnit = getResources().getString(R.string.refreshUnit);
        ListVal = getResources().getStringArray(R.array.refreshOption);
        ListStr = getResources().getStringArray(R.array.refreshOption);
        for(int i=0; i < ListStr.length; i++){
            ListStr[i]+=refreshUnit;
        }
		return v;
	}
    public void messageDialog(String title, String webmessage) {

        final Dialog myDialog = new Dialog(getActivity(),android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        myDialog.setContentView(R.layout.popup_about_app);

        myDialog.setCancelable(false);


        TextView titleView = (TextView) myDialog.findViewById(R.id.title);
        titleView.setText(title);

        Log.d("WebString", webmessage);
        WebView web=(WebView) myDialog.findViewById(R.id.web);
        web.loadDataWithBaseURL("", webmessage, "text/html", "UTF-8", "");
        web.setBackgroundColor(0x00000000);
        ImageView close = (ImageView) myDialog.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                myDialog.dismiss();


            }
        });


        myDialog.show();

    }

    String[] ListVal = {};
    String[] ListStr = {};
	private void ShowAlertDialogAndList()
	{

		Builder MyAlertDialog = new AlertDialog.Builder(getActivity());
		MyAlertDialog.setTitle(getActivity().getResources().getString(R.string.updaterate));
		//建立選擇的事件
		DialogInterface.OnClickListener ListClick = new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which) {
                peroid.setText(ListStr[which]);
                Global.s.setupdaterate(Integer.parseInt(ListVal[which]));
                peroid.setContentDescription(Global.onAirAct.getResources().getString(R.string.peroidStatus1)
                        + ListStr[which] + Global.onAirAct.getResources().getString(R.string.peroidStatus2));
			}
		};
		//建立按下取消什麼事情都不做的事件
		DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which) {
			}
		};  
		MyAlertDialog.setItems(ListStr, ListClick);
		MyAlertDialog.setNeutralButton(getActivity().getResources().getString(R.string.cancel),OkClick );
		MyAlertDialog.show();
	}
	private void del1(){
		//		final String INIT_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + name + " CHAR, " 
		//				+ relationship + " CHAR, "+ remarks + " CHAR, "+ filepath + " CHAR, "+ phone + " CHAR, " + email + " CHAR);";
		SQLiteDatabase db = null ;
		Cursor cursor = null;

		db= dbhelper1.getReadableDatabase();
		cursor=db.rawQuery("DELETE FROM "+MyDBHelper_game1.TABLE_NAME+";",null);

		//		db= dbhelper2.getReadableDatabase();
		//		cursor=db.rawQuery("DELETE FROM "+MyDBHelper_game2.TABLE_NAME+";",null);
		//
		//		db= dbhelper3.getReadableDatabase();
		//		cursor=db.rawQuery("DELETE FROM "+MyDBHelper_game3.TABLE_NAME+";",null);
		//
		//		db= dbhelper4.getReadableDatabase();
		//		cursor=db.rawQuery("DELETE FROM "+MyDBHelper_game4.TABLE_NAME+";",null);
		//
		//		db= dbhelper5.getReadableDatabase();
		//		cursor=db.rawQuery("DELETE FROM "+MyDBHelper_game5.TABLE_NAME+";",null);

		cursor.moveToFirst();
		cursor.close();
		//		return temp;
		//			list.add(temp);

	}
	private void del2(){
		//		final String INIT_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + name + " CHAR, " 
		//				+ relationship + " CHAR, "+ remarks + " CHAR, "+ filepath + " CHAR, "+ phone + " CHAR, " + email + " CHAR);";
		SQLiteDatabase db = null ;
		Cursor cursor = null;

		//		db= dbhelper1.getReadableDatabase();
		//		cursor=db.rawQuery("DELETE FROM "+MyDBHelper_game1.TABLE_NAME+";",null);

		db= dbhelper2.getReadableDatabase();
		cursor=db.rawQuery("DELETE FROM "+MyDBHelper_game2.TABLE_NAME+";",null);
		//
		//		db= dbhelper3.getReadableDatabase();
		//		cursor=db.rawQuery("DELETE FROM "+MyDBHelper_game3.TABLE_NAME+";",null);
		//
		//		db= dbhelper4.getReadableDatabase();
		//		cursor=db.rawQuery("DELETE FROM "+MyDBHelper_game4.TABLE_NAME+";",null);
		//
		//		db= dbhelper5.getReadableDatabase();
		//		cursor=db.rawQuery("DELETE FROM "+MyDBHelper_game5.TABLE_NAME+";",null);

		cursor.moveToFirst();
		cursor.close();
		//		return temp;
		//			list.add(temp);

	}
	private void del3(){
		//		final String INIT_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + name + " CHAR, " 
		//				+ relationship + " CHAR, "+ remarks + " CHAR, "+ filepath + " CHAR, "+ phone + " CHAR, " + email + " CHAR);";
		SQLiteDatabase db = null ;
		Cursor cursor = null;

		//		db= dbhelper1.getReadableDatabase();
		//		cursor=db.rawQuery("DELETE FROM "+MyDBHelper_game1.TABLE_NAME+";",null);

		//		db= dbhelper2.getReadableDatabase();
		//		cursor=db.rawQuery("DELETE FROM "+MyDBHelper_game2.TABLE_NAME+";",null);
		//
		db= dbhelper3.getReadableDatabase();
		cursor=db.rawQuery("DELETE FROM "+MyDBHelper_game3.TABLE_NAME+";",null);
		//
		//		db= dbhelper4.getReadableDatabase();
		//		cursor=db.rawQuery("DELETE FROM "+MyDBHelper_game4.TABLE_NAME+";",null);
		//
		//		db= dbhelper5.getReadableDatabase();
		//		cursor=db.rawQuery("DELETE FROM "+MyDBHelper_game5.TABLE_NAME+";",null);
		cursor.moveToFirst();

		cursor.close();
		//		return temp;
		//			list.add(temp);

	}
	private void del4(){
		//		final String INIT_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + name + " CHAR, " 
		//				+ relationship + " CHAR, "+ remarks + " CHAR, "+ filepath + " CHAR, "+ phone + " CHAR, " + email + " CHAR);";
		SQLiteDatabase db = null ;
		Cursor cursor = null;

		//		db= dbhelper1.getReadableDatabase();
		//		cursor=db.rawQuery("DELETE FROM "+MyDBHelper_game1.TABLE_NAME+";",null);

		//		db= dbhelper2.getReadableDatabase();
		//		cursor=db.rawQuery("DELETE FROM "+MyDBHelper_game2.TABLE_NAME+";",null);
		//
		//		db= dbhelper3.getReadableDatabase();
		//		cursor=db.rawQuery("DELETE FROM "+MyDBHelper_game3.TABLE_NAME+";",null);
		//
		db= dbhelper4.getReadableDatabase();
		cursor=db.rawQuery("DELETE FROM "+MyDBHelper_game4.TABLE_NAME+";",null);
		//
		//		db= dbhelper5.getReadableDatabase();
		//		cursor=db.rawQuery("DELETE FROM "+MyDBHelper_game5.TABLE_NAME+";",null);
		cursor.moveToFirst();

		cursor.close();
		//		return temp;
		//			list.add(temp);

	}

	private void del5(){
		//		final String INIT_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + name + " CHAR, " 
		//				+ relationship + " CHAR, "+ remarks + " CHAR, "+ filepath + " CHAR, "+ phone + " CHAR, " + email + " CHAR);";
		SQLiteDatabase db = null ;
		Cursor cursor = null;

		//		db= dbhelper1.getReadableDatabase();
		//		cursor=db.rawQuery("DELETE FROM "+MyDBHelper_game1.TABLE_NAME+";",null);

		//		db= dbhelper2.getReadableDatabase();
		//		cursor=db.rawQuery("DELETE FROM "+MyDBHelper_game2.TABLE_NAME+";",null);
		//
		//		db= dbhelper3.getReadableDatabase();
		//		cursor=db.rawQuery("DELETE FROM "+MyDBHelper_game3.TABLE_NAME+";",null);
		//
		//		db= dbhelper4.getReadableDatabase();
		//		cursor=db.rawQuery("DELETE FROM "+MyDBHelper_game4.TABLE_NAME+";",null);
		//
		db= dbhelper5.getReadableDatabase();
		cursor=db.rawQuery("DELETE FROM "+MyDBHelper_game5.TABLE_NAME+";",null);
		cursor.moveToFirst();

		cursor.close();
		//		return temp;
		//			list.add(temp);

	}
	private void delfmy(){
		//		final String INIT_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + name + " CHAR, " 
		//				+ relationship + " CHAR, "+ remarks + " CHAR, "+ filepath + " CHAR, "+ phone + " CHAR, " + email + " CHAR);";
		SQLiteDatabase db = null ;
		Cursor cursor = null;

		//		db= dbhelper1.getReadableDatabase();
		//		cursor=db.rawQuery("DELETE FROM "+MyDBHelper_game1.TABLE_NAME+";",null);

		//		db= dbhelper2.getReadableDatabase();
		//		cursor=db.rawQuery("DELETE FROM "+MyDBHelper_game2.TABLE_NAME+";",null);
		//
		//		db= dbhelper3.getReadableDatabase();
		//		cursor=db.rawQuery("DELETE FROM "+MyDBHelper_game3.TABLE_NAME+";",null);
		//
		//		db= dbhelper4.getReadableDatabase();
		//		cursor=db.rawQuery("DELETE FROM "+MyDBHelper_game4.TABLE_NAME+";",null);
		//
		db= dbhelper.getReadableDatabase();
		cursor=db.rawQuery("DELETE FROM "+MyDBHelper_callfmy.TABLE_NAME+";",null);
		cursor.moveToFirst();

		cursor.close();
		//		return temp;
		//			list.add(temp);

	}
	class updategamedata extends AsyncTask<String, Integer, Integer>{
		ProgressDialog pd;
		@Override
		protected Integer doInBackground(String... param) {
			try {

				HttpClient client = new DefaultHttpClient();
				String temp="";
				if(Global.s.whichmode()){
					temp="0";
				}else{
					temp="1";
				}
				HttpGet get = new HttpGet(Global.apilink+"update_gamemode.php?token="+Global.onAirAct.token+"&type="+temp+"&login="+Global.s.userid());
				HttpResponse response = client.execute(get);
				HttpEntity resEntity = response.getEntity();
				String result = EntityUtils.toString(resEntity);
				JSONObject j=new JSONObject(result);
				Log.d("update_gamode", temp);
				Log.d("update_gamode", result);

				Log.d("update_gamode", Global.apilink+"update_gamemode.php?token="+Global.onAirAct.token+"&type="+temp+"&login="+Global.s.userid());
				//				`game_type`, `game_level`, `user_id`, `marks`, `correct_no`, `create_user`, `create_date`, `update_user`
				//				HttpClient client2 = new DefaultHttpClient();
				//				HttpGet get2 = new HttpGet(Global.apilink+"news_list.php?token="+Global.onAirAct.token);
				//				HttpResponse response2 = client2.execute(get2);
				//				HttpEntity resEntity2 = response2.getEntity();
				//				String result2 = EntityUtils.toString(resEntity2);
				//				JSONObject j2=new JSONObject(result2);

				//				Log.d("result", "result"+result+"\nresult2"+result2);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			return null;
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			//			pd.dismiss();

		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			//			pd = ProgressDialog.show(MainActivity.this, "Little Tree", "Loading...");  
		}

	}
}
