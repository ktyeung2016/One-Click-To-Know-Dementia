package hk.org.jccpa.dementia.game5;

import hk.org.jccpa.dementia.Global;
import hk.org.jccpa.dementia.Pages;
import hk.org.jccpa.dementia.R;
import hk.org.jccpa.dementia.game.UpdateGameData;

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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Game_five_3 extends Fragment {
	View v;
	ImageButton back,menu;
	TextView total,qnum2,nowmoney;
	ImageView h1,h2,h3,h4,h5,tips,demo;
	EditText totalprice;
	Button enter;
	
	LinearLayout levelup;
    RelativeLayout tipslayout;
	public void onCreate(Bundle savedInstanceState) {
		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);

		super.onCreate(savedInstanceState);
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);
		v = inflater.inflate(R.layout.activity_game_five_3, container, false);
		back=(ImageButton) v.findViewById(R.id.back);
		menu=(ImageButton) v.findViewById(R.id.menu);
		total=(TextView) v.findViewById(R.id.total);
		enter=(Button) v.findViewById(R.id.enter);
		View holepage=v.findViewById(R.id.holepage);
		holepage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		levelup=(LinearLayout) v.findViewById(R.id.levelup);
		demo=(ImageView) v.findViewById(R.id.demo);
		demo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Gamefivedemo.Stage=0;
				Global.onAirAct.pushFragment(Pages.Gamefivedemo);
			}
		});
		h1=(ImageView) v.findViewById(R.id.h1);
		h2=(ImageView) v.findViewById(R.id.h2);
		h3=(ImageView) v.findViewById(R.id.h3);
		h4=(ImageView) v.findViewById(R.id.h4);
		h5=(ImageView) v.findViewById(R.id.h5);
		qnum2=(TextView) v.findViewById(R.id.qnum2);
		qnum2.setText(String.valueOf(Global.gameqnum));
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
		total.setText(String.valueOf("$"+Global.total));
		totalprice=(EditText) v.findViewById(R.id.totalprice);
		//		EditText myEditText = (EditText) findViewById(R.id.myEditText);  
		InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(
				Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(totalprice.getWindowToken(), 0);
		Log.d("game5", "ans"+(Global.game5budget));



        tipslayout=(RelativeLayout) v.findViewById(R.id.tipslayout);
        nowmoney=(TextView) v.findViewById(R.id.nowmoney);
        tips=(ImageView) v.findViewById(R.id.tips);

        nowmoney.setText("$"+String.valueOf(Global.game5budget)+"元");

        tips.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Global.totalscore-=5;
                tipslayout.setVisibility(View.VISIBLE);
                /*if(Global.s.getgame1status()==1){
                    lvtwo.setVisibility(View.VISIBLE);
                }else if(Global.s.getgame1status()>=2){
                    lvtwo.setVisibility(View.VISIBLE);
                    lvthree.setVisibility(View.VISIBLE);
                }*/
                Button temp= (Button)tipslayout.findViewById(R.id.button1);
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





		enter.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(totalprice.getText().toString().length()>0){


					Log.d("game5", "i am here   "+totalprice.getText().toString());
					// TODO Auto-generated method stub

					Log.d("game5", String.valueOf(Global.game5budget-Integer.valueOf(totalprice.getText().toString())));
					if((Global.game5budget-Global.total)==Integer.valueOf(totalprice.getText().toString())){
						Log.d("game5", "right");
						Global.right5++;

						if(Global.s.getgame5status()==0){
							Global.totalscore=Global.totalscore+10;
						}else if(Global.s.getgame5status()==1){
							Global.totalscore=Global.totalscore+30;
						}else if(Global.s.getgame5status()>=2){
							Global.totalscore=Global.totalscore+50;
						}
						Global.game5budget-=Global.total;
						if(Global.right5 % 10==0){
							
							int temp=Global.s.getgame5status()+1;
							Global.s.game5status(temp);
							if(Global.s.getgame5status()<3){
								levelup.setVisibility(View.VISIBLE);
								levelup.setOnClickListener(null);
//								Global.onAirAct.pushFragment(Pages.Levelup);
								TextView congmsg=(TextView) levelup.findViewById(R.id.congmsg);
								if(Global.s.getgame5status()==1){
									congmsg.setText(getResources().getString(R.string.congmsg2));
								}else
								if(Global.s.getgame5status()==2){
									congmsg.setText(getResources().getString(R.string.congmsg3));
								}
								levelup.findViewById(R.id.enter).setOnClickListener(new View.OnClickListener() {
									
									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										levelup.setVisibility(View.GONE);
										Global.onAirAct.pushFragment(Pages.Game_five_1);
										
									}
								});
                                if(Global.onAirAct.isOnline()){
                                    new UpdateGameData().execute(String.valueOf(Global.s.getgame5status()));
                                }
							}
						}else{
							Global.onAirAct.pushFragment(Pages.Game_five_1);
						}
					}else{
						Global.heart--;
						Toast.makeText(getActivity(), getResources().getString(R.string.wrong), Toast.LENGTH_LONG).show();
						heart();
						if(Global.heart<=0){
//							addtodb();
							Global.onAirAct.pushFragment(Pages.Endgame5);
//							Toast.makeText(getActivity(), "Lose", Toast.LENGTH_LONG).show();
							
						}else{
                            if(Global.onAirAct.isOnline()){
                                new UpdateGameData().execute(String.valueOf(Global.s.getgame5status()));
                            }
                        }
					}
				}
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
