package com.folklore.ui.widget;

import com.folklore.tourengine.R;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;

import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

public class VideoWebView extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
          //  WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
		setContentView(R.layout.videowebview);

		
		WebView myWebView = (WebView) findViewById(R.id.webview); 
		
		//myWebView.setWebViewClient(new VideoWebViewClient());
		WebSettings webSettings = myWebView.getSettings(); 
		webSettings.setPluginsEnabled(true);
		
		
				webSettings.setPluginsEnabled(true);
			
		//webSettings.setPluginState(PluginState.ON);
		webSettings.setJavaScriptEnabled(true); 
		
		System.gc();
	    Intent i = getIntent();
	    Bundle extras = i.getExtras();
	    
		String url = extras.getString("url");
		url = url.replace("m.", "");
		url = url.replace("details", "watch");
		System.out.println("url:: "+url);
		myWebView.loadUrl(url); 
		
		
		
		
	    
	}
}

class VideoWebViewClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }
}
