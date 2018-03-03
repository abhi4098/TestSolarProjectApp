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
import com.solarprojectapp.ui.adapters.NewComplaintAdapter;
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


public class NewComplaintListActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;
    private RetrofitInterface.UserNewCompaintListClient UserNewCompaintListAdapter;
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

    ArrayList<ComplaintListsDatum> newComplaintList = null;
    NewComplaintAdapter newComplaintAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_new_complaint);
        mContext = NewComplaintListActivity.this;
        ButterKnife.bind(this);
        ivBackIcon.setOnClickListener(this);
        tvAppTitle.setText("NEW COMPLAINT RECEIVED");

        setUpRestAdapter();
        getNewComplaints();

    }

    private void setUpRestAdapter() {
        UserNewCompaintListAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.UserNewCompaintListClient.class, MAIN_BASE_URL, this);
    }


    private void getNewComplaints() {
        LoadingDialog.showLoadingDialog(this,"Loading...");
        Call<NewComplaintResponse> call = UserNewCompaintListAdapter.userNewComplaintList("complainlist");
        if (NetworkUtils.isNetworkConnected(this)) {
            call.enqueue(new Callback<NewComplaintResponse>() {

                @Override
                public void onResponse(Call<NewComplaintResponse> call, Response<NewComplaintResponse> response) {

                    if (response.isSuccessful()) {

                        if (response.body().getSuccess().equals("true")) {
                            setNewComplaints(response);
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

    private void setNewComplaints(Response<NewComplaintResponse> response) {
        newComplaintList = new ArrayList<>();

        for (int i = 0; i < response.body().getComplaintListsData().size(); i++) {
            //Log.e("abhi", "setNewComplaints: "+response.body().getComplaintListsData().get(i).size() );
            ComplaintListsDatum complaintListsDatum = new ComplaintListsDatum();
            complaintListsDatum.setComplainId(response.body().getComplaintListsData().get(i).get(i).getComplainId());
            complaintListsDatum.setComplainDescription(response.body().getComplaintListsData().get(i).get(i).getComplainDescription());
            complaintListsDatum.setComplaint(response.body().getComplaintListsData().get(i).get(i).getComplaint());
            complaintListsDatum.setEndConsumer(response.body().getComplaintListsData().get(i).get(i).getEndConsumer());
            complaintListsDatum.setProjectOwner(response.body().getComplaintListsData().get(i).get(i).getProjectOwner());
            complaintListsDatum.setProjectType(response.body().getComplaintListsData().get(i).get(i).getProjectType());
            complaintListsDatum.setState(response.body().getComplaintListsData().get(i).get(i).getState());
            newComplaintList.add(complaintListsDatum);
            Log.e("abhi", "onResponse:..new complaint list "+newComplaintList.get(i).getComplaint());

        }

        newComplaintAdapter = new NewComplaintAdapter(this, R.layout.layout_new_complaint_list, R.id.complaint_name, newComplaintList);
        listview.setAdapter(newComplaintAdapter);
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


