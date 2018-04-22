package com.solarprojectapp.ui.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.solarprojectapp.R;
import com.solarprojectapp.utils.PrefUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class BreakdownActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;

    @BindView(R.id.back_icon)
    ImageView ivBackIcon;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_app_title)
    TextView tvAppTitle;

    @BindView(R.id.project_name)
    TextView tvProjectName;

    @BindView(R.id.project_owner)
    TextView tvProjectOwner;

    @BindView(R.id.commisioning_date)
    TextView tvCommisionDate;

    @BindView(R.id.onm_date)
    TextView tvOnDate;

    @BindView(R.id.other_details)
    TextView tvOtherDetail;

    @BindView(R.id.add_complaint_button)
    Button addComplaintBtn;
   @OnClick(R.id.add_complaint_button)
   public void addComplaint()
   {
       Intent i = new Intent(BreakdownActivity.this, AddComplaintActivity.class);
       startActivity(i);
   }
   /* @BindView(R.id.spinner2)
    Spinner spinner;*/

    //private EditText result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_breakdown);
        mContext = BreakdownActivity.this;
        ButterKnife.bind(this);
        //addComplaintBtn.setOnClickListener(this);
        ivBackIcon.setOnClickListener(this);
        tvAppTitle.setText("BREAKDOWN");
        tvProjectName.setText(PrefUtils.getProject(this));
        tvProjectOwner.setText(PrefUtils.getProjectOwner(this));
        tvCommisionDate.setText(PrefUtils.getCommisionDate(this));
        tvOnDate.setText(PrefUtils.getOM(this));
        tvOtherDetail.setText(PrefUtils.getOtherDetails(this));


    }




    @Override
    public void onClick(View view) {

        super.onBackPressed();

    }
}


