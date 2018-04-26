package com.solarprojectapp.ui.activities;

import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.solarprojectapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ShowNewComplaintDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;

    @BindView(R.id.back_icon)
    ImageView ivBackIcon;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_app_title)
    TextView tvAppTitle;

    @BindView(R.id.end_consumer)
    TextView tvEndConsumer;
    @BindView(R.id.project_owner)
    TextView tvProjectOwner;
    @BindView(R.id.state)
    TextView tvprojectState;
    @BindView(R.id.complaint_id)
    TextView tvComplaintId;
    @BindView(R.id.complaint_name)
    TextView tvComplaintName;
    @BindView(R.id.description)
    TextView tvDescription;
    @BindView(R.id.project_type)
    TextView tvProjectType;
    @BindView(R.id.contact_no)
    TextView tvContactNum;
    @BindView(R.id.creation_date)
    TextView tvCreationDate;
    @BindView(R.id.closed_date)
    TextView tvClosedDate;




    //private EditText result;
   String  complaintCreationDate,complaintCloseDate,complaintDesc,complaintId,complaintName,complaintEndConsumer,complaintProjectOwner,complaintProjectType,complaintState,complaintContact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_show_new_complaint_details);
        mContext = ShowNewComplaintDetailsActivity.this;
        ButterKnife.bind(this);
        ivBackIcon.setOnClickListener(this);
        tvAppTitle.setText("COMPLAINT DETAILS");
        complaintDesc = getIntent().getExtras().getString("COMPLAINT_DESC");
        complaintId = getIntent().getExtras().getString("COMPLAINT_ID");
        complaintName = getIntent().getExtras().getString("COMPLAINT");
        complaintEndConsumer = getIntent().getExtras().getString("COMPLAINT_END_CONSUMER");
        complaintProjectOwner = getIntent().getExtras().getString("COMPLAINT_PROJECT_OWNER");
        complaintProjectType = getIntent().getExtras().getString("COMPLAINT_PROJECT_TYPE");
        complaintState = getIntent().getExtras().getString("COMPLAINT_STATE");
        complaintContact = getIntent().getExtras().getString("COMPLAINT_CONTACT");
        complaintCreationDate = getIntent().getExtras().getString("COMPLAINT_CREATION_DATE");
        complaintCloseDate = getIntent().getExtras().getString("COMPLAINT_CLOSE_DATE");

        tvComplaintId.setText(complaintId);
        tvComplaintName.setText(complaintName);
        tvProjectType.setText(complaintProjectType);
        tvContactNum.setText(complaintContact);
        tvprojectState.setText(complaintState);
        tvDescription.setText(complaintDesc);
        tvProjectOwner.setText(complaintProjectOwner);
        tvEndConsumer.setText(complaintEndConsumer);
        if (!complaintCreationDate.equals("")||complaintCreationDate !=null) {
            tvCreationDate.setText(parseTodaysDate(complaintCreationDate));
        }
        if (!complaintCloseDate.equals("")||complaintCloseDate !=null) {
            tvClosedDate.setText(parseTodaysDate(complaintCloseDate));
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


    @Override
    public void onClick(View view) {

        super.onBackPressed();

    }
}


