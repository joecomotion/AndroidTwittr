package com.yahoo.jgc.twittr;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yahoo.jgc.twittr.models.User;

public class ProfileActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		long userId = getIntent().getLongExtra("id", 0);
		if (userId == 0) {
			loadMyProfileInfo();
		} 
		else { 
			loadOtherProfileInfo(userId);	
		} 
	}

	private void handleClientResponse(JSONObject json){
		User u = User.fromJson(json);
		getActionBar().setTitle("@" + u.getScreenName());
		populateProfileHeader(u);
	}
	
	private void loadMyProfileInfo() {
		TwittrApp.getRestClient().getMyInfo(new JsonHttpResponseHandler(){
			public void onSuccess(JSONObject json){
				handleClientResponse(json);
			}
		});
	}

	private void loadOtherProfileInfo(long userId) {
		TwittrApp.getRestClient().getUserInfo(userId, new JsonHttpResponseHandler(){
			public void onSuccess(JSONObject json){
				handleClientResponse(json);
			}
		});
	}
	
	private void populateProfileHeader(User u) {
		TextView tvName = (TextView) findViewById(R.id.tvName);
		TextView tvTagLine = (TextView) findViewById(R.id.tvTagline);
		TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
		TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);	
		ImageView ivProfile = (ImageView) findViewById(R.id.ivProfileImage);
		
		tvName.setText(u.getName());
		tvTagLine.setText(u.getTagLine());
		tvFollowers.setText(u.getFollowersCount() + " followers");
		tvFollowing.setText(u.getFriendsCount() + " friends");
		ImageLoader.getInstance().displayImage(u.getProfileImageUrl(), ivProfile);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}

}
