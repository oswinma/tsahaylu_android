package net.tsahaylu.www.ui;

import net.tsahaylu.www.R;
import net.tsahaylu.www.common.Constants;
import net.tsahaylu.www.dto.UserDTO;
import net.tsahaylu.www.service.CoreService;
import net.tsahaylu.www.util.MyApp;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
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

public class Activity_Main extends FragmentActivity implements ActionBar.TabListener {
	
    private Fragment_NewFavrurl newFragment = new Fragment_NewFavrurl();  
    private Fragment_ArchiveFavrurl archiveFragment = new Fragment_ArchiveFavrurl();  
    private Fragment_FavFavrurl favFragement = new Fragment_FavFavrurl();  
    
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
    	setUpTabs();
    	
    	CoreService cs=CoreService.getCoreService();
    	cs.initRequestQueue(this);    	
    }
    
    private void setUpActionBar() {  
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);

    }  
    
    
    private void setUpTabs() {  
        final ActionBar actionBar = getActionBar();
        ActionBar.Tab newtab=actionBar.newTab();
        ActionBar.Tab archivetab=actionBar.newTab();
        ActionBar.Tab favtab=actionBar.newTab();
        newtab.setText("New");
        archivetab.setText("Archive");
        favtab.setText("Favorites");
        newtab.setTabListener(this);
        archivetab.setTabListener(this);
        favtab.setTabListener(this);
        
        actionBar.addTab(newtab);
        actionBar.addTab(archivetab);
        actionBar.addTab(favtab);       
   
    }  
    
    
    
    private void setUpViewPager() {  
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager()); 
        
        mViewPager = (ViewPager)findViewById(R.id.pager);  
        mViewPager.setAdapter(mViewPagerAdapter);  
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {  
            @Override  
            public void onPageSelected(int position) {  
                final ActionBar actionBar = getActionBar();  
                actionBar.setSelectedNavigationItem(position);  
            }  
              
            @Override  
            public void onPageScrollStateChanged(int state) {  
                switch(state) {  
                    case ViewPager.SCROLL_STATE_IDLE:  
                        //TODO  
                        break;  
                    case ViewPager.SCROLL_STATE_DRAGGING:  
                        //TODO  
                        break;  
                    case ViewPager.SCROLL_STATE_SETTLING:  
                        //TODO  
                        break;  
                    default:  
                        //TODO  
                        break;  
                }  
            }  
        });  
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
            switch (position) {  
                case Constants.TAB_INDEX_ONE:  
                    return newFragment;  
                case Constants.TAB_INDEX_TWO:  
                    return archiveFragment;  
                case Constants.TAB_INDEX_THREE:  
                    return favFragement;  
            }  
            throw new IllegalStateException("No fragment at position " + position);  
        }  
  
        @Override  
        public int getCount() {  
            // TODO Auto-generated method stub  
            return Constants.TAB_INDEX_COUNT;  
        }  
          
        @Override  
        public CharSequence getPageTitle(int position) {  
            String tabLabel = null;  
            switch (position) {  
                case Constants.TAB_INDEX_ONE:  
                    tabLabel = getString(R.string.tab_new);  
                    break;  
                case Constants.TAB_INDEX_TWO:  
                    tabLabel = getString(R.string.tab_archive);  
                    break;  
                case Constants.TAB_INDEX_THREE:  
                    tabLabel = getString(R.string.tab_favorites);  
                    break;  
            }  
            return tabLabel;  
        }
        	    
    }

	@Override
	public void onTabReselected(Tab arg0, android.app.FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onTabSelected(Tab tab, android.app.FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		mViewPager.setCurrentItem(tab.getPosition()); 

        
	}


	@Override
	public void onTabUnselected(Tab arg0, android.app.FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
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
