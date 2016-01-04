package hk.org.jccpa.dementia.adaptor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import hk.org.jccpa.dementia.Global;
import hk.org.jccpa.dementia.Pages;
import hk.org.jccpa.dementia.R;
import hk.org.jccpa.dementia.classpackage.fmyinfo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class fmyinfoAdapter extends BaseAdapter{
	Context c;
	private LayoutInflater myInflater;
	ImageLoader mImageLoader;
	DisplayImageOptions displayImageOptions;
	ArrayList<fmyinfo> list=null;
	public fmyinfoAdapter(Context c,ArrayList<fmyinfo> list){
		this.c=c;
		myInflater = LayoutInflater.from(c);
		this.list=list;
		mImageLoader=ImageLoader.getInstance();
		initImageLoader(c);
		displayImageOptions = new DisplayImageOptions.Builder() 

		.resetViewBeforeLoading()

		.imageScaleType(ImageScaleType.IN_SAMPLE_INT) 
		.bitmapConfig(Bitmap.Config.RGB_565)

		.cacheInMemory(true) 
		.cacheOnDisc(true).displayer(new FadeInBitmapDisplayer(300)) 
		.build();

	}
	public fmyinfoAdapter(Context c){
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
			convertView = myInflater.inflate(R.layout.fmymeminfo, null);

			//建構listItem內容view
			viewTag = new ViewTag((ImageView)convertView.findViewById(R.id.icon),
					(ImageView)convertView.findViewById(R.id.edit),
					(TextView)convertView.findViewById(R.id.title),
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
			viewTag.title.setText(list.get(position).name+"("+list.get(position).relationship+")");
			viewTag.call.setText(list.get(position).phone);
			viewTag.call.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent callIntent = new Intent(Intent.ACTION_CALL);
					callIntent.setData(Uri.parse("tel:"+list.get(position).phone));
					c.startActivity(callIntent);
				}
			});

			Log.d("list.get(position).filepath","list.get(position).filepath:  "+ list.get(position).filepath);
			String [] s = list.get(position).filepath.split(":");
            if(Global.s.whichmode()){
                viewTag.edit.setVisibility(View.GONE);
            }
			viewTag.edit.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Global.info=list.get(position);
					Global.onAirAct.pushFragment(Pages.Editfmyinfo);
				}
			});
			try{
				if(s[1].equals("2")){
					//				viewTag.icon.setImageURI(Uri.parse(s[0]));
					Log.d("list.get(position).filepath","list.get(position).filepath:  "+ "file://"+s[0]);



					//				    viewTag.icon.setImageBitmap(resize(null,1,Uri.parse("file://"+s[0]),viewTag.icon.getHeight(),viewTag.icon.getWidth()));



				}else{
					//				viewTag.icon.setImageBitmap(resize(s[0],0,null,viewTag.icon.getHeight(),viewTag.icon.getWidth()));
					//				BitmapFactory.Options option = new BitmapFactory.Options();
					//
					//				option.inJustDecodeBounds = true;
					//				Bitmap bitmap = BitmapFactory.decodeFile(s[0], option);
					//
					//				final int NEW_SIZE=100;
					//
					//
					//
					//				//Now we have image width and height. We should find the correct scale value. (power of 2)
					//
					//				int width=option.outWidth;
					//
					//				int height=option.outHeight;
					//
					//				int scale=1;
					//
					//				while(true){
					//
					//					if(width/2<viewTag.icon.getHeight() || height/2<viewTag.icon.getWidth())
					//
					//						break;
					//
					//					width/=2;
					//
					//					height/=2;
					//
					//					scale++;
					//
					//				}
					//
					//				//Decode again with inSampleSize
					//				
					//				option = new BitmapFactory.Options();
					//
					//				option.inSampleSize=scale;
					//				Bitmap b= BitmapFactory.decodeFile(s[0],option);

					//				viewTag.icon.setImageBitmap(b);
					mImageLoader.displayImage("file:///"+s[0], viewTag.icon,displayImageOptions);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return convertView;
	}

	public static void initImageLoader(Context context) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration
				.Builder(context)
		.threadPriority(Thread.NORM_PRIORITY - 2)
		.denyCacheImageMultipleSizesInMemory()
		.memoryCache(new UsingFreqLimitedMemoryCache(200000)) 
		.memoryCacheSize(6000000)

		.memoryCache(new WeakMemoryCache())
		//				.offOutOfMemoryHandling()
		.discCacheFileNameGenerator(new Md5FileNameGenerator())
		.tasksProcessingOrder(QueueProcessingType.LIFO)
		.build();
		ImageLoader.getInstance().init(config);
	}



	class ViewTag{
		ImageView icon,edit;
		TextView title;
		Button call;

		public ViewTag(ImageView icon,ImageView edit, TextView title, Button call){
			this.icon = icon;
			this.edit=edit;
			this.title = title;
			this.call = call;
		}

	}
	public Bitmap resize(String filename,int isuri,Uri uri,int iconheight,int iconwidth){
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

				if(width/2<iconheight || height/2<iconwidth)

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
			return dstBmp;
		}else{
			// Decode image size 

			try {
				File f=new File(filename);
				BitmapFactory.Options o = new BitmapFactory.Options();
				o.inJustDecodeBounds = true;
				BitmapFactory.decodeStream(c.getContentResolver().openInputStream(uri), null, o);

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
				return BitmapFactory.decodeStream(c.getContentResolver().openInputStream(uri), null, o2);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
		//		getActivity().getWindow().setBackgroundDrawable(d);//(BitmapFactory.decodeResource(getResources(), R.drawable.tcl_bg, option));
	}
	private int exifToDegrees(int exifOrientation) {        
		if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) { return 90; } 
		else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {  return 180; } 
		else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {  return 270; }            
		return 0;     
	} 

}
