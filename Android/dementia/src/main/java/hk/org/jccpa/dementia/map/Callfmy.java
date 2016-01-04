package hk.org.jccpa.dementia.map;

import java.util.ArrayList;

import hk.org.jccpa.dementia.Global;
import hk.org.jccpa.dementia.Pages;
import hk.org.jccpa.dementia.R;
import hk.org.jccpa.dementia.adaptor.HotlineAdapter;
import hk.org.jccpa.dementia.classpackage.Hotline;
import hk.org.jccpa.dementia.classpackage.fmyinfo;
import hk.org.jccpa.dementia.sql.MyDBHelper_callfmy;

import com.example.basefamework.fontsize.Preferences;


import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class Callfmy extends Fragment {
	View v;
	ImageButton back,menu;
	Button newfmymem;
    ListView fmylist;
    ListView hotlineList;
    ArrayList<fmyinfo> list;
    ArrayList<Hotline> hotlineAL;
	MyDBHelper_callfmy dbhelper;
	Handler h;
	RelativeLayout demolayout;
    hk.org.jccpa.dementia.adaptor.fmyinfoAdapter fmyinfoAdapter;
    hk.org.jccpa.dementia.adaptor.HotlineAdapter hotlineAdapter;
	public void onCreate(Bundle savedInstanceState) {

		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);
		super.onCreate(savedInstanceState);
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);
		v = inflater.inflate(R.layout.activity_callfmy, container, false);
		back=(ImageButton) v.findViewById(R.id.back);
		menu=(ImageButton) v.findViewById(R.id.menu);
		Global.whichpage=Pages.Callfmy;
		newfmymem=(Button) v.findViewById(R.id.newfmymem);
		View holepage=v.findViewById(R.id.holepage);
		holepage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
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
				Global.onAirAct.pushFragment(Pages.Newafmymem);
			}
		});

		dbhelper = new MyDBHelper_callfmy(getActivity());
		dbhelper.close();

		show();
		fmylist=(ListView) v.findViewById(R.id.listView1);
		fmyinfoAdapter=new hk.org.jccpa.dementia.adaptor.fmyinfoAdapter(getActivity(),list);
		fmylist.setAdapter(fmyinfoAdapter);

        initHotline();
        hotlineList=(ListView) v.findViewById(R.id.hotlineList);
        hotlineAdapter=new HotlineAdapter(getActivity(),hotlineAL);
        hotlineList.setAdapter(hotlineAdapter);


		return v;
	}
	private Cursor getCursor(){

		SQLiteDatabase db = dbhelper.getReadableDatabase();

		String[] columns = { android.provider.BaseColumns._ID,MyDBHelper_callfmy.name, MyDBHelper_callfmy.relationship, MyDBHelper_callfmy.remarks
				,MyDBHelper_callfmy.filepath,MyDBHelper_callfmy.phone,MyDBHelper_callfmy.email,MyDBHelper_callfmy.userid};

		Cursor cursor = db.query(MyDBHelper_callfmy.TABLE_NAME, columns, null, null, null, null, null);

		getActivity().startManagingCursor(cursor);

		return cursor;

    }
    private void initHotline(){
        hotlineAL=new ArrayList<Hotline>();
        String[] labels = getResources().getStringArray(R.array.hotline_array);
        String[] numbers = getResources().getStringArray(R.array.hotline_num_array);
        int itemCount = Math.min(labels.length,numbers.length);
        for(int i=0; i<itemCount; i++){
            hotlineAL.add(new Hotline( labels[i], numbers[i]));
        }
    }
    private void show(){
		//		final String INIT_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + name + " CHAR, "
		//				+ relationship + " CHAR, "+ remarks + " CHAR, "+ filepath + " CHAR, "+ phone + " CHAR, " + email + " CHAR);";
		Cursor cursor = getCursor();

		StringBuilder resultData = new StringBuilder("RESULT: \n");
		list=new ArrayList<fmyinfo>();
		while(cursor.moveToNext()){
//			Log.d("Global.s.userid()",""+Global.s.userid() );
			if(!Global.s.userid().equals("no_user")){
				if(cursor.getString(7).equals(Global.s.userid())){
					int id = cursor.getInt(0);

					String name = cursor.getString(1);

					String relationship = cursor.getString(2);

					String remarks = cursor.getString(3);

					String filepath = cursor.getString(4);

					String phone = cursor.getString(5);

					String email = cursor.getString(6);



					fmyinfo temp=new fmyinfo( String.valueOf(id),name,
							phone,
							email,
							relationship,
							remarks,
							filepath);
					resultData.append(id).append(": ");

					resultData.append(name).append(", ");

					resultData.append(relationship).append(", ");

					resultData.append(remarks).append(", ");

					resultData.append(filepath).append(", ");

					resultData.append(phone).append(", ");

					resultData.append(email).append(", ");

					resultData.append("\n");
					Log.d("result", "result:"+ resultData.toString());
					list.add(temp);
				}
			}
			else {
				if(cursor.getString(7).equals("no_user")){
				int id = cursor.getInt(0);

				String name = cursor.getString(1);

				String relationship = cursor.getString(2);

				String remarks = cursor.getString(3);

				String filepath = cursor.getString(4);

				String phone = cursor.getString(5);

				String email = cursor.getString(6);



				fmyinfo temp=new fmyinfo( String.valueOf(id),name,
						phone,
						email,
						relationship,
						remarks,
						filepath);
				resultData.append(id).append(": ");

				resultData.append(name).append(", ");

				resultData.append(relationship).append(", ");

				resultData.append(remarks).append(", ");

				resultData.append(filepath).append(", ");

				resultData.append(phone).append(", ");

				resultData.append(email).append(", ");

				resultData.append("\n");
				Log.d("result", "result:"+ resultData.toString());
				list.add(temp);
				}
			}
		}


	}



	public void onResume(){
		super.onResume();
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
}
