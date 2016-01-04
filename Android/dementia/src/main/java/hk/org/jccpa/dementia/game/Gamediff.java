package hk.org.jccpa.dementia.game;

import hk.org.jccpa.dementia.Global;
import hk.org.jccpa.dementia.R;

import com.example.basefamework.fontsize.Preferences;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class Gamediff extends Fragment {
	View v;
	ImageButton back,menu;
	TextView d1,d2,d3,d4,d5;
	Button b1,b2,b3,b4,b5;
	public void onCreate(Bundle savedInstanceState) {
		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);

		super.onCreate(savedInstanceState);
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);
		v = inflater.inflate(R.layout.activity_gamediff, container, false);
		back=(ImageButton) v.findViewById(R.id.back);
		menu=(ImageButton) v.findViewById(R.id.menu);

		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Global.onAirAct.clearOneBackStack();
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


		d1=(TextView) v.findViewById(R.id.d1);
		d2=(TextView) v.findViewById(R.id.d2);
		d3=(TextView) v.findViewById(R.id.d3);
		d4=(TextView) v.findViewById(R.id.d4);
		d5=(TextView) v.findViewById(R.id.d5);

		if(Global.s.getgame1status()<=0){
			d1.setText(getResources().getString(R.string.diff)+":  "+getResources().getString(R.string.diff1));
		}else if(Global.s.getgame1status()==1){
			d1.setText(getResources().getString(R.string.diff)+":  "+getResources().getString(R.string.diff2));
		}else if(Global.s.getgame1status()>=2){
			d1.setText(getResources().getString(R.string.diff)+":  "+getResources().getString(R.string.diff3));
		}
		b1=(Button) v.findViewById(R.id.b1);
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Global.s.game1status(0);
				if(Global.s.getgame1status()<=0){
					d1.setText(getResources().getString(R.string.diff)+":  "+getResources().getString(R.string.diff1));
				}else if(Global.s.getgame1status()==1){
					d1.setText(getResources().getString(R.string.diff)+":  "+getResources().getString(R.string.diff2));
				}else if(Global.s.getgame1status()>=2){
					d1.setText(getResources().getString(R.string.diff)+":  "+getResources().getString(R.string.diff3));
				}
			}
		});
		
		if(Global.s.getgame2status()<=0){
			d2.setText(getResources().getString(R.string.diff)+":  "+getResources().getString(R.string.diff1));
		}else if(Global.s.getgame2status()==1){
			d2.setText(getResources().getString(R.string.diff)+":  "+getResources().getString(R.string.diff2));
		}else if(Global.s.getgame2status()>=2){
			d2.setText(getResources().getString(R.string.diff)+":  "+getResources().getString(R.string.diff3));
		}
		b2=(Button) v.findViewById(R.id.b2);
		b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Global.s.game2status(0);
				if(Global.s.getgame2status()<=0){
					d2.setText(getResources().getString(R.string.diff)+":  "+getResources().getString(R.string.diff1));
				}else if(Global.s.getgame2status()==1){
					d2.setText(getResources().getString(R.string.diff)+":  "+getResources().getString(R.string.diff2));
				}else if(Global.s.getgame2status()>=2){
					d2.setText(getResources().getString(R.string.diff)+":  "+getResources().getString(R.string.diff3));
				}
			}
		});
//		if(Global.s.getgame3status()<=0){
//			d3.setText(getResources().getString(R.string.diff1));
//		}else if(Global.s.getgame1status()==1){
//			d3.setText(getResources().getString(R.string.diff2));
//		}else if(Global.s.getgame1status()>=2){
//			d3.setText(getResources().getString(R.string.diff3));
//		}
		
		if(Global.s.getgame3status()<=0){
			d3.setText(getResources().getString(R.string.diff)+":  "+getResources().getString(R.string.diff1));
		}else if(Global.s.getgame3status()==1){
			d3.setText(getResources().getString(R.string.diff)+":  "+getResources().getString(R.string.diff2));
		}else if(Global.s.getgame3status()>=2){
			d3.setText(getResources().getString(R.string.diff)+":  "+getResources().getString(R.string.diff3));
		}
		b3=(Button) v.findViewById(R.id.b3);
		b3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Global.s.game3status(0);
				if(Global.s.getgame3status()<=0){
					d3.setText(getResources().getString(R.string.diff)+":  "+getResources().getString(R.string.diff1));
				}else if(Global.s.getgame3status()==1){
					d3.setText(getResources().getString(R.string.diff)+":  "+getResources().getString(R.string.diff2));
				}else if(Global.s.getgame3status()>=2){
					d3.setText(getResources().getString(R.string.diff)+":  "+getResources().getString(R.string.diff3));
				}
			}
		});
		
		if(Global.s.getgame4status()<=0){
			d4.setText(getResources().getString(R.string.diff)+":  "+getResources().getString(R.string.diff1));
		}else if(Global.s.getgame4status()==1){
			d4.setText(getResources().getString(R.string.diff)+":  "+getResources().getString(R.string.diff2));
		}else if(Global.s.getgame4status()>=2){
			d4.setText(getResources().getString(R.string.diff)+":  "+getResources().getString(R.string.diff3));
		}
		b4=(Button) v.findViewById(R.id.b4);
		b4.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Global.s.game4status(0);
				if(Global.s.getgame4status()<=0){
					d4.setText(getResources().getString(R.string.diff)+":  "+getResources().getString(R.string.diff1));
				}else if(Global.s.getgame4status()==1){
					d4.setText(getResources().getString(R.string.diff)+":  "+getResources().getString(R.string.diff2));
				}else if(Global.s.getgame4status()>=2){
					d4.setText(getResources().getString(R.string.diff)+":  "+getResources().getString(R.string.diff3));
				}
			}
		});
		
		
		
		if(Global.s.getgame5status()<=0){
			d5.setText(getResources().getString(R.string.diff)+":  "+getResources().getString(R.string.diff1));
		}else if(Global.s.getgame5status()==1){
			d5.setText(getResources().getString(R.string.diff)+":  "+getResources().getString(R.string.diff2));
		}else if(Global.s.getgame5status()>=2){
			d5.setText(getResources().getString(R.string.diff)+":  "+getResources().getString(R.string.diff3));
		}
		b5=(Button) v.findViewById(R.id.b5);
		b5.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Global.s.game5status(0);
				if(Global.s.getgame5status()<=0){
					d5.setText(getResources().getString(R.string.diff)+":  "+getResources().getString(R.string.diff1));
				}else if(Global.s.getgame5status()==1){
					d5.setText(getResources().getString(R.string.diff)+":  "+getResources().getString(R.string.diff2));
				}else if(Global.s.getgame5status()>=2){
					d5.setText(getResources().getString(R.string.diff)+":  "+getResources().getString(R.string.diff3));
				}
			}
		});
		return v;
	}

}
