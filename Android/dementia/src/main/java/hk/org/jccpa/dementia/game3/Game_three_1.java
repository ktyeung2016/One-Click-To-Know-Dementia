package hk.org.jccpa.dementia.game3;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import hk.org.jccpa.dementia.Global;
import hk.org.jccpa.dementia.Pages;
import hk.org.jccpa.dementia.R;
import hk.org.jccpa.dementia.game.UpdateGameData;

import com.example.basefamework.fontsize.Preferences;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;


import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Game_three_1 extends Fragment {
	View v;
	ImageButton back,menu;
	ImageLoader mImageLoader;
	DisplayImageOptions displayImageOptions;
	ArrayList <String> ans;
	ArrayList <String> qes;
	ArrayList <String> element;
	ArrayList <Integer> anspool;
	String realans="";
	String lv="";
	ImageView h1,h2,h3,h4,h5;
	boolean gettip=false;
	TextView qnum,qnum2;
	int questionset=0;
	String questiontoans="";
	String filestring="";
	String s[];
	RelativeLayout ans1,ans2,ans3,levelup,numberquestionlayout;
	ImageView question,c1,c2,c3,tips,rorw,demo;
	public static void initImageLoader(Context context) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration
				.Builder(context)
		.threadPriority(Thread.NORM_PRIORITY - 2)
		.denyCacheImageMultipleSizesInMemory()
		.discCacheFileNameGenerator(new Md5FileNameGenerator())
		.tasksProcessingOrder(QueueProcessingType.LIFO)
		.build();
		ImageLoader.getInstance().init(config);
	}
	public void onCreate(Bundle savedInstanceState) {
		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);

		super.onCreate(savedInstanceState);
		loadfilename();
		displayImageOptions = new DisplayImageOptions.Builder() 
		.showStubImage(R.drawable.ic_launcher) 
		.showImageOnFail(R.drawable.ic_launcher) 

		.imageScaleType(ImageScaleType.IN_SAMPLE_INT) 
		.bitmapConfig(Bitmap.Config.RGB_565)
		.showImageForEmptyUri(R.drawable.ic_launcher).cacheInMemory(true) 
		.cacheOnDisc(true).displayer(new FadeInBitmapDisplayer(300)) 
		.imageScaleType(ImageScaleType.EXACTLY_STRETCHED).build();
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);
		v = inflater.inflate(R.layout.game_three_1, container, false);
		//		System.gc();
		back=(ImageButton) v.findViewById(R.id.back);
		menu=(ImageButton) v.findViewById(R.id.menu);
		View holepage=v.findViewById(R.id.holepage);
		c1=(ImageView) v.findViewById(R.id.c1);
		c2=(ImageView) v.findViewById(R.id.c2);
		c3=(ImageView) v.findViewById(R.id.c3);
		mImageLoader=ImageLoader.getInstance();
		Global.gameqnum++;
		initImageLoader(getActivity());
		tips=(ImageView) v.findViewById(R.id.tips);
		levelup=(RelativeLayout) v.findViewById(R.id.levelupg3);
		holepage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		demo=(ImageView) v.findViewById(R.id.demo);
		demo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				game3demo.Stage=0;
				Global.onAirAct.pushFragment(Pages.game3demo);
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
		h1=(ImageView) v.findViewById(R.id.g3h1);
		h2=(ImageView) v.findViewById(R.id.g3h2);
		h3=(ImageView) v.findViewById(R.id.g3h3);
		h4=(ImageView) v.findViewById(R.id.g3h4);
		h5=(ImageView) v.findViewById(R.id.g3h5);
		heart();
		qnum=(TextView) v.findViewById(R.id.qnum);
		qnum2=(TextView) v.findViewById(R.id.qnum2);
		qnum.setText(String.valueOf(Global.gameqnum));
		qnum2.setText(String.valueOf(Global.gameqnum));
		question=(ImageView) v.findViewById(R.id.question);
		rorw=(ImageView) v.findViewById(R.id.rorw);
		//		question.setImageBitmap(bm)
		//		File f=new File("file:///"+Environment.getExternalStorageDirectory()+"/.jcfloder/game/qes/"+lv+"/"+qes.get(20));
		//		if(f.isFile()){
		//			Log.d("file", "file:///"+Environment.getExternalStorageDirectory()+"/.jcfloder/game/qes/"+lv+"/"+qes.get(6)+mImageLoader );
		//			mImageLoader.displayImage("file:///"+Environment.getExternalStorageDirectory()+"/.jcfloder/game/qes/"+lv+"/"+qes.get(20), question,displayImageOptions);
		//			String s[]=qes.get(20).split("_");
		//			mImageLoader.displayImage("file:///"+Environment.getExternalStorageDirectory()+"/.jcfloder/game/shape2/"+element.get(element.indexOf("game3_"+s[s.length-1]))
		//					, c1,displayImageOptions);
		//			mImageLoader.displayImage("file:///"+Environment.getExternalStorageDirectory()+"/.jcfloder/game/shape2/"+element.get(element.indexOf("game3_"+s[s.length-1])+1)
		//					, c2,displayImageOptions);
		//			mImageLoader.displayImage("file:///"+Environment.getExternalStorageDirectory()+"/.jcfloder/game/shape2/"+element.get(element.indexOf("game3_"+s[s.length-1])-1)
		//					, c3,displayImageOptions);
		//		}

		//randquestion
		for(;;){
			try{
				anspool=new ArrayList<Integer>();
				questionset=(int)(Math.random() * (qes.size()-0+1)) ;
				s=qes.get(questionset).split("_");
				realans="game3_"+s[s.length-1];
				anspool.add(element.indexOf("game3_"+s[s.length-1]));
				anspool.add(element.indexOf("game3_"+s[s.length-1])+1);
				anspool.add(element.indexOf("game3_"+s[s.length-1])-1);

				for(int i=0;i<2;i++){
					int temp1=(int)(Math.random() * (anspool.size()-0)) + 0;
					int temp2=(int)(Math.random() * (anspool.size()-0)) + 0;
					Collections.swap(anspool,temp1,temp2);
				}
				mImageLoader.displayImage("file:///"+Environment.getExternalStorageDirectory()+"/.jcfloder/game/qes/"+lv+"/"+qes.get(questionset), question,displayImageOptions);

				//			File f2=new File("file:///"+Environment.getExternalStorageDirectory()+"/.jcfloder/game/shape2/"+element.get(element.indexOf("game3_"+s[s.length-1])+1));
				//			if(f2.isFile()){

				mImageLoader.displayImage("file:///"+Environment.getExternalStorageDirectory()+"/.jcfloder/game/shape2/"+element.get(anspool.get(0))
						, c1,displayImageOptions);
				c1.setTag(element.get(anspool.get(0)));
				//			}
				//			File f3=new File("file:///"+Environment.getExternalStorageDirectory()+"/.jcfloder/game/shape2/"+element.get(element.indexOf("game3_"+s[s.length-1])+1));
				//			if(f3.isFile()){
				mImageLoader.displayImage("file:///"+Environment.getExternalStorageDirectory()+"/.jcfloder/game/shape2/"+element.get(anspool.get(1))
						, c2,displayImageOptions);
				c2.setTag(element.get(anspool.get(1)));
				//			}
				//			File f4=new File("file:///"+Environment.getExternalStorageDirectory()+"/.jcfloder/game/shape2/"+element.get(element.indexOf("game3_"+s[s.length-1])-1));
				//			if(f4.isFile()){
				mImageLoader.displayImage("file:///"+Environment.getExternalStorageDirectory()+"/.jcfloder/game/shape2/"+element.get(anspool.get(2))
						, c3,displayImageOptions);
				c3.setTag(element.get(anspool.get(2)));
				questiontoans=s[s.length-2].replace("q", "a");
				filestring="game3_"+lv+"_"+questiontoans+"_"+s[s.length-1];

				break;
			}catch(Exception e){

			}
		}

		//		//		File f=new File("file:///"+Environment.getExternalStorageDirectory()+"/.jcfloder/game/qes/"+lv+"/"+qes.get(questionset));
		//		//		if(f.isFile()){
		//		mImageLoader.displayImage("file:///"+Environment.getExternalStorageDirectory()+"/.jcfloder/game/qes/"+lv+"/"+qes.get(questionset), question,displayImageOptions);
		//		String s[]=qes.get(questionset).split("_");
		//		//			File f2=new File("file:///"+Environment.getExternalStorageDirectory()+"/.jcfloder/game/shape2/"+element.get(element.indexOf("game3_"+s[s.length-1])+1));
		//		//			if(f2.isFile()){
		//
		//		mImageLoader.displayImage("file:///"+Environment.getExternalStorageDirectory()+"/.jcfloder/game/shape2/"+element.get(element.indexOf("game3_"+s[s.length-1]))
		//				, c1,displayImageOptions);
		//
		//		//			}
		//		//			File f3=new File("file:///"+Environment.getExternalStorageDirectory()+"/.jcfloder/game/shape2/"+element.get(element.indexOf("game3_"+s[s.length-1])+1));
		//		//			if(f3.isFile()){
		//		mImageLoader.displayImage("file:///"+Environment.getExternalStorageDirectory()+"/.jcfloder/game/shape2/"+element.get(element.indexOf("game3_"+s[s.length-1])+1)
		//				, c2,displayImageOptions);
		//		//			}
		//		//			File f4=new File("file:///"+Environment.getExternalStorageDirectory()+"/.jcfloder/game/shape2/"+element.get(element.indexOf("game3_"+s[s.length-1])-1));
		//		//			if(f4.isFile()){
		//		mImageLoader.displayImage("file:///"+Environment.getExternalStorageDirectory()+"/.jcfloder/game/shape2/"+element.get(element.indexOf("game3_"+s[s.length-1])-1)
		//				, c3,displayImageOptions);
		//		//			}
		//		//	}

		View.OnClickListener check =new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String temp=(String)(v.getTag());
				//				if(temp.equals(realans)){
				//					Log.d("game3", "right!!!!~~~");
				//					int currentlevel=Global.s.getgame3status();
				//					Global.s.game3status(++currentlevel);
				//					Global.onAirAct.clearOneBackStack();
				//					Global.onAirAct.pushFragment(Pages.Game_three_1);
				//
				//				}

				if(temp.equals(realans)){
					mImageLoader.displayImage("file:///"+Environment.getExternalStorageDirectory()+"/.jcfloder/game/ans/"+lv+"/"+ans.get(ans.indexOf(filestring)), question,displayImageOptions);

					Global.right5++;
					if(Global.s.getgame3status()==0){
						Global.totalscore=Global.totalscore+10;
					}else if(Global.s.getgame3status()==1){
						Global.totalscore=Global.totalscore+30;
					}else if(Global.s.getgame3status()>=2){
						Global.totalscore=Global.totalscore+50;
					}

					Log.d("game 3 status","status: "+Global.s.getgame3status() );
					if(Global.right5%10==0 ){

						if(Global.s.getgame3status()>=2){
							mImageLoader.displayImage("file:///"+Environment.getExternalStorageDirectory()+"/.jcfloder/game/ans/"+lv+"/"+ans.get(ans.indexOf(filestring)), question,displayImageOptions);
							rorw.bringToFront();
							rorw.setVisibility(View.VISIBLE);
							Handler h=new Handler();
							h.postDelayed(new Runnable(){

								@Override
								public void run() {
									// TODO Auto-generated method stub
									System.gc();
									mImageLoader.clearDiskCache();
									mImageLoader.clearMemoryCache();
									Global.onAirAct.clearBackStack();
									Global.onAirAct.pushFragment(Pages.Game_three_1);
								}

							}, 500);
						}else{
							int currentlevel=Global.s.getgame3status()+1;
							Global.s.game3status(currentlevel);
							levelup.setVisibility(View.VISIBLE);
							levelup.setOnClickListener(null);
							//							Global.onAirAct.pushFragment(Pages.Levelup);
							TextView congmsg=(TextView) levelup.findViewById(R.id.congmsg123321);
							if(Global.s.getgame3status()==1){
								congmsg.setText(getResources().getString(R.string.congmsg2));
							}else
								if(Global.s.getgame3status()==2){
									congmsg.setText(getResources().getString(R.string.congmsg3));
								}
							levelup.findViewById(R.id.enter).setOnClickListener(new View.OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									levelup.setVisibility(View.GONE);
									System.gc();
									mImageLoader.clearDiskCache();
									mImageLoader.clearMemoryCache();
									Global.onAirAct.clearBackStack();
									Global.onAirAct.pushFragment(Pages.Game_three_1);

								}
							});
                            if(Global.onAirAct.isOnline()){
                                new UpdateGameData().execute(String.valueOf(Global.s.getgame3status()));
                            }
							Log.d("game 3 status","temp status: "+Global.s.getgame3status());
						}
					}else{
						mImageLoader.displayImage("file:///"+Environment.getExternalStorageDirectory()+"/.jcfloder/game/ans/"+lv+"/"+ans.get(ans.indexOf(filestring)), question,displayImageOptions);
						rorw.bringToFront();
						rorw.setVisibility(View.VISIBLE);
						Handler h=new Handler();
						c1.setOnClickListener(null);
						c2.setOnClickListener(null);
						c3.setOnClickListener(null);
						h.postDelayed(new Runnable(){

							@Override
							public void run() {
								// TODO Auto-generated method stub
								System.gc();
								mImageLoader.clearDiskCache();
								mImageLoader.clearMemoryCache();
								Global.onAirAct.clearBackStack();
								Global.onAirAct.pushFragment(Pages.Game_three_1);
							}

						}, 500);
					}
				}else{
					Global.heart--;
					//					Global.heart=0;
					Log.d("game 3 status","heart status: "+Global.heart );
					//					rorw.setVisibility(View.VISIBLE);
					//					rorw.setImageResource(resId);

					if(Global.heart<=0){
						c1.setOnClickListener(null);
						c2.setOnClickListener(null);
						c3.setOnClickListener(null);
						Handler h=new Handler();
						h.postDelayed(new Runnable(){

							@Override
							public void run() {
								// TODO Auto-generated method stub
								Global.onAirAct.pushFragment(Pages.Endgame3);
							}

						}, 500);
					}else{
						rorw.bringToFront();
						rorw.setVisibility(View.VISIBLE);
						rorw.setImageResource(R.drawable.cross);
						c1.setOnClickListener(null);
						c2.setOnClickListener(null);
						c3.setOnClickListener(null);
                        if(Global.onAirAct.isOnline()){
                            new UpdateGameData().execute(String.valueOf(Global.s.getgame3status()));
                        }
						Handler h=new Handler();
						h.postDelayed(new Runnable(){

							@Override
							public void run() {
								// TODO Auto-generated method stub
								System.gc();
								Global.onAirAct.clearBackStack();
								mImageLoader.clearDiskCache();
								mImageLoader.clearMemoryCache();
								Global.onAirAct.pushFragment(Pages.Game_three_1);
							}

						}, 500);
					}
				}

				heart();
			}

		};

		c1.setOnClickListener(check);
		c2.setOnClickListener(check);
		c3.setOnClickListener(check);

		numberquestionlayout=(RelativeLayout) v.findViewById(R.id.numberquestionlayout);
		numberquestionlayout.setOnClickListener(null);
		Handler h1=new Handler();
		h1.postDelayed(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				numberquestionlayout.setVisibility(View.GONE);
			}

		}, 1000);

		ans1=(RelativeLayout) v.findViewById(R.id.ans1);
		ans2=(RelativeLayout) v.findViewById(R.id.ans2);
		ans3=(RelativeLayout) v.findViewById(R.id.ans3);

		tips.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String temp=(String)(c1.getTag());
				String temp1=(String)(c2.getTag());
				String temp2=(String)(c3.getTag());
				if(gettip==false){
					Global.totalscore-=5;
					gettip=true;
					if(!temp1.equals(realans)){
						ans2.setVisibility(View.INVISIBLE);
						c2.setOnClickListener(null);
					}else if(!temp2.equals(realans)){
						ans3.setVisibility(View.INVISIBLE);
						c3.setOnClickListener(null);
					}else if(!temp2.equals(realans)){
						ans1.setVisibility(View.INVISIBLE);
						c1.setOnClickListener(null);
					}
				}
			}
		});
		return v;
	}

	public void loadfilename(){
		ans=new ArrayList<String>();
		File sdCardRoot = Environment.getExternalStorageDirectory();
		if(Global.s.getgame3status()<=0){
			lv= "lv1";
		}else if(Global.s.getgame3status()==1){
			lv= "lv2";
		}else{
			lv= "lv3";
		}

		//if
		File yourDir = new File(sdCardRoot, "/.jcfloder/game/ans/"+lv);
		for (File f : yourDir.listFiles()) {
			if (f.isFile())
				ans.add(f.getName());
			Log.d("Loadingfilename",f.getName());
			// make something with the name 
		} 
		qes=new ArrayList<String>();


		//if
		File yourDir1 = new File(sdCardRoot, "/.jcfloder/game/qes/"+lv);
		for (File f : yourDir1.listFiles()) {
			if (f.isFile() && !f.getName().equals("humbs.db"))
				qes.add(f.getName());
			Log.d("Loadingfilename",f.getName());
			// make something with the name 
		} 
		element=new ArrayList<String>();


		//if
		File yourDir2 = new File(sdCardRoot, "/.jcfloder/game/shape2");
		for (File f : yourDir2.listFiles()) {
			if (f.isFile())

				element.add((int)(Math.random() * (element.size()-1-0+1)),f.getName());
			Log.d("Loadingfilename",f.getName());
			// make something with the name 
		} 

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
	public void onPause(){
		super.onPause();
		mImageLoader.clearDiskCache();
		mImageLoader.clearMemoryCache();
	}
	public void onDestroy(){
		super.onDestroy();
		mImageLoader.clearDiskCache();
		mImageLoader.clearMemoryCache();
	}
}
