package hk.org.jccpa.dementia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hk.org.jccpa.dementia.adaptor.ExpandableListAdapter;
import hk.org.jccpa.dementia.object.soc;
import hk.org.jccpa.dementia.sql.MyDBHelper_soc;

import com.example.basefamework.fontsize.Preferences;


import android.app.AlertDialog;
import android.app.Fragment;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Socres extends Fragment {
	View v;
	ImageButton back,menu;
	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	List<String> listDataHeader;
	List<List<String>> contenttitle;
	TextView type,area;
	MyDBHelper_soc socdb;
	String condition1="123",condition2="123";
	HashMap<String, List<String>> listDataChild;


	private void prepareListData() { 
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();

		// Adding child data 
		//		listDataHeader.add("熱線");
		//		listDataHeader.add("資源中心");
		//		listDataHeader.add("社會福利署長者日間護理中心網上資訊");
		//		listDataHeader.add("社會福利署長者日間暫托服務網上資訊");
		readtype();
		loadalldata();
		// Adding child data 
		//		List<String> top250 = new ArrayList<String>();
		//
		//		top250.add("長者安居服務協會");
		//		top250.add("耆安鈴");
		//		top250.add("香港復康會社區復康網");
		//		top250.add("絡長期病復康一線通");
		//		top250.add("香港老年痴呆症協會");
		//		top250.add("照顧者熱線");
		//
		//		listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
		//
		//		top250.clear();
		//		//		List<String> nowShowing = new ArrayList<String>();
		//		top250.add("長者安居服務協會");
		//		top250.add("耆安鈴");
		//		top250.add("香港復康會社區復康網");
		//		top250.add("絡長期病復康一線通");
		//		top250.add("香港老年痴呆症協會");
		//		top250.add("照顧者熱線");

		//		listDataChild.put(listDataHeader.get(1), top250);
		//		List<String> comingSoon = new ArrayList<String>();
		//		comingSoon.add("長者安居服務協會");
		//		comingSoon.add("耆安鈴");
		//		comingSoon.add("香港復康會社區復康網");
		//		comingSoon.add("絡長期病復康一線通");
		//		comingSoon.add("香港老年痴呆症協會");
		//		comingSoon.add("照顧者熱線");
		//

		listDataHeader.add("tips");

		contenttitle.add(new ArrayList<String>());

		for(int i = 0 ;i<contenttitle.size(); i++){

			listDataChild.put(listDataHeader.get(i), contenttitle.get(i));

		}

		//		listDataChild.put(listDataHeader.get(listDataHeader.size()-1), contenttitle.get(listDataHeader.size()-1));
		//				listDataChild.put(listDataHeader.get(3), comingSoon);
	} 


	public void onCreate(Bundle savedInstanceState) {

		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);
		super.onCreate(savedInstanceState);

	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);
		v = inflater.inflate(R.layout.activity_socres, container, false);
		back=(ImageButton) v.findViewById(R.id.back);
		menu=(ImageButton) v.findViewById(R.id.menu);
		type=(TextView) v.findViewById(R.id.type);
		area=(TextView) v.findViewById(R.id.area);
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


		// get the listview 
		expListView = (ExpandableListView) v.findViewById(R.id.lvExp);

		// preparing list data 
		prepareListData(); 

		listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild,expListView);

		// setting list adapter 
		expListView.setAdapter(listAdapter);



		// Listview Group click listener 
		expListView.setOnGroupClickListener(new OnGroupClickListener() {

			@Override 
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {

				//				ImageView view = (ImageView) parent.findViewById(R.id.marker);

				//				Imav.findViewById(R.id.marker);
				// Toast.makeText(getApplicationContext(), 
				// "Group Clicked " + listDataHeader.get(groupPosition), 
				// Toast.LENGTH_SHORT).show(); 

				return false; 
			} 
		}); 

		// Listview Group expanded listener 
		expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

			@Override 
			public void onGroupExpand(int groupPosition) {
//				Toast.makeText(getActivity().getApplicationContext(),
//						listDataHeader.get(groupPosition) + " Expanded",
//						Toast.LENGTH_SHORT).show();
				//				expListView.getChildAt(groupPosition).setBackgroundColor(Color.parseColor("#FBDC9C"));
				//				ImageView maker=(ImageView) expListView.getChildAt(groupPosition).findViewById(R.id.marker);


				//					maker.setImageResource(R.drawable.marker_socres);

				//					maker.setImageResource(R.drawable.marker2_socres);

				//				 if(groupPosition != lastExpandedGroupPosition){
				//					 expListView.collapseGroup(lastExpandedGroupPosition);
				//			        }
				//


				//			        super.onGroupExpanded(groupPosition);           
				//			        lastExpandedGroupPosition = groupPosition;



			} 
		}); 

		// Listview Group collasped listener 
		expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

			@Override 
			public void onGroupCollapse(int groupPosition) {
//				Toast.makeText(getActivity().getApplicationContext(),
//						listDataHeader.get(groupPosition) + " Collapsed",
//						Toast.LENGTH_SHORT).show();

			} 
		}); 

		// Listview on child click listener 
		expListView.setOnChildClickListener(new OnChildClickListener() {

			@Override 
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub 
				//				Toast.makeText(
				//						getActivity().getApplicationContext(),
				//						listDataHeader.get(groupPosition)
				//						+ " : " 
				//						+ listDataChild.get(
				//								listDataHeader.get(groupPosition)).get(
				//										childPosition), Toast.LENGTH_SHORT)
				//										.show();
				String temp=listDataChild.get(
						listDataHeader.get(groupPosition)).get(
								childPosition);


				if(Global.s.whichlang()){
					if(temp.contains("\n-")){
						String temp2[]=temp.split("\n-");
						Toast.makeText(
								getActivity().getApplicationContext(),temp2[0]+"   "+temp2[1]
										, Toast.LENGTH_SHORT)
										.show();
						for(int i = 0 ; i<Global.soclist.size() ;i++){
							if(Global.soclist.get(i).service_name1_zh.contains(temp2[0]) && Global.soclist.get(i).service_name2_zh.contains(temp2[1])){
								Global.socobject=Global.soclist.get(i);
								Log.d("temp", ""+Global.soclist.get(i).service_name1_zh+"   ////  "+Global.soclist.get(i).service_name2_zh);
								break;
							}
						}
						Global.onAirAct.pushFragment(Pages.Socresdetail);
					}else{
						Toast.makeText(
								getActivity().getApplicationContext(),temp
								, Toast.LENGTH_SHORT)
								.show();
						for(int i = 0 ; i<Global.soclist.size() ;i++){
							if(Global.soclist.get(i).service_name1_zh.contains(temp) && Global.soclist.get(i).service_name2_zh.equals("")){
								Global.socobject=Global.soclist.get(i);
								Log.d("temp", ""+Global.soclist.get(i).service_name1_zh+"   ///  "+Global.soclist.get(i).service_name2_zh);
								break;
							}
						}
						Global.onAirAct.pushFragment(Pages.Socresdetail);
					}
				}else{
					if(temp.contains("\n-")){
						String temp2[]=temp.split("\n-");
						Toast.makeText(
								getActivity().getApplicationContext(),temp2[0]+"   "+temp2[1]
										, Toast.LENGTH_SHORT)
										.show();
						for(int i = 0 ; i<Global.soclist.size() ;i++){
							if(Global.soclist.get(i).service_name1_gb.contains(temp2[0]) && Global.soclist.get(i).service_name2_gb.contains(temp2[1])){
								Global.socobject=Global.soclist.get(i);
								break;
							}
						}
						Global.onAirAct.pushFragment(Pages.Socresdetail);
					}else{
						Toast.makeText(
								getActivity().getApplicationContext(),temp
								, Toast.LENGTH_SHORT)
								.show();
						for(int i = 0 ; i<Global.soclist.size() ;i++){
							if(Global.soclist.get(i).service_name1_gb.contains(temp) && Global.soclist.get(i).service_name2_gb.equals("")){
								Global.socobject=Global.soclist.get(i);
								break;
							}
						}
						Global.onAirAct.pushFragment(Pages.Socresdetail);
					}
				}
				return false; 
			} 
		}); 
		View holepage=v.findViewById(R.id.holepage);
		holepage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		type.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ShowAlertDialogAndList1();
			}
		});
		area.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ShowAlertDialogAndList2();
			}
		});
		if(listDataHeader.size()==1){
			new AlertDialog.Builder(getActivity())
			.setTitle("沒有資料")
			.setMessage("請連接網絡!／请连接网络!")
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int whichButton) {
					Global.onAirAct.clearBackStack();
					Global.onAirAct.pushFragment(Pages.Homepage1);
				}
			}).show();
		}
		return v;
	}


	private void ShowAlertDialogAndList1()
	{
		final String[] ListStr = getActivity().getResources().getStringArray(R.array.f1);

		Builder MyAlertDialog = new AlertDialog.Builder(getActivity());
		MyAlertDialog.setTitle(getActivity().getResources().getString(R.string.choice));
		//建立選擇的事件
		DialogInterface.OnClickListener ListClick = new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which) {
				if(which==0){
					condition1="1e221z";
					type.setText(ListStr[which]);
				}else{
					condition1=ListStr[which];
					type.setText(ListStr[which]);
				}
                type.setContentDescription(getActivity().getResources().getString(R.string.typeOption1) + ListStr[which] + getActivity().getResources().getString(R.string.typeOption2));
				// preparing list data 
				prepareListData(); 

				listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild,expListView);

				// setting list adapter 
				expListView.setAdapter(listAdapter);

			}
		};
		//建立按下取消什麼事情都不做的事件
		DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which) {
			}
		};  
		MyAlertDialog.setItems(ListStr, ListClick);
		MyAlertDialog.setNeutralButton(getActivity().getResources().getString(R.string.cancel),OkClick );
		MyAlertDialog.show();
	}

	private void ShowAlertDialogAndList2()
	{
		final String[] ListStr2 = getActivity().getResources().getStringArray(R.array.f2);
		Builder MyAlertDialog = new AlertDialog.Builder(getActivity());
		MyAlertDialog.setTitle(getActivity().getResources().getString(R.string.choice));
		//建立選擇的事件
		DialogInterface.OnClickListener ListClick = new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which) {
				if(which==0){
					condition2="abc";
					area.setText(ListStr2[which]);
				}else{
					condition2=ListStr2[which];
					area.setText(ListStr2[which]);
				}
                area.setContentDescription(getActivity().getResources().getString(R.string.areaOption1) + ListStr2[which] + getActivity().getResources().getString(R.string.areaOption2));
				// preparing list data 
				prepareListData(); 

				listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild,expListView);

				// setting list adapter 
				expListView.setAdapter(listAdapter);

			}
		};
		//建立按下取消什麼事情都不做的事件
		DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which) {
			}
		};  
		MyAlertDialog.setItems(ListStr2, ListClick);
		MyAlertDialog.setNeutralButton(getActivity().getResources().getString(R.string.cancel),OkClick );
		MyAlertDialog.show();
	}


	//	private Cursor getCursor(){
	//
	//		SQLiteDatabase db = socdb.getReadableDatabase();
	//
	//		//		String[] columns = { android.provider.BaseColumns._ID,MyDBHelper_game5.diff, MyDBHelper_game5.marks, MyDBHelper_game5.numofq};
	//		Log.d("","select * from "+MyDBHelper_news.TABLE_NAME+";");
	//		//		Cursor cursor = db.query(MyDBHelper_game5.TABLE_NAME, columns, null, null, null, null, MyDBHelper_game5.marks);
	//		Cursor cursor=db.rawQuery("select "+MyDBHelper_knowledge.dbid+" , "+MyDBHelper_knowledge.redirect+" , "+MyDBHelper_knowledge.title_zh+" , "+MyDBHelper_knowledge.title_gb+" , "+MyDBHelper_knowledge.html_content_zh+" , "+MyDBHelper_knowledge.html_content_gb+" from "+MyDBHelper_knowledge.TABLE_NAME+";",null);
	//
	//		//db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy)
	//		//		getActivity().startManagingCursor(cursor);
	//
	//		return cursor;
	//
	//	}
	public void readtype(){
		socdb = new MyDBHelper_soc(getActivity());
		SQLiteDatabase db = socdb.getReadableDatabase();

		Cursor cursor=null;
		if(Global.s.whichlang()){
			String query="SELECT DISTINCT "+MyDBHelper_soc.title_zh+" from "+MyDBHelper_soc.TABLE_NAME+";";
			cursor=db.rawQuery(query,null);
		}else{
			cursor=db.rawQuery("SELECT DISTINCT "+MyDBHelper_soc.title_gb+" from "+MyDBHelper_soc.TABLE_NAME+";",null);	
		}
		//		SELECT *
		//		FROM Persons
		//		LIMIT 5;
		//		

		cursor.moveToFirst();

		for(int i =0 ;i<cursor.getCount();i++){

			listDataHeader.add(cursor.getString(0));
			cursor.moveToNext();
		}

		cursor.close();
		socdb.close();
	}
	public void loadalldata(){
		socdb = new MyDBHelper_soc(getActivity());
		SQLiteDatabase db = socdb.getReadableDatabase();

		Cursor cursor=null;

		cursor=db.rawQuery("SELECT "
				+MyDBHelper_soc.dbid +" , "
				+MyDBHelper_soc.title_zh +" , "
				+MyDBHelper_soc.title_gb +" , "
				+MyDBHelper_soc.organization_zh +" , "
				+MyDBHelper_soc.organization_gb +" , "
				+MyDBHelper_soc.service_name1_zh +" , "
				+MyDBHelper_soc.service_name1_gb +" , "
				+MyDBHelper_soc.service_name2_zh +" , "
				+MyDBHelper_soc.service_name2_gb +" , "
				+MyDBHelper_soc.district_zh +" , "
				+MyDBHelper_soc.district_gb +" , "
				+MyDBHelper_soc.first +" , "
				+MyDBHelper_soc.second +" , "
				+MyDBHelper_soc.third +" , "
				+MyDBHelper_soc.four +" , "
				+MyDBHelper_soc.family +" , "
				+MyDBHelper_soc.helper +" , "
				+MyDBHelper_soc.remark_zh +" , "
				+MyDBHelper_soc.remark_gb +" , "
				+MyDBHelper_soc.phone +" , "
				+MyDBHelper_soc.website +" , "
				+MyDBHelper_soc.temp +" from "+MyDBHelper_soc.TABLE_NAME+";",null);


		//		SELECT *
		//		FROM Persons
		//		LIMIT 5;
		//		

		cursor.moveToFirst();
		ArrayList <soc> soclist =new ArrayList <soc>();
		for(int i =0 ;i<cursor.getCount();i++){

			soclist.add(new soc(cursor.getString(0),
					cursor.getString(1)
					,cursor.getString(2)
					,cursor.getString(3)
					,cursor.getString(4)
					,cursor.getString(5)
					,cursor.getString(6)
					,cursor.getString(7)
					,cursor.getString(8)
					,cursor.getString(9)
					,cursor.getString(10)
					,cursor.getString(11)
					,cursor.getString(12)
					,cursor.getString(13)
					,cursor.getString(14)
					,cursor.getString(15)
					,cursor.getString(16)
					,cursor.getString(17)
					,cursor.getString(18)
					,cursor.getString(19)
					,cursor.getString(20)
					,cursor.getString(21)

					)
					);
			cursor.moveToNext();
		}
		Global.soclist=soclist;

		contenttitle=new ArrayList<List<String>>();
		for(int i=0 ;i<listDataHeader.size();i++){
			List<String> temp = new ArrayList<String>();
			for(int j=0 ;j<soclist.size();j++){
				String name="";
				if(Global.s.whichlang()){
					if(soclist.get(j).title_zh.equals(listDataHeader.get(i))){
						name=name+soclist.get(j).service_name1_zh;
						if(!soclist.get(j).service_name2_zh.equals("") && soclist.get(j).service_name2_zh!=null){
							name=name+"\n-"+soclist.get(j).service_name2_zh;
						}
						if(condition1!=null){
							if(condition1.equals("輕度認知障礙")){
								//							<item>輕度認知障礙</item>
								//							<item>初期患者</item>
								//							<item>中期患者</item>
								//							<item>晚期患者</item>
								//							<item>照顧者服務</item>
								if(soclist.get(j).first.equals("1")){
									if(checkarea(soclist.get(j),name)!=null){
										temp.add(checkarea(soclist.get(j),name));
									}
								}
							}else
								if(condition1.equals("初期患者")){
									//							<item>輕度認知障礙</item>
									//							<item>初期患者</item>
									//							<item>中期患者</item>
									//							<item>晚期患者</item>
									//							<item>照顧者服務</item>
									if(soclist.get(j).second.equals("1")){
										if(checkarea(soclist.get(j),name)!=null){
											temp.add(checkarea(soclist.get(j),name));
										}
									}
								}else
									if(condition1.equals("中期患者")){
										//							<item>輕度認知障礙</item>
										//							<item>初期患者</item>
										//							<item>中期患者</item>
										//							<item>晚期患者</item>
										//							<item>照顧者服務</item>
										if(soclist.get(j).third.equals("1")){
											if(checkarea(soclist.get(j),name)!=null){
												temp.add(checkarea(soclist.get(j),name));
											}
										}
									}else
										if(condition1.equals("晚期患者")){
											//							<item>輕度認知障礙</item>
											//							<item>初期患者</item>
											//							<item>中期患者</item>
											//							<item>晚期患者</item>
											//							<item>照顧者服務</item>
											if(soclist.get(j).four.equals("1")){
												if(checkarea(soclist.get(j),name)!=null){
													temp.add(checkarea(soclist.get(j),name));
												}
											}
										}else 
											if(condition1.equals("照顧者服務")){
												//							<item>輕度認知障礙</item>
												//							<item>初期患者</item>
												//							<item>中期患者</item>
												//							<item>晚期患者</item>
												//							<item>照顧者服務</item>
												if(soclist.get(j).family.equals("1")||soclist.get(j).helper.equals("1")){
													if(checkarea(soclist.get(j),name)!=null){
														temp.add(checkarea(soclist.get(j),name));
													}												}
											}else{

												if(checkarea(soclist.get(j),name)!=null){
													temp.add(checkarea(soclist.get(j),name));
												}
											}
						}
					}

				}else{
					if(soclist.get(j).title_gb.equals(listDataHeader.get(i))){
						name=name+soclist.get(j).service_name1_gb;
						if(!soclist.get(j).service_name2_gb.equals("") && soclist.get(j).service_name2_gb!=null){
							name=name+"\n-"+soclist.get(j).service_name2_gb;
						}
						//						<item>港島</item>
						//						<item>九龍</item>
						//						<item>新界</item>
						//						<item>離島</item>
						if(condition1!=null){
							if(condition1.equals("轻度认知障碍")){
								//							<item>轻度认知障碍</item>
								//							<item>初期患者</item>
								//							<item>中期患者</item>
								//							<item>晚期患者</item>
								//							<item>照顾者服务</item>
								if(soclist.get(j).first.equals("1")){
									if(checkarea2(soclist.get(j),name)!=null){
										temp.add(checkarea2(soclist.get(j),name));
									}
								}
							}else
								if(condition1.equals("初期患者")){
									//							<item>轻度认知障碍</item>
									//							<item>初期患者</item>
									//							<item>中期患者</item>
									//							<item>晚期患者</item>
									//							<item>照顾者服务</item>
									if(soclist.get(j).second.equals("1")){
										if(checkarea2(soclist.get(j),name)!=null){
											temp.add(checkarea2(soclist.get(j),name));
										}
									}
								}else
									if(condition1.equals("中期患者")){
										//							<item>轻度认知障碍</item>
										//							<item>初期患者</item>
										//							<item>中期患者</item>
										//							<item>晚期患者</item>
										//							<item>照顾者服务</item>
										if(soclist.get(j).third.equals("1")){
											if(checkarea2(soclist.get(j),name)!=null){
												temp.add(checkarea2(soclist.get(j),name));
											}
										}
									}else
										if(condition1.equals("晚期患者")){
											//							<item>轻度认知障碍</item>
											//							<item>初期患者</item>
											//							<item>中期患者</item>
											//							<item>晚期患者</item>
											//							<item>照顾者服务</item>
											if(soclist.get(j).four.equals("1")){
												if(checkarea2(soclist.get(j),name)!=null){
													temp.add(checkarea2(soclist.get(j),name));
												}
											}
										}else 
											if(condition1.equals("照顾者服务")){
												//							<item>轻度认知障碍</item>
												//							<item>初期患者</item>
												//							<item>中期患者</item>
												//							<item>晚期患者</item>
												//							<item>照顾者服务</item>
												if(soclist.get(j).family.equals("1")||soclist.get(j).helper.equals("1")){
													if(checkarea2(soclist.get(j),name)!=null){
														temp.add(checkarea2(soclist.get(j),name));
													}												}
											}else{

												if(checkarea2(soclist.get(j),name)!=null){
													temp.add(checkarea2(soclist.get(j),name));
												}
											}


						}
					}
				}
			}
			contenttitle.add(temp);
		}

		cursor.close();
		socdb.close();
	}

	public String checkarea(soc temp ,String temp2){
		if(condition2.equals("港島")){
			//							中西區
			//							灣仔
			//							東區
			//							南區
			//							九龍城
			Log.d("condition2", condition2);
			if(temp.district_zh.contains("中西區") ||temp.district_zh.contains("灣仔") 
					||temp.district_zh.contains("東區") ||temp.district_zh.contains("南區")
					||temp.district_zh.contains("九龍城")){
				Log.d("condition2", condition2);
				return temp2;
			}
		}else
			if(condition2.equals("九龍")){
				if(temp.district_zh.contains("黃大仙") ||temp.district_zh.contains("觀塘") 
						||temp.district_zh.contains("油尖旺") ||temp.district_zh.contains("深水埗")
						){
					Log.d("condition2", condition2);
					return temp2;
				}
			}else
				if(condition2.equals("新界")){

					if(temp.district_zh.contains("荃灣") ||temp.district_zh.contains("葵青") 
							||temp.district_zh.contains("西貢") ||temp.district_zh.contains("沙田")
							||temp.district_zh.contains("大埔")||temp.district_zh.contains("北區")
							||temp.district_zh.contains("屯門")
							||temp.district_zh.contains("元朗") || temp.district_zh.contains("將軍澳") ){
						return temp2;
						//						Log.d("condition2", condition2);
					}
				}else
					if(condition2.equals("離島")){
						if(temp.district_zh.contains("離島")){
							return temp2;
							//							Log.d("condition2", condition2);
						}
					}else{

						return temp2;
					}
		return null;
	}
	public String checkarea2(soc temp ,String temp2){
		if(condition2.equals("港岛")){
			//							中西区
			//							湾仔
			//							东区
			//							南区
			//							九龙城
			Log.d("condition2", condition2);
			if(temp.district_gb.contains("中西区") ||temp.district_gb.contains("湾仔") 
					||temp.district_gb.contains("东区") ||temp.district_gb.contains("南区")
					||temp.district_gb.contains("九龙城")){
				Log.d("condition2", condition2);
				return temp2;
			}
		}else
			if(condition2.equals("九龙")){
				if(temp.district_gb.contains("黄大仙") ||temp.district_gb.contains("观塘") 
						||temp.district_gb.contains("油尖旺") ||temp.district_gb.contains("深水埗")
						){
					Log.d("condition2", condition2);
					return temp2;
				}
			}else
				if(condition2.equals("新界")){

					if(temp.district_gb.contains("荃湾") ||temp.district_gb.contains("葵青") 
							||temp.district_gb.contains("西贡") ||temp.district_gb.contains("沙田")
							||temp.district_gb.contains("大埔")||temp.district_gb.contains("北区")
							||temp.district_gb.contains("屯门")
							||temp.district_gb.contains("元朗") || temp.district_gb.contains("将军澳") ){
						return temp2;
						//						Log.d("condition2", condition2);
					}
				}else
					if(condition2.equals("离岛")){
						if(temp.district_gb.contains("离岛")){
							return temp2;
							//							Log.d("condition2", condition2);
						}
					}else{

						return temp2;
					}
		return null;
	}
}
