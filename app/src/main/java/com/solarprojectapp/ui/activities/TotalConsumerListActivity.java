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
import com.solarprojectapp.generated.model.ConsumerList;
import com.solarprojectapp.generated.model.NewComplaintResponse;
import com.solarprojectapp.generated.model.TotalConsumerListResponse;
import com.solarprojectapp.ui.adapters.ComplaintAdapter;
import com.solarprojectapp.ui.adapters.TotalConsumerAdapter;
import com.solarprojectapp.utils.LoadingDialog;
import com.solarprojectapp.utils.NetworkUtils;
import com.solarprojectapp.utils.PrefUtils;
import com.solarprojectapp.utils.SnakBarUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.solarprojectapp.api.ApiEndPoints.MAIN_BASE_URL;


public class TotalConsumerListActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;
    private RetrofitInterface.TotalConsumerClient totalConsumerAdapter;
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
    String userType;

    ArrayList<ConsumerList> totalConsumerList = null;
    TotalConsumerAdapter totalConsumersAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_new_complaint);
        mContext = TotalConsumerListActivity.this;
        ButterKnife.bind(this);
        ivBackIcon.setOnClickListener(this);
        tvAppTitle.setText("TOTAL CONSUMERS");

        setUpRestAdapter();
        getTotalConsumers();

    }

    private void setUpRestAdapter() {
        totalConsumerAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.TotalConsumerClient.class, MAIN_BASE_URL, this);
    }


    private void getTotalConsumers() {
        LoadingDialog.showLoadingDialog(this,"Loading...");
        userType ="Admin";
        Call<TotalConsumerListResponse> call = totalConsumerAdapter.totalConsumer(PrefUtils.getFkId(TotalConsumerListActivity.this),userType,"consumerlist");
        if (NetworkUtils.isNetworkConnected(this)) {
            call.enqueue(new Callback<TotalConsumerListResponse>() {

                @Override
                public void onResponse(Call<TotalConsumerListResponse> call, Response<TotalConsumerListResponse> response) {

                    if (response.isSuccessful()) {

                        if (response.body().getSuccess().equals("true")) {
                            setTotalConsumers(response);
                            Log.e("abhi", "onResponse: "+response.body().getMsg());
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
                public void onFailure(Call<TotalConsumerListResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"Error occur",Toast.LENGTH_SHORT).show();
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(this);
        }
    }

    private void setTotalConsumers(Response<TotalConsumerListResponse> response) {
        totalConsumerList = new ArrayList<>();

        for (int i = 0; i < response.body().getConsumerList().size(); i++) {
            for (int j = 0; j < response.body().getConsumerList().get(i).size();j++) {
                //Log.e("abhi", "setNewComplaints: "+response.body().getComplaintListsData().get(i).size() );
                ConsumerList consumerList = new ConsumerList();
                consumerList.setAddres(response.body().getConsumerList().get(i).get(j).getAddres());
                consumerList.setConsumerName(response.body().getConsumerList().get(i).get(j).getConsumerName());
                consumerList.setEmailId(response.body().getConsumerList().get(i).get(j).getEmailId());
                consumerList.setFlatNo(response.body().getConsumerList().get(i).get(j).getFlatNo());
                consumerList.setGeoAddress(response.body().getConsumerList().get(i).get(j).getGeoAddress());
                consumerList.setMobileNo(response.body().getConsumerList().get(i).get(j).getMobileNo());
                consumerList.setPinNo(response.body().getConsumerList().get(i).get(j).getPinNo());
                totalConsumerList.add(consumerList);
                Log.e("abhi", "onResponse:..new complaint list " + totalConsumerList.get(i).getConsumerName());

            }
        }

        totalConsumersAdapter = new TotalConsumerAdapter(this, R.layout.layout_total_consumer_list, R.id.consumer_name, totalConsumerList);
        listview.setAdapter(totalConsumersAdapter);
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


