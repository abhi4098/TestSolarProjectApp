package com.solarprojectapp.ui.activities;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.solarprojectapp.R;
import com.solarprojectapp.api.ApiAdapter;
import com.solarprojectapp.api.RetrofitInterface;
import com.solarprojectapp.generated.model.ComplaintListsDatum;
import com.solarprojectapp.generated.model.NewComplaintResponse;
import com.solarprojectapp.generated.model.RejectedComplaintListResponse;
import com.solarprojectapp.generated.model.RejectedComplaintListsDatum;
import com.solarprojectapp.ui.adapters.ComplaintAdapter;
import com.solarprojectapp.ui.adapters.RejectedComplaintAdapter;
import com.solarprojectapp.utils.LoadingDialog;
import com.solarprojectapp.utils.NetworkUtils;
import com.solarprojectapp.utils.SnakBarUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.solarprojectapp.api.ApiEndPoints.MAIN_BASE_URL;


public class RejectedComplaintListActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;
    private RetrofitInterface.UserRejectedCompaintListClient UserRejectedCompaintListAdapter;
    @BindView(R.id.back_icon)
    ImageView ivBackIcon;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_app_title)
    TextView tvAppTitle;

 /*   @BindView(R.id.spinner2)
    Spinner spinner;
*/
    @BindView(R.id.listview)
    ListView listview;

    ArrayList<RejectedComplaintListsDatum> rejectedComplaintList = null;
    RejectedComplaintAdapter rejectedComplaintAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_new_complaint);
        mContext = RejectedComplaintListActivity.this;
        ButterKnife.bind(this);
        ivBackIcon.setOnClickListener(this);
        tvAppTitle.setText("REJECTED COMPLAINTS");

        setUpRestAdapter();
        getRejectedComplaints();

    }

    @Override
    public void onResume() {
        super.onResume();
        setUpRestAdapter();
        getRejectedComplaints();
    }
    private void setUpRestAdapter() {
        UserRejectedCompaintListAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.UserRejectedCompaintListClient.class, MAIN_BASE_URL, this);
    }


    private void getRejectedComplaints() {
        LoadingDialog.showLoadingDialog(this,"Loading...");
        Call<RejectedComplaintListResponse> call = UserRejectedCompaintListAdapter.userRejectedComplaintList("rejectedcomplainlist");
        if (NetworkUtils.isNetworkConnected(this)) {
            call.enqueue(new Callback<RejectedComplaintListResponse>() {

                @Override
                public void onResponse(Call<RejectedComplaintListResponse> call, Response<RejectedComplaintListResponse> response) {

                    if (response.isSuccessful()) {

                        if (response.body().getSuccess().equals("true")) {
                            setRejectedComplaints(response);
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
                public void onFailure(Call<RejectedComplaintListResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"Error occur",Toast.LENGTH_SHORT).show();
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(this);
        }
    }

    private void setRejectedComplaints(Response<RejectedComplaintListResponse> response) {
        rejectedComplaintList = new ArrayList<>();

        for (int i = 0; i < response.body().getRejectedComplaintListsData().size(); i++) {
            for (int j = 0; j < response.body().getRejectedComplaintListsData().get(i).size();j++) {
                //Log.e("abhi", "setNewComplaints: "+response.body().getRejectedComplaintListsData().get(i).size() );
                RejectedComplaintListsDatum rejectedComplaintListsDatum = new RejectedComplaintListsDatum();
                rejectedComplaintListsDatum.setComplainId(response.body().getRejectedComplaintListsData().get(i).get(j).getComplainId());
                rejectedComplaintListsDatum.setComplainDescription(response.body().getRejectedComplaintListsData().get(i).get(j).getComplainDescription());
                rejectedComplaintListsDatum.setComplaint(response.body().getRejectedComplaintListsData().get(i).get(j).getComplaint());
                rejectedComplaintListsDatum.setEndConsumer(response.body().getRejectedComplaintListsData().get(i).get(j).getEndConsumer());
                rejectedComplaintListsDatum.setProjectOwner(response.body().getRejectedComplaintListsData().get(i).get(j).getProjectOwner());
                rejectedComplaintListsDatum.setProjectType(response.body().getRejectedComplaintListsData().get(i).get(j).getProjectType());
                rejectedComplaintListsDatum.setState(response.body().getRejectedComplaintListsData().get(i).get(j).getState());
                rejectedComplaintListsDatum.setEndConsumerContactno(response.body().getRejectedComplaintListsData().get(i).get(j).getEndConsumerContactno());
                rejectedComplaintListsDatum.setCreateDate(response.body().getRejectedComplaintListsData().get(i).get(j).getCreateDate());
                rejectedComplaintListsDatum.setComplainCloseDate(response.body().getRejectedComplaintListsData().get(i).get(j).getComplainCloseDate());

                rejectedComplaintList.add(rejectedComplaintListsDatum);
                Log.e("abhi", "onResponse:..new complaint list " + rejectedComplaintList.get(i).getComplaint());

            }
        }

        rejectedComplaintAdapter = new RejectedComplaintAdapter(this, R.layout.layout_new_complaint_list, R.id.complaint_name, rejectedComplaintList);
        listview.setAdapter(rejectedComplaintAdapter);
        LoadingDialog.cancelLoading();
        listview.setDivider(new ColorDrawable(Color.TRANSPARENT));  //hide the divider
        listview.setClipToPadding(false);
        listview.setDividerHeight(50);
        listview.setTextFilterEnabled(true);


    }


    @Override
    public void onClick(View view) {

        super.onBackPressed();

    }
}


