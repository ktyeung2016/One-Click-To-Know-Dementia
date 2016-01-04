package hk.org.jccpa.dementia.game;

import hk.org.jccpa.dementia.Global;
import hk.org.jccpa.dementia.Pages;
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

public class Levelup extends Fragment {
	View v;
	ImageButton back,menu;
	TextView congmsg;
	Button enter;
	public void onCreate(Bundle savedInstanceState) {
		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);

		super.onCreate(savedInstanceState);
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);
		v = inflater.inflate(R.layout.activity_levelup, container, false);

		View holepage=v.findViewById(R.id.holepage);
		holepage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		enter=(Button) v.findViewById(R.id.enter);
		enter.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Global.onAirAct.pushFragment(Pages.Game_five_1);
			}
		});
		congmsg=(TextView) v.findViewById(R.id.congmsg);
		if(Global.s.getgame5status()==1){
			congmsg.setText(getResources().getString(R.string.congmsg2));
		}else
		if(Global.s.getgame5status()==2){
			congmsg.setText(getResources().getString(R.string.congmsg3));
		}
		return v;
	}

}
