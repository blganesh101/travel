package com.folklore.adstemp;

import com.folklore.tourengine.R;
import com.folklore.util.Utility;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.widget.ImageView;

public class TempAdActivity extends Activity {
    /** Called when the activity is first created. */
	private static final int STOPSPLASH = 0;
    //time in milliseconds
    private static final long SPLASHTIME = 3000;
   
    private ImageView splash;
    
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Message msg = new Message();
        
        msg.what = STOPSPLASH;
        splashHandler.sendMessageDelayed(msg, SPLASHTIME);
        
        //setContentView(R.layout.main);
        
        setContentView(R.layout.tempadd);
    }
    
    private Handler splashHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
                switch (msg.what) {
                case STOPSPLASH:
                        //remove SplashScreen from view
                        
                	System.gc();
            	    Intent i = getIntent();
            	    Bundle extras = i.getExtras();
            	    
            		String url = extras.getString("url");
            		
            		Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(url));
            		try
            		{
            			if(Utility.isAppInstalled("com.google.android.youtube", getApplicationContext())) {
            				intent.setClassName("com.google.android.youtube", "com.google.android.youtube.WatchActivity");
            			}
            		}
            		catch(Exception e)
            		{
            			if(Utility.isAppInstalled("com.google.android.youtube", getApplicationContext())) {
            				intent.setClassName("com.google.android.youtube", "com.google.android.youtube.PlayerActivity");
            			}
            		}
                    startActivity(intent);
                    
                        //splash.setVisibility(View.GONE);
                        break;
                }
                super.handleMessage(msg);
        }
	};
}