package hk.org.jccpa.dementia.game5;

import java.util.Random;

import hk.org.jccpa.dementia.Global;
import hk.org.jccpa.dementia.Pages;
import hk.org.jccpa.dementia.R;

import com.example.basefamework.fontsize.Preferences;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Game_five_2 extends Fragment {
	View v;
	ImageButton back,menu;
	//	int total=0;
	Button enter;
	EditText totalprice;
	LinearLayout setthree;
	ImageView h1,h2,h3,h4,h5,settwoimg,setoneimg,setthreeimg,outline,demo;
	TextView setoneprice,settwoprice,setthreeprice,setoneq,settwoq,setthreeq,qnum2;
	public void onCreate(Bundle savedInstanceState) {
		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);

		super.onCreate(savedInstanceState);
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);
		v = inflater.inflate(R.layout.activity_game_five_2, container, false);

		h1=(ImageView) v.findViewById(R.id.h1);
		h2=(ImageView) v.findViewById(R.id.h2);
		h3=(ImageView) v.findViewById(R.id.h3);
		h4=(ImageView) v.findViewById(R.id.h4);
		h5=(ImageView) v.findViewById(R.id.h5);
		View holepage=v.findViewById(R.id.holepage);
		holepage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		
		outline=(ImageView) v.findViewById(R.id.outline);
		demo=(ImageView) v.findViewById(R.id.demo);
		demo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Gamefivedemo.Stage=0;
				Global.onAirAct.pushFragment(Pages.Gamefivedemo);
			}
		});
		qnum2=(TextView) v.findViewById(R.id.qnum2);
		qnum2.setText(String.valueOf(Global.gameqnum));
		enter=(Button) v.findViewById(R.id.enter);
		totalprice=(EditText) v.findViewById(R.id.totalprice);
		back=(ImageButton) v.findViewById(R.id.back);
		menu=(ImageButton) v.findViewById(R.id.menu);
		setoneprice=(TextView) v.findViewById(R.id.setoneprice);
		settwoprice=(TextView) v.findViewById(R.id.settwoprice);
		setthreeprice=(TextView) v.findViewById(R.id.setthreeprice);

		setoneq=(TextView) v.findViewById(R.id.setoneq);
		settwoq=(TextView) v.findViewById(R.id.settwoq);
		setthreeq=(TextView) v.findViewById(R.id.setthreeq);

		setthree=(LinearLayout) v.findViewById(R.id.setthree);
		setoneimg=(ImageView) v.findViewById(R.id.setoneimg);
		settwoimg=(ImageView) v.findViewById(R.id.settwoimg);
		setthreeimg=(ImageView) v.findViewById(R.id.setthreeimg);
		setoneimg.setImageResource( randpic());
		settwoimg.setImageResource( randpic());
		Random dice = new Random();
		int discount = dice.nextInt();
		if(discount%2!=0){
			setthreeimg.setImageResource( randpic());
		}else{
			setthreeimg.setImageResource(R.drawable.game5_discount);
		}

		if(Global.s.getgame5status()==0){

			Global.setonep=dice.nextInt(8)+1;
			Global.settwop=dice.nextInt(8)+1;
			Global.setthreep=0;
			setthree.setVisibility(View.INVISIBLE);
		}else if(Global.s.getgame5status()==1){
			Global.setonep=dice.nextInt(24)+1;
			Global.settwop=dice.nextInt(24)+1;
			if(discount%2!=0){
				Global.setthreep=dice.nextInt(24)+1;
			}else{
				Global.setthreep=(dice.nextInt(12)+1);
				Global.setthreep*=-1;
			}
		}else{
			Global.setonep=dice.nextInt(49)+1;
			Global.settwop=dice.nextInt(49)+1;
			if(discount%2!=0){
				Global.setthreep=dice.nextInt(49)+1;
				
			}else{
				Global.setthreep=(dice.nextInt(24)+1);
				Global.setthreep*=-1;
			}
		}
		if(Global.s.getgame5status()==0){
			setthree.setVisibility(View.INVISIBLE);
			Global.setoneq=1;
			Global.settwoq=1;
			Global.setthreeq=0;

		}else if(Global.s.getgame5status()==1){
			Global.setoneq=dice.nextInt(2)+1;
			Global.settwoq=dice.nextInt(2)+1;
			if(discount%2!=0){
				Global.setthreeq=dice.nextInt(2)+1;
				
			}else{
				Global.setthreeq=1;
				outline.setVisibility(View.GONE);
			}
			
		}else{
			Global.setoneq=dice.nextInt(8)+1;
			Global.settwoq=dice.nextInt(8)+1;
			if(discount%2!=0){
				Global.setthreeq=dice.nextInt(8)+1;
				
			}else{
				outline.setVisibility(View.GONE);
				Global.setthreeq=1;
			}
			
		}

		for(;Global.game5budget<(Global.setonep*Global.setoneq+Global.settwop*Global.settwoq+Global.setthreep*Global.setthreeq);){
			if(Global.s.getgame5status()==0){

				Global.setonep=dice.nextInt(8)+1;
				Global.settwop=dice.nextInt(8)+1;
				Global.setthreep=0;
				setthree.setVisibility(View.INVISIBLE);
			}else if(Global.s.getgame5status()==1){
				Global.setonep=dice.nextInt(24)+1;
				Global.settwop=dice.nextInt(24)+1;
//				Global.setthreep=dice.nextInt(98)+1;
				if(discount%2!=0){
					Global.setthreep=dice.nextInt(24)+1;
					
				}else{
					outline.setVisibility(View.GONE);
					Global.setthreep=(dice.nextInt(12)+1);
					Global.setthreep*=-1;
				}
			}else{
				Global.setonep=dice.nextInt(49)+1;
				Global.settwop=dice.nextInt(49)+1;
//				Global.setthreep=dice.nextInt(98)+1;
				if(discount%2!=0){
					Global.setthreep=dice.nextInt(49)+1;
					
				}else{
					outline.setVisibility(View.GONE);
					Global.setthreep=(dice.nextInt(24)+1);
					Global.setthreep*=-1;
				}
			}
			if(Global.s.getgame5status()==0){
				setthree.setVisibility(View.INVISIBLE);
				Global.setoneq=1;
				Global.settwoq=1;
				Global.setthreeq=0;

			}else if(Global.s.getgame5status()==1){
				Global.setoneq=dice.nextInt(2)+1;
				Global.settwoq=dice.nextInt(2)+1;
				if(discount%2!=0){
					Global.setthreeq=dice.nextInt(2)+1;
					
				}else{
					Global.setthreeq=1;
					outline.setVisibility(View.GONE);
				}
			}else{
				Global.setoneq=dice.nextInt(8)+1;
				Global.settwoq=dice.nextInt(8)+1;
				if(discount%2!=0){
					Global.setthreeq=dice.nextInt(8)+1;
					
				}else{
					outline.setVisibility(View.GONE);
					Global.setthreeq=1;
				}
			}
		}
		setoneprice.setText("$"+String.valueOf(Global.setonep));
		settwoprice.setText("$"+String.valueOf(Global.settwop));
		setthreeprice.setText("$"+String.valueOf(Global.setthreep));

		//		Global.heart=5;



		setoneq.setText(String.valueOf(Global.setoneq));
		settwoq.setText(String.valueOf(Global.settwoq));
		setthreeq.setText(String.valueOf(Global.setthreeq));

		Global.total=Global.setonep*Global.setoneq+Global.settwop*Global.settwoq+Global.setthreep*Global.setthreeq;
		InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(
				Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(totalprice.getWindowToken(), 0);
		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//				
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
		enter.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("game5", String.valueOf(Global.total));
				if(totalprice.getText().toString().equals(String.valueOf(Global.total))){
					Log.d("game5", "right");
					Global.onAirAct.pushFragment(Pages.Game_five_3);
				}else{

					Global.heart--;
					Toast.makeText(getActivity(), getResources().getString(R.string.wrong), Toast.LENGTH_LONG).show();
					heart();
					if(Global.heart<=0){
						//						Global.onAirAct.pushFragment(Pages.Gamemenu);
						//						Toast.makeText(getActivity(), "Lose", Toast.LENGTH_LONG).show();
						Global.onAirAct.pushFragment(Pages.Endgame5);
					}
				}
			}
		});


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
	public void onResume(){
		super.onResume();
		totalprice.setText("");
		heart();
	}
	public int randpic(){
		Random dice = new Random();
		int a[]={R.drawable.a001,
				R.drawable.	a002,
				R.drawable.a003,
				R.drawable.a004,
				R.drawable.a005,
				R.drawable.a006,
				R.drawable.	a007,
				R.drawable.a008,
				R.drawable.	a009,
				R.drawable.a010,
				R.drawable.	a011,
				R.drawable.a012,
				R.drawable.	a013,
				R.drawable.a014,
				R.drawable.	a015,
				R.drawable.a016,
				R.drawable.	a017,
				R.drawable.a018,
				R.drawable.	a019,
				R.drawable.a020,
				R.drawable.a021,
				R.drawable.a022,
				R.drawable.	a023,
				R.drawable.	a024,
				R.drawable.	a025,
				R.drawable.	a026,
				R.drawable.	a027,
				R.drawable.	a028,
				R.drawable.	a029,
				R.drawable.a030
		};
		return a[dice.nextInt(a.length-1)];
	}



}
