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
import com.solarprojectapp.ui.adapters.ComplaintAdapter;
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


public class OverDueComplaintListActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;
    private RetrofitInterface.UserCompaintListClient UserCompaintListAdapter;
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

    ArrayList<ComplaintListsDatum> complaintList = null;
    ComplaintAdapter complaintAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_new_complaint);
        mContext = OverDueComplaintListActivity.this;
        ButterKnife.bind(this);
        ivBackIcon.setOnClickListener(this);
        tvAppTitle.setText("OVERDUE COMPLAINTS");

        setUpRestAdapter();
        getNewComplaints();

    }

    private void setUpRestAdapter() {
        UserCompaintListAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.UserCompaintListClient.class, MAIN_BASE_URL, this);
    }


    private void getNewComplaints() {
        LoadingDialog.showLoadingDialog(this,"Loading...");
        Call<NewComplaintResponse> call = UserCompaintListAdapter.userNewComplaintList("overduecomplainlist");
        if (NetworkUtils.isNetworkConnected(this)) {
            call.enqueue(new Callback<NewComplaintResponse>() {

                @Override
                public void onResponse(Call<NewComplaintResponse> call, Response<NewComplaintResponse> response) {

                    if (response.isSuccessful()) {

                        if (response.body().getSuccess().equals("true")) {
                            setOverDueComplaints(response);
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
                public void onFailure(Call<NewComplaintResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"Error occur",Toast.LENGTH_SHORT).show();
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(this);
        }
    }

    private void setOverDueComplaints(Response<NewComplaintResponse> response) {
        complaintList = new ArrayList<>();

        for (int i = 0; i < response.body().getComplaintListsData().size(); i++) {
            for (int j = 0; j < response.body().getComplaintListsData().get(i).size();j++) {
                //Log.e("abhi", "setNewComplaints: "+response.body().getComplaintListsData().get(i).size() );
                ComplaintListsDatum complaintListsDatum = new ComplaintListsDatum();
                complaintListsDatum.setComplainId(response.body().getComplaintListsData().get(i).get(j).getComplainId());
                complaintListsDatum.setComplainDescription(response.body().getComplaintListsData().get(i).get(j).getComplainDescription());
                complaintListsDatum.setComplaint(response.body().getComplaintListsData().get(i).get(j).getComplaint());
                complaintListsDatum.setEndConsumer(response.body().getComplaintListsData().get(i).get(j).getEndConsumer());
                complaintListsDatum.setProjectOwner(response.body().getComplaintListsData().get(i).get(j).getProjectOwner());
                complaintListsDatum.setProjectType(response.body().getComplaintListsData().get(i).get(j).getProjectType());
                complaintListsDatum.setState(response.body().getComplaintListsData().get(i).get(j).getState());
                complaintList.add(complaintListsDatum);
                Log.e("abhi", "onResponse:..new complaint list " + complaintList.get(i).getComplaint());

            }
        }

        complaintAdapter = new ComplaintAdapter(this, R.layout.layout_new_complaint_list, R.id.complaint_name, complaintList);
        listview.setAdapter(complaintAdapter);
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


