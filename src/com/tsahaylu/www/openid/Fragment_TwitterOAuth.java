package com.tsahaylu.www.openid;

import com.tsahaylu.www.R;
import com.tsahaylu.www.ui.Activity_Welcome;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by ServusKevin on 5/3/14.
 */
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by ServusKevin on 5/1/14.
 */
public class Fragment_TwitterOAuth extends Fragment {
    private WebView webView;
    private String authenticationUrl;

    public Fragment_TwitterOAuth(String authenticationUrl) {
        this.authenticationUrl = authenticationUrl;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        webView.loadUrl(authenticationUrl);
        webView.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("oauth_verifier="))
                {
                    Intent intent = new Intent(getActivity().getApplicationContext(), Activity_Welcome.class);
                    intent.setData( Uri.parse(url));
                    startActivity(intent);
                }
                view.loadUrl(url);
                return true;
            }
        });
        WebSettings webSettings= webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.oauth_webview,container,false);
        webView = (WebView)view.findViewById(R.id.webViewOAuth);
        return view;
    }
}