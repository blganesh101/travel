package com.folklore.tourengine;

import com.folklore.audiotour.MediaAudioList;
import com.folklore.videotour.VideodemoActivity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class TourRecorderActivity extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setting default screen to login.xml
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.tourrecordhome);
 
        //TextView registerScreen = (TextView)  this.findViewById(R.id.link_to_register);
        
        
        Button recordButton = (Button)findViewById(R.id.btnRecordVideo);
        recordButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Intent intent = new Intent(getApplicationContext(), com.folklore.yupload.MainActivity.class);
				Intent intent = new Intent(getApplicationContext(), com.folklore.videotour.VideodemoActivity.class);
				
				startActivity(intent);
			}
        	
        	
        });
        
        
        Button playButton = (Button)findViewById(R.id.btnRecordAudio);
        playButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(), MediaAudioList.class);
				startActivity(intent);
			}
        	
        	
        });
        
        /*registerScreen.setOnClickListener(new View.OnClickListener() {
   		 
            public void onClick(View v) {
                // Switching to Register screen
                //Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                ///startActivity(i);
            }
        });
 */       
        

    }
	
	
	
	

}
