package hk.org.jccpa.dementia.beforelogin;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.example.basefamework.fontsize.Preferences;

import hk.org.jccpa.dementia.Global;
import hk.org.jccpa.dementia.R;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Loadingdatapage extends Activity {
	int loadingrate= 0;
	ProgressBar progressBar1;
	TextView precentage,tips;
	Handler h=new Handler();
	Handler h2=new Handler();
	String []randtip;
	int count = 0;
	boolean timeout=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getTheme().applyStyle(new Preferences(this).getFontStyle().getResId(), true);
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		getActionBar().hide();
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

		setContentView(R.layout.activity_loadingdatapage);
		progressBar1=(ProgressBar) findViewById(R.id.progressBar1);
		precentage=(TextView) findViewById(R.id.precentage);



		tips=(TextView) findViewById(R.id.tips);
		randtip=getResources().getStringArray(R.array.tipsarray);
		int which=(int) (System.currentTimeMillis()%randtip.length);
		tips.setText(randtip[which]);
		h2.postDelayed(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				int which2=(int) (System.currentTimeMillis()%randtip.length);
				tips.setText(randtip[which2]);
				h2.postDelayed(this,5000);
			}

		}, 5000);



		getFile(Environment.getExternalStorageDirectory()+"/.jcfloder/game/") ; 

		Log.d("file count", "count: "+count);



		if(Global.onAirAct.isOnline()){


			h.postDelayed(new Runnable(){
				public void run() { 
					// do something             
					if(loadingrate!=100){
						//					loadingrate+=50;
						//					progressBar1.setProgress(loadingrate);
						//					precentage.setText(loadingrate+"%");
						//					h.postDelayed(this, 500);
						String PATH = Environment.getExternalStorageDirectory()+"/.jcfloder/game";
						File f = new File(PATH);
						//					  File f = new File(Environment.getExternalStorageDirectory() + "/somedir");
						if(f.isDirectory() && count==3345) { 
							if(Global.s.gamedatako()
									){
								/* do something */  
								Global.login=true;
								loadingrate+=50;
								progressBar1.setProgress(loadingrate);
								precentage.setText(loadingrate+"%");
								h.postDelayed(this, 1000);
								//							finish();
							}else{
								new DownloadFileAsync().execute();
							}
						} 
						else 
						{ 
							File dir = new File(Environment.getExternalStorageDirectory()+"/.jcfloder/game"); 
							
							if (dir.isDirectory())
								for (File child : dir.listFiles())
									DeleteRecursive(child);

							dir.delete();
							new DownloadFileAsync().execute();

							//And your other stuffs goes here 
						}
					}else{
						Global.login=true;
						//					loadingrate+=10;
						//					progressBar1.setProgress(loadingrate);
						//					precentage.setText(loadingrate+"%");
						//					h.postDelayed(this, 1000);
						finish();
					}



				}}, 500); 
		}else{
			Global.login=true;
			//			loadingrate+=10;
			//			progressBar1.setProgress(loadingrate);
			//			precentage.setText(loadingrate+"%");
			//			h.postDelayed(this, 1000);
			finish();
		}
	}


	class DownloadFileAsync extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			//			showDialog(DIALOG_DOWNLOAD_PROGRESS);
		}

		@Override
		protected String doInBackground(String... aurl) {
			int count;

			try {

				URL url = new URL("http://demo.ecthk.net/tim/game.zip");
				URLConnection conexion = url.openConnection();
				conexion.connect();
				String PATH3 = Environment.getExternalStorageDirectory()+"/.jcfloder";
				File f2 = new File(PATH3);
				f2.mkdir();


				int lenghtOfFile = conexion.getContentLength();
				Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);

				InputStream input = new BufferedInputStream(url.openStream());
				String PATH1 = Environment.getExternalStorageDirectory()+"/.jcfloder/game.zip";
				OutputStream output = new FileOutputStream(PATH1);

				byte data[] = new byte[1024];

				long total = 0;

				while ((count = input.read(data)) != -1) {
					total += count;
					publishProgress(""+(int)((total*100)/lenghtOfFile));
					output.write(data, 0, count);
				}

				output.flush();
				output.close();
				input.close();
			} catch (Exception e) {
				e.printStackTrace();
				timeout=true;
			}
			return null;

		}
		protected void onProgressUpdate(String... progress) {
			Log.d("ANDRO_ASYNC",progress[0]);
			progressBar1.setProgress(Integer.parseInt(progress[0]));
			precentage.setText(Integer.parseInt(progress[0])+"%");
		}

		@Override
		protected void onPostExecute(String unused) {
			//			dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
			String zipFile = Environment.getExternalStorageDirectory()+"/.jcfloder/game.zip";
			String location = Environment.getExternalStorageDirectory()+"/.jcfloder/";

			Decompress d=new Decompress(zipFile,location);
			boolean done =false;
			if(!timeout){
				done =d.unzip();
			}

			if(done==true){
				Log.d("Decompress", "unzip finished"); 
				Global.s.setgamedatako(true);
				File temp = new File(zipFile);
				temp.delete();
			}else{
				String zipFile1 = Environment.getExternalStorageDirectory()+"/.jcfloder/game";
				File temp = new File(zipFile1);
				if (temp.isDirectory())
					for (File child : temp.listFiles())
						DeleteRecursive(child);

				temp.delete();
				String zipFile2 = Environment.getExternalStorageDirectory()+"/.jcfloder/game.zip";
				File temp2 = new File(zipFile2);
				temp2.delete();
			} 


			//			d.unpackZip();
			Global.login=true;

			finish();
		}
	}
	void DeleteRecursive(File fileOrDirectory) {

		if (fileOrDirectory.isDirectory())
			for (File child : fileOrDirectory.listFiles())
				DeleteRecursive(child);

		fileOrDirectory.delete();

	} 
	public class Decompress { 
		private String _zipFile; 
		private String _location; 

		public Decompress(String zipFile, String location) { 
			_zipFile = zipFile; 
			_location = location; 

			_dirChecker(""); 
		} 

		public boolean unzip() { 
			try  { 
				FileInputStream fin = new FileInputStream(_zipFile); 
				ZipInputStream zin = new ZipInputStream(new BufferedInputStream(fin)); 
				ZipEntry ze = null; 
				while ((ze = zin.getNextEntry()) != null) { 
					try{
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						byte[] buffer = new byte[1024];
						int count;
						Log.v("Decompress", "Unzipping " + ze.getName()); 
						String s[]=ze.getName().split("/game/");
						Log.d("unzip",s[1]);
						if(ze.isDirectory()) { 
							_dirChecker(s[1]); 
						} else { 
							FileOutputStream fout = new FileOutputStream(_location+"/game/" + s[1]); 
							while((count = zin.read(buffer)) != -1) 
							{ 
								baos.write(buffer, 0, count);
								byte[] bytes = baos.toByteArray();
								fout.write(bytes);             
								baos.reset();
							} 

							zin.closeEntry(); 
							fout.close(); 
						}
					}catch(Exception e){
						Log.e("Decompress", "unzip", e); 
					}

				} 
				zin.close(); 
			} catch(Exception e) { 
				Log.e("Decompress", "unzip", e); 

				return false;
			} 
			return true;
		} 

		private boolean unpackZip()
		{        
			InputStream is;
			ZipInputStream zis;
			try  
			{ 
				is = new FileInputStream(_zipFile);
				zis = new ZipInputStream(new BufferedInputStream(is));          
				ZipEntry ze;

				while((ze = zis.getNextEntry()) != null) 
				{ 
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					byte[] buffer = new byte[1024];
					int count;
					Log.v("Decompress", "Unzipping " + ze.getName()); 
					String filename = ze.getName();
					//		             String s[]=ze.getName().split("/game/");

					//		             FileOutputStream fout = new FileOutputStream(_location+"/game/" + s[1]); 
					FileOutputStream fout = new FileOutputStream(_location+"/game/" + filename); 
					//		             FileOutputStream fout = new FileOutputStream(path + filename);

					// reading and writing 
					while((count = zis.read(buffer)) != -1) 
					{ 
						baos.write(buffer, 0, count);
						byte[] bytes = baos.toByteArray();
						fout.write(bytes);             
						baos.reset();
					} 

					fout.close();               
					zis.closeEntry();
				} 

				zis.close();
			}  
			catch(IOException e)
			{ 
				e.printStackTrace();
				return false; 
			} 

			return true; 
		} 
		private void _dirChecker(String dir) { 
			File f = new File(Environment.getExternalStorageDirectory()+"/.jcfloder/game"); 
			File f1 = new File(Environment.getExternalStorageDirectory()+"/.jcfloder/game/shape2"); 
			File f2 = new File(Environment.getExternalStorageDirectory()+"/.jcfloder/game/ans"); 
			File f3 = new File(Environment.getExternalStorageDirectory()+"/.jcfloder/game/ans/lv1");
			File f4 = new File(Environment.getExternalStorageDirectory()+"/.jcfloder/game/ans/lv2");
			File f5 = new File(Environment.getExternalStorageDirectory()+"/.jcfloder/game/ans/lv3");
			File f6 = new File(Environment.getExternalStorageDirectory()+"/.jcfloder/game/qes"); 
			File f7 = new File(Environment.getExternalStorageDirectory()+"/.jcfloder/game/qes/lv1"); 
			File f8 = new File(Environment.getExternalStorageDirectory()+"/.jcfloder/game/qes/lv2"); 
			File f9 = new File(Environment.getExternalStorageDirectory()+"/.jcfloder/game/qes/lv3"); 

			if(!f.isDirectory()) { 
				f.mkdirs(); 
				f1.mkdirs(); 
				f2.mkdirs(); 
				f3.mkdirs(); 
				f4.mkdirs(); 
				f5.mkdirs(); 
				f6.mkdirs(); 
				f7.mkdirs(); 
				f8.mkdirs(); 
				f9.mkdirs(); 

			} 
		} 
	} 

	public void onBackPressed(){
		super.onBackPressed();
		System.exit(0);

	}


	private void getFile(String dirPath) 
	{ 
		File f = new File(dirPath);
		File[] files  = f.listFiles();

		if(files != null)
			for(int i=0; i < files.length; i++)
			{ 
				count ++;
				File file = files[i];
				if(file.isDirectory())
				{    
					getFile(file.getAbsolutePath()); 
				} 
			} 
	}





}
