package com.folklore.videotour;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.util.ArrayList;

import com.folklore.tourengine.R;
import com.google.ads.c;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
//import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.os.Environment;
//import android.provider.MediaStore.Video.Thumbnails;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class VideodemoActivity extends Activity implements OnClickListener,OnItemClickListener{

	static ArrayList<String> arr_title,arr_description,arr_duration,arr_tag,arr_path_uri;
	
	ImageView btn_add_info,btn_refresh,btn_search;
	
	Context context;
	public static MyOpenHelper mo;
	public static SQLiteDatabase dbs;
	
	ListView list_video;
	Bitmap bmThumbnail=null;
	
	  static File sdDir;
	  
	 private EfficientAdapter videoListAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video_main);
		
		list_video = (ListView)findViewById(R.id.list_video);
		btn_add_info = (ImageView)findViewById(R.id.bottom_leftimage);
		btn_refresh = (ImageView)findViewById(R.id.bottom_centreimage);
		btn_search = (ImageView)findViewById(R.id.bottom_rightimage);
		
		btn_add_info.setOnClickListener(this);
		btn_refresh.setOnClickListener(this);
		btn_search.setOnClickListener(this);
		
		videoListAdapter = new EfficientAdapter(this);
		
		btn_add_info.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (v.getId()) {
				case R.id.bottom_leftimage:
					if(event.getAction()==MotionEvent.ACTION_DOWN)
						btn_add_info.setImageResource(R.drawable.add_nonfocus);
					else if(event.getAction()==MotionEvent.ACTION_UP)
						btn_add_info.setImageResource(R.drawable.add_focus);
					break;
				default:
					break;
				}
				return false;
			}
		});
        
		btn_refresh.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (v.getId()) {
				case R.id.bottom_centreimage:
					if(event.getAction()==MotionEvent.ACTION_DOWN)
						btn_refresh.setImageResource(R.drawable.refresh_nonfocus);
					else if(event.getAction()==MotionEvent.ACTION_UP)
						btn_refresh.setImageResource(R.drawable.refresh_focus);
					break;
				default:
					break;
				}
				return false;
			}
		});
        
        
        
		btn_search.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (v.getId()) {
				case R.id.bottom_rightimage:
					if(event.getAction()==MotionEvent.ACTION_DOWN)
						btn_search.setImageResource(R.drawable.search);
					else if(event.getAction()==MotionEvent.ACTION_UP)
						btn_search.setImageResource(R.drawable.search_focus);
					break;
				default:
					break;
				}
				return false;
			}
		});
		
		
		

	}

	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		
		if(dbs!=null)
			dbs.close();
	
		list_video.setOnItemClickListener(this);
		
		arr_description = new ArrayList<String>();
		arr_duration = new ArrayList<String>();
		arr_tag = new ArrayList<String>();
		
		arr_path_uri = new ArrayList<String>();
		
		
		mo = new MyOpenHelper(this, "myvideo.db", null, 1);

		  
		
		dbs = mo.getWritableDatabase();
		
		arr_title = new ArrayList<String>();

		
		try {
			  
		
		
		Cursor c = dbs.query("video_list", null, null, null, null, null, null); 
  
		
		c.moveToFirst();

		c.moveToFirst();
		do {
			  
			

			arr_description.add(c.getString(c.getColumnIndex("vdescription")));
			
			arr_tag.add(c.getString(c.getColumnIndex("vtag")));  



		} while (c.moveToNext());
		c.close();
		} catch (Exception e) {
			// TODO: handle exception
			
		}
		
		System.out.println("ArrayList Description is: "+arr_description);
		
	    System.out.println("ArrayList Tag is: "+arr_tag);
		
		
	    File folder = new File(Environment.getExternalStorageDirectory() + "/folklore/videos");
			
      	   sdDir = folder;
      	   
      	   if(!sdDir.exists())
      		   sdDir.mkdir();
      	   
      	 //sdDir.mkdir();
      	 
      	    try {
      	 
	            File[] sdDirFiles = sdDir.listFiles();
	          
	            System.out.println("sdfilesize  "+sdDirFiles.length);
	            for(int y=0;y<sdDirFiles.length;y++)
	            {
	            	File yourFile;
	            	MediaPlayer mp = new MediaPlayer();
	            	FileInputStream fs;
	            	FileDescriptor fd;
	            	System.out.println("datasource "+sdDirFiles[y].toString());
	            	fs = new FileInputStream(sdDirFiles[y].toString());
	            	fd = fs.getFD();
	            	
	            	if(sdDir.exists())
	            	mp.setDataSource(fd);
	            	mp.prepare(); // might be optional
	            	int length = mp.getDuration();
	            	mp.release();
	            	
	            	System.out.println("FILE LENGHT COMING AS+++++++++: "+length);
	            	
	            	int cal_duration_in_sec = length/1000;
	            	
	            	Integer convt_to_str = new Integer(cal_duration_in_sec);
	            	
	            	String audio_duration = convt_to_str.toString()+".0"+" sec";
	            	
	            	arr_duration.add(audio_duration);
	            	
	            	System.out.println("DURATION IN SECONDS IS::: "+cal_duration_in_sec);
	            	
	            	System.out.println("VVVVVVVVVVVV"+sdDirFiles[y]);
	            	
	            	arr_path_uri.add(sdDirFiles[y].toString());
	            	
	            	//System.out.println("AAAAAAAAAAAAAAAAA  "+sdDirFiles[y].toString().substring(24));
	            	
	            	arr_title.add(sdDirFiles[y].toString().substring(24));
	            	  
	
	            }
	            
	          
	         list_video.setAdapter(videoListAdapter);
	      	
	      	
	      	
	      	/*if(arr_title.size()<1)
	      	{
	      		
	      		for(int i=0;i<arr_description.size();i++)
	      		{
	      			dbs.delete("video_list", "description="+"'"+arr_description.get(i)+"'", null);
	      			dbs.delete("video_list", "tag="+"'"+arr_tag.get(i)+"'", null);
	      			
	      		}
	
	      	}
	      	*/
			} catch (Exception e) {
				Toast.makeText(getBaseContext(), "No files exist", Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}
		
		  
	}


	private static class EfficientAdapter extends BaseAdapter {
		

		private LayoutInflater vi;

		public EfficientAdapter(Context context) {
			
			vi = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		}

		@Override
		public int getCount() {

		
			return arr_title.size();

		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			
			
				
			
			ViewHolder holder;

			View v = convertView;
			try {
				
			v = vi.inflate(R.layout.list_row, null);
			holder = new ViewHolder();
			holder.title = (TextView) v
					.findViewById(R.id.title);
			holder.description = (TextView) v
					.findViewById(R.id.artist);
			
			holder.duration = (TextView) v
					.findViewById(R.id.duration);
			holder.iv = (ImageView) v
					.findViewById(R.id.list_image);
			
			holder.title.setText(arr_title.get(position));
			
			holder.description.setText(arr_description.get(position));
			
			holder.duration.setText(arr_duration.get(position));  
			
			
		
			Bitmap bmThumbnail;
			System.out.println(sdDir+"/"+arr_title.get(position));
			//bmThumbnail = ThumbnailUtils.createVideoThumbnail(sdDir+"/"+arr_title.get(position), Thumbnails.MICRO_KIND);
			
			//holder.iv.setImageBitmap(bmThumbnail);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return v;
			
			
		}

		static class ViewHolder {
			TextView title,description,duration;
			ImageView iv;

		}
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == btn_add_info)
		{
			
			Intent i = new Intent(this, Video_Details.class);
			startActivityForResult(i,0);
		}
		
		if(v == btn_refresh)
		{
			
			onResume();
		}
		
		if(v == btn_search)
		{
			
			Intent i = new Intent(this, Video_Search.class);
			startActivityForResult(i,0);
		}
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

		Intent i = new Intent(this, ViewVideo.class);
		
		String str_send_uri = arr_path_uri.get(arg2);
		Bundle b = new Bundle();
		b.putString("uri", str_send_uri);
		i.putExtras(b);
		startActivity(i);
	}
	
}


class MyOpenHelper extends SQLiteOpenHelper {

	public MyOpenHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE video_list(_id INTEGER PRIMARY KEY AUTOINCREMENT, vdescription TEXT, vtag TEXT)");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		System.out.println("database upgraded");
	}

}