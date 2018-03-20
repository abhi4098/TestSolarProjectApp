package com.solarprojectapp.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.Toast;

import com.solarprojectapp.R;
import com.solarprojectapp.api.ApiAdapter;
import com.solarprojectapp.api.RetrofitInterface;
import com.solarprojectapp.generated.model.ComplaintListsDatum;
import com.solarprojectapp.generated.model.ComplaintTypeDropdown;
import com.solarprojectapp.generated.model.Complaintstypelist;
import com.solarprojectapp.generated.model.NewComplaintResponse;
import com.solarprojectapp.generated.model.SubmitComplaintResponse;
import com.solarprojectapp.ui.adapters.ComplaintAdapter;
import com.solarprojectapp.utils.LoadingDialog;
import com.solarprojectapp.utils.NetworkUtils;
import com.solarprojectapp.utils.PrefUtils;
import com.solarprojectapp.utils.SnakBarUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.solarprojectapp.api.ApiEndPoints.MAIN_BASE_URL;


public class AddComplaintActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;
    private RetrofitInterface.UserCompaintTypeClient userCompaintTypeAdapter;
    private RetrofitInterface.UserSubmitComplaintClient submitComplaintAdapter;

    @BindView(R.id.back_icon)
    ImageView ivBackIcon;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_app_title)
    TextView tvAppTitle;
    String spComplaintSelectedItem = "Select Your Complaint";

    @BindView(R.id.spinner_complaint)
    Spinner spinner;
    String projectid,complaintstypeid;
    ArrayList<Complaintstypelist> userComplaintTypeList = null;
    ArrayList<String> showuserComplaintTypeList = null;

    //private EditText result;
    @OnClick(R.id.submit_add_complaint)
    public void submitComplaint()
    {
        if (spComplaintSelectedItem.equals("Select Your Complaint"))
        {
            Toast.makeText(getApplicationContext(),"Select Your Complaint",Toast.LENGTH_SHORT).show();
        }
        else
        {
            for (int i = 0; i < userComplaintTypeList.size(); i++) {
                if (userComplaintTypeList.get(i).getComplaintstypeName().equals(spComplaintSelectedItem))
                {
                    projectid=userComplaintTypeList.get(i).getFkProjectid();
                    complaintstypeid = userComplaintTypeList.get(i).getComplaintstypeId();
                }
            }
            submitComplaintToAdmin();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_add_complaint);
        mContext = AddComplaintActivity.this;
        ButterKnife.bind(this);
        ivBackIcon.setOnClickListener(this);
        tvAppTitle.setText("ADD A COMPLAINT");

        setUpRestAdapter();
        getComplaintsType();
    /*    List<String> list = new ArrayList<String>();
        list.add("Select Your Complaint");
        list.add("Complaint 1");
        list.add("Complaint 2");
        list.add("Complaint 3");
        list.add("Complaint 4");
        list.add("Complaint 5");
        list.add("Others");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String itemSelected = (String) spinner.getSelectedItem();
                Log.e("abhi", "onItemSelected: ........."+itemSelected );
                if (itemSelected.equals("Others"))

                {

                    LayoutInflater li = LayoutInflater.from(AddComplaintActivity.this);
                    View promptsView = li.inflate(R.layout.prompts, null);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            AddComplaintActivity.this);

                    // set prompts.xml to alertdialog builder
                    alertDialogBuilder.setView(promptsView);

                    final EditText userInput = (EditText) promptsView
                            .findViewById(R.id.editTextDialogUserInput);

                    // set dialog message
                    alertDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton("Submit",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,int id) {
                                            // get user input and set it to result
                                            // edit text
                                           // result.setText(userInput.getText());
                                            dialog.cancel();
                                        }
                                    })
                            .setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,int id) {
                                            dialog.cancel();
                                        }
                                    });

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();
                    alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#ea466b"));
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#ea466b"));
                  *//*  Intent j = new Intent(SolarProjectLoginActivity.this, NavigationalActivity.class);
                    j.putExtra("LOGIN_TYPE", itemSelected);
                    startActivity(j);
                    finish();*//*
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
*/

    }


    private void setUpRestAdapter() {
        userCompaintTypeAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.UserCompaintTypeClient.class, MAIN_BASE_URL, this);
        submitComplaintAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.UserSubmitComplaintClient.class, MAIN_BASE_URL, this);
    }


    private void getComplaintsType() {
        LoadingDialog.showLoadingDialog(this,"Loading...");
        Call<ComplaintTypeDropdown> call = userCompaintTypeAdapter.userComplaintTypeList("complainstypelist");
        if (NetworkUtils.isNetworkConnected(this)) {
            call.enqueue(new Callback<ComplaintTypeDropdown>() {

                @Override
                public void onResponse(Call<ComplaintTypeDropdown> call, Response<ComplaintTypeDropdown> response) {

                    if (response.isSuccessful()) {

                        if (response.body().getSuccess().equals("true")) {
                            setComplaintType(response);
                            LoadingDialog.cancelLoading();
                        }
                        else
                        {
                            LoadingDialog.cancelLoading();

                            Toast.makeText(getApplicationContext(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
                        }



                    }

                }

                @Override
                public void onFailure(Call<ComplaintTypeDropdown> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"Error occur",Toast.LENGTH_SHORT).show();
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(this);
        }
    }

    private void submitComplaintToAdmin() {
        LoadingDialog.showLoadingDialog(this,"Loading...");
        Call<SubmitComplaintResponse> call = submitComplaintAdapter.userSubmitComplaint(PrefUtils.getFkId(AddComplaintActivity.this),projectid,complaintstypeid,"remarks","addcomplaintsbyconsumer");
        if (NetworkUtils.isNetworkConnected(this)) {
            call.enqueue(new Callback<SubmitComplaintResponse>() {

                @Override
                public void onResponse(Call<SubmitComplaintResponse> call, Response<SubmitComplaintResponse> response) {

                    if (response.isSuccessful()) {

                        if (response.body().getSuccess().equals("true")) {

                            Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
                            finish();
                            LoadingDialog.cancelLoading();
                        }
                        else
                        {
                            LoadingDialog.cancelLoading();

                            Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        }



                    }

                }

                @Override
                public void onFailure(Call<SubmitComplaintResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"Error occur",Toast.LENGTH_SHORT).show();
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(this);
        }
    }

    private void setComplaintType(Response<ComplaintTypeDropdown> response) {
        userComplaintTypeList = new ArrayList<>();
        showuserComplaintTypeList = new ArrayList<>();
        showuserComplaintTypeList.add(spComplaintSelectedItem);


        for (int i = 0; i < response.body().getComplaintstypelist().size(); i++) {
            for (int j = 0; j < response.body().getComplaintstypelist().get(i).size();j++) {
                //Log.e("abhi", "setNewComplaints: "+response.body().getComplaintListsData().get(i).size() );
                Complaintstypelist complaintstypelist = new Complaintstypelist();
                complaintstypelist.setComplaintstypeAdddate(response.body().getComplaintstypelist().get(i).get(j).getComplaintstypeAdddate());
                complaintstypelist.setComplaintstypeId(response.body().getComplaintstypelist().get(i).get(j).getComplaintstypeId());
                complaintstypelist.setComplaintstypeModifieddate(response.body().getComplaintstypelist().get(i).get(j).getComplaintstypeModifieddate());
                complaintstypelist.setComplaintstypeName(response.body().getComplaintstypelist().get(i).get(j).getComplaintstypeName());
                complaintstypelist.setComplaintstypeOrder(response.body().getComplaintstypelist().get(i).get(j).getComplaintstypeOrder());
                complaintstypelist.setComplaintstypeStatus(response.body().getComplaintstypelist().get(i).get(j).getComplaintstypeStatus());
                complaintstypelist.setFkProjectid(response.body().getComplaintstypelist().get(i).get(j).getFkProjectid());
                userComplaintTypeList.add(complaintstypelist);
                showuserComplaintTypeList.add(response.body().getComplaintstypelist().get(i).get(j).getComplaintstypeName());
                Log.e("abhi", "onResponse:..new complaint list " + userComplaintTypeList.get(i).getComplaintstypeName());

            }
        }

        final ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, showuserComplaintTypeList);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(categoryAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spComplaintSelectedItem = spinner.getSelectedItem().toString();
                Log.e("abhi", "onItemSelected: " +spComplaintSelectedItem );

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    @Override
    public void onClick(View view) {

        super.onBackPressed();

    }
}


