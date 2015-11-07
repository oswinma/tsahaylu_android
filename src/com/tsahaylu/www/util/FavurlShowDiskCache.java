package com.tsahaylu.www.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.content.Context;

import com.jakewharton.disklrucache.DiskLruCache;
import com.tsahaylu.www.common.Constants;
import com.tsahaylu.www.dto.FavURLShow;

public class FavurlShowDiskCache{  
	  
 
    private DiskLruCache diskCache; 
        
    public FavurlShowDiskCache(Context context) {
    	
        File cacheDir = CommonUtils.getDiskCacheDir(context, Constants.FAVURL_CACHE_DIR);  
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
  
    public ArrayList<FavURLShow> getFavurlshowlist(String key) {  
    	
    	ArrayList<FavURLShow> list=null;
    	
    	try {  
    	    DiskLruCache.Snapshot snapShot = diskCache.get(key);         
    	    if (snapShot != null) {  
    	 	    ObjectInputStream in = new ObjectInputStream(snapShot.getInputStream(0));
                list= (ArrayList<FavURLShow>) in.readObject();  
    	    }  
    	} catch (IOException e) {  
    	    e.printStackTrace();  
    	} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
    	
    	return list;
    }
  
    public void putFavurlshowlist(final String key, final ArrayList<FavURLShow> list) {  
    	 
    	new Thread(new Runnable() {  
    	    @Override  
    	    public void run() {    	    	
    	    
    	try {    		
			DiskLruCache.Editor editor = diskCache.edit(key);
			
			 if (editor != null) {				 
				 	ObjectOutputStream out = new ObjectOutputStream(editor.newOutputStream(0));
		            out.writeObject(list);
		            out.close();	            
		            editor.commit();                  
	         	}  
			 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	    }}).start();
    	
    }
    
    
public String getstartCursor(String key) {  
    	
    	String startCursor=null;
    	
    	try {  
    	    DiskLruCache.Snapshot snapShot = diskCache.get(key);         
    	    if (snapShot != null) {  
    	    	InputStream   in = snapShot.getInputStream(0);
    	    	ByteArrayOutputStream bos = new ByteArrayOutputStream();  
    	    	  
    	    	//读取缓存  
    	    	byte[] buffer = new byte[2048];  
    	    	int length = 0;  
    	    	while((length = in.read(buffer)) != -1) {  
    	    	    bos.write(buffer, 0, length);//写入输出流  
    	    	}  
    	    	in.close();//读取完毕，关闭输入流  
    	    	
    	 	   startCursor= bos.toString();
    	 	   
    	 	  //System.out.println("getstartCursor "+key+"|"+startCursor);
    	    }  
    	} catch (IOException e) {  
    	    e.printStackTrace();  
    	}  
    	
    	return startCursor;
    }
  
    public void putstartCursor(final String key, final String startCursor) {  
    	 
    	new Thread(new Runnable() {  
    	    @Override  
    	    public void run() {    	    	
    	    
    	try {    		
			DiskLruCache.Editor editor = diskCache.edit(key);
			
			 if (editor != null) {				 
				 OutputStream   out = editor.newOutputStream(0);
		            out.write(startCursor.getBytes());		           
		            out.close();	            
		            editor.commit();             
		            //System.out.println("putstartCursor "+key+"|"+startCursor);
	         	}  
			 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	    }}).start();
    	
    }
    
    
  
}  

