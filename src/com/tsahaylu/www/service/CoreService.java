package com.tsahaylu.www.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;







import org.json.JSONException;
import org.json.JSONObject;

import com.tsahaylu.www.R;
import com.tsahaylu.www.common.Constants;
import com.tsahaylu.www.dto.CommentDTO;
import com.tsahaylu.www.dto.Contact;
import com.tsahaylu.www.dto.FavURLShow;
import com.tsahaylu.www.dto.GroupDTO;
import com.tsahaylu.www.dto.MessageInfo;
import com.tsahaylu.www.dto.UserDTO;
import com.tsahaylu.www.util.BitmapMemoryCache;
import com.tsahaylu.www.util.FavurlShowDiskCache;
import com.tsahaylu.www.util.StringRequest;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.preference.PreferenceManager;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class CoreService {

	private static ObjectMapper mapper = new ObjectMapper();
	private static RequestQueue mQueue;
	private static Context context;
	private static FavurlShowDiskCache favdiskcache;
	
	private static CoreService cs=new CoreService();
	
	private CoreService()
	{
		
	}
	
	public static CoreService getCoreService()
	{
		return cs;
	}
	

	public void initRequestQueue(Context context)
	{
		this.context=context;
		favdiskcache=new FavurlShowDiskCache(context);
	
/*		DefaultHttpClient httpclient = new DefaultHttpClient();

		CookieStore cookieStore = new BasicCookieStore();
		httpclient.setCookieStore( cookieStore );

		HttpStack httpStack = new HttpClientStack( httpclient );*/
		mQueue = Volley.newRequestQueue(context);
	}
	
	public static void setImageView(ImageView imageView, String url)
	{
		
		ImageLoader imageLoader = new ImageLoader(mQueue, new BitmapMemoryCache(context));  
		ImageListener listener = ImageLoader.getImageListener(imageView,R.drawable.default_avatar, R.drawable.default_avatar);		
		imageLoader.get(url, listener);
		
	}
	
	
	public static Bitmap getBitmapfromNetwork(String url)
	{
		
		Bitmap bitmap=null;
		
		ImageRequest imageRequest = new ImageRequest(url,  
		        new Response.Listener<Bitmap>() {  
		            @Override  
		            public void onResponse(Bitmap response) {  
		            	
		            }  
		        }, 0, 0, Config.RGB_565, new Response.ErrorListener() {  
		            @Override  
		            public void onErrorResponse(VolleyError error) {  
		                  
		            }  
		        });  
		
		        return null;        
	}
	
	
	public static ArrayList<FavURLShow> getFavurlListFromDisk(String key)
	{
		return favdiskcache.getFavurlshowlist(key);		
	}
	
	 public static void putFavurlshowlistToDisk(final String key, final ArrayList<FavURLShow> list)
	 {
		 favdiskcache.putFavurlshowlist(key, list);
	 }
	 
	 
	 public String getstartCursor(String key) 
	 {
		 return favdiskcache.getstartCursor(key);
	 }
	 
	 public void putstartCursor(final String key, final String startCursor) 
	 { 
		 favdiskcache.putstartCursor(key, startCursor);
	 }
	    
		
	public static void addStringRequestToQueue(StringRequest stringRequest)
	{
		
		if (stringRequest!=null)
		mQueue.add(stringRequest );		
	}
	
	
	public static void addStringRequestToQueue(StringRequest stringRequest,Context c)
	{
		if (mQueue==null)
			mQueue = Volley.newRequestQueue(c);			
		
		if (stringRequest!=null)
		mQueue.add(stringRequest );		
	}
	
	
	public static JSONObject  convertJsonToJSONObject(String json)
	{
		
		JSONObject ja=null;
		if (json!=null)
		try {
			ja=new  JSONObject(json);			
			
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return ja;
        
	}
	
	public static String  getStartCursorFromJSONObject(JSONObject ja)
	{
		
		String startCursor=null;
		if (ja!=null)
		try {
			startCursor = ja.getString("startCursor");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return startCursor;
        
	}
	
	public static UserDTO  getUserDTOFromJson(String json)
	{

		UserDTO userdto=null;
		try {
			userdto = mapper.readValue(json,UserDTO.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  		
        return userdto;        
	}
	
	
	public static ArrayList<FavURLShow>  getFavURLShowsFromJSONObject(JSONObject ja)
	{
		
		
		ArrayList<FavURLShow> fuslist=null;
		try {
		String favurlshows=ja.getString("FavURLShows");
		JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, FavURLShow.class);   
		fuslist = (ArrayList<FavURLShow>)mapper.readValue(favurlshows,javaType);  
			
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        return fuslist;        
	}
	
	public static void saveUserDTOToDisk(UserDTO userdto)
	{
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.USERDTO_ID, userdto.getId());
        editor.putString(Constants.USERDTO_EMAIL, userdto.getEmail());
        editor.putString(Constants.USERDTO_NICKNAME, userdto.getNickname());
        editor.putString(Constants.USERDTO_COUNTRY, userdto.getCountry());
        editor.putString(Constants.USERDTO_LANGUAGE, userdto.getLanguage());
        editor.putString(Constants.USERDTO_AVATARURL, userdto.getAvatarURL());
        editor.putInt(Constants.USERDTO_STATUS, userdto.getStatus());
        editor.putString(Constants.COOKIE_USERID, userdto.getId());
        editor.commit();        
	}
 
	public static UserDTO getUserDTOFromDisk()
	{
		 UserDTO userdto=null;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        
        String id= sharedPreferences.getString(Constants.USERDTO_ID, "");
        if (id!=null&&id!="")
        {
            userdto=new UserDTO();
            userdto.setId(id);
            userdto.setEmail(sharedPreferences.getString(Constants.USERDTO_EMAIL, ""));
            userdto.setNickname(sharedPreferences.getString(Constants.USERDTO_NICKNAME, ""));
            userdto.setCountry(sharedPreferences.getString(Constants.USERDTO_COUNTRY, ""));
            userdto.setLanguage(sharedPreferences.getString(Constants.USERDTO_LANGUAGE, ""));
            userdto.setAvatarURL(sharedPreferences.getString(Constants.USERDTO_AVATARURL, ""));
            userdto.setStatus(sharedPreferences.getInt(Constants.USERDTO_STATUS,0));  
        }     
        return  userdto;
	}
	
	public static UserDTO getUserDTOFromDisk(Context c)
	{
		 UserDTO userdto=null;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
        
        String id= sharedPreferences.getString(Constants.USERDTO_ID, "");
        if (id!=null&&id!="")
        {
            userdto=new UserDTO();
            userdto.setId(id);
            userdto.setEmail(sharedPreferences.getString(Constants.USERDTO_EMAIL, ""));
            userdto.setNickname(sharedPreferences.getString(Constants.USERDTO_NICKNAME, ""));
            userdto.setCountry(sharedPreferences.getString(Constants.USERDTO_COUNTRY, ""));
            userdto.setLanguage(sharedPreferences.getString(Constants.USERDTO_LANGUAGE, ""));
            userdto.setAvatarURL(sharedPreferences.getString(Constants.USERDTO_AVATARURL, ""));
            userdto.setStatus(sharedPreferences.getInt(Constants.USERDTO_STATUS,0));  
        }     
        return  userdto;
	}
	
	public ArrayList<CommentDTO> getCommentDTOFromJSONObject(JSONObject jo) {
		// TODO Auto-generated method stub
		ArrayList<CommentDTO> list=null;
		
		if (jo!=null)
		try {
		String CommentDTOs=jo.getString("Comments");
		JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, CommentDTO.class);   
		list = (ArrayList<CommentDTO>)mapper.readValue(CommentDTOs,javaType);  
			
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        return list;   
	}

	public void putCommentDTOToDisk(String string, ArrayList<CommentDTO> clist) {
		// TODO Auto-generated method stub
		
	}

	public ArrayList<Contact> getContactsFromJson(String json) {
		// TODO Auto-generated method stub	
		
		JSONObject ja=null;
	
		ArrayList<Contact> clist=null;
		
		try {
			
		ja=new  JSONObject(json);	
			
		String contacts=ja.getString("Contacts");
		JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, Contact.class);   
		clist = (ArrayList<Contact>)mapper.readValue(contacts,javaType);  
			
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        return clist;  
	}

	
	public ArrayList<UserDTO> getUserDTOListFromJson(String json) {
		// TODO Auto-generated method stub	
		
		JSONObject ja=null;
	
		ArrayList<UserDTO> clist=null;
		
		try {
			
		ja=new  JSONObject(json);	
			
		String userdtos=ja.getString("UserDTOs");
		JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, UserDTO.class);   
		clist = (ArrayList<UserDTO>)mapper.readValue(userdtos,javaType);  
			
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        return clist;  
	}
	
	public ArrayList<MessageInfo> getMessageInfoFromJSONObject(JSONObject jo) {
		// TODO Auto-generated method stub
		ArrayList<MessageInfo> list=null;
		try {
		String MessageInfos=jo.getString("MessageInfos");
		JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, MessageInfo.class);   
		list = (ArrayList<MessageInfo>)mapper.readValue(MessageInfos,javaType);  
			
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        return list;   
	}
	
	
	public void ToggleArchive(String favurlid)
	{
				
		String url =Constants.URL_HOST+Constants.URL_FAVURL_STATUS;		
		Map<String, String> params = new HashMap<String, String>();
			
		String json= "[{'id':"+favurlid+",'status':2}]";		
		params.put("json", json);
		
		StringRequest stringRequest = new StringRequest(Method.POST, url, params, new Listener<String>(){

			@Override
			public void onResponse(String json) {
				// TODO Auto-generated method stub
				
				
			}},new ErrorListener(){

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				
			}}){  
			

		};  
		
		cs.addStringRequestToQueue(stringRequest);		
	}
	
	public void ToggleUnArchive(String favurlid)
	{
		
		String url =Constants.URL_HOST+Constants.URL_FAVURL_STATUS;		
		Map<String, String> params = new HashMap<String, String>();
			
		String json= "[{'id':"+favurlid+",'status':1}]";		
		params.put("json", json);
		
		StringRequest stringRequest = new StringRequest(Method.POST, url, params, new Listener<String>(){

			@Override
			public void onResponse(String json) {
				// TODO Auto-generated method stub
				
				if (json!=null)
		    	{
				}
				
				
			}},new ErrorListener(){

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				
			}}){  
			

		};  
		
		cs.addStringRequestToQueue(stringRequest);		
	}
	
	
	public void ToggleFav(String favurlid)
	{
		
		String url =Constants.URL_HOST+Constants.URL_FAVURL_FAV_UPDATE;		
		final Map<String, String> params = new HashMap<String, String>();  
		params.put("id", favurlid);
		params.put("fav", "true");
		
		StringRequest stringRequest = new StringRequest(Method.POST, url, params, new Listener<String>(){

			@Override
			public void onResponse(String json) {
				// TODO Auto-generated method stub
				
				
			}},new ErrorListener(){

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				
			}}){  
			
		};  
		
		cs.addStringRequestToQueue(stringRequest);		
	}
	
	public void ToggleUnFav(String favurlid)
	{
		
		String url =Constants.URL_HOST+Constants.URL_FAVURL_FAV_UPDATE;		
		final Map<String, String> params = new HashMap<String, String>();  
		params.put("id", favurlid);
		params.put("fav", "false");
		
		StringRequest stringRequest = new StringRequest(Method.POST, url, params, new Listener<String>(){

			@Override
			public void onResponse(String json) {
				// TODO Auto-generated method stub
				
				
			}},new ErrorListener(){

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				
			}}){  
			
		};  
		
		cs.addStringRequestToQueue(stringRequest);		
	}
	
	public void ToggleDelete(String favurlid)
	{
		
		String url =Constants.URL_HOST+Constants.URL_FAVURL_STATUS;		
		Map<String, String> params = new HashMap<String, String>();
			
		String json= "[{'id':"+favurlid+",'status':3}]";		
		params.put("json", json);
		
		StringRequest stringRequest = new StringRequest(Method.POST, url, params, new Listener<String>(){

			@Override
			public void onResponse(String json) {
				// TODO Auto-generated method stub
							
				
			}},new ErrorListener(){

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				
			}}){  
			

		};  
		
		cs.addStringRequestToQueue(stringRequest);		
		
	}

	public ArrayList<GroupDTO> getGROUPDTOFromJSONObject(JSONObject jo) {
		// TODO Auto-generated method stub
		ArrayList<GroupDTO> list=null;
		try {
		String datastr=jo.getString("GroupDTOs");
		JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, GroupDTO.class);   
		list = (ArrayList<GroupDTO>)mapper.readValue(datastr,javaType);  
			
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        return list;
	}

	public void UpdateDiskFavURLShow( FavURLShow favurlshow, int position) {
		// TODO Auto-generated method stub
        ArrayList<FavURLShow> list=cs.getFavurlListFromDisk("new");
        list.set(position, favurlshow);
        cs.putFavurlshowlistToDisk("new", list);
	}
	
	
}
