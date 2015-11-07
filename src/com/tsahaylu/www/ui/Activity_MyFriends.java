package com.tsahaylu.www.ui;

import java.util.ArrayList;

import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.tsahaylu.www.R;
import com.tsahaylu.www.adapter.FriendsGroupAdapter;
import com.tsahaylu.www.common.Constants;
import com.tsahaylu.www.dto.Contact;
import com.tsahaylu.www.dto.GroupDTO;
import com.tsahaylu.www.service.CoreService;
import com.tsahaylu.www.util.StringRequest;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;


public class Activity_MyFriends extends Activity {

	
	CoreService cs=CoreService.getCoreService();
	
	private ExpandableListView expandview;
	private FriendsGroupAdapter adapter;
	
	
	private ArrayList<GroupDTO> glist; 
	private ArrayList<ArrayList<Contact>> clist; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_friends);
		
		initView();
		getGroups();
	}
	
	public void initView()
	{
		
		 expandview = (ExpandableListView) findViewById(R.id.expandview);    
	        
		 glist = new ArrayList<GroupDTO>();	       
		 clist=new ArrayList<ArrayList<Contact>>();
		 
	     adapter = new  FriendsGroupAdapter(this, glist,clist, R.layout.friend_group,R.layout.friend);            	
	     expandview.setAdapter(adapter);    
	}
	
	public void getGroups()
	{
    	String requesturl = Constants.URL_HOST+Constants.URL_GROUP_INFO;

    	StringRequest stringRequest = new StringRequest(Request.Method.GET, requesturl, null, new Listener<String>(){

			@Override
			public void onResponse(String json) {
				// TODO Auto-generated method stub
				
				if (json!=null)
		    	{
					JSONObject jo=cs.convertJsonToJSONObject(json);
		        	ArrayList<GroupDTO> newlist=cs.getGROUPDTOFromJSONObject(jo);

		        	if (newlist.size()>0)
		        	{
		        		glist.addAll(newlist);
		        		getGroupContact();
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
	
	public void getGroupContact()
	{
		
		if (glist.size() > 0) {

			for (int j = 0,len=glist.size(); j < len; j++) {
				GroupDTO group= glist.get(j);
				final int index =j;
				getGroupData(group.getId().toString(),index);			
			}
		}
		
		
	}
	
	public void getGroupData(String groupid,final int position)
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
						clist.add(position,newlist);
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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity__my_friends, menu);
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
	

	
}
