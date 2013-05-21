package com.folklore.videotour;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class ViewVideo extends Activity {
      private String filename;
      @Override
      public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            try {
				
			
            System.gc();
            Intent i = getIntent();
            Bundle extras = i.getExtras();
            filename = extras.getString("uri");
            VideoView vv = new VideoView(getApplicationContext());
            setContentView(vv);
            vv.setVideoPath(filename);
            vv.setMinimumHeight(480);
            vv.setMinimumWidth(800);
            vv.setMediaController(new MediaController(this));
            vv.requestFocus();
            vv.start();
            } catch (Exception e) {
				// TODO: handle exception
			}
      }
} 