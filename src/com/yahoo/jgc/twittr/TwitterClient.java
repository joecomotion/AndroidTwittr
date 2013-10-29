package com.yahoo.jgc.twittr;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; 
    public static final String REST_URL = "http://api.twitter.com/1.1"; 
    public static final String REST_CONSUMER_KEY = "38Qev1mBSomZ5fSnkzjqg";     
    public static final String REST_CONSUMER_SECRET = "b0nglro1fDvClBRW5BMPfVdGpN8P2dvDTqND56vjKp4"; 
    public static final String REST_CALLBACK_URL = "oauth://twittr"; 
    
    public TwitterClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }
    
    public void getHomeTimeline(AsyncHttpResponseHandler handler) {
        String url = getApiUrl("statuses/home_timeline.json");
        client.get(url, null, handler);
    }

    public void getMentions(AsyncHttpResponseHandler handler) {
        String url = getApiUrl("statuses/mentions_timeline.json");
        client.get(url, null, handler);
    }

    public void getUserTimeline(long user_id, AsyncHttpResponseHandler handler) {
        String url = getApiUrl("statuses/user_timeline.json");
        RequestParams params = null; new RequestParams();
        if (user_id != 0) {
        	params = new RequestParams("user_id", user_id);
        }
        client.get(url, params, handler);
    }
    
    public void getMyInfo(AsyncHttpResponseHandler handler) {
        String url = getApiUrl("account/verify_credentials.json");
        client.get(url, null, handler);
    }
    
    public void getUserInfo(long user_id, AsyncHttpResponseHandler handler) {
        String url = getApiUrl("users/show.json");
        client.get(url, new RequestParams("user_id", user_id), handler);
    }
    
    public void tweet(String tweet, AsyncHttpResponseHandler handler) {
    	String url = getApiUrl("statuses/update.json");
    	RequestParams params = new RequestParams();
    	params.put("status", tweet);
    	client.post(url, params, handler);
    }
    

}