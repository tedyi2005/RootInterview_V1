package com.mobiapp.rootinterview.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.mobiapp.rootinterview.R;
import com.mobiapp.rootinterview.common.CheckIntnetConnection;
import com.mobiapp.rootinterview.model.DrawerItem;

import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {
    private static String TAG = MainActivity.class.getSimpleName();
    List<DrawerItem> dataList;
    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    SharedPreferences pref;
    Boolean isInternetPresent = false;
    String user, username;
    // Other class variable declaration - Session Manager Class.
    CheckIntnetConnection checkInternet;
    // used to store app title
    private CharSequence mTitle;
    // nav drawer title
    private CharSequence mDrawerTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTitle = mDrawerTitle = getTitle();
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setIcon(R.drawable.ic_logo);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        // display the first navigation drawer view on app launch
        displayView(0);

        // check internet is present or not
        checkInternet = new CheckIntnetConnection(getApplicationContext());
        isInternetPresent = checkInternet.isConnectingToInternet();

        // check for Internet status
        if (isInternetPresent) {
            // Toast.makeText(this, "Internet Connected",
            // Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this,
                    "No Internet Connection. Please turn on the connection.",
                    Toast.LENGTH_LONG).show();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_delitems) {
            Toast.makeText(getApplicationContext(), "Delivery items action is selected!", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.action_notification) {
            Toast.makeText(getApplicationContext(), "Notifications action is selected!", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.action_search) {
            Toast.makeText(getApplicationContext(), "Search action is selected!", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == R.id.action_scan) {
            Toast.makeText(getApplicationContext(), "Scan action is selected!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:

                fragment = new HomeFragment();
                title = getString(R.string.title_home);
                break;
            case 1:
                /*fragment = new FriendsFragment();
                title = getString(R.string.title_friends);*/
                isInternetPresent = checkInternet.isConnectingToInternet();
                if (isInternetPresent) {
                    // Toast.makeText(this, "Internet Connected",
                    // Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(this,InterviewActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(this,
                            "No Internet Connection. Please turn on the connection.",
                            Toast.LENGTH_LONG).show();
                }
                break;
            case 2:
                fragment = new MessagesFragment();
                title = getString(R.string.title_messages);
                break;

            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }
}