package com.solarprojectapp.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import com.solarprojectapp.R;


public class InformationActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;
    Button skip;
    VideoView videoview;
    boolean canceled = false;
    Uri uri;
    private static int SPLASH_TIME_OUT = 25000;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_information);
        skip = (Button)findViewById(R.id.skip);
        videoview = (VideoView) findViewById(R.id.videoView);

        uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.myway01);

        videoview.setVideoURI(uri);
        videoview.start();


        skip.setOnClickListener(this);
        mContext = InformationActivity.this;

        calltoSplash();

    }


    public void calltoSplash() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e("abhi", "run:outside "+canceled );
                if (!canceled) {
                    Log.e("abhi", "run:inside "+canceled );
                    Intent i = new Intent(InformationActivity.this, SolarProjectLoginActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);
    }
    @Override
    public void onClick(View view) {
        canceled =true;
        Intent i = new Intent(InformationActivity.this, SolarProjectLoginActivity.class);
        startActivity(i);
        finish();
    }
}


