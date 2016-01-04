package hk.org.jccpa.dementia.login;

import java.io.IOException;
import java.net.URLEncoder;

import hk.org.jccpa.dementia.Global;
import hk.org.jccpa.dementia.Pages;
import hk.org.jccpa.dementia.R;

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


import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;

public class Reallogin extends Fragment {
	View v;
	Button register,login,registerlater;
	EditText userlogin;
	PopupWindow pwindo;
	public void onCreate(Bundle savedInstanceState) {
		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);

		super.onCreate(savedInstanceState);

	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);
		v = inflater.inflate(R.layout.activity_reallogin, container, false);

		//			Global.afterlogin=true;
		//			Global.onAirAct.pushFragment(Pages.Homepage1);
		//		


		register=(Button) v.findViewById(R.id.register);

		register.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(Global.onAirAct.isNetworkOnline()){
					Global.onAirAct.pushFragment(Pages.Register);
				}else{
					Toast.makeText(getActivity(), "請連接網絡!／请连接网络!", Toast.LENGTH_LONG).show();
				}
			}
		});
		login=(Button) v.findViewById(R.id.login);
		login.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(userlogin.getText().toString().length()>0){
					InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
					imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
					if(Global.onAirAct.isNetworkOnline()){
						new logintask().execute();
					}else{
						Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.nonetwork), Toast.LENGTH_LONG).show();
					}
					

				}else{
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.plzinputname), Toast.LENGTH_LONG).show();

				}
				//				Global.onAirAct.pushFragment(Pages.Homepage1);

			}
		});
		View holepage=v.findViewById(R.id.holepage);
		holepage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		//	    LayoutInflater inflater2 = (LayoutInflater)
		//	    	       getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		//	    	    PopupWindow pw = new PopupWindow(
		//	    	       inflater.inflate(R.layout.tandc, null, false), 
		//	    	       100,  
		//	    	       100,  
		//	    	       true); 
		//	    	    // The code below assumes that the root container has an id called 'main' 
		//	    	    
		//	    	    pw.showAtLocation(container, Gravity.CENTER, 0, 0); 
		//		showPopup();
		if(Global.s.ispfirstuse()==true){
			Global.onAirAct.pushFragment(Pages.Tandc);
		}
		registerlater=(Button) v.findViewById(R.id.registerlater);
		registerlater.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Global.afterlogin=true;
				Global.onAirAct.pushFragment(Pages.Homepage1);

			}
		});
		userlogin=(EditText) v.findViewById(R.id.userlogin);
		if(!Global.s.userid().equals("no_user") && !Global.afterlogin  ){
			InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
			imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
			userlogin.setText(Global.s.userlogin());
			if(Global.onAirAct.isOnline()){
				new logintask().execute();
			}else{

				//				Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.nonetwork), Toast.LENGTH_LONG).show();
				Global.afterlogin=true;
				Global.onAirAct.pushFragment(Pages.Homepage1);
			}

		}
		return v;
	}
	public void onResume(){
		super.onResume();
		InputMethodManager inputManager = 
				(InputMethodManager) getActivity().
				getSystemService(Context.INPUT_METHOD_SERVICE); 
		inputManager.hideSoftInputFromWindow(
				getActivity().getCurrentFocus().getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS); 

	}
	private void showPopup(){ 
		LayoutInflater inflater2 = (LayoutInflater)
				getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		//		Button btn_closepopup=(Button)layout.findViewById(R.id.btn_closePoppup);
		View tempView = inflater2.inflate(R.layout.tandc, null, false);
		pwindo=new PopupWindow( tempView,LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT,true);

		pwindo.showAsDropDown(tempView);
		//		chartContainer1.addView(mChart);

		//		btn_closepopup.setOnClickListener(new OnClickListener() {
		//		 
		//		    @Override 
		//		    public void onClick(View arg0) {
		//		    // TODO Auto-generated method stub 
		//		                                            pwindo.dismiss(); 
		//		    } 
		//		    }); 
	} 
	class logintask extends AsyncTask<String, Integer, Integer>{
		ProgressDialog pd;
		JSONObject j2;
		@Override
		protected Integer doInBackground(String... param) {
			try {

				//				HttpClient client = new DefaultHttpClient();
				//				HttpGet get = new HttpGet(Global.apilink+"auth.php");
				//				HttpResponse response = client.execute(get);
				//				HttpEntity resEntity = response.getEntity();
				//				String result = EntityUtils.toString(resEntity);
				//				JSONObject j=new JSONObject(result);
				//				MainActivity.token = j.getString("token");

				HttpClient client2 = new DefaultHttpClient();
				HttpGet get2 = new HttpGet(Global.apilink+"login.php?token="+Global.onAirAct.token+"&login="+URLEncoder.encode(userlogin.getText().toString(),"UTF-8")+"&user_key="+Global.s.userKey());
				HttpResponse response2 = client2.execute(get2);
				HttpEntity resEntity2 = response2.getEntity();
				String result2 = EntityUtils.toString(resEntity2);
				Log.d("result","\nresult2"+result2);
				j2=new JSONObject(result2);

				//				Log.d("result","\nresult2"+result2);
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
			pd.dismiss();
			try {
				if(j2!=null){
					if(j2.getString("status").equals("Y")){
						JSONArray temp=j2.getJSONArray("user");
						//					JSONObject tempobject=temp.getJSONObject(0)
						Global.s.setuserid(temp.getJSONObject(0).getString("id"));
						Global.s.setuserlogin(userlogin.getText().toString());
                        Global.s.setuserKey(temp.getJSONObject(0).getString("user_key"));
						if(temp.getJSONObject(0).getString("type").equals("1")){
							Global.s.setmode(false);
						}else{
							Global.s.setmode(true);
						}
						Global.onAirAct.chdrawer();
						Log.d("JSONObject_get_id", temp.getJSONObject(0).getString("id"));
						Global.afterlogin=true;
						Global.onAirAct.pushFragment(Pages.Homepage1);

					}else{
						Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.plzinputrightname), Toast.LENGTH_LONG).show();
					}
				}else{
					Global.afterlogin=true;
					Global.onAirAct.pushFragment(Pages.Homepage1);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pd = ProgressDialog.show(getActivity(), "Dementia", "Loading...");  
		}

	}
}
