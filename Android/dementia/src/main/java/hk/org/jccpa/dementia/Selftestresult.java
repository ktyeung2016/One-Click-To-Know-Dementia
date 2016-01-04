package hk.org.jccpa.dementia;

import java.util.ArrayList;

import com.example.basefamework.fontsize.Preferences;


import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Selftestresult extends Fragment {
	View v;
	ImageButton back,menu;
	Button playag;
	TextView f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,phone;
    WebView result;
	LinearLayout flist;
	ArrayList <TextView> TextList;
	public void onCreate(Bundle savedInstanceState) {

		//		new Preferences(getActivity()).setFontStyle(FontStyle.Large);
		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);
		super.onCreate(savedInstanceState);
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);
		v = inflater.inflate(R.layout.activity_selftestresult, container, false);
		back=(ImageButton) v.findViewById(R.id.back);
		menu=(ImageButton) v.findViewById(R.id.menu);
		playag=(Button) v.findViewById(R.id.playag);
		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//				Global.onAirAct.clearOneBackStack();
				Global.onAirAct.clearBackStack();
				Global.onAirAct.pushFragmentnotag(Pages.Homepage1);
			}
		});
		menu.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Global.onAirAct.mSlidingMenu.toggleRightDrawer();
			}
		});
		playag.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Global.selftestqumber=1;
				Global.onAirAct.pushFragment(Pages.Selftest);
			}
		});
		View holepage=v.findViewById(R.id.holepage);
		holepage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		setup();
		return v;
	}
	public void setup(){
		flist=(LinearLayout) v.findViewById(R.id.flist);
		phone=(TextView) v.findViewById(R.id.phone);
		f1=(TextView) v.findViewById(R.id.f1);
		f2=(TextView) v.findViewById(R.id.f2);
		f3=(TextView) v.findViewById(R.id.f3);
		f4=(TextView) v.findViewById(R.id.f4);
		f5=(TextView) v.findViewById(R.id.f5);
		f6=(TextView) v.findViewById(R.id.f6);
		f7=(TextView) v.findViewById(R.id.f7);
		f8=(TextView) v.findViewById(R.id.f8);
		f9=(TextView) v.findViewById(R.id.f9);
		f10=(TextView) v.findViewById(R.id.f10);
		result=(WebView)v.findViewById(R.id.result);
		TextList=new ArrayList<TextView>();
		TextList.add(f1);
		TextList.add(f2);
		TextList.add(f3);
		TextList.add(f4);
		TextList.add(f5);
		TextList.add(f6);
		TextList.add(f7);
		TextList.add(f8);
		TextList.add(f9);
		TextList.add(f10);
		int isDcounter=0;
		for(int i = 0;i<Global.selftestans.length;i++){
			if(Global.selftestans[i]!=1){
				TextList.get(i).setVisibility(View.GONE);
				
			}else{
				isDcounter++;
			}
		}
		

		if(isDcounter<3){
			flist.setVisibility(View.GONE);
            result.loadDataWithBaseURL("", getResources().getString(R.string.nofeature), "text/html", "UTF-8", "");
            result.setBackgroundColor(0x00000000);
			//result.setText(getResources().getString(R.string.nofeature));
		}else{
//			result.setText(Html.fromHtml(v.getContext().getString(R.string.textWithHtml)));
			//result.setMovementMethod(LinkMovementMethod.getInstance());
            //result.setText(Html.fromHtml(v.getContext().getString(R.string.textWithHtml)),TextView.BufferType.SPANNABLE);
            result.loadDataWithBaseURL("", v.getContext().getString(R.string.textWithHtml), "text/html", "UTF-8", "");
            result.setBackgroundColor(0x00000000);
		}
		phone.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:26366323"));
				startActivity(callIntent);
			}
		});
	}
}
