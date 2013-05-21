package com.folklore.tourengine;
 
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
 

import com.folklore.domain.Library;
import com.folklore.domain.Video;
import com.folklore.service.task.GetYouTubeUserVideosTask;
import com.folklore.ui.widget.VideoPlayerView;
import com.folklore.ui.widget.VideoWebView;
import com.folklore.ui.widget.VideosListView;
import com.folklore.util.Utility;
import com.google.ads.AdActivity;

/**
 * The Activity can retrieve Videos for a specific username from YouTube</br>
 * It then displays them into a list including the Thumbnail preview and the title</br>
 * There is a reference to each video on YouTube as well but this isn't used in this tutorial</br>
 * </br>
 * <b>Note<b/> orientation change isn't covered in this tutorial, you will want to override
 * onSaveInstanceState() and onRestoreInstanceState() when you come to this
 * </br>
 * @author paul.blundell
 */
public class MainActivity extends Activity{
    // A reference to our list that will hold the video details
    private VideosListView listView;
    
    private boolean loading;
    private int visibleThreshold = 5;
    private int currentPage = 0;
    private int previousTotal = 0;
    // Create a list to store are videos in
    List<Video> videos = new ArrayList<Video>();
 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
          //  WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        
        setContentView(R.layout.videolist);
        
        
 
        listView = (VideosListView) findViewById(R.id.videosListView);
        new Thread(new GetYouTubeUserVideosTask(responseHandler,1,videos)).start();
    }
 
    // This is the XML onClick listener to retreive a users video feed
    public void getUserYouTubeFeed(View v){
        // We start a new task that does its work on its own thread
        // We pass in a handler that will be called when the task has finished
        // We also pass in the name of the user we are searching YouTube for
        new Thread(new GetYouTubeUserVideosTask(responseHandler, 1,videos)).start();
    }
 
    // This is the handler that receives the response when the YouTube task has finished
    Handler responseHandler = new Handler() {
        public void handleMessage(Message msg) {
            populateListWithVideos(msg);
        };
    };
 
    /**
     * This method retrieves the Library of videos from the task and passes them to our ListView
     * @param msg
     */
    private void populateListWithVideos(Message msg) {
        // Retreive the videos are task found from the data bundle sent back
        Library lib = (Library) msg.getData().get(GetYouTubeUserVideosTask.LIBRARY);
        // Because we have created a custom ListView we don't have to worry about setting the adapter in the activity
        // we can just call our custom method with the list of items we want to display
        listView.setVideos(lib.getVideos());
        //listView.setOnScrollListener(new EndlessScrollListener());
        listView.setSelectionFromTop(lib.getVideos().size()-25, 0);
        listView.setOnItemClickListener(new OnItemClickListener() {	
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				Video vid = (Video)arg0.getItemAtPosition(arg2);
				
				System.out.println("clcik"+vid.getUrl());
				/*
				Intent videoIntent = new Intent(getApplicationContext(), VideoPlayerView.class);
				videoIntent.putExtra("url", vid.getUrl());
				videoIntent.putExtra("title", vid.getTitle());
				videoIntent.putExtra("description", vid.getDescription());
				videoIntent.putExtra("rating", vid.getRating());
				videoIntent.putExtra("user", vid.getUser());

				
				startActivity(videoIntent);*/
				
				String url = vid.getUrl();
				url = url.replace("m.", "");
				url = url.replace("details", "watch");
				System.out.println("url:: "+url);
				
				/*
				String url = vid.getUrl();
				url = url.replace("m.", "");
				url = url.replace("details", "watch");
				System.out.println("url:: "+url);
				
				Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(url));
                if(Utility.isAppInstalled("com.google.android.youtube", getApplicationContext())) {
                    intent.setClassName("com.google.android.youtube", "com.google.android.youtube.WatchActivity");
                }
                startActivity(intent);*/
				
                
				Intent videoIntent = new Intent(getApplicationContext(),com.folklore.adstemp.TempAdActivity.class);
				videoIntent.putExtra("url", url);
				
				startActivity(videoIntent);
				
			}
        	
		});
    }
 
    @Override
    protected void onStop() {
        // Make sure we null our handler when the activity has stopped
        // because who cares if we get a callback once the activity has stopped? not me!
        responseHandler = null;
        super.onStop();
    }


	
	public class EndlessScrollListener implements OnScrollListener {
		 
	    private int visibleThreshold = 5;
	    private int currentPage = 0;
	    private int previousTotal = 0;
	    private boolean loading = true;
	 
	    public EndlessScrollListener() {
	    }
	    public EndlessScrollListener(int visibleThreshold) {
	        this.visibleThreshold = visibleThreshold;
	    }
	 
	    @Override
	    public void onScroll(AbsListView view, int firstVisibleItem,
	            int visibleItemCount, int totalItemCount) {
	        if (loading) {
	            if (totalItemCount > previousTotal) {
	                loading = false;
	                previousTotal = totalItemCount;
	                currentPage++;
	            }
	        }
	        System.out.println("visibleItemCount "+visibleItemCount);
	        System.out.println("previousTotal "+previousTotal);
	        System.out.println("currentpage "+currentPage);
	        System.out.println("totalCount "+totalItemCount);
	        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
	            // I load the next page of gigs using a background task,
	            // but you can call any function here.
	        	new Thread(new GetYouTubeUserVideosTask(responseHandler,totalItemCount,videos)).start();
	            loading = true;
	        }
	    }
	 
	    @Override
	    public void onScrollStateChanged(AbsListView view, int scrollState) {
	    }
	}
}