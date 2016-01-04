package hk.org.jccpa.dementia.map;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import android.location.LocationListener;

import hk.org.jccpa.dementia.Global;
import hk.org.jccpa.dementia.sharepreference;
import hk.org.jccpa.dementia.sql.MyDBHelper_callfmy;

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

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;


//public class MyService extends Service {
//
//	public static final String TAG = "MyService";
//
//
//	private LocationManager lms;
//
//
//	public Double longitude ;	//®˙±o∏g´◊
//	public Double latitude ;
//	String getSimSerialNumber;
//	String position;
//
//	Handler h;
//	Runnable r;
//	sharepreference s;
//	private String bestProvider = LocationManager.GPS_PROVIDER;
//	@Override
//	public void onCreate() {
//		super.onCreate();
//		Log.d(TAG, "onCreate() executed");
//
//
//	}
//	@Override
//	public void onStart(Intent intent, int startId) {
//		s=new sharepreference(this);
//		h=new Handler();
//		r=new Runnable(){
//
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				if(!s.userid().equals("no_user")){
//
//
//					LocationManager status = (LocationManager) (getSystemService(Context.LOCATION_SERVICE));
//					if (status.isProviderEnabled(LocationManager.GPS_PROVIDER) || status.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
//						//¶p™GGPS©Œ∫Ù∏Ù©w¶Ï∂}±“°A©I•slocationServiceInitial()ßÛ∑s¶Ï∏m
//
//						locationServiceInitial();
//					} else {
//						//Toast.makeText(this, "Ω–∂}±“©w¶Ï™A∞»", Toast.LENGTH_LONG).show();
//						//startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));	//∂}±“≥]©w≠∂≠±
//					}
//
//				}
//				h.postDelayed(r, 1000);
//			}
//
//		};
//		h.postDelayed(r, 1000);
//		super.onStart(intent, startId);
//	}
//
//	@Override
//	public int onStartCommand(Intent intent, int flags, int startId) {
//		Log.d(TAG, "onStartCommand() executed");
//		//		return super.onStartCommand(intent, flags, startId);
//		return START_STICKY; 
//	}
//
//	@Override
//	public void onDestroy() {
//		super.onDestroy();
//		Log.d(TAG, "onDestroy() executed");
//	}
//
//	@Override
//	public IBinder onBind(Intent intent) {
//		return null;
//	}
//	private void locationServiceInitial() {
//		lms = (LocationManager) getSystemService(LOCATION_SERVICE);	//®˙±o®t≤Œ©w¶Ï™A∞»
//		Criteria criteria = new Criteria();	//∏Í∞T¥£®—™ÃøÔ®˙º–∑«
//		bestProvider = lms.getBestProvider(criteria, true);	//øÔæ‹∫Î∑«´◊≥Ã∞™™∫¥£®—™Ã
//		Location location = lms.getLastKnownLocation(bestProvider);
//		getLocation(location);
//	}
//	private void getLocation(Location location) {	//±N©w¶Ï∏Í∞T≈„•‹¶bµe≠±§§
//		if(location != null) {
//
//
//			longitude = location.getLongitude();	//®˙±o∏g´◊
//			latitude = location.getLatitude();	//®˙±oΩn´◊
//			position=longitude+":"+latitude;
//			new Savelocation().execute();			
//		}
//		else {
//			//Toast.makeText(this, "µL™k©w¶ÏÆyº–", Toast.LENGTH_LONG).show();
//		}
//
//	}
//

//}


//繼承android.app.Service
public class MyService extends Service implements LocationListener {
    private Handler handler = new Handler();
    String position;
    private LocationManager lms;
    public Double longitude;    //®˙±o∏g´◊
    public Double latitude;
    private String bestProvider = LocationManager.GPS_PROVIDER;
    sharepreference s;
    public static boolean start = false;

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onStart(Intent intent, int startId) {

        super.onStart(intent, startId);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("time:", "onStartCommand() executed");

        s = new sharepreference(this);
        handler.postDelayed(showTime, 1000);
//				return super.onStartCommand(intent, flags, startId);
        return START_STICKY_COMPATIBILITY;
//		return START_REDELIVER_INTENT; 
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacks(showTime);
        start = false;
        super.onDestroy();
    }

    private Runnable showTime = new Runnable() {
        public void run() {
            //log目前時間
            start = true;
            Log.i("time:", new Date().toString());
            Log.i("MyService.showTime", "userid=" + s.userid());
            if (!s.userid().equals("no_user")) {


                LocationManager status = (LocationManager) (getSystemService(Context.LOCATION_SERVICE));
                Log.i("MyService.showTime", "GPS_PROVIDER=" + status.isProviderEnabled(LocationManager.GPS_PROVIDER));
                Log.i("MyService.showTime", "NETWORK_PROVIDER=" + status.isProviderEnabled(LocationManager.NETWORK_PROVIDER));
                if (status.isProviderEnabled(LocationManager.GPS_PROVIDER) || status.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                    //¶p™GGPS©Œ∫Ù∏Ù©w¶Ï∂}±“°A©I•slocationServiceInitial()ßÛ∑s¶Ï∏m

                    //locationServiceInitial();
                    getLocation();
                } else {
                    //Toast.makeText(this, "Ω–∂}±“©w¶Ï™A∞»", Toast.LENGTH_LONG).show();
                    //startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));	//∂}±“≥]©w≠∂≠±
                }

            }
            handler.postDelayed(this, s.updaterate() * 60 * 1000);//s.updaterate()*60*
        }
    };

    class Savelocation extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */

        /**
         * Saving product
         */
        protected String doInBackground(String... args) {

            // getting updated data from EditTexts

            // Building Parameters

            HttpClient client = new DefaultHttpClient();
            HttpGet get;

            HttpResponse response;
            try {
                String getURLStr = Global.apilink + "update_position_without_token.php?position=" + position + "&user_id=" + s.userid();
                if (args.length > 0) {
                    getURLStr += "&family=" + URLEncoder.encode(args[0], "UTF-8");
                }
                Log.d("MyService.getURLStr", getURLStr);
                get = new HttpGet(getURLStr);

                response = client.execute(get);
                HttpEntity resEntity = response.getEntity();
                String result = EntityUtils.toString(resEntity);
                Log.d("result", "" + result);
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return null;
        }


        /**
         * After completing background task Dismiss the progress dialog
         * *
         */
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product uupdated

        }
    }

    private void getLocation() {
        getLocation(new LocationProvider().getLocation(this));
    }

    private void getLocation(Location location) {    //±N©w¶Ï∏Í∞T≈„•‹¶bµe≠±§§
        if (location != null) {

            Log.d("MyService.getLocation", "location=" + location);

            longitude = location.getLongitude();    //®˙±o∏g´◊
            latitude = location.getLatitude();    //®˙±oΩn´◊

            try {
                SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy", Locale.US);
                position = longitude + ":" + latitude + ":" + URLEncoder.encode(format.format(new Date(location.getTime())), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            Log.d("MyService.getLocation", "position=" + position);
            JSONArray familyJson = new JSONArray();
            Cursor cursor = getCursor();
            while (cursor.moveToNext()) {
                if (!Global.s.userid().equals("no_user")) {
                    if (cursor.getString(3).equals(Global.s.userid())) {
                        int id = cursor.getInt(0);

                        String name = cursor.getString(1);
                        String email = cursor.getString(2);

                        try {
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
            Log.d("MyService.familyJson", familyJson.toString());
            new Savelocation().execute(familyJson.toString());
        } else {
            //Toast.makeText(this, "µL™k©w¶ÏÆyº–", Toast.LENGTH_LONG).show();
        }

    }

    private Cursor getCursor() {

        MyDBHelper_callfmy dbhelper;
        dbhelper = new MyDBHelper_callfmy(Global.onAirAct);
        dbhelper.close();
        SQLiteDatabase db = dbhelper.getReadableDatabase();

        String[] columns = {android.provider.BaseColumns._ID, MyDBHelper_callfmy.name, MyDBHelper_callfmy.email, MyDBHelper_callfmy.userid};

        Cursor cursor = db.query(MyDBHelper_callfmy.TABLE_NAME, columns, null, null, null, null, null);

        Global.onAirAct.startManagingCursor(cursor);

        return cursor;

    }

    public void onLocationChanged(Location location) {
        //getLocation(location);
        Log.d("MyService", "onLocationChanged().location=" + location);
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

    public class LocalBinder extends Binder {
        public MyService getService() {
            return MyService.this;
        }
    }

    //	public IBinder onBind(Intent intent) {
//		return mBinder;
//	}
    private final IBinder mBinder = new LocalBinder();
}
