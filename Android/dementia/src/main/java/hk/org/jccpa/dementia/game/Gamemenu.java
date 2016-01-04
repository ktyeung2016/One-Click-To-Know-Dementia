package hk.org.jccpa.dementia.game;

import java.io.File;


import hk.org.jccpa.dementia.Global;
import hk.org.jccpa.dementia.Pages;
import hk.org.jccpa.dementia.R;
import hk.org.jccpa.dementia.game1.Game1demo;
import hk.org.jccpa.dementia.game2.Game2demo;
import hk.org.jccpa.dementia.game3.game3demo;
import hk.org.jccpa.dementia.game4.game4demo;
import hk.org.jccpa.dementia.game5.Gamefivedemo;

import com.example.basefamework.fontsize.Preferences;


import android.app.Fragment;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

public class Gamemenu extends Fragment {
	View v;
	ImageButton back,menu;
	Button mindcal,rank,cancel,puzzle,memory,buyer,findroot;

	public void onCreate(Bundle savedInstanceState) {

		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);
		super.onCreate(savedInstanceState);
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);
		Global.whichpage=Pages.Gamemenu;
		v = inflater.inflate(R.layout.activity_gamemenu, container, false);
		back=(ImageButton) v.findViewById(R.id.back);
		menu=(ImageButton) v.findViewById(R.id.menu);
		rank=(Button) v.findViewById(R.id.rank);
		memory=(Button) v.findViewById(R.id.memory);
		mindcal=(Button) v.findViewById(R.id.mindcal);
		cancel=(Button) v.findViewById(R.id.delete);
		puzzle=(Button) v.findViewById(R.id.puzzle);
		buyer=(Button) v.findViewById(R.id.buyer);
		findroot=(Button) v.findViewById(R.id.findroot);
		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//				Global.onAirAct.clearOneBackStack();
				Global.onAirAct.clearBackStack();
				Global.onAirAct.pushFragment(Pages.Homepage1);
			}
		});
		menu.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Global.onAirAct.mSlidingMenu.toggleRightDrawer();
			}
		});
		View holepage=v.findViewById(R.id.holepage);
		holepage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		buyer.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Global.heart=5;
				Global.right5=0;
				Global.gameqnum=0;
				Global.gamenum=1;
				Global.thingsbought=0;
				Game1demo.Stage=0;
				Global.totalscore=100;
				//				Global.s.game1status(-1);
				Global.star=0;
				if(Global.s.getgame1status()==-1){
					Global.drqweropen=false;
					Global.onAirAct.pushFragment(Pages.Game1demo);
				}else{
                    if(Global.onAirAct.isOnline()){
                        new UpdateGameData().execute(String.valueOf(Global.s.getgame1status()));
                    }
					Global.onAirAct.pushFragment(Pages.game_one_1);
				}
			}
		});
		rank.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Global.onAirAct.pushFragment(Pages.Gamerkmenu);
			}
		});
		mindcal.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//				Global.totalscore=0;
				Global.heart=5;
				Global.right5=0;
				Global.gameqnum=0;
				Global.game5budget=0;
				Global.gamenum=5;
				Gamefivedemo.Stage=0;
				Global.totalscore=100;
				//				Global.s.game5status(-1);
				if(Global.s.getgame5status()==-1){
					Global.drqweropen=false;
					Global.onAirAct.pushFragment(Pages.Gamefivedemo);
				}else{
                    if(Global.onAirAct.isOnline()){
                        new UpdateGameData().execute(String.valueOf(Global.s.getgame5status()));
                    }
					Global.onAirAct.pushFragment(Pages.Game_five_1);
				}
			}
		});
		puzzle.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Global.heart=5;
				Global.right5=0;
				Global.gameqnum=0;
				Global.gamenum=2;
				Game2demo.Stage=0;
				Global.totalscore=100;
				//				Global.s.game2status(-1);
				if(Global.s.getgame2status()==-1){
					Global.drqweropen=false;
					Global.onAirAct.pushFragment(Pages.Game2demo);
				}else{
                    if(Global.onAirAct.isOnline()){
                        new UpdateGameData().execute(String.valueOf(Global.s.getgame2status()));
                    }
					Global.onAirAct.pushFragment(Pages.Game_two_1);
				}

				//				Global.onAirAct.pushFragment(Pages.Game_two_1);
			}
		});
		memory.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Global.heart=5;
				Global.right5=0;
				game4demo.Stage=0;
				Global.gameqnum=0;
				Global.gamenum=4;
				Global.totalscore=100;
				//				Global.s.game4status(-1);
				Log.d("memory", "memory game");
				if(Global.s.getgame4status()==-1){
					Global.drqweropen=false;
					Global.onAirAct.pushFragment(Pages.game4demo);
				}
				else {
                    if(Global.onAirAct.isOnline()){
                        new UpdateGameData().execute(String.valueOf(Global.s.getgame4status()));
                    }
					Global.onAirAct.pushFragment(Pages.Game_four_1);
				}
			}
		});
		findroot.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String PATH = Environment.getExternalStorageDirectory()+"/.jcfloder/game";
				File f = new File(PATH);
				Global.heart=5;
				Global.right5=0;
				Global.gameqnum=0;
				Global.gamenum=3;
				game3demo.Stage=0;
				Global.totalscore=100;
				//					  File f = new File(Environment.getExternalStorageDirectory() + "/somedir");
				if(f.isDirectory()) { 
				
//					Global.s.game3status(-1);
					if(Global.s.getgame3status()==-1){
						Global.drqweropen=false;
						Global.onAirAct.pushFragment(Pages.game3demo);
					}
					else{
                        if(Global.onAirAct.isOnline()){
                            new UpdateGameData().execute(String.valueOf(Global.s.getgame3status()));
                        }
						Global.onAirAct.pushFragment(Pages.Game_three_1);
					}
				}
			}
		});
		cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Global.onAirAct.pushFragment(Pages.Gamediff);
			}
		});
		return v;
	}

}
