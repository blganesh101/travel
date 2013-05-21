package com.folklore.audiotour;


import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

import com.folklore.tourengine.R;

import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class CopysdActivity extends ListActivity implements OnClickListener{
        private static final int UPDATE_FREQUENCY = 500;
        private static final int STEP_VALUE = 4000;
        
        private MediaCursorAdapter mediaAdapter = null;
        private TextView selelctedFile = null;
        private SeekBar seekbar = null;
        private MediaPlayer player = null;
        private ImageButton playButton = null;
        private ImageButton prevButton = null;
        private ImageButton nextButton = null;
        
        ImageView m_ImageViewleft;
        ImageView m_ImageViewright;
        ImageView m_ImageViewcentre;
        
        ImageView thumb_img;
        TextView file_name;
        
        private boolean isStarted = true;
        private String currentFile = "";
        private boolean isMoveingSeekBar = false;
        
       ArrayList<Integer> arr_img;
       
       ArrayList<String> arr_file_title;
        
        private final Handler handler = new Handler();
        
        int count=0;
       
        
        @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        
//        selelctedFile = (TextView)findViewById(R.id.selectedfile);
//        seekbar = (SeekBar)findViewById(R.id.seekbar);
//        playButton = (ImageButton)findViewById(R.id.play);
//        prevButton = (ImageButton)findViewById(R.id.prev);
//        nextButton = (ImageButton)findViewById(R.id.next);
        
    //    player = new MediaPlayer();
        
       // player.setOnCompletionListener(onCompletion);
       // player.setOnErrorListener(onError);
       // seekbar.setOnSeekBarChangeListener(seekBarChanged);
        
        arr_file_title = new ArrayList<String>();
        
        m_ImageViewleft = (ImageView)findViewById(R.id.bottom_leftimage);
        m_ImageViewcentre = (ImageView)findViewById(R.id.bottom_centreimage);
        m_ImageViewright = (ImageView)findViewById(R.id.bottom_rightimage);
        
        m_ImageViewleft.setOnClickListener(this);
        m_ImageViewcentre.setOnClickListener(this);
        m_ImageViewright.setOnClickListener(this);
        
        
        m_ImageViewleft.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				switch (v.getId()) {
				case R.id.bottom_leftimage:
					if(event.getAction()==MotionEvent.ACTION_DOWN)
						m_ImageViewleft.setImageResource(R.drawable.add_nonfocus);
					else if(event.getAction()==MotionEvent.ACTION_UP)
						m_ImageViewleft.setImageResource(R.drawable.add_focus);
					break;
				default:
					break;
				}
				return false;
			}
		});
        
        m_ImageViewcentre.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				switch (v.getId()) {
				case R.id.bottom_centreimage:
					if(event.getAction()==MotionEvent.ACTION_DOWN)
						m_ImageViewcentre.setImageResource(R.drawable.refresh_nonfocus);
					else if(event.getAction()==MotionEvent.ACTION_UP)
						m_ImageViewcentre.setImageResource(R.drawable.refresh_focus);
					break;
				default:
					break;
				}
				return false;
			}
		});
        
        
        
        m_ImageViewright.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				switch (v.getId()) {
				case R.id.bottom_rightimage:
					if(event.getAction()==MotionEvent.ACTION_DOWN)
						m_ImageViewright.setImageResource(R.drawable.search);
					else if(event.getAction()==MotionEvent.ACTION_UP)
						m_ImageViewright.setImageResource(R.drawable.search_focus);
					break;
				default:
					break;
				}
				return false;
			}
		});
        
//        Uri jk = Uri.parse("/sdcard/folklore/audios/");
        
        File[] f = (Environment.getExternalStorageDirectory()).listFiles();
        int i;
        for(i = 0; i < f.length; i++) {
        System.out.println("############## "+f[i].getAbsolutePath());
        }
        
        try {
		
        	  File sdDir = new File("/sdcard/folklore/audios");
              File[] sdDirFiles = sdDir.listFiles();
              for(int y=0;y<sdDirFiles.length;y++)
              {
              	System.out.println("VVVVVVVVVVVV"+sdDirFiles[y]);
              	
              	System.out.println("AAAAAAAAAAAAAAAAA  "+sdDirFiles[y].toString().substring(24));
              	
              	arr_file_title.add(sdDirFiles[y].toString().substring(24));
              	
              	Toast.makeText(getBaseContext(), sdDirFiles[y].toString().substring(24), 3000).show();
              	
//                 ImageView myImageView = new ImageView(context);
//                 myImageView.setImageDrawable(Drawable.createFromPath(singleFile.getAbsolutePath());
//                 myImageView.setId(picIndex);
//                 picIndex++;
//                 drawablesId.add(myImageView.getId());
//                 mySDCardImages.add(myImageView);
              }
              
            
        	
		} catch (Exception e) {
			Toast.makeText(getBaseContext(), "No files exist", Toast.LENGTH_SHORT).show();
		}
        
      
//        System.out.println("URI GOING AS::::::::::::::::::::::: "+MediaStore.Audio.Media.EXTERNAL_CONTENT_URI.toString());
        Uri uri = Uri.fromFile(new File("/sdcard/folklore/audios"));  
         
//        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        
        
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

                setListAdapter(mediaAdapter);
                System.out.println("list"+mediaAdapter);
                
//                playButton.setOnClickListener(onButtonClick);
//                nextButton.setOnClickListener(onButtonClick);
//                prevButton.setOnClickListener(onButtonClick);
        }
    }
    
    
        
        
        
       
        
       
        private class MediaCursorAdapter extends SimpleCursorAdapter{

                public MediaCursorAdapter(Context context, int layout, Cursor c) {
                        super(context, layout, c, 
                                        new String[] { MediaStore.MediaColumns.DISPLAY_NAME, MediaStore.MediaColumns.TITLE, MediaStore.Audio.AudioColumns.DURATION},
                                        new int[] { R.id.title,R.id.artist,R.id.duration });
                }

                @Override
                public void bindView(View view, Context context, Cursor cursor) {
                	
                	    //ImageView img=(ImageView)view.findViewById(R.id.thumb_audio);
                        TextView title = (TextView)view.findViewById(R.id.title);
                        TextView name = (TextView)view.findViewById(R.id.artist);
                        TextView duration = (TextView)view.findViewById(R.id.duration);
                        
                      //  img.getDrawable();
                        
//                        name.setText(cursor.getString(
//                                        cursor.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME)));
                        
                        name.setText("dev");
                
                        
//                        title.setText(cursor.getString(
//                                        cursor.getColumnIndex(MediaStore.MediaColumns.TITLE)));

                        title.setText(arr_file_title.get(count));
System.out.println("VAL is coming as: "+arr_file_title.get(count));
                        
                        long durationInMs = Long.parseLong(cursor.getString(
                                        cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DURATION)));
                        
                        double durationInMin = ((double)durationInMs/1000.0)/60.0;
                        
                        durationInMin = new BigDecimal(Double.toString(durationInMin)).setScale(2, BigDecimal.ROUND_UP).doubleValue(); 

                        duration.setText("" + durationInMin);
                        
                        view.setTag(cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA)));
                        count++;
                }

                @Override
                public View newView(Context context, Cursor cursor, ViewGroup parent) {
                        LayoutInflater inflater = LayoutInflater.from(context);
                        View v = inflater.inflate(R.layout.list_row, parent, false);
                        
                        bindView(v, context, cursor);
                        
                        return v;
                }
    }
        
//        private View.OnClickListener onButtonClick = new View.OnClickListener() {
//                
//                @Override
//                public void onClick(View v) {
//                        switch(v.getId())
//                        {
//                                case R.id.play:
//                                {
//                                        if(player.isPlaying())
//                                        {
//                                                handler.removeCallbacks(updatePositionRunnable);
//                                                player.pause();
//                                                playButton.setImageResource(android.R.drawable.ic_media_play);
//                                        }
//                                        else
//                                        {
//                                                if(isStarted)
//                                                {
//                                                        player.start();
//                                                        playButton.setImageResource(android.R.drawable.ic_media_pause);
//                                                        
//                                                        updatePosition();
//                                                }
//                                                else
//                                                {
//                                                        startPlay(currentFile);
//                                                }
//                                        }
//                                        
//                                        break;
//                                }
//                                case R.id.next:
//                                {
//                                        int seekto = player.getCurrentPosition() + STEP_VALUE;
//                                        
//                                        if(seekto > player.getDuration())
//                                                seekto = player.getDuration();
//                                        
//                                        player.pause();
//                                        player.seekTo(seekto);
//                                        player.start();
//                                        
//                                        break;
//                                }
//                                case R.id.prev:
//                                {
//                                        int seekto = player.getCurrentPosition() - STEP_VALUE;
//                                        
//                                        if(seekto < 0)
//                                                seekto = 0;
//                                        
//                                        player.pause();
//                                        player.seekTo(seekto);
//                                        player.start();
//                                        
//                                        break;
//                                }
//                        }
//                }
//        };
        
       
       

		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			int id = v.getId();
			
			switch (id) {
			case R.id.bottom_leftimage:
				//code
				break;
				
				
			case R.id.bottom_centreimage:
				//code
				break;
				
			case R.id.bottom_rightimage:
				//code
				break;
				
				
				
				

			default:
				break;
			}
			
		}
       
}
