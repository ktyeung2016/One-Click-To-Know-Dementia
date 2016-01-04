package hk.org.jccpa.dementia.game5;

import hk.org.jccpa.dementia.Global;
import hk.org.jccpa.dementia.Pages;
import hk.org.jccpa.dementia.R;
import hk.org.jccpa.dementia.game.UpdateGameData;

import com.example.basefamework.fontsize.Preferences;


import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Gamefivedemo extends Fragment {
	View v;
	ImageButton back,menu;
	Handler h;
	Toast toast;
	Runnable r;
	public static int Stage=0;
	int lastStage=0;
	ImageView laststep,nextstep, overlayImage;
	TextView text,stagenumber;
	View layout, overlay;
	String []s;
	public void onCreate(Bundle savedInstanceState) {

		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);
		super.onCreate(savedInstanceState);
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);

		if (Stage<=1){

			v = inflater.inflate(R.layout.activity_gamefive, container, false);
		}else if(Stage<=4){
			v = inflater.inflate(R.layout.gamefive2, container, false);
		}else{
			v = inflater.inflate(R.layout.gamefive3, container, false);
		}
		back=(ImageButton) v.findViewById(R.id.back);
		menu=(ImageButton) v.findViewById(R.id.menu);
		stagenumber=(TextView) v.findViewById(R.id.stagenumber);
		laststep=(ImageView) v.findViewById(R.id.laststep);
		nextstep=(ImageView) v.findViewById(R.id.nextstep);
		LayoutInflater inflater2 = getActivity().getLayoutInflater();
		layout = inflater2.inflate(R.layout.custoast,
				(ViewGroup) getActivity().findViewById(R.id.toast_layout_root));

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

		text = (TextView) layout.findViewById(R.id.text);
		s=getResources().getStringArray(R.array.game5tipsarray);
		stagenumber.setText((Stage+1)+"/9");
		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(Global.s.getgame5status()==-1){
					Global.onAirAct.pushFragmentBackTo(Pages.Gamemenu);
				}else{
					Global.heart=5;
					Global.right5=0;
					Global.gameqnum=0;
					Global.game5budget=0;
					Global.gamenum=5;
					Gamefivedemo.Stage=0;
					Global.totalscore=100;
					Global.onAirAct.pushFragmentBackTo(Pages.Game_five_1);
				}
					
				
			}
		});
		menu.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Global.onAirAct.mSlidingMenu.toggleRightDrawer();
			}
		});
		laststep.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				toast.cancel();
				Stage--;
				if(Stage<0){
					Stage=0;

				}


				//				if(){
				//
				//				}else if(Stage<=2){
				//					v = inflater.inflate(R.layout.gamefive2, container, false);
				//				}else if (Stage<=1){
				//
				//					v = inflater.inflate(R.layout.activity_gamefive, container, false);
				//				}


				if(Stage==1){
//					Global.onAirAct.pushFragment(Pages.Gamefivedemo);
					Global.onAirAct.clearOneBackStack();
				}
				if(Stage==4){

//					Global.onAirAct.pushFragment(Pages.Gamefivedemo);
					Global.onAirAct.clearOneBackStack();
				}
				stagenumber.setText((Stage+1)+"/9");
				//				text.setText(s[Stage]);
                drawOverlay(v);
			}
		});

		nextstep.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Stage++;
				toast.cancel();
				if(Stage==s.length){
//					Global.onAirAct.clearOneBackStack();
					if(Global.s.getgame5status()==-1){
						Global.s.game5status(0);
                        if(Global.onAirAct.isOnline()){
                            new UpdateGameData().execute(String.valueOf(Global.s.getgame5status()));
                        }
					}else{
						Global.heart=5;
						Global.right5=0;
						Global.gameqnum=0;
						Global.game5budget=0;
						Global.gamenum=5;
						Gamefivedemo.Stage=0;
						Global.totalscore=100;
					}
					
					Global.onAirAct.pushFragmentBackTo(Pages.Game_five_1);
				}
				if(Stage>s.length-1){
					Stage=s.length-1;

				}


				//				if(){
				//
				//				}else if(Stage<=2){
				//					v = inflater.inflate(R.layout.gamefive2, container, false);
				//				}else if (Stage<=1){
				//
				//					v = inflater.inflate(R.layout.activity_gamefive, container, false);
				//				}

				if(Stage==2){
					Global.onAirAct.pushFragment(Pages.Gamefivedemo);
				}
				if(Stage==5){
					Global.onAirAct.pushFragment(Pages.Gamefivedemo);
				}
				stagenumber.setText((Stage+1)+"/9");
                drawOverlay(v);


			}
		});


	

		//		 String []s=getResources().getStringArray(R.array.game5tipsarray);

		text.setText(s[Stage]);

		toast = new Toast(getActivity().getApplicationContext());
		//		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);

		if(Stage==1 || Stage==2  || Stage==4 || Stage==6){


			toast.setGravity(Gravity.CENTER, 0, 0);

		}else if(Stage==3){
			//					toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
			EditText e =(EditText) v.findViewById(R.id.totalprice);
			e.setText("36");

			toast.setGravity(Gravity.CENTER, 0, 0);
			Log.d("stage", "1");
		}else{

		}


		toast.setDuration(Toast.LENGTH_LONG);
		//		toast.setDuration(1000000);
		toast.setView(layout);
		toast.show();

		//		toast.cancel();
		//		toast.

		h=new Handler();
		r=new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				text.setText(s[Stage]);
				if(lastStage!=Stage){
					toast.cancel();
					lastStage=Stage;
					if(Stage==1 || Stage==2  || Stage==4  ){

						toast = new Toast(getActivity().getApplicationContext());
						//					toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
						toast.setDuration(Toast.LENGTH_LONG);
						//		toast.setDuration(1000000);
						toast.setView(layout);
						toast.setGravity(Gravity.CENTER, 0, 0);
						Log.d("stage", "1");
					}else if(Stage==3){
						toast = new Toast(getActivity().getApplicationContext());
						//					toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
						EditText e =(EditText) v.findViewById(R.id.totalprice);
						e.setText("36");
						e.setFocusable(false);
						
						toast.setDuration(Toast.LENGTH_LONG);
						//		toast.setDuration(1000000);
						toast.setView(layout);
						toast.setGravity(Gravity.CENTER, 0, 0);
						Log.d("stage", "1");
					}else if(Stage==6){
						toast = new Toast(getActivity().getApplicationContext());
						//					toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
						toast.setDuration(Toast.LENGTH_LONG);
						//		toast.setDuration(1000000);
						EditText e =(EditText) v.findViewById(R.id.totalprice);
						e.setText("64");
						e.setFocusable(false);
						
						toast.setView(layout);
						toast.setGravity(Gravity.CENTER, 0, 0);
						
					}else{

						toast = new Toast(getActivity().getApplicationContext());
						//					toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
						toast.setDuration(Toast.LENGTH_LONG);
						//		toast.setDuration(1000000);
						toast.setView(layout);
						//					toast.show();
						//					toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM, 0, 0);
						Log.d("stage", "other");
					}
				}


				toast.show();
				h.postDelayed(r, 100);
			}

		};

		//		h.removeCallbacks(r);
		return v;
	}

    public void drawOverlay(View view){
        view.postDelayed(new Runnable() {
            public void run() {
                Rect r = new Rect();
                if (null != overlay && null != overlayImage &&  Stage >= 0 && Stage < s.length) {
                    Bitmap result = Global.maskOverlay.getOverlayBitmap(overlay.getWidth(), overlay.getHeight());
                    if(null == result){
                        return;
                    }
                    overlayImage.setImageBitmap(result);
                    overlayImage.setScaleType(ImageView.ScaleType.FIT_XY);

                    switch (Stage){
                        case 0:{
                            TextView question = (TextView)v.findViewById(R.id.question);
                            if(null != question && question.getGlobalVisibleRect(r)){
                                Global.maskOverlay.drawMask(result, r);
                            }
                            View gameArea = v.findViewById(R.id.gameArea);
                            if(null != gameArea && gameArea.getGlobalVisibleRect(r)){
                                Global.maskOverlay.drawMask(result, r);
                            }
                            View heartContainer = v.findViewById(R.id.heartContainer);
                            if(null != heartContainer && heartContainer.getGlobalVisibleRect(r)){
                                Global.maskOverlay.drawMask(result, r);
                            }
                            break;
                        }
                        case 1:{
                            View enter = v.findViewById(R.id.enter);
                            if(null != enter && enter.getGlobalVisibleRect(r)){
                                Global.maskOverlay.drawMask(result, r);
                            }
                            break;
                        }
                        case 2:{
                            View totalprice = v.findViewById(R.id.totalprice);
                            if(null != totalprice && totalprice.getGlobalVisibleRect(r)){
                                Global.maskOverlay.drawMask(result, r);
                            }
                            View buyList = v.findViewById(R.id.buyList);
                            if(null != buyList && buyList.getGlobalVisibleRect(r)){
                                Global.maskOverlay.drawMask(result, r);
                            }
                            break;
                        }
                        case 3:{
                            View enter = v.findViewById(R.id.enter);
                            if(null != enter && enter.getGlobalVisibleRect(r)){
                                Global.maskOverlay.drawMask(result, r);
                            }
                            break;
                        }
                        case 5:{
                            View totalprice = v.findViewById(R.id.totalprice);
                            if(null != totalprice && totalprice.getGlobalVisibleRect(r)){
                                Global.maskOverlay.drawMask(result, r);
                            }
                            break;
                        }
                        case 7:{
                            View enter = v.findViewById(R.id.enter);
                            if(null != enter && enter.getGlobalVisibleRect(r)){
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
	public void onDestroy(){
		super.onDestroy();
	}
	public void onResume(){
		super.onResume();
		h.postDelayed(r, 100);
	}
	public void onPause(){
		super.onPause();
		h.removeCallbacks(r);
		toast.cancel();
	}
}
