package com.yahoo.jgc.twittr.models;

import org.json.JSONObject;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name="PersistedTweet")
public class PersistedTweet extends Model{
	@Column(name="twitterId")
	public long twitterId;
	
	@Column(name="json")
	public String json;
	
	public PersistedTweet() {
		
	}
	
	public PersistedTweet(Tweet tweet){
		twitterId = tweet.getId();
		json = tweet.jsonObject.toString();
	}
}
