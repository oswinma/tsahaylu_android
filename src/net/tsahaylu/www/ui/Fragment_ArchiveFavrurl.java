package net.tsahaylu.www.ui;

import java.util.ArrayList;

import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;

import net.tsahaylu.www.R;
import net.tsahaylu.www.adapter.FavurlShowAdapter;
import net.tsahaylu.www.common.Constants;
import net.tsahaylu.www.dto.FavURLShow;
import net.tsahaylu.www.service.CoreService;
import net.tsahaylu.www.util.StringRequest;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SlidingPaneLayout.PanelSlideListener;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class Fragment_ArchiveFavrurl extends Fragment implements OnItemClickListener, PanelSlideListener{

	 private SlidingPaneLayout mSlidingPaneLayout; 

		private ListView listView;
		private String startCursor;
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
	  	
	 
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                
        	
	        if(viewcache==null){  
	        	viewcache =inflater.inflate(R.layout.favurl_list, null);
	        }  
  
	        ViewGroup parent = (ViewGroup) viewcache.getParent();  
	        if (parent != null) {  
	            parent.removeView(viewcache);  
	        }

	        init(viewcache);
	        
	        return  viewcache;     
              
        }        
        
        
        private void init(View view)
        {
			startCursor=null;
		 	listView = (ListView) view.findViewById(R.id.list_view);    
            listView.setOnItemClickListener(this);
            
			fuslist=new ArrayList<FavURLShow>();   	
            ArrayList<FavURLShow> list=cs.getFavurlListFromDisk("new");
            if (list!=null)
            {
            	fuslist.addAll(list);
            }
            
        	favurlshowadapter=new  FavurlShowAdapter(getActivity(), fuslist, R.layout.favurl_show);            	
            listView.setAdapter(favurlshowadapter);          
        	getNewMoreList();
        	
        	
        	  mSlidingPaneLayout = (SlidingPaneLayout) view.findViewById(R.id.sliding_layout);  
        	  mSlidingPaneLayout.setPanelSlideListener(this);  
        }
        
        
        public void getNewMoreList()
        {   	
	        isLoading = true;
        	String url = Constants.URL_HOST+Constants.URL_NEW;
        	if (startCursor!=null&&startCursor!="")
        		url=url+"?startCursor="+startCursor;
        	
        	StringRequest stringRequest = new StringRequest(Request.Method.GET, url,null,
        		
        		new Listener<String>(){
        		
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
    		        	
    		       		isLoading = false;
    				 }
    				if (firstloading)
    				{
    					firstloading=false;
    					//resetswipeLayout();
    				}
    				
    			}},
    			new ErrorListener(){

    			@Override
    			public void onErrorResponse(VolleyError error) {
    				// TODO Auto-generated method stub    				
    				
    				if (firstloading)
    				{
    					firstloading=false;
    					//resetswipeLayout();
    				}
    				
    			}}){  
    			
    		};	
    		
    		cs.addStringRequestToQueue(stringRequest);
        }


		@Override
		public void onItemClick(AdapterView<?> adapterview, View view, int position, long arg3) {
			// TODO Auto-generated method stub
			
			
		}


		@Override
		public void onPanelClosed(View arg0) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void onPanelOpened(View arg0) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void onPanelSlide(View arg0, float arg1) {
			// TODO Auto-generated method stub
			
		}        
        
}
