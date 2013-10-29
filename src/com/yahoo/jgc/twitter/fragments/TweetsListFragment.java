package com.yahoo.jgc.twitter.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.yahoo.jgc.twittr.R;
import com.yahoo.jgc.twittr.TweetsAdapter;
import com.yahoo.jgc.twittr.models.Tweet;

public class TweetsListFragment extends Fragment {
	ArrayList<Tweet> tweets;
	TweetsAdapter adapter;
	
	public View onCreateView(LayoutInflater inf, ViewGroup parent, Bundle savedInstanceState){
		return inf.inflate(R.layout.fragment_tweets_list, parent, false);
	}
	
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		tweets = new ArrayList<Tweet>();
		ListView lvTweets = (ListView)getActivity().findViewById(R.id.lvTweets);
		adapter = new TweetsAdapter(getActivity(), tweets);
		lvTweets.setAdapter(adapter);	
	}
	
	public TweetsAdapter getAdapter() {
		return adapter;
	}
}
