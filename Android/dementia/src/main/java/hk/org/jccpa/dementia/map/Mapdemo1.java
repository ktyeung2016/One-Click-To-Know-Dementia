package hk.org.jccpa.dementia.map;

import hk.org.jccpa.dementia.Global;
import hk.org.jccpa.dementia.Pages;
import hk.org.jccpa.dementia.R;


import com.example.basefamework.fontsize.Preferences;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;


import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Mapdemo1 extends Fragment {
	View v;
	ImageButton back,menu;
	Button resetlocation,callfmy;
	TextView title;
	//demo
	RelativeLayout demolayout,wholepage;
	LinearLayout buttonlayout,wholelayout,topbarlayout;
	Toast toast;
	View layout, overlay;
	TextView 	text,stagenumber;
	Runnable r;
	ImageView nextstep, overlayImage;
	//	Toast toast;
	//demo
	private GoogleMap map;
	private MapView mapView;
	Handler h,h2;
	public void onCreate(Bundle savedInstanceState) {

		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);
		super.onCreate(savedInstanceState);
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);
		v = inflater.inflate(R.layout.mapdemo1, container, false);

		//demo
		LayoutInflater inflater2 = getActivity().getLayoutInflater();
		layout = inflater2.inflate(R.layout.custoast,
				(ViewGroup) getActivity().findViewById(R.id.toast_layout_root));
		
		
		
		
		text = (TextView) layout.findViewById(R.id.text);
		//		text.setText(s[Stage]);

		toast = new Toast(getActivity().getApplicationContext());
		text.setText(Global.onAirAct.mapdemo[Global.mapdemosteps]);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setView(layout);
		toast.show();
		nextstep=(ImageView) v.findViewById(R.id.nextstep);
		nextstep.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Global.mapdemosteps++;
				Global.onAirAct.clearBackStack();
				Global.onAirAct.pushFragment(Pages.Mapdemo2);
			}
		});
		stagenumber=(TextView) v.findViewById(R.id.stagenumber);
		stagenumber.setText(String.valueOf(Global.mapdemosteps+1)+"/"+String.valueOf(Global.onAirAct.mapdemo.length));
		//demo

		//map
		mapView = (MapView) v.findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);
		mapView.setClickable(false);
		mapView.setEnabled(false);
		
		mapView.onResume();// needed to get the map to display immediately
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
		MapsInitializer.initialize(getActivity());

		map = mapView.getMap();

		map.setOnMapClickListener(new OnMapClickListener(){

			@Override
			public void onMapClick(LatLng arg0) {
				// TODO Auto-generated method stub
				Global.mapdemosteps++;
				Global.onAirAct.clearBackStack();
				Global.onAirAct.pushFragment(Pages.Mapdemo2);
			}
			
		});
		
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
				//				if(map.getMyLocation()!=null){
				//					LatLng latLng = new LatLng(map.getMyLocation().getLatitude(), map.getMyLocation().getLongitude());
				//					//				map.setLocationSource(source)
				//					CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 18);
				//					//				Marker nkut=map.addMarker(new MarkerOptions().position(latLng).title("You are here!!!"));
				//					map.animateCamera(cameraUpdate);
				//				}else{
				//
				//				}
			}
		});
		callfmy.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//				Global.onAirAct.pushFragment(Pages.Callfmy);
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








		//		callfmy.getY();
		//		
		//		
		//		tip.setX(callfmy.getX());
		//		tip.setY(callfmy.getX()+tip.getWidth()/2);
		//		demolayout.addView(tip);





		//demo
		h2=new Handler();
		r=new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub

				toast.show();

				h.postDelayed(r, 100);
			}


		};

		return v;
	}
	public void onResume(){
		super.onResume();
		h2.postDelayed(r, 100);
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

		//		
	}
    public void drawOverlay(View view){
        view.postDelayed(new Runnable() {
            public void run() {
                Rect r = new Rect();
                if (null != overlay && null != overlayImage &&  Global.mapdemosteps == 0) {
                    Bitmap result = Global.maskOverlay.getOverlayBitmap(overlay.getWidth(), overlay.getHeight());
                    if(null == result){
                        return;
                    }
                    overlayImage.setImageBitmap(result);
                    overlayImage.setScaleType(ImageView.ScaleType.FIT_XY);

                    switch (Global.mapdemosteps){
                        case 0:{
                            View callfmy = v.findViewById(R.id.callfmy);
                            if(null != callfmy && callfmy.getGlobalVisibleRect(r)){
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
}
