package com.mobiapp.rootinterview.activity;

import com.mobiapp.rootinterview.activity.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mobiapp.rootinterview.R;
import com.mobiapp.rootinterview.adapter.ImageLoadAdapter;
import com.mobiapp.rootinterview.common.CheckIntnetConnection;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class InterviewActivity extends Activity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    ListView list;
    ImageLoadAdapter adapter;
    private static final boolean AUTO_HIDE = true;
    Boolean isInternetPresent = false;
    CheckIntnetConnection checkInternet;
    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * If set, will toggle the system UI visibility upon interaction. Otherwise,
     * will show the system UI visibility upon interaction.
     */
    private static final boolean TOGGLE_ON_CLICK = true;

    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */
    private SystemUiHider mSystemUiHider;
    ProgressBar progressbarPG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_interview);

        final View controlsView = findViewById(R.id.fullscreen_content_controls);
        progressbarPG = (ProgressBar) findViewById(R.id.progressBar);
        //Code added by Nayan
        list = (ListView) findViewById(R.id.list);
        // Create custom adapter for listview
        adapter = new ImageLoadAdapter(this, mStrings);
        //Set adapter to listview
        list.setAdapter(adapter);
        Button refreshBTN = (Button) findViewById(R.id.btnrefresh);
        refreshBTN.setOnClickListener(listener);
        Button doneBTN = (Button) findViewById(R.id.btndone);
        doneBTN.setOnClickListener(listener);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }


    Handler mHideHandler = new Handler();
    Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {

            // mSystemUiHider.hide();
        }
    };

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    @Override
    public void onDestroy() {
        // Remove adapter refference from list
        list.setAdapter(null);
        super.onDestroy();
    }

    public OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.btnrefresh:
                    checkInternet = new CheckIntnetConnection(getApplicationContext());
                    isInternetPresent = checkInternet.isConnectingToInternet();
                    if (isInternetPresent) {
                        adapter.imageLoader.clearCache();
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(InterviewActivity.this,
                                "No Internet Connection. Please turn on the connection.",
                                Toast.LENGTH_LONG).show();
                    }
                    break;
                case R.id.btndone:
                    Intent i = new Intent(InterviewActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                    break;
            }
        }
    };

    public void onItemClick(int mPosition) {
        String tempValues = mStrings[mPosition];

        Toast.makeText(InterviewActivity.this,
                "Image URL : " + tempValues,
                Toast.LENGTH_LONG).show();
    }

    // Image urls used in LazyImageLoadAdapter.java file

    private String[] mStrings = {

            "https://s3-us-west-2.amazonaws.com/root-interview/1.jpg",
            "https://s3-us-west-2.amazonaws.com/root-interview/2.jpg",
            "https://s3-us-west-2.amazonaws.com/root-interview/3.jpg",
            "https://s3-us-west-2.amazonaws.com/root-interview/4.jpg",
            "https://s3-us-west-2.amazonaws.com/root-interview/5.jpg"
    };


}
