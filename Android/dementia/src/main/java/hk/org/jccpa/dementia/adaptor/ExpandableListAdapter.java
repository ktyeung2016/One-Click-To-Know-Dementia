package hk.org.jccpa.dementia.adaptor;

import java.util.HashMap;
import java.util.List;

import hk.org.jccpa.dementia.R;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

	private Context _context;
	private List<String> _listDataHeader; // header titles
	// child data in format of header title, child title
	private HashMap<String, List<String>> _listDataChild;
	int lastExpandedGroupPosition;
	ExpandableListView expListView;
	@Override 
	public void onGroupExpanded(int groupPosition) {

		/*
		Toast.makeText(getActivity().getApplicationContext(),
				listDataHeader.get(groupPosition) + " Expanded",
				Toast.LENGTH_SHORT).show();
		 */
		if(groupPosition != lastExpandedGroupPosition){
			expListView.collapseGroup(lastExpandedGroupPosition);
			//			expListView.getChildAt(groupPosition).setBackgroundColor(Color.parseColor("#FFFFFF"));
			//			ImageView maker=(ImageView) expListView.getChildAt(lastExpandedGroupPosition).findViewById(R.id.marker);
			//
			//			maker.setImageResource(R.drawable.marker_socres);
		}

		super.onGroupExpanded(groupPosition);           
		lastExpandedGroupPosition = groupPosition;
		//		expListView.getChildAt(groupPosition).setBackgroundColor(Color.parseColor("#FBDC9C"));
		//		ImageView maker=(ImageView) expListView.getChildAt(groupPosition).findViewById(R.id.marker);
		//
		//
		//		maker.setImageResource(R.drawable.marker2_socres);


	} 
	public ExpandableListAdapter(Context context, List<String> listDataHeader,
			HashMap<String, List<String>> listChildData ,ExpandableListView expListView) {
		this._context = context;
		this._listDataHeader = listDataHeader;
		this._listDataChild = listChildData;
		this.expListView=expListView;
	}

	@Override
	public Object getChild(int groupPosition, int childPosititon) {
		return this._listDataChild.get(this._listDataHeader.get(groupPosition))
				.get(childPosititon);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		final String childText = (String) getChild(groupPosition, childPosition);

		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.list_item, null);
		}

		TextView txtListChild = (TextView) convertView
				.findViewById(R.id.lblListItem);

		txtListChild.setText(childText);
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return this._listDataChild.get(this._listDataHeader.get(groupPosition))
				.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return this._listDataHeader.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return this._listDataHeader.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		String headerTitle = (String) getGroup(groupPosition);

		if(!headerTitle.equals("tips")){
//			if (convertView == null) {
				LayoutInflater infalInflater = (LayoutInflater) this._context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = infalInflater.inflate(R.layout.list_group, null);
//			}
			//		ImageView maker=(ImageView) convertView.findViewById(R.id.marker);
			//		if(isExpanded){
			//
			//			maker.setImageResource(R.drawable.marker_socres);
			//		}else{
			//
			//			maker.setImageResource(R.drawable.marker_socres);
			//		}

			ImageView img_selection=(ImageView) convertView.findViewById(R.id.marker);
			int imageResourceId = isExpanded ? R.drawable.marker_socres
					: R.drawable.marker2_socres;
			img_selection.setImageResource(imageResourceId);
			if(isExpanded){
				convertView.setBackgroundColor(Color.parseColor("#FBDC9C"));
			}else{
				convertView.setBackgroundColor(Color.parseColor("#FFFFFF"));
			}
			TextView lblListHeader = (TextView) convertView
					.findViewById(R.id.lblListHeader);
			try{
				Log.d("headerTitle",headerTitle+"  +  yoyoyooy   "+lblListHeader);
				lblListHeader.setTypeface(null, Typeface.BOLD);
                lblListHeader.setText(headerTitle);
                lblListHeader.setContentDescription(headerTitle + this._context.getResources().getString(R.string.socialresGrpBtn));
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			Log.d("headerTitle", headerTitle);
//			if (convertView == null) {
				LayoutInflater infalInflater = (LayoutInflater) this._context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = infalInflater.inflate(R.layout.soctips, null);
//			}	
		}
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
