package net.tsahaylu.www.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.Volley;

import de.hdodenhof.circleimageview.CircleImageView;
import net.tsahaylu.www.R;
import net.tsahaylu.www.common.Constants;
import net.tsahaylu.www.dto.Contact;
import net.tsahaylu.www.dto.GroupDTO;
import net.tsahaylu.www.dto.UserDTO;
import net.tsahaylu.www.service.CoreService;
import net.tsahaylu.www.util.StringRequest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridLayout;
import android.widget.TextView;

public class Activity_FriendsSelector extends Activity implements OnClickListener {
	
	private GridLayout group_grid;	
	private GridLayout friend_grid;
	private TextView tolist;
	private static RequestQueue mQueue;
	private CoreService cs=CoreService.getCoreService();
	private LayoutInflater inflater;  
  	private static Handler handler=new Handler();
  	private UserDTO userdto;
  	private HashMap<String,String> grouplist;
  	private HashMap<String,String> friendlist;
  	
  	private boolean tome=false;
  	private boolean toall=true;
  	
  	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friends_selector);
		
		handler.post(new Runnable() {
			public void run() {
				
				ActivityControl();
			}
		});	
	}
	
	
    public void ActivityControl()
    {
    	userdto=cs.getUserDTOFromDisk(this);
    	if (userdto==null)
    	{
    		startWelcomeActivity();
    	}
    	else
    	{
    		String userid=userdto.getId();
			initView();
			getGroupGridRequest();
			getFriendGrid(userid);
    	}
    }
    
	public void startWelcomeActivity()
	{
			Intent intent = new Intent(this, Activity_Welcome.class);
			startActivityForResult(intent,0);		   
	}
	
	public void initView()
	{
		mQueue = Volley.newRequestQueue(this);
		grouplist=new HashMap<String,String>();
		friendlist=new HashMap<String,String>();
		
		inflater=(LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		group_grid=(GridLayout) this.findViewById(R.id.group_grid);		
		friend_grid=(GridLayout) this.findViewById(R.id.friend_grid);
		tolist=(TextView) this.findViewById(R.id.tolist);
		
	}
	
	public void getGroupGridRequest()
	{
	  	String requesturl = Constants.URL_HOST+Constants.URL_GROUP_INFO;

    	StringRequest stringRequest = new StringRequest(Request.Method.GET, requesturl, null, new Listener<String>(){

			@Override
			public void onResponse(String json) {
				// TODO Auto-generated method stub
				
				if (json!=null)
		    	{
					JSONObject jo=cs.convertJsonToJSONObject(json);
		        	ArrayList<GroupDTO> newlist=cs.getGROUPDTOFromJSONObject(jo);

		        	if (newlist.size()>0)
		        	{
		        		initGroupGridData(newlist);
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
	
	
	public void initGroupGridData(ArrayList<GroupDTO> list)
	{
		
		
		View allv = inflater.inflate(R.layout.group_cricle, null); 			
	    TextView all_name = (TextView) allv.findViewById(R.id.group_name);
	    CircleImageView all_avatar = (CircleImageView) allv.findViewById(R.id.group_avatar);  
		all_name.setText("ALL");
		all_avatar.setOnClickListener(new toallClickListener());
		group_grid.addView(allv);  	
		
		
		View mev = inflater.inflate(R.layout.group_cricle, null); 			
	    CircleImageView me_avatar = (CircleImageView) mev.findViewById(R.id.group_avatar);  
	    TextView mev_name = (TextView) mev.findViewById(R.id.group_name);  		    
	    
		  String avatarURL=userdto.getAvatarURL();
		  if (avatarURL!=null && avatarURL!="")
		  cs.setImageView(me_avatar, avatarURL);
		  else
			  me_avatar.setImageResource(R.drawable.default_avatar);
		  
	    mev_name.setText("Me");
	    me_avatar.setOnClickListener(new tomeClickListener());
		group_grid.addView(mev);  	

		for (int j = 0; j < list.size(); j++) 
		{  
			GroupDTO group=list.get(j);
			View v = inflater.inflate(R.layout.group_cricle, null); 			
		    CircleImageView group_avatar = (CircleImageView) v.findViewById(R.id.group_avatar);  
		    TextView group_name = (TextView) v.findViewById(R.id.group_name);  		    
			group_name.setText(group.getName());
			group_avatar.setTag(group);
			group_avatar.setOnClickListener(this);
			group_grid.addView(v);  		
		}  
		
	}
	
	public void getFriendGrid(String userid)
	{
		String requesturl = Constants.URL_HOST+Constants.URL_FRIEND_USEID;
    	
	 	if (userid!=null&&userid!="")
	 		requesturl=requesturl+"?fromid="+userid;
		
	 	StringRequest stringRequest = new StringRequest(Request.Method.GET, requesturl, null, new Listener<String>(){

			@Override
			public void onResponse(String json) {
				// TODO Auto-generated method stub
				
				if (json!=null)
		    	{
					
					 ArrayList<Contact>  list=cs.getContactsFromJson(json);
					 initFriendGridData(list);
		    	}			
				
			}},new ErrorListener(){

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				
			}}){  
			
		};	
		
		cs.addStringRequestToQueue(stringRequest);
	}
	
	public void initFriendGridData(ArrayList<Contact> list)
	{
		

		for (int j = 0; j < list.size(); j++) 
		{  
			Contact contact=list.get(j);
			View v = inflater.inflate(R.layout.friend_circle, null); 			
		    CircleImageView friend_avatar = (CircleImageView) v.findViewById(R.id.friend_avatar);  
		    TextView friend_name = (TextView) v.findViewById(R.id.friend_name);  
		    TextView friend_Group_name = (TextView) v.findViewById(R.id.friend_Group_name);  
		    friend_name.setText(contact.getNickname());
		    friend_Group_name.setText(contact.getNickname());
		    friend_avatar.setTag(contact);
		    		    
		    String avatarURL=contact.getAvatarURL();
			if (avatarURL!=null && avatarURL!="")
				  cs.setImageView(friend_avatar, avatarURL);
			  
			friend_avatar.setOnClickListener(new firendClickListener());
			friend_grid.addView(v);  		
		}  
		
	}
	

	public void ConfrimSelected(View view)
	{
		Intent intent = new Intent(this, Activity_SharePage.class);
		intent.putExtra("toall",toall);
		intent.putExtra("toall",tome);
		intent.putExtra("grouplist", grouplist);
		intent.putExtra("friendlist", friendlist);
		intent.putExtra("tolist", tolist.getText());
		
		this.setResult(0,intent);
		finish(); 
	}
	
	class toallClickListener implements OnClickListener {


		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			toall=!toall;
			updateTolist();
		}
		
		
	}
	
	class tomeClickListener implements OnClickListener {


		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			tome=!tome;
			updateTolist();
		}
		
		
	}
	
	class firendClickListener implements OnClickListener {


		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			v.setSelected(!v.isSelected());
			Contact c=(Contact) v.getTag();
			String id=c.getId();
			String name=c.getNickname();
			
			if (friendlist.containsKey(id))
			{
				if (!v.isSelected())
				{
					
					friendlist.remove(id);
				}			

			}
			else
			{
				if (v.isSelected())
				{
					friendlist.put(id, name);
				}
				
			}
			updateTolist();
		}		
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
			v.setSelected(!v.isSelected());
			GroupDTO g=(GroupDTO) v.getTag();
			String id=g.getId().toString();
			String name=g.getName();

			if (grouplist.containsKey(id))
			{
				if (!v.isSelected())
				{
					
					grouplist.remove(id);
				}			

			}
			else
			{
				if (v.isSelected())
				{
					grouplist.put(id, name);
				}
				
			}
		updateTolist();
	}
	
	
	public void updateTolist()
	{
		StringBuffer sb=new StringBuffer();
	
		if (toall)
			sb.append("ALL");
		
		if (tome)
			  if (sb.length()>0)
			    	sb.append(",Me");
			    else
			    	sb.append("Me");
		
		Iterator iter = grouplist.keySet().iterator(); 
		while (iter.hasNext()) { 
		    Object key = iter.next(); 
		    String name = grouplist.get(key);		    
		    if (sb.length()>0)
		    	sb.append(","+name);
		    else
		    	sb.append(name);
		} 
		
		
		Iterator itera = friendlist.keySet().iterator(); 
		while (itera.hasNext()) { 
		    Object key = itera.next(); 
		    String name = friendlist.get(key);
		    
		    if (sb.length()>0)
		    	sb.append(","+name);
		    else
		    	sb.append(name);
		} 
		

		tolist.setText(sb.toString());
	}
	
}
