package hk.org.jccpa.dementia.game4;

import java.util.ArrayList;

import hk.org.jccpa.dementia.Global;
import hk.org.jccpa.dementia.Pages;
import hk.org.jccpa.dementia.R;
import hk.org.jccpa.dementia.game.UpdateGameData;

import com.example.basefamework.fontsize.Preferences;


import android.app.Fragment;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Game_four_1 extends Fragment {
	View v;
	ImageButton back,menu;
	Button enter;
	ArrayList<ImageView> viewList; 
	ImageView a1,a2,a3,b1,b2,b3,c1,c2,c3,tips,demo;
	//	View.OnClickListener click;
	RelativeLayout levelup,numberquestionlayout;
	boolean go =false;
	TextView qnum,qnum2;
	boolean rightorwrong = true;
	ImageView g4h1,g4h2,g4h3,g4h4,g4h5,rorw;

	int s[]={1,1,1,1,0,0,0,0,0};
	int s1[]={0,0,0,0,0,0,0,0,0};


	int t[]={1,1,1,1,1,2,0,0,0,0,0,0};
	int t1[]={0,0,0,0,0,0,0,0,0,0,0,0};


	int u[]={1,1,1,1,1,1,1,2,2,0,0,0};
	int u1[]={0,0,0,0,0,0,0,0,0,0,0,0};

	public void onCreate(Bundle savedInstanceState) {
		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);

		super.onCreate(savedInstanceState);
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);
		Global.gameqnum++;
		if(Global.s.getgame4status()<=0){
			v = inflater.inflate(R.layout.activity_game_four_1, container, false);
		}else{
			v = inflater.inflate(R.layout.activity_game_four_2, container, false);
		}

		back=(ImageButton) v.findViewById(R.id.back);
		menu=(ImageButton) v.findViewById(R.id.menu);
		View holepage=v.findViewById(R.id.holepage);
		holepage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Global.onAirAct.clearBackStack();
				Global.onAirAct.pushFragment(Pages.Gamemenu);
			}
		});
		menu.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Global.onAirAct.mSlidingMenu.toggleRightDrawer();
			}
		});

		rorw=(ImageView) v.findViewById(R.id.rorwg4);
		viewList=new ArrayList<ImageView>();
		viewList.add((ImageView) v.findViewById(R.id.a1));
		viewList.add((ImageView) v.findViewById(R.id.a2));
		viewList.add((ImageView) v.findViewById(R.id.a3));
		viewList.add((ImageView) v.findViewById(R.id.b1));
		viewList.add((ImageView) v.findViewById(R.id.b2));
		viewList.add((ImageView) v.findViewById(R.id.b3));
		viewList.add((ImageView) v.findViewById(R.id.c1));
		viewList.add((ImageView) v.findViewById(R.id.c2));
		viewList.add((ImageView) v.findViewById(R.id.c3));
		if(Global.s.getgame4status()>0){
			viewList.add((ImageView) v.findViewById(R.id.a4));
			viewList.add((ImageView) v.findViewById(R.id.b4));
			viewList.add((ImageView) v.findViewById(R.id.c4));	
		}
		g4h1=(ImageView) v.findViewById(R.id.g4h1);
		g4h2=(ImageView) v.findViewById(R.id.g4h2);
		g4h3=(ImageView) v.findViewById(R.id.g4h3);
		g4h4=(ImageView) v.findViewById(R.id.g4h4);
		g4h5=(ImageView) v.findViewById(R.id.g4h5);

		demo=(ImageView) v.findViewById(R.id.demo);
		demo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				game4demo.Stage=0;
				Global.onAirAct.pushFragment(Pages.game4demo);
			}
		});
		levelup=(RelativeLayout) v.findViewById(R.id.levelupg4);

		if(Global.s.getgame4status()==0){
			for(int i=0;i<6;i++){
				int temp1 = (int)(Math.random() * (s.length-1-0)) + 0;
				int temp2 = (int)(Math.random() * (s.length-1-0)) + 0;
				int temp = s[temp1];
				s[temp1]= s[temp2];
				s[temp2]=temp;
			}
		}else if(Global.s.getgame4status()==1){
			for(int i=0;i<9;i++){
				int temp1 = (int)(Math.random() * (t.length-1-0)) + 0;
				int temp2 = (int)(Math.random() * (t.length-1-0)) + 0;
				int temp = t[temp1];
				t[temp1]= t[temp2];
				t[temp2]=temp;
			}
		}else{
			for(int i=0;i<9;i++){
				int temp1 = (int)(Math.random() * (u.length-1-0)) + 0;
				int temp2 = (int)(Math.random() * (u.length-1-0)) + 0;
				int temp = u[temp1];
				u[temp1]= u[temp2];
				u[temp2]=temp;
			}
		}

		//		click= new View.OnClickListener() {
		//
		//			@Override
		//			public void onClick(View v) {
		//				Log.d("Click","i am here");
		//				int temp= Integer.valueOf((String) v.getTag());
		//				// TODO Auto-generated method stub
		//				if(s1[temp]==1){
		//					viewList.get(temp).setImageResource(R.drawable.game4element2);
		//					s1[temp]=0;
		//				}else{
		//					viewList.get(temp).setImageResource(R.drawable.game4element);
		//					s1[temp]=1;
		//				}
		//
		//			}
		//		};

		for(int i = 0 ;i<viewList.size();i++){
			viewList.get(i).setTag(i);
			Log.d("Click","i am here"  +  "tag:   "+String.valueOf(viewList.get(i).getTag())+"     "+ viewList.size() );


			if(Global.s.getgame4status()==0){
				if(s[i]==0){
					viewList.get(i).setImageResource(R.drawable.game4element2);
				}else{
					viewList.get(i).setImageResource(R.drawable.game4element);
				}
			}else if(Global.s.getgame4status()==1){
				if(t[i]==0){
					viewList.get(i).setImageResource(R.drawable.game4element_a2);
				}else if(t[i]==2){
					viewList.get(i).setImageResource(R.drawable.game4element_a);
					viewList.get(i).setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
				}else{
					viewList.get(i).setImageResource(R.drawable.game4element_a);
				}
			}else{
				if(u[i]==0){
					viewList.get(i).setImageResource(R.drawable.game4element_a2);
				}else if(u[i]==2){
					viewList.get(i).setImageResource(R.drawable.game4element_a);
					viewList.get(i).setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
				}else{
					viewList.get(i).setImageResource(R.drawable.game4element_a);
				}
			}
		}



		//		Handler h= new Handler();
		//		h.postDelayed(new Runnable(){
		//
		//			@Override
		//			public void run() {
		//				// TODO Auto-generated method stub
		//				for(int i = 0 ;i<viewList.size();i++){
		//					viewList.get(i).setOnClickListener(click);
		//					viewList.get(i).setVisibility(View.INVISIBLE);
		//				}
		//			}
		//
		//		}, 5000);

		tips=(ImageView) v.findViewById(R.id.tips);
		tips.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Global.totalscore-=5;
				go=false;
				enter.setText(R.string.gamestart);
				if(Global.s.getgame4status()==0){
					for(int i=0;i < s1.length; i++){
						s1[i]=0;
					}
				}else if(Global.s.getgame4status()==1){
					for(int i=0;i < t1.length; i++){
						t1[i]=0;
					}
				}else{
					for(int i=0;i < u1.length; i++){
						u1[i]=0;
					}
				}
				for(int i = 0 ;i<viewList.size();i++){
					viewList.get(i).setTag(i);
					viewList.get(i).setOnClickListener(null);
					Log.d("Click","i am here"  +  "tag:   "+String.valueOf(viewList.get(i).getTag())+"     "+ viewList.size() );


					if(Global.s.getgame4status()==0){
						if(s[i]==0){
							viewList.get(i).setImageResource(R.drawable.game4element2);
						}else{
							viewList.get(i).setImageResource(R.drawable.game4element);
						}
					}else if(Global.s.getgame4status()==1){
						if(t[i]==0){
							viewList.get(i).setImageResource(R.drawable.game4element_a2);
						}else if(t[i]==2){
							viewList.get(i).setImageResource(R.drawable.game4element_a);
							viewList.get(i).setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
						}else{
							viewList.get(i).setImageResource(R.drawable.game4element_a);
						}
					}else{
						if(u[i]==0){
							viewList.get(i).setImageResource(R.drawable.game4element_a2);
						}else if(u[i]==2){
							viewList.get(i).setImageResource(R.drawable.game4element_a);
							viewList.get(i).setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
						}else{
							viewList.get(i).setImageResource(R.drawable.game4element_a);
						}
					}
				}
			}
		});
		enter=(Button) v.findViewById(R.id.enter);
		enter.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(go==false){
					go=true;
					for(int i = 0;i<viewList.size();i++){
						Log.d("Click","i am here");
						viewList.get(i).setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								Log.d("Click","i am here");
								int temp= (Integer) v.getTag();
								// TODO Auto-generated method stub



								if(Global.s.getgame4status()==0){
									if(s1[temp]==1){
										viewList.get(temp).setImageResource(R.drawable.game4element2);
										s1[temp]=0;
									}else{
										viewList.get(temp).setImageResource(R.drawable.game4element);
										s1[temp]=1;
									}
								}else if(Global.s.getgame4status()==1){
									if(t1[temp]==1){
										viewList.get(temp).setImageResource(R.drawable.game4element2);
										t1[temp]=0;
									}else{
										viewList.get(temp).setImageResource(R.drawable.game4element);
										t1[temp]=1;
									}
								}else{
									if(u1[temp]==1){
										viewList.get(temp).setImageResource(R.drawable.game4element2);
										u1[temp]=0;
									}else{
										viewList.get(temp).setImageResource(R.drawable.game4element);
										u1[temp]=1;
									}
								}

							}
						});
						enter.setText(R.string.enter);
						viewList.get(i).setImageResource(R.drawable.game4element2);//.setVisibility(View.INVISIBLE);
						viewList.get(i).setClickable(true);
						viewList.get(i).clearColorFilter();
					}

				}else{
					boolean nowrong=true;

					for(int i = 0;i<s.length;i++){
						if(Global.s.getgame4status()==0){
							if(s[i]==s1[i] ){

								//							Global.onAirAct.pushFragment(Pages.Game_four_1);
							}else{
								Global.heart--;
								heart();
								rightorwrong=false;
								nowrong=false;
								if(Global.heart>0){
									//									Global.onAirAct.pushFragment(Pages.Game_four_1);
								}else{

								}
								break;

							}
						}else if(Global.s.getgame4status()==1){
							if(t[i]==t1[i] || (t1[i]+2)==t[i]){

								//							Global.onAirAct.pushFragment(Pages.Game_four_1);
							}else{
								Global.heart--;
								heart();
								rightorwrong=false;
								nowrong=false;
								if(Global.heart>0){
									//									Global.onAirAct.pushFragment(Pages.Game_four_1);
								}else{

								}
								break;

							}
						}else{
							if(u[i]==u1[i]|| (u1[i]+2)==u[i]){

								//							Global.onAirAct.pushFragment(Pages.Game_four_1);
							}else{
								Global.heart--;
								heart();
								rightorwrong=false;
								nowrong=false;
								//								if(Global.heart>0){
								//									Global.onAirAct.pushFragment(Pages.Game_four_1);
								//								}else{
								//									Global.onAirAct.pushFragment(Pages.Endgame4);
								//								}
								break;

							}
						}




					}

					if(nowrong==true){
						Global.right5++;
						if(Global.s.getgame4status()==0){
							Global.totalscore=Global.totalscore+10;
						}else if(Global.s.getgame4status()==1){
							Global.totalscore=Global.totalscore+30;
						}else if(Global.s.getgame4status()>=2){
							Global.totalscore=Global.totalscore+50;
						}
						if(Global.right5%10==0){

							if(Global.s.getgame4status()>=2){
								rorw.bringToFront();
								rorw.setVisibility(View.VISIBLE);
								Handler h =new Handler();
								h.postDelayed(new Runnable(){

									@Override
									public void run() {
										// TODO Auto-generated method stub
										Global.onAirAct.pushFragment(Pages.Game_four_1);
									}

								}, 500);
							}else{
								int temp=Global.s.getgame4status()+1;
								Global.s.game4status(temp);
								levelup.setVisibility(View.VISIBLE);
								levelup.setOnClickListener(null);
								//							Global.onAirAct.pushFragment(Pages.Levelup);
								TextView congmsg=(TextView) levelup.findViewById(R.id.congmsg1);
								if(Global.s.getgame4status()==1){
									congmsg.setText(getResources().getString(R.string.congmsg2));
								}else
									if(Global.s.getgame4status()==2){
										congmsg.setText(getResources().getString(R.string.congmsg3));
									}
								levelup.findViewById(R.id.enter2).setOnClickListener(new View.OnClickListener() {

									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										levelup.setVisibility(View.GONE);
										Global.onAirAct.pushFragment(Pages.Game_four_1);

									}
								});
                                if(Global.onAirAct.isOnline()){
                                    new UpdateGameData().execute(String.valueOf(Global.s.getgame4status()));
                                }
								Log.d("game 4 status","temp status: "+Global.s.getgame4status());
							}
						}
						else{
							//							rorw.setImageResource(R.drawable.cross);
							//							rorw.bringToFront();
							//							rorw.setVisibility(View.VISIBLE);
							rorw.bringToFront();
							rorw.setVisibility(View.VISIBLE);
							Handler h =new Handler();
							h.postDelayed(new Runnable(){

								@Override
								public void run() {
									// TODO Auto-generated method stub
									Global.onAirAct.pushFragment(Pages.Game_four_1);
								}

							}, 500);


						}
						//					go=false;
					}else {
						if(Global.heart>0){
                            if(Global.onAirAct.isOnline()){
                                new UpdateGameData().execute(String.valueOf(Global.s.getgame4status()));
                            }
							//							Handler h =new Handler();
							//							h.postDelayed(new Runnable(){
							//
							//								@Override
							//								public void run() {
							//									// TODO Auto-generated method stub
							//									Global.onAirAct.pushFragment(Pages.Game_four_1);
							//								}
							//								
							//							}, 5000);
						}else{
							Global.onAirAct.pushFragment(Pages.Endgame4);
						}
					}
				}

			}

		});

		qnum=(TextView) v.findViewById(R.id.qnum);
		qnum.setText(String.valueOf(Global.gameqnum));


		qnum2=(TextView) v.findViewById(R.id.qnum2);
		qnum2.setText(String.valueOf(Global.gameqnum));
		numberquestionlayout=(RelativeLayout) v.findViewById(R.id.numberquestionlayout123);
		numberquestionlayout.setOnClickListener(null);
		Handler h1=new Handler();
		h1.postDelayed(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				numberquestionlayout.setVisibility(View.GONE);
			}

		}, 1000);

		return v;
	}
	public void onResume(){
		super.onResume();
		heart();

	}
	public void heart(){
		switch(Global.heart){
		case 4:
			g4h1.setImageResource(R.drawable.icon_emptyheart);
			break;
		case 3:
			g4h1.setImageResource(R.drawable.icon_emptyheart);
			g4h2.setImageResource(R.drawable.icon_emptyheart);
			break;
		case 2:
			g4h1.setImageResource(R.drawable.icon_emptyheart);
			g4h2.setImageResource(R.drawable.icon_emptyheart);
			g4h3.setImageResource(R.drawable.icon_emptyheart);
			break;
		case 1:
			g4h1.setImageResource(R.drawable.icon_emptyheart);
			g4h2.setImageResource(R.drawable.icon_emptyheart);
			g4h3.setImageResource(R.drawable.icon_emptyheart);
			g4h4.setImageResource(R.drawable.icon_emptyheart);
			break;
		case 0:
			g4h1.setImageResource(R.drawable.icon_emptyheart);
			g4h2.setImageResource(R.drawable.icon_emptyheart);
			g4h3.setImageResource(R.drawable.icon_emptyheart);
			g4h4.setImageResource(R.drawable.icon_emptyheart);
			g4h5.setImageResource(R.drawable.icon_emptyheart);
			break;

		}
	}
}
