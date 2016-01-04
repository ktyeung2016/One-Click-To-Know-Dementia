package hk.org.jccpa.dementia.game5;

import java.util.Random;

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
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Game_five_1 extends Fragment {
	View v;
	ImageButton back,menu;
	TextView nowmoney,qnum,qnum2;
	ImageView h1,h2,h3,h4,h5,demo;
	RelativeLayout numberquestionlayout;
	Button start;
	public void onCreate(Bundle savedInstanceState) {
		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);

		super.onCreate(savedInstanceState);



	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);
		v = inflater.inflate(R.layout.activity_game_five, container, false);
		back=(ImageButton) v.findViewById(R.id.back);
		menu=(ImageButton) v.findViewById(R.id.menu);
		start=(Button) v.findViewById(R.id.start);
		nowmoney=(TextView) v.findViewById(R.id.nowmoney);
		Random dice = new Random();
		if(Global.s.getgame5status()==0){
			if(Global.game5budget<15){
				Global.game5budget=dice.nextInt(85)+15;
			}
		}else if(Global.s.getgame5status()==1){
			if(Global.game5budget<100){
				Global.game5budget=dice.nextInt(100)+100;
			}
		}else{if(Global.game5budget<200){
			Global.game5budget=dice.nextInt(300)+200;
		}}

		Global.gameqnum++;
		View holepage=v.findViewById(R.id.holepage);
		holepage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		h1=(ImageView) v.findViewById(R.id.h1);
		h2=(ImageView) v.findViewById(R.id.h2);
		h3=(ImageView) v.findViewById(R.id.h3);
		h4=(ImageView) v.findViewById(R.id.h4);
		h5=(ImageView) v.findViewById(R.id.h5);

		qnum=(TextView) v.findViewById(R.id.qnum);
		qnum2=(TextView) v.findViewById(R.id.qnum2);
		qnum.setText(String.valueOf(Global.gameqnum));
		qnum2.setText(String.valueOf(Global.gameqnum));
		numberquestionlayout=(RelativeLayout) v.findViewById(R.id.numberquestionlayout);
		numberquestionlayout.setOnClickListener(null);
		demo=(ImageView) v.findViewById(R.id.demo);
		demo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Gamefivedemo.Stage=0;
				Global.onAirAct.pushFragment(Pages.Gamefivedemo);
			}
		});
		Handler h=new Handler();
		h.postDelayed(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				numberquestionlayout.setVisibility(View.GONE);
			}

		}, 1000);


		nowmoney.setText("$"+String.valueOf(Global.game5budget)+"元");
		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Global.onAirAct.pushFragment(Pages.Gamemenu);
				//				Global.onAirAct.clearOneBackStack();
			}
		});
		menu.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Global.onAirAct.mSlidingMenu.toggleRightDrawer();
			}
		});
		start.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Global.onAirAct.pushFragment(Pages.Game_five_2);
			}
		});


		return v;
	}
	public void onResume(){
		super.onResume();
		//		totalprice.setText("");
		heart();
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
		//		h1=(ImageView) v.findViewById(R.id.h1);
		//		h2=(ImageView) v.findViewById(R.id.h2);
		//		h3=(ImageView) v.findViewById(R.id.h3);
		//		h4=(ImageView) v.findViewById(R.id.h4);
		//		h5=(ImageView) v.findViewById(R.id.h5);
	}
}
