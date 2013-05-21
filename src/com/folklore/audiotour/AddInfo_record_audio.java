package com.folklore.audiotour;

import com.folklore.tourengine.R;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AddInfo_record_audio extends Activity implements OnClickListener{

	EditText edt_title,edt_description,edt_tag;
	
	Button btn_started_recording;
	
//	MyOpenHelper1 mo;
//	SQLiteDatabase dbs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {    
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.addinfo_record_audio);
		
		edt_title = (EditText)findViewById(R.id.editText_title);
		edt_description = (EditText)findViewById(R.id.editText_description);
		edt_tag = (EditText)findViewById(R.id.editText_tag);
		
		btn_started_recording = (Button)findViewById(R.id.button_start_recording);
		
		btn_started_recording.setOnClickListener(this);
		

//		mo = new MyOpenHelper1(this, "mydev.db", null, 1);
//
//		dbs = mo.getWritableDatabase();

		
	
	}


	public void onClick(View v) {
		// TODO Auto-generated method stub
		
//		Media_Audio_List.arr_title.add(edt_title.getText().toString());
		
		ContentValues cv = new ContentValues();
		
		  
//		cv.put("first_name", user.getText().toString());
		cv.put("description", edt_description.getText().toString());
		cv.put("tag", edt_tag.getText().toString());
		
		MediaAudioList md = new MediaAudioList();  
		
		MediaAudioList.dbs.insert("media_list", null, cv);  
		cv=null;  
		MediaAudioList.dbs.close();
//		Media_Audio_List.arr_description.add(edt_description.getText().toString());
//		Media_Audio_List.arr_tag.add(edt_tag.getText().toString());
	Intent i = new Intent(this, AudioRecorder.class);
	Bundle b = new Bundle();
	b.putString("title", edt_title.getText().toString());
	i.putExtras(b);
	startActivityForResult(i,0);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		finish();
	}
}



//class MyOpenHelper1 extends SQLiteOpenHelper {
//
//	public MyOpenHelper1(Context context, String name, CursorFactory factory,
//			int version) {
//		super(context, name, factory, version);
//		// TODO Auto-generated constructor stub
//	}
//
//	public void onCreate(SQLiteDatabase db) {
//		// TODO Auto-generated method stub
//		db
//				.execSQL("CREATE TABLE media_list(_id INTEGER PRIMARY KEY AUTOINCREMENT, description TEXT, tag TEXT)");
//
//	}
//
//	@Override
//	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//		// TODO Auto-generated method stub
//		System.out.println("database upgraded");
//	}
//
//}