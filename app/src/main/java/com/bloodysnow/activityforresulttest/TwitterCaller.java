package com.bloodysnow.activityforresulttest;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

public class TwitterCaller {
    public interface Callback {
        public void onResult(int resultCode);
    }

    public static final String ACTION_TWEET_SUCCESS = TwitterCaller.class.getName() + ".SUCCESS";
    public static final String ACTION_TWEET_CANCEL = TwitterCaller.class.getName() + ".CANCEL";

    public void tweet(Context context, String text, final Callback callback) {
        final BroadcastReceiver tweetBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                LocalBroadcastManager.getInstance(context).unregisterReceiver(this);
                callback.onResult(intent.getAction().equals(ACTION_TWEET_SUCCESS) ? Activity.RESULT_OK : Activity.RESULT_CANCELED);
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_TWEET_SUCCESS);
        intentFilter.addAction(ACTION_TWEET_CANCEL);
        LocalBroadcastManager.getInstance(context).registerReceiver(tweetBroadcastReceiver, intentFilter);
        Intent intent = new Intent(context, TweetActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        context.startActivity(intent);
    }
}