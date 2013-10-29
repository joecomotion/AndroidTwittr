package com.yahoo.jgc.twittr;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.yahoo.jgc.twitter.fragments.HomeTimelineFragment;
import com.yahoo.jgc.twitter.fragments.MentionsFragment;
import com.yahoo.jgc.twittr.models.Tweet;

public class TimelineActivity extends FragmentActivity implements TabListener {
	HomeTimelineFragment homeTimelineFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		setupNavigationTabs();
	}

	private void setupNavigationTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);
		Tab tabHome = actionBar.newTab().setText("home")
				.setTag("HomeTimelineFragment")
				.setIcon(R.drawable.ic_home)
				.setTabListener(this);

		Tab tabMentions = actionBar.newTab().setText("mentions")
                .setTag("MentionsFragment")
                .setIcon(R.drawable.ic_mentions)
                .setTabListener(this);
		actionBar.addTab(tabHome);
		actionBar.addTab(tabMentions);
		actionBar.selectTab(tabHome);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}
	
	public boolean onComposeClick(MenuItem mi) {
		Intent i = new Intent(getApplicationContext(), ComposeActivity.class);
		startActivityForResult(i, 0);
		return true;
	}
	
	public boolean onRefreshClick(MenuItem mi) {
		Log.i("info", "onRefreshClick");
		//refresh();
		return true;
	}
	
	public void onProfileClick(MenuItem mi) {
		Intent i = new Intent(this, ProfileActivity.class);
		startActivity(i);
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		String jsonTweet = data.getStringExtra("tweet");
		if (jsonTweet == null) {
			return;
		}
		
		Log.i("info", "got new tweet");
		
		homeTimelineFragment.cacheNewTweet(Tweet.fromJsonString(jsonTweet));
	}
	
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		FragmentManager manager = getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction fts = manager.beginTransaction();
		if (tab.getTag() == "HomeTimelineFragment") {
			homeTimelineFragment = new HomeTimelineFragment();
			fts.replace(R.id.frameContainer, homeTimelineFragment);
		}
		else {
			fts.replace(R.id.frameContainer, new MentionsFragment());
		}
		fts.commit();
		
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

}
