package net.tsahaylu.www.ui;

import java.util.ArrayList;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;

import net.tsahaylu.www.R;
import net.tsahaylu.www.adapter.UserDTOAdapter;
import net.tsahaylu.www.common.Constants;
import net.tsahaylu.www.dto.UserDTO;
import net.tsahaylu.www.service.CoreService;
import net.tsahaylu.www.util.StringRequest;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class Fragment_UserFriend extends Fragment {

	private ListView friendlist;
	
  	private ArrayList<UserDTO> clist;
  	private UserDTOAdapter adapter;
  	private String userid=null;  
	CoreService cs=CoreService.getCoreService();
	private View viewcache;
  	private static Handler handler=new Handler();
  	
	  @Override
	  public void onAttach(Activity activity) {
	    super.onAttach(activity);

	    Activity_UserProfile mainActivity = (Activity_UserProfile)activity;
	    userid=mainActivity.getUserID();
	  }

	  @Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	        if(viewcache==null){  
	        	viewcache = inflater.inflate(R.layout.userfriend, null);
	        	
	        }  
  
	        ViewGroup parent = (ViewGroup) viewcache.getParent();  
	        if (parent != null) {  
	            parent.removeView(viewcache);  
	        }	        
	        
	        handler.post((new Runnable() {
	    			public void run() {
	    				
	    		        initView(viewcache); 
	    		        getFriendList();
	    			}
	    		}));    
     	
	        	      
	      return  viewcache;
	  }
	  
	  
		public void initView( View view)
		{ 	
			   clist=new ArrayList<UserDTO>(); 
               friendlist = (ListView) view.findViewById(R.id.friend_list);
               adapter=new UserDTOAdapter(getActivity(), clist, R.layout.user);
	           friendlist.setAdapter(adapter);
		}
		
	  
	  @Override
	  public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState); 
           
	  }
	  
	  private  void getFriendList() {
			// TODO Auto-generated method stub
			
		String requesturl = Constants.URL_HOST+Constants.URL_FRIEND_USERFRIEND;
	    	
	 	if (userid!=null&&userid!="")
	 		requesturl=requesturl+"?fromid="+userid;
		
	 	StringRequest stringRequest = new StringRequest(Request.Method.GET, requesturl, null, new Listener<String>(){

			@Override
			public void onResponse(String json) {
				// TODO Auto-generated method stub
				
				if (json!=null)
		    	{
					clist.addAll(cs.getUserDTOListFromJson(json));					
	        		adapter.notifyDataSetChanged();				
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
