package com.yahoo.jgc.twittr;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.yahoo.jgc.twittr.models.PersistedTweet;
import com.yahoo.jgc.twittr.models.Tweet;

public class Persistence {
	static public void save(ArrayList<Tweet> tweets, Boolean purge) {
		ArrayList<PersistedTweet> pTweets = new Select().from(PersistedTweet.class)
				             			 	.orderBy("twitterId ASC").execute();
		long latestSavedTwitterId = 0;
		
		if(pTweets.size() > 0) {
			latestSavedTwitterId = pTweets.get(0).twitterId;
		}
		
	    //long oldestTwitterId = tweets.get(tweets.size() - 1).getId();
				
		Log.i("info", "latestTwitterId in DB is " + latestSavedTwitterId);
		//Log.i("info", "oldestTwitterId in DB is " + oldestDBId);
		
		ActiveAndroid.beginTransaction();
		
		try {
	        for (Tweet tweet : tweets) {
	        	if (tweet.getId() > latestSavedTwitterId) {
		        	(new PersistedTweet(tweet)).save();
		        	Log.i("info", "Saved tweet " + tweet.getId());
	        	} 
	        }
	         
	        if (purge) {
	        	//Log.i("info", "Deleting tweets older than " + oldestTwitterId);
	        	//new Delete().from(PersistedTweet.class).where("twitterId > " + oldestTwitterId);
	        }	
	        ActiveAndroid.setTransactionSuccessful();
		}
		finally {
			ActiveAndroid.endTransaction();
		}
	}
	
	static public ArrayList<Tweet> load() {
		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
		ArrayList<PersistedTweet> pTweets = new Select().from(PersistedTweet.class)
														.orderBy("twitterId DESC").execute();
		for (PersistedTweet pTweet : pTweets){
			if(pTweet.json != null){
				try {
					Tweet tmp = Tweet.fromJson(new JSONObject(pTweet.json));
					Log.i("info", "loaded tweet id:" + tmp.getId() + " body:" + tmp.getBody());
					tweets.add(tmp);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
		}
		
		return tweets;
	}

	public static void save(Tweet tweet) {
		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
		tweets.add(tweet);
		Persistence.save(tweets, false);
	}
}
