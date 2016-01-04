package hk.org.jccpa.dementia.login;

import hk.org.jccpa.dementia.Global;
import hk.org.jccpa.dementia.Pages;
import hk.org.jccpa.dementia.R;

import com.example.basefamework.fontsize.Preferences;


import android.app.Dialog;
import android.app.Fragment;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class Tandc extends Fragment {
	View v;
	Button register,login,ag,disag;
	ImageView exit;
	PopupWindow pwindo;
	LinearLayout notagree;
	public void onCreate(Bundle savedInstanceState) {
		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);

		super.onCreate(savedInstanceState);
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);
		v = inflater.inflate(R.layout.tandc, container, false);
		exit=(ImageView) v.findViewById(R.id.exit);
		
		exit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				messageDialog();
				
			}
		});
		ag=(Button) v.findViewById(R.id.ag);
		ag.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Global.s.setispfirstuse(false);
				Global.onAirAct.clearBackStack();
				Global.onAirAct.pushFragment(Pages.Reallogin);
			}
		});
		disag=(Button) v.findViewById(R.id.disag);
		disag.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				messageDialog();
				
			}
		});
		return v;
	}
	public void messageDialog() {
		 
        final Dialog myDialog = new Dialog(getActivity());
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        myDialog.setContentView(R.layout.popup);
       
        myDialog.setCancelable(false);
       
      
        Button close = (Button) myDialog.findViewById(R.id.close);
        close.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
 
            	 myDialog.dismiss();
 
 
            } 
        }); 
 
       
        myDialog.show();
 
    } 
}
