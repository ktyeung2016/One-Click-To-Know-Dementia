package hk.org.jccpa.dementia.map;

import java.util.ArrayList;

import hk.org.jccpa.dementia.Global;
import hk.org.jccpa.dementia.Pages;
import hk.org.jccpa.dementia.R;
import hk.org.jccpa.dementia.classpackage.fmyinfo;
import hk.org.jccpa.dementia.sql.MyDBHelper_callfmy;

import com.example.basefamework.fontsize.Preferences;


import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Mapdemo4 extends Fragment {
	View v;
	ImageButton back,menu;
	Button newfmymem;
	ListView fmylist;
	ArrayList<fmyinfo> list;
	MyDBHelper_callfmy dbhelper;
	Handler h;
	//demo
		RelativeLayout demolayout;
		Handler h2;
		LinearLayout buttonlayout,wholelayout,topbarlayout;
		Toast toast;
		View layout, overlay;
		TextView text,stagenumber;
		Runnable r;
		ImageView nextstep,laststep, overlayImage;
		//	Toast toast;
		//demo
//	RelativeLayout demolayout;
	hk.org.jccpa.dementia.adaptor.fmyinfoAdapter fmyinfoAdapter;
	public void onCreate(Bundle savedInstanceState) {

		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);
		super.onCreate(savedInstanceState);
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);
		v = inflater.inflate(R.layout.mapdemo2, container, false);
		back=(ImageButton) v.findViewById(R.id.back);
		menu=(ImageButton) v.findViewById(R.id.menu);
		newfmymem=(Button) v.findViewById(R.id.newfmymem);
        overlay=v.findViewById(R.id.overlay);
        overlay.setVisibility(View.VISIBLE);
        overlayImage = (ImageView) v.findViewById(R.id.overlayImage);
		View holepage=v.findViewById(R.id.holepage);
		holepage.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Global.mapdemosteps++;
				Global.s.setmap(false);
				Global.onAirAct.clearBackStack();
				Global.onAirAct.pushFragment(Pages.Map);
			}
		});
		
		//demo
		laststep=(ImageView) v.findViewById(R.id.laststep);
		laststep.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Global.mapdemosteps--;
				Global.onAirAct.clearBackStack();
				Global.onAirAct.pushFragment(Pages.Mapdemo3);
			}
		});
		LayoutInflater inflater2 = getActivity().getLayoutInflater();
		layout = inflater2.inflate(R.layout.custoast,
				(ViewGroup) getActivity().findViewById(R.id.toast_layout_root));
		text = (TextView) layout.findViewById(R.id.text);
		//		text.setText(s[Stage]);

		toast = new Toast(getActivity().getApplicationContext());
		text.setText(Global.onAirAct.mapdemo[Global.mapdemosteps]);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setView(layout);
		toast.show();
		nextstep=(ImageView) v.findViewById(R.id.nextstep);
		nextstep.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Global.mapdemosteps++;
				Global.s.setmap(false);
				Global.onAirAct.clearBackStack();
				Global.onAirAct.pushFragment(Pages.Map);
			}
		});
		stagenumber=(TextView) v.findViewById(R.id.stagenumber);
		stagenumber.setText(String.valueOf(Global.mapdemosteps+1)+"/"+String.valueOf(Global.onAirAct.mapdemo.length));
		//demo
		
		
		demolayout=(RelativeLayout) v.findViewById(R.id.demolayout);

		h=new Handler();
		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
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
		newfmymem.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Global.onAirAct.pushFragment(Pages.Newafmymem);
			}
		});

		dbhelper = new MyDBHelper_callfmy(getActivity());
		dbhelper.close();

//		show();
//		list=new ArrayList<fmyinfo>();
		fmylist=(ListView) v.findViewById(R.id.listView1);
		fmyinfoAdapter=new hk.org.jccpa.dementia.adaptor.fmyinfoAdapter(getActivity(),list);
		fmylist.setAdapter(fmyinfoAdapter);

		h2=new Handler();
		r=new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub

				toast.show();

				h.postDelayed(r, 100);
			}


		};
		try { 
		    Thread.sleep(200);                 //1000 milliseconds is one second.
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		} 
		return v;
	}

//	private Cursor getCursor(){
//
//		SQLiteDatabase db = dbhelper.getReadableDatabase();
//
//		String[] columns = { android.provider.BaseColumns._ID,MyDBHelper_callfmy.name, MyDBHelper_callfmy.relationship, MyDBHelper_callfmy.remarks
//				,MyDBHelper_callfmy.filepath,MyDBHelper_callfmy.phone,MyDBHelper_callfmy.email,MyDBHelper_callfmy.userid};
//
//		Cursor cursor = db.query(MyDBHelper_callfmy.TABLE_NAME, columns, null, null, null, null, null);
//
//		getActivity().startManagingCursor(cursor);
//
//		return cursor;
//
//	}
	private void show(){
		//		final String INIT_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + name + " CHAR, " 
		//				+ relationship + " CHAR, "+ remarks + " CHAR, "+ filepath + " CHAR, "+ phone + " CHAR, " + email + " CHAR);";
//		Cursor cursor = getCursor();
//
//		StringBuilder resultData = new StringBuilder("RESULT: \n");
//		list=new ArrayList<fmyinfo>();
//		while(cursor.moveToNext()){
////			Log.d("Global.s.userid()",""+Global.s.userid() );
//			if(!Global.s.userid().equals("no_user")){
//				if(cursor.getString(7).equals(Global.s.userid())){
//					int id = cursor.getInt(0);
//
//					String name = cursor.getString(1);
//
//					String relationship = cursor.getString(2);
//
//					String remarks = cursor.getString(3);
//
//					String filepath = cursor.getString(4);
//
//					String phone = cursor.getString(5);
//
//					String email = cursor.getString(6);
//
//
//
//					fmyinfo temp=new fmyinfo( String.valueOf(id),name,
//							phone,
//							email,
//							relationship,
//							remarks,
//							filepath);
//					resultData.append(id).append(": ");
//
//					resultData.append(name).append(", ");
//
//					resultData.append(relationship).append(", ");
//
//					resultData.append(remarks).append(", ");
//
//					resultData.append(filepath).append(", ");
//
//					resultData.append(phone).append(", ");
//
//					resultData.append(email).append(", ");
//
//					resultData.append("\n");
//					Log.d("result", "result:"+ resultData.toString());
//					list.add(temp);
//				}
//			}
//			else {
//				if(cursor.getString(7).equals("no_user")){
//				int id = cursor.getInt(0);
//
//				String name = cursor.getString(1);
//
//				String relationship = cursor.getString(2);
//
//				String remarks = cursor.getString(3);
//
//				String filepath = cursor.getString(4);
//
//				String phone = cursor.getString(5);
//
//				String email = cursor.getString(6);
//
//
//
//				fmyinfo temp=new fmyinfo( String.valueOf(id),name,
//						phone,
//						email,
//						relationship,
//						remarks,
//						filepath);
//				resultData.append(id).append(": ");
//
//				resultData.append(name).append(", ");
//
//				resultData.append(relationship).append(", ");
//
//				resultData.append(remarks).append(", ");
//
//				resultData.append(filepath).append(", ");
//
//				resultData.append(phone).append(", ");
//
//				resultData.append(email).append(", ");
//
//				resultData.append("\n");
//				Log.d("result", "result:"+ resultData.toString());
//				list.add(temp);
//				}
//			}
//		}


	}



	public void onResume(){
		super.onResume();
		h2.postDelayed(r, 100);
		/*h.postDelayed(new Runnable(){
			public void run() { 
				// do something   
				Button tip = new Button(getActivity());

				//		LayoutInflater inflater = getActivity().getLayoutInflater();
				//		View rowView = inflater.inflate(R.layout.demobar, null);
				//		demolayout.addView(rowView);
				tip.setBackgroundResource(R.drawable.messagedialogup);
				tip.setText(getResources().getString(R.string.mapfunctionstep1));
				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 150);
				//		params.topMargin=50;
				//		params.leftMargin=50;
//				params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
				params.leftMargin = (int) newfmymem.getX()- (tip.getWidth()/ 2);
				params.topMargin = (newfmymem.getHeight());
				Log.d("Margin", "Margin"+"     "+params.leftMargin+"    "+params.topMargin+"   "+newfmymem.getX()+"/"+newfmymem.getHeight());


				demolayout.addView(tip, params);
			}
		}, 100); */
	}
	public void onPause(){
		super.onPause();
		h.removeCallbacks(r);
		toast.cancel();
	}
	public void onDestroy(){
		toast.cancel();
		h.removeCallbacks(r);
		super.onDestroy();
	}
    public void drawOverlay(View view){
        view.postDelayed(new Runnable() {
            public void run() {
                Rect r = new Rect();
                if (null != overlay && null != overlayImage &&  Global.mapdemosteps == 9) {
                    Bitmap result = Global.maskOverlay.getOverlayBitmap(overlay.getWidth(), overlay.getHeight());
                    if(null == result){
                        return;
                    }
                    overlayImage.setImageBitmap(result);
                    overlayImage.setScaleType(ImageView.ScaleType.FIT_XY);

                    switch (Global.mapdemosteps){
                        case 9:{
                            View listView1 = v.findViewById(R.id.listView1);
                            if(null != listView1 && listView1.getGlobalVisibleRect(r)){
                                Global.maskOverlay.drawMask(result, r);
                            }
                            break;
                        }
                        default:{

                        }
                    }
                }
            }
        }, 1);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.postDelayed(new Runnable() {
            public void run() {
                drawOverlay(v);
            }
        }, 1);
    }
}
