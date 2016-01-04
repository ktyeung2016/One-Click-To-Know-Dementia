package hk.org.jccpa.dementia.game2;

import hk.org.jccpa.dementia.ColorFilterGenerator;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Game2demo extends Fragment {
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

		//		if (Stage<=1){

		v = inflater.inflate(R.layout.gametwo1, container, false);
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
        overlay=v.findViewById(R.id.overlay);
        overlayImage = (ImageView) v.findViewById(R.id.overlayImage);
		View holepage=v.findViewById(R.id.holepage);
		holepage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v1) {
                nextstep.performClick();

			}
		});

		text = (TextView) layout.findViewById(R.id.text);
		s=getResources().getStringArray(R.array.game2tipsarray);
		stagenumber.setText((Stage+1)+"/8");
		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(Global.s.getgame2status()==-1){
					Global.heart=5;
					Global.right5=0;
					Global.gameqnum=0;
					Global.gamenum=2;
					Game2demo.Stage=0;
					Global.totalscore=100;
					Global.onAirAct.pushFragmentBackTo(Pages.Gamemenu);
				}else{
					Global.onAirAct.pushFragmentBackTo(Pages.Game_two_1);
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
				if(Stage<=5){
					ImageView one = (ImageView) v.findViewById(R.id.one);
					one.setImageResource(R.drawable.b001_a);

					ImageView wrong=(ImageView) v.findViewById(R.id.wrong);
					wrong.setVisibility(View.GONE);
					ImageView g3h1=(ImageView) v.findViewById(R.id.g3h1);
					g3h1.setImageResource(R.drawable.icon_heart);
					ImageView rorw=(ImageView) v.findViewById(R.id.rorw);
					rorw.setVisibility(View.VISIBLE);
					rorw.setImageResource(R.drawable.tick);
				}
				if(Stage<=4){
					ImageView rorw=(ImageView) v.findViewById(R.id.rorw);
					rorw.setVisibility(View.GONE);
					rorw.setImageResource(R.drawable.tick);
					LinearLayout pool=(LinearLayout) v.findViewById(R.id.pool);
					pool.setVisibility(View.GONE);
					LinearLayout confrimLayout=(LinearLayout) v.findViewById(R.id.confrimLayout);
					confrimLayout.setVisibility(View.VISIBLE);
					ImageView one = (ImageView) v.findViewById(R.id.one);
					one.setImageResource(R.drawable.b001_a);
					ImageView onea = (ImageView) v.findViewById(R.id.onea);
					onea.setVisibility(View.VISIBLE);
					ImageView three = (ImageView) v.findViewById(R.id.three);
					three.setImageResource(R.drawable.b002_a);
					ImageView threea = (ImageView) v.findViewById(R.id.threea);
					threea.setVisibility(View.VISIBLE);
					ImageView two = (ImageView) v.findViewById(R.id.two);
					two.setImageResource(R.drawable.b004_a);
					ImageView twoa = (ImageView) v.findViewById(R.id.threea);
					twoa.setVisibility(View.VISIBLE);
					twoa.setColorFilter(
							ColorFilterGenerator.adjustBrightness(80));
					//					LinearLayout pool=(LinearLayout) v.findViewById(R.id.pool);
					//					pool.setVisibility(View.VISIBLE);
					//					LinearLayout confrimLayout=(LinearLayout) v.findViewById(R.id.confrimLayout);
					//					confrimLayout.setVisibility(View.GONE);
					//					ImageView threea = (ImageView) v.findViewById(R.id.threea);
					threea.setColorFilter(
							ColorFilterGenerator.adjustBrightness(0));
					//					ImageView two = (ImageView) v.findViewById(R.id.two);
					two.setColorFilter(
							ColorFilterGenerator.adjustBrightness(0));
					//					ImageView twoa = (ImageView) v.findViewById(R.id.twoa);
					twoa.setColorFilter(
							ColorFilterGenerator.adjustBrightness(0));
					//					ImageView one = (ImageView) v.findViewById(R.id.one);
					one.setImageResource(R.drawable.b001_a);
					//					ImageView onea = (ImageView) v.findViewById(R.id.onea);
					onea.setVisibility(View.GONE);

					two.setColorFilter(
							ColorFilterGenerator.adjustBrightness(80));

					twoa.setColorFilter(
							ColorFilterGenerator.adjustBrightness(80));
				}
				if(Stage<=3){
					
					LinearLayout pool=(LinearLayout) v.findViewById(R.id.pool);
					pool.setVisibility(View.VISIBLE);
					LinearLayout confrimLayout=(LinearLayout) v.findViewById(R.id.confrimLayout);
					confrimLayout.setVisibility(View.GONE);
					ImageView threea = (ImageView) v.findViewById(R.id.threea);
					threea.setColorFilter(
							ColorFilterGenerator.adjustBrightness(0));
					ImageView two = (ImageView) v.findViewById(R.id.two);
					two.setColorFilter(
							ColorFilterGenerator.adjustBrightness(0));
					ImageView twoa = (ImageView) v.findViewById(R.id.twoa);
					twoa.setColorFilter(
							ColorFilterGenerator.adjustBrightness(0));
					ImageView one = (ImageView) v.findViewById(R.id.one);
					one.setImageResource(R.drawable.b001_a);
					ImageView onea = (ImageView) v.findViewById(R.id.onea);
					onea.setVisibility(View.GONE);

					two.setColorFilter(
							ColorFilterGenerator.adjustBrightness(80));

					twoa.setColorFilter(
							ColorFilterGenerator.adjustBrightness(80));
					two.setImageResource(R.drawable.b004);
					ImageView three = (ImageView) v.findViewById(R.id.three);

					three.setImageResource(R.drawable.b002);
				}
				if(Stage<=2){
					ImageView two = (ImageView) v.findViewById(R.id.two);
					two.setColorFilter(
							ColorFilterGenerator.adjustBrightness(0));
					ImageView twoa = (ImageView) v.findViewById(R.id.twoa);
					twoa.setColorFilter(
							ColorFilterGenerator.adjustBrightness(0));
					ImageView one = (ImageView) v.findViewById(R.id.one);
					one.setImageResource(R.drawable.b001_a);
					ImageView onea = (ImageView) v.findViewById(R.id.onea);
					onea.setVisibility(View.GONE);
					//					twoa.setVisibility(View.GONE);
				}
				if(Stage<2){
					ImageView one = (ImageView) v.findViewById(R.id.one);
					one.setImageResource(R.drawable.b001);
					ImageView onea = (ImageView) v.findViewById(R.id.onea);
					onea.setVisibility(View.VISIBLE);
				}

				//				if(){
				//
				//				}else if(Stage<=2){
				//					v = inflater.inflate(R.layout.gamefive2, container, false);
				//				}else if (Stage<=1){
				//
				//					v = inflater.inflate(R.layout.activity_gamefive, container, false);
				//				}


				//				if(Stage==1){
				//					//					Global.onAirAct.pushFragment(Pages.Gamefivedemo);
				//					Global.onAirAct.clearOneBackStack();
				//				}
				//				if(Stage==4){
				//
				//					//					Global.onAirAct.pushFragment(Pages.Gamefivedemo);
				//					Global.onAirAct.clearOneBackStack();
				//				}
				stagenumber.setText((Stage+1)+"/8");
				//				text.setText(s[Stage]);
                drawOverlay(v);
			}
		});

		nextstep.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v1) {
				// TODO Auto-generated method stub
				Stage++;
				toast.cancel();
				if(Stage==s.length){
					//					Global.onAirAct.clearOneBackStack();
					if(Global.s.getgame2status()==-1){
						Global.s.game2status(0);
                        if(Global.onAirAct.isOnline()){
                            new UpdateGameData().execute(String.valueOf(Global.s.getgame2status()));
                        }
					}else{
						Global.heart=5;
						Global.right5=0;
						Global.gameqnum=0;
						Global.gamenum=2;
						Game2demo.Stage=0;
						Global.totalscore=100;
					}
					Global.onAirAct.pushFragmentBackTo(Pages.Game_two_1);
				}
				if(Stage>s.length-1){
					Stage=s.length-1;

				}
				if(Stage==2){
					ImageView one = (ImageView) v.findViewById(R.id.one);
					one.setImageResource(R.drawable.b001_a);
					ImageView onea = (ImageView) v.findViewById(R.id.onea);
					onea.setVisibility(View.GONE);
				}
				if(Stage==3){
					ImageView two = (ImageView) v.findViewById(R.id.two);
					two.setColorFilter(
							ColorFilterGenerator.adjustBrightness(80));
					ImageView twoa = (ImageView) v.findViewById(R.id.twoa);
					twoa.setColorFilter(
							ColorFilterGenerator.adjustBrightness(80));
					//					twoa.setVisibility(View.GONE);
				}
				if(Stage==4){
					LinearLayout pool=(LinearLayout) v.findViewById(R.id.pool);
					pool.setVisibility(View.GONE);
					LinearLayout confrimLayout=(LinearLayout) v.findViewById(R.id.confrimLayout);
					confrimLayout.setVisibility(View.VISIBLE);
					ImageView one = (ImageView) v.findViewById(R.id.one);
					one.setImageResource(R.drawable.b001_a);
					ImageView onea = (ImageView) v.findViewById(R.id.onea);
					onea.setVisibility(View.GONE);
					ImageView three = (ImageView) v.findViewById(R.id.three);
					three.setImageResource(R.drawable.b002_a);
					ImageView threea = (ImageView) v.findViewById(R.id.threea);
					threea.setVisibility(View.GONE);
					ImageView two = (ImageView) v.findViewById(R.id.two);
					two.setImageResource(R.drawable.b004_a);
					ImageView twoa = (ImageView) v.findViewById(R.id.threea);
					twoa.setVisibility(View.GONE);
					twoa.setColorFilter(
							ColorFilterGenerator.adjustBrightness(0));

				}
				if(Stage==5){
					ImageView rorw=(ImageView) v.findViewById(R.id.rorw);
					rorw.setVisibility(View.VISIBLE);
				}
				if(Stage==6){
					ImageView one = (ImageView) v.findViewById(R.id.one);
					one.setImageResource(R.drawable.b001);
					ImageView rorw=(ImageView) v.findViewById(R.id.rorw);
					rorw.setImageResource(R.drawable.cross);
					ImageView wrong=(ImageView) v.findViewById(R.id.wrong);
					wrong.setVisibility(View.VISIBLE);
					ImageView g3h1=(ImageView) v.findViewById(R.id.g3h1);
					g3h1.setImageResource(R.drawable.icon_emptyheart);
				}
				//				if(){
				//
				//				}else if(Stage<=2){
				//					v = inflater.inflate(R.layout.gamefive2, container, false);
				//				}else if (Stage<=1){
				//
				//					v = inflater.inflate(R.layout.activity_gamefive, container, false);
				//				}


				stagenumber.setText((Stage+1)+"/8");
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
					if(Stage==1  || Stage==4  ){

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
                    overlay.setVisibility(View.VISIBLE);

                    switch (Stage) {
                        case 0: {
                            TextView question = (TextView) v.findViewById(R.id.question);
                            if (null != question && question.getGlobalVisibleRect(r)) {
                                Global.maskOverlay.drawMask(result, r);
                            }
                            View gameArea = v.findViewById(R.id.gameArea);
                            if (null != gameArea && gameArea.getGlobalVisibleRect(r)) {
                                Global.maskOverlay.drawMask(result, r);
                            }
                            View heartContainer = v.findViewById(R.id.heartContainer);
                            if (null != heartContainer && heartContainer.getGlobalVisibleRect(r)) {
                                Global.maskOverlay.drawMask(result, r);
                            }
                            break;
                        }
                        case 1: {
                            View bottomlinear = v.findViewById(R.id.bottomlinear);
                            if (null != bottomlinear && bottomlinear.getGlobalVisibleRect(r)) {
                                Global.maskOverlay.drawMask(result, r);
                            }
                            break;
                        }
                        case 2: {
                            View gameArea = v.findViewById(R.id.gameArea);
                            if (null != gameArea && gameArea.getGlobalVisibleRect(r)) {
                                Global.maskOverlay.drawMask(result, r);
                            }
                            break;
                        }
                        case 3: {
                            ImageView tips = (ImageView) v.findViewById(R.id.tips);
                            if (null != tips && tips.getGlobalVisibleRect(r)) {
                                Global.maskOverlay.drawMask(result, r);
                            }
                            break;
                        }
                        case 4: {
                            View enter = v.findViewById(R.id.enter);
                            if (null != enter && enter.getGlobalVisibleRect(r)) {
                                Global.maskOverlay.drawMask(result, r);
                            }
                            break;
                        }
                        case 5: {
                            ImageView rorw = (ImageView) v.findViewById(R.id.rorw);
                            if (null != rorw && rorw.getGlobalVisibleRect(r)) {
                                Global.maskOverlay.drawMask(result, r);
                            }
                            Rect groupRect = new Rect();
                            ImageView one = (ImageView) v.findViewById(R.id.one);
                            if (null != one && one.getGlobalVisibleRect(r)) {
                                //Global.maskOverlay.drawMask(result, r);
                                groupRect.union(r);
                            }
                            ImageView two = (ImageView) v.findViewById(R.id.two);
                            if (null != two && two.getGlobalVisibleRect(r)) {
                                //Global.maskOverlay.drawMask(result, r);
                                groupRect.union(r);
                            }
                            ImageView three = (ImageView) v.findViewById(R.id.three);
                            if (null != three && three.getGlobalVisibleRect(r)) {
                                //Global.maskOverlay.drawMask(result, r);
                                groupRect.union(r);
                            }
                            Global.maskOverlay.drawMask(result, groupRect);
                            break;
                        }
                        case 6: {
                            ImageView rorw = (ImageView) v.findViewById(R.id.rorw);
                            if (null != rorw && rorw.getGlobalVisibleRect(r)) {
                                Global.maskOverlay.drawMask(result, r);
                            }
                            Rect groupRect = new Rect();
                            ImageView one = (ImageView) v.findViewById(R.id.one);
                            if (null != one && one.getGlobalVisibleRect(r)) {
                                groupRect.union(r);
                            }
                            ImageView two = (ImageView) v.findViewById(R.id.two);
                            if (null != two && two.getGlobalVisibleRect(r)) {
                                groupRect.union(r);
                            }
                            ImageView three = (ImageView) v.findViewById(R.id.three);
                            if (null != three && three.getGlobalVisibleRect(r)) {
                                groupRect.union(r);
                            }
                            Global.maskOverlay.drawMask(result, groupRect);
                            ImageView wrong = (ImageView) v.findViewById(R.id.wrong);
                            if (null != wrong && wrong.getGlobalVisibleRect(r)) {
                                Global.maskOverlay.drawMask(result, r);
                            }
                            View heartContainer = v.findViewById(R.id.heartContainer);
                            if (null != heartContainer && heartContainer.getGlobalVisibleRect(r)) {
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
