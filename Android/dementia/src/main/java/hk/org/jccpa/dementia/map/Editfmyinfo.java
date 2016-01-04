package hk.org.jccpa.dementia.map;

import static android.provider.BaseColumns._ID;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import hk.org.jccpa.dementia.Global;
import hk.org.jccpa.dementia.Pages;
import hk.org.jccpa.dementia.R;
import hk.org.jccpa.dementia.sql.MyDBHelper_callfmy;

import com.example.basefamework.fontsize.Preferences;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class Editfmyinfo extends Fragment {
	View v;
	ImageButton back,menu;
	ImageView pic;
	Button takephoto,chphoto,finish, delete;
	MyDBHelper_callfmy dbhelper;
	int type=0;
	String fname=null;
	String filepath=null;
	EditText name,phone,email,relationship,remarks;
	public void onCreate(Bundle savedInstanceState) {

		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);
		super.onCreate(savedInstanceState);
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getActivity().getTheme().applyStyle(new Preferences(getActivity()).getFontStyle().getResId(), true);
		v = inflater.inflate(R.layout.activity_newafmymem, container, false);
		Global.whichpage=Pages.Editfmyinfo;
		if(Global.info==null){
			Global.onAirAct.clearOneBackStack();
		}
		View holepage=v.findViewById(R.id.holepage);
		holepage.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		back=(ImageButton) v.findViewById(R.id.back);
		menu=(ImageButton) v.findViewById(R.id.menu);
		takephoto=(Button) v.findViewById(R.id.takephoto);
		pic=(ImageView) v.findViewById(R.id.pic);
		chphoto=(Button) v.findViewById(R.id.chphoto);
		name=(EditText) v.findViewById(R.id.name);
		phone=(EditText) v.findViewById(R.id.phone);
		email=(EditText) v.findViewById(R.id.email);
		relationship=(EditText) v.findViewById(R.id.relationship);
		remarks=(EditText) v.findViewById(R.id.remarks);
        delete=(Button) v.findViewById(R.id.delete);
		finish=(Button) v.findViewById(R.id.finish);
		
		
		name.setText(Global.info.name);
		email.setText(Global.info.email);
		phone.setText(Global.info.phone);
		relationship.setText(Global.info.relationship);
		remarks.setText(Global.info.remarks);
		filepath=Global.info.filepath;
		String s[] = filepath.split(":");
		Log.d("path","path:    "+s[0]);
		File imgFile = new  File(s[0]);
		 
		if(imgFile.exists()){
		 
		    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
		 
		   
		 
		    pic.setImageBitmap(myBitmap);
		 
		} 
//		pic.setImageBitmap(resize(s[0],0,null));
		
		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Global.onAirAct.clearOneBackStack();
			}
		});
		menu.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Global.onAirAct.mSlidingMenu.toggleRightDrawer();
			}
		});
		takephoto.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				open();	
			}
		});
		chphoto.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
				photoPickerIntent.setType("image/*");
				startActivityForResult(photoPickerIntent, 1);  
			}
		});
		delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //				show();
//				SQLiteDatabase db;
//				db=dbhelper.getWritableDatabase();
//				db.delete(MyDBHelper.TABLE_NAME, null, null);
                //Global.onAirAct.clearOneBackStack();
                showAlertDialogToDelete();
            }
        });
		finish.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(  name.getText().toString().length()>0){
					if(  phone.getText().toString().length()>0){
//						if(  email.getText().toString().length()>0){
							if(  relationship.getText().toString().length()>0){
//								if( remarks.getText().toString().length()>0){
									if( filepath!=null){
										Log.d("click", "onclick");
//										addtodb();
										update();
										Global.onAirAct.clearOneBackStack();
									}else{
										Toast.makeText(getActivity(), getResources().getString(R.string.plzphoto), Toast.LENGTH_LONG).show();
									}
//								}else{
//
//								}
							}else{
								Toast.makeText(getActivity(), getResources().getString(R.string.plzrel), Toast.LENGTH_LONG).show();
							}
//						}else{
//
//						}
					}else{
						Toast.makeText(getActivity(), getResources().getString(R.string.plzphone), Toast.LENGTH_LONG).show();
					}
				}else{
					Toast.makeText(getActivity(), getResources().getString(R.string.plzname), Toast.LENGTH_LONG).show();
				}






			}
		});
		dbhelper = new MyDBHelper_callfmy(getActivity());
		dbhelper.close();
		return v;
	}

    private void showAlertDialogToDelete()
    {

        AlertDialog.Builder MyAlertDialog = new AlertDialog.Builder(getActivity());
        MyAlertDialog.setTitle(getActivity().getResources().getString(R.string.delete));
        //建立選擇的事件
        DialogInterface.OnClickListener okClick = new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which) {
                delete();
                Global.onAirAct.clearOneBackStack();
            }
        };
        //建立按下取消什麼事情都不做的事件
        DialogInterface.OnClickListener cancelClick = new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which) {
            }
        };
        MyAlertDialog.setPositiveButton(getActivity().getResources().getString(R.string.delete),okClick);
        MyAlertDialog.setNeutralButton(getActivity().getResources().getString(R.string.cancel),cancelClick );
        MyAlertDialog.show();
    }
    private void delete(){
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        db.delete(MyDBHelper_callfmy.TABLE_NAME, _ID + "=" + Global.info.id, null);
    }
	 private void update(){

	       ContentValues values = new ContentValues();

	       values.put(MyDBHelper_callfmy.name,  name.getText().toString());

			values.put(MyDBHelper_callfmy.phone, phone.getText().toString());

			values.put(MyDBHelper_callfmy.email, email.getText().toString());

			values.put(MyDBHelper_callfmy.relationship, relationship.getText().toString());

			values.put(MyDBHelper_callfmy.remarks, remarks.getText().toString());

			values.put(MyDBHelper_callfmy.filepath, filepath+":"+type);
	 
			values.put(MyDBHelper_callfmy.userid,Global.s.userid());
			
	       SQLiteDatabase db = dbhelper.getWritableDatabase();

	       db.update(MyDBHelper_callfmy.TABLE_NAME, values, _ID + "=" + Global.info.id, null);

	 }
	
	
//	private void addtodb(){
//
//		SQLiteDatabase db = dbhelper.getWritableDatabase();
//
//		ContentValues values = new ContentValues();
//		//	       name,phone,email,relationship,remarks
//		values.put(MyDBHelper.name,  name.getText().toString());
//
//		values.put(MyDBHelper.phone, phone.getText().toString());
//
//		values.put(MyDBHelper.email, email.getText().toString());
//
//		values.put(MyDBHelper.relationship, relationship.getText().toString());
//
//		values.put(MyDBHelper.remarks, remarks.getText().toString());
//
//		values.put(MyDBHelper.filepath, filepath+":"+type);
//
//		db.insert(MyDBHelper.TABLE_NAME, null, values);
//
//		Global.onAirAct.clearOneBackStack();
//
//	}

	public void open(){
		//		Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		//		startActivityForResult(intent, 0);

		fname=String.valueOf(System.currentTimeMillis());
		File file = new File(Global.path+fname+".jpg");
		Uri output = Uri.fromFile(file);
		Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, output);
		startActivityForResult(cameraIntent, 0);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==0 && resultCode == Activity.RESULT_OK){

			final File imgFile = new  File(Global.path+fname+".jpg");
			  
			    /* Write bitmap to file using JPEG and 80% quality hint for JPEG. */ 
			    
			if(imgFile.exists()){

				new Thread(new Runnable() {

					public void run() {



						// TODO Auto-generated method stub


						//					myBitmap = Staticmethod.getImage(BitmapFactory.decodeFile(imgFile.getAbsolutePath()));
						getActivity().runOnUiThread(new Runnable(){

							@Override
							public void run() {
								// TODO Auto-generated method stub
								//							resize(imgFile.getAbsolutePath());
								filepath=imgFile.getAbsolutePath();
								Log.d("uri", "uri: "+imgFile.getAbsolutePath());
								type=1;
								pic.setImageBitmap(resize(imgFile.getAbsolutePath(),0,null));

							}

						});
					}
				}).start();


			} 
		}else if(requestCode==1) {
			if(resultCode == Activity.RESULT_OK){  
				Uri selectedImage = data.getData();
//				File myFile = new File(selectedImage.toString());
//
//
//				Log.d("uri", "uri: "+myFile.getAbsolutePath());
//				filepath=selectedImage.getPath();
//				type=1;
//				//				pic.setImageBitmap(resize(getRealPathFromURI(getActivity(),selectedImage),1,null));
//				pic.setImageBitmap(resize(null,1,selectedImage));
				
				Bitmap bmp=resize(null,1,selectedImage);
				fname=String.valueOf(System.currentTimeMillis());
				filepath=Global.path+fname+".jpg";
//				File file = new File(Global.path+fname+".jpg");
				FileOutputStream out = null;
				try { 
				    out = new FileOutputStream(Global.path+fname+".jpg");
				    bmp.compress(Bitmap.CompressFormat.JPEG, 90, out);
				} catch (Exception e) {
				    e.printStackTrace();
				} finally { 
				    try { 
				        if (out != null) {
				            out.close();
				        } 
				    } catch (IOException e) {
				        e.printStackTrace();
				    } 
				}
				type=1;
				//				pic.setImageBitmap(resize(getRealPathFromURI(getActivity(),selectedImage),1,null));
				pic.setImageBitmap(bmp);
				
				
				//	            InputStream imageStream;
				//				try {
				//					imageStream = getActivity().getContentResolver().openInputStream(selectedImage);
				//					Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
				//				} catch (FileNotFoundException e) {
				//					// TODO Auto-generated catch block
				//					e.printStackTrace();
				//				}

			} 
		}
	}
	public String getRealPathFromURI(Context context, Uri contentUri) {
		Cursor cursor = null;
		try {  
			String[] proj = { MediaStore.Images.Media.DATA };
			cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
			int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			Log.d("path123",cursor.getString(column_index));
			return cursor.getString(column_index);
		} finally { 
			if (cursor != null) {
				cursor.close();
			} 
		} 
	}
	Bitmap resize(String filename,int isuri,Uri uri){
		if(isuri==0){
			Matrix matrix = new Matrix();
			try {
				ExifInterface exif = new ExifInterface(filename);
				int rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
				int rotationInDegrees = exifToDegrees(rotation);

				if (rotation != 0f) {matrix.preRotate(rotationInDegrees);}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			BitmapFactory.Options option = new BitmapFactory.Options();

			option.inJustDecodeBounds = true;
			File f=new File(filename);
			//		BitmapFactory.decodeResource(getResources(), R.drawable.tcl_bg, option);
			BitmapFactory.decodeFile(filename,option);


			//The new size to decode to 

			final int NEW_SIZE=100;



			//Now we have image width and height. We should find the correct scale value. (power of 2)

			int width=option.outWidth;

			int height=option.outHeight;

			int scale=1;

			while(true){

				if(width/2<pic.getWidth() || height/2<pic.getHeight())

					break;

				width/=2;

				height/=2;

				scale++;

			}

			//Decode again with inSampleSize

			option = new BitmapFactory.Options();

			option.inSampleSize=scale;
			Bitmap b= BitmapFactory.decodeFile(filename,option);
			Bitmap dstBmp;
			if (b.getWidth() >= b.getHeight()){ 

				dstBmp = Bitmap.createBitmap(
						b,  
						b.getWidth()/2 - b.getHeight()/2, 
						0, 
						b.getHeight(),  
						b.getHeight() ,matrix, true
						); 

			}else{ 

				dstBmp = Bitmap.createBitmap(
						b, 
						0,  
						b.getHeight()/2 - b.getWidth()/2, 
						b.getWidth(), 
						b.getWidth()  , matrix, true
						); 
			} 
			//		Canvas canvas       = new Canvas(dstBmp);

			//		return b;
			
		    OutputStream stream;
			try {
				stream = new FileOutputStream(Global.path+fname+".jpg");
				dstBmp.compress(CompressFormat.JPEG, 80, stream);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return dstBmp;
		}else{
			// Decode image size 

			try {
				BitmapFactory.Options o = new BitmapFactory.Options();
				o.inJustDecodeBounds = true;
				BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri), null, o);

				// The new size we want to scale to 
				final int REQUIRED_SIZE = 140;

				// Find the correct scale value. It should be the power of 2. 
				int width_tmp = o.outWidth, height_tmp = o.outHeight;
				int scale = 1;
				while (true) { 
					if (width_tmp / 2 < REQUIRED_SIZE
							|| height_tmp / 2 < REQUIRED_SIZE) {
						break; 
					} 
					width_tmp /= 2;
					height_tmp /= 2;
					scale *= 2;
				} 

				// Decode with inSampleSize 
				BitmapFactory.Options o2 = new BitmapFactory.Options();
				o2.inSampleSize = scale;
				return BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri), null, o2);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
		//		getActivity().getWindow().setBackgroundDrawable(d);//(BitmapFactory.decodeResource(getResources(), R.drawable.tcl_bg, option));
	}
	private static int exifToDegrees(int exifOrientation) {        
		if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) { return 90; } 
		else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {  return 180; } 
		else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {  return 270; }            
		return 0;     
	} 
}

