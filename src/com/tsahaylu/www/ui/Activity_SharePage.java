package com.tsahaylu.www.ui;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.Volley;
import com.tsahaylu.www.R;
import com.tsahaylu.www.common.Constants;
import com.tsahaylu.www.service.CoreService;
import com.tsahaylu.www.util.DateUtils;
import com.tsahaylu.www.util.StringRequest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Activity_SharePage extends Activity {
	
	private EditText titlev;
	private EditText urlv;
	private static RequestQueue mQueue;
  	private HashMap<String,String> grouplist;
  	private HashMap<String,String> friendlist;
  	private boolean tome=false;
  	private boolean toall=true;
	private TextView tolist;
	private String groupidss ="";
	private String friendidss ="";
	
	CoreService cs=CoreService.getCoreService();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity__share_page);
		
		mQueue = Volley.newRequestQueue(this);
		initView();		
		Intent intent = getIntent();
	    String action = intent.getAction();
	    String type = intent.getType();

	    if (Intent.ACTION_SEND.equals(action) && ("text/plain".equals(type))) {
	  
	    	handleSendText(intent);
	    	
	        }
	    
	}
	
	public void initView()
	{	        
		   urlv = (EditText) findViewById(R.id.url);
		   titlev = (EditText) findViewById(R.id.title);
		   tolist=(TextView) this.findViewById(R.id.tolist);
	}
	
	public void handleSendText(Intent intent) {
	    String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
	    String sharedSubject = intent.getStringExtra(Intent.EXTRA_SUBJECT);
	    if (sharedText != null) {
	    	
	    	titlev.setText(sharedSubject);
	    	urlv.setText(sharedText);
	    	    
	    }
	}
	
	
	public void StartFriendsSelector(View view)
	{
		Intent intent = new Intent(this, Activity_FriendsSelector.class);
		startActivityForResult(intent,0);   
	}
		
	@Override
	protected void onActivityResult(int requestCode, int responseCode, Intent intent) {			
		
    	Bundle data = intent.getExtras(); 
    	grouplist = (HashMap<String, String>) data.getSerializable("grouplist");
    	friendlist = (HashMap<String, String>) data.getSerializable("friendlist");
    	CharSequence tolists=data.getCharSequence("tolist");
    	toall=data.getBoolean("toall");
    	tome=data.getBoolean("tome");
    	tolist.setText(tolists);		
	}
	
	
	public void formatTolist()
	{
		StringBuffer gsb=new StringBuffer();
	
		Iterator iter = grouplist.keySet().iterator(); 
		while (iter.hasNext()) { 
			String key = (String) iter.next();
			if (gsb.length()>0)
		    	gsb.append("|"+key);
			else
				gsb.append(key);
		} 
		
		
		StringBuffer fsb=new StringBuffer();
		Iterator itera = friendlist.keySet().iterator(); 
		while (itera.hasNext()) { 
			String key = (String) itera.next(); 	    
			if (fsb.length()>0)
				fsb.append("|"+key);
			else
				fsb.append(key);
		}		
		
		groupidss=gsb.toString();		
		friendidss=fsb.toString();
	}
	
	
	
	public void SendLink(View view)
	{
		

		
		String toalls;
		if (toall)
		 toalls =Constants.RETURN_SUCCESS;
		else
		 toalls =Constants.RETURN_FAILUTE;
		
		
		String tomes;
		if (tome)
			tomes =Constants.RETURN_SUCCESS;
		else
			tomes =Constants.RETURN_FAILUTE;
				
		String url =  urlv.getText().toString();
		String sendtimes = DateUtils.getCurrenTimeString();
		String urltitles = titlev.getText().toString();
		
		String requesturl = Constants.URL_HOST+Constants.URL_FAVURL_SEND;
    	
	 	Map<String, String> params = new HashMap<String, String>();  
		params.put("groupids", groupidss);	
		params.put("friendids", friendidss);
		params.put("toall", toalls);	
		params.put("tome", tomes);	
		params.put("url", url);	
		params.put("sendtime", sendtimes);	
		params.put("urltitle", urltitles);	
	 	
	 	StringRequest stringRequest = new StringRequest(Request.Method.POST, requesturl, params, new Listener<String>(){

			@Override
			public void onResponse(String json) {
				// TODO Auto-generated method stub
				
				if (json!=null)
		    	{
					if(json.equals("true"))
					{
						Activity_SharePage.this.finish();
					}
		    	}			
				
			}},new ErrorListener(){

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				
			}}){  
			
		};	
		
		mQueue.add(stringRequest);	 		
	}
}
