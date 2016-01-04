package hk.org.jccpa.dementia.game2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import hk.org.jccpa.dementia.ColorFilterGenerator;
import hk.org.jccpa.dementia.Global;
import hk.org.jccpa.dementia.Pages;
import hk.org.jccpa.dementia.R;
import hk.org.jccpa.dementia.game.UpdateGameData;

import android.graphics.Color;
import android.graphics.PorterDuff;

import com.example.basefamework.fontsize.Preferences;


import android.app.Fragment;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;

public class Game_two_1 extends Fragment {
	View v;
	ImageButton back,menu;
	int sizeWidth=0;
	int sizeHeight=0;
	Button enter;
	TextView qnum2;
	ImageView myImage,myImage1,myImage2,myImage3,myImage4,myImage5 ,myImage6,myImage7,myImage8,tips;
	ImageView h1,h2,h3,h4,h5;
	RelativeLayout bglayout,toplinear,numberquestionlayout,levelup;
	LinearLayout bottomlinear,confrimLayout;
	ImageView q1,q2,q3,rorw,demo;
	int check1=0,check2=0,check3=0;
	int anscount=0;
	ArrayList<Integer>  ans;
	private static final String IMAGEVIEW_TAG = "The Android Logo";
	LinearLayout holepage;
	ArrayList<Integer> randlist;
	TextView qnum;
	int qes1,qes2,qes3;
	int gendistance=0;

	int anspic[]={

			R.drawable.b002_a,
			R.drawable.b005_a,
			R.drawable.b008_a,
			R.drawable.b009_a,
			R.drawable.b003_a,
			R.drawable.b006_a,
			R.drawable.b001_a,
			R.drawable.b004_a,
			R.drawable.b007_a



	};
	int qespic[]={
			R.drawable.b002,
			R.drawable.b005,
			R.drawable.b008,
			R.drawable.b009,
			R.drawable.b003,
			R.drawable.b006,
			R.drawable.b001,
			R.drawable.b004,
			R.drawable.b007
	};
	public void onCreate(Bundle savedInstanceState) {
		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);

		super.onCreate(savedInstanceState);
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);
		v = inflater.inflate(R.layout.activity_game_two_1, container, false);
		back=(ImageButton) v.findViewById(R.id.back);
		menu=(ImageButton) v.findViewById(R.id.menu);
		randlist =new ArrayList<Integer>();
		View holepage=v.findViewById(R.id.holepage);
		confrimLayout=(LinearLayout) v.findViewById(R.id.confrimLayout);
		enter=(Button) v.findViewById(R.id.enter);
		qnum=(TextView) v.findViewById(R.id.qnum);
		qnum2=(TextView) v.findViewById(R.id.qnum2);
		Global.gameqnum++;
		qnum2.setText(String.valueOf(Global.gameqnum));
		qnum.setText(String.valueOf(Global.gameqnum));
		rorw=(ImageView) v.findViewById(R.id.rorw);
		levelup=(RelativeLayout) v.findViewById(R.id.levelup);
		demo=(ImageView) v.findViewById(R.id.demo);
		demo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Game2demo.Stage=0;
				Global.onAirAct.pushFragment(Pages.Game2demo);
			}
		});
		holepage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		//		if (Global.s.getgame2status()>=2){
		//			ans.add(0);
		//			ans.add(1);
		//			ans.add(2);
		//			ans.add(3);
		//			ans.add(4);
		//			ans.add(5);
		//			ans.add(6);
		//			ans.add(7);
		//			ans.add(8);
		//			for(int i=0;i<6;i++){
		//				int temp1 = new Numbers().random(ans.size()-1);
		//				int temp2 = new Numbers().random(ans.size()-1);
		//				Collections.swap(ans,temp1,temp2);
		//			}
		//		}else{
		randans();
		//		}



		holepage=(LinearLayout) v.findViewById(R.id.holepage);
		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Global.onAirAct.pushFragment(Pages.Gamemenu);
			}
		});
		holepage.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}

		});
		menu.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Global.onAirAct.mSlidingMenu.toggleRightDrawer();
			}
		});

		myImage = (ImageView)v.findViewById(R.id.image);

		myImage1 = (ImageView)v.findViewById(R.id.image1);
		myImage2 = (ImageView)v.findViewById(R.id.image2);
		myImage3 = (ImageView)v.findViewById(R.id.image3);

		myImage4 = (ImageView)v.findViewById(R.id.image4);
		myImage5 = (ImageView)v.findViewById(R.id.image5);
		myImage6 = (ImageView)v.findViewById(R.id.image6);

		myImage7 = (ImageView)v.findViewById(R.id.image7);
		myImage8 = (ImageView)v.findViewById(R.id.image8);
		//		myImage

		myImage.setImageResource(anspic[ans.get(0)]);
		myImage1.setImageResource(anspic[ans.get(1)]);
		myImage2.setImageResource(anspic[ans.get(2)]);
		myImage3.setImageResource(anspic[ans.get(3)]);
		myImage4.setVisibility(View.GONE);
		myImage5.setVisibility(View.GONE);
		myImage6.setVisibility(View.GONE);
		myImage7.setVisibility(View.GONE);
		myImage8.setVisibility(View.GONE);
		if(Global.s.getgame2status()>=1){
			myImage4.setImageResource(anspic[ans.get(4)]);
			myImage4.setVisibility(View.VISIBLE);
			myImage5.setImageResource(anspic[ans.get(5)]);
			myImage5.setVisibility(View.VISIBLE);
			if(Global.s.getgame2status()>=2){
				myImage6.setImageResource(anspic[ans.get(6)]);
				myImage6.setVisibility(View.VISIBLE);
				myImage7.setImageResource(anspic[ans.get(7)]);
				myImage7.setVisibility(View.VISIBLE);
				myImage8.setImageResource(anspic[ans.get(8)]);
				myImage8.setVisibility(View.VISIBLE);
			}
		}

		//		    myImage3 = (ImageView)v.findViewById(R.id.image3);

		// Sets the tag

		myImage.setTag("a:"+ans.get(0));
		myImage1.setTag("a:"+ans.get(1));
		myImage2.setTag("a:"+ans.get(2));
		myImage3.setTag("a:"+ans.get(3));
		if(Global.s.getgame2status()>=1){
			myImage4.setTag("a:"+ans.get(4));
			myImage5.setTag("a:"+ans.get(5));
			if(Global.s.getgame2status()>=2){
				myImage6.setTag("a:"+ans.get(6));
				myImage7.setTag("a:"+ans.get(7));
				myImage8.setTag("a:"+ans.get(8));
			}
		}
		//		    myImage3.setTag(IMAGEVIEW_TAG+"3");

		// set the listener to the dragging data
		myImage.setOnLongClickListener(new MyClickListener());
		myImage1.setOnLongClickListener(new MyClickListener());
		myImage2.setOnLongClickListener(new MyClickListener());
		myImage3.setOnLongClickListener(new MyClickListener());
		if(Global.s.getgame2status()>=1){
			myImage4.setOnLongClickListener(new MyClickListener());
			myImage5.setOnLongClickListener(new MyClickListener());
			if(Global.s.getgame2status()>=2){
				myImage6.setOnLongClickListener(new MyClickListener());
				myImage7.setOnLongClickListener(new MyClickListener());
				myImage8.setOnLongClickListener(new MyClickListener());
			}
		}



		//		    myImage3.setOnLongClickListener(new MyClickListener());

		toplinear=(RelativeLayout) v.findViewById(R.id.toplinear);

		bottomlinear	=(LinearLayout) v.findViewById(R.id.bottomlinear);
		toplinear.setOnDragListener(new MyDragListener());
		bottomlinear.setOnDragListener(new MyDragListener());
		v.findViewById(R.id.actionbar).setOnDragListener(new MyDragListener());
		v.findViewById(R.id.heartbar).setOnDragListener(new MyDragListener());


		addques();
		tips=(ImageView) v.findViewById(R.id.tips);
		tips.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("anscount", " "+anscount);
				Global.totalscore-=5;
				if(toplinear.getChildCount()==1){
					q1.setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
					q1.setColorFilter(
							ColorFilterGenerator.adjustBrightness(80));
					String temp[]=String.valueOf(myImage.getTag()).split(":");
					int id= Integer.valueOf(temp[1]);
					if(qespic[id]==Integer.valueOf(String.valueOf(q1.getTag()))){
						myImage.setColorFilter(
								ColorFilterGenerator.adjustBrightness(50));
					}
					temp=String.valueOf(myImage1.getTag()).split(":");
					id= Integer.valueOf(temp[1]);
					if(qespic[id]==Integer.valueOf(String.valueOf(q1.getTag()))){
						myImage1.setColorFilter(
								ColorFilterGenerator.adjustBrightness(50));
					}
					temp=String.valueOf(myImage2.getTag()).split(":");
					id= Integer.valueOf(temp[1]);
					if(qespic[id]==Integer.valueOf(String.valueOf(q1.getTag()))){
						myImage2.setColorFilter(
								ColorFilterGenerator.adjustBrightness(50));
					}
					temp=String.valueOf(myImage3.getTag()).split(":");
					id= Integer.valueOf(temp[1]);
					if(qespic[id]==Integer.valueOf(String.valueOf(q1.getTag()))){
						myImage3.setColorFilter(
								ColorFilterGenerator.adjustBrightness(50));
					}
					if (Global.s.getgame2status()>=1){
						temp=String.valueOf(myImage4.getTag()).split(":");
						id= Integer.valueOf(temp[1]);
						if(qespic[id]==Integer.valueOf(String.valueOf(q1.getTag()))){
							myImage4.setColorFilter(
									ColorFilterGenerator.adjustBrightness(50));
						}
						temp=String.valueOf(myImage5.getTag()).split(":");
						id= Integer.valueOf(temp[1]);
						if(qespic[id]==Integer.valueOf(String.valueOf(q1.getTag()))){
							myImage5.setColorFilter(
									ColorFilterGenerator.adjustBrightness(50));
						}
						if (Global.s.getgame2status()>=2){
							temp=String.valueOf(myImage6.getTag()).split(":");
							id= Integer.valueOf(temp[1]);
							if(qespic[id]==Integer.valueOf(String.valueOf(q1.getTag()))){
								myImage6.setColorFilter(
										ColorFilterGenerator.adjustBrightness(50));
							}
							temp=String.valueOf(myImage7.getTag()).split(":");
							id= Integer.valueOf(temp[1]);
							if(qespic[id]==Integer.valueOf(String.valueOf(q1.getTag()))){
								myImage7.setColorFilter(
										ColorFilterGenerator.adjustBrightness(50));
							}
							temp=String.valueOf(myImage8.getTag()).split(":");
							id= Integer.valueOf(temp[1]);
							if(qespic[id]==Integer.valueOf(String.valueOf(q1.getTag()))){
								myImage8.setColorFilter(
										ColorFilterGenerator.adjustBrightness(50));
							}
						}
					}
					//					myImage.setTag("a:"+ans.get(0));
					//					myImage1.setTag("a:"+ans.get(1));
					//					myImage2.setTag("a:"+ans.get(2));
					//					myImage3.setTag("a:"+ans.get(3));
					//					if(Global.s.getgame2status()>=1){
					//						myImage4.setTag("a:"+ans.get(4));
					//						myImage5.setTag("a:"+ans.get(5));
					//						if(Global.s.getgame2status()>=2){
					//							myImage6.setTag("a:"+ans.get(6));
					//							myImage7.setTag("a:"+ans.get(7));
					//							myImage8.setTag("a:"+ans.get(8));
					//						}
					//					}
				}
				if(toplinear.getChildCount()==2){
					q2.setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
					q2.setColorFilter(
							ColorFilterGenerator.adjustBrightness(50));
					String temp[]=String.valueOf(myImage.getTag()).split(":");
					int id= Integer.valueOf(temp[1]);
					if(qespic[id]==Integer.valueOf(String.valueOf(q2.getTag()))){
						myImage.setColorFilter(
								ColorFilterGenerator.adjustBrightness(50));
					}
					temp=String.valueOf(myImage1.getTag()).split(":");
					id= Integer.valueOf(temp[1]);
					if(qespic[id]==Integer.valueOf(String.valueOf(q2.getTag()))){
						myImage1.setColorFilter(
								ColorFilterGenerator.adjustBrightness(50));
					}
					temp=String.valueOf(myImage2.getTag()).split(":");
					id= Integer.valueOf(temp[1]);
					if(qespic[id]==Integer.valueOf(String.valueOf(q2.getTag()))){
						myImage2.setColorFilter(
								ColorFilterGenerator.adjustBrightness(50));
					}
					temp=String.valueOf(myImage3.getTag()).split(":");
					id= Integer.valueOf(temp[1]);
					if(qespic[id]==Integer.valueOf(String.valueOf(q2.getTag()))){
						myImage3.setColorFilter(
								ColorFilterGenerator.adjustBrightness(50));
					}
					if (Global.s.getgame2status()>=1){
						temp=String.valueOf(myImage4.getTag()).split(":");
						id= Integer.valueOf(temp[1]);
						if(qespic[id]==Integer.valueOf(String.valueOf(q2.getTag()))){
							myImage4.setColorFilter(
									ColorFilterGenerator.adjustBrightness(50));
						}
						temp=String.valueOf(myImage5.getTag()).split(":");
						id= Integer.valueOf(temp[1]);
						if(qespic[id]==Integer.valueOf(String.valueOf(q2.getTag()))){
							myImage5.setColorFilter(
									ColorFilterGenerator.adjustBrightness(50));
						}
						if (Global.s.getgame2status()>=2){
							temp=String.valueOf(myImage6.getTag()).split(":");
							id= Integer.valueOf(temp[1]);
							if(qespic[id]==Integer.valueOf(String.valueOf(q2.getTag()))){
								myImage6.setColorFilter(
										ColorFilterGenerator.adjustBrightness(50));
							}
							temp=String.valueOf(myImage7.getTag()).split(":");
							id= Integer.valueOf(temp[1]);
							if(qespic[id]==Integer.valueOf(String.valueOf(q2.getTag()))){
								myImage7.setColorFilter(
										ColorFilterGenerator.adjustBrightness(50));
							}
							temp=String.valueOf(myImage8.getTag()).split(":");
							id= Integer.valueOf(temp[1]);
							if(qespic[id]==Integer.valueOf(String.valueOf(q2.getTag()))){
								myImage8.setColorFilter(
										ColorFilterGenerator.adjustBrightness(50));
							}
						}
					}
				}
				if(toplinear.getChildCount()==3){
					q3.setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
					q3.setColorFilter(
							ColorFilterGenerator.adjustBrightness(50));
					String temp[]=String.valueOf(myImage.getTag()).split(":");
					int id= Integer.valueOf(temp[1]);
					if(qespic[id]==Integer.valueOf(String.valueOf(q3.getTag()))){
						myImage.setColorFilter(
								ColorFilterGenerator.adjustBrightness(50));
					}
					temp=String.valueOf(myImage1.getTag()).split(":");
					id= Integer.valueOf(temp[1]);
					if(qespic[id]==Integer.valueOf(String.valueOf(q3.getTag()))){
						myImage1.setColorFilter(
								ColorFilterGenerator.adjustBrightness(50));
					}
					temp=String.valueOf(myImage2.getTag()).split(":");
					id= Integer.valueOf(temp[1]);
					if(qespic[id]==Integer.valueOf(String.valueOf(q3.getTag()))){
						myImage2.setColorFilter(
								ColorFilterGenerator.adjustBrightness(50));
					}
					temp=String.valueOf(myImage3.getTag()).split(":");
					id= Integer.valueOf(temp[1]);
					if(qespic[id]==Integer.valueOf(String.valueOf(q3.getTag()))){
						myImage3.setColorFilter(
								ColorFilterGenerator.adjustBrightness(50));
					}
					if (Global.s.getgame2status()>=1){
						temp=String.valueOf(myImage4.getTag()).split(":");
						id= Integer.valueOf(temp[1]);
						if(qespic[id]==Integer.valueOf(String.valueOf(q3.getTag()))){
							myImage4.setColorFilter(
									ColorFilterGenerator.adjustBrightness(50));
						}
						temp=String.valueOf(myImage5.getTag()).split(":");
						id= Integer.valueOf(temp[1]);
						if(qespic[id]==Integer.valueOf(String.valueOf(q3.getTag()))){
							myImage5.setColorFilter(
									ColorFilterGenerator.adjustBrightness(50));
						}
						if (Global.s.getgame2status()>=2){
							temp=String.valueOf(myImage6.getTag()).split(":");
							id= Integer.valueOf(temp[1]);
							if(qespic[id]==Integer.valueOf(String.valueOf(q3.getTag()))){
								myImage6.setColorFilter(
										ColorFilterGenerator.adjustBrightness(50));
							}
							temp=String.valueOf(myImage7.getTag()).split(":");
							id= Integer.valueOf(temp[1]);
							if(qespic[id]==Integer.valueOf(String.valueOf(q3.getTag()))){
								myImage7.setColorFilter(
										ColorFilterGenerator.adjustBrightness(50));
							}
							temp=String.valueOf(myImage8.getTag()).split(":");
							id= Integer.valueOf(temp[1]);
							if(qespic[id]==Integer.valueOf(String.valueOf(q3.getTag()))){
								myImage8.setColorFilter(
										ColorFilterGenerator.adjustBrightness(50));
							}
						}
					}
				}
			}
		});

		h1=(ImageView) v.findViewById(R.id.g2h1);
		h2=(ImageView) v.findViewById(R.id.g2h2);
		h3=(ImageView) v.findViewById(R.id.g2h3);
		h4=(ImageView) v.findViewById(R.id.g2h4);
		h5=(ImageView) v.findViewById(R.id.g2h5);

		enter.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//				h1.setImageResource(R.drawable.a001);

				if(check1==2 && check2==2 && check3==2 && toplinear.getChildCount()!=0){

					Global.right5++;
					if(Global.s.getgame2status()==0){
						Global.totalscore=Global.totalscore+10;
					}else if(Global.s.getgame2status()==1){
						Global.totalscore=Global.totalscore+30;
					}else if(Global.s.getgame2status()>=2){
						Global.totalscore=Global.totalscore+50;
					}

					Log.d("game 2 status","status: "+Global.s.getgame2status() );
					if(Global.right5%10==0){

						if(Global.s.getgame2status()>=2){

						}else{
							int temp=Global.s.getgame2status()+1;
							Global.s.game2status(temp);
							levelup.setVisibility(View.VISIBLE);
							levelup.setOnClickListener(null);
							//							Global.onAirAct.pushFragment(Pages.Levelup);
							TextView congmsg=(TextView) levelup.findViewById(R.id.congmsg);
							if(Global.s.getgame2status()==1){
								congmsg.setText(getResources().getString(R.string.congmsg2));
							}else
								if(Global.s.getgame2status()==2){
									congmsg.setText(getResources().getString(R.string.congmsg3));
								}
							levelup.findViewById(R.id.enter).setOnClickListener(new View.OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									levelup.setVisibility(View.GONE);
									Global.onAirAct.pushFragment(Pages.Game_two_1);

								}
							});
                            if(Global.onAirAct.isOnline()){
                                new UpdateGameData().execute(String.valueOf(Global.s.getgame2status()));
                            }
							Log.d("game 2 status","temp status: "+Global.s.getgame2status());
						}
					}else{
						rorw.bringToFront();
						rorw.setVisibility(View.VISIBLE);
						Handler h=new Handler();
						h.postDelayed(new Runnable(){

							@Override
							public void run() {
								// TODO Auto-generated method stub
								Global.onAirAct.pushFragment(Pages.Game_two_1);
							}

						}, 500);
					}
				}else{
					Global.heart--;
					//					Global.heart=0;
					Log.d("game 2 status","heart status: "+Global.heart );
					//					rorw.setVisibility(View.VISIBLE);
					//					rorw.setImageResource(resId);

					if(Global.heart<=0){

						Handler h=new Handler();
						h.postDelayed(new Runnable(){

							@Override
							public void run() {
								// TODO Auto-generated method stub
								Global.onAirAct.pushFragment(Pages.Endgame2);
							}

						}, 500);
					}else{
						rorw.bringToFront();
						rorw.setVisibility(View.VISIBLE);
						rorw.setImageResource(R.drawable.cross);
                        if(Global.onAirAct.isOnline()){
                            new UpdateGameData().execute(String.valueOf(Global.s.getgame2status()));
                        }
						Handler h=new Handler();
						h.postDelayed(new Runnable(){

							@Override
							public void run() {
								// TODO Auto-generated method stub
								Global.onAirAct.pushFragment(Pages.Game_two_1);
							}

						}, 500);
					}
				}

				heart();
			}
		});
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
	private final class MyClickListener implements OnLongClickListener {



		@Override
		public boolean onLongClick(View v) {
			// TODO Auto-generated method stub
			// create it from the object's tag
			ClipData.Item item = new ClipData.Item((CharSequence)v.getTag());

			String[] mimeTypes = { ClipDescription.MIMETYPE_TEXT_PLAIN };
			ClipData data = new ClipData(v.getTag().toString(), mimeTypes, item);
			DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);

			v.startDrag( data, //data to be dragged
					shadowBuilder, //drag shadow
					v, //local data about the drag and drop operation
					0   //no needed flags
					);


			v.setVisibility(View.INVISIBLE);
			return true;
		}	
	}

	class MyDragListener implements OnDragListener {
		//		Drawable normalShape = getResources().getDrawable(R.drawable.ic_launcher);
		//		Drawable targetShape = getResources().getDrawable(R.drawable.ic_launcher);
		boolean ishere=false;

		@Override
		public boolean onDrag(View v1, DragEvent event) {
			// TODO Auto-generated method stub
			Log.d("Drag", ""+v1);
			// Handles each of the expected events
			switch (event.getAction()) {

			//signal for the start of a drag and drop operation.
			case DragEvent.ACTION_DRAG_STARTED:
				// do nothing
				break;

				//the drag point has entered the bounding box of the View
			case DragEvent.ACTION_DRAG_ENTERED:
				//		        v.setBackground(targetShape);	//change the shape of the view
				if(v1 == v.findViewById(R.id.bottomlinear)||v1 == v.findViewById(R.id.toplinear)) {
					ishere=true;
				}else{
					ishere=false;
				}
				break;

				//the user has moved the drag shadow outside the bounding box of the View
			case DragEvent.ACTION_DRAG_EXITED:
				//		        v.setBackground(normalShape);	//change the shape of the view back to normal
				break;

				//drag shadow has been released,the drag point is within the bounding box of the View
			case DragEvent.ACTION_DROP:
				if(ishere){
					// if the view is the bottomlinear, we accept the drag item
					if(v1 == v.findViewById(R.id.bottomlinear)) {
						//					if(toplinear.getChildCount()<3){
						View view = (View) event.getLocalState();
						ViewGroup viewgroup = (ViewGroup) view.getParent();
						viewgroup.removeView(view);
						LinearLayout temp =(LinearLayout) v1.findViewById(R.id.pool);
						LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
						temp.addView(view,lp);
						Log.d("child", ":"+temp.getChildCount() +  "   /tag "+view.getTag());
						view.setVisibility(View.VISIBLE);
						String temp2[]=String.valueOf(view.getTag()).split(":");
						int id= Integer.valueOf(temp2[1]);
						if(randlist.get(0)==qespic[id]){
							check1=0;

							//						anscount--;

						}else
							if(randlist.get(1)==qespic[id]){
								check2=0;
								//							anscount--;
							}else
								if(randlist.get(2)==qespic[id]){
									check3=0;
									//								anscount--;
								}
						anscount--;
						if(anscount<0){
							anscount=0;
						}
						//					Context context = getActivity().getApplicationContext();
						//					Toast.makeText(context, getActivity().getResources().getString(R.string.ucannodrophere), 
						//							Toast.LENGTH_LONG).show();
						//					}else{
						//						View view = (View) event.getLocalState();
						//						view.setVisibility(View.VISIBLE);
						//						Context context = getActivity().getApplicationContext();
						//
						//						Toast.makeText(context, getActivity().getResources().getString(R.string.ucannodrophere), 
						//								Toast.LENGTH_LONG).show();
						//					}
						break;
					} else if(v1 == v.findViewById(R.id.actionbar)) {

						View view = (View) event.getLocalState();
						view.setVisibility(View.VISIBLE);
						Context context = getActivity().getApplicationContext();

						Toast.makeText(context, getActivity().getResources().getString(R.string.ucannodrophere), 
								Toast.LENGTH_LONG).show();
						break;
					} else if(v1 == v.findViewById(R.id.heartbar)) {

						View view = (View) event.getLocalState();
						view.setVisibility(View.VISIBLE);
						Context context = getActivity().getApplicationContext();
						Toast.makeText(context, getActivity().getResources().getString(R.string.ucannodrophere), 
								Toast.LENGTH_LONG).show();
						break;
					} else if(v1 == v.findViewById(R.id.layout4)||v1 == v.findViewById(R.id.layout3)||v1==v.findViewById(R.id.qnum)||v1==v.findViewById(R.id.qwerty)) {

						View view = (View) event.getLocalState();
						view.setVisibility(View.VISIBLE);
						Context context = getActivity().getApplicationContext();
						Toast.makeText(context, getActivity().getResources().getString(R.string.ucannodrophere), 
								Toast.LENGTH_LONG).show();
						break;
					} else if(v1 == v.findViewById(R.id.toplinear)){
						View view = (View) event.getLocalState();
						ViewGroup viewgroup = (ViewGroup) view.getParent();
						viewgroup.removeView(view);
						Log.d("tag","tag:"+String.valueOf(view.getTag()));
						String temp[]=String.valueOf(view.getTag()).split(":");
						int id= Integer.valueOf(temp[1]);

						//change the text
						//		    		  TextView text = (TextView) v.findViewById(R.id.text);
						//		    		  text.setText("The item is dropped");
						RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
						lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
						lp.leftMargin = (int) event.getX()- (view.getWidth() / 2);
						Log.d("layout prams", "layout prams:"+ lp.leftMargin+" / "+ sizeWidth);
						if((lp.leftMargin+ view.getWidth()) > sizeWidth){
							lp.leftMargin = sizeWidth- (view.getWidth() );
						}

						lp.topMargin = (int) event.getY() - (view.getHeight()/2);
						if((lp.topMargin+ view.getHeight()) > sizeHeight){
							lp.topMargin = sizeHeight- (view.getHeight() );
						}

						//		    		  MarginLayoutParams marginParams = new MarginLayoutParams(view.getLayoutParams());              
						//		    		  int left = (int) event.getX() - (v.getWidth() / 2);
						//		    		  int top = (int) event.getY() - (v.getHeight());
						//		    		  marginParams.setMargins(left, top, 0, 0);
						//		    		  RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(marginParams);
						//		    		  layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);

						RelativeLayout containView = (RelativeLayout) v1;
						containView.addView(view, lp);
						view.setVisibility(View.VISIBLE);
						if(randlist.get(0)==qespic[id] && check1==0){
							check1=1;
							//						anscount++;
							//						int distance = (int) Math.sqrt((view.getX()-q1.getX())*(view.getX()-q1.getX()) + (view.getY()-q1.getY())*(view.getY()-q1.getY()));
							//						if(distance<20){
							//							check1=2;
							//						}else{
							//							check1=1;
							//						}
							//						Log.d("distance", " "+distance);
						}else
							if(randlist.get(1)==qespic[id] && check2==0){
								check2=1;
								//							anscount++;
								//							int distance = (int) Math.sqrt((view.getX()-q2.getX())*(view.getX()-q2.getX()) + (view.getY()-q2.getY())*(view.getY()-q2.getY()));
								//							if(distance<20){
								//								check2=2;
								//							}else{
								//								check2=1;
								//							}
								//							Log.d("distance", " "+distance);
							}else
								if(randlist.get(2)==qespic[id] && check3==0){
									check3=1;
									//								anscount++;
									//								int distance = (int) Math.sqrt((view.getX()-q3.getX())*(view.getX()-q3.getX()) + (view.getY()-q3.getY())*(view.getY()-q3.getY()));
									//								if(distance<20){
									//									check3=2;
									//								}else{
									//									check3=1;
									//								}
									//								Log.d("distance", " distance"+distance);
								}
						anscount++;
						if(toplinear.getChildCount()>=4){
							bottomlinear.setVisibility(View.GONE);
							confrimLayout.setVisibility(View.VISIBLE);
							myImage.setOnLongClickListener(null);
							myImage1.setOnLongClickListener(null);
							myImage2.setOnLongClickListener(null);
							myImage3.setOnLongClickListener(null);
							if(Global.s.getgame2status()>=1){
								myImage4.setOnLongClickListener(null);
								myImage5.setOnLongClickListener(null);
								if(Global.s.getgame2status()>=2){
									myImage6.setOnLongClickListener(null);
									myImage7.setOnLongClickListener(null);
									myImage8.setOnLongClickListener(null);
								}
							}
						}
						Log.d("checking ", "check:"+check1+"  "+check2+" "+check3);
					}else{
						View view = (View) event.getLocalState();
						view.setVisibility(View.VISIBLE);
						Context context = getActivity().getApplicationContext();
						Toast.makeText(context, getActivity().getResources().getString(R.string.ucannodrophere), 
								Toast.LENGTH_LONG).show();
						break;
					}
				}else{
					View view = (View) event.getLocalState();
					view.setVisibility(View.VISIBLE);
					Context context = getActivity().getApplicationContext();
					Toast.makeText(context, getActivity().getResources().getString(R.string.ucannodrophere), 
							Toast.LENGTH_LONG).show();
					break;
				}
				break;

				//the drag and drop operation has concluded.
			case DragEvent.ACTION_DRAG_ENDED:
				//		        v.setBackground(normalShape);	//go back to normal shape
				View view = (View) event.getLocalState();
				String temp[]=String.valueOf(view.getTag()).split(":");
				int id= Integer.valueOf(temp[1]);
				if(randlist.get(0)==qespic[id] ){
					check1=1;
					//					anscount++;
					int distance = (int) Math.sqrt((view.getX()-q1.getX())*(view.getX()-q1.getX()) + (view.getY()-q1.getY())*(view.getY()-q1.getY()));
					if(distance<20){
						check1=2;
					}else{
						check1=1;
					}
					Log.d("ACTION_DRAG_ENDED", " distance"+distance);
				}else
					if(randlist.get(1)==qespic[id]){
						check2=1;
						//						anscount++;
						int distance = (int) Math.sqrt((view.getX()-q2.getX())*(view.getX()-q2.getX()) + (view.getY()-q2.getY())*(view.getY()-q2.getY()));
						if(distance<20){
							check2=2;
						}else{
							check2=1;
						}
						Log.d("ACTION_DRAG_ENDED", " distance"+distance);
					}else
						if(randlist.get(2)==qespic[id]){
							check3=1;
							//							anscount++;
							int distance = (int) Math.sqrt((view.getX()-q3.getX())*(view.getX()-q3.getX()) + (view.getY()-q3.getY())*(view.getY()-q3.getY()));
							if(distance<20){
								check3=2;
							}else{
								check3=1;
							}
							Log.d("ACTION_DRAG_ENDED", " distance"+distance);
						}
				Log.d("checking ", "check:"+check1+"  "+check2+" "+check3);
			default:
				break;
			}
			return true;
		}
	}


	public void randans(){
		Random dice = new Random();
		ans =new ArrayList<Integer>();
		randlist =new ArrayList<Integer>();
		int temp=0;
		int m=6;
		if (Global.s.getgame2status()>=1){
			m=4;
		}
		if (Global.s.getgame2status()>=2){
			m=1;
		}
		for(int i=0;i<3;){

			temp = (int)(Math.random() * (qespic.length-m-0+1)) + 0;
			if(!randlist.contains(qespic[temp])){
				Log.d("temp","temp:"+temp);
				randlist.add(qespic[temp]);

				ans.add(temp);
				i++;
			}else{

			}

		}
		int j=1;
		if (Global.s.getgame2status()==1){
			j+=2;
		}
		if (Global.s.getgame2status()>=2){
			j+=5;
		}
		int tempSize= ans.size()+j;
		for(int i=0 ;i<(qespic.length-m+1);i++){
			//			temp = dice.nextInt(qespic.length-m);
			if(!ans.contains(i)){
				//				Log.d("temp","temp123:"+i+"    "+ans.size()+"    "+(ans.size()+j));
				//				ans.add(anspic[temp]);

				ans.add(i);

			}else{

			}

		}
		for(int i=0;i<6;i++){
			int temp1 = new Numbers().random(ans.size()-1);
			int temp2 = new Numbers().random(ans.size()-ans.size()/2);

			Collections.swap(ans,temp1,temp2);
			Log.d("Collections.swap","Collections.swap: "+ans.toString());
		}
	}



	public void addques(){
		//		Random dice = new Random();
		//		int temp = dice.nextInt(qespic.length-1);
		try{
			bglayout=(RelativeLayout) v.findViewById(R.id.bglayout);
			ViewTreeObserver vto2 = bglayout.getViewTreeObserver();   
			vto2.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {  


				@Override   
				public void onGlobalLayout() {
					try{
						sizeWidth=bglayout.getWidth();
						sizeHeight=bglayout.getHeight();

						gendistance=200;

						if (Global.s.getgame2status()==1){
							gendistance=100;
						}
						if (Global.s.getgame2status()>=2){
							gendistance=50;
						}

						bglayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                        if(null == getActivity()){
                            return;
                        }
						q1=new ImageView(getActivity());
						q1.setImageResource(randlist.get(0));
						RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
						lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
						lp.leftMargin =new Numbers().random(bglayout.getWidth());
						if(lp.leftMargin>bglayout.getWidth()/3*2){

							lp.leftMargin=lp.leftMargin/2;
						}
						lp.topMargin =new Numbers().random(bglayout.getHeight());
						if(lp.topMargin>bglayout.getHeight()/3*2){
							lp.topMargin=lp.topMargin/3*2;
						}
						bglayout.addView(q1, lp);

						Log.d("", ""+lp.leftMargin+"  /  "+lp.topMargin);
						//	    		Random dice2 = new Random();
						q2=new ImageView(getActivity());
						RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
						lp2.addRule(RelativeLayout.ALIGN_PARENT_TOP);

						q2.setImageResource(randlist.get(1));
						//				lp2.leftMargin = new Numbers().random(bglayout.getWidth()); 
						//				lp2.topMargin = new Numbers().random(bglayout.getHeight());
						//				if(lp2.leftMargin>bglayout.getWidth()/3*2){
						//					lp2.leftMargin=lp.leftMargin/3*2;
						//				}
						//				//				lp.topMargin =new Numbers().random(bglayout.getHeight());
						//				if(lp2.topMargin>bglayout.getHeight()/3*2){
						//					lp2.topMargin=lp.topMargin/3*2;
						//				}



						for(;;){
							lp2.leftMargin = new Numbers().random(bglayout.getWidth()); 
							lp2.topMargin = new Numbers().random(bglayout.getHeight());
							if(lp2.leftMargin>bglayout.getWidth()/3*2){
								lp2.leftMargin=lp.leftMargin/3*2;
							}
							//				lp.topMargin =new Numbers().random(bglayout.getHeight());
							if(lp2.topMargin>bglayout.getHeight()/3*2){
								lp2.topMargin=lp.topMargin/3*2;
							}
							//					if((float)((lp.leftMargin-lp2.leftMargin)/bglayout.getWidth())>0.3 || (float)((lp2.leftMargin-lp.leftMargin)/bglayout.getWidth())<-0.3){


							int distance = (int) Math.sqrt((lp.topMargin-lp2.topMargin)*(lp.topMargin-lp2.topMargin) + (lp.leftMargin -lp2.leftMargin )*(lp.leftMargin -lp2.leftMargin ));
							if(distance>gendistance){
								break;
							}
							//					}

						}
						//				if((float)((lp.leftMargin-lp2.leftMargin)/bglayout.getWidth())>0.1){
						//					lp.leftMargin/=1.2;
						//				}else 
						//				if((float)((lp2.leftMargin-lp.leftMargin)/bglayout.getWidth())<-0.1){
						//					lp.leftMargin*=1.2;
						//				}
						//				if((float)((lp.topMargin-lp2.topMargin)/bglayout.getWidth())>0.1){
						//					lp.topMargin/=1.2;
						//				}else 
						//				if((float)((lp2.topMargin-lp.topMargin)/bglayout.getWidth())<-0.1){
						//					lp.topMargin*=1.2;
						//				}

						bglayout.addView(q2, lp2);
						Log.d("", ""+lp2.leftMargin+"  /  "+lp2.topMargin);
						//	    		Random dice3 = new Random();
						RelativeLayout.LayoutParams lp3 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
						q3=new ImageView(getActivity());
						lp3.addRule(RelativeLayout.ALIGN_PARENT_TOP);
						q3.setImageResource(randlist.get(2));
						//					lp3.topMargin = new Numbers().random(bglayout.getHeight());
						//					lp3.leftMargin = new Numbers().random(bglayout.getWidth());
						//					if(lp3.leftMargin>bglayout.getWidth()/3*2){
						//						lp3.leftMargin=lp.leftMargin/3*2;
						//					}
						//					//				lp.topMargin =new Numbers().random(bglayout.getHeight());
						//					if(lp3.topMargin>bglayout.getHeight()/3*2){
						//						lp3.topMargin=lp.topMargin/3*2;
						//					}
						//
						//					if((float)((lp.leftMargin-lp3.leftMargin)/bglayout.getWidth())>0.1){
						//						lp.leftMargin/=1.2;
						//					}else 
						//						if((float)((lp3.leftMargin-lp.leftMargin)/bglayout.getWidth())<-0.1){
						//							lp.leftMargin*=1.2;
						//						}
						//					if((float)((lp.topMargin-lp3.topMargin)/bglayout.getWidth())>0.1){
						//						lp.topMargin/=1.2;
						//					}else 
						//						if((float)((lp3.topMargin-lp.topMargin)/bglayout.getWidth())<-0.1){
						//							lp.topMargin*=1.2;
						//						}
						//
						//					if((float)((lp2.leftMargin-lp3.leftMargin)/bglayout.getWidth())>0.1){
						//						lp.leftMargin/=1.2;
						//					}else 
						//						if((float)((lp3.leftMargin-lp2.leftMargin)/bglayout.getWidth())<-0.1){
						//							lp.leftMargin*=1.2;
						//						}
						//					if((float)((lp2.topMargin-lp3.topMargin)/bglayout.getWidth())>0.1){
						//						lp.topMargin/=1.2;
						//					}else 
						//						if((float)((lp3.topMargin-lp2.topMargin)/bglayout.getWidth())<-0.1){
						//							lp.topMargin*=1.2;
						//						}
						/*for(;;){
					lp3.leftMargin = new Numbers().random(bglayout.getWidth()); 
					lp3.topMargin = new Numbers().random(bglayout.getHeight());
					if(lp3.leftMargin>bglayout.getWidth()/3*2){
						lp3.leftMargin=lp.leftMargin/3*2;
					}
					//				lp.topMargin =new Numbers().random(bglayout.getHeight());
					if(lp3.topMargin>bglayout.getHeight()/3*2){
						lp3.topMargin=lp.topMargin/3*2;
					}
					if((float)((lp.leftMargin-lp3.leftMargin)/bglayout.getWidth())>0.3 || (float)((lp3.leftMargin-lp.leftMargin)/bglayout.getWidth())<-0.3){



						if((float)((lp2.leftMargin-lp3.leftMargin)/bglayout.getWidth())>0.3 || (float)((lp3.leftMargin-lp2.leftMargin)/bglayout.getWidth())<-0.3){



							break;
						}
					}

				}
						 */
						for(;;){
							lp3.leftMargin = new Numbers().random(bglayout.getWidth()); 
							lp3.topMargin = new Numbers().random(bglayout.getHeight());
							if(lp3.leftMargin>bglayout.getWidth()/3*2){
								lp3.leftMargin=lp.leftMargin/3*2;
							}
							//				lp.topMargin =new Numbers().random(bglayout.getHeight());
							if(lp3.topMargin>bglayout.getHeight()/3*2){
								lp3.topMargin=lp.topMargin/3*2;
							}
							//					if((float)((lp.leftMargin-lp2.leftMargin)/bglayout.getWidth())>0.3 || (float)((lp2.leftMargin-lp.leftMargin)/bglayout.getWidth())<-0.3){


							int distance = (int) Math.sqrt((lp.topMargin-lp3.topMargin)*(lp.topMargin-lp3.topMargin) + (lp.leftMargin -lp3.leftMargin )*(lp.leftMargin -lp3.leftMargin ));
							int distance2 = (int) Math.sqrt((lp2.topMargin-lp3.topMargin)*(lp2.topMargin-lp3.topMargin) + (lp2.leftMargin -lp3.leftMargin )*(lp2.leftMargin -lp3.leftMargin ));

							if(distance>gendistance){
								if(distance2>gendistance){
									break;
								}
							}
							//					}

						}
						bglayout.addView(q3, lp3);
						q1.setTag(randlist.get(0));
						q2.setTag(randlist.get(1));
						q3.setTag(randlist.get(2));
					}catch(Exception e){
						e.printStackTrace();
					}
				}   
			});   
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public class Numbers { 
		Random randnum;

		public Numbers() { 
			randnum = new Random();
			//	        randnum.setSeed();
		} 

		public int random(int i){
			return randnum.nextInt(i);
		} 
	}
}
