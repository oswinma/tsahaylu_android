package com.tsahaylu.www.ui;

import com.tsahaylu.www.R;
import com.tsahaylu.www.R.layout;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class Activity_FindFriends extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity__find_friends);
		
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
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
	
}
