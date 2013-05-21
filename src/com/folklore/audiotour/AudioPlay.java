package com.folklore.audiotour;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

import com.folklore.tourengine.R;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class AudioPlay extends Activity {
	
	String str;
	
        @Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		Bundle b = getIntent().getExtras();
		if(b != null)
		{
			str = b.getString("uri");
			startPlay(str);
			
			
		}
		else
		{
			Toast.makeText(getBaseContext(), "Oop's, Something went Wrong!!", 3000).show();
		}
		
		
	}

		private static final int UPDATE_FREQUENCY = 500;
        private static final int STEP_VALUE = 4000;
        
        private MediaCursorAdapter mediaAdapter = null;
        private TextView selelctedFile = null;
        private SeekBar seekbar = null;
        private MediaPlayer player = null;
        private ImageButton playButton = null;
        private ImageButton prevButton = null;
        private ImageButton nextButton = null;
        
        ImageView thumb_img;
        TextView file_name;
        
        private boolean isStarted = true;
        private String currentFile = "";
        private boolean isMoveingSeekBar = false;
        
       ArrayList<Integer> arr_img;
        
        private final Handler handler = new Handler();
        
        private final Runnable updatePositionRunnable = new Runnable() {
                public void run() {
                        updatePosition();
                }
        };
        
        @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audioplay);
        
        selelctedFile = (TextView)findViewById(R.id.selectedfile);
        seekbar = (SeekBar)findViewById(R.id.seekbar);
        playButton = (ImageButton)findViewById(R.id.play);
        prevButton = (ImageButton)findViewById(R.id.prev);
        nextButton = (ImageButton)findViewById(R.id.next);
        
        player = new MediaPlayer();
        
        player.setOnCompletionListener(onCompletion);
        player.setOnErrorListener(onError);
        seekbar.setOnSeekBarChangeListener(seekBarChanged);
        
        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        
        if(null != cursor)
        {
        	//arr_img=new ArrayList<Integer>();
                cursor.moveToFirst();
                
                String ss=MediaStore.Audio.Media.EXTERNAL_CONTENT_URI.toString();
                
                
                System.out.println("String value value:"+ss);
                System.out.println("cursor value:"+cursor.getPosition());
                System.out.println("cursor value:"+cursor.getCount());
                System.out.println("cursor value:"+cursor.toString());
        
                mediaAdapter = new MediaCursorAdapter(this, R.layout.row, cursor);
//                for(int i=0;i<cursor.getCount();i++)
//                {
//                	int z=R.drawable.ic_launcher;
//                	arr_img.add(z);
//                }
//                
//              thumb_img=(ImageView)findViewById(R.id.Thumbnail);
//             file_name=(TextView)findViewById(R.id.FilePath);
             
             //file_name.setText(resid);
                
//                setListAdapter(mediaAdapter);
                System.out.println("list"+mediaAdapter);
                
                playButton.setOnClickListener(onButtonClick);
                nextButton.setOnClickListener(onButtonClick);
                prevButton.setOnClickListener(onButtonClick);
        }
    }
    
//    @Override
//        protected void onListItemClick(ListView list, View view, int position, long id) {
//                super.onListItemClick(list, view, position, id);
//                
//                currentFile = (String) view.getTag();
//                
//                startPlay(str);
//        }
//    
        @Override
        protected void onDestroy() {
                super.onDestroy();
                
                handler.removeCallbacks(updatePositionRunnable);
                player.stop();
                player.reset();
                player.release();

                player = null;
        }

        private void startPlay(String file) {
                Log.i("Selected: ", file);
                
                selelctedFile.setText(file);
                seekbar.setProgress(0);
                                
                player.stop();
                player.reset();
                
                try {
                        player.setDataSource(file);
                        player.prepare();
                        player.start();
                } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                } catch (IllegalStateException e) {
                        e.printStackTrace();
                } catch (IOException e) {
                        e.printStackTrace();
                }
                
                seekbar.setMax(player.getDuration());
                playButton.setImageResource(android.R.drawable.ic_media_pause);
                
                updatePosition();
                
                isStarted = true;
        }
        
        private void stopPlay() {
                player.stop();
                player.reset();
                playButton.setImageResource(android.R.drawable.ic_media_play);
                handler.removeCallbacks(updatePositionRunnable);
                seekbar.setProgress(0);
                
                isStarted = false;
        }
        
        private void updatePosition(){
                handler.removeCallbacks(updatePositionRunnable);
                
                seekbar.setProgress(player.getCurrentPosition());
                
                handler.postDelayed(updatePositionRunnable, UPDATE_FREQUENCY);
        }

        private class MediaCursorAdapter extends SimpleCursorAdapter{

                public MediaCursorAdapter(Context context, int layout, Cursor c) {
                        super(context, layout, c, 
                                        new String[] { MediaStore.MediaColumns.DISPLAY_NAME, MediaStore.MediaColumns.TITLE, MediaStore.Audio.AudioColumns.DURATION},
                                        new int[] { R.id.displayname, R.id.title, R.id.duration,R.id.thumb_audio });
                }

                @Override
                public void bindView(View view, Context context, Cursor cursor) {
                	
                	    ImageView img=(ImageView)view.findViewById(R.id.thumb_audio);
                        TextView title = (TextView)view.findViewById(R.id.title);
                        TextView name = (TextView)view.findViewById(R.id.displayname);
                        TextView duration = (TextView)view.findViewById(R.id.duration);
                        
                        img.getDrawable();
                        
                        name.setText(cursor.getString(
                                        cursor.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME)));
                        
                        title.setText(cursor.getString(
                                        cursor.getColumnIndex(MediaStore.MediaColumns.TITLE)));

                        long durationInMs = Long.parseLong(cursor.getString(
                                        cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DURATION)));
                        
                        double durationInMin = ((double)durationInMs/1000.0)/60.0;
                         
                        durationInMin = new BigDecimal(Double.toString(durationInMin)).setScale(2, BigDecimal.ROUND_UP).doubleValue(); 

                        duration.setText("" + durationInMin);
                        
                        view.setTag(cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA)));
                }

                @Override
                public View newView(Context context, Cursor cursor, ViewGroup parent) {
                        LayoutInflater inflater = LayoutInflater.from(context);
                        View v = inflater.inflate(R.layout.listitem, parent, false);
                        
                        bindView(v, context, cursor);
                        
                        return v;
                }
    }
        
        private View.OnClickListener onButtonClick = new View.OnClickListener() {
                
               
                public void onClick(View v) {
                        switch(v.getId())
                        {
                                case R.id.play:
                                {
                                        if(player.isPlaying())
                                        {
                                                handler.removeCallbacks(updatePositionRunnable);
                                                player.pause();
                                                playButton.setImageResource(android.R.drawable.ic_media_play);
                                        }
                                        else
                                        {
                                                if(isStarted)
                                                {
                                                        player.start();
                                                        playButton.setImageResource(android.R.drawable.ic_media_pause);
                                                        
                                                        updatePosition();
                                                }
                                                else
                                                {
                                                        startPlay(str);
                                                }
                                        }
                                        
                                        break;
                                }
                                case R.id.next:
                                {
                                        int seekto = player.getCurrentPosition() + STEP_VALUE;
                                        
                                        if(seekto > player.getDuration())
                                                seekto = player.getDuration();
                                        
                                        player.pause();
                                        player.seekTo(seekto);
                                        player.start();
                                        
                                        break;
                                }
                                case R.id.prev:
                                {
                                        int seekto = player.getCurrentPosition() - STEP_VALUE;
                                        
                                        if(seekto < 0)
                                                seekto = 0;
                                        
                                        player.pause();
                                        player.seekTo(seekto);
                                        player.start();
                                        
                                        break;
                                }
                        }
                }
        };
        
        private MediaPlayer.OnCompletionListener onCompletion = new MediaPlayer.OnCompletionListener() {
                
                
                public void onCompletion(MediaPlayer mp) {
                        stopPlay();
                        finish();
                }
        };
        
        private MediaPlayer.OnErrorListener onError = new MediaPlayer.OnErrorListener() {
                
               
                public boolean onError(MediaPlayer mp, int what, int extra) {
                        // returning false will call the OnCompletionListener
                        return false;
                }
        };
        
        private SeekBar.OnSeekBarChangeListener seekBarChanged = new SeekBar.OnSeekBarChangeListener() {
                
                public void onStopTrackingTouch(SeekBar seekBar) {
                        isMoveingSeekBar = false;
                }
                
               
                public void onStartTrackingTouch(SeekBar seekBar) {
                        isMoveingSeekBar = true;
                }
                
               
                public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                        if(isMoveingSeekBar)
                        {
                                player.seekTo(progress);
                        
                                Log.i("OnSeekBarChangeListener","onProgressChanged");
                        }
                }
        };
       
}
