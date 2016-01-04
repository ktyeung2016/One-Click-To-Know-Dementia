package hk.org.jccpa.dementia.map;

import hk.org.jccpa.dementia.Global;
import hk.org.jccpa.dementia.Pages;
import hk.org.jccpa.dementia.R;
import hk.org.jccpa.dementia.sql.MyDBHelper_callfmy;

import com.example.basefamework.fontsize.Preferences;


import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class Mapdemo3 extends Fragment {
	View v;
	ImageButton back,menu;
	ImageView pic;
	Button takephoto,chphoto,finish,cancel;
	MyDBHelper_callfmy dbhelper;
	int type=0;
	String fname=null;
	Handler h;
	String filepath=null;
	EditText name,phone,email,relationship,remarks;
	Button tip1,tip2,tip3,tip4,tip5;

	//demo
	Handler h2;
	RelativeLayout demolayout;
	LinearLayout buttonlayout,wholelayout,topbarlayout;
	Toast toast;
	View layout, overlay;
	TextView text,stagenumber;
	Runnable r;
	ImageView nextstep,laststep, overlayImage;
	int laststate;
	//	Toast toast;
	//demo
	LinearLayout tiplayout1,tiplayout2,tiplayout3,tiplayout4,tiplayout5,tiplayout6;
	//	RelativeLayout demolayout;
	public void onCreate(Bundle savedInstanceState) {

		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);
		super.onCreate(savedInstanceState);
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);
		v = inflater.inflate(R.layout.mapdemo3, container, false);
		back=(ImageButton) v.findViewById(R.id.back);
		menu=(ImageButton) v.findViewById(R.id.menu);
		takephoto=(Button) v.findViewById(R.id.takephoto);
		pic=(ImageView) v.findViewById(R.id.pic);
		chphoto=(Button) v.findViewById(R.id.chphoto);
		name=(EditText) v.findViewById(R.id.name);
		phone=(EditText) v.findViewById(R.id.phone);
		email=(EditText) v.findViewById(R.id.email);
		relationship=(EditText) v.findViewById(R.id.relationship);
		remarks=(EditText) v.findViewById(R.id.remarks);
		cancel=(Button) v.findViewById(R.id.delete);
		finish=(Button) v.findViewById(R.id.finish);
		h=new Handler();
        overlay=v.findViewById(R.id.overlay);
        overlay.setVisibility(View.VISIBLE);
        overlayImage = (ImageView) v.findViewById(R.id.overlayImage);
        View holepage=v.findViewById(R.id.holepage);
		holepage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				nextstep.performClick();
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
		takephoto.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//				open();	
			}
		});
		chphoto.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//				Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
				//				photoPickerIntent.setType("image/*");
				//				startActivityForResult(photoPickerIntent, 1);  
			}
		});
		cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//				show();
				//				SQLiteDatabase db;
				//				db=dbhelper.getWritableDatabase();
				//				db.delete(MyDBHelper.TABLE_NAME, null, null);
				//				Global.onAirAct.clearOneBackStack();
			}
		});
		//		finish.setOnClickListener(new View.OnClickListener() {
		//
		//			@Override
		//			public void onClick(View v) {
		//				// TODO Auto-generated method stub
		//				if(  name.getText().toString().length()>0){
		//					if(  phone.getText().toString().length()>0){
		//						if(  email.getText().toString().length()>0){
		//							if(  relationship.getText().toString().length()>0){
		//								if( remarks.getText().toString().length()>0){
		//									if( filepath!=null){
		//										Log.d("click", "onclick");
		//										addtodb();
		//
		//									}else{
		//										Toast.makeText(getActivity(), getResources().getString(R.string.plzphoto), Toast.LENGTH_LONG).show();
		//									}
		//								}else{
		//									Toast.makeText(getActivity(), getResources().getString(R.string.plzremarks), Toast.LENGTH_LONG).show();
		//								}
		//							}else{
		//								Toast.makeText(getActivity(), getResources().getString(R.string.plzrel), Toast.LENGTH_LONG).show();
		//							}
		//						}else{
		//							Toast.makeText(getActivity(), getResources().getString(R.string.plzemail), Toast.LENGTH_LONG).show();
		//						}
		//					}else{
		//						Toast.makeText(getActivity(), getResources().getString(R.string.plzphone), Toast.LENGTH_LONG).show();
		//					}
		//				}else{
		//					Toast.makeText(getActivity(), getResources().getString(R.string.plzname), Toast.LENGTH_LONG).show();
		//				}
		//
		//
		//
		//
		//			}
		//		});
		//		dbhelper = new MyDBHelper_callfmy(getActivity());
		//		dbhelper.close();


		tiplayout1=(LinearLayout) v.findViewById(R.id.tiplayout1);
		tiplayout2=(LinearLayout) v.findViewById(R.id.tiplayout2);
		tiplayout3=(LinearLayout) v.findViewById(R.id.tiplayout3);
		tiplayout4=(LinearLayout) v.findViewById(R.id.tiplayout4);
		tiplayout5=(LinearLayout) v.findViewById(R.id.tiplayout5);
		tiplayout6=(LinearLayout) v.findViewById(R.id.tiplayout6);
		demolayout=(RelativeLayout) v.findViewById(R.id.demolayout);
		//demo
		laststep=(ImageView) v.findViewById(R.id.laststep);
		laststep.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(toast!=null){
					toast.cancel();
				}
				Global.mapdemosteps--;
				//				laststate=Global.mapdemosteps;
				stagenumber.setText(String.valueOf(Global.mapdemosteps+1)+"/"+String.valueOf(Global.onAirAct.mapdemo.length));
				if(Global.mapdemosteps<1){

				}
				if(Global.mapdemosteps<2){
					name.setText("");
				}

				if(Global.mapdemosteps<3){
					relationship.setText("");
				}
				if(Global.mapdemosteps<4){
					phone.setText("");
				}
				if(Global.mapdemosteps<5){
					email.setText("");
				}
				if(Global.mapdemosteps<6){
					remarks.setText("");
				}

				if(Global.mapdemosteps<2){
					Global.onAirAct.clearBackStack();
					Global.onAirAct.pushFragment(Pages.Mapdemo2);
				}
                drawOverlay(v);
			}
		});
		LayoutInflater inflater2 = getActivity().getLayoutInflater();
		layout = inflater2.inflate(R.layout.custoast,
				(ViewGroup) getActivity().findViewById(R.id.toast_layout_root));
		text = (TextView) layout.findViewById(R.id.text);
		//		text.setText(s[Stage]);
		name.setText("陳大文");

		if(Global.mapdemosteps==8){

			email.setText("ChanTaiMan@email.com");

			


			phone.setText("12345678");

			relationship.setText("兒子");

			name.setText("陳大文");

		}
		nextstep=(ImageView) v.findViewById(R.id.nextstep);
		nextstep.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(toast!=null){
					toast.cancel();
				}
				Global.mapdemosteps++;
				//				laststate=Global.mapdemosteps;
				stagenumber.setText(String.valueOf(Global.mapdemosteps+1)+"/"+String.valueOf(Global.onAirAct.mapdemo.length));




				if(Global.mapdemosteps==5){
					email.setText("ChanTaiMan@email.com");
				}
				if(Global.mapdemosteps==6){
					
				}
				if(Global.mapdemosteps==7){

				}
				if(Global.mapdemosteps==8){

				}

				if(Global.mapdemosteps==4){
					phone.setText("12345678");
				}
				if(Global.mapdemosteps==3){
					relationship.setText("兒子");
				}
				if(Global.mapdemosteps==2){
					name.setText("陳大文");
				}
				if(Global.mapdemosteps>8){
					Global.onAirAct.clearBackStack();
					Global.onAirAct.pushFragment(Pages.Mapdemo4);
				}
                drawOverlay(v);
			}
		});
		stagenumber=(TextView) v.findViewById(R.id.stagenumber);
		stagenumber.setText(String.valueOf(Global.mapdemosteps+1)+"/"+String.valueOf(Global.onAirAct.mapdemo.length));
		h2=new Handler();
		r=new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				//				laststate=Global.mapdemosteps;&& laststate!=Global.mapdemosteps
				if(toast!=null ){
					toast.cancel();

				}

				if(Global.mapdemosteps==2){
					laststate=Global.mapdemosteps;
					toast = new Toast(getActivity().getApplicationContext());
					text.setText(Global.onAirAct.mapdemo[Global.mapdemosteps]);
					toast.setDuration(Toast.LENGTH_LONG);

					toast.setGravity(Gravity.BOTTOM, 0, 0);
					toast.setView(layout);
				}
				if(Global.mapdemosteps==3){
					toast = new Toast(getActivity().getApplicationContext());
					toast.setDuration(Toast.LENGTH_LONG);

					text.setText(Global.onAirAct.mapdemo[Global.mapdemosteps]);
					toast.setGravity(Gravity.BOTTOM, 0, 0);toast.setView(layout);
				}

				if(Global.mapdemosteps==4){
					toast = new Toast(getActivity().getApplicationContext());
					toast.setDuration(Toast.LENGTH_LONG);

					text.setText(Global.onAirAct.mapdemo[Global.mapdemosteps]);
					toast.setGravity(Gravity.BOTTOM, 0, 0);toast.setView(layout);
				}
				if(Global.mapdemosteps==5){
					toast = new Toast(getActivity().getApplicationContext());
					toast.setDuration(Toast.LENGTH_LONG);

					text.setText(Global.onAirAct.mapdemo[Global.mapdemosteps]);
					toast.setGravity(Gravity.BOTTOM, 0, 0);toast.setView(layout);
				}
				if(Global.mapdemosteps==6){
					toast = new Toast(getActivity().getApplicationContext());

					toast.setDuration(Toast.LENGTH_LONG);
					text.setText(Global.onAirAct.mapdemo[Global.mapdemosteps]);
					toast.setGravity(Gravity.BOTTOM, 0, 0);toast.setView(layout);
				}
				if(Global.mapdemosteps==7){
					toast = new Toast(getActivity().getApplicationContext());

					toast.setDuration(Toast.LENGTH_LONG);
					text.setText(Global.onAirAct.mapdemo[Global.mapdemosteps]);
					toast.setGravity(Gravity.BOTTOM, 0, 0);
					toast.setView(layout);
				}
				if(Global.mapdemosteps==8){
					toast = new Toast(getActivity().getApplicationContext());
					toast.setDuration(Toast.LENGTH_LONG);

					text.setText(Global.onAirAct.mapdemo[Global.mapdemosteps]);
					toast.setGravity(Gravity.CENTER, 0, 0);toast.setView(layout);
				}
				toast.show();
				h.postDelayed(r, 100);
			}


		};

		//demo
		try { 
		    Thread.sleep(200);                 //1000 milliseconds is one second.
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		} 
		return v;
	}
    public void drawOverlay(View view){
        view.postDelayed(new Runnable() {
            public void run() {
                Rect r = new Rect();
                if (null != overlay && null != overlayImage &&  Global.mapdemosteps >= 2 && Global.mapdemosteps <= 8) {
                    Bitmap result = Global.maskOverlay.getOverlayBitmap(overlay.getWidth(), overlay.getHeight());
                    if(null == result){
                        return;
                    }
                    overlayImage.setImageBitmap(result);
                    overlayImage.setScaleType(ImageView.ScaleType.FIT_XY);

                    switch (Global.mapdemosteps){
                        case 2:{
                            View text = v.findViewById(R.id.name);
                            if(null != text && text.getGlobalVisibleRect(r)){
                                Global.maskOverlay.drawMask(result, r);
                            }
                            break;
                        }
                        case 3:{
                            View text = v.findViewById(R.id.relationship);
                            if(null != text && text.getGlobalVisibleRect(r)){
                                Global.maskOverlay.drawMask(result, r);
                            }
                            break;
                        }
                        case 4:{
                            View text = v.findViewById(R.id.phone);
                            if(null != text && text.getGlobalVisibleRect(r)){
                                Global.maskOverlay.drawMask(result, r);
                            }
                            break;
                        }
                        case 5:{
                            View text = v.findViewById(R.id.email);
                            if(null != text && text.getGlobalVisibleRect(r)){
                                Global.maskOverlay.drawMask(result, r);
                            }
                            break;
                        }
                        case 6:{
                            View text = v.findViewById(R.id.remarks);
                            if(null != text && text.getGlobalVisibleRect(r)){
                                Global.maskOverlay.drawMask(result, r);
                            }
                            break;
                        }
                        case 7:{
                            View text = v.findViewById(R.id.photoInput);
                            if(null != text && text.getGlobalVisibleRect(r)){
                                Global.maskOverlay.drawMask(result, r);
                            }
                            break;
                        }
                        case 8:{
                            View text = v.findViewById(R.id.finish);
                            if(null != text && text.getGlobalVisibleRect(r)){
                                Global.maskOverlay.drawMask(result, r);
                            }
                            break;
                        }
                        default:{

                        }
                    }
                }
            }
        }, 1);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.postDelayed(new Runnable() {
            public void run() {
                drawOverlay(v);
            }
        }, 1);
    }
	public void onPause(){
		super.onPause();
		h.removeCallbacks(r);
		toast.cancel();
	}
	public void onDestroy(){
		toast.cancel();
		h.removeCallbacks(r);
		super.onDestroy();
	}
	public void onResume(){
		super.onResume();
		h2.postDelayed(r, 100);
		Display display = getActivity().getWindowManager().getDefaultDisplay();
		final Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;
		tip1 = new Button(getActivity());

		//		LayoutInflater inflater = getActivity().getLayoutInflater();
		//		View rowView = inflater.inflate(R.layout.demobar, null);
		//		demolayout.addView(rowView);
		tip1.setBackgroundResource(R.drawable.messagedialogdown);
		//		tip1.setGravity(Gravity.CENTER);
		tip1.setPaddingRelative(0, 0, 0, 3);
		tip1.setText(getResources().getString(R.string.mapfunctionstep1));
		final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int)(size.x/2), 120);
		//		params.topMargin=50;
		//		params.leftMargin=50;
		//							params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		//							final Handler h1=new Handler();

		params.leftMargin = (int)(size.x/3);
		params.topMargin =(int) (size.y/22*(2+(20*2/11)));// (newfmymem.getHeight());

		Log.d("Margin", "Margin"+"     "+params.leftMargin+"    "+params.topMargin+"   "+tiplayout2.getX()+"/"+tiplayout2.getY());


		demolayout.addView(tip1, params);

		h.postDelayed(new Runnable(){
			public void run() { 
				//tipslayout


				tiplayout2.setBackground(null);
				//				name.setOnEditorActionListener(new OnEditorActionListener(){
				//
				//					@Override
				//					public boolean onEditorAction(TextView v, int actionId,
				//							KeyEvent event) {
				//						// TODO Auto-generated method stub
				//
				//						if(actionId == EditorInfo.IME_ACTION_NEXT){
				//							Log.d("hi", "hi");
				//							tip1.setVisibility(View.GONE);
				//							tip2 = new Button(getActivity());
				//
				//							//		LayoutInflater inflater = getActivity().getLayoutInflater();
				//							//		View rowView = inflater.inflate(R.layout.demobar, null);
				//							//		demolayout.addView(rowView);
				//							tip2.setBackgroundResource(R.drawable.messagedialogdown);
				////							tip1.setGravity(Gravity.CENTER);
				//							tip2.setPaddingRelative(0, 0, 0, 3);
				//							tip2.setText(getResources().getString(R.string.mapfunctionstep1));
				//							final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int)(size.x/2), 120);
				//							//		params.topMargin=50;
				//							//		params.leftMargin=50;
				//							//							params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
				//							//							final Handler h1=new Handler();
				//
				//							params.leftMargin = (int)(size.x/3);
				//							params.topMargin =(int) (size.y/22*(2+(20*2.5/11)));// (newfmymem.getHeight());
				//
				//							Log.d("Margin", "Margin"+"     "+params.leftMargin+"    "+params.topMargin+"   "+tiplayout2.getX()+"/"+tiplayout2.getY());
				//
				//
				//							demolayout.addView(tip2, params);
				//							tiplayout2.setBackgroundResource(R.drawable.transparentbg);
				//							tiplayout3.setBackground(null);
				//
				//
				//
				//						}
				//						return false;
				//					}
				//
				//				});

				//				relationship.setOnEditorActionListener(new OnEditorActionListener(){
				//
				//					@Override
				//					public boolean onEditorAction(TextView v, int actionId,
				//							KeyEvent event) {
				//						// TODO Auto-generated method stub
				//
				//						if(actionId == EditorInfo.IME_ACTION_NEXT){
				//							Log.d("hi", "hi");
				//							
				//							
				//							
				//							tip2.setVisibility(View.GONE);
				//							tip3 = new Button(getActivity());
				//
				//							//		LayoutInflater inflater = getActivity().getLayoutInflater();
				//							//		View rowView = inflater.inflate(R.layout.demobar, null);
				//							//		demolayout.addView(rowView);
				//							tip3.setBackgroundResource(R.drawable.messagedialogdown);
				////							tip1.setGravity(Gravity.CENTER);
				//							tip3.setPaddingRelative(0, 0, 0, 3);
				//							tip3.setText(getResources().getString(R.string.mapfunctionstep1));
				//							final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int)(size.x/2), 120);
				//							//		params.topMargin=50;
				//							//		params.leftMargin=50;
				//							//							params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
				//							//							final Handler h1=new Handler();
				//
				//							params.leftMargin = (int)(size.x/3);
				//							params.topMargin =(int) (size.y/22*(2+(20*3.6/11)));// (newfmymem.getHeight());
				//
				//							Log.d("Margin", "Margin"+"     "+params.leftMargin+"    "+params.topMargin+"   "+tiplayout2.getX()+"/"+tiplayout2.getY());
				//
				//
				//							demolayout.addView(tip3, params);
				//							
				//							
				//							
				//							tiplayout3.setBackgroundResource(R.drawable.transparentbg);
				//							tiplayout4.setBackground(null);
				//						}
				//						return false;
				//					}
				//
				//				});
				//				phone.setOnEditorActionListener(new OnEditorActionListener(){
				//
				//					@Override
				//					public boolean onEditorAction(TextView v, int actionId,
				//							KeyEvent event) {
				//						// TODO Auto-generated method stub
				//
				//						if(actionId == EditorInfo.IME_ACTION_NEXT){
				//							Log.d("hi", "hi");
				//							
				//							tip3.setVisibility(View.GONE);
				//							tip4 = new Button(getActivity());
				//
				//							//		LayoutInflater inflater = getActivity().getLayoutInflater();
				//							//		View rowView = inflater.inflate(R.layout.demobar, null);
				//							//		demolayout.addView(rowView);
				//							tip4.setBackgroundResource(R.drawable.messagedialogdown);
				////							tip1.setGravity(Gravity.CENTER);
				//							tip4.setPaddingRelative(0, 0, 0, 3);
				//							tip4.setText(getResources().getString(R.string.mapfunctionstep1));
				//							final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int)(size.x/2), 120);
				//							//		params.topMargin=50;
				//							//		params.leftMargin=50;
				//							//							params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
				//							//							final Handler h1=new Handler();
				//
				//							params.leftMargin = (int)(size.x/3);
				//							params.topMargin =(int) (size.y/22*(2+(20*4.7/11)));// (newfmymem.getHeight());
				//
				//							Log.d("Margin", "Margin"+"     "+params.leftMargin+"    "+params.topMargin+"   "+tiplayout2.getX()+"/"+tiplayout2.getY());
				//
				//
				//							demolayout.addView(tip4, params);
				//							
				//							
				//							tiplayout4.setBackgroundResource(R.drawable.transparentbg);
				//							tiplayout5.setBackground(null);
				//						}
				//						return false;
				//					}
				//
				//				});
				//				email.setOnEditorActionListener(new OnEditorActionListener(){
				//
				//					@Override
				//					public boolean onEditorAction(TextView v, int actionId,
				//							KeyEvent event) {
				//						// TODO Auto-generated method stub
				//
				//						if(actionId == EditorInfo.IME_ACTION_NEXT){
				//							Log.d("hi", "hi");
				//							
				//							
				//							
				//							tip4.setVisibility(View.GONE);
				//							tip5 = new Button(getActivity());
				//
				//							//		LayoutInflater inflater = getActivity().getLayoutInflater();
				//							//		View rowView = inflater.inflate(R.layout.demobar, null);
				//							//		demolayout.addView(rowView);
				//							tip5.setBackgroundResource(R.drawable.messagedialogdown);
				////							tip1.setGravity(Gravity.CENTER);
				//							tip5.setPaddingRelative(0, 0, 0, 3);
				//							tip5.setText(getResources().getString(R.string.mapfunctionstep1));
				//							final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int)(size.x/2), 120);
				//							//		params.topMargin=50;
				//							//		params.leftMargin=50;
				//							//							params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
				//							//							final Handler h1=new Handler();
				//
				//							params.leftMargin = (int)(size.x/3);
				//							params.topMargin =(int) (size.y/22*(2+(20*6.1/11)));// (newfmymem.getHeight());
				//
				//							Log.d("Margin", "Margin"+"     "+params.leftMargin+"    "+params.topMargin+"   "+tiplayout2.getX()+"/"+tiplayout2.getY());
				//
				//
				//							demolayout.addView(tip5, params);
				//							
				//							
				//							
				//							tiplayout5.setBackgroundResource(R.drawable.transparentbg);
				//							tiplayout6.setBackground(null);
				//						}
				//						return false;
				//					}
				//
				//				});
				remarks.setOnEditorActionListener(new OnEditorActionListener(){

					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						// TODO Auto-generated method stub


						return false;
					}

				});
			}
		}, 500); 
		//tipslayout

	}


	//	private void addtodb(){
	//
	//		SQLiteDatabase db = dbhelper.getWritableDatabase();
	//
	//		ContentValues values = new ContentValues();
	//		//	       name,phone,email,relationship,remarks
	//		values.put(MyDBHelper_callfmy.name,  name.getText().toString());
	//
	//		values.put(MyDBHelper_callfmy.phone, phone.getText().toString());
	//
	//		values.put(MyDBHelper_callfmy.email, email.getText().toString());
	//
	//		values.put(MyDBHelper_callfmy.relationship, relationship.getText().toString());
	//
	//		values.put(MyDBHelper_callfmy.remarks, remarks.getText().toString());
	//
	//		values.put(MyDBHelper_callfmy.filepath, filepath+":"+type);
	//		values.put(MyDBHelper_callfmy.userid,Global.s.userid());
	//
	//		db.insert(MyDBHelper_callfmy.TABLE_NAME, null, values);
	//
	//		
	//
	//		Global.onAirAct.clearOneBackStack();
	//
	//	}
	//
	//	public void open(){
	//		//		Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
	//		//		startActivityForResult(intent, 0);
	//
	//		fname=String.valueOf(System.currentTimeMillis());
	//		File file = new File(Global.path+fname+".jpg");
	//		Uri output = Uri.fromFile(file);
	//		Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
	//		cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, output);
	//		startActivityForResult(cameraIntent, 0);
	//	}
	//
	//	@Override
	//	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	//		// TODO Auto-generated method stub
	//		super.onActivityResult(requestCode, resultCode, data);
	//		if(requestCode==0 && resultCode == Activity.RESULT_OK){
	//
	//			final File imgFile = new  File(Global.path+fname+".jpg");
	//
	//			/* Write bitmap to file using JPEG and 80% quality hint for JPEG. */ 
	//
	//			if(imgFile.exists()){
	//
	//				new Thread(new Runnable() {
	//
	//					public void run() {
	//
	//
	//
	//						// TODO Auto-generated method stub
	//
	//
	//						//					myBitmap = Staticmethod.getImage(BitmapFactory.decodeFile(imgFile.getAbsolutePath()));
	//						getActivity().runOnUiThread(new Runnable(){
	//
	//							@Override
	//							public void run() {
	//								// TODO Auto-generated method stub
	//								//							resize(imgFile.getAbsolutePath());
	//								filepath=imgFile.getAbsolutePath();
	//								Log.d("uri", "uri: "+imgFile.getAbsolutePath());
	//								type=1;
	//								pic.setImageBitmap(resize(imgFile.getAbsolutePath(),0,null));
	//
	//							}
	//
	//						});
	//					}
	//				}).start();
	//
	//
	//			} 
	//		}else if(requestCode==1) {
	//			if(resultCode == Activity.RESULT_OK){  
	//				Uri selectedImage = data.getData();
	//				//				File myFile = new File(selectedImage.toString());
	//				//
	//				//
	//				//				Log.d("uri", "uri: "+myFile.getAbsolutePath());
	//				//				filepath=selectedImage.getPath();
	//				Bitmap bmp=resize(null,1,selectedImage);
	//				fname=String.valueOf(System.currentTimeMillis());
	//				filepath=Global.path+fname+".jpg";
	//				//				File file = new File(Global.path+fname+".jpg");
	//				FileOutputStream out = null;
	//				try { 
	//					out = new FileOutputStream(Global.path+fname+".jpg");
	//					bmp.compress(Bitmap.CompressFormat.JPEG, 90, out);
	//				} catch (Exception e) {
	//					e.printStackTrace();
	//				} finally { 
	//					try { 
	//						if (out != null) {
	//							out.close();
	//						} 
	//					} catch (IOException e) {
	//						e.printStackTrace();
	//					} 
	//				}
	//				type=1;
	//				//				pic.setImageBitmap(resize(getRealPathFromURI(getActivity(),selectedImage),1,null));
	//				pic.setImageBitmap(bmp);
	//				//	            InputStream imageStream;
	//				//				try {
	//				//					imageStream = getActivity().getContentResolver().openInputStream(selectedImage);
	//				//					Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
	//				//				} catch (FileNotFoundException e) {
	//				//					// TODO Auto-generated catch block
	//				//					e.printStackTrace();
	//				//				}
	//
	//			} 
	//		}
	//	}
	//	public String getRealPathFromURI(Context context, Uri contentUri) {
	//		Cursor cursor = null;
	//		try {  
	//			String[] proj = { MediaStore.Images.Media.DATA };
	//			cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
	//			int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	//			cursor.moveToFirst();
	//			Log.d("path123",cursor.getString(column_index));
	//			return cursor.getString(column_index);
	//		} finally { 
	//			if (cursor != null) {
	//				cursor.close();
	//			} 
	//		} 
	//	}
	//	Bitmap resize(String filename,int isuri,Uri uri){
	//		if(isuri==0){
	//			Matrix matrix = new Matrix();
	//			try {
	//				ExifInterface exif = new ExifInterface(filename);
	//				int rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
	//				int rotationInDegrees = exifToDegrees(rotation);
	//
	//				if (rotation != 0f) {matrix.preRotate(rotationInDegrees);}
	//			} catch (IOException e) {
	//				// TODO Auto-generated catch block
	//				e.printStackTrace();
	//			}
	//			BitmapFactory.Options option = new BitmapFactory.Options();
	//
	//			option.inJustDecodeBounds = true;
	//			File f=new File(filename);
	//			//		BitmapFactory.decodeResource(getResources(), R.drawable.tcl_bg, option);
	//			BitmapFactory.decodeFile(filename,option);
	//
	//
	//			//The new size to decode to 
	//
	//			final int NEW_SIZE=100;
	//
	//
	//
	//			//Now we have image width and height. We should find the correct scale value. (power of 2)
	//
	//			int width=option.outWidth;
	//
	//			int height=option.outHeight;
	//
	//			int scale=1;
	//
	//			while(true){
	//
	//				if(width/2<pic.getWidth() || height/2<pic.getHeight())
	//
	//					break;
	//
	//				width/=2;
	//
	//				height/=2;
	//
	//				scale++;
	//
	//			}
	//
	//			//Decode again with inSampleSize
	//
	//			option = new BitmapFactory.Options();
	//
	//			option.inSampleSize=scale;
	//			Bitmap b= BitmapFactory.decodeFile(filename,option);
	//			Bitmap dstBmp;
	//			if (b.getWidth() >= b.getHeight()){ 
	//
	//				dstBmp = Bitmap.createBitmap(
	//						b,  
	//						b.getWidth()/2 - b.getHeight()/2, 
	//						0, 
	//						b.getHeight(),  
	//						b.getHeight() ,matrix, true
	//						); 
	//
	//			}else{ 
	//
	//				dstBmp = Bitmap.createBitmap(
	//						b, 
	//						0,  
	//						b.getHeight()/2 - b.getWidth()/2, 
	//						b.getWidth(), 
	//						b.getWidth()  , matrix, true
	//						); 
	//			} 
	//			//		Canvas canvas       = new Canvas(dstBmp);
	//
	//			//		return b;
	//
	//			OutputStream stream;
	//			try {
	//				stream = new FileOutputStream(Global.path+fname+".jpg");
	//				dstBmp.compress(CompressFormat.JPEG, 80, stream);
	//			} catch (FileNotFoundException e) {
	//				// TODO Auto-generated catch block
	//				e.printStackTrace();
	//			}
	//			return dstBmp;
	//		}else{
	//			// Decode image size 
	//
	//			try {
	//				BitmapFactory.Options o = new BitmapFactory.Options();
	//				o.inJustDecodeBounds = true;
	//				BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri), null, o);
	//
	//				// The new size we want to scale to 
	//				final int REQUIRED_SIZE = 140;
	//
	//				// Find the correct scale value. It should be the power of 2. 
	//				int width_tmp = o.outWidth, height_tmp = o.outHeight;
	//				int scale = 1;
	//				while (true) { 
	//					if (width_tmp / 2 < REQUIRED_SIZE
	//							|| height_tmp / 2 < REQUIRED_SIZE) {
	//						break; 
	//					} 
	//					width_tmp /= 2;
	//					height_tmp /= 2;
	//					scale *= 2;
	//				} 
	//
	//				// Decode with inSampleSize 
	//				BitmapFactory.Options o2 = new BitmapFactory.Options();
	//				o2.inSampleSize = scale;
	//				return BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri), null, o2);
	//			} catch (FileNotFoundException e) {
	//				// TODO Auto-generated catch block
	//				e.printStackTrace();
	//			}
	//		}
	//		return null;
	//		//		getActivity().getWindow().setBackgroundDrawable(d);//(BitmapFactory.decodeResource(getResources(), R.drawable.tcl_bg, option));
	//	}
	//	private static int exifToDegrees(int exifOrientation) {        
	//		if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) { return 90; } 
	//		else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {  return 180; } 
	//		else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {  return 270; }            
	//		return 0;     
	//	}
	//	@Override
	//	public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
	//		// TODO Auto-generated method stub
	//		if (keyCode == KeyEvent.KEYCODE_ENTER) {
	//			// Do Code here 
	//			Log.d("hi", "hi");
	//		} 
	//		return false;
	//
	//	} 
}

