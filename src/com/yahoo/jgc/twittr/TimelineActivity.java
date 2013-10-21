package com.yahoo.jgc.twittr;

import java.util.ArrayList;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.yahoo.jgc.twittr.models.Tweet;

public class TimelineActivity extends Activity {
	ArrayList<Tweet> tweets;
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);

		updateFromPersistence();
		
		TwittrApp.getRestClient().getHomeTimeline(new JsonHttpResponseHandler(){
			public void onSuccess(JSONArray jsonTweets){
				Persistence.save(Tweet.fromJson(jsonTweets));
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
		startActivity(i);
		return true;
	}
	
	public void updateFromPersistence() {
		tweets = Persistence.load();
		ListView lvTweets = (ListView)findViewById(R.id.lvTweets);
		TweetsAdapter adapter = new TweetsAdapter(getBaseContext(), tweets);
		lvTweets.setAdapter(adapter);	
	}

}
