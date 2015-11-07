package com.tsahaylu.www.ui;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.tsahaylu.www.R;
import com.tsahaylu.www.R.id;
import com.tsahaylu.www.common.Constants;
import com.tsahaylu.www.dto.UserDTO;
import com.tsahaylu.www.openid.Activity_TwitterOAuth;
import com.tsahaylu.www.openid.TwitterUtil;
import com.tsahaylu.www.service.CoreService;
import com.tsahaylu.www.util.StringRequest;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

public class Activity_Welcome extends Activity {


	private EditText mUserNameEditText;
	private EditText mPasswdEditText;
	private CheckBox mRememberPwdCheckBox;
	private CheckBox mAutoLoginCheckBox;
	private CoreService cs=CoreService.getCoreService();   
	private Twitter twitter;
	private boolean openidcheckpassed=false;
	private UserDTO tempuser=null;	
	private User tuser;
	private GoogleApiClient mGoogleApiClient;
	private boolean mIntentInProgress;	 
	private boolean mSignInClicked;
	private static final int RC_SIGN_IN =995;
	private ConnectionResult mConnectionResult;
	public static final String SCOPES = "https://www.googleapis.com/auth/plus.login ";
	private String openid_url=null;
	private PopupWindow popUp;
  	private static Handler handler=new Handler();
  	
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	 
		setContentView(R.layout.activity__welcome);

		
		handler.post(new Runnable() {
			public void run() {
				initView(savedInstanceState);
				TwitterAuthCallback();
				initGoogleApi();
			}
		});	
		
	}
	
	public void initGoogleApi()
	{
	    // Initializing google plus api client
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(ccallback)
                .addOnConnectionFailedListener(cecallback).addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();        
	}
	

	 
	private GoogleApiClient.ConnectionCallbacks ccallback=new GoogleApiClient.ConnectionCallbacks(){

		@Override
		public void onConnected(Bundle bundle) {
			// TODO Auto-generated method stub
			getProfileInformation();
		}

		@Override
		public void onConnectionSuspended(int arg0) {
			// TODO Auto-generated method stub
			mGoogleApiClient.connect();
		}
		
	};
		
	private GoogleApiClient.OnConnectionFailedListener cecallback=new GoogleApiClient.OnConnectionFailedListener(){

			@Override
			public void onConnectionFailed(ConnectionResult result) {
				// TODO Auto-generated method stub
				if (!mIntentInProgress) {
			        // Store the ConnectionResult for later usage
			        mConnectionResult = result;
			 
			        if (mSignInClicked) {
			            // The user has already clicked 'sign-in' so we attempt to
			            // resolve all
			            // errors until the user is signed in, or they cancel.
			            resolveSignInError();
			        }
			    }
				
			}
	};
			
	@Override
	protected void onActivityResult(int requestCode, int responseCode, Intent intent) {			
		
		if (requestCode == RC_SIGN_IN) {
			        if (responseCode != RESULT_OK) {
			            mSignInClicked = false;
			        }
			 
			        mIntentInProgress = false;
			 
			        if (!mGoogleApiClient.isConnecting()) {
			            mGoogleApiClient.connect();
			        }
		  }
		else
		{
			Session.getActiveSession().onActivityResult(this, requestCode, responseCode, intent);
		}
	}

	public void LoginWithGoogle(View view) {
		openid_url=null;
        mSignInClicked = true;
		    if (!mGoogleApiClient.isConnecting()) {

		       if(mConnectionResult==null)
		       {
		    	   mGoogleApiClient.connect();
		       }
		       else		    	   
		        resolveSignInError();
		    }
	}		    
		    
	private void resolveSignInError() {
		        if (mConnectionResult.hasResolution()) {
		            try {
		                mIntentInProgress = true;
		                mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
		            } catch (SendIntentException e) {
		                mIntentInProgress = false;
		                mGoogleApiClient.connect();
		            }
		        }
	}		
	

	
	
	AsyncTask<String, Void, String> getOpenIdURLtask = new getOpenIdURLtask();
    
    
    class getOpenIdURLtask extends AsyncTask<String, Void, String>
    {

        @Override
        protected String doInBackground(String... params) {

           
			try {
				openid_url = GoogleAuthUtil.getToken(Activity_Welcome.this, params[0],"oauth2:" + SCOPES);
			} catch (UserRecoverableAuthException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (GoogleAuthException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}            
            return openid_url;
        }

        @Override
        protected void onPostExecute(String openid_url) {
        	
        	checkGoogleOpenid(openid_url);
        }
        
    }
       
	
	private void checkGoogleOpenid(String openid_url)
	{
		
		String url =Constants.URL_HOST+Constants.URL_OPENIDOAUTH;		
		final Map<String, String> params = new HashMap<String, String>();  
		params.put("openid_url",openid_url);	 
		Map<String, String> cookies=new HashMap<String, String>();
				
		StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, url, cookies, new Listener<String>(){

			@Override
			public void onResponse(String result) {
				// TODO Auto-generated method stub
				
				if (result!=null)
		    	{
						if (result.equals("false"))
						{
							openidcheckpassed=false;							
							StartSignupActivity(Constants.OPENID_PROVIDER_GOOGLE);							
						}
						else
						{
							UserDTO userdto=cs.getUserDTOFromJson(result);							
							cs.saveUserDTOToDisk(userdto);
							openidcheckpassed=true;
							StartMainActivity();
						}						
				 }
				
				
			}},new ErrorListener(){

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				
			}}){  
			
		    @Override  
		    protected Map<String, String> getParams(){		         
		        return params;
		    }  
		};  
		
		cs.addStringRequestToQueue(stringRequest);	
	}
	
	private void getProfileInformation() {
	    try {
	        if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
	        	
	            Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
	            
	            String country = currentPerson.getCurrentLocation();         	           
	            String name = currentPerson.getDisplayName();    	            
	            String email = Plus.AccountApi.getAccountName(mGoogleApiClient);
	            
	            tempuser=null;
    			tempuser=new UserDTO();
    			tempuser.setCountry(country);
				tempuser.setNickname(name);				
				tempuser.setId(email);
				tempuser.setEmail(email);				
				getOpenIdURLtask.execute(email);				
	            
	        } else {
	            Toast.makeText(getApplicationContext(), "Person information is null", Toast.LENGTH_LONG).show();
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
		
	public void initView(Bundle savedInstanceState)
	{
		
	}	
	
	
	public void LoginWithFacebook(View view)
	{
			openid_url=null;
		  Session session = Session.getActiveSession();
		    if (session == null || session.getState().isClosed()) {
		        Session nsession = new Session.Builder(this.getApplicationContext()).build();
		        Session.setActiveSession(nsession);
		        session = nsession;
		    }

		    if (!session.isOpened() && !session.isClosed()) 
		    {
		    	
		        session.openForRead(new Session.OpenRequest(this).setPermissions(Arrays.asList("public_profile","email")).setCallback(statusCallback));
		    } 
		    else 
		    {	        
		    	Session.openActiveSession(this, true, statusCallback);
		    }

	}
	
	
	private Session.StatusCallback statusCallback = new SessionStatusCallback();	
	private class SessionStatusCallback implements Session.StatusCallback {
	    @Override
	    public void call(Session session, SessionState state, Exception exception) {
	            // Respond to session state changes, ex: updating the view
	    	
	    	
	        if (session.isOpened()) {	        	
	        	
				openid_url=session.getAccessToken();
				checkFacebookOpenid(openid_url,session);
		        }
	    	
	    }
	}

	private void checkFacebookOpenid(String openid_url, final Session session)
	{
		
		String url =Constants.URL_HOST+Constants.URL_OPENIDOAUTH;		
		final Map<String, String> params = new HashMap<String, String>();  
		params.put("openid_url",openid_url);	 
				
		StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, url, params, new Listener<String>(){

			@Override
			public void onResponse(String result) {
				// TODO Auto-generated method stub
				
				if (result!=null)
		    	{
						if (result.equals("false"))
						{
							openidcheckpassed=false;							
							Request.newMeRequest(session, facebookusercallback).executeAsync();							
						}
						else
						{
							UserDTO userdto=cs.getUserDTOFromJson(result);							
							cs.saveUserDTOToDisk(userdto);
							openidcheckpassed=true;
							StartMainActivity();
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
	
	private Request.GraphUserCallback facebookusercallback = new FacebookUserCallback();
	private class FacebookUserCallback implements Request.GraphUserCallback
	{		
		
		@Override
		public void onCompleted(GraphUser u, Response response) {
			// TODO Auto-generated method stub
			
            if (u != null) 
            {
            	
	        	if (!openidcheckpassed)
	        	{
	        			tempuser=new UserDTO();
	        			//tempuser.setCountry(u.getLocation().toString());
	        			if (u.getLocation()!=null)
	        			{
	        				System.out.println(u.getLocation());
	        			}
						tempuser.setNickname(u.getName());				
						tempuser.setId(u.getId());
						tempuser.setEmail(u.asMap().get("email").toString());				
	        	}        	
          	  
	        }
            StartSignupActivity(Constants.OPENID_PROVIDER_FACEBOOK);			
		}
		
	}


	
	public void LoginWithTwitter(View view)
	{
		openid_url=null;
		new TwitterAuthenticateTask().execute();
	}
	
	
    private void TwitterAuthCallback() {
        Uri uri = getIntent().getData();
        if (uri != null)
        if (uri.toString().startsWith(Constants.TWITTER_CALLBACK_URL)) {
            String verifier = uri.getQueryParameter(Constants.URL_PARAMETER_TWITTER_OAUTH_VERIFIER);
            new TwitterGetAccessTokenTask().execute(verifier);
        } else
            new TwitterGetAccessTokenTask().execute("");
    }   
	  
	    class TwitterGetAccessTokenTask extends AsyncTask<String, String, AccessToken> {

	        @Override
	        protected AccessToken doInBackground(String... params) {

	            twitter = TwitterUtil.getInstance().getTwitter();
	            RequestToken requestToken = TwitterUtil.getInstance().getRequestToken();
	            if (params[0]!=null) {
	            		AccessToken accessToken;
						try {
							accessToken = twitter.getOAuthAccessToken(requestToken, params[0]);	
							openid_url=accessToken.getToken();
							
							
							checkTwitterOpenid(openid_url, accessToken);	
							
							return accessToken;
							
						} catch (TwitterException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}                
	                    
	                }

	            return null;  //To change body of implemented methods use File | Settings | File Templates.
	        }
	        
	        
	        @Override
	        protected void onPostExecute(AccessToken accessToken) {	        }
	        
	    }
	  
	    
	private void checkTwitterOpenid(String openid_url, final AccessToken accessToken)
	{
		
		String url =Constants.URL_HOST+Constants.URL_OPENIDOAUTH;		
		final Map<String, String> params = new HashMap<String, String>();  
		params.put("openid_url",openid_url);	 
				
		StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, url, params, new Listener<String>(){

			@Override
			public void onResponse(String result) {
				// TODO Auto-generated method stub
				
				if (result!=null)
		    	{
						if (result.equals("false"))
						{
							openidcheckpassed=false;
		
								new getTwitterUserSignupInfo().execute(accessToken);							
						}
						else
						{
							UserDTO userdto=cs.getUserDTOFromJson(result);							
							cs.saveUserDTOToDisk(userdto);
							openidcheckpassed=true;
							StartMainActivity();
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
	    
	
	class getTwitterUserSignupInfo extends AsyncTask<AccessToken, Void, Void> {
		 
	    @Override
	    protected Void doInBackground(AccessToken... params) {
	    	

			AccessToken accessToken =params[0];
			
			try {
				tuser=twitter.showUser(accessToken .getUserId());
				tempuser=new UserDTO();
				tempuser.setCountry(tuser.getLocation());
				tempuser.setLanguage(tuser.getLocation());
				tempuser.setNickname(tuser.getName());						 
				tempuser.setId(Long.toString(tuser.getId()));
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return null;	        
	    }
	    
	    @Override
	    protected void onPostExecute(Void v) {
	    	
	    	StartSignupActivity(Constants.OPENID_PROVIDER_TWITTER);
	    	
	    }
	    
	    
	}
		
	public void startLogin(View view)
	{
		openPopupwin();
	}
	
	public void StartSignup(View view)
	{
		StartSignupActivity(null);
	}
	
	public void StartSignupActivity(String provider)
	{		
		Intent intent = new Intent(Activity_Welcome.this, Activity_Register.class);
		if (tempuser!=null)
		intent.putExtra("userdto", tempuser);
		
		if(provider!=null)
		{
			intent.putExtra("openid_provider", provider);
			intent.putExtra("openid_url", openid_url);
		}
		
		startActivityForResult(intent,0);
		Activity_Welcome.this.finish();		
		
	}
	
	public void StartMainActivity()
	{		
		
		Intent intent = new Intent(Activity_Welcome.this, Activity_Main.class);
		startActivityForResult(intent,0);
		Activity_Welcome.this.finish();		
	}
	
	@Override
	protected void onDestroy() {
		
		popUp.dismiss();
		super.onDestroy();
	}
	
	
	private void openPopupwin() {
	
		View layout =getLayoutInflater().inflate(R.layout.popup_login, null);
		
		popUp = new PopupWindow(layout, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
		
		mUserNameEditText =(EditText)layout.findViewById(id.login_user_edit);
		mPasswdEditText=(EditText)layout.findViewById(id.login_passwd_edit);
		mRememberPwdCheckBox=(CheckBox)layout.findViewById(id.login_remember_pwd);
		mAutoLoginCheckBox=(CheckBox)layout.findViewById(id.login_auto_login);
		
		popUp.setTouchable(true);
		popUp.setOutsideTouchable(true);
		popUp.showAtLocation(layout, Gravity.CENTER, 0,0); 
	        
        // layout.setBackgroundColor(Color.TRANSPARENT);		
	}
	
	public void onLoginClick(View tager)
	{
		
		String url =Constants.URL_HOST+Constants.URL_CHECKUSER;		
		final Map<String, String> params = new HashMap<String, String>();  
		params.put("email", mUserNameEditText.getText().toString());  
		params.put("password", mPasswdEditText.getText().toString()); 
		
		StringRequest stringRequest = new StringRequest(Method.POST, url, params, new Listener<String>(){

			@Override
			public void onResponse(String response) {
				// TODO Auto-generated method stub
				
				if (response!=null)
		    	{
		    		JSONObject ja;
					try {				
						
						ja=new  JSONObject(response);
						String result=ja.getString("pass");						
						String userdtostring=ja.getString("UserDTO");
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
			
		    @Override  
		    protected Map<String, String> getParams(){		         
		       
		    	return params;
		    }  
		};  
		
		cs.addStringRequestToQueue(stringRequest,this);		
		
	}
	
	public void onForgetPasswdClick(View view)
	{
		
		
	}	
	
	
	class TwitterAuthenticateTask extends AsyncTask<String, String, RequestToken> {
		 
	    @Override
	    protected RequestToken doInBackground(String... params) {
	        return TwitterUtil.getInstance().getRequestToken();
	    }
	    
	    @Override
	    protected void onPostExecute(RequestToken requestToken) {
	    	
           Intent intent = new Intent(getApplicationContext(), Activity_TwitterOAuth.class);
            intent.putExtra(Constants.STRING_EXTRA_AUTHENCATION_URL,requestToken.getAuthenticationURL());
            startActivity(intent);
	    }
	    
	    
	}

	

	
}
