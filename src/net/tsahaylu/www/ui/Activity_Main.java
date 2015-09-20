package net.tsahaylu.www.ui;

import java.util.ArrayList;
import java.util.List;

import net.tsahaylu.www.R;
import net.tsahaylu.www.dto.UserDTO;
import net.tsahaylu.www.service.CoreService;
import net.tsahaylu.www.util.MyApp;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

public class Activity_Main extends FragmentActivity{
	
    private Fragment_NewFavrurl newFragment ;  
    private Fragment_ArchiveFavrurl archiveFragment ;  
    private Fragment_FavFavrurl favFragement;  
    private List<Fragment> fragmentList = new ArrayList<Fragment>();
    private List<String>   titleList    = new ArrayList<String>();
    
    private ViewPager mViewPager;  
    private ViewPagerAdapter mViewPagerAdapter; 
    private MyApp myApp;
    private CoreService cs=CoreService.getCoreService(); 
    private static Handler handler=new Handler();
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		
		
		handler.post(new Runnable() {
			public void run() {
				myApp = (MyApp) getApplication(); //获得自定义的应用程序MyApp 				
				initView();      
				ActivityControl(); 				
			}
		});			

	}
	
	
    private void initView(){
    	setUpActionBar();
    	setUpViewPager();
    	
    	CoreService cs=CoreService.getCoreService();
    	cs.initRequestQueue(this);    	
    }
    
    private void setUpActionBar() {  
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
    }  
   
    private void setUpViewPager() {      	
    	
        newFragment = new Fragment_NewFavrurl();  
        archiveFragment = new Fragment_ArchiveFavrurl();  
        favFragement = new Fragment_FavFavrurl();          
    	
        fragmentList.add(newFragment);
        fragmentList.add(archiveFragment);
        fragmentList.add(favFragement);
        titleList.add(getString(R.string.tab_new));
        titleList.add(getString(R.string.tab_archive));
        titleList.add(getString(R.string.tab_favorites));
   	
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager()); 
        
        mViewPager = (ViewPager)findViewById(R.id.pager);  
        mViewPager.setAdapter(mViewPagerAdapter);  
   }  
    
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
        	case R.id.action_message:
        		openMessage();
        		return true;
	        case R.id.action_find:
	            openFind();
	            return true;
	        case R.id.action_settings:
	            openSettings();
	            return true;
	        case R.id.action_myfriends:
	        	openMyfriends();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	
	private void openMyfriends() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, Activity_MyFriends.class);
		startActivity(intent);
	}


	private void openFind() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, Activity_FindFriends.class);
		startActivity(intent);
		
	}


	private void openMessage() {
		// TODO Auto-generated method stub
		
		Intent intent = new Intent(this, Activity_Message.class);
		startActivity(intent);
	}


	@Override  
	  public boolean onCreateOptionsMenu(Menu menu) {  
	    MenuInflater inflater = getMenuInflater();  
	    inflater.inflate(R.menu.main_activity_actions, menu);  
	    
	    MenuItem searchItem = menu.findItem(R.id.action_search);  
	    SearchView searchView = (SearchView) searchItem.getActionView();  
	    return true;  
	  }
	
	
	public void openSettings()
	{
		Intent intent = new Intent(this, Activity_Settings.class);
		startActivity(intent);
	}
	
	public class ViewPagerAdapter extends FragmentPagerAdapter {  
		  
        public ViewPagerAdapter(FragmentManager fm) {  
        	  
        	super(fm);               
        }  
  
        @Override  
        public Fragment getItem(int position) {  
            // TODO Auto-generated method stub  
        	return (fragmentList == null || fragmentList.size() == 0) ? null : fragmentList.get(position);
        }  
  
        @Override  
        public int getCount() {  
            // TODO Auto-generated method stub  
        	return fragmentList == null ? 0 : fragmentList.size();
        }  
          
        @Override  
        public CharSequence getPageTitle(int position) {  
        	return (titleList.size() > position) ? titleList.get(position) : "";
        }
        	    
    }
	
    @Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data)  
    {  
	         if(resultCode == 0){  
  	           // userid = Long.getLong(data.getStringExtra("userid"));  
  	            
	         }  
	
	}
	
    public void ActivityControl()
    {
    	UserDTO userdto=cs.getUserDTOFromDisk();
    	if (userdto==null)
    	{
    		startWelcomeActivity();
    	}
    	else
    	{
    		
    	}
    }
    
	public void startWelcomeActivity()
	{
			Intent intent = new Intent(this, Activity_Welcome.class);
			startActivityForResult(intent,0);		   
	}
	
}
