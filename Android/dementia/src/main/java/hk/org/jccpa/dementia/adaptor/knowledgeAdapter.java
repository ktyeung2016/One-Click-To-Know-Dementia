package hk.org.jccpa.dementia.adaptor;

import java.util.ArrayList;

import hk.org.jccpa.dementia.Global;
import hk.org.jccpa.dementia.Pages;
import hk.org.jccpa.dementia.R;
import hk.org.jccpa.dementia.object.knowledge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class knowledgeAdapter extends BaseAdapter{
	Context c;
	private LayoutInflater myInflater;

	ArrayList<knowledge> list=null;
	public knowledgeAdapter(Context c,ArrayList<knowledge> list){
		this.c=c;
		myInflater = LayoutInflater.from(c);
		this.list=list;

	}
	public knowledgeAdapter(Context c){
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
			convertView = myInflater.inflate(R.layout.knowledgelist_item, null);

			//建構listItem內容view
			viewTag = new ViewTag(

					(TextView)convertView.findViewById(R.id.lblListItem)

					);

			//設置容器內容
			convertView.setTag(viewTag);
		}
		else{
			viewTag = (ViewTag) convertView.getTag();
		}


		//設定內容文字
		if(Global.s.whichlang()){
			viewTag.title.setText(list.get(position).title_zh);

		}else{
			viewTag.title.setText(list.get(position).title_gb);
		}
		convertView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Global.knowledgeobject=list.get(position);
				Global.onAirAct.pushFragment(Pages.Knowledgedetail);
			}
		});

		return convertView;
	}




	class ViewTag{

		TextView title;

		public ViewTag(TextView title){

			this.title = title;

		}

	}


}
