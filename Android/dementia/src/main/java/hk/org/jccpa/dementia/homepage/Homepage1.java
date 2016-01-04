package hk.org.jccpa.dementia.homepage;

import java.io.IOException;
import java.util.ArrayList;

import hk.org.jccpa.dementia.Global;
import hk.org.jccpa.dementia.Pages;
import hk.org.jccpa.dementia.R;

import hk.org.jccpa.dementia.classpackage.fmyinfo;
import hk.org.jccpa.dementia.sql.MyDBHelper_callfmy;
import hk.org.jccpa.dementia.sql.MyDBHelper_news;

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
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Homepage1 extends Fragment {
	View v;
    Context c;
	ImageLoader mImageLoader;
	ImageButton knowledge,mylocation,setting,socres,selftest,game,callmyfmy,oneTouchImage;
    LinearLayout oneTouch;
    TextView oneTouchText;
    fmyinfo oneTouchFmy;
	MyDBHelper_news newsdb;
    MyDBHelper_callfmy dbhelperContact;
	ImageView ads;
	DisplayImageOptions displayImageOptions;
	String webmessage ="";
	public void onCreate(Bundle savedInstanceState) {
		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);
		super.onCreate(savedInstanceState);
		displayImageOptions = new DisplayImageOptions.Builder() 
		.showStubImage(R.drawable.ic_launcher) 
		.showImageOnFail(R.drawable.ic_launcher) 

		.imageScaleType(ImageScaleType.IN_SAMPLE_INT) 
		.bitmapConfig(Bitmap.Config.RGB_565)
		.showImageForEmptyUri(R.drawable.ic_launcher).cacheInMemory(true) 
		.cacheOnDisc(true).displayer(new FadeInBitmapDisplayer(300)) 
		.imageScaleType(ImageScaleType.EXACTLY_STRETCHED).build();
	}
	public static void initImageLoader(Context context) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration
				.Builder(context)
		.threadPriority(Thread.NORM_PRIORITY - 2)
		.denyCacheImageMultipleSizesInMemory()
		.discCacheFileNameGenerator(new Md5FileNameGenerator())
		.tasksProcessingOrder(QueueProcessingType.LIFO)
		.build();
		ImageLoader.getInstance().init(config);
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);
		//		Global.s.setmode(false);
		Global.whichpage=Pages.Homepage1;
        c = getActivity();
		mImageLoader=ImageLoader.getInstance();
		initImageLoader(getActivity());
		if(!Global.s.whichmode()){
			v = inflater.inflate(R.layout.activity_homepage1, container, false);
			View holepage=v.findViewById(R.id.holepage);
			holepage.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

				}
			});
			knowledge=(ImageButton) v.findViewById(R.id.knowledge);
			setting=(ImageButton) v.findViewById(R.id.setting);
			mylocation=(ImageButton) v.findViewById(R.id.mylocation);
			socres=(ImageButton) v.findViewById(R.id.socres);
			selftest=(ImageButton) v.findViewById(R.id.selftest);
			game=(ImageButton) v.findViewById(R.id.imageButton1);
			knowledge.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Global.mapdemosteps=0;
					Global.onAirAct.pushFragment(Pages.Kowledgecenter);	
				}
			});
			mylocation.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//					Global.onAirAct.pushFragment(Pages.Map);
					Global.mapdemosteps=0;
					if(!Global.s.userid().equals("no_user")){
						if(Global.onAirAct.isNetworkOnline()){
							if(Global.s.isfirstmap()){
								Global.onAirAct.pushFragment(Pages.Mapdemo1);
							}else{
								Global.onAirAct.pushFragment(Pages.Map);
							}
						}else{
							Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.nogps), Toast.LENGTH_LONG).show();	
						}
					}else{
						ShowMsgDialog();
					}
				}
			});
			setting.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Global.onAirAct.pushFragment(Pages.Setting);	
				}
			});
			socres.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Global.onAirAct.pushFragment(Pages.Socres);	
				}
			});
			selftest.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Global.selftestqumber=1;

					Global.onAirAct.pushFragment(Pages.Selftest);	
				}
			});
			game.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Global.onAirAct.pushFragment(Pages.Gamemenu);	
				}
			});
		}else{
			v = inflater.inflate(R.layout.activity_homepage2, container, false);	
			game=(ImageButton) v.findViewById(R.id.imageButton1);
			mylocation=(ImageButton) v.findViewById(R.id.mylocation);
			callmyfmy=(ImageButton) v.findViewById(R.id.callmyfmy);
            oneTouch=(LinearLayout) v.findViewById(R.id.oneTouch);
            oneTouchImage=(ImageButton) v.findViewById(R.id.oneTouchImage);
            oneTouchText=(TextView) v.findViewById(R.id.oneTouchText);




			game.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Global.onAirAct.pushFragment(Pages.Gamemenu);	
				}
			});
			mylocation.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Global.mapdemosteps=0;
					if(!Global.s.userid().equals("no_user")){
						if(Global.onAirAct.isNetworkOnline()){
							if(Global.s.isfirstmap()){
								Global.onAirAct.pushFragment(Pages.Mapdemo1);
							}else{
								Global.onAirAct.pushFragment(Pages.Map);
							}
						}else{
							Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.nogps), Toast.LENGTH_LONG).show();	
						}
					}else{
						ShowMsgDialog();
					}	
				}
			});

			callmyfmy.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Global.onAirAct.pushFragment(Pages.Callfmy);	
				}
			});
            dbhelperContact = new MyDBHelper_callfmy(getActivity());
            dbhelperContact.close();

            oneTouchFmy = getFmyinfo();

            if(oneTouchFmy == null) {
                oneTouch.setVisibility(View.GONE);
            }else {
                //oneTouch.setImageURI(filepath);
                String [] s = oneTouchFmy.filepath.split(":");
                try{
                    if(s[1].equals("2")){
                        Log.d("list.get(position).filepath","list.get(position).filepath:  "+ "file://"+s[0]);
                    }else{
                        mImageLoader.displayImage("file:///"+s[0], oneTouchImage,displayImageOptions);
                        oneTouchText.setText(oneTouchFmy.name);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
                oneTouchImage.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        //Global.onAirAct.pushFragment(Pages.Callfmy);

                        Global.mapdemosteps=0;
                        if(!Global.s.userid().equals("no_user")){
                            if(Global.onAirAct.isNetworkOnline()){
                                if(Global.s.isfirstmap()){
                                    Global.onAirAct.pushFragment(Pages.Mapdemo1);
                                }else{
                                    Global.onAirAct.pushFragment(Pages.Map);
                                }
                            }else{
                                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.nogps), Toast.LENGTH_LONG).show();
                            }
                        }else{
                            ShowMsgDialog();
                        }
                        if (oneTouchFmy != null) {
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:" + oneTouchFmy.phone));
                            c.startActivity(callIntent);
                        }
                    }
                });
            }

		}
		readdb();
		Log.d("webmessage", ""+webmessage);
		if(Global.isfirsttimelogin && !webmessage.equals("")){

			Global.isfirsttimelogin=false;
			messageDialog();
		}
		ads=(ImageView) v.findViewById(R.id.ads);
//		new loadads().execute();
		return v;
    }
    private fmyinfo getFmyinfo(){
        Cursor cursor = getCursorContact();
        StringBuilder resultData = new StringBuilder("RESULT: \n");
        fmyinfo temp = null;
        while(cursor.moveToNext()){
//			Log.d("Global.s.userid()",""+Global.s.userid() );
            if(!Global.s.userid().equals("no_user")){
                if(cursor.getString(7).equals(Global.s.userid())){
                    int id = cursor.getInt(0);

                    String name = cursor.getString(1);

                    String relationship = cursor.getString(2);

                    String remarks = cursor.getString(3);

                    String filepath = cursor.getString(4);

                    String phone = cursor.getString(5);

                    String email = cursor.getString(6);



                    temp=new fmyinfo( String.valueOf(id),name,
                            phone,
                            email,
                            relationship,
                            remarks,
                            filepath);
                    resultData.append(id).append(": ");

                    resultData.append(name).append(", ");

                    resultData.append(relationship).append(", ");

                    resultData.append(remarks).append(", ");

                    resultData.append(filepath).append(", ");

                    resultData.append(phone).append(", ");

                    resultData.append(email).append(", ");

                    resultData.append("\n");
                    Log.d("result", "result:"+ resultData.toString());
                    return temp;
                }
            }
            else {
                if(cursor.getString(7).equals("no_user")){
                    int id = cursor.getInt(0);

                    String name = cursor.getString(1);

                    String relationship = cursor.getString(2);

                    String remarks = cursor.getString(3);

                    String filepath = cursor.getString(4);

                    String phone = cursor.getString(5);

                    String email = cursor.getString(6);



                    temp=new fmyinfo( String.valueOf(id),name,
                            phone,
                            email,
                            relationship,
                            remarks,
                            filepath);
                    resultData.append(id).append(": ");

                    resultData.append(name).append(", ");

                    resultData.append(relationship).append(", ");

                    resultData.append(remarks).append(", ");

                    resultData.append(filepath).append(", ");

                    resultData.append(phone).append(", ");

                    resultData.append(email).append(", ");

                    resultData.append("\n");
                    Log.d("result", "result:"+ resultData.toString());
                    return temp;
                }
            }
        }
        return temp;
    }
    private Cursor getCursorContact(){

        SQLiteDatabase db = dbhelperContact.getReadableDatabase();

        String[] columns = { android.provider.BaseColumns._ID, MyDBHelper_callfmy.name, MyDBHelper_callfmy.relationship, MyDBHelper_callfmy.remarks
                ,MyDBHelper_callfmy.filepath,MyDBHelper_callfmy.phone,MyDBHelper_callfmy.email,MyDBHelper_callfmy.userid};

        Cursor cursor = db.query(MyDBHelper_callfmy.TABLE_NAME, columns, null, null, null, null, null);

        getActivity().startManagingCursor(cursor);

        return cursor;

    }

    private void ShowMsgDialog()
	{
		Builder MyAlertDialog = new AlertDialog.Builder(getActivity());
		MyAlertDialog.setTitle(getActivity().getResources().getString(R.string.plzlogin));

		DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which) {
				Global.onAirAct.clearBackStack();
				Global.onAirAct.pushFragment(Pages.Reallogin);
			}
		};
		MyAlertDialog.setNeutralButton(getActivity().getResources().getString(R.string.login),OkClick );
		MyAlertDialog.setOnCancelListener(new OnCancelListener(){

			@Override
			public void onCancel(DialogInterface dialog) {
				// TODO Auto-generated method stub

			}

		});
		MyAlertDialog.show();
	}
	public void messageDialog() {

		final Dialog myDialog = new Dialog(getActivity(),android.R.style.Theme_Black_NoTitleBar_Fullscreen);
		myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		myDialog.setContentView(R.layout.popup2);

		myDialog.setCancelable(false);




		Log.d("WebString", webmessage);
		WebView web=(WebView) myDialog.findViewById(R.id.web);
		web.loadDataWithBaseURL("", webmessage, "text/html", "UTF-8", "");
		web.setBackgroundColor(0x00000000);
		ImageView close = (ImageView) myDialog.findViewById(R.id.close);
		close.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				myDialog.dismiss();


			} 
		}); 


		myDialog.show();

	} 
	private Cursor getCursor(){

		SQLiteDatabase db = newsdb.getReadableDatabase();

		//		String[] columns = { android.provider.BaseColumns._ID,MyDBHelper_game5.diff, MyDBHelper_game5.marks, MyDBHelper_game5.numofq};
		Log.d("","select * from "+MyDBHelper_news.TABLE_NAME+";");
		//		Cursor cursor = db.query(MyDBHelper_game5.TABLE_NAME, columns, null, null, null, null, MyDBHelper_game5.marks);
		Cursor cursor=db.rawQuery("select "+MyDBHelper_news.title_zh+" , "+MyDBHelper_news.title_gb+" , "+MyDBHelper_news.html_content_zh+" , "+MyDBHelper_news.html_content_gb+" from "+MyDBHelper_news.TABLE_NAME+";",null);

		//db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy)
		//		getActivity().startManagingCursor(cursor);

		return cursor;

	}
	public void readdb(){
		newsdb = new MyDBHelper_news(getActivity());
		Cursor cursor=getCursor();
		cursor.moveToFirst();
		webmessage ="";
		for(int i =0 ;i<cursor.getCount();i++){

			if(Global.s.whichlang()){
				webmessage+=cursor.getString(2);
			}else{
				webmessage+=cursor.getString(3);
			}
			webmessage+="<br />";
			cursor.moveToNext();
		}

		cursor.close();
		newsdb.close();
	}
	
	class loadads extends AsyncTask<String, Integer, Integer>{
		ProgressDialog pd;
		JSONObject j2=null;
		@Override
		protected Integer doInBackground(String... param) {
			try {
				HttpClient client2 = new DefaultHttpClient();
				HttpGet get2 = new HttpGet(Global.apilink+"get_adbanner.php?token="+Global.onAirAct.token);
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
					if(j2.getString("status").equals("Y")){
						JSONArray temp = j2.getJSONArray("media");
						String path=temp.getJSONObject(0).getString("file_media");
						mImageLoader.displayImage(Global.link+"media/"+path, ads, displayImageOptions);
					}
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
			//			pd = ProgressDialog.show(MainActivity.this, "Little Tree", "Loading...");  
		}

	}
	
	
}
