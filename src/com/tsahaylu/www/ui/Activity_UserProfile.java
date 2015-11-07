package com.tsahaylu.www.ui;



import com.tsahaylu.www.R;
import com.tsahaylu.www.service.CoreService;

import android.app.ActionBar;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class Activity_UserProfile extends FragmentActivity {

	private TextView nicknamev ;
	private ImageView avatarURLv;
  	private String userid=null;  
	CoreService cs=CoreService.getCoreService();
  	private static Handler handler=new Handler();
  	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity__user_profile);
		
		handler.post(new Runnable() {
			public void run() {
				initTab();
				initView();
				getUserInfo();  				
			}
		});	
		

	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case android.R.id.home:  
	            finish();  
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	public void initView()
	{
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        
		   nicknamev = (TextView) findViewById(R.id.nickname);
		   avatarURLv = (ImageView) findViewById(R.id.avatarURL);       	
       
	}
	
	
	public void initTab()
	{
		FragmentTabHost tabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);

	    tabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
	    tabHost.addTab(tabHost.newTabSpec("FavURL").setIndicator("FavURL"), Fragment_UserFavURL.class, null);
	    tabHost.addTab(tabHost.newTabSpec("Friend") .setIndicator("Friend"),  Fragment_UserFriend.class, null);
	}
	

	
	protected void getUserInfo()  
    {      	
    	Bundle data = getIntent().getExtras();     	

    	userid=data.getString("userid");
    	String nickname=data.getString("nickname");
    	String avatarURL=data.getString("avatarURL");
		
	        	 if (avatarURL!=null)
	        	 {
	    		  if (avatarURL!=null && avatarURL!="")
	    		  cs.setImageView(avatarURLv, avatarURL);
	    		  else
	    			  avatarURLv.setImageResource(R.drawable.default_avatar);   		  
	    		  
	    		  nicknamev.setText(nickname);
	        	 }  		  
	    		  
	}


	public String getUserID() {
		// TODO Auto-generated method stub
		return this.userid;
	}

	

}
