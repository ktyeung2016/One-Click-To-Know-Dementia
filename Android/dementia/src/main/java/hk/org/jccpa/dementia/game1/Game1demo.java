package hk.org.jccpa.dementia.game1;

import hk.org.jccpa.dementia.Global;
import hk.org.jccpa.dementia.Pages;
import hk.org.jccpa.dementia.R;
import hk.org.jccpa.dementia.game.UpdateGameData;

import com.example.basefamework.fontsize.Preferences;


import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Game1demo extends Fragment {
	View v;
	ImageButton back,menu;
	Handler h;
	Toast toast;
	Runnable r;
	public static int Stage=0;
	int lastStage=0;
	ImageView laststep,nextstep, overlayImage;
	TextView text,stagenumber,qnum;
	View layout, overlay;
	String []s;
	public void onCreate(Bundle savedInstanceState) {

		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);
		super.onCreate(savedInstanceState);
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);

		//		if (Stage<=1){
		if(Stage==0){
			v = inflater.inflate(R.layout.demo_gameone_1, container, false);
		}else if(Stage==1){
			v = inflater.inflate(R.layout.demogame_one_2, container, false);
		}else{
			v = inflater.inflate(R.layout.demogameone_3, container, false);
		}
		//		v = inflater.inflate(R.layout.demo_gameone_1, container, false);
		//		v = inflater.inflate(R.layout.demo_gameone_1, container, false);
		//		}else if(Stage<=4){
		//			v = inflater.inflate(R.layout.gamefive2, container, false);
		//		}else{
		//			v = inflater.inflate(R.layout.gamefive3, container, false);
		//		}
		back=(ImageButton) v.findViewById(R.id.back);
		menu=(ImageButton) v.findViewById(R.id.menu);
		stagenumber=(TextView) v.findViewById(R.id.stagenumber);
		laststep=(ImageView) v.findViewById(R.id.laststep);
		nextstep=(ImageView) v.findViewById(R.id.nextstep);
		LayoutInflater inflater2 = getActivity().getLayoutInflater();
		layout = inflater2.inflate(R.layout.custoast,
				(ViewGroup) getActivity().findViewById(R.id.toast_layout_root));
		qnum=(TextView) v.findViewById(R.id.qnum);
		qnum.setText("1");
        overlay=v.findViewById(R.id.overlay);
        overlayImage = (ImageView) v.findViewById(R.id.overlayImage);

        View holepage=v.findViewById(R.id.holepage);
		holepage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(null != nextstep){
                    nextstep.performClick();
                }
			}
		});

		text = (TextView) layout.findViewById(R.id.text);
		s=getResources().getStringArray(R.array.game1tipsarray);
		stagenumber.setText((Stage+1)+"/"+s.length);
		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(Global.s.getgame1status()>-1){
					Global.heart=5;
					Global.right5=0;
					Global.gameqnum=0;
					Global.gamenum=1;
					Global.thingsbought=0;
					Game1demo.Stage=0;
					Global.totalscore=100;
//					Global.s.game1status(-1);
					Global.star=0;
					Global.onAirAct.pushFragmentBackTo(Pages.game_one_1);
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
		laststep.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v1) {
				// TODO Auto-generated method stub

				toast.cancel();
				Stage--;
				if(Stage<0){
					Stage=0;

				}
				if(Stage<=1){
					Global.onAirAct.pushFragment(Pages.Game1demo);
				}
				if(Stage==2){
					Global.onAirAct.pushFragment(Pages.Game1demo);
				}
				if(Stage==3){
					ImageView w1=(ImageView) v.findViewById(R.id.w1);
					w1.setImageResource(R.drawable.gameoneoutline2);
					ImageView w2=(ImageView) v.findViewById(R.id.w2);
					w2.setImageResource(R.drawable.gameoneoutline);
				}
				if(Stage==4){
					ImageView w1=(ImageView) v.findViewById(R.id.w1);
					w1.setImageResource(R.drawable.gameoneoutline);
					ImageView w2=(ImageView) v.findViewById(R.id.w2);
					w2.setImageResource(R.drawable.gameoneoutline3);

				}
				if(Stage==5){
					ImageView w1=(ImageView) v.findViewById(R.id.w1);
					w1.setImageResource(R.drawable.gameoneoutline3);
					ImageView w2=(ImageView) v.findViewById(R.id.w2);
					w2.setImageResource(R.drawable.gameoneoutline);

				}
				if(Stage==6){
					ImageView w1=(ImageView) v.findViewById(R.id.w1);
					w1.setImageResource(R.drawable.gameoneoutline);
					ImageView w2=(ImageView) v.findViewById(R.id.w2);
					w2.setImageResource(R.drawable.gameoneoutline);
					RelativeLayout tipslayout=(RelativeLayout) v.findViewById(R.id.tipslayoutdemoone);
					tipslayout.setVisibility(View.GONE);
				}
				if(Stage==7){
					RelativeLayout tipslayout=(RelativeLayout) v.findViewById(R.id.tipslayoutdemoone);
					tipslayout.setVisibility(View.VISIBLE);
				}

				stagenumber.setText((Stage+1)+"/"+s.length);
				//				text.setText(s[Stage]);
                drawOverlay(v);
			}
		});

		nextstep.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v1) {
                // TODO Auto-generated method stub
                toast.cancel();
                Stage++;
                if (Stage == s.length) {
                    //					Global.onAirAct.clearOneBackStack();
                    if (Global.s.getgame1status() == -1) {
                        Global.s.game1status(0);
                        if(Global.onAirAct.isOnline()){
                            new UpdateGameData().execute(String.valueOf(Global.s.getgame1status()));
                        }
                    } else {
                        Global.heart = 5;
                        Global.right5 = 0;
                        Global.gameqnum = 0;
                        Global.gamenum = 1;
                        Global.thingsbought = 0;
                        Game1demo.Stage = 0;
                        Global.totalscore = 100;
//						Global.s.game1status(-1);
                        Global.star = 0;
                    }
                    Global.onAirAct.pushFragmentBackTo(Pages.game_one_1);
                }
                if (Stage > s.length - 1) {
                    Stage = s.length - 1;

                }
                if (Stage == 1) {
                    Global.onAirAct.pushFragment(Pages.Game1demo);
                }
                if (Stage == 2) {
                    Global.onAirAct.pushFragment(Pages.Game1demo);
                }

                if (Stage == 3) {
                    ImageView w1 = (ImageView) v.findViewById(R.id.w1);
                    if(null != w1){
                        w1.setImageResource(R.drawable.gameoneoutline2);
                    }
                }
                if (Stage == 4) {
                    ImageView w1 = (ImageView) v.findViewById(R.id.w1);
                    if(null != w1){
                        w1.setImageResource(R.drawable.gameoneoutline);
                    }
                    ImageView w2 = (ImageView) v.findViewById(R.id.w2);
                    if(null != w2){
                        w2.setImageResource(R.drawable.gameoneoutline3);
                    }
                }
                if (Stage == 5) {
                    ImageView w1 = (ImageView) v.findViewById(R.id.w1);
                    if(null != w1){
                        w1.setImageResource(R.drawable.gameoneoutline3);
                    }
                    ImageView w2 = (ImageView) v.findViewById(R.id.w2);
                    if(null != w2){
                        w2.setImageResource(R.drawable.gameoneoutline);
                    }
                }
                if (Stage == 6) {
                    ImageView w1 = (ImageView) v.findViewById(R.id.w1);
                    if(null != w1){
                        w1.setImageResource(R.drawable.gameoneoutline);
                    }
                    ImageView w2 = (ImageView) v.findViewById(R.id.w2);
                    if(null != w2){
                        w2.setImageResource(R.drawable.gameoneoutline);
                    }
                }
                if (Stage == 7) {
                    RelativeLayout tipslayout = (RelativeLayout) v.findViewById(R.id.tipslayoutdemoone);
                    if(null != tipslayout){
                        tipslayout.setVisibility(View.VISIBLE);
                    }
                }
                if (Stage == 8) {
                    RelativeLayout tipslayout = (RelativeLayout) v.findViewById(R.id.tipslayoutdemoone);
                    if(null != tipslayout){
                        tipslayout.setVisibility(View.GONE);
                    }
                }
                if(null != stagenumber){
                    stagenumber.setText((Stage + 1) + "/" + s.length);
                }
                drawOverlay(v);
            }
		});




		//		 String []s=getResources().getStringArray(R.array.game5tipsarray);

		text.setText(s[Stage]);

		toast = new Toast(getActivity().getApplicationContext());
		//		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);

		if(    Stage==5|| Stage==6){


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
                try {
                    text.setText(s[Stage]);

                    if (lastStage != Stage) {
                        if (null != toast) {
                            toast.cancel();
                        }
                        lastStage = Stage;

                        Log.d("Game1demo", "stage=" + Stage);
                        if (Stage == 9) {

                            toast = new Toast(getActivity().getApplicationContext());
                            //					toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                            toast.setDuration(Toast.LENGTH_LONG);
                            //		toast.setDuration(1000000);
                            toast.setView(layout);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                        } else if (Stage == 0 || Stage == 1 || Stage == 2 || Stage == 4 || Stage == 5 || Stage == 7 || Stage == 8) {
                            toast = new Toast(getActivity().getApplicationContext());
                            //					toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);


                            toast.setDuration(Toast.LENGTH_LONG);
                            //		toast.setDuration(1000000);
                            toast.setView(layout);
                            //						toast.setGravity(Gravity.CENTER, 0, 0);
                        } else if (Stage == 3 || Stage == 6) {
                            if (null != toast) {
                                toast.cancel();
                            }
                            //						toast = new Toast(getActivity().getApplicationContext());
                            //						//					toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                            //						toast.setDuration(Toast.LENGTH_LONG);
                            //						//		toast.setDuration(1000000);
                            //						toast.setView(layout);
                            //						//					toast.show();
                            //						//					toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM, 0, 0);
                            //						Log.d("stage", "other");
                        }
                    }

                    if (Stage != 3 && Stage != 7) {
                        if (null != toast) {
                            toast.show();
                        }
                    }
                } catch (Exception e) {
                    Log.d("Game1demo", e.getMessage());
                }
                h.postDelayed(r, 100);
            }


        };

		//		h.removeCallbacks(r);
		return v;
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

                    switch (Stage) {
                        case 0: {
                            LinearLayout gameArea = (LinearLayout) v.findViewById(R.id.gameArea);
                            if (null != gameArea && gameArea.getGlobalVisibleRect(r)) {
                                Global.maskOverlay.drawMask(result, r);
                            }
                            break;
                        }
                        case 1: {
                            TextView question = (TextView) v.findViewById(R.id.question);
                            if (null != question && question.getGlobalVisibleRect(r)) {
                                Global.maskOverlay.drawMask(result, r);
                            }
                            LinearLayout gameArea = (LinearLayout) v.findViewById(R.id.gameArea);
                            if (null != gameArea && gameArea.getGlobalVisibleRect(r)) {
                                Global.maskOverlay.drawMask(result, r);
                            }
                            break;
                        }
                        case 2: {
                            LinearLayout heartContainer = (LinearLayout) v.findViewById(R.id.heartContainer);
                            if (null != heartContainer && heartContainer.getGlobalVisibleRect(r)) {
                                Global.maskOverlay.drawMask(result, r);
                            }
                            break;
                        }
                        case 3: {
                            LinearLayout gameArea = (LinearLayout) v.findViewById(R.id.gameArea);
                            if (null != gameArea && gameArea.getGlobalVisibleRect(r)) {
                                Global.maskOverlay.drawMask(result, r);
                            }
                            break;
                        }
                        case 4: {
                            ImageView w1 = (ImageView) v.findViewById(R.id.w1);
                            w1.setImageResource(R.drawable.gameoneoutline);
                            ImageView w2 = (ImageView) v.findViewById(R.id.w2);
                            w2.setImageResource(R.drawable.gameoneoutline3);
                            if (null != w2 && w2.getGlobalVisibleRect(r)) {
                                Global.maskOverlay.drawMask(result, r);
                            }
                            break;
                        }
                        case 5: {
                            ImageView w1 = (ImageView) v.findViewById(R.id.w1);
                            w1.setImageResource(R.drawable.gameoneoutline3);
                            if (null != w1 && w1.getGlobalVisibleRect(r)) {
                                Global.maskOverlay.drawMask(result, r);
                            }
                            ImageView w2 = (ImageView) v.findViewById(R.id.w2);
                            w2.setImageResource(R.drawable.gameoneoutline);
                            break;
                        }
                        case 6: {
                            ImageView tips = (ImageView) v.findViewById(R.id.tips);
                            if (null != tips && tips.getGlobalVisibleRect(r)) {
                                Global.maskOverlay.drawMask(result, r);
                            }
                            break;
                        }
                        case 7: {
                            RelativeLayout tipslayout = (RelativeLayout) v.findViewById(R.id.tipslayoutdemoone);
                            if (null != tipslayout && tipslayout.getGlobalVisibleRect(r)) {
                                Global.maskOverlay.drawMask(result, r);
                            }
                            View lvone = v.findViewById(R.id.lvone);
                            if (null != lvone && lvone.getGlobalVisibleRect(r)) {
                                Global.maskOverlay.drawMask(result, r);
                            }
                            break;
                        }
                        case 8: {
                            LinearLayout starlayout = (LinearLayout) v.findViewById(R.id.starlayout);
                            if (null != starlayout && starlayout.getGlobalVisibleRect(r)) {
                                Global.maskOverlay.drawMask(result, r);
                            }
                            break;
                        }
                        default: {

                        }
                    }
                }
            }
        }, 1);
    }


	public void onDestroy(){
        if (null != toast) {
            toast.cancel();
        }
        super.onDestroy();
	}
	public void onResume(){
		super.onResume();
		if(null != h){
            h.postDelayed(r, 100);
        }
	}
	public void onPause(){
		super.onPause();
		if(null != h){
            h.removeCallbacks(r);
        }
		if(null != toast){
            toast.cancel();
        }
	}
}
