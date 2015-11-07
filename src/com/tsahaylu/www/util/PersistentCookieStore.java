package com.tsahaylu.www.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;

import com.tsahaylu.www.common.Constants;

/**
 * Class that implements CookieStore interface. This class saves to SharedPreferences the session
 * cookie.
 *
 * Created by lukas.
 */
public class PersistentCookieStore implements CookieStore {

	private Context context;
	private SharedPreferences sp;
	
	public PersistentCookieStore(Context c)
	{
		this.context=context;
		sp = PreferenceManager.getDefaultSharedPreferences(context);		
	}


	@Override
	public void addCookie(Cookie cookie) {
		// TODO Auto-generated method stub
        if (cookie.getName().equals(Constants.COOKIE_USERID)) {
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(cookie.getName(), cookie.getValue());
            editor.commit(); 
        }
        
	}


	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean clearExpired(Date date) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public List<Cookie> getCookies() {
		// TODO Auto-generated method stub
	List<Cookie> list=new ArrayList<Cookie>();
	

        	
		return null;
	}

 
}