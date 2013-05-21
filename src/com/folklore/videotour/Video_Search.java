package com.folklore.videotour;

import com.folklore.tourengine.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class Video_Search extends Activity implements OnItemClickListener {
	
	AutoCompleteTextView search;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		setTheme(android.R.style.Theme_Light);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		
		search = (AutoCompleteTextView)findViewById(R.id.editText_search);
		search.setOnItemClickListener(this);
		search.setTextColor(Color.BLACK);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		search.setAdapter(new ArrayAdapter<String>(getBaseContext(),
				android.R.layout.simple_list_item_1, VideodemoActivity.arr_title));
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent i = new Intent(this, ViewVideo.class);
		Bundle b = new Bundle();
		
		
		b.putString("uri", VideodemoActivity.arr_path_uri.get(arg2));
		i.putExtras(b);
		startActivity(i);
	}

}
