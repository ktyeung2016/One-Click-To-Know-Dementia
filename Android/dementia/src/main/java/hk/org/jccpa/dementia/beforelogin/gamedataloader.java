//package hk.org.jccpa.dementia.beforelogin;
//
//import java.io.BufferedInputStream;
//import java.io.FileOutputStream;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.net.URL;
//import java.net.URLConnection;
//
//import android.app.Activity;
//import android.app.Dialog;
//import android.app.ProgressDialog;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//
//public class gamedataloader extends Activity {
//   
//    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
//    private Button startBtn;
//    private ProgressDialog mProgressDialog;
//   
//    /** Called when the activity is first created. */
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);
//        startBtn = (Button)findViewById(R.id.startBtn);
//        startBtn.setOnClickListener(new OnClickListener(){
//            public void onClick(View v) {
//                startDownload();
//            }
//        });
//    }
//
//    private void startDownload() {
//        String url = "http://farm1.static.flickr.com/114/298125983_0e4bf66782_b.jpg";
//        new DownloadFileAsync().execute(url);
//    }
//    @Override
//    protected Dialog onCreateDialog(int id) {
//        switch (id) {
//		case DIALOG_DOWNLOAD_PROGRESS:
//			mProgressDialog = new ProgressDialog(this);
//			mProgressDialog.setMessage("Downloading file..");
//			mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//			mProgressDialog.setCancelable(false);
//			mProgressDialog.show();
//			return mProgressDialog;
//		default:
//			return null;
//        }
//    }
//
//
//}
