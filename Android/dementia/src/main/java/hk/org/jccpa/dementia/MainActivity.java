package hk.org.jccpa.dementia;

import android.os.Bundle;


import java.io.File;
import java.io.IOException;
import java.util.Locale;

import hk.org.jccpa.dementia.beforelogin.Launchpage;
import hk.org.jccpa.dementia.game.Gamediff;
import hk.org.jccpa.dementia.game.Gamemenu;
import hk.org.jccpa.dementia.game.Gamerabkboard;
import hk.org.jccpa.dementia.game.Gamerkmenu;
import hk.org.jccpa.dementia.game.Levelup;
import hk.org.jccpa.dementia.game1.Endgame1;
import hk.org.jccpa.dementia.game1.Game1demo;
import hk.org.jccpa.dementia.game1.game1Gamerankboard;
import hk.org.jccpa.dementia.game1.game_one_1;
import hk.org.jccpa.dementia.game1.game_one_2;
import hk.org.jccpa.dementia.game1.game_one_3;
import hk.org.jccpa.dementia.game2.Endgame2;
import hk.org.jccpa.dementia.game2.Game2demo;
import hk.org.jccpa.dementia.game2.Game_two_1;
import hk.org.jccpa.dementia.game2.game2Gamerankboard;
import hk.org.jccpa.dementia.game3.Endgame3;
import hk.org.jccpa.dementia.game3.Game_three_1;
import hk.org.jccpa.dementia.game3.game3Gamerankboard;
import hk.org.jccpa.dementia.game3.game3demo;
import hk.org.jccpa.dementia.game4.Endgame4;
import hk.org.jccpa.dementia.game4.Game_four_1;
import hk.org.jccpa.dementia.game4.game4Gamerankboard;
import hk.org.jccpa.dementia.game4.game4demo;
import hk.org.jccpa.dementia.game5.Endgame5;
import hk.org.jccpa.dementia.game5.Game_five_1;
import hk.org.jccpa.dementia.game5.Game_five_2;
import hk.org.jccpa.dementia.game5.Game_five_3;
import hk.org.jccpa.dementia.game5.Gamefivedemo;
import hk.org.jccpa.dementia.game5.game5Gamerankboard;
import hk.org.jccpa.dementia.homepage.Homepage1;
import hk.org.jccpa.dementia.knowledge.Knowledgedetail;
import hk.org.jccpa.dementia.knowledge.Kowledgecenter;
import hk.org.jccpa.dementia.login.Login;
import hk.org.jccpa.dementia.login.Reallogin;
import hk.org.jccpa.dementia.login.Register;
import hk.org.jccpa.dementia.login.Tandc;
import hk.org.jccpa.dementia.setting.Setting;
import hk.org.jccpa.dementia.map.Callfmy;
import hk.org.jccpa.dementia.map.Editfmyinfo;
import hk.org.jccpa.dementia.map.Map;
import hk.org.jccpa.dementia.map.Mapdemo1;
import hk.org.jccpa.dementia.map.Mapdemo2;
import hk.org.jccpa.dementia.map.Mapdemo3;
import hk.org.jccpa.dementia.map.Mapdemo4;
import hk.org.jccpa.dementia.map.MyService;
import hk.org.jccpa.dementia.map.Newafmymem;
import hk.org.jccpa.dementia.sql.MyDBHelper_callfmy;


import com.example.basefamework.fontsize.FontStyle;
import com.example.basefamework.fontsize.Preferences;
import com.navdrawer.SimpleSideDrawer;




import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {
	public Context ctx = this;
	public static String tag ="";

	public static int lang=0;
	public static String token=null;
	//Sliding menu
	String[] menu;
	DrawerLayout dLayout;
	ListView dList;
	ArrayAdapter<String> adapter;
	public SimpleSideDrawer mSlidingMenu;
	MyDBHelper_callfmy dbhelper;
	public MediaPlayer player;
	//map demo array
	Handler h;
	Runnable r;
	public static String[] mapdemo;
	//	public static String token;


	private MyService mBoundService;
	private boolean mIsBound;

	private ServiceConnection mConnection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// 当进程崩溃时将被调用，因为运行在同一程序，如果是崩溃将所以永远不会发生
			// 当解除绑定时也被调用
			mBoundService = null;
			//			Toast.makeText(LocalActivity.this,
			//					R.string.local_service_disconnected, Toast.LENGTH_SHORT)
			//					.show();

		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// service连接建立时将调用该方法
			mBoundService = ((MyService.LocalBinder) service).getService();
			//			Toast.makeText(LocalActivity.this,
			//					R.string.local_service_connected, Toast.LENGTH_SHORT)
			//					.show();
		}


	};

	void doBindService() {
		// 建立service连接。因为我们知道程序会运行在本地里，因此使用显示的类名来实现service
		// （但是不支持跟其他程序交互）
		// 两种传递，一种是在manifest里写好intent-filter的action，一种是显示传递
		// bindService(new Intent("com.LocalService.LocalService"), mConnection,
		// Context.BIND_AUTO_CREATE);
		//	         bindService(new Intent(LocalActivity.this, LocalService.class),
		//	         mConnection, Context.BIND_AUTO_CREATE);

		//如果用这种方法将会调用onStartCommand方法
		startService(new Intent(MainActivity.this, MyService.class));
		mIsBound = true;
	}

	void doUnbindService() {
		if (mIsBound) {
			// Detach our existing connection.
			stopService(new Intent(MainActivity.this, MyService.class));
			//	            unbindService(mConnection);
			mIsBound = false;
		}
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		mapdemo=getResources().getStringArray(R.array.mapdemo);
		Global.s=new sharepreference(this);
		Global.isfirsttimelogin=true;
		//		Global.s.game5status(1);
		if(Global.s.whichsize()){
			new Preferences(this).setFontStyle(FontStyle.Medium);
		}else{
			new Preferences(this).setFontStyle(FontStyle.Small);
		}
		if(Global.s.whichlang()){
			//			mode.getChildAt(mode.indexOfChild((RadioButton) v.findViewById(R.id.full)));
			changeLocale("zh");
		}else{
			changeLocale("cn");
		}
		getTheme().applyStyle(new Preferences(this).getFontStyle().getResId(), true);

		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getActionBar().hide();
		final Preferences prefs = new Preferences(this);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		setContentView(R.layout.activity_main);

		Global.onAirAct = this;
		//		mSlidingMenu.findViewById(R.id.yoyoyo).setOnClickListener(new View.OnClickListener() {
		//			
		//			@Override
		//			public void onClick(View v) {
		//				// TODO Auto-generated method stub
		//				finish();
		//			}
		//		});

		//testing...

		Log.d("size", "size"+prefs.getFontStyle().ordinal());

		//....

		//		mSlidingMenu = new SimpleSideDrawer( this );
		//		mSlidingMenu.setRightBehindContentView( R.layout.activity_a12313 );



		//		if(isNetworkOnline()){
		//		menu = new String[]{"Home","Android","Windows","Linux","Raspberry Pi","WordPress","Videos","Contact Us"};
		//		dLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		//		dList = (ListView) findViewById(R.id.left_drawer);
		//		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,menu);
		//		dList.setAdapter(adapter);
		//		dList.setSelector(android.R.color.holo_blue_dark);
		//		dList.setOnItemClickListener(new OnItemClickListener(){
		//			//
		//			//				@Override
		//			public void onItemClick(AdapterView<?> parent, View view,
		//					int position, long id) {
		//				// TODO Auto-generated method stub
		//				dLayout.closeDrawers();
		//				Bundle args = new Bundle();
		//				args.putString("Menu", menu[position]);
		//				//					Fragment detail = new DetailFragment();
		//				//					detail.setArguments(args);
		//				//					FragmentManager fragmentManager = getFragmentManager();
		//				//					fragmentManager.beginTransaction().replace(R.id.container, detail).commit();
		//			}
		//		});



		chdrawer();


		//		mSlidingMenu = new SimpleSideDrawer( this );
		//		if(!Global.s.whichmode()){
		//			mSlidingMenu.setRightBehindContentView( R.layout.manulayout2);
		//		}else{
		//			mSlidingMenu.setRightBehindContentView( R.layout.manulayout3);
		//		}



		Intent newAct = new Intent();
		newAct.setClass( MainActivity.this, Launchpage.class );

		// 呼叫新的 Activity Class
		startActivity( newAct );
		//		}else{
		//			new AlertDialog.Builder(this)
		//			.setTitle("沒有網絡／没有网络")
		//			.setMessage("請連接網絡!／请连接网络!")
		//			.setIcon(android.R.drawable.ic_dialog_alert)
		//			.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
		//
		//				public void onClick(DialogInterface dialog, int whichButton) {
		//					finish();
		//
		//				}
		//			}).show();
		//		}
		//		dbhelper = new MyDBHelper(this);//執行Helper類,會建立demo資料庫，和有三個欄位NAME、TEL、EMAIL 的 friends 資料表 .


		File wallpaperDirectory = new File(Global.path);
		Log.d("path", Global.path);
		// have the object build the directory structure, if needed. 
		wallpaperDirectory.mkdirs();
		player = MediaPlayer.create(this, R.raw.jccpa_app_bgm);
		player.setAudioStreamType(AudioManager.STREAM_MUSIC);
		try {
			player.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//		try {
		//			player.prepareAsync();
		//		} catch (IllegalStateException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}
		//		Intent startIntent = new Intent(this, MyService.class);
		//
		//		this.startService(startIntent); 
		if(MyService.start==false){
			Intent startIntent = new Intent(this, MyService.class);

			this.startService(startIntent); 
			//						Calendar cal = Calendar.getInstance();
			//						cal.add(Calendar.MINUTE, Global.s.updaterate());
			//			//			cal.add(Calendar.MINUTE, s.updaterate());
			//			//			cal.add(Calendar.SECOND, 1);
			//						AlarmIntent.setUpAsAlarm_Service(this, cal);
		}
	}

	public void onResume(){
		super.onResume();
		h=new Handler();
		if(Global.s.isplaymusic()){
			player.start();
			player.setLooping(true);
		}
		//		r=new Runnable(){
		//
		//			@Override
		//			public void run() {
		//				// TODO Auto-generated method stub
		//				if(Global.s.isplaymusic()){
		//					if(!player.isPlaying()){
		//						player.start();
		//						player.setLooping(true);
		//					}
		//				}
		//				h.postDelayed(this, 5000);
		//			}
		//
		//		};
		//		h.postDelayed(r, 5000);
		if(Global.login==true && Global.afterlogin==false){
			//			pushFragment(Pages.Login);
			pushFragment(Pages.Reallogin);
		}
	}
    public void pushFragmentBackTo(int newFragTag) {
        pushFragment(newFragTag,newFragTag);
    }
    public void pushFragment(int newFragTag, int tagBackStackTo) {
        clearBackStackTo(Integer.toString(tagBackStackTo));
        pushFragment(newFragTag);
    }
    public void pushFragment(int newFragTag) {

		Fragment fragment = null;
		Global.whichpage=newFragTag;
		switch (newFragTag){
		case Pages.Homepage1: fragment = new Homepage1(); break;
		case Pages.Reallogin: fragment = new Reallogin(); break;
		case Pages.Register:fragment = new Register(); break;
		case Pages.Login:fragment = new Login(); break;
		case Pages.Map:fragment = new Map(); break;
		case Pages.Callfmy:fragment = new Callfmy(); break;
		case Pages.Newafmymem:fragment = new Newafmymem(); break;
		case Pages.Editfmyinfo:fragment = new Editfmyinfo(); break;
		case Pages.Setting:fragment = new Setting(); break;
		case Pages.Socres:fragment = new Socres(); break;
		case Pages.Selftest:fragment =new Selftest();break;
		case Pages.Selftestresult:fragment =new Selftestresult();break;
		case Pages.Kowledgecenter:fragment =new Kowledgecenter();break;
		case Pages.Knowledgedetail:fragment =new Knowledgedetail();break;
		case Pages.Gamemenu:fragment =new Gamemenu();break;
		case Pages.Gamerkmenu:fragment =new Gamerkmenu();break;
		case Pages.Gamerabkboard:fragment =new Gamerabkboard();break;
		case Pages.Gamefivedemo:fragment =new Gamefivedemo();break;
		case Pages.Gamediff:fragment =new Gamediff();break;
		case Pages.Game_five_1:fragment=new Game_five_1();break;
		case Pages.Game_five_2:fragment=new Game_five_2();break;

		case Pages.Game_five_3:fragment=new Game_five_3();break;
		case Pages.Game_two_1:fragment=new Game_two_1();break;
		case Pages.Levelup:fragment=new Levelup();break;
		case Pages.Endgame5:fragment=new Endgame5();break;
		case Pages.game5Gamerankboard:fragment=new game5Gamerankboard();break;
		case Pages.Endgame2:fragment=new Endgame2();break;
		case Pages.game2Gamerankboard:fragment=new game2Gamerankboard();break;
		case Pages.Game2demo:fragment=new Game2demo();break;
		case Pages.	Game_four_1:fragment=new Game_four_1();break;
		case Pages.	 Endgame4:fragment=new Endgame4();break;
		case Pages.	 game4Gamerankboard:fragment=new game4Gamerankboard();break;
		case Pages.	game4demo:fragment=new game4demo();break;
		case Pages.	game_one_1:fragment=new game_one_1();break;
		case Pages.	game_one_2:fragment=new game_one_2();break;
		case Pages.	game_one_3:fragment=new game_one_3();break;
		case Pages.Endgame1:fragment=new Endgame1();break;
		case Pages.game1Gamerankboard:fragment=new game1Gamerankboard();break;
		case Pages.Game1demo:fragment=new Game1demo();break;
		case Pages.Game_three_1:fragment=new Game_three_1();break;
		case Pages.game3Gamerankboard:fragment=new game3Gamerankboard();break;
		case Pages.Endgame3:fragment=new Endgame3();break;
		case Pages.game3demo:fragment=new game3demo();break;
		case Pages.Mapdemo1:fragment=new Mapdemo1();break;
		case Pages.Mapdemo2:fragment=new Mapdemo2();break;
		case Pages.Mapdemo3:fragment=new Mapdemo3();break;
		case Pages.Mapdemo4:fragment=new Mapdemo4();break;
		case Pages.Socresdetail:fragment=new Socresdetail();break;
		case Pages.Tandc:fragment=new Tandc();break;
		//		case Pages.FBLOGIN:fragment = new Fblogin(); break;
		default:break;
		};
		tag = Integer.toString(newFragTag);
		if (fragment!=null){
			FragmentManager fm = getFragmentManager();
			FragmentTransaction transaction = fm.beginTransaction();

			transaction.addToBackStack(tag);

			Log.d("BackStack",fm.getBackStackEntryCount()+"");
			//			transaction.setCustomAnimations(R.animator.fade_left, R.animator.fade_right);
			transaction.replace(R.id.container, fragment ,tag);
			transaction.commit();
		}else{
			Log.d("pushFragment","null Fragment");
		}
	}
	public void onPause(){
		super.onPause();
		h.removeCallbacks(r);
		if(Global.s.isplaymusic()){
			player.pause();
		}
	}
	public void pushFragmentnotag(int newFragTag) {
		//cannot back
		Fragment fragment = null;
		Global.whichpage=newFragTag;
		switch (newFragTag){
		case Pages.Homepage1: fragment = new Homepage1(); break;
		case Pages.Reallogin: fragment = new Reallogin(); break;
		case Pages.Register:fragment = new Register(); break;
		case Pages.Login:fragment = new Login(); break;
		case Pages.Map:fragment = new Map(); break;
		case Pages.Callfmy:fragment = new Callfmy(); break;
		case Pages.Newafmymem:fragment = new Newafmymem(); break;
		case Pages.Editfmyinfo:fragment = new Editfmyinfo(); break;
		case Pages.Setting:fragment = new Setting(); break;
		case Pages.Socres:fragment = new Socres(); break;
		case Pages.Selftest:fragment =new Selftest();break;
		case Pages.Selftestresult:fragment =new Selftestresult();break;
		case Pages.Kowledgecenter:fragment =new Kowledgecenter();break;
		case Pages.Knowledgedetail:fragment =new Knowledgedetail();break;
		case Pages.Gamemenu:fragment =new Gamemenu();break;
		case Pages.Gamerkmenu:fragment =new Gamerkmenu();break;
		case Pages.Gamerabkboard:fragment =new Gamerabkboard();break;
		case Pages.Gamefivedemo:fragment =new Gamefivedemo();break;
		case Pages.Gamediff:fragment =new Gamediff();break;
		case Pages.Game_five_1:fragment=new Game_five_1();break;
		case Pages.Game_five_2:fragment=new Game_five_2();break;
		case Pages.Game_five_3:fragment=new Game_five_3();break;
		case Pages.Game_two_1:fragment=new Game_two_1();break;
		case Pages.Levelup:fragment=new Levelup();break;
		case Pages.Endgame5:fragment=new Endgame5();break;
		case Pages.game5Gamerankboard:fragment=new game5Gamerankboard();break;
		case Pages.Endgame2:fragment=new Endgame2();break;
		case Pages.game2Gamerankboard:fragment=new game2Gamerankboard();break;
		case Pages.Game2demo:fragment=new Game2demo();break;
		case Pages.	Game_four_1:fragment=new Game_four_1();break;
		case Pages.	 Endgame4:fragment=new Endgame4();break;
		case Pages.	 game4Gamerankboard:fragment=new game4Gamerankboard();break;
		case Pages.	game4demo:fragment=new game4demo();break;
		case Pages.	game_one_1:fragment=new game_one_1();break;
		case Pages.	game_one_2:fragment=new game_one_2();break;
		case Pages.	game_one_3:fragment=new game_one_3();break;
		case Pages.Endgame1:fragment=new Endgame1();break;
		case Pages.game1Gamerankboard:fragment=new game1Gamerankboard();break;
		case Pages.Game1demo:fragment=new Game1demo();break;
		case Pages.Game_three_1:fragment=new Game_three_1();break;
		case Pages.game3Gamerankboard:fragment=new game3Gamerankboard();break;
		case Pages.Endgame3:fragment=new Endgame3();break;
		case Pages.game3demo:fragment=new game3demo();break;
		case Pages.Mapdemo1:fragment=new Mapdemo1();break;
		case Pages.Mapdemo2:fragment=new Mapdemo2();break;
		case Pages.Mapdemo3:fragment=new Mapdemo3();break;
		case Pages.Mapdemo4:fragment=new Mapdemo4();break;
		case Pages.Socresdetail:fragment=new Socresdetail();break;
		case Pages.Tandc:fragment=new Tandc();break;
		default:break;
		};
		if (fragment!=null){
			FragmentManager fm = getFragmentManager();
			FragmentTransaction transaction = fm.beginTransaction();
			Log.d("BackStack",fm.getBackStackEntryCount()+"");
			//			transaction.setCustomAnimations(R.animator.fade_left, R.animator.fade_right);
			transaction.replace(R.id.container, fragment ,tag);
			transaction.commit();
		}else{
			Log.d("pushFragment","null Fragment");
		}
	}




	public void backFragment() {
		Log.d("MainActivity", "backFragment");
		if(getFragmentManager().getBackStackEntryCount()<=1){
			this.finish();

		}else{
			Log.d("getFragmentManager",""+getFragmentManager().getBackStackEntryCount());
			getFragmentManager().popBackStack();
		}


	}

    public void clearBackStack() {
        Log.d("MainActivity", "clearBackStack");
        FragmentManager fm = getFragmentManager();

        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            //			Log.d("Main-clearBackStack",fm.getBackStackEntryAt(i).getName());
            fm.popBackStack();
        }
    }
    public void clearBackStackTo(String name) {
        //clearBackStackTo(name, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        clearBackStackTo(name, 0);
    }
    public void clearBackStackTo(String name, int flags) {
        Log.d("MainActivity", "clearBackStackTo("+name+","+flags+")");
        FragmentManager fm = getFragmentManager();
        fm.popBackStack(name, flags);
    }

	public void clearOneBackStack() {
		Log.d("MainActivity", "clearOneBackStack");
		FragmentManager fm = getFragmentManager();
		fm.popBackStack();

	}



	public void cleanupClose(){
		Log.d("MainActivity", "cleanupClose");

		onDestroy();
	}

	public void onBackPressed (){
		switch(Global.whichpage){
		case Pages.Selftest:
			Global.onAirAct.clearBackStack();
			Global.onAirAct.pushFragment(Pages.Homepage1);
			break;
		case Pages.Selftestresult:
		
			Global.onAirAct.clearBackStack();
			Global.onAirAct.pushFragmentnotag(Pages.Homepage1);
			break;
		case Pages.Socres:
			Global.onAirAct.clearBackStack();
			Global.onAirAct.pushFragment(Pages.Homepage1);
			break;
		case Pages.Socresdetail:
			Global.onAirAct.clearBackStack();
			Global.onAirAct.pushFragment(Pages.Socres);
			break;

		case Pages.Gamediff:
			Global.onAirAct.clearOneBackStack();
			break;
		case Pages.Gamemenu:
			Global.onAirAct.clearBackStack();
			Global.onAirAct.pushFragment(Pages.Homepage1);
			break;
		case Pages.Gamerabkboard:
			Global.onAirAct.clearOneBackStack();
			break;
		case Pages.Gamerkmenu:
			Global.onAirAct.clearOneBackStack();
			break;
		case Pages.Endgame1:
			Global.onAirAct.clearBackStack();
			Global.onAirAct.pushFragment(Pages.Gamemenu);
			break;
		case Pages.game_one_1:
		case Pages.Game_two_1:
		case Pages.Game_three_1:
		case Pages.Game_four_1:
		case Pages.Game_five_1:
		case Pages.Game_five_2:
		case Pages.Game_five_3:
			Global.onAirAct.clearBackStack();
			Global.onAirAct.pushFragment(Pages.Gamemenu);
			break;
		case Pages.game_one_2:
			Global.onAirAct.clearBackStack();
			Global.onAirAct.pushFragment(Pages.Gamemenu);
			break;
		case Pages.game_one_3:
			Global.onAirAct.clearBackStack();
			Global.onAirAct.pushFragment(Pages.Gamemenu);
			break;
		case Pages.Game1demo:
			if(Global.s.getgame1status()>-1){
				Global.heart=5;
				Global.right5=0;
				Global.gameqnum=0;
				Global.gamenum=1;
				Global.thingsbought=0;
				Game1demo.Stage=0;
				Global.totalscore=100;
				//				Global.s.game1status(-1);
				Global.star=0;
                clearBackStackTo(Integer.toString(Pages.game_one_1));
				Global.onAirAct.pushFragment(Pages.game_one_1);
			}else{
                clearBackStackTo(Integer.toString(Pages.Gamemenu));
				Global.onAirAct.pushFragment(Pages.Gamemenu);
			}
			break;
		case Pages.game1Gamerankboard:
			Global.onAirAct.clearOneBackStack();
//			Global.onAirAct.pushFragment(Pages.Endgame1);
			break;
		case Pages.game2Gamerankboard:
			Global.onAirAct.clearOneBackStack();
//			Global.onAirAct.pushFragment(Pages.Endgame2);
			break;
		case Pages.game3Gamerankboard:
			Global.onAirAct.clearOneBackStack();
//			Global.onAirAct.pushFragment(Pages.Endgame3);
			break;
		case Pages.game4Gamerankboard:
			Global.onAirAct.clearOneBackStack();
//			Global.onAirAct.pushFragment(Pages.Endgame4);
			break;
		case Pages.game5Gamerankboard:
			Global.onAirAct.clearOneBackStack();
//			Global.onAirAct.pushFragment(Pages.Endgame5);
			break;
		case Pages.Endgame2:
		case Pages.Endgame3:
		case Pages.Endgame4:
		case Pages.Endgame5:
			Global.onAirAct.clearBackStack();
			Global.onAirAct.pushFragment(Pages.Gamemenu);
			break;
		case Pages.Game2demo:
			if(Global.s.getgame2status()==-1){
				Global.heart=5;
				Global.right5=0;
				Global.gameqnum=0;
				Global.gamenum=2;
				Game2demo.Stage=0;
				Global.totalscore=100;
				Global.onAirAct.pushFragment(Pages.Gamemenu);
			}else{
				Global.onAirAct.pushFragment(Pages.Game_two_1);
			}
			break;
		case Pages.game3demo:
			if(Global.s.getgame3status()!=-1){
				Global.heart=5;
				Global.right5=0;
				game3demo.Stage=0;
				Global.gameqnum=0;
				Global.gamenum=3;
				Global.totalscore=100;
				Global.onAirAct.pushFragment(Pages.Game_three_1);
			}else{
				Global.onAirAct.pushFragment(Pages.Gamemenu);
			}
			break;
		case Pages.game4demo:
			if(Global.s.getgame4status()!=-1){
				Global.heart=5;
				Global.right5=0;
				game4demo.Stage=0;
				Global.gameqnum=0;
				Global.gamenum=4;
				Global.totalscore=100;
				Global.onAirAct.pushFragment(Pages.Game_four_1);
			}else{
				Global.onAirAct.pushFragment(Pages.Gamemenu);
			}
			break;
		case Pages.Gamefivedemo:
			if(Global.s.getgame5status()==-1){
				Global.onAirAct.pushFragment(Pages.Gamemenu);
			}else{
				Global.heart=5;
				Global.right5=0;
				Global.gameqnum=0;
				Global.game5budget=0;
				Global.gamenum=5;
				Gamefivedemo.Stage=0;
				Global.totalscore=100;
				Global.onAirAct.pushFragment(Pages.Game_five_1);
			}
			break;
		case Pages.Knowledgedetail:
			Global.onAirAct.clearBackStack();
			Global.onAirAct.pushFragment(Pages.Kowledgecenter);
			break;
		case Pages.Kowledgecenter:
			Global.onAirAct.clearBackStack();
			Global.onAirAct.pushFragment(Pages.Homepage1);
			break;
		case Pages.Callfmy:
			Global.onAirAct.clearBackStack();
			Global.onAirAct.pushFragment(Pages.Map);
			break;
		case Pages.Newafmymem:
		case Pages.Editfmyinfo:
			Global.onAirAct.clearBackStack();
			Global.onAirAct.pushFragment(Pages.Callfmy);
			break;
		
		case Pages.Map:
			Global.onAirAct.clearBackStack();
			//		Global.onAirAct.clearOneBackStack();
			Global.onAirAct.pushFragment(Pages.Homepage1);
			break;
		case Pages.Mapdemo1:
		case Pages.Mapdemo2:
		case Pages.Mapdemo3:
		case Pages.Mapdemo4:
			Global.onAirAct.clearBackStack();
			//				Global.onAirAct.clearOneBackStack();
			Global.onAirAct.pushFragment(Pages.Homepage1);
			break;
		case Pages.Setting:
			Global.onAirAct.clearBackStack();
			Global.onAirAct.pushFragment(Pages.Homepage1);
			break;
		default:
			new AlertDialog.Builder(this)
			.setTitle(R.string.exit)
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int whichButton) {
					clearBackStack();
					Global.login=false;
					Global.afterlogin=false;
					//				Global.onAirAct.pushFragmentnotag(Pages.LANDING);

					finish();
					System.exit(0);
				}}).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {

					}}).show();
			break;
		}
	}

	protected void onDestroy(){

		super.onDestroy();
		//		dbhelper.close();
		if(player.isPlaying()){
			player.stop();	
		}
		//		doUnbindService();
		Global.afterlogin=false;
		//		//		if(MyService.start==false){
		Intent startIntent = new Intent(this, MyService.class);
		//
		this.startService(startIntent); 
		//		//		}
		Log.d("MainActivity", "onDestroy");
	}



	private void ShowMsgDialog(String Msg)
	{
		Builder MyAlertDialog = new AlertDialog.Builder(this);
		MyAlertDialog.setTitle("TimeOut");
		MyAlertDialog.setMessage(Msg);
		DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		};
		MyAlertDialog.setNeutralButton("OK",OkClick );
		MyAlertDialog.setOnCancelListener(new OnCancelListener(){

			@Override
			public void onCancel(DialogInterface dialog) {
				// TODO Auto-generated method stub
				finish();
			}

		});
		MyAlertDialog.show();
	}
	public boolean isNetworkOnline() { 
		boolean status=false;
		try{ 
			ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo netInfo = cm.getNetworkInfo(0);
			if (netInfo != null && netInfo.getState()==NetworkInfo.State.CONNECTED) {
				status= true;
			}else { 
				netInfo = cm.getNetworkInfo(1);
				if(netInfo!=null && netInfo.getState()==NetworkInfo.State.CONNECTED)
					status= true;
			} 
		}catch(Exception e){
			e.printStackTrace();  
			return false; 
		} 
		return status;

	} 
	public void changeLocale(String lan){
		Locale locale = new Locale(lan);
		Locale.setDefault(locale);
		Configuration config = new Configuration();
		config.locale = locale;
		this.getBaseContext().getResources().updateConfiguration(config, this.getBaseContext().getResources().getDisplayMetrics());
    }
    public void chdrawer(){
        mSlidingMenu = new SimpleSideDrawer( this );

		if(!Global.s.whichmode()){
			mSlidingMenu.setRightBehindContentView( R.layout.manulayout2);
			ImageView mylocation=(ImageView) mSlidingMenu.findViewById(R.id.mylocation);
			mylocation.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(!Global.onAirAct.mSlidingMenu.isClosed()){
						mSlidingMenu.toggleRightDrawer();
//						Global.onAirAct.clearBackStack();
//						Global.onAirAct.pushFragment(Pages.Map);
						Global.mapdemosteps=0;
						if(!Global.s.userid().equals("no_user")){
							if(Global.onAirAct.isNetworkOnline()){
								if(Global.s.isfirstmap()){
									Global.onAirAct.pushFragment(Pages.Mapdemo1);
								}else{
									Global.onAirAct.pushFragment(Pages.Map);
								}
							}else{
								Toast.makeText(MainActivity.this, getResources().getString(R.string.nogps), Toast.LENGTH_LONG).show();	
							}
						}else{
							ShowMsgDialog();
						}
					}
				}
			});
			TextView selfas=(TextView) mSlidingMenu.findViewById(R.id.selfas);
			selfas.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(!Global.onAirAct.mSlidingMenu.isClosed()){
						mSlidingMenu.toggleRightDrawer();
						Global.onAirAct.clearBackStack();
						Global.onAirAct.pushFragment(Pages.Selftest);
					}
				}
			});
			TextView socialres=(TextView) mSlidingMenu.findViewById(R.id.socialres);
			socialres.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(!Global.onAirAct.mSlidingMenu.isClosed()){
						mSlidingMenu.toggleRightDrawer();
                        Global.onAirAct.clearBackStack();
						Global.onAirAct.pushFragment(Pages.Socres);
					}
				}
			});
			TextView mylocation2=(TextView) mSlidingMenu.findViewById(R.id.mylocation2);
			mylocation2.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(!Global.onAirAct.mSlidingMenu.isClosed()){
						mSlidingMenu.toggleRightDrawer();
//						Global.onAirAct.clearBackStack();
//						Global.onAirAct.pushFragment(Pages.Map);
						Global.mapdemosteps=0;
						if(!Global.s.userid().equals("no_user")){
							if(Global.onAirAct.isNetworkOnline()){
								if(Global.s.isfirstmap()){
									Global.onAirAct.pushFragment(Pages.Mapdemo1);
								}else{
									Global.onAirAct.pushFragment(Pages.Map);
								}
							}else{
								Toast.makeText(MainActivity.this, getResources().getString(R.string.nogps), Toast.LENGTH_LONG).show();	
							}
						}else{
							ShowMsgDialog();
						}
					}
				}
			});
			TextView knowledge=(TextView) mSlidingMenu.findViewById(R.id.knowledge);
			knowledge.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(!Global.onAirAct.mSlidingMenu.isClosed()){
						mSlidingMenu.toggleRightDrawer();
						Global.onAirAct.clearBackStack();
						Global.onAirAct.pushFragment(Pages.Kowledgecenter);
					}
				}
			});
			TextView home=(TextView) mSlidingMenu.findViewById(R.id.home);
			home.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(!Global.onAirAct.mSlidingMenu.isClosed()){
						mSlidingMenu.toggleRightDrawer();
						Global.onAirAct.clearBackStack();
						Global.onAirAct.pushFragment(Pages.Homepage1);
					}
				}
			});

			TextView baingame=(TextView) mSlidingMenu.findViewById(R.id.baingame);
			baingame.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(!Global.onAirAct.mSlidingMenu.isClosed()){
						mSlidingMenu.toggleRightDrawer();
						Global.onAirAct.clearBackStack();
						Global.onAirAct.pushFragment(Pages.Gamemenu);
					}
				}
			});
			TextView callfmy=(TextView) mSlidingMenu.findViewById(R.id.callfmy);
			callfmy.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(!Global.onAirAct.mSlidingMenu.isClosed()){
						mSlidingMenu.toggleRightDrawer();
						Global.onAirAct.clearBackStack();
						Global.onAirAct.pushFragment(Pages.Callfmy);
					}
				}
			});
			TextView setting=(TextView) mSlidingMenu.findViewById(R.id.setting);
			setting.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(!Global.onAirAct.mSlidingMenu.isClosed()){
						mSlidingMenu.toggleRightDrawer();
						Global.onAirAct.clearBackStack();
						Global.onAirAct.pushFragment(Pages.Setting);
					}
				}
			});
		}else{
			mSlidingMenu.setRightBehindContentView( R.layout.manulayout3);
			ImageView mylocation=(ImageView) mSlidingMenu.findViewById(R.id.mylocation);
			mylocation.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(!Global.onAirAct.mSlidingMenu.isClosed()){
						mSlidingMenu.toggleRightDrawer();
//						Global.onAirAct.clearBackStack();
//						Global.onAirAct.pushFragment(Pages.Map);
						Global.mapdemosteps=0;
						if(!Global.s.userid().equals("no_user")){
							if(Global.onAirAct.isNetworkOnline()){
								if(Global.s.isfirstmap()){
									Global.onAirAct.pushFragment(Pages.Mapdemo1);
								}else{
									Global.onAirAct.pushFragment(Pages.Map);
								}
							}else{
								Toast.makeText(MainActivity.this, getResources().getString(R.string.nogps), Toast.LENGTH_LONG).show();	
							}
						}else{
							ShowMsgDialog();
						}
					}
				}
			});
			TextView mylocation2=(TextView) mSlidingMenu.findViewById(R.id.mylocation2);
			mylocation2.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(!Global.onAirAct.mSlidingMenu.isClosed()){
						mSlidingMenu.toggleRightDrawer();
//						Global.onAirAct.clearBackStack();
//						Global.onAirAct.pushFragment(Pages.Map);
						Global.mapdemosteps=0;
						if(!Global.s.userid().equals("no_user")){
							if(Global.onAirAct.isNetworkOnline()){
								if(Global.s.isfirstmap()){
									Global.onAirAct.pushFragment(Pages.Mapdemo1);
								}else{
									Global.onAirAct.pushFragment(Pages.Map);
								}
							}else{
								Toast.makeText(MainActivity.this, getResources().getString(R.string.nogps), Toast.LENGTH_LONG).show();	
							}
						}else{
							ShowMsgDialog();
						}
					}
				}
			});
			TextView home=(TextView) mSlidingMenu.findViewById(R.id.home);
			home.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(!Global.onAirAct.mSlidingMenu.isClosed()){
						mSlidingMenu.toggleRightDrawer();
						Global.onAirAct.clearBackStack();
						Global.onAirAct.pushFragment(Pages.Homepage1);
					}
				}
			});

			TextView baingame=(TextView) mSlidingMenu.findViewById(R.id.baingame);
			baingame.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(!Global.onAirAct.mSlidingMenu.isClosed()){
						mSlidingMenu.toggleRightDrawer();
						Global.onAirAct.clearBackStack();
						Global.onAirAct.pushFragment(Pages.Gamemenu);
					}
				}
			});
			TextView callfmy=(TextView) mSlidingMenu.findViewById(R.id.callfmy);
			callfmy.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(!Global.onAirAct.mSlidingMenu.isClosed()){
						mSlidingMenu.toggleRightDrawer();
						Global.onAirAct.clearBackStack();
						Global.onAirAct.pushFragment(Pages.Callfmy);
					}
				}
			});
			TextView setting=(TextView) mSlidingMenu.findViewById(R.id.setting);
			setting.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(!Global.onAirAct.mSlidingMenu.isClosed()){
						mSlidingMenu.toggleRightDrawer();
						Global.onAirAct.clearBackStack();
						Global.onAirAct.pushFragment(Pages.Setting);
					}
				}
			});
		}

	}
	public boolean isOnline() { 
		ConnectivityManager cm =
				(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

		return cm.getActiveNetworkInfo() != null && 
				cm.getActiveNetworkInfo().isConnectedOrConnecting();
	} 
	private void ShowMsgDialog()
	{
		Builder MyAlertDialog = new AlertDialog.Builder(this);
		MyAlertDialog.setTitle(getResources().getString(R.string.plzlogin));

		DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which) {
				Global.onAirAct.clearBackStack();
				Global.onAirAct.pushFragment(Pages.Reallogin);
			}
		};
		MyAlertDialog.setNeutralButton(getResources().getString(R.string.login),OkClick );
		MyAlertDialog.setOnCancelListener(new OnCancelListener(){

			@Override
			public void onCancel(DialogInterface dialog) {
				// TODO Auto-generated method stub

			}

		});
		MyAlertDialog.show();
	}
}
