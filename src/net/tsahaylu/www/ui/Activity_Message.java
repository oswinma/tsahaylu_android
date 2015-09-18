package net.tsahaylu.www.ui;

import java.util.ArrayList;

import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;

import net.tsahaylu.www.R;
import net.tsahaylu.www.adapter.MessageAdapter;
import net.tsahaylu.www.common.Constants;
import net.tsahaylu.www.dto.MessageInfo;
import net.tsahaylu.www.service.CoreService;
import net.tsahaylu.www.util.StringRequest;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

public class Activity_Message extends Activity implements OnRefreshListener,OnScrollListener {
	
	CoreService cs=CoreService.getCoreService();
	private SwipeRefreshLayout swipeLayout;
	private ListView listView;
	private MessageAdapter adapter;
	private String startCursor;
	private ArrayList<MessageInfo> clist;
	private static Handler handler=new Handler();
  	private boolean isLoading=false;
  	private boolean enableLoading=true;
  	private boolean isRefreshing=false;
  	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity__message);
		
		handler.post(new Runnable() {
			public void run() {
		        initView();
		        getMessages();  				
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
			startCursor=null;
	        final ActionBar actionBar = getActionBar();
	        actionBar.setDisplayHomeAsUpEnabled(true);	        
			   
			listView = (ListView) findViewById(R.id.list_view); 	        
	        clist = new ArrayList<MessageInfo>();	        
	        adapter = new  MessageAdapter(this, clist, R.layout.message);            	
	        listView.setAdapter(adapter);            
	        listView.setOnScrollListener(this);
	        
	        swipeLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_container);            
	        swipeLayout.setOnRefreshListener(this);
	    	swipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,android.R.color.holo_green_light, android.R.color.holo_orange_light,android.R.color.holo_red_light);
		}
		
		public void getMessages()
		{
			isLoading = true;
	    	String requesturl = Constants.URL_HOST+Constants.URL_MESSAGE;
	    	
	    	if (startCursor!=null&&startCursor!="")
	    		requesturl=requesturl+"?startCursor="+startCursor;
	    	
			net.tsahaylu.www.util.StringRequest stringRequest = new StringRequest(Request.Method.GET, requesturl, null, new Listener<String>(){

				@Override
				public void onResponse(String json) {
					// TODO Auto-generated method stub
					
					if (json!=null)
			    	{
						JSONObject jo=cs.convertJsonToJSONObject(json);
			        	ArrayList<MessageInfo> newlist=cs.getMessageInfoFromJSONObject(jo);
			        	startCursor=cs.getStartCursorFromJSONObject(jo);
			        	if (newlist.size()>0)
			        	{
			        		clist.addAll(newlist);
			        		adapter.notifyDataSetChanged();			        		
			        	}
			        	else
			        	{
			        		enableLoading=false;
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
				swipeLayout.setRefreshing(true);
				swipeLayout.setEnabled(false);
				handler.post(new Runnable() {
	    			public void run() {

	    				getMessages();  
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

		@Override
		public void onScroll(AbsListView view,int firstVisibleItem, int visibleItemCount, int totalItemCount){
			// TODO Auto-generated method stub
			if (enableLoading)
			{
			  if (adapter == null)
			        return ;
			    
			    if (adapter.getCount() == 0)
			        return ;
			    
			    int l = visibleItemCount + firstVisibleItem;
			    if (l >= totalItemCount && !isLoading) {
			        // It is time to add new data. We call the listener
			        			        
			        handler.post((new Runnable() {
		    			public void run() {

		    				getMessages();
					        
		    			}
		    		}));		
			        
			        
			    }
			    
			}
		}

		@Override
		public void onScrollStateChanged(AbsListView arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}
	
}
