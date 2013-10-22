package com.yahoo.jgc.twittr;

import java.util.ArrayList;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.yahoo.jgc.twittr.models.Tweet;

public class TimelineActivity extends Activity {
	ArrayList<Tweet> tweets;
	TweetsAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		tweets = new ArrayList<Tweet>();
		ListView lvTweets = (ListView)findViewById(R.id.lvTweets);
		adapter = new TweetsAdapter(getBaseContext(), tweets);
		lvTweets.setAdapter(adapter);	
		refresh();
	}
	
	public void refresh() {
		//will get anything in persistence, even just-composed.
		updateFromPersistence();  
		
		//gets home timeline from svr
		TwittrApp.getRestClient().getHomeTimeline(new JsonHttpResponseHandler(){
			public void onSuccess(JSONArray jsonTweets){
				Persistence.save(Tweet.fromJson(jsonTweets), true);
				updateFromPersistence();
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}
	
	public boolean onComposeClick(MenuItem mi) {
		Intent i = new Intent(getApplicationContext(), ComposeActivity.class);
		startActivityForResult(i, 0);
		return true;
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Boolean didTweet = data.getBooleanExtra("didTweet", true);
		if (!didTweet) {
			return;
		}
		
		Log.i("info", "got new tweet");
		refresh();
	}
	
	public void updateFromPersistence() {
		tweets.clear();
		tweets.addAll(Persistence.load());
		Log.i("info", "Tweets loaded:" + tweets.size());
		if (tweets.size() > 0) {
			Log.i("info", "Newest loaded:" + tweets.get(0).getId() + " " + tweets.get(0).getBody());
		}
		adapter.notifyDataSetChanged();
	}

}
