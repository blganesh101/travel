package com.folklore.ads;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.google.ads.*;
import com.google.ads.AdRequest.ErrorCode;
import com.folklore.tourengine.R;
import com.folklore.util.Utility;

public class AdsActivity extends Activity implements AdListener {
  private AdView adView;
  /** The interstitial ad. */
  private InterstitialAd interstitialAd;
	/** Called when the activity is first created. */
	private static final int STOPSPLASH = 0;
	//time in milliseconds
	private static final long SPLASHTIME = 3000;
  

	@Override
  public void onCreate(Bundle savedInstanceState) {
   super.onCreate(savedInstanceState);
    setContentView(R.layout.adview);

    // Create an ad.
    interstitialAd = new InterstitialAd(this,"a14f80fe51d8187");

    // Set the AdListener.
    interstitialAd.setAdListener(this);
    
    // Load the interstitial ad. Check logcat output for the hashed device ID
    // to get test ads on a physical device.
    AdRequest adRequest = new AdRequest();
    adRequest.addTestDevice(AdRequest.LOGTAG);
    interstitialAd.loadAd(adRequest);
    
    
    Message msg = new Message();
    
    msg.what = STOPSPLASH;
    
    
    splashHandler.sendMessageDelayed(msg, SPLASHTIME);
    
    //setContentView(R.layout.main);
    
   // setContentView(R.layout.splash);
    
    
    
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
                    if(Utility.isAppInstalled("com.google.android.youtube", getApplicationContext())) {
                        intent.setClassName("com.google.android.youtube", "com.google.android.youtube.WatchActivity");
                    }
                    startActivity(intent);
                    
                        //splash.setVisibility(View.GONE);
                        break;
                }
                super.handleMessage(msg);
        }
	};
  @Override
  public void onDestroy() {
    if (adView != null) {
      adView.destroy();
    }
    super.onDestroy();
  }


public void onDismissScreen(Ad arg0) {
	// TODO Auto-generated method stub
	
}

public void onFailedToReceiveAd(Ad arg0, ErrorCode arg1) {
	// TODO Auto-generated method stub
	
}


public void onLeaveApplication(Ad arg0) {
	// TODO Auto-generated method stub
	
}


public void onPresentScreen(Ad arg0) {
	// TODO Auto-generated method stub
	
}


public void onReceiveAd(Ad arg0) {
	System.out.println("ad received");
}

}