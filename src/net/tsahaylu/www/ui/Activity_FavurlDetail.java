package net.tsahaylu.www.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;

import net.tsahaylu.www.R;
import net.tsahaylu.www.adapter.CommentAdapter;
import net.tsahaylu.www.common.Constants;
import net.tsahaylu.www.dto.CommentDTO;
import net.tsahaylu.www.dto.FavURLShow;
import net.tsahaylu.www.dto.UserDTO;
import net.tsahaylu.www.service.CoreService;
import net.tsahaylu.www.util.CommonUtils;
import net.tsahaylu.www.util.DateUtils;
import net.tsahaylu.www.util.StringRequest;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Activity_FavurlDetail extends Activity implements OnRefreshListener{
	
	CoreService cs=CoreService.getCoreService();
	private SwipeRefreshLayout swipeLayout;
	private ListView listView;
	private CommentAdapter commentadapter;
	private String startCursor="";
	private ArrayList<CommentDTO> clist; 
	private TextView urlv ;
	private TextView sendtimev ;
	private TextView titlev;
	private ImageView iconv ;
	private TextView nicknamev ;
	private ImageView avatarURLv;
	private FavURLShow favurlshow;
	private int position;
	private TextView sharev ;
	private TextView favv;
	private EditText inputv ;
  	private boolean isRefreshing=false;
  	private static Handler handler=new Handler();
  	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favurl_detail);
		 
		
		handler.post(new Runnable() {
			public void run() {
				initView();
				getFavURLShowInfo();
				getComments(favurlshow.getUrl());   				
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
	
    protected void getFavURLShowInfo()  
    {      	
    	Bundle data = getIntent().getExtras(); 
    	favurlshow = (FavURLShow) data.getSerializable("FavURLShow");
    	position = data.getInt("position");
    	
	        	 if (favurlshow!=null)
	        	 {

	       		  String avatarURL=favurlshow.getAvatarURL();
	    		  if (avatarURL!=null && avatarURL!="")
	    		  cs.setImageView(avatarURLv, avatarURL);
	    		  else
	    			  avatarURLv.setImageResource(R.drawable.default_avatar);
	    		  
	    		  String icon=favurlshow.getIcon();
	    		  if (icon!=null && icon!="")
	    			  	cs.setImageView(iconv, icon);
	    			  else
	    				iconv.setImageResource(R.drawable.defaulticon);

	    		  CommonUtils.stripUnderlines(urlv);				
	    		  urlv.setText(favurlshow.getUrl());
	    		  
	    		  Date sendtime=favurlshow.getSendtime();	    		  
	    		  sendtimev.setText(DateUtils.formatSendTime(sendtime));
	    		  
	    		  titlev.setText(favurlshow.getTitle());
	    		  nicknamev.setText(favurlshow.getNickname());	    		  
	    		  sharev.setText(favurlshow.getShare().toString());
	    		  favv.setText(favurlshow.getFavs().toString());	    		  
	        	 }
	}

	public void initView()
	{	
		
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        
		   urlv = (TextView) findViewById(R.id.url);
		   sendtimev = (TextView)findViewById(R.id.sendtime);
		   titlev = (TextView) findViewById(R.id.title);
		   iconv = (ImageView) findViewById(R.id.icon);
		   nicknamev = (TextView) findViewById(R.id.nickname);
		   avatarURLv = (ImageView) findViewById(R.id.avatarURL);
		   sharev = (TextView) findViewById(R.id.share);
		   favv = (TextView) findViewById(R.id.fav);
		   inputv = (EditText) findViewById(R.id.commentinput);
		   		   
		   
		listView = (ListView) findViewById(R.id.list_view);    
        
        clist = new ArrayList<CommentDTO>();
        
/*        ArrayList<FavURLShow> list=cs.getFavurlListFromDisk("new");
        if (list!=null)
        {
        	fuslist.addAll(list);
        }*/
        
    	commentadapter = new  CommentAdapter(this, clist, R.layout.favurl_comment);            	
        listView.setAdapter(commentadapter);            
        
        swipeLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_container);            
        swipeLayout.setOnRefreshListener(this);
    	swipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,android.R.color.holo_green_light, android.R.color.holo_orange_light,android.R.color.holo_red_light);
    	
    	 
	}
	
	public void getComments(String url)
	{
    	String requesturl = Constants.URL_HOST+Constants.URL_COMMENT+"?url="+url;
    	
    	if (startCursor!=null&&startCursor!="")
    		requesturl=requesturl+"&startCursor="+startCursor;
    	
		StringRequest stringRequest = new StringRequest(Request.Method.GET, requesturl, null, new Listener<String>(){

			@Override
			public void onResponse(String json) {
				// TODO Auto-generated method stub
				
				if (json!=null&&!json.equals("null"))
		    	{
					JSONObject jo=cs.convertJsonToJSONObject(json);			
					
			        	ArrayList<CommentDTO> newlist=cs.getCommentDTOFromJSONObject(jo);
			        	startCursor=cs.getStartCursorFromJSONObject(jo);
			        	if (newlist.size()>0)
			        	{
			        		clist.addAll(newlist);
			        		commentadapter.notifyDataSetChanged();
			        		//cs.putCommentDTOToDisk("new", clist);
			        		
			        	}
				 }
				
				resetswipeLayout();
				
			}},new ErrorListener(){

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				
				resetswipeLayout();
			}}){  
			
		};	
		
		cs.addStringRequestToQueue(stringRequest);
		
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		
		
		if (!isRefreshing)
		{
			isRefreshing=true;
			swipeLayout.setEnabled(false);
		swipeLayout.setRefreshing(true);
		handler.post(new Runnable() {
			public void run() {
				getComments(favurlshow.getUrl());    				
			}
		});
		
		}
	}
	
    public void resetswipeLayout()
    {
		swipeLayout.setRefreshing(false);
		swipeLayout.setEnabled(true);
		isRefreshing=false;
    }
    
	
	public void CommentSubmit(View view)
	{
		String content=inputv.getText().toString();
		
		if (content!=null && !content.equals(""))
		{
			
		inputv.setText("");
		String url =Constants.URL_HOST+Constants.URL_COMMENT_ADD;		
		Map<String, String> params = new HashMap<String, String>();  
		params.put("toid", favurlshow.getFromid().toString());
		params.put("favurlid", favurlshow.getId().toString());
		params.put("content", content);
		params.put("url", favurlshow.getUrl());
		params.put("urltitle", favurlshow.getTitle());
		String iconurl=favurlshow.getIcon();
		if (iconurl==null)
			iconurl="";
		params.put("urlicon", iconurl);
		params.put("sendtime",DateUtils.getCurrenTimeString());
		
		StringRequest stringRequest = new StringRequest(Method.POST, url, params, new Listener<String>(){

			@Override
			public void onResponse(String json) {
				// TODO Auto-generated method stub
				
				if (json!=null)
		    	{
					JSONObject jo=cs.convertJsonToJSONObject(json);
		        	
		    		try {
		    		String	result = jo.getString("result");
		    		
		    		if (result.equals("true"))
		    		{
		    			ArrayList<CommentDTO> newlist=cs.getCommentDTOFromJSONObject(jo);
			    		if (newlist.size()>0)
			        	{
			        		clist.addAll(0,newlist);
			        		commentadapter.notifyDataSetChanged();
			        		favurlshow.setCommentnum(favurlshow.getCommentnum()+1);
			        		cs.UpdateDiskFavURLShow(favurlshow,position);
			        		inputv.clearFocus();
			        	}
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
	}
	
	public void ToggleArchive(View view)
	{
	
		cs.ToggleArchive(favurlshow.getId().toString());
	}
	
	
	public void ToggleFav(View view)
	{
		cs.ToggleFav(favurlshow.getId().toString());	
	}
	
	public void ToggleDelete(View view)
	{
		
		cs.ToggleDelete(favurlshow.getId().toString());		
	}
	
	
	public void ToggleReply(View view)
	{
		
	}
	
	public void ToggleShare(View view)
	{
	
		Intent intentTwo = new Intent(Intent.ACTION_SEND);  
        intentTwo.setType("text/plain");  
        intentTwo.putExtra(Intent.EXTRA_SUBJECT,favurlshow.getTitle() );  
        intentTwo.putExtra(Intent.EXTRA_TEXT, favurlshow.getUrl());  
        intentTwo.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
        startActivity(Intent.createChooser(intentTwo, "Share"));        
	}
	
	
	public void StartShareBy(View view)
	{
		Intent intent = new Intent(this, Activity_ShareByUser.class);			
		intent.putExtra("urlid", favurlshow.getUrlid().toString());   			
		startActivityForResult(intent,0);   
	}
	
	public void SendLink(View view)
	{
		
	}
		
		
}
