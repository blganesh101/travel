package com.folklore.audiotour;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.folklore.tourengine.R;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class AudioRecorder extends Activity implements OnClickListener{
    /** Called when the activity is first created. */
	
	Button start,stop;
	MediaRecorder recorder = new MediaRecorder();
	
//	static ArrayList<String> title,description;
	
	String str_title=null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio_recorder);
        start = (Button)findViewById(R.id.button1);
        stop = (Button)findViewById(R.id.button2);
        start.setOnClickListener(this);
        stop.setOnClickListener(this);
        
        Bundle b = getIntent().getExtras();
        
        if(b != null)
        {
        	str_title = b.getString("title"); 
            	
        }
        System.out.println("STR TITLE IS: "+str_title);
        
//        title = new ArrayList<String>();
//        description = new ArrayList<String>();
        
    }


	public void onClick(View v) {
		// TODO Auto-generated method stub   
		if(v == start)
		{
			recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
			recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
			
			// writing code for creating new directory inside sd card
			
			File folder = new File(Environment.getExternalStorageDirectory() + "/folklore/audios");
			boolean success = false;
			if(!folder.exists())
			{
			    success = folder.mkdirs();
			}         
			if (!success) 
			{ 
			    // Do something on success
				System.out.println("Its Success!!");
				recorder.setOutputFile("/sdcard/folklore/audios/"+str_title+".3gp");
				
			}
			else 
			{
			    // Do something else on failure
				System.out.println("Its Failure!!");
				recorder.setOutputFile("/sdcard/folklore/audios/"+str_title+".3gp");
				
//				recorder.
				
			}
			
			// End of code
			
//			recorder.setOutputFile("/sdcard/devsample.3gp");

//			recorder.setOnErrorListener(errorListener);
//			recorder.setOnInfoListener(infoListener);

			try {
				recorder.prepare();
				recorder.start();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Toast.makeText(this, "Started Recording...", Toast.LENGTH_LONG).show();
			
		}
		if(v == stop)
		{
			
			recorder.stop();
			recorder.reset();
			recorder.release();
			
			
			
			Toast.makeText(this, "Recording Stopped", Toast.LENGTH_LONG).show();
			finish();
			
		}
	}
}