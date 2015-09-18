package net.tsahaylu.www.util;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import net.tsahaylu.www.common.Constants;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.jakewharton.disklrucache.DiskLruCache;

public class BitmapDiskCache implements ImageCache {  
	  
 
    private DiskLruCache diskCache; 
        
    public BitmapDiskCache(Context context) {
    	
        File cacheDir = CommonUtils.getDiskCacheDir(context, Constants.BITMAP_CACHE_DIR);  
        if (!cacheDir.exists()) {  
            cacheDir.mkdirs();  
        } 
        
        try {
			diskCache = DiskLruCache.open(cacheDir, CommonUtils.getAppVersion(context), 1, Constants.BITMAP_CACHE_MAX_SIZE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }  
  
    @Override  
    public Bitmap getBitmap(String url) {  
    	
    	Bitmap bitmap=null;
    	
    	try {  
    	    String key = PassEncode.Encode(url);  
    	    DiskLruCache.Snapshot snapShot = diskCache.get(key);  
    	    if (snapShot != null) {  
    	        InputStream is = snapShot.getInputStream(0);  
    	        bitmap = BitmapFactory.decodeStream(is);    
    	    }  
    	} catch (IOException e) {  
    	    e.printStackTrace();  
    	}  
    	
    	return bitmap;
    }  
  
    @Override  
    public void putBitmap(final String url, final Bitmap bitmap) {  
    	 
    	String key = PassEncode.Encode(url);
    	try {
    		
			DiskLruCache.Editor editor = diskCache.edit(key);
			
			 if (editor != null) {
				   	OutputStream outputStream = editor.newOutputStream(0);  
	                if (PushBitmapToStream(bitmap, outputStream)) {  
	                    editor.commit();  
	                } else {  
	                    editor.abort();  
	                }  
	         	}  
			 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}      	    
    	
   }
    	    

	private boolean PushBitmapToStream(Bitmap bitmap, OutputStream outputStream) {
		// TODO Auto-generated method stub		
		try {
			bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream);
			outputStream.close();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}  
  
}  

