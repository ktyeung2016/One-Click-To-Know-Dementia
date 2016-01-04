package hk.org.jccpa.dementia.game1;

import java.util.ArrayList;
import java.util.Collections;

import hk.org.jccpa.dementia.Global;
import hk.org.jccpa.dementia.Pages;
import hk.org.jccpa.dementia.R;

import com.example.basefamework.fontsize.Preferences;


import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class game_one_1 extends Fragment {
	View v;
	ImageButton back,menu;
	ImageView e1,e2,e3,e4,e5,e6,e7;
	ImageView demo;
	Handler h,hh;
	ImageView h1,h2,h3,h4,h5;
	Runnable r,r2;
	TextView qnum,qnum2;
	Button start;
	LinearLayout lvthree,lvtwo,starlayout,tempa,tempb;
	RelativeLayout lvthree_1;
	ArrayList<ImageView> elementList;
	RelativeLayout numberquestionlayout;
	public void onCreate(Bundle savedInstanceState) {
		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);

		super.onCreate(savedInstanceState);
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);
		v = inflater.inflate(R.layout.activity_gameone_1, container, false);
		back=(ImageButton) v.findViewById(R.id.back);
		menu=(ImageButton) v.findViewById(R.id.menu);
		View holepage=v.findViewById(R.id.holepage);
		Global.gameqnum++;
		starlayout=(LinearLayout) v.findViewById(R.id.starlayout);
		lvtwo=(LinearLayout) v.findViewById(R.id.lvtwo);
		lvthree=(LinearLayout) v.findViewById(R.id.lvthree);
		qnum=(TextView) v.findViewById(R.id.qnum);
		qnum.setText(String.valueOf(Global.gameqnum));
		qnum2=(TextView) v.findViewById(R.id.qnum2);
		qnum2.setText(String.valueOf(Global.gameqnum));
		tempa=(LinearLayout) v.findViewById(R.id.temp1);
		tempb=(LinearLayout) v.findViewById(R.id.temp2);
		h1=(ImageView) v.findViewById(R.id.g1h1);
		h2=(ImageView) v.findViewById(R.id.g1h2);
		h3=(ImageView) v.findViewById(R.id.g1h3);
		h4=(ImageView) v.findViewById(R.id.g1h4);
		h5=(ImageView) v.findViewById(R.id.g1h5);

		demo=(ImageView) v.findViewById(R.id.demo);
		demo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Game1demo.Stage=0;
				Global.onAirAct.pushFragment(Pages.Game1demo);
			}
		});
		heart();
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
				//Global.onAirAct.clearBackStack();
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

		lvthree_1=(RelativeLayout) v.findViewById(R.id.lvthree_1);
		Global.thingtobutlist=new ArrayList<Integer>();
		Global.kolistlist=new ArrayList<Integer>();
		int j=3;
		if(Global.s.getgame1status()==1){
			lvtwo.setVisibility(View.VISIBLE);
			starlayout.setVisibility(View.GONE);
			j=5;
		}else if(Global.s.getgame1status()>1){
			lvtwo.setVisibility(View.VISIBLE);
			starlayout.setVisibility(View.GONE);
			lvthree.setVisibility(View.VISIBLE);
			lvthree_1.setVisibility(View.VISIBLE);
			tempa.setVisibility(View.GONE);
			tempb.setVisibility(View.GONE);
			j=7;
		}
		for(int i=0;i<j;){
			int temp = (int)(Math.random() * (Global.element.length-0)) + 0;
			if(!Global.thingtobutlist.contains(Global.element[temp])){
				Global.thingtobutlist.add(Global.element[temp]);
				i++;
			}

		}

		elementList= new ArrayList<ImageView>();
		elementList.add((ImageView)v.findViewById(R.id.e1));
		elementList.add((ImageView) v.findViewById(R.id.e2));
		elementList.add((ImageView) v.findViewById(R.id.e3));
		elementList.add((ImageView) v.findViewById(R.id.e4));
		elementList.add((ImageView) v.findViewById(R.id.e5));
		elementList.add((ImageView) v.findViewById(R.id.e6));
		elementList.add((ImageView) v.findViewById(R.id.e7));



		for(int i=0;i<j;i++){
			elementList.get(i).setImageResource(Global.thingtobutlist.get(i));
		}
		numberquestionlayout=(RelativeLayout) v.findViewById(R.id.numberquestionlayout);
		numberquestionlayout.setOnClickListener(null);
		h=new Handler();
		r = new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				numberquestionlayout.setVisibility(View.GONE);
			}

		};
		h.postDelayed(r, 1000);

		hh=new Handler();
		r2=new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try{
					Global.onAirAct.pushFragment(Pages.game_one_2);
				}catch(Exception e){
					e.printStackTrace();
				}
			}

		};
		hh.postDelayed(r2, 10000);
		//		start=(Button) v.findViewById(R.id.start);
		//		start.setOnClickListener(new View.OnClickListener() {
		//
		//			@Override
		//			public void onClick(View v) {
		//				// TODO Auto-generated method stub
		//				Global.onAirAct.pushFragment(Pages.game_one_2);
		//			}
		//		});

		//swap
		for(int i=0;i<7;i++){
			int temp1=(int)(Math.random() * (Global.thingtobutlist.size()-0)) + 0;
			int temp2=(int)(Math.random() * (Global.thingtobutlist.size()-0)) + 0;
			Collections.swap(Global.thingtobutlist,temp1,temp2);
		}
		return v;
	}
	public void onPause(){
		super.onPause();
		if(h!=null){
			h.removeCallbacks(r);
		}
		if(hh!=null){
			hh.removeCallbacks(r2);
		}
	}
	public void onDestroy(){
		super.onDestroy();
		
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
}
