package com.folklore.ui.widget;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;
import com.folklore.tourengine.R; 
public class VideoPlayerView extends Activity {
 
	
	public Handler handler;
	private String url;
	private String description;
	private String title;
	private String duration;
	private int rating;
	 
   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.videoview);
      
       
       System.gc();
       Intent i = getIntent();
       Bundle extras = i.getExtras();
       url = extras.getString("url");
       title = extras.getString("title");
       description = extras.getString("description");
       duration = extras.getString("duration");
       rating = extras.getInt("rating");
       
       
       TextView titleText = (TextView) findViewById(R.id.title);
       titleText.setText(title);
       
       TextView durationText = (TextView) findViewById(R.id.duration);
       durationText.setText(duration);
       
       TextView descriptionText = (TextView) findViewById(R.id.description);
       descriptionText.setText(description);
       
       TextView ratingText = (TextView) findViewById(R.id.rating);
       ratingText.setText(new Integer(rating).toString());
       
       
       VideoView videoView = (VideoView)findViewById(R.id.video);
       videoView.setVideoURI(Uri.parse(url));
       videoView.setMediaController(new MediaController(this));
       videoView.requestFocus();
       videoView.start();

 	}
   
    
}