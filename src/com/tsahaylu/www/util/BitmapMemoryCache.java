package com.tsahaylu.www.util;


import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.tsahaylu.www.common.Constants;

public class BitmapMemoryCache implements ImageCache {  
	  
    private LruCache<String, Bitmap> memoryCache;  
    private Context context;
    private BitmapDiskCache dcache; 
    
    public BitmapMemoryCache(Context context) {  
    	
    	this.context=context;
    	
    	
        memoryCache = new LruCache<String, Bitmap>(Constants.BITMAP_CACHE_MAX_SIZE) {  
            @Override  
            protected int sizeOf(String key, Bitmap bitmap) {  
                return bitmap.getRowBytes() * bitmap.getHeight();  
            }  
        };  
		dcache=new BitmapDiskCache(context);
    }  
  
    @Override  
    public Bitmap getBitmap(String url) {
    	
    	 	Bitmap bitmap=null;
    		bitmap=memoryCache.get(url);    		

    		if (bitmap==null)
    		{
    			bitmap=dcache.getBitmap(url);
    			if (bitmap!=null)
    			{
    				memoryCache.put(url, bitmap);
    			}
    		}
    		
        return bitmap;       
    }  
  
    @Override  
    public void putBitmap(String url, Bitmap bitmap) {  
    	memoryCache.put(url, bitmap);
    	dcache.putBitmap(url, bitmap);
    }  
  
}  

