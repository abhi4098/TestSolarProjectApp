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
import android.widget.ImageView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.solarprojectapp.R;


public class InformationActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;
    Button skip;
    ImageView imageView;
    boolean canceled = false;
    Uri uri;
    private static int SPLASH_TIME_OUT = 25000;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_information);
        skip = (Button)findViewById(R.id.skip);
        imageView = (ImageView) findViewById(R.id.imageView);
        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imageView);
        Glide.with(this).load(R.raw.solar).into(imageViewTarget);

      /*  uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.myway);

        videoview.setVideoURI(uri);
        videoview.start();*/


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


