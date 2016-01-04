package hk.org.jccpa.dementia.adaptor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import hk.org.jccpa.dementia.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class rankAdapter extends BaseAdapter{
	Context c;
	private LayoutInflater myInflater;

	ArrayList<String> list=null;
	ArrayList<Integer> marklist=null;
	public rankAdapter(Context c,ArrayList<String> list){
		this.c=c;
		myInflater = LayoutInflater.from(c);
		this.list=list;

	}
	public rankAdapter(Context c){
		this.c=c;
		myInflater = LayoutInflater.from(c);
	}
	public rankAdapter(Context c,ArrayList<Integer> marklist,ArrayList<String> list){
		this.c=c;
		myInflater = LayoutInflater.from(c);
		this.marklist=marklist;
		this.list=list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(list!=null && marklist!=null)
		{
			return list.size();
		}
		return 10;

	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		//自訂類別，表達個別listItem中的view物件集合。
		ViewTag viewTag;
		TextView date,marks;
		//		if(convertView == null){
		//取得listItem容器 view
		if(position==0){
			convertView = myInflater.inflate(R.layout.rk_numone, null);
		}else
			if(position==1){
				convertView = myInflater.inflate(R.layout.rk_numtwo, null);
			}else 
				if(position==2){
					convertView = myInflater.inflate(R.layout.rk_numthree, null);
				}else{
					convertView = myInflater.inflate(R.layout.rk_numother, null);
					TextView num=(TextView) convertView.findViewById(R.id.number);
					num.setText(String.valueOf(position+1));
				}
		date=(TextView) convertView.findViewById(R.id.date);
		marks=(TextView) convertView.findViewById(R.id.marks);



		if(list!=null && marklist!=null)
		{

			Date d = new Date(Long.valueOf(list.get(position))*1000);
			String tsStr = "";
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			try {
				//方法一
				tsStr = sdf.format(d);


			} catch (Exception e) {
				e.printStackTrace();
			}


			date.setText(tsStr);
			marks.setText(String.valueOf(marklist.get(position)));
		}
		//建構listItem內容view
		//			viewTag = new ViewTag(
		//					
		//					(TextView)convertView.findViewById(R.id.lblListHeader)
		//					
		//					);

		//設置容器內容
		//			convertView.setTag(viewTag);
		//		}
		//		else{
		//			viewTag = (ViewTag) convertView.getTag();
		//		}


		//設定內容文字
		//		viewTag.title.setText(list.get(position));
		return convertView;
	}




	class ViewTag{

		TextView title;

		public ViewTag(TextView title){

			this.title = title;

		}

	}


}
