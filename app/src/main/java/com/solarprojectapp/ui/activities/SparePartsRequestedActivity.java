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
import com.solarprojectapp.generated.model.SparePartsPendingResponse;
import com.solarprojectapp.generated.model.SparePartsRequestResponse;
import com.solarprojectapp.generated.model.SparepartsrequestList;
import com.solarprojectapp.ui.adapters.SparePartRequestededAdapter;
import com.solarprojectapp.ui.adapters.UserSparePartsPendingAdapter;
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


public class SparePartsRequestedActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;
    private RetrofitInterface.SparePartsRequestClient spPartsRequested;
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

    ArrayList<SparepartsrequestList> sparePartsRequestedList = null;
    SparePartRequestededAdapter sparePartRequestededAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_spare_parts_requested);
        mContext = SparePartsRequestedActivity.this;
        ButterKnife.bind(this);
        ivBackIcon.setOnClickListener(this);
        tvAppTitle.setText("SPARE PARTS REQUESTED");

        setUpRestAdapter();
        getSparePartRequested();

    }

    private void setUpRestAdapter() {
        spPartsRequested = ApiAdapter.createRestAdapter(RetrofitInterface.SparePartsRequestClient.class, MAIN_BASE_URL, this);
    }


    private void getSparePartRequested() {
        LoadingDialog.showLoadingDialog(this,"Loading...");
        Call<SparePartsRequestResponse> call = spPartsRequested.sparePartsRequestList("opensparepartrequest");
        if (NetworkUtils.isNetworkConnected(this)) {
            call.enqueue(new Callback<SparePartsRequestResponse>() {

                @Override
                public void onResponse(Call<SparePartsRequestResponse> call, Response<SparePartsRequestResponse> response) {

                    if (response.isSuccessful()) {

                        if (response.body().getSuccess().equals("true")) {
                            setSparePartsRequested(response);
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
                public void onFailure(Call<SparePartsRequestResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"Error occur",Toast.LENGTH_SHORT).show();
                    Log.e("abhi", "onFailure: "+t.getMessage() );
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(this);
        }
    }

    private void setSparePartsRequested(Response<SparePartsRequestResponse> response) {
        sparePartsRequestedList = new ArrayList<>();
        Log.e("abhi", "setNewComplaints: "+response.body().getSparepartsrequestList().size() );
        for (int i = 0; i < response.body().getSparepartsrequestList().size(); i++) {
            for (int j = 0; j < response.body().getSparepartsrequestList().get(i).size(); j++) {
                SparepartsrequestList sparepartsrequestList = new SparepartsrequestList();
                sparepartsrequestList.setSparepartId(response.body().getSparepartsrequestList().get(i).get(j).getSparepartId());
                sparepartsrequestList.setSparepartBrand(response.body().getSparepartsrequestList().get(i).get(j).getSparepartBrand());
                sparepartsrequestList.setSparepartName(response.body().getSparepartsrequestList().get(i).get(j).getSparepartName());
                sparepartsrequestList.setSparepartCreatedate(response.body().getSparepartsrequestList().get(i).get(j).getSparepartCreatedate());
                sparepartsrequestList.setSparepartRequestQuantity(response.body().getSparepartsrequestList().get(i).get(j).getSparepartRequestQuantity());
                sparepartsrequestList.setStatusName(response.body().getSparepartsrequestList().get(i).get(j).getStatusName());
                sparepartsrequestList.setSparepartRequestPrice(response.body().getSparepartsrequestList().get(i).get(j).getSparepartRequestPrice());
                sparepartsrequestList.setSparepartStatus(response.body().getSparepartsrequestList().get(i).get(j).getSparepartStatus());
                sparepartsrequestList.setSparepartPrice(response.body().getSparepartsrequestList().get(i).get(j).getSparepartPrice());
                sparepartsrequestList.setSparepartRequestId(response.body().getSparepartsrequestList().get(i).get(j).getSparepartRequestId());
                sparePartsRequestedList.add(sparepartsrequestList);
                Log.e("abhi", "onResponse:..new complaint list " + sparePartsRequestedList.get(i).getSparepartCreatedate());

            }
        }

        sparePartRequestededAdapter = new SparePartRequestededAdapter(this, R.layout.layout_spare_parts_requested, R.id.complaint_id, sparePartsRequestedList);
        listview.setAdapter(sparePartRequestededAdapter);
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


