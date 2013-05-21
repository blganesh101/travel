package com.folklore.audiotour;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.util.ArrayList;

import com.folklore.tourengine.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.MediaPlayer;
import android.os.Bundle;
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

public class MediaAudioList extends Activity implements OnClickListener,OnItemClickListener{

	static ArrayList<String> arr_title,arr_description,arr_duration,arr_tag,arr_path_uri;
	
	ImageView btn_add_info,btn_refresh,btn_search;
	
	Context context;
	public static MyOpenHelper2 mo;
	public static SQLiteDatabase dbs;
	
	private EfficientAdapter audioListAdapter;
	
	ListView list_audio;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.audio_main);
		
		audioListAdapter = new EfficientAdapter(this);
		
		list_audio = (ListView)findViewById(R.id.list_audio);
		btn_add_info = (ImageView)findViewById(R.id.bottom_leftimage);
		btn_refresh = (ImageView)findViewById(R.id.bottom_centreimage);
		btn_search = (ImageView)findViewById(R.id.bottom_rightimage);
		
		btn_add_info.setOnClickListener(this);
		btn_refresh.setOnClickListener(this);
		btn_search.setOnClickListener(this);
		
		
		
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
		
		
		
//		arr_title = new ArrayList<String>();
		
		
		

//		ContentValues cv = new ContentValues();
////		cv.put("first_name", user.getText().toString());
//		cv.put("description", "desc");
//		cv.put("tag", "tag");
//
////		dbs.delete("media_list", "description='desc'", null);
//
//		dbs.insert("media_list", null, cv);
	}

	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	
		list_audio.setOnItemClickListener(this);
		
		arr_description = new ArrayList<String>();
		arr_duration = new ArrayList<String>();
		arr_tag = new ArrayList<String>();
		
		arr_path_uri = new ArrayList<String>();
		
		
		mo = new MyOpenHelper2(this, "mydev.db", null, 1);

		  
		
		dbs = mo.getWritableDatabase();
		
		arr_title = new ArrayList<String>();
//		arr_description.add(0, "Sample Test");
//		arr_tag.add(0, "TAG");
		
		try {
			  
		
		
		Cursor c = dbs.query("media_list", null, null, null, null, null, null); 
  
		// int i = 0;
		c.moveToFirst();
//		arrlist = new ArrayList<String>();
		c.moveToFirst();
		do {
			  
			//System.out.println("Val from database of Description coming as: "+c.getString(c.getColumnIndex("description")));
			
			//System.out.println("Val from database of TAG coming as: "+c.getString(c.getColumnIndex("tag")));
			

			arr_description.add(c.getString(c.getColumnIndex("description")));
			//System.out.println("arr_description: "+arr_description);
			
			arr_tag.add(c.getString(c.getColumnIndex("tag")));  

//			username.setAdapter(new ArrayAdapter<String>(getBaseContext(),
//					android.R.layout.simple_list_item_1, arrlist));  

		} while (c.moveToNext());
		} catch (Exception e) {
			// TODO: handle exception
		}
		//System.out.println("ArrayList Description is: "+arr_description);
		
		//System.out.println("ArrayList Tag is: "+arr_tag);
		
		try {
			
      	  File sdDir = new File("/sdcard/folklore/audios");
            File[] sdDirFiles = sdDir.listFiles();
            for(int y=0;y<sdDirFiles.length;y++)
            {
            	File yourFile;
            	MediaPlayer mp = new MediaPlayer();
            	FileInputStream fs;
            	FileDescriptor fd;
            	fs = new FileInputStream(sdDirFiles[y].toString());
            	fd = fs.getFD();
            	mp.setDataSource(fd);
            	mp.prepare(); // might be optional
            	int length = mp.getDuration();
            	mp.release();
//            	
            	//System.out.println("FILE LENGHT COMING AS+++++++++: "+length);
            	
            	int cal_duration_in_sec = length/1000;
            	
            	Integer convt_to_str = new Integer(cal_duration_in_sec);
            	
            	String audio_duration = convt_to_str.toString()+".0"+" sec";
            	
            	arr_duration.add(audio_duration);
            	
            	//System.out.println("DURATION IN SECONDS IS::: "+cal_duration_in_sec);
            	
            	arr_path_uri.add(sdDirFiles[y].toString());
            	
            	//System.out.println("AAAAAAAAAAAAAAAAA  "+sdDirFiles[y].toString().substring(24));
            	
            	arr_title.add(sdDirFiles[y].toString().substring(24));
            	  
//            	Toast.makeText(getBaseContext(), sdDirFiles[y].toString().substring(24), 3000).show();
            	
//               ImageView myImageView = new ImageView(context);
//               myImageView.setImageDrawable(Drawable.createFromPath(singleFile.getAbsolutePath());
//               myImageView.setId(picIndex);
//               picIndex++;
//               drawablesId.add(myImageView.getId());
//               mySDCardImages.add(myImageView);
            }
            
          
      	list_audio.setAdapter(audioListAdapter);
      	
      	
      	if(arr_title.size()<1)
      	{
      		
      		for(int i=0;i<arr_description.size();i++)
      		{
      			dbs.delete("media_list", "description="+"'"+arr_description.get(i)+"'", null);
      			dbs.delete("media_list", "tag="+"'"+arr_tag.get(i)+"'", null);
      			
      		}

      	}
      	
      	
		} catch (Exception e) {
			Toast.makeText(getBaseContext(), "No files exist", Toast.LENGTH_SHORT).show();
		}
	}


	private static class EfficientAdapter extends BaseAdapter {
		// private LayoutInflater li;

		private LayoutInflater vi;

		public EfficientAdapter(Context context) {
			// li = LayoutInflater.from(context);
			vi = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		}

		@Override
		public int getCount() {

			// return arr_name.size()+2;
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

			//Implementing global try catch for Efficient adapter for onResume and ToDo Option
			
//			View v = null;
			ViewHolder holder;
//
			View v = convertView;
			
			try
			{
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
				
				holder.iv.setImageResource(R.drawable.audio_icon);
			}
			catch(Exception e)
			{
				
			}
//			
			
			
			return v;
		}

		static class ViewHolder {
			TextView title,description,duration;
			ImageView iv;
//			TextView title_heading;
//			TextView chore_star_rating, chore_title, chore_bucks,
//					chore_summary, parent_name_val, due_date_text_val, id_val,chore_id_val;
		}
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == btn_add_info)
		{
			Toast.makeText(this, "Adding Info", Toast.LENGTH_SHORT).show();
			Intent i = new Intent(this, AddInfo_record_audio.class);
			startActivityForResult(i,0);
		}
		
		if(v == btn_refresh)
		{
			Toast.makeText(this, "Refreshing", Toast.LENGTH_SHORT).show();
			onResume();
		}
		
		if(v == btn_search)
		{
			Toast.makeText(this, "Searching", Toast.LENGTH_SHORT).show();
			Intent i = new Intent(this, Search_Audio.class);
			startActivity(i);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		onResume();
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent i = new Intent(this, AudioPlay.class);
		String str_send_uri = arr_path_uri.get(arg2);
		Bundle b = new Bundle();
		b.putString("uri", str_send_uri);
		i.putExtras(b);
		startActivityForResult(i,0);
	}
	
}


class MyOpenHelper2 extends SQLiteOpenHelper {

	public MyOpenHelper2(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db
				.execSQL("CREATE TABLE media_list(_id INTEGER PRIMARY KEY AUTOINCREMENT, description TEXT, tag TEXT)");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		System.out.println("database upgraded");
	}

}