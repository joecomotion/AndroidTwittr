package com.yahoo.jgc.twitter.fragments;

import org.json.JSONArray;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.yahoo.jgc.twittr.TwittrApp;
import com.yahoo.jgc.twittr.models.Tweet;

import android.os.Bundle;

public class MentionsFragment extends TweetsListFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		refresh();
	}
	
	public void refresh() {

		TwittrApp.getRestClient().getMentions(new JsonHttpResponseHandler(){
			public void onSuccess(JSONArray jsonTweets){
				getAdapter().clear();
				getAdapter().addAll(Tweet.fromJson((jsonTweets)));		
			}
		});
		
	}
	
	
}
