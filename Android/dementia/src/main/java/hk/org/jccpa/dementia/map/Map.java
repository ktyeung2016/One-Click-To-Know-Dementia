package hk.org.jccpa.dementia.map;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import hk.org.jccpa.dementia.Global;
import hk.org.jccpa.dementia.Pages;
import hk.org.jccpa.dementia.R;
import hk.org.jccpa.dementia.sql.MyDBHelper_callfmy;

import com.example.basefamework.fontsize.Preferences;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import android.location.LocationListener;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.AsyncTask;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

public class Map extends Fragment implements LocationListener {
	View v;
	ImageButton back,menu;
	Button resetlocation,callfmy;
    JSONArray familyJson = new JSONArray();
	TextView title;
	//demo
	RelativeLayout demolayout;
	LinearLayout buttonlayout,wholelayout,topbarlayout;
	//demo
	private GoogleMap map;
	private MapView mapView;
	Handler h;
	public void onCreate(Bundle savedInstanceState) {

		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);
		super.onCreate(savedInstanceState);
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);
		v = inflater.inflate(R.layout.activity_map, container, false);
		Global.whichpage=Pages.Map;
		//map
		mapView = (MapView) v.findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);
		mapView.onResume();// needed to get the map to display immediately
		View holepage=v.findViewById(R.id.holepage);
		holepage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		MapsInitializer.initialize(getActivity());

		map = mapView.getMap();


		map.setMyLocationEnabled(true);
		map.getUiSettings(). setMyLocationButtonEnabled(false);

		//map
		back=(ImageButton) v.findViewById(R.id.back);
		menu=(ImageButton) v.findViewById(R.id.menu);
		title=(TextView) v.findViewById(R.id.title);
		resetlocation=(Button) v.findViewById(R.id.resetlocation);
		callfmy=(Button) v.findViewById(R.id.callfmy);
		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Global.onAirAct.clearBackStack();
				//				Global.onAirAct.clearOneBackStack();
				Global.onAirAct.pushFragmentBackTo(Pages.Homepage1);
			}
		});
		menu.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Global.onAirAct.mSlidingMenu.toggleRightDrawer();
			}
		});
		title.setText(getResources().getString(R.string.mylocation));

		resetlocation.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(map.getMyLocation()!=null){
					LatLng latLng = new LatLng(map.getMyLocation().getLatitude(), map.getMyLocation().getLongitude());
                    //Toast.makeText(getActivity().getApplicationContext(), "latLng = "+latLng, Toast.LENGTH_LONG).show();
					//				map.setLocationSource(source)
					CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 18);
					//				Marker nkut=map.addMarker(new MarkerOptions().position(latLng).title("You are here!!!"));
					map.animateCamera(cameraUpdate);
				}else{
                    //Toast.makeText(getActivity().getApplicationContext(), "map.getMyLocation() == null", Toast.LENGTH_LONG).show();
				}
			}
		});
		callfmy.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Global.onAirAct.pushFragmentBackTo(Pages.Callfmy);
			}
		});


		h=new Handler();





		//demo
		wholelayout=(LinearLayout) v.findViewById(R.id.wholelayout);
		topbarlayout=(LinearLayout) v.findViewById(R.id.topbarlayout);
		demolayout=(RelativeLayout) v.findViewById(R.id.demolayout);
		demolayout.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}

		});
		topbarlayout.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}

		});


        if(Global.onAirAct.isNetworkOnline()){
            familyJson = new JSONArray();
            Cursor cursor = getCursor();
            while(cursor.moveToNext()){
                if(!Global.s.userid().equals("no_user")){
                    if(cursor.getString(7).equals(Global.s.userid())){
                        int id = cursor.getInt(0);

                        String name = cursor.getString(1);
                        String email = cursor.getString(6);

                        try{
                            JSONObject familyMember = new JSONObject();
                            familyMember.put("name", name);
                            familyMember.put("email", email);
                            familyJson.put(familyMember);
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            }

            LocationProvider locPro = new LocationProvider();
            Location location = locPro.getLocation(this);
            String position = "";
            try {
                if(location == null){
                    location = map.getMyLocation();
                }
                if(location != null){
                    SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy", Locale.US);
                    double longitude = location != null ? location.getLongitude() : 0;
                    double latitude = location != null ? location.getLatitude() : 0;
                    Date locationTime = location != null ? new Date(location.getTime()) : new Date();
                    position=longitude+":"+latitude+":"+URLEncoder.encode(format.format(locationTime), "UTF-8");
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            //Toast.makeText(getActivity(), familyJson.toString(), Toast.LENGTH_LONG).show();
            new emergencyCallTask().execute(Global.s.userid(), position, familyJson.toString());
        }else{
            //Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.nonetwork), Toast.LENGTH_LONG).show();
        }






		//		callfmy.getY();
		//		
		//		
		//		tip.setX(callfmy.getX());
		//		tip.setY(callfmy.getX()+tip.getWidth()/2);
		//		demolayout.addView(tip);





		//demo


		return v;
	}
    private Cursor getCursor(){

        MyDBHelper_callfmy dbhelper;
        dbhelper = new MyDBHelper_callfmy(getActivity());
        dbhelper.close();
        SQLiteDatabase db = dbhelper.getReadableDatabase();

        String[] columns = { android.provider.BaseColumns._ID,MyDBHelper_callfmy.name, MyDBHelper_callfmy.relationship, MyDBHelper_callfmy.remarks
                ,MyDBHelper_callfmy.filepath,MyDBHelper_callfmy.phone,MyDBHelper_callfmy.email,MyDBHelper_callfmy.userid};

        Cursor cursor = db.query(MyDBHelper_callfmy.TABLE_NAME, columns, null, null, null, null, null);

        getActivity().startManagingCursor(cursor);

        return cursor;

    }
	public void onResume(){
		super.onResume();
		h.postDelayed(new Runnable(){
			public void run() { 
				// do something             

				if(map.getMyLocation()==null){
					h.postDelayed(this, 100);
				}else{
					LatLng latLng = new LatLng(map.getMyLocation().getLatitude(), map.getMyLocation().getLongitude());
					//				map.setLocationSource(source)
					CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 18);
					//				Marker nkut=map.addMarker(new MarkerOptions().position(latLng).title("You are here!!!"));
					map.animateCamera(cameraUpdate);

					//					wholelayout.bringToFront();
					//					demolayout.bringToFront();
					//					resetlocation.setEnabled(false);
					//					buttonlayout=(LinearLayout) v.findViewById(R.id.buttonlayout);
					//					buttonlayout.bringToFront();
					//					Button tip = new Button(getActivity());

					//					LayoutInflater inflater = getActivity().getLayoutInflater();
					//					View rowView = inflater.inflate(R.layout.demobar, null);
					//					demolayout.addView(rowView);
					//					tip.setBackgroundResource(R.drawable.messagedialogdown);
					//					tip.setText(getResources().getString(R.string.mapfunctionstep1));
					//					RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 150);
					//					params.topMargin=50;
					//					params.leftMargin=50;
					//					params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
					//					params.leftMargin = (int) callfmy.getX()- (tip.getWidth()/ 2);

					//					params.topMargin = (int) buttonlayout.getY() - (tip.getHeight()/2);


					//					Log.d("Margin", "Margin"+"     "+params.leftMargin+"    "+params.topMargin+"   "+callfmy.getX()+"/"+callfmy.getY());
					//					demolayout.addView(tip, params);

				}
			}}, 100); 


	}

    @Override
    public void onLocationChanged(Location location) {
        Log.d("Map", "onLocationChanged().location="+location);
        /*String position = "";
        try {
            SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy", Locale.US);
            double longitude = location != null ? location.getLongitude() : 0;
            double latitude = location != null ? location.getLatitude() : 0;
            Date locationTime = location != null ? new Date(location.getTime()) : new Date();
            position=longitude+":"+latitude+":"+URLEncoder.encode(format.format(locationTime), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //Toast.makeText(getActivity(), familyJson.toString(), Toast.LENGTH_LONG).show();
        new emergencyCallTask().execute(Global.s.userid(), position, familyJson.toString());*/
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    class emergencyCallTask extends AsyncTask<String, Integer, Integer> {
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

                if(param.length > 2){
                    HttpClient client = new DefaultHttpClient();
                    String requestURL = Global.apilink+"send_position.php?user_id="+ param[0]+"&position="+param[1]+"&family="+URLEncoder.encode(param[2], "UTF-8");
                    Log.d("emergencyCallTask","requestURL="+requestURL);
                    HttpGet get = new HttpGet(requestURL);
                    HttpResponse response = client.execute(get);
                    HttpEntity resEntity = response.getEntity();
                    String result = EntityUtils.toString(resEntity);
                    Log.d("result","\nresult"+result);
                    j2=new JSONObject(result);
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            //pd.dismiss();
            try {
                if(j2!=null){
                    if(j2.getString("status").equals("Y")){

                    }
                }else{
                    // any handling for invalid JSON
                }
            } catch (JSONException e) {
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
            //pd = ProgressDialog.show(getActivity(), "Dementia", "Loading...");
        }

    }
}
