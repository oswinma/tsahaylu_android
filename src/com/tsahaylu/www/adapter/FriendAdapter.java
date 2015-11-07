package com.tsahaylu.www.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.tsahaylu.www.R;
import com.tsahaylu.www.R.drawable;
import com.tsahaylu.www.common.Constants;
import com.tsahaylu.www.common.FriendStatus;
import com.tsahaylu.www.dto.CommentDTO;
import com.tsahaylu.www.dto.Contact;
import com.tsahaylu.www.service.CoreService;
import com.tsahaylu.www.ui.Activity_UserProfile;
import com.tsahaylu.www.util.DateUtils;
import com.tsahaylu.www.util.StringRequest;

public class FriendAdapter extends BaseAdapter {	
	
	 private ArrayList<Contact> list;// 要绑定的数据
	 private int resource;// 绑定的一个条目界面的id，此例中即为item.xml
	 private LayoutInflater inflater;// 布局填充器，它可以使用一个xml文件生成一个View对象，可以通过Context获取实例对象
	 CoreService cs=CoreService.getCoreService();
	 
	public FriendAdapter(Context context, ArrayList<Contact> clist, int resource) {
		// TODO Auto-generated constructor stub
		
		  inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		  this.resource = resource;
		  this.list = clist;	
		  
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
   	  
		   ViewCache cache;
		  if (convertView == null) {// 显示第一页的时候convertView为空
		   cache = new ViewCache();		   
		   convertView = inflater.inflate(resource, null);// 生成条目对象
		   cache.nicknamev  = (TextView) convertView.findViewById(R.id.nickname);
		   cache.avatarURLv = (ImageView) convertView.findViewById(R.id.avatarURL);
		   cache.inviteButtonv = (Button) convertView.findViewById(R.id.inviteButton);
		   cache.popupbtnv = (ImageButton) convertView.findViewById(R.id.popupbtn);
		   cache.blockbtnv = (ImageButton) convertView.findViewById(R.id.blockbtn);
		   cache.deletebtnv = (ImageButton) convertView.findViewById(R.id.deletebtn);
		   
		   convertView.setTag(cache);
		  } else {
			  
		   cache = (ViewCache) convertView.getTag();
		  }	
		  
		  TextView nicknamev = cache.nicknamev;
		  ImageView avatarURLv = cache.avatarURLv;
		  final Button inviteButtonv=cache.inviteButtonv;
		  
		  final ImageButton popupbtnv= cache.popupbtnv;
		  final ImageButton blockbtnv= cache.blockbtnv;
		  ImageButton deletebtnv= cache.deletebtnv;
		  
		   final Contact contact = list.get(position);
		  	  
		  String avatarURL=contact.getAvatarURL();
		  if (avatarURL!=null && avatarURL!="")
		  cs.setImageView(avatarURLv, avatarURL);
		  else
			  avatarURLv.setImageResource(R.drawable.default_avatar);
		  
		  nicknamev.setText(contact.getNickname());
		    
		  
		  avatarURLv.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				startActivity_UserProfile();
			}		  
			  
		  });
		  
		 
		deletebtnv.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				deleteFriend(contact.getId());
			}			  
			  
		  });
		  
		if (contact.getStatus()==FriendStatus.BLOCK)
		{
			blockbtnv.setImageResource(drawable.block);
		}
		else
		{
			blockbtnv.setImageResource(drawable.unblock);
		}
		  
		blockbtnv.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				String block;
				
				if (contact.getStatus()==FriendStatus.BLOCK)
				{
					block="false";
					blockbtnv.setImageResource(drawable.unblock);
				}
				else
				{
					block="true";
					blockbtnv.setImageResource(drawable.block);
				}
				
				blockFriend(contact.getId() ,block);
			}			  
			  
		  });
		  
		
		if (contact.isPopup())
		{
			popupbtnv.setImageResource(android.R.drawable.ic_lock_silent_mode_off);
		}
		else
		{
			popupbtnv.setImageResource(android.R.drawable.ic_lock_silent_mode);
		}
		
		
		popupbtnv.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View view) {
				
				// TODO Auto-generated method stub
				String popup;
				if (contact.isPopup())
				{
					popup="false";
					popupbtnv.setImageResource(android.R.drawable.ic_lock_silent_mode_off);
				}
				else
				{
					popup="true";
					popupbtnv.setImageResource(android.R.drawable.ic_lock_silent_mode);
				}
				
				popupFriend(contact.getId() ,popup);
			}			  
			  
		  });
		  
		
		inviteButtonv.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				inviteUser(contact.getId() ,inviteButtonv);
			}			  
			  
		  });
		
		  return convertView;
		  
	}

	public void deleteFriend(String userid)
   	{
		String requesturl = Constants.URL_HOST+Constants.URL_FRIEND_DELETE;
    	
	 	Map<String, String> params = new HashMap<String, String>();  
		params.put("id",userid);	
	
		StringRequest stringRequest = new StringRequest(Request.Method.POST, requesturl, params, new Listener<String>(){

		@Override
		public void onResponse(String result) {
			// TODO Auto-generated method stub
			
			if (result!=null)
	    	{
				if(result.equals("true"))
				{
					
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
	
	
	public void blockFriend(String userid,String block)
   	{
		String requesturl = Constants.URL_HOST+Constants.URL_FRIEND_BLOCK;
    	
	 	Map<String, String> params = new HashMap<String, String>();  
		params.put("id",userid);	
		params.put("block", block);
		
		StringRequest stringRequest = new StringRequest(Request.Method.POST, requesturl, params, new Listener<String>(){

		@Override
		public void onResponse(String result) {
			// TODO Auto-generated method stub
			
			if (result!=null)
	    	{
				if(result.equals("true"))
				{
					
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
	
	public void popupFriend(String userid,String popup)
   	{
		String requesturl = Constants.URL_HOST+Constants.URL_FRIEND_POPUP;
    	
	 	Map<String, String> params = new HashMap<String, String>();  
		params.put("id",userid);	
		params.put("popup", popup);
		
		StringRequest stringRequest = new StringRequest(Request.Method.POST, requesturl, params, new Listener<String>(){

		@Override
		public void onResponse(String result) {
			// TODO Auto-generated method stub
			
			if (result!=null)
	    	{
				if(result.equals("true"))
				{
					
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
	
	
	public void inviteUser(String userid, final TextView inviteButtonv)
   	{
		String requesturl = Constants.URL_HOST+Constants.URL_FRIEND_INVITE;
    	
	 	Map<String, String> params = new HashMap<String, String>();  
		params.put("toid",userid);	
	
		StringRequest stringRequest = new StringRequest(Request.Method.POST, requesturl, params, new Listener<String>(){

		@Override
		public void onResponse(String result) {
			// TODO Auto-generated method stub
			
			if (result!=null)
	    	{
				if(result.equals("true"))
				{
					inviteButtonv.setText("Invited");
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
	
	 private static class ViewCache {
		 
		  public ImageButton deletebtnv;
		public ImageButton blockbtnv;
		public ImageButton popupbtnv;
		public TextView nicknamev;
		  public ImageView avatarURLv;
		  public Button inviteButtonv;
		 }
	 
	   	public void startActivity_UserProfile()
		{
				Intent intent = new Intent(inflater.getContext().getApplicationContext(), Activity_UserProfile.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				inflater.getContext().getApplicationContext().startActivity(intent);		
		}
}
