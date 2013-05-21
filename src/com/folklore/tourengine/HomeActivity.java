package com.folklore.tourengine;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class HomeActivity extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        // setting default screen to login.xml
        setContentView(R.layout.home);
        
        //TextView registerScreen = (TextView)  this.findViewById(R.id.link_to_register);
        
        
        Button recordButton = (Button)findViewById(R.id.btnRecord);
        recordButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), TourRecorderActivity.class);
				startActivity(intent);
				
			}
        	
        	
        });
        
        
        Button playButton = (Button)findViewById(R.id.btnPlay);
        playButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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
