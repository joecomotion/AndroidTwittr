package com.yahoo.jgc.twitter.fragments;

import org.json.JSONArray;

import android.os.Bundle;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.yahoo.jgc.twittr.TwittrApp;
import com.yahoo.jgc.twittr.models.Tweet;

public class UserTimelineFragment extends TweetsListFragment {
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		TwittrApp.getRestClient().getUserTimeline(0, makeResponseHandler());
		
	}
	
	private JsonHttpResponseHandler makeResponseHandler() {
		return new JsonHttpResponseHandler(){
			public void onSuccess(JSONArray jsonTweets){
				getAdapter().clear();
				getAdapter().addAll(Tweet.fromJson((jsonTweets)));		
			}
		};
	}
}
