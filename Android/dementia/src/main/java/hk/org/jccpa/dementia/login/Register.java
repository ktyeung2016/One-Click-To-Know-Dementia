package hk.org.jccpa.dementia.login;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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


import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class Register extends Fragment {
	View v;
	Button back;
	Button submit,reset;
	EditText userid,age;
	String genderString="1";
	String gameString="0";
	RadioGroup gender_radio_group,mode_radio_group;
	public void onCreate(Bundle savedInstanceState) {

		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);
		super.onCreate(savedInstanceState);
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);
		v = inflater.inflate(R.layout.activity_register, container, false);
		back=(Button) v.findViewById(R.id.back);
		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Global.onAirAct.clearOneBackStack();
			}
		});

		View holepage=v.findViewById(R.id.holepage);
		holepage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		userid=(EditText) v.findViewById(R.id.userid);
		age=(EditText) v.findViewById(R.id.age);
		gender_radio_group=(RadioGroup) v.findViewById(R.id.gender_radio_group);
		mode_radio_group=(RadioGroup) v.findViewById(R.id.mode_radio_group);
		gender_radio_group.check(R.id.boy);

		gender_radio_group.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				//				int id= group.getCheckedRadioButtonId();
				switch (group.getCheckedRadioButtonId()) {
				case R.id.boy:
					genderString="1";
					break;
				case R.id.girl:
					genderString="0";
					break;

				}

			}
		});
		mode_radio_group.check(R.id.full);
		mode_radio_group.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				//				int id= group.getCheckedRadioButtonId();
				switch (group.getCheckedRadioButtonId()) {
				case R.id.game:
					gameString="1";
					break;
				case R.id.full:
					gameString="0";
					break;

				}

			}
		});

		submit=(Button) v.findViewById(R.id.submit);
		submit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(userid.getText().toString().length()>0){
					if(age.getText().toString().length()>0){
						new register().execute();
					}else{
						Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.plzinputage), Toast.LENGTH_LONG).show();

					}	
				}else{
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.plzinputname), Toast.LENGTH_LONG).show();

				}
			}
		});
		reset=(Button) v.findViewById(R.id.reset);
		reset.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				gender_radio_group.check(R.id.boy);
				mode_radio_group.check(R.id.full);
				userid.setText("");
				age.setText("");
			}
		});

		//		gender_radio_group.check(R.id.girl);
		return v;
	}

	class register extends AsyncTask<String, Integer, Integer>{
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
				HttpGet get2 = new HttpGet(Global.apilink+"register.php?token="+Global.onAirAct.token+"&device_type=Android"+"&type="+gameString+"&age="+age.getText().toString()+"&login="+URLEncoder.encode(userid.getText().toString(),"UTF-8")+"&gender="+genderString+"&nickname="+URLEncoder.encode(userid.getText().toString(),"UTF-8") + "&user_key="+Global.s.userKey());
				HttpResponse response2 = client2.execute(get2);
				HttpEntity resEntity2 = response2.getEntity();
				String result2 = EntityUtils.toString(resEntity2);
				j2=new JSONObject(result2);

				Log.d("result","\nresult2"+result2);
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
				if(j2.getString("status").equals("Y")){
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.registerdone), Toast.LENGTH_LONG).show();
                    JSONArray temp=j2.getJSONArray("user");
                    //					JSONObject tempobject=temp.getJSONObject(0)
                    Global.s.setuserlogin(URLEncoder.encode(userid.getText().toString(),"UTF-8"));
                    Global.s.setuserKey(temp.getJSONObject(0).getString("user_key"));
					Global.onAirAct.clearBackStack();
					Global.onAirAct.pushFragment(Pages.Reallogin);
				}else{
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.usernameexcit), Toast.LENGTH_LONG).show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
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
			//			pd = ProgressDialog.show(MainActivity.this, "Little Tree", "Loading...");  
		}

	}



}
