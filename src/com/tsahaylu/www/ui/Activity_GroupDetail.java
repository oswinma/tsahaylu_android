package com.tsahaylu.www.ui;

import java.util.ArrayList;

import com.tsahaylu.www.R;
import com.tsahaylu.www.R.id;
import com.tsahaylu.www.R.layout;
import com.tsahaylu.www.R.menu;
import com.tsahaylu.www.adapter.FriendAdapter;
import com.tsahaylu.www.common.Constants;
import com.tsahaylu.www.dto.Contact;
import com.tsahaylu.www.service.CoreService;
import com.tsahaylu.www.util.StringRequest;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class Activity_GroupDetail extends Activity {

	
	private ListView listview;
  	private ArrayList<Contact> clist;
  	private FriendAdapter adapter;
	CoreService cs=CoreService.getCoreService();
  	private static Handler handler=new Handler(); 	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_detail);
		
		handler.post(new Runnable() {
			public void run() {
				initView();
				getIntentData();
			}
		});	
		
	}

	protected void getIntentData()  
    {      	
    	Bundle data = getIntent().getExtras(); 
    	String groupid = data.getString("groupid");
    	if (groupid!=null)
    	getGroupData(groupid);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity__group_detail, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void initView()
	{ 	
		   clist=new ArrayList<Contact>(); 
           listview = (ListView) findViewById(R.id.list_view);
           adapter=new FriendAdapter(this, clist, R.layout.friend);
           listview.setAdapter(adapter);
	}
	
  
	public void getGroupData(String groupid)
	{
    	String requesturl = Constants.URL_HOST+Constants.URL_GROUP_DATA;
    	
    	if (groupid!=null)
    		requesturl=requesturl+"?groupid="+groupid;

    	StringRequest stringRequest = new StringRequest(Request.Method.GET, requesturl, null, new Listener<String>(){

			@Override
			public void onResponse(String json) {
				// TODO Auto-generated method stub
				
				if (json!=null)
		    	{
		        	ArrayList<Contact> newlist=cs.getContactsFromJson(json);

		        	if (newlist.size()>0)
		        	{
						clist.addAll(newlist);
						adapter.notifyDataSetChanged();			
		        	}
		        	
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
