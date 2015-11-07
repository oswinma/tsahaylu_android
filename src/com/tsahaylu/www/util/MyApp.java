package com.tsahaylu.www.util;

import java.util.Map;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.tsahaylu.www.common.Constants;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class MyApp extends Application {
    private static final String SET_COOKIE_KEY = "Set-Cookie";
    private static final String COOKIE_KEY = "Cookie";
    private static final String JSESSIONID = "JSESSIONID";

    private static MyApp _instance;
    private RequestQueue _requestQueue;
    private SharedPreferences _preferences;

    public static MyApp get() {
        return _instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        _instance = this;
        _preferences = PreferenceManager.getDefaultSharedPreferences(this);
        _requestQueue = Volley.newRequestQueue(this);
    }

    public RequestQueue getRequestQueue() {
        return _requestQueue;
    }


    /**
     * Checks the response headers for session cookie and saves it
     * if it finds it.
     * @param response Response Headers.
     */
    public final void checkSessionCookie(NetworkResponse response) {
    	
    	
    	String cookie=null;
    	Editor prefEditor = _preferences.edit();
    	
    	Map<String, String> hm=response.headers;
		cookie= hm.get(SET_COOKIE_KEY);
				if (cookie!=null){
		        	
                	int sindex=cookie.indexOf(Constants.COOKIE_USERID);
                	
                	if (sindex>=0)
                	{
                    	String temps=cookie.substring(sindex);
                    	String[] temp = temps.split(";");
                    	String[] tempp = temp[0].split("=");
                        String userid=tempp[1];
                        prefEditor.putString(Constants.COOKIE_USERID, userid);
                	}

                	sindex=cookie.indexOf(JSESSIONID);
                	if (sindex>=0)
                	{
                    	String temps=cookie.substring(sindex);
                    	String[] temp = temps.split(";");
                    	String[] tempp = temp[0].split("=");
                        String sessionid=tempp[1];
                        prefEditor.putString(JSESSIONID, sessionid);
                	}           
                               
                    
          }
		     	prefEditor.commit();
            
    }

    /**
     * Adds session cookie to headers if exists.
     * @param headers
     */
    public final void addSessionCookie(Map<String, String> headers) {
        String sessionId = _preferences.getString(JSESSIONID, "");
        String userid = _preferences.getString(Constants.COOKIE_USERID, "");
        if (sessionId.length() > 0) {
            StringBuilder builder = new StringBuilder();
            builder.append(JSESSIONID);
            builder.append("=");
            builder.append(sessionId);
            if (headers.containsKey(COOKIE_KEY)) {
                builder.append("; ");
                builder.append(headers.get(COOKIE_KEY));
            }
            headers.put(COOKIE_KEY, builder.toString());
        }
        if (userid.length() > 0) {
            StringBuilder builder = new StringBuilder();
            builder.append(Constants.COOKIE_USERID);
            builder.append("=");
            builder.append(userid);
            if (headers.containsKey(COOKIE_KEY)) {
                builder.append("; ");
                builder.append(headers.get(COOKIE_KEY));
            }
            headers.put(COOKIE_KEY, builder.toString());
        }        
    }

}