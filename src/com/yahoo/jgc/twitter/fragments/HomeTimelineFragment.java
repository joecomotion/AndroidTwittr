package com.yahoo.jgc.twitter.fragments;


import java.util.ArrayList;

import org.json.JSONArray;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.yahoo.jgc.twittr.Persistence;
import com.yahoo.jgc.twittr.R;
import com.yahoo.jgc.twittr.TwittrApp;
import com.yahoo.jgc.twittr.models.PersistedTweet;
import com.yahoo.jgc.twittr.models.Tweet;

import android.os.Bundle;
import android.util.Log;

public class HomeTimelineFragment extends TweetsListFragment {
    //for locally-composed tweets
	ArrayList<Tweet> cache = new ArrayList<Tweet>();
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		refresh();
	}
	
	public void cacheNewTweet(Tweet tweet) {
		cache.add(tweet);
		refresh();
	}
	
	public void refresh() {
		
		//will get anything in persistence, even just-composed.
		//updateFromPersistence();  
		
		//gets home timeline from svr
		TwittrApp.getRestClient().getHomeTimeline(new JsonHttpResponseHandler(){
			public void onSuccess(JSONArray jsonTweets){
				//Disabling persistence and we'll just cache locally until I have time to debug persistence.
				
				//Persistence.save(Tweet.fromJson(jsonTweets), true);
				//updateFromPersistence();
				
				ArrayList <Tweet> tweets = Tweet.fromJson(jsonTweets);
				long latestIdFromSvr = tweets.get(0).getId();
				for (Tweet tweet : cache) {
		        	if (tweet.getId() > latestIdFromSvr) {
			        	tweets.add(0, tweet);
		        	} 
		        }
				//TODO:  cache purging
				getAdapter().clear();
				getAdapter().addAll(tweets);		
			}
		});
		
	}
	
	public void updateFromPersistence() {
		ArrayList<Tweet> tweets = Persistence.load();
		getAdapter().clear();
		getAdapter().addAll(tweets);		
	
		Log.i("info", "Tweets loaded:" + tweets.size());
		if (tweets.size() > 0) {
			Log.i("info", "Newest loaded:" + tweets.get(0).getId() + " " + tweets.get(0).getBody());
		}
	}
	
}
