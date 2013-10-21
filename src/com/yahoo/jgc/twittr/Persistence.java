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
	static public void save(ArrayList<Tweet> tweets) {
		ArrayList<PersistedTweet> pTweets = new Select().from(PersistedTweet.class)
				             			 	.orderBy("id ASC").execute();
		long latestTwitterId = pTweets.get(0).twitterId,
			 //oldestDBId = pTweets.get(pTweets.size() - 1).twitterId,
			 oldestTwitterId = tweets.get(tweets.size() - 1).getId();
				
		Log.i("info", "latestTwitterId in DB is " + latestTwitterId);
		//Log.i("info", "oldestTwitterId in DB is " + oldestDBId);
		
		ActiveAndroid.beginTransaction();
		
		try {
	        for (Tweet tweet : tweets) {
	        	if (tweet.getId() > latestTwitterId) {
		        	(new PersistedTweet(tweet)).save();
		        	Log.i("info", "Saved tweet " + tweet.getId());
	        	} 
	        }
	         
	        Log.i("info", "Deleting tweets older than " + oldestTwitterId);
	        new Delete().from(PersistedTweet.class).where("twitterId > " + oldestTwitterId);
	        ActiveAndroid.setTransactionSuccessful();
		}
		finally {
			ActiveAndroid.endTransaction();
		}
	}
	
	static public ArrayList<Tweet> load() {
		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
		ArrayList<PersistedTweet> pTweets = new Select().from(PersistedTweet.class).execute();
		for (PersistedTweet pTweet : pTweets){
			if(pTweet.json != null){
				try {
					tweets.add(Tweet.fromJson(new JSONObject(pTweet.json)));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
		}
		
		return tweets;
	}
}
