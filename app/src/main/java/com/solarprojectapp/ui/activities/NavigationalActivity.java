package com.solarprojectapp.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.solarprojectapp.R;
import com.solarprojectapp.ui.fragments.MyProfileFragment;
import com.solarprojectapp.ui.fragments.ProfileHomePageFragment;
import com.solarprojectapp.utils.PrefUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NavigationalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Fragment profileHomePageFragment;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    /*@BindView(R.id.back_icon)
    ImageView ivBackIcon;*/
    NavigationView navigationView;

    @BindView(R.id.tv_app_title)
    TextView tvAppTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigational);
        ButterKnife.bind(this);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
        setFragment();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
        setUserLoggedIn();
    }


    public void setFragment() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        profileHomePageFragment = new ProfileHomePageFragment();
        fragmentTransaction.add(R.id.fragment_container, profileHomePageFragment, "PROFILE").addToBackStack(null);
        fragmentTransaction.commit();
        tvAppTitle.setText("DASHBOARD");



    }

    @Override
    protected void onResume() {
        super.onResume();
        navigationView.getMenu().getItem(0).setChecked(true);
        Log.e("abhi", "onResume: ....................." );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigational, menu);
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

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Fragment fragment = null;
        int id = item.getItemId();

        if (id == R.id.nav_dashboard) {
            fragment = new ProfileHomePageFragment();
            //ivBackIcon.setVisibility(View.INVISIBLE);
            tvAppTitle.setText(item.getTitle());
        } else if (id == R.id.nav_profile) {

            Intent i = new Intent(NavigationalActivity.this, MyProfileActivity.class);
            startActivity(i);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);


        } else if (id == R.id.nav_logout) {
            callRestart();

        } /*else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);

        }
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }  else if (getFragmentManager().getBackStackEntryCount() == 0)
        {

            MenuItem itemid = navigationView.getMenu().findItem(R.id.nav_dashboard);
            if (getFragmentManager().findFragmentById(R.id.fragment_container) == null) {
                onNavigationItemSelected(itemid);
            }
            Log.e("abhi", "onBackPressed: "+getFragmentManager().getBackStackEntryCount() );
            tvAppTitle.setText("DASHBOARD");
            super.onBackPressed();


        }
        else
        {
            Log.e("abhi", "onBackPressed:else "+getFragmentManager().getBackStackEntryCount() );
            getFragmentManager().popBackStack();

        }
    }

    private void callRestart() {

        PrefUtils.storeUserLoggedIn(false, this);
        Intent intent = new Intent(getApplicationContext(), SolarProjectLoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void setUserLoggedIn() {
        PrefUtils.storeUserLoggedIn(true, this);
    }
}
