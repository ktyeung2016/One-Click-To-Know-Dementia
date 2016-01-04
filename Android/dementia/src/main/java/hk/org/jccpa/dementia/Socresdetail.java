package hk.org.jccpa.dementia;

import com.example.basefamework.fontsize.Preferences;


import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Socresdetail extends Fragment {
	View v;
	ImageButton back,menu;
	Button web;
	TextView org,s1name,s2name,area,phone,remark;
	ImageView lv1,lv2,lv3,lv4,lvfmy,lvhelper;
	LinearLayout phonelayout;
	public void onCreate(Bundle savedInstanceState) {
		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);

		super.onCreate(savedInstanceState);
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);
		v = inflater.inflate(R.layout.socresdetail, container, false);
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
				Global.onAirAct.pushFragment(Pages.Socres);
			}
		});
		menu.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Global.onAirAct.mSlidingMenu.toggleRightDrawer();
			}
		});
		lv1=(ImageView) v.findViewById(R.id.lv1);
		lv2=(ImageView) v.findViewById(R.id.lv2);
		lv3=(ImageView) v.findViewById(R.id.lv3);
		lv4=(ImageView) v.findViewById(R.id.lv4);
		lvfmy=(ImageView) v.findViewById(R.id.lvfmy);
		lvhelper=(ImageView) v.findViewById(R.id.lvhelper);

		org=(TextView) v.findViewById(R.id.org);
		s1name=(TextView) v.findViewById(R.id.s1name);
		s2name=(TextView) v.findViewById(R.id.s2name);
		area=(TextView) v.findViewById(R.id.area);
		phone=(TextView) v.findViewById(R.id.phone);
		web=(Button) v.findViewById(R.id.web);
		remark=(TextView) v.findViewById(R.id.remark);

		if(Global.s.whichlang()){

			org.setText(Global.socobject.organization_zh);
			s1name.setText(Global.socobject.service_name1_zh);
			if(!Global.socobject.service_name2_zh.equals("")){
				s2name.setText("-"+Global.socobject.service_name2_zh);
			}else{
				s2name.setText("");
			}
			area.setText(Global.socobject.district_zh);
			phone.setText(Global.socobject.phone);
			remark.setText("".equals(Global.socobject.remark_zh) ? getActivity().getResources().getString(R.string.noRemarks) : Global.socobject.remark_zh);
		}else{

			org.setText(Global.socobject.organization_gb);
			s1name.setText(Global.socobject.service_name1_gb);
			if(!Global.socobject.service_name2_gb.equals("")){
				s2name.setText("-"+Global.socobject.service_name2_gb);
			}else{
				s2name.setText("");
			}
			area.setText(Global.socobject.district_gb);
			phone.setText(Global.socobject.phone);
			remark.setText("".equals(Global.socobject.remark_gb) ? getActivity().getResources().getString(R.string.noRemarks) :Global.socobject.remark_gb);
		}
		web.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(Global.socobject.website));
				startActivity(i);	
			}
		});
		if(!Global.socobject.family.equals("1")){
			lvfmy.setVisibility(View.GONE);
		}
		if(!Global.socobject.first.equals("1")){
			lv1.setVisibility(View.GONE);
		}
		if(!Global.socobject.second.equals("1")){
			lv2.setVisibility(View.GONE);
		}
		if(!Global.socobject.third.equals("1")){
			lv3.setVisibility(View.GONE);
		}
		if(!Global.socobject.helper.equals("1")){
			lvhelper.setVisibility(View.GONE);
		}
		if(!Global.socobject.four.equals("1")){
			lv4.setVisibility(View.GONE);
		}
		phonelayout=(LinearLayout) v.findViewById(R.id.phonelayout);
		phonelayout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:"+Global.socobject.phone));
				startActivity(callIntent);
			}
		});
		return v;
	}

}
