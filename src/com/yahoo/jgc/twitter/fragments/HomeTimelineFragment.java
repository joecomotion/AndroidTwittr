package com.yahoo.jgc.twitter.fragments;


import org.json.JSONArray;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.yahoo.jgc.twittr.R;
import com.yahoo.jgc.twittr.TwittrApp;
import com.yahoo.jgc.twittr.models.Tweet;

import android.os.Bundle;

public class HomeTimelineFragment extends TweetsListFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		refresh();
	}
	
	public void refresh() {
		
		//will get anything in persistence, even just-composed.
		//updateFromPersistence();  
		
		//gets home timeline from svr
		TwittrApp.getRestClient().getHomeTimeline(new JsonHttpResponseHandler(){
			public void onSuccess(JSONArray jsonTweets){
				//Persistence.save(Tweet.fromJson(jsonTweets), true);
				//updateFromPersistence();
				
				getAdapter().clear();
				getAdapter().addAll(Tweet.fromJson((jsonTweets)));		
			}
		});
		
	}
	
}
