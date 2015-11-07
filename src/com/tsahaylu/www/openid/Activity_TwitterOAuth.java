package com.tsahaylu.www.openid;


import com.tsahaylu.www.common.Constants;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by ServusKevin on 5/3/14.
 */
public class Activity_TwitterOAuth extends FragmentActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String authenticationUrl = getIntent().getStringExtra(Constants.STRING_EXTRA_AUTHENCATION_URL);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment_TwitterOAuth oAuthWebViewFragment = new Fragment_TwitterOAuth(authenticationUrl);
        fragmentTransaction.add(android.R.id.content,oAuthWebViewFragment);
        fragmentTransaction.commit();
    }
}