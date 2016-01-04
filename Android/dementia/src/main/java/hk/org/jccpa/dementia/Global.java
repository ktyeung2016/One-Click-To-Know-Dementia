package hk.org.jccpa.dementia;

import java.util.ArrayList;

import hk.org.jccpa.dementia.classpackage.fmyinfo;
import hk.org.jccpa.dementia.object.knowledge;
import hk.org.jccpa.dementia.object.soc;
import hk.org.jccpa.dementia.view.MaskOverlay;

import android.graphics.Bitmap;
import android.os.Environment;


public class Global {
	


	public static MainActivity onAirAct = null;
	public static sharepreference s;

	// PassData
	public static fmyinfo info=null;
	public static Bitmap bm=null;


	//API
	public static String link="http://dementia.westcomzivo.com/dementia/";
	public static String apilink=link+"api/";

	//Status
	public static boolean login=false;


	public static boolean afterlogin=false;
	public static boolean menuon=false;
	
	
	public static int totalscore;
	public static int right5;
	public static int heart;
	public static int total;
	public static int gameqnum;
	public static int gamenum;
	//game 5
	public static int game5budget;
	public static int setonep;
	public static int setoneq;
	public static int settwop;
	public static int settwoq;
	public static int setthreep;
	public static int setthreeq;
	
	//game 5
	
	//game 2 
	//protected static int right2;
	//game 2
	//game1
	public static int element[]={
			R.drawable.a001,
			R.drawable.a002,
			R.drawable.a003,
			R.drawable.a004,
			R.drawable.a005,
			R.drawable.a006,
			R.drawable.a007,
			R.drawable.a008,
			R.drawable.a009,
			R.drawable.a010,
			R.drawable.a011,
			R.drawable.a012,
			R.drawable.a013,
			R.drawable.a014,
			R.drawable.a015,
			R.drawable.a016,
			R.drawable.a017,
			R.drawable.a018,
			R.drawable.a019,
			R.drawable.a020,
			R.drawable.a021,
			R.drawable.a022,
			R.drawable.a023,
			R.drawable.a024,
			R.drawable.a025,
			R.drawable.a026,
			R.drawable.a027,
			R.drawable.a028,
			R.drawable.a029,
			R.drawable.a030
	};
	public static ArrayList<Integer> thingtobutlist;
	public static ArrayList<Integer> kolistlist;
	public static int thingsbought;
	public static int star;
	public static int rankgamenum;
	public static boolean drqweropen;

    public static MaskOverlay maskOverlay = new MaskOverlay();
	
	//game1
	//sql
	public static final String path = Environment.getExternalStorageDirectory()+"/.jcfloder/";
	public static int selftestqumber;
	public static int selftestyesnum;
	public static int[] selftestans={0,0,0,0,0,0,0,0,0,0};
	public static void reset_selftestans(){
		for(int i = 0;i<selftestans.length;i++){
			selftestans[i]=0;
		}
	}
	//map
	public static int mapdemosteps;
	public static boolean isfirsttimelogin;
	public static knowledge knowledgeobject;
	public static ArrayList<hk.org.jccpa.dementia.object.knowledge> knowledgelist;
	public static ArrayList<soc> soclist;
	public static soc socobject;
	public static String sharemsg;
	public static int whichpage;
}
