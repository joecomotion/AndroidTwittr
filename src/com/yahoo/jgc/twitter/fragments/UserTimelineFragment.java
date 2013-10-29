package com.yahoo.jgc.twitter.fragments;

import org.json.JSONArray;

import android.os.Bundle;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.yahoo.jgc.twittr.TwittrApp;
import com.yahoo.jgc.twittr.models.Tweet;

public class UserTimelineFragment extends TweetsListFragment {
	
	public static UserTimelineFragment newInstance(long userId) {
		UserTimelineFragment fragment = new UserTimelineFragment();
        Bundle args = new Bundle();
        args.putLong("user_id", userId);
        fragment.setArguments(args);
        return fragment;
    }
	
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		long userId = getArguments().getLong("user_id");
		TwittrApp.getRestClient().getUserTimeline(userId, makeResponseHandler());
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
