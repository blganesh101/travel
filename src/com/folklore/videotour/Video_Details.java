package com.folklore.videotour;

import com.folklore.tourengine.R;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Video_Details extends Activity implements OnClickListener{

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		edt_description.setText("");
		
		edt_tag.setText("");
		edt_title.setText("");
		
	}

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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
//		Media_Audio_List.arr_title.add(edt_title.getText().toString());
		
		if(edt_description.getText().toString().length()>0 && edt_title.getText().toString().length()>0
				&& edt_tag.getText().toString().length()>0)
		{
			
		
			
		ContentValues cv = new ContentValues();
		
		  

		cv.put("vdescription", edt_description.getText().toString());
		cv.put("vtag", edt_tag.getText().toString());
		
		VideodemoActivity md = new VideodemoActivity();  
		
		VideodemoActivity.dbs.insert("video_list", null, cv);  
		cv=null;  
		VideodemoActivity.dbs.close();

		
		Intent i=new Intent(this,AndroidVideoCapture.class);
		Bundle b = new Bundle();
		b.putString("title", edt_title.getText().toString());
		i.putExtras(b);
		startActivity(i);
		finish();
	
	
		}
		else
		{
			Toast.makeText(getBaseContext(), "Please fill all the details properly!!", 3000).show();
				
		}
	}

}