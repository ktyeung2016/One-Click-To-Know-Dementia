package hk.org.jccpa.dementia.game1;

import java.util.ArrayList;
import java.util.Collections;

import hk.org.jccpa.dementia.Global;
import hk.org.jccpa.dementia.Pages;
import hk.org.jccpa.dementia.R;
import hk.org.jccpa.dementia.game.UpdateGameData;

import com.example.basefamework.fontsize.Preferences;


import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class game_one_3 extends Fragment {
	View v;
	ImageButton back,menu;
	//	ImageView c1,c2,c3,c4,w1,w2,w3,w4;
	ImageView h1,h2,h3,h4,h5;
	ImageView star1,star2,star3,tips,demo;
	TextView qnum,qnum2;
	ArrayList<Integer> fakeelement;
	Button temp;
	RelativeLayout levelup,tipslayout;
	ArrayList<ImageView> tipsList;
	ArrayList<ImageView> outlist;
	ArrayList<ImageView> anslist;
	ArrayList<ImageView> ansoutlist;
	LinearLayout starlayout,lvthree,lvtwo;

	public void onCreate(Bundle savedInstanceState) {
		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);

		super.onCreate(savedInstanceState);
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);

		if(Global.s.getgame1status()==0){

			v = inflater.inflate(R.layout.gameone_3, container, false);
		}else if(Global.s.getgame1status()==1){
			v = inflater.inflate(R.layout.gameone_3_2, container, false);

		}else if(Global.s.getgame1status()>=2){
			v = inflater.inflate(R.layout.gameone_3_3, container, false);

		}
		back=(ImageButton) v.findViewById(R.id.back);
		menu=(ImageButton) v.findViewById(R.id.menu);
		View holepage=v.findViewById(R.id.holepage);

		anslist= new ArrayList<ImageView>();
		ansoutlist= new ArrayList<ImageView>();



		anslist.add((ImageView) v.findViewById(R.id.c1));
		anslist.add((ImageView) v.findViewById(R.id.c2));
		anslist.add((ImageView) v.findViewById(R.id.c3));
		anslist.add((ImageView) v.findViewById(R.id.c4));
		if(Global.s.getgame1status()>0){
			anslist.add((ImageView) v.findViewById(R.id.a1));
			anslist.add((ImageView) v.findViewById(R.id.a2));
		}
		if(Global.s.getgame1status()>1){
			anslist.add((ImageView) v.findViewById(R.id.b1));
			anslist.add((ImageView) v.findViewById(R.id.b2));
			anslist.add((ImageView) v.findViewById(R.id.b3));
		}
		ansoutlist.add((ImageView) v.findViewById(R.id.w1));
		ansoutlist.add((ImageView) v.findViewById(R.id.w2));
		ansoutlist.add((ImageView) v.findViewById(R.id.w3));
		ansoutlist.add((ImageView) v.findViewById(R.id.w4));
		if(Global.s.getgame1status()>0){
			ansoutlist.add((ImageView) v.findViewById(R.id.aw1));
			ansoutlist.add((ImageView) v.findViewById(R.id.aw2));
		}
		if(Global.s.getgame1status()>1){
			ansoutlist.add((ImageView) v.findViewById(R.id.bw1));
			ansoutlist.add((ImageView) v.findViewById(R.id.bw2));
			ansoutlist.add((ImageView) v.findViewById(R.id.bw3));
		}
		/*
		c1=(ImageView) v.findViewById(R.id.c1);
		c2=(ImageView) v.findViewById(R.id.c2);
		c3=(ImageView) v.findViewById(R.id.c3);
		c4=(ImageView) v.findViewById(R.id.c4);
		w1=(ImageView) v.findViewById(R.id.w1);
		w2=(ImageView) v.findViewById(R.id.w2);
		w3=(ImageView) v.findViewById(R.id.w3);
		w4=(ImageView) v.findViewById(R.id.w4);
		 */
		demo=(ImageView) v.findViewById(R.id.demo);
		demo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Game1demo.Stage=0;
				Global.onAirAct.pushFragment(Pages.Game1demo);
			}
		});
		levelup=(RelativeLayout) v.findViewById(R.id.levelup);
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


		h1=(ImageView) v.findViewById(R.id.g1h1);
		h2=(ImageView) v.findViewById(R.id.g1h2);
		h3=(ImageView) v.findViewById(R.id.g1h3);
		h4=(ImageView) v.findViewById(R.id.g1h4);
		h5=(ImageView) v.findViewById(R.id.g1h5);
		star1=(ImageView) v.findViewById(R.id.star1);
		star2=(ImageView) v.findViewById(R.id.star2);
		star3=(ImageView) v.findViewById(R.id.star3);
		star();
		heart();
		qnum=(TextView) v.findViewById(R.id.qnum);
		qnum.setText(String.valueOf(Global.gameqnum));
		fakeelement=new ArrayList<Integer>();

		int j =3;
		if(Global.s.getgame1status()==1){
			j=5;
		}else if(Global.s.getgame1status()>=2){
			j=8;
		}

		for(int i=0;i<j;){
			int temp=Global.element[(int)(Math.random() * (Global.element.length-0)) + 0];
			if(!Global.thingtobutlist.contains(temp) && !fakeelement.contains(temp)){
				fakeelement.add(temp);
				i++;
			}
		}
		starlayout=(LinearLayout) v.findViewById(R.id.starlayout);
		if(Global.s.getgame1status()>0){
			starlayout.setVisibility(View.GONE);
		}



		//tips part
		tipslayout=(RelativeLayout) v.findViewById(R.id.tipslayout);
		lvthree=(LinearLayout) v.findViewById(R.id.lvthree);
		lvtwo=(LinearLayout) v.findViewById(R.id.lvtwo);
		tips=(ImageView) v.findViewById(R.id.tips);
		tipsList= new ArrayList<ImageView>();
		tipsList.add((ImageView)v.findViewById(R.id.e1));
		tipsList.add((ImageView) v.findViewById(R.id.e2));
		tipsList.add((ImageView) v.findViewById(R.id.e3));
		tipsList.add((ImageView) v.findViewById(R.id.e4));
		tipsList.add((ImageView) v.findViewById(R.id.e5));
		tipsList.add((ImageView) v.findViewById(R.id.e6));
		tipsList.add((ImageView) v.findViewById(R.id.e7));
		outlist= new ArrayList<ImageView>();
		outlist.add((ImageView)v.findViewById(R.id.o1));
		outlist.add((ImageView)v.findViewById(R.id.o2));
		outlist.add((ImageView)v.findViewById(R.id.o3));
		outlist.add((ImageView)v.findViewById(R.id.o4));
		outlist.add((ImageView)v.findViewById(R.id.o5));
		outlist.add((ImageView)v.findViewById(R.id.o6));
		outlist.add((ImageView)v.findViewById(R.id.o7));
		int i=0;
		if(Global.kolistlist.size()!=0){
			for(;i<(Global.kolistlist.size());i++){
				tipsList.get(i).setImageResource(Global.kolistlist.get(i));
				outlist.get(i).setImageResource(R.drawable.gameoneoutline2);
				Log.d("listitem", "i:  "+i+"   listitem:   "+Global.kolistlist.get(i));
			}
		}
		if(Global.thingtobutlist.size()!=0){

			for(;i<(Global.thingtobutlist.size()+Global.kolistlist.size());i++){
				tipsList.get(i).setImageResource(Global.thingtobutlist.get(i-Global.kolistlist.size()));
				Log.d("listitem", "i:  "+i+"   listitem:   "+Global.thingtobutlist.get(i-Global.kolistlist.size()));
			}
		}


		tips.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Global.totalscore-=5;
				tipslayout.setVisibility(View.VISIBLE);
				if(Global.s.getgame1status()==1){
					lvtwo.setVisibility(View.VISIBLE);
				}else if(Global.s.getgame1status()>=2){
					lvtwo.setVisibility(View.VISIBLE);
					lvthree.setVisibility(View.VISIBLE);
				}
				temp= (Button)tipslayout.findViewById(R.id.button1);
				temp.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						tipslayout.setVisibility(View.GONE);
					}

				});
			}
		});

		tipslayout.setOnClickListener(null);
		for(int g=0;g<20;g++){
			int temp1=(int)(Math.random() * (anslist.size()-0)) + 0;
			int temp2=(int)(Math.random() * (anslist.size()-0)) + 0;
			Collections.swap(anslist,temp1,temp2);
			Collections.swap(ansoutlist,temp1,temp2);
		}
		//tipspart
		try{
			for(int k=0;k<anslist.size();k++){
				if(k==0){
					anslist.get(k).setImageResource(Global.thingtobutlist.get(k));
					anslist.get(k).setTag(Global.thingtobutlist.get(k));
					ansoutlist.get(k).setTag(Global.thingtobutlist.get(k));
				}else{
					anslist.get(k).setImageResource(fakeelement.get(k-1));
					anslist.get(k).setTag(fakeelement.get(k-1));
					ansoutlist.get(k).setTag(fakeelement.get(k-1));
				}
			}
			/*if(System.currentTimeMillis()%4==0){
				Log.d("hint", "1");
				c1.setImageResource(Global.thingtobutlist.get(0));
				c2.setImageResource(fakeelement.get(0));
				c3.setImageResource(fakeelement.get(1));
				c4.setImageResource(fakeelement.get(2));
				c1.setTag(Global.thingtobutlist.get(0));
				c2.setTag(fakeelement.get(0));
				c3.setTag(fakeelement.get(1));
				c4.setTag(fakeelement.get(2));
				w1.setTag(Global.thingtobutlist.get(0));
				w2.setTag(fakeelement.get(0));
				w3.setTag(fakeelement.get(1));
				w4.setTag(fakeelement.get(2));
			}else if(System.currentTimeMillis()%4==1){
				Log.d("hint", "2");
				c2.setImageResource(Global.thingtobutlist.get(0));
				c1.setImageResource(fakeelement.get(0));
				c3.setImageResource(fakeelement.get(1));
				c4.setImageResource(fakeelement.get(2));
				c2.setTag(Global.thingtobutlist.get(0));
				c1.setTag(fakeelement.get(0));
				c3.setTag(fakeelement.get(1));
				c4.setTag(fakeelement.get(2));
				w2.setTag(Global.thingtobutlist.get(0));
				w1.setTag(fakeelement.get(0));
				w3.setTag(fakeelement.get(1));
				w4.setTag(fakeelement.get(2));
			}else if(System.currentTimeMillis()%4==2){
				Log.d("hint", "3");
				c3.setImageResource(Global.thingtobutlist.get(0));
				c2.setImageResource(fakeelement.get(0));
				c1.setImageResource(fakeelement.get(1));
				c4.setImageResource(fakeelement.get(2));
				c3.setTag(Global.thingtobutlist.get(0));
				c2.setTag(fakeelement.get(0));
				c1.setTag(fakeelement.get(1));
				c4.setTag(fakeelement.get(2));
				w3.setTag(Global.thingtobutlist.get(0));
				w2.setTag(fakeelement.get(0));
				w1.setTag(fakeelement.get(1));
				w4.setTag(fakeelement.get(2));
			}else{
				Log.d("hint", "4");
				c4.setImageResource(Global.thingtobutlist.get(0));
				c2.setImageResource(fakeelement.get(0));
				c3.setImageResource(fakeelement.get(1));
				c1.setImageResource(fakeelement.get(2));
				c4.setTag(Global.thingtobutlist.get(0));
				c2.setTag(fakeelement.get(0));
				c3.setTag(fakeelement.get(1));
				c1.setTag(fakeelement.get(2));
				w4.setTag(Global.thingtobutlist.get(0));
				w2.setTag(fakeelement.get(0));
				w3.setTag(fakeelement.get(1));
				w1.setTag(fakeelement.get(2));
			}*/
		}catch(Exception e){
			e.printStackTrace();
		}

		View.OnClickListener l=new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("Tag", "remeaning:   "+Global.thingtobutlist.size());
				//				Log.d("Tag", "Global.thingsbought"+v.getTag());
				if(Global.thingtobutlist.size()>0){
					Log.d("Tag", "thingtobutlist > 0");
					Log.d("Tag", "tag1:  "+v.getTag());
					if(Global.thingtobutlist.contains(v.getTag())){
						Log.d("Tag", "same tag");
						//						if(w1.getTag()==v.getTag()){
						//							w1.setImageResource(R.drawable.gameoneoutline2);
						//						}else if(w2.getTag()==v.getTag()){
						//							w2.setImageResource(R.drawable.gameoneoutline2);
						//						}else if(w3.getTag()==v.getTag()){
						//							w3.setImageResource(R.drawable.gameoneoutline2);
						//						}else{
						//							w4.setImageResource(R.drawable.gameoneoutline2);
						//						}
						for(int j=0;j<ansoutlist.size();j++){
							if(ansoutlist.get(j).getTag()==v.getTag()){
								ansoutlist.get(j).setImageResource(R.drawable.gameoneoutline2);
							}
						}
						for(int j=0;j<anslist.size();j++){
							anslist.get(j).setEnabled(false);
						}
						//						c1.setEnabled(false);
						//						c2.setEnabled(false);
						//						c3.setEnabled(false);
						//						c4.setEnabled(false);
						Global.thingsbought++;
						Log.d("Tag", "index:   "+Global.thingtobutlist.indexOf(v.getTag()));
						Global.kolistlist.add(Global.thingtobutlist.get(Global.thingtobutlist.indexOf(v.getTag())));
						Global.thingtobutlist.remove(Global.thingtobutlist.indexOf(v.getTag()));
						if(Global.thingtobutlist.size()==0){
							Global.right5++;
							if(Global.s.getgame1status()==0){
								Global.totalscore=Global.totalscore+10;
							}else if(Global.s.getgame1status()==1){
								Global.totalscore=Global.totalscore+30;
							}else if(Global.s.getgame1status()>=2){
								Global.totalscore=Global.totalscore+50;
							}
							if(Global.right5%10==0 && Global.right5>0 && Global.s.getgame1status()<2 ){
								if(Global.s.getgame1status()>=2){

								}else{
									int temp=Global.s.getgame1status()+1;
									Global.s.game1status(temp);
									levelup.setVisibility(View.VISIBLE);
									levelup.setOnClickListener(null);
									//							Global.onAirAct.pushFragment(Pages.Levelup);
									TextView congmsg=(TextView) levelup.findViewById(R.id.congmsg);
									if(Global.s.getgame1status()==1){
										congmsg.setText(getResources().getString(R.string.congmsg2));
									}else if(Global.s.getgame1status()==2){
                                        congmsg.setText(getResources().getString(R.string.congmsg3));
                                    }
									levelup.findViewById(R.id.enter).setOnClickListener(new View.OnClickListener() {

										@Override
										public void onClick(View v) {
											// TODO Auto-generated method stub
											levelup.setVisibility(View.GONE);
											Global.thingsbought=0;
											Global.onAirAct.pushFragment(Pages.game_one_1);

										}
									});
                                    if(Global.onAirAct.isOnline()){
                                        new UpdateGameData().execute(String.valueOf(Global.s.getgame1status()));
                                    }
									Log.d("game 2 status","temp status: "+Global.s.getgame1status());
								}
							}else{
								Global.thingsbought=0;
								Handler h=new Handler();
								h.postDelayed(new Runnable(){

									@Override
									public void run() {
										// TODO Auto-generated method stub
										Log.d("Tag","YOYOYOYOYO");
										Global.onAirAct.pushFragment(Pages.game_one_1);
									}

								}, 1000);

							}
						}else{
							Handler h=new Handler();
							h.postDelayed(new Runnable(){

								@Override
								public void run() {
									// TODO Auto-generated method stub
									Log.d("Tag","YOYOYOYOYO");
									Global.onAirAct.pushFragment(Pages.game_one_3);
								}

							}, 1000);
						}
						Log.d("Tag", "Global.thingsbought"+Global.thingsbought+"   "+v.getTag());

					}else{
						for(int j=0;j<ansoutlist.size();j++){
							if(ansoutlist.get(j).getTag()==v.getTag()){
								ansoutlist.get(j).setImageResource(R.drawable.gameoneoutline3);
							}
						}
						//						if(w1.getTag()==v.getTag()){
						//							w1.setImageResource(R.drawable.gameoneoutline3);
						//						}else if(w2.getTag()==v.getTag()){
						//							w2.setImageResource(R.drawable.gameoneoutline3);
						//						}else if(w3.getTag()==v.getTag()){
						//							w3.setImageResource(R.drawable.gameoneoutline3);
						//						}else{
						//							w4.setImageResource(R.drawable.gameoneoutline3);
						//						}
						Global.heart--;
						if(Global.heart>0){
                            // send score whenever heartbreak, die not send, end game handled
                            if(Global.onAirAct.isOnline()){
                                new UpdateGameData().execute(String.valueOf(Global.s.getgame1status()));
                            }
						}else{
							Log.d("Tag", "die");
							Global.onAirAct.pushFragment(Pages.Endgame1);
						}
					}
					heart();
				}


			}
			/*if(Global.thingtobutlist.size()==0){
					Global.right5++;
					if(Global.right5%2==0 && Global.right5>0){
						//level up
						if(Global.s.getgame2status()>=2){

						}else{
							int temp=Global.s.getgame1status()+1;
							Global.s.game1status(temp);
							levelup.setVisibility(View.VISIBLE);
							levelup.setOnClickListener(null);
							//							Global.onAirAct.pushFragment(Pages.Levelup);
							TextView congmsg=(TextView) levelup.findViewById(R.id.congmsg);
							if(Global.s.getgame2status()==1){
								congmsg.setText(getResources().getString(R.string.congmsg2));
							}else
								if(Global.s.getgame2status()==2){
									congmsg.setText(getResources().getString(R.string.congmsg3));
								}
							levelup.findViewById(R.id.enter).setOnClickListener(new View.OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									levelup.setVisibility(View.GONE);
									Global.onAirAct.pushFragment(Pages.game_one_1);

								}
							});
							Log.d("game 2 status","temp status: "+Global.s.getgame2status());
						}
					}
				}

				if(Global.heart>0){
					if(Global.thingtobutlist.size()==0){
						Global.thingsbought=0;


						Global.onAirAct.pushFragment(Pages.game_one_1);
					}else{
//						Global.thingsbought=0;
//						Global.onAirAct.pushFragment(Pages.game_one_3);
					}
				}else{

				}*/

			//			}
		};
		for(int x=0;x<anslist.size();x++){
			anslist.get(x).setOnClickListener(l);
		}
		//		c1.setOnClickListener(l);
		//		c2.setOnClickListener(l);
		//		c3.setOnClickListener(l);
		//		c4.setOnClickListener(l);
		return v;
	}
	public void heart(){
		switch(Global.heart){
		case 4:
			h1.setImageResource(R.drawable.icon_emptyheart);
			break;
		case 3:
			h1.setImageResource(R.drawable.icon_emptyheart);
			h2.setImageResource(R.drawable.icon_emptyheart);
			break;
		case 2:
			h1.setImageResource(R.drawable.icon_emptyheart);
			h2.setImageResource(R.drawable.icon_emptyheart);
			h3.setImageResource(R.drawable.icon_emptyheart);
			break;
		case 1:
			h1.setImageResource(R.drawable.icon_emptyheart);
			h2.setImageResource(R.drawable.icon_emptyheart);
			h3.setImageResource(R.drawable.icon_emptyheart);
			h4.setImageResource(R.drawable.icon_emptyheart);
			break;
		case 0:
			h1.setImageResource(R.drawable.icon_emptyheart);
			h2.setImageResource(R.drawable.icon_emptyheart);
			h3.setImageResource(R.drawable.icon_emptyheart);
			h4.setImageResource(R.drawable.icon_emptyheart);
			h5.setImageResource(R.drawable.icon_emptyheart);
			break;

		}
	}
	public void star(){
		switch(Global.thingsbought){
		case 1:
			star1.setImageResource(R.drawable.star);
			break;
		case 2:
			star1.setImageResource(R.drawable.star);
			star2.setImageResource(R.drawable.star);

			break;
		case 3:
			star1.setImageResource(R.drawable.star);
			star2.setImageResource(R.drawable.star);
			star3.setImageResource(R.drawable.star);
			break;
		default:
			break;
		}
	}
}
