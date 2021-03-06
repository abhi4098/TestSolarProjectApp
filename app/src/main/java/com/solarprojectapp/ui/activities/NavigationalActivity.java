package com.solarprojectapp.ui.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.internal.NavigationMenuView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.iid.FirebaseInstanceId;
import com.solarprojectapp.R;
import com.solarprojectapp.api.ApiAdapter;
import com.solarprojectapp.api.RetrofitInterface;
import com.solarprojectapp.generated.model.LoginResponse;
import com.solarprojectapp.generated.model.ProfileResponse;
import com.solarprojectapp.generated.model.SendTokenToServerResponse;
import com.solarprojectapp.ui.fragments.ProfileCustomerPageFragment;
import com.solarprojectapp.ui.fragments.ProfileHomePageFragment;
import com.solarprojectapp.ui.fragments.ProfileTechnicalFragment;
import com.solarprojectapp.utils.LoadingDialog;
import com.solarprojectapp.utils.NetworkUtils;
import com.solarprojectapp.utils.PrefUtils;
import com.solarprojectapp.utils.SnakBarUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.solarprojectapp.api.ApiEndPoints.BASE_URL_FOR_IMAGE;
import static com.solarprojectapp.api.ApiEndPoints.MAIN_BASE_URL;

public class NavigationalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String loginType;
    private String profilePicUrl;
    private RetrofitInterface.UserLoginClient UserLoginAdapter;
    private RetrofitInterface.UserProfileClient UserProfileAdapter;
    private RetrofitInterface.SendTokenToServerClient SendTokenAdapter;
    de.hdodenhof.circleimageview.CircleImageView personImage;
    String tokenId;

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
        ivBackIcon.setVisibility(View.INVISIBLE);
        loginType= getIntent().getStringExtra("LOGIN_TYPE");


        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(false);

        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        toggle.setHomeAsUpIndicator(R.drawable.hamburger_new);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
        personImage = (de.hdodenhof.circleimageview.CircleImageView)navigationView.getHeaderView(0).findViewById(R.id.person_image);
        tokenId = FirebaseInstanceId.getInstance().getToken();
        Log.e("abhi", "onCreate:................. "+tokenId );
        setUserLoggedIn();
        setUpRestAdapter();

        setHeaderData();
        sendFirebaseTokenToServer();
    }

    private void setHeaderData() {

        if (PrefUtils.getUserImage(NavigationalActivity.this) !=null) {
            getProfileDetails();
        }
    }


    public void setFragment() {
        if (loginType.equals("Admin")) {
            PrefUtils.storeUserFrag("Admin",getBaseContext());
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            profileHomePageFragment = new ProfileHomePageFragment();
            fragmentTransaction.add(R.id.fragment_container, profileHomePageFragment, "PROFILE").addToBackStack(null);
            fragmentTransaction.commit();
        }
        else if (loginType.equals("End Consumer"))
        {
            PrefUtils.storeUserFrag("End Consumer",getBaseContext());
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            profileCustomerFragment = new ProfileCustomerPageFragment();
            fragmentTransaction.add(R.id.fragment_container, profileCustomerFragment, "PROFILE").addToBackStack(null);
            fragmentTransaction.commit();
        }
        else if (loginType.equals("Client"))
        {
            PrefUtils.storeUserFrag("Client",getBaseContext());
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            profileHomePageFragment = new ProfileHomePageFragment();
            fragmentTransaction.add(R.id.fragment_container, profileHomePageFragment, "PROFILE").addToBackStack(null);
            fragmentTransaction.commit();
        }
        else if (loginType.equals("Technical Partner"))
        {
            PrefUtils.storeUserFrag("Technical Partner",getBaseContext());
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            profileCustomerFragment = new ProfileCustomerPageFragment();
            fragmentTransaction.add(R.id.fragment_container, profileCustomerFragment, "PROFILE").addToBackStack(null);
            fragmentTransaction.commit();
        }
        else
        {
            PrefUtils.storeUserFrag(loginType,getBaseContext());
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            profileHomePageFragment = new ProfileHomePageFragment();
            fragmentTransaction.add(R.id.fragment_container, profileHomePageFragment, "PROFILE").addToBackStack(null);
            fragmentTransaction.commit();
        }
        tvAppTitle.setText("DASHBOARD");



    }

    @Override
    protected void onResume() {
        super.onResume();
        navigationView.getMenu().getItem(0).setChecked(true);

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
            else if (loginType.equals("End Consumer"))
            {
                fragment = new ProfileCustomerPageFragment();
            }
            else if (loginType.equals("Technical Partner"))
            {
                fragment = new ProfileCustomerPageFragment();
            }else if (loginType.equals("Client"))
            {
                fragment = new ProfileHomePageFragment();
            }
            else
            {
                fragment = new ProfileTechnicalFragment();
            }
            tvAppTitle.setText( "DASHBOARD");

        } else if (id == R.id.nav_profile) {

            Intent i = new Intent(NavigationalActivity.this, MyProfileActivity.class);
            startActivity(i);


        } else if (id == R.id.nav_logout) {
            logoutSolarApp();

        }

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
               //onNavigationItemSelected(itemid);
                           Log.e("abhi123", " inside null" );

               openExitAppDialog();

            }
            Log.e("abhi123", " outside null" );
           tvAppTitle.setText("DASHBOARD");
           // super.onBackPressed();


        }
        else
        {
            Log.e("abhi", "onBackPressed:else "+getFragmentManager().getBackStackEntryCount() );
            getFragmentManager().popBackStack();

        }
    }

    private void openExitAppDialog() {
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setCancelable(false);
        ab.setTitle("Exit App?");
        ab.setMessage("Are you sure you want to exit?");
        ab.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //if you want to kill app . from other then your main avtivity.(Launcher)
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);

                //if you want to finish just current activity

                NavigationalActivity.this.finish();
            }
        });
        ab.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        ab.show();
    }

    private void setUpRestAdapter() {
        UserLoginAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.UserLoginClient.class, MAIN_BASE_URL, this);
        UserProfileAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.UserProfileClient.class, MAIN_BASE_URL, this);
        SendTokenAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.SendTokenToServerClient.class, MAIN_BASE_URL, this);

    }


    private void sendFirebaseTokenToServer() {
        LoadingDialog.showLoadingDialog(this,"Loading...");
        Call<SendTokenToServerResponse> call = SendTokenAdapter.sendTokenToServer( PrefUtils.getUserId(NavigationalActivity.this),tokenId,"addtokenid");
        if (NetworkUtils.isNetworkConnected(this)) {
            call.enqueue(new Callback<SendTokenToServerResponse>() {

                @Override
                public void onResponse(Call<SendTokenToServerResponse> call, Response<SendTokenToServerResponse> response) {

                    if (response.isSuccessful()) {

                        if (response.body().getSuccess().equals("true")) {
                            Log.e("abhi", "onResponse: ..............."+tokenId );
                            //Toast.makeText(getApplicationContext(),response.body().getTokenId(),Toast.LENGTH_SHORT).show();
                            LoadingDialog.cancelLoading();
                        }
                        else
                        {
                            LoadingDialog.cancelLoading();

                           // Toast.makeText(getApplicationContext(),response.body().getTokenId(),Toast.LENGTH_SHORT).show();
                        }



                    }

                }

                @Override
                public void onFailure(Call<SendTokenToServerResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"Error occur",Toast.LENGTH_SHORT).show();
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(this);
        }
    }

    public static String parseTodaysDate(String time) {



        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        String outputPattern = "dd-MM-yyyy";

        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);

            Log.i("mini", "Converted Date Today:" + str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    private void getProfileDetails() {
        LoadingDialog.showLoadingDialog(this,"Loading...");
        Call<ProfileResponse> call = UserProfileAdapter.userProfile( PrefUtils.getUserName(NavigationalActivity.this),PrefUtils.getUserType(NavigationalActivity.this),PrefUtils.getFkId(NavigationalActivity.this),"userprofiledetails");
        if (NetworkUtils.isNetworkConnected(this)) {
            call.enqueue(new Callback<ProfileResponse>() {

                @Override
                public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {

                    if (response.isSuccessful()) {

                        if (response.body().getSuccess().equals("true")) {
                            for (int i = 0; i < response.body().getProfileDetailsData().size(); i++) {

                                PrefUtils.storeProject(response.body().getProfileDetailsData().get(i).get(i).getProjectName(),NavigationalActivity.this);
                                PrefUtils.storeProjectOwner(response.body().getProfileDetailsData().get(i).get(i).getProjectOwner(),NavigationalActivity.this);
                                PrefUtils.storeName(response.body().getProfileDetailsData().get(i).get(i).getName(),NavigationalActivity.this);

                                Log.e("abhi", "onResponse: project owner name..........."+ PrefUtils.getProjectOwner(NavigationalActivity.this) );
                                if (response.body().getProfileDetailsData().get(i).get(i).getDateOfCommission() !=null) {
                                    PrefUtils.storeCommisionDate(parseTodaysDate(response.body().getProfileDetailsData().get(i).get(i).getDateOfCommission()), NavigationalActivity.this);
                                }
                                if (response.body().getProfileDetailsData().get(i).get(i).getOMStartedOn() !=null) {
                                    PrefUtils.storeOM(parseTodaysDate(response.body().getProfileDetailsData().get(i).get(i).getOMStartedOn()), NavigationalActivity.this);
                                }
                                PrefUtils.storeOtherDetails(response.body().getProfileDetailsData().get(i).get(i).getOtherDetails(),NavigationalActivity.this);
                                setFragment();


                                if (response.body().getProfileDetailsData().get(i).get(i).getImage() != null) {
                                    profilePicUrl = response.body().getProfileDetailsData().get(i).get(i).getImage() ;
                                    String profilePictureUrlComplete = BASE_URL_FOR_IMAGE + profilePicUrl;
                                    PrefUtils.storeUserImage(profilePictureUrlComplete,NavigationalActivity.this);
                                    PrefUtils.storeUserId(response.body().getProfileDetailsData().get(i).get(i).getUserId(),NavigationalActivity.this);

                                    Log.e("abhi", "onResponse: image link............"+ response.body().getProfileDetailsData().get(i).get(i).getUserId());
                                    setProfilePicURL(profilePictureUrlComplete);
                                }
                                else {
                                    personImage.setBackgroundResource(R.drawable.solor_profile);
                                }
                            }

                            LoadingDialog.cancelLoading();
                        }
                        else
                        {
                            setFragment();
                            LoadingDialog.cancelLoading();

                            Toast.makeText(getApplicationContext(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
                        }



                    }

                }

                @Override
                public void onFailure(Call<ProfileResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"Error occur",Toast.LENGTH_SHORT).show();
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(this);
        }
    }

    private void setProfilePicURL(String profilepicUrlComplete) {
        Glide.with(this).load(profilepicUrlComplete).asBitmap().centerCrop().dontAnimate().dontTransform().listener(new RequestListener<String, Bitmap>() {
            @Override
            public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                // imageProgressBar.setVisibility(View.GONE);
                personImage.setBackgroundResource(R.drawable.solor_profile);
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                //imageProgressBar.setVisibility(View.GONE);
                personImage.setBackgroundResource(R.drawable.solor_profile);
                return false;
            }
        })
                .into(new BitmapImageViewTarget(personImage) {
                    @Override
                    protected void setResource(Bitmap bitmap) {
                        Bitmap output;

                        if (bitmap.getWidth() > bitmap.getHeight()) {
                            output = Bitmap.createBitmap(bitmap.getHeight(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
                        } else {
                            output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getWidth(), Bitmap.Config.ARGB_8888);
                        }

                        Canvas canvas = new Canvas(output);

                        final int color = 0xff424242;
                        final Paint paint = new Paint();
                        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

                        float r = 0;

                        if (bitmap.getWidth() > bitmap.getHeight()) {
                            r = bitmap.getHeight() / 2;
                        } else {
                            r = bitmap.getWidth() / 2;
                        }

                        paint.setAntiAlias(true);
                        canvas.drawARGB(0, 0, 0, 0);
                        paint.setColor(color);
                        canvas.drawCircle(r, r, r, paint);
                        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                        canvas.drawBitmap(bitmap, rect, rect, paint);
                      //  personImage.setImageBitmap(output);
                        personImage.setBackgroundResource(R.drawable.solor_profile);

                        // imageProgressBar.setVisibility(View.GONE);

                    }
                });
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
