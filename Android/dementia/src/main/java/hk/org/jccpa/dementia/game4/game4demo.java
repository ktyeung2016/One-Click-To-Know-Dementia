package hk.org.jccpa.dementia.game4;

import java.util.ArrayList;

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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class game4demo extends Fragment {
	View v;
	ImageButton back,menu;
	ImageView laststep,nextstep, overlayImage;
	TextView text,stagenumber;
	View layout, overlay;
	int lastStage=0;
	Handler h;
	Toast toast;
	Runnable r;
	String []s;
	ArrayList<ImageView> viewList; 
	ImageView a1,a2,a3,b1,b2,b3,c1,c2,c3,tips,heart;
	int d[]={1,0,0,0,0,1,1,1,0};
	public static int Stage=0;
	public void onCreate(Bundle savedInstanceState) {
		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);

		super.onCreate(savedInstanceState);
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);
		v = inflater.inflate(R.layout.game4demo, container, false);
		back=(ImageButton) v.findViewById(R.id.back);
		menu=(ImageButton) v.findViewById(R.id.menu);
        overlay=v.findViewById(R.id.overlay);
        overlayImage = (ImageView) v.findViewById(R.id.overlayImage);
		View holepage=v.findViewById(R.id.holepage);
		holepage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v1) {
				// TODO Auto-generated method stub
                nextstep.performClick();
			}
		});
		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(Global.s.getgame4status()!=-1){
					Global.heart=5;
					Global.right5=0;
					game4demo.Stage=0;
					Global.gameqnum=0;
					Global.gamenum=4;
					Global.totalscore=100;
					Global.onAirAct.pushFragmentBackTo(Pages.Game_four_1);
				}else{
					Global.onAirAct.pushFragmentBackTo(Pages.Gamemenu);
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
		LayoutInflater inflater2 = getActivity().getLayoutInflater();
		layout = inflater2.inflate(R.layout.custoast,
				(ViewGroup) getActivity().findViewById(R.id.toast_layout_root));

		text = (TextView) layout.findViewById(R.id.text);
		s=getResources().getStringArray(R.array.game4tipsarray);
		stagenumber=(TextView) v.findViewById(R.id.stagenumber);
		stagenumber.setText((Stage+1)+"/"+s.length);
        Button enterBtn = (Button)v.findViewById(R.id.enter);
        enterBtn.setText(getResources().getString(R.string.gamestart));

		laststep=(ImageView) v.findViewById(R.id.laststep);
		nextstep=(ImageView) v.findViewById(R.id.nextstep);

		laststep.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v1) {
				// TODO Auto-generated method stub

				toast.cancel();
				Stage--;
				Log.d("Stage","Stage:   "+Stage);
				if(Stage<0){
					Stage=0;

				}
				if(Stage<10){
					for(int i = 0 ;i<viewList.size();i++){
						viewList.get(i).setTag(i);

						if(d[i]==0){
							Log.d("Click","i am here"  +  "tag:   "+String.valueOf(viewList.get(i).getTag())+"     "+ viewList.size() );
							viewList.get(i).setImageResource(R.drawable.game4element2);
						}else{
							Log.d("Click","i am here"  +  "tag:   "+String.valueOf(viewList.get(i).getTag())+"     "+ viewList.size() );
							viewList.get(i).setImageResource(R.drawable.game4element);
						}

					}
					ImageView temp=(ImageView) v.findViewById(R.id.rorwg4);
					temp.setVisibility(View.GONE);
					heart= (ImageView)v.findViewById(R.id.g4h1);
					heart.setImageResource(R.drawable.icon_emptyheart);
				}
				if(Stage<9){
					for(int i = 0 ;i<viewList.size();i++){
						viewList.get(i).setTag(i);

						if(d[i]==1){
							Log.d("Click","i am here"  +  "tag:   "+String.valueOf(viewList.get(i).getTag())+"     "+ viewList.size() );
							viewList.get(i).setImageResource(R.drawable.game4element2);
						}else{
							Log.d("Click","i am here"  +  "tag:   "+String.valueOf(viewList.get(i).getTag())+"     "+ viewList.size() );
							viewList.get(i).setImageResource(R.drawable.game4element);
						}

					}
					ImageView temp=(ImageView) v.findViewById(R.id.rorwg4);
					temp.setVisibility(View.GONE);
					heart.setImageResource(R.drawable.icon_emptyheart);
                }
                if(Stage<2){
                    Button enterBtn = (Button)v.findViewById(R.id.enter);
                    enterBtn.setText(getResources().getString(R.string.gamestart));
                }
                if(Stage<7){
					for(int i = 0 ;i<viewList.size();i++){
						viewList.get(i).setTag(i);

						if(d[i]==0){
							Log.d("Click","i am here"  +  "tag:   "+String.valueOf(viewList.get(i).getTag())+"     "+ viewList.size() );
							viewList.get(i).setImageResource(R.drawable.game4element2);
						}else{
							Log.d("Click","i am here"  +  "tag:   "+String.valueOf(viewList.get(i).getTag())+"     "+ viewList.size() );
							viewList.get(i).setImageResource(R.drawable.game4element);
						}

					}
					heart.setImageResource(R.drawable.icon_heart);
				}
				if(Stage<6){
					for(int i = 0 ;i<viewList.size();i++){
						viewList.get(i).setTag(i);


						Log.d("Click","i am here"  +  "tag:   "+String.valueOf(viewList.get(i).getTag())+"     "+ viewList.size() );
						viewList.get(i).setImageResource(R.drawable.game4element2);


					}
					viewList.get(7).setImageResource(R.drawable.game4element);
					viewList.get(4).setImageResource(R.drawable.game4element2);
				}
				if(Stage<4){
					for(int i = 0 ;i<viewList.size();i++){
						viewList.get(i).setTag(i);


						Log.d("Click","i am here"  +  "tag:   "+String.valueOf(viewList.get(i).getTag())+"     "+ viewList.size() );
						viewList.get(i).setImageResource(R.drawable.game4element2);


					}
					viewList.get(7).setImageResource(R.drawable.game4element);
					viewList.get(4).setImageResource(R.drawable.game4element);
				}

				if(Stage<3){
					for(int i = 0 ;i<viewList.size();i++){
						viewList.get(i).setTag(i);


						Log.d("Click","i am here"  +  "tag:   "+String.valueOf(viewList.get(i).getTag())+"     "+ viewList.size() );
						viewList.get(i).setImageResource(R.drawable.game4element2);


					}
					viewList.get(7).setImageResource(R.drawable.game4element);
					viewList.get(4).setImageResource(R.drawable.game4element2);
				}
				if(Stage<2){
					for(int i = 0 ;i<viewList.size();i++){
						viewList.get(i).setTag(i);

						if(d[i]==0){
							Log.d("Click","i am here"  +  "tag:   "+String.valueOf(viewList.get(i).getTag())+"     "+ viewList.size() );
							viewList.get(i).setImageResource(R.drawable.game4element2);
						}else{
							Log.d("Click","i am here"  +  "tag:   "+String.valueOf(viewList.get(i).getTag())+"     "+ viewList.size() );
							viewList.get(i).setImageResource(R.drawable.game4element);
						}

					}
				}
				stagenumber.setText((Stage+1)+"/"+s.length);
                drawOverlay(v);
			}
		});
		viewList=new ArrayList<ImageView>();
		viewList.add((ImageView) v.findViewById(R.id.a1d));
		viewList.add((ImageView) v.findViewById(R.id.b1d));
		viewList.add((ImageView) v.findViewById(R.id.c1d));
		viewList.add((ImageView) v.findViewById(R.id.a2d));
		viewList.add((ImageView) v.findViewById(R.id.b2d));
		viewList.add((ImageView) v.findViewById(R.id.c2d));
		viewList.add((ImageView) v.findViewById(R.id.a3d));
		viewList.add((ImageView) v.findViewById(R.id.b3d));
		viewList.add((ImageView) v.findViewById(R.id.c3d));
		for(int i = 0 ;i<viewList.size();i++){
			viewList.get(i).setTag(i);

			if(d[i]==0){
				Log.d("Click","i am here"  +  "tag:   "+String.valueOf(viewList.get(i).getTag())+"     "+ viewList.size() );
				viewList.get(i).setImageResource(R.drawable.game4element2);
			}else{
				Log.d("Click","i am here"  +  "tag:   "+String.valueOf(viewList.get(i).getTag())+"     "+ viewList.size() );
				viewList.get(i).setImageResource(R.drawable.game4element);
			}

		}

		nextstep.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v1) {
				// TODO Auto-generated method stub
				Stage++;
				toast.cancel();
				Log.d("Stage","Stage:   "+Stage);

                Button enterBtn = (Button)v.findViewById(R.id.enter);
                enterBtn.setText(getResources().getString(R.string.gamestart));
				if(Stage==s.length){
					//					Global.onAirAct.clearOneBackStack();
					if(Global.s.getgame4status()==-1){
						Global.s.game4status(0);
                        if(Global.onAirAct.isOnline()){
                            new UpdateGameData().execute(String.valueOf(Global.s.getgame4status()));
                        }
					}else{
						Global.heart=5;
						Global.right5=0;
						game4demo.Stage=0;
						Global.gameqnum=0;
						Global.gamenum=4;
						Global.totalscore=100;
					}
					
					Global.onAirAct.pushFragmentBackTo(Pages.Game_four_1);
				}
				if(Stage>s.length-1){
					Stage=s.length-1;

				}
				if(Stage==2){
					for(int i = 0 ;i<viewList.size();i++){
						viewList.get(i).setTag(i);


						Log.d("Click","i am here"  +  "tag:   "+String.valueOf(viewList.get(i).getTag())+"     "+ viewList.size() );
						viewList.get(i).setImageResource(R.drawable.game4element2);


					}
					viewList.get(7).setImageResource(R.drawable.game4element);
				}
                if(Stage>2){
                    enterBtn = (Button)v.findViewById(R.id.enter);
                    enterBtn.setText(getResources().getString(R.string.enter));
                }
				if(Stage==3){
					for(int i = 0 ;i<viewList.size();i++){
						viewList.get(i).setTag(i);


						Log.d("Click","i am here"  +  "tag:   "+String.valueOf(viewList.get(i).getTag())+"     "+ viewList.size() );
						viewList.get(i).setImageResource(R.drawable.game4element2);


					}
					viewList.get(7).setImageResource(R.drawable.game4element);
					viewList.get(4).setImageResource(R.drawable.game4element);
				}
				if(Stage==4){
					for(int i = 0 ;i<viewList.size();i++){
						viewList.get(i).setTag(i);


						Log.d("Click","i am here"  +  "tag:   "+String.valueOf(viewList.get(i).getTag())+"     "+ viewList.size() );
						viewList.get(i).setImageResource(R.drawable.game4element2);


					}
					viewList.get(7).setImageResource(R.drawable.game4element);
					viewList.get(4).setImageResource(R.drawable.game4element2);
				}
				if(Stage==5){
					for(int i = 0 ;i<viewList.size();i++){
						viewList.get(i).setTag(i);


						Log.d("Click","i am here"  +  "tag:   "+String.valueOf(viewList.get(i).getTag())+"     "+ viewList.size() );
						viewList.get(i).setImageResource(R.drawable.game4element2);


					}
					viewList.get(7).setImageResource(R.drawable.game4element);
					viewList.get(4).setImageResource(R.drawable.game4element2);
				}
				if(Stage==6){
					for(int i = 0 ;i<viewList.size();i++){
						viewList.get(i).setTag(i);

						if(d[i]==0){
							Log.d("Click","i am here"  +  "tag:   "+String.valueOf(viewList.get(i).getTag())+"     "+ viewList.size() );
							viewList.get(i).setImageResource(R.drawable.game4element2);
						}else{
							Log.d("Click","i am here"  +  "tag:   "+String.valueOf(viewList.get(i).getTag())+"     "+ viewList.size() );
							viewList.get(i).setImageResource(R.drawable.game4element);
						}

					}
                }
                if(Stage>=6){
                    enterBtn.setText(getResources().getString(R.string.enter));
				}
				//				if(Stage==7){
				//					for(int i = 0 ;i<viewList.size();i++){
				//						viewList.get(i).setTag(i);
				//
				//						if(d[i]==0){
				//							Log.d("Click","i am here"  +  "tag:   "+String.valueOf(viewList.get(i).getTag())+"     "+ viewList.size() );
				//							viewList.get(i).setImageResource(R.drawable.game4element2);
				//						}else{
				//							Log.d("Click","i am here"  +  "tag:   "+String.valueOf(viewList.get(i).getTag())+"     "+ viewList.size() );
				//							viewList.get(i).setImageResource(R.drawable.game4element);
				//						}
				//
				//					}
				//				}
				if(Stage==7){
					for(int i = 0 ;i<viewList.size();i++){
						viewList.get(i).setTag(i);

						if(d[i]==1){
							Log.d("Click","i am here"  +  "tag:   "+String.valueOf(viewList.get(i).getTag())+"     "+ viewList.size() );
							viewList.get(i).setImageResource(R.drawable.game4element2);
						}else{
							Log.d("Click","i am here"  +  "tag:   "+String.valueOf(viewList.get(i).getTag())+"     "+ viewList.size() );
							viewList.get(i).setImageResource(R.drawable.game4element);
						}

					}
					heart= (ImageView)v.findViewById(R.id.g4h1);
					heart.setImageResource(R.drawable.icon_emptyheart);
				}
				if(Stage==8){
					for(int i = 0 ;i<viewList.size();i++){
						viewList.get(i).setTag(i);

						if(d[i]==1){
							Log.d("Click","i am here"  +  "tag:   "+String.valueOf(viewList.get(i).getTag())+"     "+ viewList.size() );
							viewList.get(i).setImageResource(R.drawable.game4element2);
						}else{
							Log.d("Click","i am here"  +  "tag:   "+String.valueOf(viewList.get(i).getTag())+"     "+ viewList.size() );
							viewList.get(i).setImageResource(R.drawable.game4element);
						}

					}

				}
				if(Stage==9){
					for(int i = 0 ;i<viewList.size();i++){
						viewList.get(i).setTag(i);

						if(d[i]==0){
							Log.d("Click","i am here"  +  "tag:   "+String.valueOf(viewList.get(i).getTag())+"     "+ viewList.size() );
							viewList.get(i).setImageResource(R.drawable.game4element2);
						}else{
							Log.d("Click","i am here"  +  "tag:   "+String.valueOf(viewList.get(i).getTag())+"     "+ viewList.size() );
							viewList.get(i).setImageResource(R.drawable.game4element);
						}

					}
					ImageView temp=(ImageView) v.findViewById(R.id.rorwg4);
					temp.setVisibility(View.VISIBLE);

					heart.setImageResource(R.drawable.icon_heart);
				}
				stagenumber.setText((Stage+1)+"/"+s.length);
                drawOverlay(v);
			}
		});

		//		 String []s=getResources().getStringArray(R.array.game5tipsarray);

		text.setText(s[Stage]);

		toast = new Toast(getActivity().getApplicationContext());
		//		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);

		if(Stage==1   || Stage==5|| Stage==6){


			toast.setGravity(Gravity.CENTER, 0, 0);

		}else if(Stage==3){
			//					toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);

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
					if(Stage==1 || Stage==9 ){

						toast = new Toast(getActivity().getApplicationContext());
						//					toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
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
						toast.setView(layout);
						toast.setGravity(Gravity.CENTER, 0, 0);
						Log.d("stage", "1");
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
                    overlay.setVisibility(View.VISIBLE);

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
                            View box = v.findViewById(R.id.b3d);
                            if(null != box && box.getGlobalVisibleRect(r)){
                                Global.maskOverlay.drawMask(result, r);
                            }
                            break;
                        }
                        case 3:{
                            View box = v.findViewById(R.id.b2d);
                            if(null != box && box.getGlobalVisibleRect(r)){
                                Global.maskOverlay.drawMask(result, r);
                            }
                            break;
                        }
                        case 4:{
                            View box = v.findViewById(R.id.b2d);
                            if(null != box && box.getGlobalVisibleRect(r)){
                                Global.maskOverlay.drawMask(result, r);
                            }
                            break;
                        }
                        case 5:{
                            ImageView tips=(ImageView) v.findViewById(R.id.tips);
                            if(null != tips && tips.getGlobalVisibleRect(r)){
                                Global.maskOverlay.drawMask(result, r);
                            }
                            break;
                        }
                        case 6:{
                            View enter = v.findViewById(R.id.enter);
                            if(null != enter && enter.getGlobalVisibleRect(r)){
                                Global.maskOverlay.drawMask(result, r);
                            }
                            break;
                        }
                        case 7:{
                            View heartContainer = v.findViewById(R.id.heartContainer);
                            if(null != heartContainer && heartContainer.getGlobalVisibleRect(r)){
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
