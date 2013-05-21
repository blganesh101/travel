package com.folklore.service.task;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.webkit.JsPromptResult;

import com.folklore.domain.Library;
import com.folklore.domain.Video;
import com.folklore.util.Log;
import com.folklore.util.StreamUtils;

/**
 * This is the task that will ask YouTube for a list of videos for a specified user</br>
 * This class implements Runnable meaning it will be ran on its own Thread</br>
 * Because it runs on it's own thread we need to pass in an object that is notified when it has finished
 *
 * @author paul.blundell
 */
public class GetYouTubeUserVideosTask implements Runnable {
	// A reference to retrieve the data when this task finishes
	public static final String LIBRARY = "Library";
	// A handler that will be notified when the task is finished
	private final Handler replyTo;
	// The user we are querying on YouTube for videos
	
	
	private int index;
	
	private List<Video> videos;

	/**
	 * Don't forget to call run(); to start this task
	 * @param replyTo - the handler you want to receive the response when this task has finished
	 * @param username - the username of who on YouTube you are browsing
	 */
	public GetYouTubeUserVideosTask(Handler replyTo, int index,List<Video> videos) {
		this.replyTo = replyTo;
		this.index = index;
		this.videos = videos;
	}
	
	public void run() {
		try {
			// Get a httpclient to talk to the internet
			HttpClient client = new DefaultHttpClient();
			// Perform a GET request to YouTube for a JSON list of all the videos by a specific user
			HttpUriRequest request = new HttpGet("https://gdata.youtube.com/feeds/api/playlists/3DC1659FA8788E2A?v=2&alt=jsonc");
			// Get the response that YouTube sends back
			HttpResponse response = client.execute(request);
			// Convert this response into a readable string
			String jsonString = StreamUtils.convertToString(response.getEntity().getContent());
			// Create a JSON object that we can use from the String
			JSONObject json = new JSONObject(jsonString);
			
			// For further information about the syntax of this request and JSON-C
			// see the documentation on YouTube http://code.google.com/apis/youtube/2.0/developers_guide_jsonc.html
			System.out.println("jsonString "+jsonString);
			// Get are search result items
			JSONArray jsonArray = json.getJSONObject("data").getJSONArray("items");
			
			// Create a list to store are videos in
			
			// Loop round our JSON list of videos creating Video objects to use within our app
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i).getJSONObject("video");
				// The title of the video
				
				System.out.println("jsonobj "+jsonObject.toString());
				String title = jsonObject.getString("title");
				// The url link back to YouTube, this checks if it has a mobile url
				// if it doesnt it gets the standard url
				String url;
				try {
					url = jsonObject.getJSONObject("player").getString("mobile");
				} catch (JSONException ignore) {
					url = jsonObject.getJSONObject("player").getString("default");
				}
				
				String duration;
				
				int time = jsonObject.getInt("duration");
				TimeZone tz = TimeZone.getTimeZone("UTC");
			    SimpleDateFormat df = new SimpleDateFormat("mm:ss");
			    df.setTimeZone(tz);
			    duration = df.format(new Date(time*1000));
			    //System.out.println(time);
				//duration = df.format(time);
				
				String user;
				
				try
				{
					user = jsonObject.getString("user");
				}
				catch(JSONException e)
				{
					user = "";
				}
				String description;
				description = jsonObject.getString("description");
				
				int rating;
				try
				{
					rating = jsonObject.getInt("rating");
				}
				catch(JSONException e)
				{
					rating =0;
				}
				
				// A url to the thumbnail image of the video
				// We will use this later to get an image using a Custom ImageView
				// Found here http://blog.blundell-apps.com/imageview-with-loading-spinner/
				String thumbUrl = jsonObject.getJSONObject("thumbnail").getString("sqDefault");
				
				// Create the video object and add it to our list
				videos.add(new Video(title, url, thumbUrl,duration,user,description,rating));
			}
			// Create a library to hold our videos
			Library lib = new Library("folklore", videos);
			// Pack the Library into the bundle to send back to the Activity
			Bundle data = new Bundle();
			data.putSerializable(LIBRARY, lib);
			
			// Send the Bundle of data (our Library) back to the handler (our Activity)
			Message msg = Message.obtain();
			msg.setData(data);
			replyTo.sendMessage(msg);
			
		// We don't do any error catching, just nothing will happen if this task falls over
		// an idea would be to reply to the handler with a different message so your Activity can act accordingly
		} catch (ClientProtocolException e) {
			Log.e("Feck", e);
		} catch (IOException e) {
			Log.e("Feck", e);
		} catch (JSONException e) {
			Log.e("Feck", e);
		}
	}
}