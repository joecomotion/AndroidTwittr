package com.yahoo.jgc.twittr;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yahoo.jgc.twittr.models.Tweet;
import com.yahoo.jgc.twittr.models.User;

public class TweetsAdapter extends ArrayAdapter<Tweet>{
	private OnProfileImageClickedListener listener;

	public TweetsAdapter(Context context, List<Tweet> tweets) {
		super(context, 0, tweets);
		if (context instanceof OnProfileImageClickedListener) {
	        listener = (OnProfileImageClickedListener) context;
	      } else {
	        Log.i("warning", context.toString()
	              + " doesn't implement TweetsAdapter.OnProfileImageClickedListener");
	      }
	}
	
	public interface OnProfileImageClickedListener {
		public void onProfileClick(User u);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    View view = convertView;
	    if (view == null) {
	    	LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    	view = inflater.inflate(R.layout.tweet_item, null);
	    }
	     
        Tweet tweet = getItem(position);
        
        ImageView imageView = (ImageView) view.findViewById(R.id.ivProfile);
        ImageLoader.getInstance().displayImage(tweet.getUser().getProfileImageUrl(), imageView);
        imageView.setTag(tweet);
        imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Tweet t = (Tweet)v.getTag();
				Log.i("info", "got click for tweet " + t.getUser().getName());
				if (listener != null) {
					listener.onProfileClick(t.getUser());
				}
			}
        	
        });
        
        TextView nameView = (TextView) view.findViewById(R.id.tvName);
        String formattedName = "<b>" + tweet.getUser().getName() + "</b>" + " <small><font color='#777777'>@" +
                tweet.getUser().getScreenName() + "</font></small>";
        nameView.setText(Html.fromHtml(formattedName));

        TextView bodyView = (TextView) view.findViewById(R.id.tvBody);
        bodyView.setText(Html.fromHtml(tweet.getBody()));
        
        return view;
	}	

}
