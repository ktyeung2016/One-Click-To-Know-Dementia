package hk.org.jccpa.dementia.knowledge;

import java.util.ArrayList;

import hk.org.jccpa.dementia.Global;
import hk.org.jccpa.dementia.Pages;
import hk.org.jccpa.dementia.R;
import hk.org.jccpa.dementia.adaptor.knowledgedetailAdapter;

import com.example.basefamework.fontsize.Preferences;


import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class Knowledgedetail extends Fragment {
	View v;
	ImageButton back,menu;
	WebView web;
	TextView titledetial;
	Button rediectbutton;
	knowledgedetailAdapter adaptor;
	ArrayList<String> temp;
	public void onCreate(Bundle savedInstanceState) {

		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);
		super.onCreate(savedInstanceState);
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);
		v = inflater.inflate(R.layout.activity_knowledgedetail, container, false);
		back=(ImageButton) v.findViewById(R.id.back);
		menu=(ImageButton) v.findViewById(R.id.menu);
		web=(WebView) v.findViewById(R.id.web);
		titledetial=(TextView) v.findViewById(R.id.titledetial);
		rediectbutton=(Button) v.findViewById(R.id.rediectbutton);
		Global.whichpage=Pages.Knowledgedetail;
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
				Global.onAirAct.pushFragment(Pages.Kowledgecenter);
			}
		});
		menu.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Global.onAirAct.mSlidingMenu.toggleRightDrawer();
			}
		});
		if(Global.s.whichlang()){
			titledetial.setText( Global.knowledgeobject.title_zh);
			web.loadDataWithBaseURL("", Global.knowledgeobject.html_content_zh, "text/html", "UTF-8", "");
			web.setBackgroundColor(0x00000000);	
		}else{
			titledetial.setText( Global.knowledgeobject.title_gb);
			web.loadDataWithBaseURL("", Global.knowledgeobject.html_content_gb, "text/html", "UTF-8", "");
			web.setBackgroundColor(0x00000000);	
		}

		web.setWebViewClient(new WebViewClient() { 
		    @Override 
		    public boolean shouldOverrideUrlLoading(WebView view, String url)  {
		    	Log.d("url","url:"+url);
		    	
		        if (url.startsWith("dementia://")) {
		            // magic 
		        	String temp=url.replace("dementia://", "");
		        	//						String temp1=java.net.URLDecoder.decode(temp,"utf-8");
//						Log.d("url","temp1:"+temp1);
					for(int i =0 ;i<Global.knowledgelist.size();i++){
						if(temp.equals(Global.knowledgelist.get(i).dbid)||temp.equals(Global.knowledgelist.get(i).dbid)){
							
							Global.knowledgeobject=Global.knowledgelist.get(i);
							break;
						}
					}   
		        	
		        	
		        	Global.onAirAct.clearBackStack();
					Global.onAirAct.pushFragment(Pages.Knowledgedetail);
		            return true;
		        } else{
		        	Intent i = new Intent(Intent.ACTION_VIEW);
		        	i.setData(Uri.parse(url));
		        	startActivity(i);
		        	return true;
		        }
//		        return false; 
		    } 
		}); 
		
//
//		if(!Global.knowledgeobject.redirect.equals("")){
//			String tempArray[]=Global.knowledgeobject.redirect.split("::");
//
//			for(int k =0; k<tempArray.length;k++){
//				for(int i =0 ;i<Global.knowledgelist.size();i++){
//					//	
//
//					//				Log.d("redirect",+Global.knowledgelist.size()+"    "+ Global.knowledgelist.get(i).title_zh+"    "+Global.knowledgelist.get(i).title_zh.equals(Global.knowledgeobject.redirect) );
//					Log.d("tempArray", tempArray.length+"    "+tempArray[k]);
//					if(Global.knowledgelist.get(i).title_zh.equals(tempArray[k]) 
//							|| Global.knowledgelist.get(i).title_zh.equals(tempArray[k]) ){
//
//						rediectbutton.setText(tempArray[k]);
//						Global.knowledgeobject=Global.knowledgelist.get(i);
//						rediectbutton.setVisibility(View.VISIBLE);
//						rediectbutton.setOnClickListener(new View.OnClickListener() {
//
//							@Override
//							public void onClick(View v) {
//								// TODO Auto-generated method stub
//								Global.onAirAct.clearBackStack();
//								Global.onAirAct.pushFragment(Pages.Knowledgedetail);
//							}
//						});
//						Log.d("redirect",""+Global.knowledgeobject.redirect +"   "+Global.knowledgelist.get(i).title_zh);
//					}
//					break;
//				}
//			}
//
//		}

//		if(!Global.knowledgeobject.redirect.equals("")){
//			String tempArray[]=Global.knowledgeobject.redirect.split("::");
//			for(int k =0; k<tempArray.length;k++){
//				for(int i =0 ;i<Global.knowledgelist.size();i++){
//					if(tempArray[k].equals(Global.knowledgelist.get(i).title_zh)||tempArray[k].equals(Global.knowledgelist.get(i).title_gb)){
//						Log.d("tempArray", "yo"+"yo   "+tempArray.length+"    "+tempArray[k]);
//						rediectbutton.setVisibility(View.VISIBLE);
//						rediectbutton.setText(tempArray[k]);
//					}
//				}
//			}
//		}
		
		
		return v;
	}

}
