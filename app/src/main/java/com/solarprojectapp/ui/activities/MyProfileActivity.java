package com.solarprojectapp.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.solarprojectapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MyProfileActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;
    @BindView(R.id.back_image)
    ImageView backImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_my_profile);
        mContext = MyProfileActivity.this;
        ButterKnife.bind(this);
        backImage.setOnClickListener(this);

    }




    @Override
    public void onClick(View view) {
        super.onBackPressed();

    }
}


