package hk.org.jccpa.dementia.game1;

import java.util.Calendar;

import hk.org.jccpa.dementia.Global;
import hk.org.jccpa.dementia.Pages;
import hk.org.jccpa.dementia.R;

import com.example.basefamework.fontsize.Preferences;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class game_one_2 extends Fragment {
	View v;
	ImageButton back,menu;
	TextView ans1,ans2,ans3,ans4;
	TextView qnum,qnum2;
	
	ImageView h1,h2,h3,h4,h5,demo;
	LinearLayout choicea,choiceb,choicec,choiced,starlayout;
	public void onCreate(Bundle savedInstanceState) {
		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);

		super.onCreate(savedInstanceState);
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);
		v = inflater.inflate(R.layout.game_one_2, container, false);
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

		demo=(ImageView) v.findViewById(R.id.demo);
		demo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Game1demo.Stage=0;
				Global.onAirAct.pushFragment(Pages.Game1demo);
			}
		});
		String weekdat[]=getResources().getStringArray(R.array.fakeans);
		ans1=(TextView) v.findViewById(R.id.ans1);
		ans2=(TextView) v.findViewById(R.id.ans2);
		ans3=(TextView) v.findViewById(R.id.ans3);
		ans4=(TextView) v.findViewById(R.id.ans4);

		h1=(ImageView) v.findViewById(R.id.g1h1);
		h2=(ImageView) v.findViewById(R.id.g1h2);
		h3=(ImageView) v.findViewById(R.id.g1h3);
		h4=(ImageView) v.findViewById(R.id.g1h4);
		h5=(ImageView) v.findViewById(R.id.g1h5);
		//
		//		int temp = (int)(Math.random()* 10)%5;  
		//		if(temp==0){
		//			ans1.setText(weekdat[0]); 
		//			ans2.setText(weekdat[2]); 
		//			ans3.setText(weekdat[3]);
		//			ans4.setText(weekdat[5]); 
		//
		//		}else if(temp==1){
		//			ans1.setText(weekdat[2]); 
		//			ans2.setText(weekdat[6]); 
		//			ans3.setText(weekdat[4]);
		//			ans4.setText(weekdat[0]); 
		//		}else if(temp==2){
		//			ans1.setText(weekdat[3]); 
		//			ans2.setText(weekdat[4]); 
		//			ans3.setText(weekdat[5]);
		//			ans4.setText(weekdat[1]); 
		//		}
		//		else if(temp==3){
		//			ans1.setText(weekdat[1]); 
		//			ans2.setText(weekdat[4]); 
		//			ans3.setText(weekdat[0]);
		//			ans4.setText(weekdat[2]); 
		//		}else{
		//			ans1.setText(weekdat[3]); 
		//			ans2.setText(weekdat[6]); 
		//			ans3.setText(weekdat[5]);
		//			ans4.setText(weekdat[4]); 
		//		}
		Calendar c = Calendar.getInstance();
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);                
		int weekDay=1;
		if (Calendar.MONDAY == dayOfWeek) weekDay = 0;
		else if (Calendar.TUESDAY == dayOfWeek) weekDay = 1;
		else if (Calendar.WEDNESDAY == dayOfWeek) weekDay = 2;
		else if (Calendar.THURSDAY == dayOfWeek) weekDay = 3;
		else if (Calendar.FRIDAY == dayOfWeek) weekDay = 4;
		else if (Calendar.SATURDAY == dayOfWeek) weekDay = 5;
		else if (Calendar.SUNDAY == dayOfWeek) weekDay = 6;
		String s[]=getResources().getStringArray(R.array.fakeans);
		if(weekDay==0){
			ans1.setText(s[weekDay]);
			ans2.setText(s[2]); 
			ans3.setText(s[3]);
			ans4.setText(s[5]);
		}else if(weekDay==1){
			ans1.setText(s[6]);
			ans2.setText(s[weekDay]);
			ans3.setText(s[3]);
			ans4.setText(s[5]);
		}else if(weekDay==2){
			ans1.setText(s[6]);
			ans3.setText(s[weekDay]);
			ans2.setText(s[3]);
			ans4.setText(s[4]);
		}else if(weekDay==3){
			ans1.setText(s[6]);
			ans3.setText(s[1]);
			ans2.setText(s[2]);
			ans4.setText(s[weekDay]);
		}else if(weekDay==4){
			ans1.setText(s[weekDay]);
			ans2.setText(s[2]); 
			ans3.setText(s[3]);
			ans4.setText(s[5]);
		}else if(weekDay==5){
			ans1.setText(s[6]);
			ans2.setText(s[weekDay]);
			ans3.setText(s[3]);
			ans4.setText(s[1]);
		}else if(weekDay==6){
			ans1.setText(s[5]);
			ans2.setText(s[2]);
			ans3.setText(s[weekDay]);
			ans4.setText(s[4]);
		}
		starlayout=(LinearLayout) v.findViewById(R.id.starlayout);
		qnum=(TextView) v.findViewById(R.id.qnum);
		qnum.setText(String.valueOf(Global.gameqnum));



		View.OnClickListener listener=new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Global.onAirAct.pushFragment(Pages.game_one_3);
			}
		};


		choicea=(LinearLayout) v.findViewById(R.id.choicea);
		choiceb=(LinearLayout) v.findViewById(R.id.choiceb);
		choicec=(LinearLayout) v.findViewById(R.id.choicec);
		choiced=(LinearLayout) v.findViewById(R.id.choiced);
		choicea.setOnClickListener(listener);
		choiceb.setOnClickListener(listener);
		choicec.setOnClickListener(listener);
		choiced.setOnClickListener(listener);
		if(Global.s.getgame1status()>0){
			starlayout.setVisibility(View.GONE);
		}
		return v;
	}
	public void onResume(){
		super.onResume();
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
	}
}
