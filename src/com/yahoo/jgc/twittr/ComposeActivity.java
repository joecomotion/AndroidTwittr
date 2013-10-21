package com.yahoo.jgc.twittr;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.loopj.android.http.JsonHttpResponseHandler;

public class ComposeActivity extends Activity {
	EditText etCompose;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
		etCompose = (EditText)findViewById(R.id.etCompose);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compose, menu);
		return true;
	}
	
	public boolean onCancel(View v) {
		this.finish();
		return true;
	}
	
	public boolean onTweet(View v) {
		String tweet = etCompose.getText().toString();
		final Activity that = this;
		
		Log.i("info", "Tweet: " + tweet);
		TwittrApp.getRestClient().tweet(tweet, new JsonHttpResponseHandler(){
			public void onFailure(Throwable exception, JSONObject result) {
				Log.e("error", result.toString());
			}
			public void onSuccess(int arg0, JSONObject result){
				Log.i("info", "tweet posted! " +  result.toString());
				that.finish();
			}
		});
		return true;
	}	

}
