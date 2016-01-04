package hk.org.jccpa.dementia.beforelogin;

import java.io.IOException;

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



import hk.org.jccpa.dementia.Global;
import hk.org.jccpa.dementia.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class Launchpage extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getTheme().applyStyle(new Preferences(this).getFontStyle().getResId(), true);
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		getActionBar().hide();
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		setContentView(R.layout.activity_launchpage);
		//		Timer timer = new Timer();
		//		timer.schedule(new TimerTask() {
		//
		//			@Override 
		//			public void run() { 
		//
		//				Intent newAct = new Intent();
		//	            newAct.setClass( Launchpage.this, Loadingdatapage.class );
		//	            startActivity( newAct );
		//				finish();
		//			} 
		//		}, 5000);
		if(Global.onAirAct.isOnline()){
			new getToken().execute();
		}else{
			Intent newAct = new Intent();
			newAct.setClass( Launchpage.this, Loadingupdatepage.class );
			startActivity( newAct );
			finish();
		}
	}
	class getToken extends AsyncTask<String, Integer, Integer>{
		ProgressDialog pd;
		@Override
		protected Integer doInBackground(String... param) {
			try {

				HttpClient client = new DefaultHttpClient();
				HttpGet get = new HttpGet(Global.apilink+"auth.php");
				HttpResponse response = client.execute(get);
				HttpEntity resEntity = response.getEntity();
				String result = EntityUtils.toString(resEntity);
				JSONObject j=new JSONObject(result);
				Log.d("result", "result"+result);
				Global.onAirAct.token = j.getString("token");

				//				HttpClient client2 = new DefaultHttpClient();
				//				HttpGet get2 = new HttpGet(Global.apilink+"news_list.php?token="+Global.onAirAct.token);
				//				HttpResponse response2 = client2.execute(get2);
				//				HttpEntity resEntity2 = response2.getEntity();
				//				String result2 = EntityUtils.toString(resEntity2);
				//				JSONObject j2=new JSONObject(result2);

				//				
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
			if(Global.onAirAct.token !=null){
				Intent newAct = new Intent();
				newAct.setClass( Launchpage.this, Loadingupdatepage.class );
				startActivity( newAct );
				finish();
			}
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
}
