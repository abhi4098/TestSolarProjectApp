package com.solarprojectapp.ui.activities;

import android.content.Intent;
import android.os.Bundle;
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
import com.solarprojectapp.api.ApiAdapter;
import com.solarprojectapp.api.RetrofitInterface;
import com.solarprojectapp.generated.model.LoginResponse;
import com.solarprojectapp.ui.fragments.ProfileCustomerPageFragment;
import com.solarprojectapp.ui.fragments.ProfileHomePageFragment;
import com.solarprojectapp.ui.fragments.ProfileTechnicalFragment;
import com.solarprojectapp.utils.LoadingDialog;
import com.solarprojectapp.utils.NetworkUtils;
import com.solarprojectapp.utils.PrefUtils;
import com.solarprojectapp.utils.SnakBarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.solarprojectapp.api.ApiEndPoints.MAIN_BASE_URL;

public class NavigationalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String loginType;
    private RetrofitInterface.UserLoginClient UserLoginAdapter;

    Fragment profileHomePageFragment,profileCustomerFragment,profileTechnicalFragment;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.back_icon)
    ImageView ivBackIcon;
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
        ivBackIcon.setVisibility(View.INVISIBLE);
        loginType= getIntent().getStringExtra("LOGIN_TYPE");
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
        setUpRestAdapter();
    }


    public void setFragment() {
        if (loginType.equals("Admin")) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            profileHomePageFragment = new ProfileHomePageFragment();
            fragmentTransaction.add(R.id.fragment_container, profileHomePageFragment, "PROFILE").addToBackStack(null);
            fragmentTransaction.commit();
        }
        else if (loginType.equals("End Consumer"))
        {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            profileCustomerFragment = new ProfileCustomerPageFragment();
            fragmentTransaction.add(R.id.fragment_container, profileCustomerFragment, "PROFILE").addToBackStack(null);
            fragmentTransaction.commit();
        }
        else if (loginType.equals("Client"))
        {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            profileCustomerFragment = new ProfileCustomerPageFragment();
            fragmentTransaction.add(R.id.fragment_container, profileCustomerFragment, "PROFILE").addToBackStack(null);
            fragmentTransaction.commit();
        }
        else if (loginType.equals("Technical Partner"))
        {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            profileCustomerFragment = new ProfileCustomerPageFragment();
            fragmentTransaction.add(R.id.fragment_container, profileCustomerFragment, "PROFILE").addToBackStack(null);
            fragmentTransaction.commit();
        }
        else
        {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            profileTechnicalFragment = new ProfileTechnicalFragment();
            fragmentTransaction.add(R.id.fragment_container, profileTechnicalFragment, "PROFILE").addToBackStack(null);
            fragmentTransaction.commit();
        }
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
            if (loginType.equals("Admin")) {

                fragment = new ProfileHomePageFragment();
            }
            else if (loginType.equals("Customer"))
            {
                fragment = new ProfileCustomerPageFragment();
            }
            else
            {
                fragment = new ProfileTechnicalFragment();
            }
            tvAppTitle.setText("DASHBOARD");

        } else if (id == R.id.nav_profile) {

            Intent i = new Intent(NavigationalActivity.this, MyProfileActivity.class);
            startActivity(i);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);


        } else if (id == R.id.nav_logout) {
            logoutSolarApp();

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

    private void setUpRestAdapter() {
        UserLoginAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.UserLoginClient.class, MAIN_BASE_URL, this);

    }

    private void logoutSolarApp() {
        LoadingDialog.showLoadingDialog(this,"Loading...");
        Call<LoginResponse> call = UserLoginAdapter.userLogIn( PrefUtils.getUserName(NavigationalActivity.this),PrefUtils.getUserPasspord(NavigationalActivity.this),"memberlogout");
        if (NetworkUtils.isNetworkConnected(this)) {
            call.enqueue(new Callback<LoginResponse>() {

                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                    if (response.isSuccessful()) {
                        LoadingDialog.cancelLoading();

                        Log.e("abhi", "onResponse: " +response.body().getMsg());
                        PrefUtils.storeUserLoggedIn(false, NavigationalActivity.this);
                        Intent intent = new Intent(getApplicationContext(), SolarProjectLoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Log.e("abhi", "onFailure: ---------------" +t.getLocalizedMessage());
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(this);
        }
    }

    private void setUserLoggedIn() {
        PrefUtils.storeUserLoggedIn(true, this);
    }
}
