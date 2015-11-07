package com.tsahaylu.www.ui;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.tsahaylu.www.R;
import com.tsahaylu.www.common.Constants;
import com.tsahaylu.www.dto.UserDTO;
import com.tsahaylu.www.service.CoreService;
import com.tsahaylu.www.util.StringRequest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Activity_Register extends Activity {

	private EditText email_edit;
	private EditText nickname_edit;
	private EditText country_edit;
	private EditText password_edit;
	private EditText password_confirm;
	private String provider=null;
	private CoreService cs=CoreService.getCoreService(); 
	private String openid_identifier;
	private String provider_id;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_register);
		super.onCreate(savedInstanceState);
		email_edit=(EditText) findViewById(R.id.email_edit);
		nickname_edit=(EditText) findViewById(R.id.nickname_edit);
		country_edit=(EditText) findViewById(R.id.country_edit);
		password_edit=(EditText) findViewById(R.id.password_edit);
		password_confirm=(EditText) findViewById(R.id.password_confirm_edit);		
		getSignUpInfo();
	}


      
    protected void getSignUpInfo()  
    {  
    	
    	Bundle data = getIntent().getExtras(); 
	        	 UserDTO userdto = (UserDTO) data.getSerializable("userdto");
	        	 if (userdto!=null)
	        	 {
	        		 email_edit.setText(userdto.getEmail());
	        		 nickname_edit.setText(userdto.getNickname());
	        		 country_edit.setText(userdto.getCountry()); 
	        		 provider_id=userdto.getId();
	        	 }
	        	 provider = (String) data.get("openid_provider");
	        	 openid_identifier= (String) data.get("openid_url");
	}
    
    
    public void Signup(View view)
    {
    	String email=email_edit.getText().toString();
    	String nickname=nickname_edit.getText().toString();
    	String country=country_edit.getText().toString();
    	String password=password_edit.getText().toString();
   		
    	
    	if (provider==null)
    	{
    		normalSignUP(email, nickname, country, password);
    	}
    	else
    	{
    		
    		if (provider.equals("google"))
    		{
    			provider_id=email;
    		}
    		
			OpendIdSignUP(email, nickname, country, password,provider_id,openid_identifier);    		
    	}

    	
    }    
    
    
    public void OpendIdSignUP(String email, String nickname, String country, String password, String provider_id, String openid_identifier)
    {
		String url =Constants.URL_HOST+Constants.URL_OPENIDSIGNUP;		
		final Map<String, String> params = new HashMap<String, String>();  
		params.put("email",email);	 
		params.put("nickname",nickname);
		params.put("country",country);
		params.put("password",password);
		params.put("openid_identifier",openid_identifier);
		params.put("provider_id",provider_id);
		params.put("openid_provider",provider);
		    				
		StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, url,params, new Listener<String>(){

			@Override
			public void onResponse(String response) {
				// TODO Auto-generated method stub
				
				if (response!=null)
		    	{
		    		JSONObject ja;
					try {				
						
						ja=new  JSONObject(response);
						String result=ja.getString("pass");						
						String userdtostring=ja.getString("user");
						UserDTO userdto=cs.getUserDTOFromJson(userdtostring);
						
						if (result.equals("true"))
						{							
							StartMainActivity();
							cs.saveUserDTOToDisk(userdto);
						}
						else
						{
							
						}
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
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
    
    public void normalSignUP(String email, String nickname, String country, String password)
    {
		String url =Constants.URL_HOST+Constants.URL_NORMALSIGNUP;		
		final Map<String, String> params = new HashMap<String, String>();  
		params.put("email",email);	 
		params.put("nickname",nickname);
		params.put("country",country);
		params.put("password",password);
		    				
		StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, url,params, new Listener<String>(){

			@Override
			public void onResponse(String result) {
				// TODO Auto-generated method stub
				
				if (result!=null)
		    	{
					if (result.equals("true"))
					{
						Toast.makeText(getApplicationContext(), "Sign Up successfully", Toast.LENGTH_LONG).show();
						StartWelcomeActivity();
					}
					else
					{
						Toast.makeText(getApplicationContext(), "Sign Up error", Toast.LENGTH_LONG).show();
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
    
	public void StartMainActivity()
	{		
		
		Intent intent = new Intent(Activity_Register.this, Activity_Main.class);
		startActivityForResult(intent,0);
		Activity_Register.this.finish();		
	}
	

	public void StartWelcomeActivity()
	{		
		
		Intent intent = new Intent(Activity_Register.this, Activity_Welcome.class);
		startActivityForResult(intent,0);
		Activity_Register.this.finish();		
	}

	
}
