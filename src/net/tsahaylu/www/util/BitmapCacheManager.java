package net.tsahaylu.www.util;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.LruCache;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.jakewharton.disklrucache.DiskLruCache;

public class BitmapCacheManager {  
	  
	
	private static BitmapCacheManager instance;
	private BitmapMemoryCache mcache;
	private Context context;
	
	private BitmapCacheManager()
	{
		
	}
	
	public static BitmapCacheManager getInstance(Context context){
		
		
		if(instance == null)
			instance = new BitmapCacheManager();
		
		return instance;
	}
	
	public void setContext()
	{
		this.context= context;
	}

	public void loadBitmaps(ImageView imageview,String url)
	{
/*		Bitmap bitmap=null;
		mcache=new BitmapMemoryCache();
		bitmap=mcache.getBitmap(url);
		if (bitmap==null)
		{

		}*/
		
	}
	
	/* class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {  
		  
	        *//** 
	         * 图片的URL地址 
	         *//*  
	        private String url;  
	  
	        @Override  
	        protected Bitmap doInBackground(String... params) {  
	            url = params[0];  
	            
	            Bitmap bitmap=null;	            
				BitmapDiskCache dcache=new BitmapDiskCache(context);
				bitmap=dcache.getBitmap(url);
				
				if (bitmap==null)
				{
					
				}
				else
				{
					mcache.putBitmap(url, bitmap);
				}
	            
	            
	        }  
	  
	        @Override  
	        protected void onPostExecute(Bitmap bitmap) {  
	            super.onPostExecute(bitmap);  
	            // 根据Tag找到相应的ImageView控件，将下载好的图片显示出来。  
	           /// ImageView imageView = (ImageView) mPhotoWall.findViewWithTag(imageUrl);  
	            if (imageView != null && bitmap != null) {  
	                imageView.setImageBitmap(bitmap);  
	            }  
	           // taskCollection.remove(this);  
	        }  
  
}*/
	 
}

