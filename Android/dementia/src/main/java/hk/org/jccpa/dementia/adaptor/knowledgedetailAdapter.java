package hk.org.jccpa.dementia.adaptor;

import java.util.ArrayList;

import hk.org.jccpa.dementia.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class knowledgedetailAdapter extends BaseAdapter{
	Context c;
	private LayoutInflater myInflater;

	ArrayList<String> list=null;
	public knowledgedetailAdapter(Context c,ArrayList<String> list){
		this.c=c;
		myInflater = LayoutInflater.from(c);
		this.list=list;
		
	}
	public knowledgedetailAdapter(Context c){
		this.c=c;
		myInflater = LayoutInflater.from(c);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		
			return list.size();
	
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

		if(convertView == null){
			//取得listItem容器 view
			convertView = myInflater.inflate(R.layout.list_item_knowledgedetail, null);

			//建構listItem內容view
			viewTag = new ViewTag(
					
					(TextView)convertView.findViewById(R.id.lblListHeader)
					
					);

			//設置容器內容
			convertView.setTag(viewTag);
		}
		else{
			viewTag = (ViewTag) convertView.getTag();
		}


		//設定內容文字
		viewTag.title.setText(list.get(position));
		return convertView;
	}
	


	
	class ViewTag{
		
		TextView title;
		
		public ViewTag(TextView title){
		
			this.title = title;
			
		}
		
	}


}
