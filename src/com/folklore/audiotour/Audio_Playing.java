package com.folklore.audiotour;

import com.folklore.tourengine.R;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class Audio_Playing extends Activity implements OnCompletionListener{
	
	MediaPlayer mp_audio_playing;
	
//	MediaController mc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.audio_playing);
		
		
		
		
//		mp_audio_playing = MediaPlayer.create(this, uri);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub  
		super.onResume();
		
//		mc = (MediaController)findViewById(R.id.mediaController1);
		
		
		
		Bundle b = getIntent().getExtras();
		
		String media_uri = b.getString("uri");
		
		System.out.println("URI COMING AS:::: "+media_uri);
		
		Uri play_file = Uri.parse(media_uri);
		
//		mp_audio_playing = MediaPlayer.create(this, play_file);
		  
		VideoView videoView = (VideoView) findViewById(R.id.videoView1);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        mediaController.setMediaPlayer(videoView);
//        mediaController.show();
// Set video link (mp4 format )
        Uri video = Uri.parse(media_uri);
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(video);
        videoView.start();
		
        videoView.setOnCompletionListener(this);
        
        Toast.makeText(this, "It will automatically will take you to playlist once this is completed", Toast.LENGTH_LONG).show();
        
	}


	public void onCompletion(MediaPlayer mp) {
		// TODO Auto-gen//erated method stub
		//Intent i = new Intent(this, MediaAudioList.class);
		//startActivity(i);
		finish();
	}

}
