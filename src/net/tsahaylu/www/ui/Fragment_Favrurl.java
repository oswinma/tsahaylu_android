package net.tsahaylu.www.ui;

import java.util.ArrayList;

import org.json.JSONObject;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.Request;
import com.android.volley.VolleyError;

import net.tsahaylu.www.R;
import net.tsahaylu.www.adapter.FavurlShowAdapter;
import net.tsahaylu.www.common.Constants;
import net.tsahaylu.www.dto.FavURLShow;
import net.tsahaylu.www.service.CoreService;
import net.tsahaylu.www.ui.Activity_FavurlDetail;
import net.tsahaylu.www.util.StringRequest;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class Fragment_Favrurl extends Fragment implements OnRefreshListener,OnItemClickListener,OnScrollListener {
	
		private ListView listView;
		private String startCursor=null;
		CoreService cs=CoreService.getCoreService();
	  	private SwipeRefreshLayout swipeLayout;	
	  	private ArrayList<FavURLShow> fuslist;
	  	private FavurlShowAdapter favurlshowadapter;
	  	private boolean isLoading=false;
	  	private boolean enableLoading=true;
	  	private boolean isRefreshing=false;
	  	private View viewcache;
	  	private static Handler handler=new Handler();
	  	private boolean firstloading=false;
	  	private String url=null;
	  	private String comingurl = null;
	  	private String disktag=null;
	  	private String startCursortag=null;
		//private  boolean loadnew=false;
	  		  	
	  	
	  	public Fragment_Favrurl(String url,String disktag)
	  	{
	  		this.url=url;
	  		this.disktag=disktag;
	  		this.startCursortag=disktag+"startcursor";	  		
	  		comingurl = Constants.URL_HOST+"/favurl/"+disktag+"coming";
	  		
	  	}	 	

	  	  	
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        	
	        if(viewcache==null){  
	        	viewcache =inflater.inflate(R.layout.fragment_favurl, null);
	        }  
  
	        ViewGroup parent = (ViewGroup) viewcache.getParent();  
	        if (parent != null) {  
	            parent.removeView(viewcache);  
	        }
	        
	        handler.post((new Runnable() {
	    			public void run() {
	    				
	    				initView(viewcache);	        
	        
	    			}
	    		}));
			  
	        return  viewcache;            
        }

		public void initView(final View view)
		{
		 	listView = (ListView) view.findViewById(R.id.list_view);    
            listView.setOnItemClickListener(this);
            listView.setOnScrollListener(this);
       	
            listView.setAdapter(favurlshowadapter);            
            
            swipeLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_container);            
            swipeLayout.setOnRefreshListener(this);
        	swipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,android.R.color.holo_green_light, android.R.color.holo_orange_light,android.R.color.holo_red_light);	        	
	    }
		
		
	    @Override  
	    public void onCreate(Bundle savedInstanceState) {  
	        super.onCreate(savedInstanceState);  
			fuslist=new ArrayList<FavURLShow>();   		        
            favurlshowadapter=new  FavurlShowAdapter(getActivity(), fuslist, R.layout.favurl_show); 
            startCursor=cs.getstartCursor(startCursortag);
            System.out.println("oncreateevent "+startCursortag+startCursor);            
	    }  
		
	    @Override  
	    public void onResume() {        
				        
	        handler.post((new Runnable() {
	    			public void run() {	    				
    		            
	    				if (fuslist.size()==0)
	    				{
	    		            ArrayList<FavURLShow> list=cs.getFavurlListFromDisk(disktag);
	    		           	    		            
	    		            if (list!=null)
	    		            {
	    		            	
	    		            	fuslist.addAll(list);            		    		            	
	    		            }
	    		            
	    		            System.out.println("onstartevent "+disktag+": "+fuslist.size());
	    		            
	    					if (fuslist.size()==0)
	    					{
	    						getMoreList();
	    					}	    					
	    					else
	    					{
	    						favurlshowadapter.notifyDataSetChanged();
	    						getComingList();
	    					}     	              						

	    				}
	    				else
	    				{
	    					getComingList();
	    				}
	        
	    				//System.out.println("onstartevent "+startCursortag+": "+startCursor);
	    			}
	    		}));
	        			
	        super.onResume();  
	        
			//System.out.println("onstart");
	    }  
	    
	    
        @Override  
        public void onStop() {  
 				
            cs.putFavurlshowlistToDisk(disktag, fuslist);
            if (startCursor!=null)
            cs.putstartCursor(startCursortag, startCursor);
            System.out.println("onstop "+startCursortag+": "+startCursor);    				
            super.onStop();
        }  
        
		@Override
		public void onItemClick(AdapterView<?> adapterview, View view, int position, long arg3) {
			// TODO Auto-generated method stub

			FavURLShow favurlshow= (FavURLShow) adapterview.getItemAtPosition(position);
			startFavurlDetailActivity(fuslist,favurlshow,position);
			
		}       
        
    	public void startFavurlDetailActivity(ArrayList<FavURLShow> fuslist, FavURLShow favurlshow,int position)
    	{
    			Intent intent = new Intent(getActivity().getApplicationContext(), Activity_FavurlDetail.class);			
    			intent.putExtra("FavURLShow", favurlshow);    			
				intent.putExtra("position", position);
				intent.putExtra("fuslist", fuslist);
    			startActivityForResult(intent,0);    			
    	}
    	                            
        public void getComingList()
        {   		
        	if (fuslist.get(0)!=null)
        	{
        		String tmpurl=null;
        	String favurlid=fuslist.get(0).getId().toString();
        	if (comingurl!=null&&favurlid!="")
        	{
        		tmpurl=comingurl+"?favurlid="+favurlid;
        	
        	StringRequest stringRequest = new StringRequest(Request.Method.GET, tmpurl,null,
        	
        		new Listener<String>(){
        		
    			@Override
    			public void onResponse(final String json) {
    				// TODO Auto-generated method stub
    				
    				if (json!=null)
    		    	{
    					
    			        handler.post((new Runnable() {
    		    			public void run() {
    		    				
    	    					JSONObject jo=cs.convertJsonToJSONObject(json);
    	    		        	ArrayList<FavURLShow> newfuslist=cs.getFavURLShowsFromJSONObject(jo);
    	    		        	//startCursor=cs.getStartCursorFromJSONObject(jo);
    	    		        	fuslist.addAll(0,newfuslist);
    	    		       		favurlshowadapter.notifyDataSetChanged();
    	    		       		//cs.putFavurlshowlistToDisk("new", fuslist);       
    		        
    		    			}
    		    		}));

    				 }
    				
    				resetswipeLayout();
    			}},
    			new ErrorListener(){

    			@Override
    			public void onErrorResponse(VolleyError error) {
    				// TODO Auto-generated method stub
    				
    				
    				resetswipeLayout();
    			}}){  
    			
    		};	
    		
    		cs.addStringRequestToQueue(stringRequest);   	
        	}
        	}
        	 		
        }
                
        public void resetswipeLayout()
        {
			swipeLayout.setRefreshing(false);
			swipeLayout.setEnabled(true);
			isRefreshing=false;
        }
        
        public void getMoreList()
        {   	
	        isLoading = true;       
        	String tmpurl=url;
        	
			if (startCursor!=null&&startCursor!="")
        		tmpurl=url+"?startCursor="+startCursor;
        	
        	StringRequest stringRequest = new StringRequest(Request.Method.GET, tmpurl,null,
        		
        		new Listener<String>(){
        		
    			@Override
    			public void onResponse(final String json) {
    				// TODO Auto-generated method stub
    				
    				if (json!=null)
    		    	{    					
    			        handler.post((new Runnable() {
    		    			public void run() {
    	    					JSONObject jo=cs.convertJsonToJSONObject(json);
    	    		        	ArrayList<FavURLShow> newfuslist=cs.getFavURLShowsFromJSONObject(jo);
    	    		        	startCursor=cs.getStartCursorFromJSONObject(jo);   	    		        	
    	    		        	
    	    		        	//System.out.println("startCursor:"+startCursor);
    	    		        	if (newfuslist.size()>0)
    	    		        	{
    	        		       		fuslist.addAll(newfuslist);
    	        		       		favurlshowadapter.notifyDataSetChanged();
    	        		       		//cs.putFavurlshowlistToDisk(disktag, fuslist);
    	    		        	}
    	    		        	else
    	    		        	{
    	    		        		enableLoading=false;
    	    		        	}
    		        
    		    			}
    		    		}));   			        
    		        	
    		       		isLoading = false;
    				 }
    				if (firstloading)
    				{
    					firstloading=false;
    					resetswipeLayout();
    				}
    				
    			}},
    			new ErrorListener(){

    			@Override
    			public void onErrorResponse(VolleyError error) {
    				// TODO Auto-generated method stub    				
    				
    				if (firstloading)
    				{
    					firstloading=false;
    					resetswipeLayout();
    				}
    				
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

	    				if (fuslist.size()>0)
	    					//if (loadnew)
	    					getComingList();
	    				else
	    				{
	    					getMoreList();
	    					firstloading = true;
	    				}
	    					
	    			}
	    		});
			}
			
		}


		@Override
		public void onScroll(AbsListView view,int firstVisibleItem, int visibleItemCount, int totalItemCount) 
		{
			if (enableLoading)
			{
			  if (favurlshowadapter == null)
			        return ;
			    
			    if (favurlshowadapter.getCount() == 0)
			        return ;
			    
			    int l = visibleItemCount + firstVisibleItem;
			    if (l >= totalItemCount && !isLoading) {
			        // It is time to add new data. We call the listener
			        
			        handler.post((new Runnable() {
		    			public void run() {

		    				getMoreList();
					        
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
