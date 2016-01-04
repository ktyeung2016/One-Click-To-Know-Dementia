package hk.org.jccpa.dementia;

import com.example.basefamework.fontsize.Preferences;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class Selftest extends Fragment {
	View v;
	ImageButton back,menu;
	Button yes,no;
	TextView qnum,question,a1,a2,a3;
	String [] ansString1,ansString2,ansString3,ansString4,ansString5,ansString6,ansString7,ansString8,ansString9,ansString10;
	int have=0;
	int ansnum=0;
	//	TextView ans1,ans2,ans3,ans4,ans5,ans6;
	public void onCreate(Bundle savedInstanceState) {

		//		new Preferences(getActivity()).setFontStyle(FontStyle.Small);
		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);
		super.onCreate(savedInstanceState);
		loadexample();
		Global.reset_selftestans();

	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);
		v = inflater.inflate(R.layout.activity_selftest, container, false);
		back=(ImageButton) v.findViewById(R.id.back);
		menu=(ImageButton) v.findViewById(R.id.menu);
		qnum=(TextView) v.findViewById(R.id.qnum);
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

		View holepage=v.findViewById(R.id.holepage);
		holepage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		question=(TextView) v.findViewById(R.id.question);
		a1=(TextView) v.findViewById(R.id.a1);
		a2=(TextView) v.findViewById(R.id.a2);
		a3=(TextView) v.findViewById(R.id.a3);


		qnum.setText(String.valueOf(Global.selftestqumber));
		yes=(Button) v.findViewById(R.id.yes);
		no=(Button) v.findViewById(R.id.no);
		set_the_text(Global.selftestqumber);
		yes.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(Global.selftestqumber%10!=0){
					
					Global.selftestans[Global.selftestqumber-1]=1;
					Global.selftestqumber++;
					
					qnum.setText(String.valueOf(Global.selftestqumber));
					set_the_text(Global.selftestqumber);
					
					have++;
				}else{
					Global.selftestans[Global.selftestqumber-1]=1;
					Global.selftestyesnum=have;
					Global.onAirAct.pushFragment(Pages.Selftestresult);
				}
			}
		});
		no.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(Global.selftestqumber%10!=0){
					Global.selftestqumber++;
					qnum.setText(String.valueOf(Global.selftestqumber));
					set_the_text(Global.selftestqumber);
				}else{
					Global.selftestyesnum=have;
					Global.onAirAct.pushFragment(Pages.Selftestresult);
				}
			}
		});
		return v;
	}
	public void loadexample(){
		ansString1=getActivity().getResources().getStringArray(R.array.selftest1content);
		ansString2=getActivity().getResources().getStringArray(R.array.selftest2content);
		ansString3=getActivity().getResources().getStringArray(R.array.selftest3content);
		ansString4=getActivity().getResources().getStringArray(R.array.selftest4content);
		ansString5=getActivity().getResources().getStringArray(R.array.selftest5content);
		ansString6=getActivity().getResources().getStringArray(R.array.selftest6content);
		ansString7=getActivity().getResources().getStringArray(R.array.selftest7content);
		ansString8=getActivity().getResources().getStringArray(R.array.selftest8content);
		ansString9=getActivity().getResources().getStringArray(R.array.selftest9content);
		ansString10=getActivity().getResources().getStringArray(R.array.selftest10content);
	}
	public void set_the_text(int qnum){
		switch(qnum){
		case 1:

			question.setText(R.string.selftest1);
			a1.setText(ansString1[0]);
			a2.setText(ansString1[1]);
			a3.setText(ansString1[2]);
			break;
		case 2:
			question.setText(R.string.selftest2);
			a1.setText(ansString2[0]);
			a2.setText(ansString2[1]);
			a3.setText(ansString2[2]);
			break;
		case 3:
			question.setText(R.string.selftest3);
			a1.setText(ansString3[0]);
			a2.setText(ansString3[1]);
			a3.setText(ansString3[2]);
			break;
		case 4:
			question.setText(R.string.selftest4);
			a1.setText(ansString4[0]);
			a2.setText(ansString4[1]);
			a3.setText(ansString4[2]);
			break;
		case 5:
			question.setText(R.string.selftest5);
			a1.setText(ansString5[0]);
			a2.setText(ansString5[1]);
			a3.setText(ansString5[2]);
			break;
		case 6:
			question.setText(R.string.selftest6);
			a1.setText(ansString6[0]);
			a2.setText(ansString6[1]);
			a3.setText(ansString6[2]);
			break;
		case 7:
			question.setText(R.string.selftest7);
			a1.setText(ansString7[0]);
			a2.setText(ansString7[1]);
			a3.setText(ansString7[2]);
			break;
		case 8:
			question.setText(R.string.selftest8);
			a1.setText(ansString8[0]);
			a2.setText(ansString8[1]);
			a3.setText(ansString8[2]);
			break;
		case 9:
			question.setText(R.string.selftest9);
			a1.setText(ansString9[0]);
			a2.setText(ansString9[1]);
			a3.setText(ansString9[2]);
			break;
		case 10:
			question.setText(R.string.selftest10);
			a1.setText(ansString10[0]);
			a2.setText(ansString10[1]);
			a3.setText(ansString10[2]);
			break;
		}
	}
}
