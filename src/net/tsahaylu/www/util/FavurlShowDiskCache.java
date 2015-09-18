package net.tsahaylu.www.util;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import net.tsahaylu.www.common.Constants;
import net.tsahaylu.www.dto.FavURLShow;
import android.content.Context;
import com.jakewharton.disklrucache.DiskLruCache;

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
  
}  
