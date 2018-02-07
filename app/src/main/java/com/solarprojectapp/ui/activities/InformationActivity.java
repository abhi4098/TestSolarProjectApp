package com.solarprojectapp.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.solarprojectapp.R;


public class InformationActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;
    Button skip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_information);
        skip = (Button)findViewById(R.id.skip);
        skip.setOnClickListener(this);
        mContext = InformationActivity.this;
    }


    @Override
    public void onClick(View view) {
        Intent i = new Intent(InformationActivity.this, SolarProjectLoginActivity.class);
        startActivity(i);
    }
}


