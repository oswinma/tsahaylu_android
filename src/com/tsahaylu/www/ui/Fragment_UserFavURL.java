package com.tsahaylu.www.ui;

import java.util.ArrayList;

import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.tsahaylu.www.R;
import com.tsahaylu.www.adapter.FavurlShowAdapter;
import com.tsahaylu.www.common.Constants;
import com.tsahaylu.www.dto.FavURLShow;
import com.tsahaylu.www.service.CoreService;
import com.tsahaylu.www.util.StringRequest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class Fragment_UserFavURL extends Fragment implements OnScrollListener, OnItemClickListener {

  	private ArrayList<FavURLShow> fuslist;
  	private FavurlShowAdapter favurlshowadapter;
  	
	private ListView listView;
	private String startCursor;

	CoreService cs=CoreService.getCoreService();
	private View viewcache;
  	private boolean isLoading=false;
  	private boolean enableLoading=true;
  	private static Handler handler=new Handler();
  	
	  @Override
	  public void onAttach(Activity activity) {
	    super.onAttach(activity);

	    Activity_UserProfile mainActivity = (Activity_UserProfile)activity;

	  }

	  @Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	        if(viewcache==null){  
	        	viewcache =inflater.inflate(R.layout.userfavurl, null);
	        }  
  
	        ViewGroup parent = (ViewGroup) viewcache.getParent();  
	        if (parent != null) {  
	            parent.removeView(viewcache);  
	        }
	        handler.post((new Runnable() {
	    			public void run() {
	    				
	    		      	initView(viewcache);
	    		      	geMyFavURLList();	    		      	       
	        
	    			}
	    		}));    

      	return  viewcache;
	  }
	  
	  	  
		public void initView(View view)
		{
			  startCursor=null;
	          listView = (ListView) view.findViewById(R.id.favurl_list);	          
	          listView.setOnItemClickListener(this);
	          fuslist=new ArrayList<FavURLShow>();
	          favurlshowadapter=new  FavurlShowAdapter(getActivity(), fuslist, R.layout.favurl_show); 
	          listView.setAdapter(favurlshowadapter);  
	          listView.setOnScrollListener(this);
	 	}
		
		@Override
		public void onItemClick(AdapterView<?> adapterview, View view, int position, long arg3) {
			// TODO Auto-generated method stub

			FavURLShow favurlshow= (FavURLShow) adapterview.getItemAtPosition(position);
			startFavurlDetailActivity(favurlshow);		
		}  
			
		private  void geMyFavURLList() {
			// TODO Auto-generated method stub
			
		String requesturl = Constants.URL_HOST+Constants.URL_FAVURL_MY;
	    	
	 	if (startCursor!=null&&startCursor!="")
	 		requesturl=requesturl+"?startCursor="+startCursor;
		
	 	StringRequest stringRequest = new StringRequest(Request.Method.GET, requesturl, null, new Listener<String>(){

			@Override
			public void onResponse(String json) {
				// TODO Auto-generated method stub
				
				if (json!=null)
		    	{
					JSONObject jo=cs.convertJsonToJSONObject(json);
		        	ArrayList<FavURLShow> newfuslist=cs.getFavURLShowsFromJSONObject(jo);
		        	startCursor=cs.getStartCursorFromJSONObject(jo);
		        	if (newfuslist.size()>0)
		        	{
		        		fuslist.addAll(newfuslist);
		        		favurlshowadapter.notifyDataSetChanged();
		        	}
		        	else
		        	{
		        		enableLoading=false;
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
			        isLoading = true;
			        
			        handler.post((new Runnable() {
		    			public void run() {

					        geMyFavURLList();
					        
		    			}
		    		}));		        

			    }
			    
			}
		}


		@Override
		public void onScrollStateChanged(AbsListView arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}
		
		public void startFavurlDetailActivity(FavURLShow favurlshow)
		{
				Intent intent = new Intent(getActivity(), Activity_FavurlDetail.class);			
				intent.putExtra("FavURLShow", favurlshow);   			
				startActivityForResult(intent,0);    			
		}
}
