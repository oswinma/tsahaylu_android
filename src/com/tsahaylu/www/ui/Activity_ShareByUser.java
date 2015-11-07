package com.tsahaylu.www.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.tsahaylu.www.R;
import com.tsahaylu.www.adapter.UserDTOAdapter;
import com.tsahaylu.www.common.Constants;
import com.tsahaylu.www.dto.UserDTO;
import com.tsahaylu.www.service.CoreService;
import com.tsahaylu.www.util.StringRequest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class Activity_ShareByUser extends Activity {

	private ListView listview;
	
  	private ArrayList<UserDTO> clist;
  	private UserDTOAdapter adapter;
	CoreService cs=CoreService.getCoreService();
	private String urlids;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share_by_user);
		initView();
		geturlids();
		getShareUsers();
	}
	
	
    protected void geturlids()  
    {      	
    	Bundle data = getIntent().getExtras(); 
    	urlids = data.getString("urlid");
	}
    
	public void initView()
	{ 	
			clist=new ArrayList<UserDTO>(); 
           listview = (ListView) findViewById(R.id.list_view);
           adapter=new UserDTOAdapter(this, clist, R.layout.user);
           listview.setAdapter(adapter);
	}
	
	 private  void getShareUsers() {
			// TODO Auto-generated method stub
			
		String requesturl = Constants.URL_HOST+Constants.URL_USER_SHARE;
	    	
	 	Map<String, String> params = new HashMap<String, String>();  
		params.put("urlids", urlids);	
	 	
	 	StringRequest stringRequest = new StringRequest(Request.Method.POST, requesturl, params, new Listener<String>(){

			@Override
			public void onResponse(String json) {
				// TODO Auto-generated method stub
				
				if (json!=null)
		    	{
					clist.addAll(cs.getUserDTOListFromJson(json));
					adapter.notifyDataSetChanged();				
		    	}			
				
			}},new ErrorListener(){

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				
			}}){  
			
		};	
		
		cs.addStringRequestToQueue(stringRequest);	 		
	 		
		}
	
}
