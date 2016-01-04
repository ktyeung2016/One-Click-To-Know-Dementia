package hk.org.jccpa.dementia.beforelogin;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.basefamework.fontsize.Preferences;

import hk.org.jccpa.dementia.Global;
import hk.org.jccpa.dementia.R;
import hk.org.jccpa.dementia.sql.MyDBHelper_knowledge;
import hk.org.jccpa.dementia.sql.MyDBHelper_news;
import hk.org.jccpa.dementia.sql.MyDBHelper_soc;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Loadingupdatepage extends Activity {
	int loadingrate= 0;
	ProgressBar progressBar1;
	TextView precentage,tips;
	Handler h=new Handler();
	Handler h2=new Handler();
	String []randtip;
	boolean timeout=false;
	MyDBHelper_news newsdb;
	MyDBHelper_knowledge knowledgedb;
	MyDBHelper_soc socdb;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getTheme().applyStyle(new Preferences(this).getFontStyle().getResId(), true);
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		getActionBar().hide();
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

		setContentView(R.layout.activity_loadingdatapage);
		progressBar1=(ProgressBar) findViewById(R.id.progressBar1);
		precentage=(TextView) findViewById(R.id.precentage);
		newsdb = new MyDBHelper_news(this);
		knowledgedb=new MyDBHelper_knowledge(this);
		socdb=new MyDBHelper_soc(this);
		tips=(TextView) findViewById(R.id.tips);
		randtip=getResources().getStringArray(R.array.tipsarray);
		int which=(int) (System.currentTimeMillis()%randtip.length);
		tips.setText(randtip[which]);
		h2.postDelayed(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				int which2=(int) (System.currentTimeMillis()%randtip.length);
				tips.setText(randtip[which2]);
				h2.postDelayed(this,5000);
			}

		}, 5000);
		if(Global.onAirAct.isOnline()){


			h.postDelayed(new Runnable(){
				public void run() { 
					// do something             
					if(loadingrate!=100){
						if(loadingrate==0){
							loadingrate+=25;	
							progressBar1.setProgress(loadingrate);
							precentage.setText(loadingrate+"%");
							new checkver().execute();
//							new getsoc().execute();
						}
					}else{
						Global.login=true;
						//					loadingrate+=10;
						//					progressBar1.setProgress(loadingrate);
						//					precentage.setText(loadingrate+"%");
						//					h.postDelayed(this, 1000);
						finish();
					}



				}}, 500); 
		}else{
			Global.login=true;
			//			loadingrate+=10;
			//			progressBar1.setProgress(loadingrate);
			//			precentage.setText(loadingrate+"%");
			//			h.postDelayed(this, 1000);
			finish();
		}
	}


	class checkver extends AsyncTask<String, Integer, Integer>{
		ProgressDialog pd;
		JSONObject j2=null;
		@Override
		protected Integer doInBackground(String... param) {
			try {
				HttpClient client2 = new DefaultHttpClient();
				HttpGet get2 = new HttpGet(Global.apilink+"other_val_list.php?token="+Global.onAirAct.token);
				HttpResponse response2 = client2.execute(get2);
				HttpEntity resEntity2 = response2.getEntity();
				String result2 = EntityUtils.toString(resEntity2);
				j2=new JSONObject(result2);

				Log.d("news", "result2"+result2);

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
			try {
				if(j2!=null){
					JSONArray temp= j2.getJSONArray("other_val");
					for(int i =0 ;i<temp.length();i++){
						String version= temp.getJSONObject(i).getString("content");
						if(temp.getJSONObject(i).getString("title").equals("news")){
							if(version.equals(Global.s.last1version())==false){
								Global.s.setlast1version(temp.getJSONObject(i).getString("content"));
								Log.d("news", "news need update   "+Global.s.last1version()+"   /   "+temp.getJSONObject(i).getString("content"));
								new getnews().execute();
							}else{
								newsdb.close();
								loadingrate+=25;
								progressBar1.setProgress(loadingrate);
								precentage.setText(loadingrate+"%");
							}
						}
						if(temp.getJSONObject(i).getString("title").equals("knowledge")){
							if(version.equals(Global.s.last2version())==false){
								Global.s.setlast2version(temp.getJSONObject(i).getString("content"));
								new getknowledge().execute();
							}else{
								knowledgedb.close();
								loadingrate+=25;
								progressBar1.setProgress(loadingrate);
								precentage.setText(loadingrate+"%");
							}
						}
						if(temp.getJSONObject(i).getString("title").equals("soc")){
							if(version.equals(Global.s.last3version())==false){
								Global.s.setlast3version(temp.getJSONObject(i).getString("content"));
								new getsoc().execute();
							}else{
								socdb.close();
								loadingrate+=25;
								progressBar1.setProgress(loadingrate);
								precentage.setText(loadingrate+"%");
							}
						}
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Intent newAct = new Intent();
			newAct.setClass( Loadingupdatepage.this, Loadingdatapage.class );
			startActivity( newAct );
			finish();

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

	public void onBackPressed(){
		super.onBackPressed();
		System.exit(0);
	}
	class getnews extends AsyncTask<String, Integer, Integer>{
		ProgressDialog pd;
		@Override
		protected Integer doInBackground(String... param) {
			try {



				HttpClient client2 = new DefaultHttpClient();
				HttpGet get2 = new HttpGet(Global.apilink+"news_list.php?token="+Global.onAirAct.token);
				HttpResponse response2 = client2.execute(get2);
				HttpEntity resEntity2 = response2.getEntity();
				String result2 = EntityUtils.toString(resEntity2);
				JSONObject j2=new JSONObject(result2);
				//				addtodbnews();
				Log.d("news", "news:   "+result2);
				delnews();
				JSONArray temp=j2.getJSONArray("news");
				for(int i = 0 ;i<temp.length();i++){
					if(temp.getJSONObject(i).getString("display").equals("0")){
						addtodbnews(temp.getJSONObject(i).getString("title_zh"),temp.getJSONObject(i).getString("title_gb"),temp.getJSONObject(i).getString("html_content_zh"),temp.getJSONObject(i).getString("html_content_gb"));
					}
				}
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





			newsdb.close();
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
	private void addtodbnews(String title_zh,String title_gb,String html_content_zh,String html_content_gb){

		SQLiteDatabase db = newsdb.getWritableDatabase();

		ContentValues values = new ContentValues();
		//	       name,phone,email,relationship,remarks
		values.put(MyDBHelper_news.title_zh,  title_zh);
		values.put(MyDBHelper_news.title_gb,  title_gb);
		values.put(MyDBHelper_news.html_content_zh,  html_content_zh);
		values.put(MyDBHelper_news.html_content_gb,  html_content_gb);

		db.insert(MyDBHelper_news.TABLE_NAME, null, values);

		//		Global.onAirAct.clearOneBackStack();

	}
	private void delnews(){
		//		final String INIT_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + name + " CHAR, " 
		//				+ relationship + " CHAR, "+ remarks + " CHAR, "+ filepath + " CHAR, "+ phone + " CHAR, " + email + " CHAR);";
		SQLiteDatabase db = null ;
		Cursor cursor = null;

		//		db= dbhelper1.getReadableDatabase();
		//		cursor=db.rawQuery("DELETE FROM "+MyDBHelper_game1.TABLE_NAME+";",null);

		//		db= dbhelper2.getReadableDatabase();
		//		cursor=db.rawQuery("DELETE FROM "+MyDBHelper_game2.TABLE_NAME+";",null);
		//
		db= newsdb.getReadableDatabase();
		cursor=db.rawQuery("DELETE FROM "+MyDBHelper_news.TABLE_NAME+";",null);
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



	class getknowledge extends AsyncTask<String, Integer, Integer>{
		ProgressDialog pd;
		@Override
		protected Integer doInBackground(String... param) {
			try {



				HttpClient client2 = new DefaultHttpClient();
				HttpGet get2 = new HttpGet(Global.apilink+"knowledge_list.php?token="+Global.onAirAct.token);
				HttpResponse response2 = client2.execute(get2);
				HttpEntity resEntity2 = response2.getEntity();
				String result2 = EntityUtils.toString(resEntity2);
				JSONObject j2=new JSONObject(result2);
				//				addtodbnews();
				Log.d("knowledge", "knowledge:   "+result2);
				//				delnews();
				delknowledge();
				JSONArray temp=j2.getJSONArray("knowledge");
				for(int i = 0 ;i<temp.length();i++){
					//					if(temp.getJSONObject(i).getString("display").equals("0")){
					addtodbknowledge(temp.getJSONObject(i).getString("id"),temp.getJSONObject(i).getString("redirect_session"),temp.getJSONObject(i).getString("title_zh"),temp.getJSONObject(i).getString("title_gb"),temp.getJSONObject(i).getString("html_content_zh"),temp.getJSONObject(i).getString("html_content_gb"));
					//					}
				}
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





			knowledgedb.close();
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
	private void addtodbknowledge(String dbid,String redirect,String title_zh,String title_gb,String html_content_zh,String html_content_gb){

		SQLiteDatabase db = knowledgedb.getWritableDatabase();

		ContentValues values = new ContentValues();
		//	       name,phone,email,relationship,remarks
		values.put(MyDBHelper_knowledge.title_zh,  title_zh);
		values.put(MyDBHelper_knowledge.title_gb,  title_gb);
		values.put(MyDBHelper_knowledge.html_content_zh,  html_content_zh);
		values.put(MyDBHelper_knowledge.html_content_gb,  html_content_gb);
		values.put(MyDBHelper_knowledge.dbid,  dbid);
		values.put(MyDBHelper_knowledge.redirect,  redirect);

		db.insert(MyDBHelper_knowledge.TABLE_NAME, null, values);

		//		Global.onAirAct.clearOneBackStack();

	}
	private void delknowledge(){
		//		final String INIT_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + name + " CHAR, " 
		//				+ relationship + " CHAR, "+ remarks + " CHAR, "+ filepath + " CHAR, "+ phone + " CHAR, " + email + " CHAR);";
		SQLiteDatabase db = null ;
		Cursor cursor = null;

		//		db= dbhelper1.getReadableDatabase();
		//		cursor=db.rawQuery("DELETE FROM "+MyDBHelper_game1.TABLE_NAME+";",null);

		//		db= dbhelper2.getReadableDatabase();
		//		cursor=db.rawQuery("DELETE FROM "+MyDBHelper_game2.TABLE_NAME+";",null);
		//
		db= knowledgedb.getReadableDatabase();
		cursor=db.rawQuery("DELETE FROM "+MyDBHelper_knowledge.TABLE_NAME+";",null);
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


	class getsoc extends AsyncTask<String, Integer, Integer>{
		ProgressDialog pd;
		@Override
		protected Integer doInBackground(String... param) {
			try {



				HttpClient client2 = new DefaultHttpClient();
				HttpGet get2 = new HttpGet(Global.apilink+"document_list.php?token="+Global.onAirAct.token);
				HttpResponse response2 = client2.execute(get2);
				HttpEntity resEntity2 = response2.getEntity();
				Log.d("document", Global.apilink+"document_list.php?token="+Global.onAirAct.token);
				String result2 = EntityUtils.toString(resEntity2);
				JSONObject j2=new JSONObject(result2);
				//				addtodbnews();
				Log.d("document", "document soc:   "+result2);
				//				delnews();
				delsoc();
				JSONArray temp=j2.getJSONArray("document");
				for(int i = 0 ;i<temp.length();i++){
					//					if(temp.getJSONObject(i).getString("display").equals("0")){
					addtodbsoc(
							temp.getJSONObject(i).getString("id"),
							temp.getJSONObject(i).getString("title_zh"),
							temp.getJSONObject(i).getString("title_gb"),
							temp.getJSONObject(i).getString("organization_zh"),
							temp.getJSONObject(i).getString("organization_gb"),
							temp.getJSONObject(i).getString("service_name1_zh")
							,temp.getJSONObject(i).getString("service_name1_gb"),
							temp.getJSONObject(i).getString("service_name2_zh"),
							temp.getJSONObject(i).getString("service_name2_gb"),
							temp.getJSONObject(i).getString("district_zh"),
							temp.getJSONObject(i).getString("district_gb")
							,temp.getJSONObject(i).getString("first"),
							temp.getJSONObject(i).getString("second")
							,temp.getJSONObject(i).getString("third")
							,temp.getJSONObject(i).getString("four")
							,temp.getJSONObject(i).getString("family"),
							temp.getJSONObject(i).getString("helper")
							,temp.getJSONObject(i).getString("remark_zh")
							,temp.getJSONObject(i).getString("remark_gb"),
							temp.getJSONObject(i).getString("phone"),
							temp.getJSONObject(i).getString("website")
							,temp.getJSONObject(i).getString("temp")
							);
					//					}
				}
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





			socdb.close();
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
	private void addtodbsoc(
			String id,
			String title_zh,
			String title_gb,
			String organization_zh,
			String organization_gb,
			String service_name1_zh
			,String service_name1_gb,
			String service_name2_zh,
			String service_name2_gb,
			String district_zh,
			String district_gb
			,String first,
			String second
			,String third
			,String four
			,String family,
			String helper
			,String remark_zh
			,String remark_gb,
			String phone,
			String website
			,String temp){

		SQLiteDatabase db = socdb.getWritableDatabase();

		ContentValues values = new ContentValues();
		//	       name,phone,email,relationship,remarks
		//		values.put(MyDBHelper_knowledge.title_zh,  title_zh);
		//		values.put(MyDBHelper_knowledge.title_gb,  title_gb);
		//		values.put(MyDBHelper_knowledge.html_content_zh,  html_content_zh);
		//		values.put(MyDBHelper_knowledge.html_content_gb,  html_content_gb);
		//		values.put(MyDBHelper_knowledge.dbid,  dbid);
		//		values.put(MyDBHelper_knowledge.redirect,  redirect);
		values.put(MyDBHelper_soc.dbid,id);
		values.put(MyDBHelper_soc.title_zh,title_zh);
		values.put(MyDBHelper_soc.title_gb,title_gb);
		values.put(MyDBHelper_soc.organization_zh,organization_zh);
		values.put(MyDBHelper_soc.organization_gb,organization_gb);
		values.put(MyDBHelper_soc.service_name1_zh,service_name1_zh);
		values.put(MyDBHelper_soc.service_name1_gb,service_name1_gb);
		values.put(MyDBHelper_soc.service_name2_zh,service_name2_zh);
		values.put(MyDBHelper_soc.service_name2_gb,service_name2_gb);
		values.put(MyDBHelper_soc.district_zh,district_zh);
		values.put(MyDBHelper_soc.district_gb,district_gb);
		values.put(MyDBHelper_soc.first,first);
		values.put(MyDBHelper_soc.second,second);
		values.put(MyDBHelper_soc.third,third);
		values.put(MyDBHelper_soc.four,four);
		values.put(MyDBHelper_soc.family,family);
		values.put(MyDBHelper_soc.helper,helper);
		values.put(MyDBHelper_soc.remark_zh,remark_zh);
		values.put(MyDBHelper_soc.remark_gb,remark_gb);
		values.put(MyDBHelper_soc.phone,phone);
		values.put(MyDBHelper_soc.website,website);
		values.put(MyDBHelper_soc.temp,temp);
		db.insert(MyDBHelper_soc.TABLE_NAME, null, values);

		//		Global.onAirAct.clearOneBackStack();

	}
	private void delsoc(){
		//		final String INIT_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + name + " CHAR, " 
		//				+ relationship + " CHAR, "+ remarks + " CHAR, "+ filepath + " CHAR, "+ phone + " CHAR, " + email + " CHAR);";
		SQLiteDatabase db = null ;
		Cursor cursor = null;

		//		db= dbhelper1.getReadableDatabase();
		//		cursor=db.rawQuery("DELETE FROM "+MyDBHelper_game1.TABLE_NAME+";",null);

		//		db= dbhelper2.getReadableDatabase();
		//		cursor=db.rawQuery("DELETE FROM "+MyDBHelper_game2.TABLE_NAME+";",null);
		//
		db= socdb.getReadableDatabase();
		cursor=db.rawQuery("DELETE FROM "+MyDBHelper_soc.TABLE_NAME+";",null);
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
}
