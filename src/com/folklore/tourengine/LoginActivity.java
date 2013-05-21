package com.folklore.tourengine;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setting default screen to login.xml
        setContentView(R.layout.login);
 
        TextView registerScreen = (TextView)   this.findViewById(R.id.link_to_register);
        
        
        Button loginButton = (Button)findViewById(R.id.btnLogin);
        loginButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText email = (EditText)findViewById(R.id.userEmail);
				EditText password = (EditText)findViewById(R.id.userPassword);
				TextView error = (TextView) findViewById(R.id.error);
				
				System.out.println("Username "+email.getText().toString()+" Password: "+password.getText().toString());
				
				ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
            	postParameters.add(new BasicNameValuePair("username", email.getText().toString()));
            	postParameters.add(new BasicNameValuePair("password", password.getText().toString()));

            	String response = null;
            	/*try {
            	    response = CustomHttpClient.executeHttpPost("<target page url>", postParameters);
            	    String res=response.toString();
            	    res= res.replaceAll("\\s+","");
            	    if(res.equals("1"))
            	    {	
            	    	Intent homePageIntent = new Intent(getApplicationContext(),HomePageActivity.class);
            	    	startActivity(homePageIntent);
            	    	error.setText("Correct Username or Password");
            	    }
            	    else
            	    	error.setText("Sorry!! Incorrect Username or Password");
            	} catch (Exception e) {
            		Intent homePageIntent = new Intent(getApplicationContext(),HomePageActivity.class);
        	    	startActivity(homePageIntent);
        	    	
            		email.setText(e.toString());
            	}*/
            	//Intent homePageIntent = new Intent(getApplicationContext(),HomePageActivity.class);
            	//startActivity(homePageIntent);
            	
            	Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);

			}
        	
        	
        });
        
        
        registerScreen.setOnClickListener(new View.OnClickListener() {
   		 
            public void onClick(View v) {
                // Switching to Register screen
                //Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                ///startActivity(i);
            }
        });
        
        

    }
	
	
	
	

}
