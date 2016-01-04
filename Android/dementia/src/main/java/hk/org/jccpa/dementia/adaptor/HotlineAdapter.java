package hk.org.jccpa.dementia.adaptor;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import hk.org.jccpa.dementia.R;
import hk.org.jccpa.dementia.classpackage.Hotline;

import java.util.ArrayList;

public class HotlineAdapter extends BaseAdapter{
	Context c;
	private LayoutInflater myInflater;
	ImageLoader mImageLoader;
	DisplayImageOptions displayImageOptions;
	ArrayList<Hotline> list=null;
	public HotlineAdapter(Context c, ArrayList<Hotline> list){
		this.c=c;
		myInflater = LayoutInflater.from(c);
		this.list=list;
	}
	public HotlineAdapter(Context c){
		this.c=c;
		myInflater = LayoutInflater.from(c);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(list!=null){
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

		if(convertView == null){
			//取得listItem容器 view
			convertView = myInflater.inflate(R.layout.hotline_item, null);

			//建構listItem內容view
			viewTag = new ViewTag(
					(TextView)convertView.findViewById(R.id.label),
					(Button)convertView.findViewById(R.id.call)
					);

			//設置容器內容
			convertView.setTag(viewTag);
		}
		else{
			viewTag = (ViewTag) convertView.getTag();
		}

		if(list!=null){
			//設定內容文字
			viewTag.label.setText(list.get(position).getName());
			viewTag.call.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent callIntent = new Intent(Intent.ACTION_CALL);
					callIntent.setData(Uri.parse("tel:"+list.get(position).getPhone()));
					c.startActivity(callIntent);
				}
			});
		}
		return convertView;
	}



	class ViewTag{
		TextView label;
		Button call;

		public ViewTag(TextView label, Button call){
			this.label = label;
			this.call = call;
		}

	}

}
