package com.solarprojectapp.ui.activities;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.solarprojectapp.R;
import com.solarprojectapp.api.ApiAdapter;
import com.solarprojectapp.api.RetrofitInterface;
import com.solarprojectapp.generated.model.SparePartsRequestResponse;
import com.solarprojectapp.generated.model.SparepartsrequestList;
import com.solarprojectapp.generated.model.SubmitComplaintResponse;
import com.solarprojectapp.utils.LoadingDialog;
import com.solarprojectapp.utils.NetworkUtils;
import com.solarprojectapp.utils.PrefUtils;
import com.solarprojectapp.utils.SnakBarUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.solarprojectapp.api.ApiEndPoints.MAIN_BASE_URL;


public class RequestSparePartByTechPartnerActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;
    String complaintId;
    private RetrofitInterface.UserCompaintTypeClient userCompaintTypeAdapter;
    private RetrofitInterface.UserSubmitSparePartClient userSubmitSparePartClient;
    ArrayList<SparepartsrequestList> sparePartsRequestedList = null;
    ArrayList<String> showSparePartsRequestedList = null;


    @BindView(R.id.back_icon)
    ImageView ivBackIcon;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_app_title)
    TextView tvAppTitle;
    String spComplaintSelectedItem = "Select Spare Part";
    private RetrofitInterface.SparePartsRequestByTechPartnerClient sparePartsRequestByTechPartnerClient;

    @BindView(R.id.spinner_complaint)
    Spinner spinner;
    String projectid,sparePartId;


    //private EditText result;
    @OnClick(R.id.submit_add_complaint)
    public void submitComplaint()
    {
        if (spComplaintSelectedItem.equals("Select Spare Part"))
        {
            Toast.makeText(getApplicationContext(),"Select Spare Part",Toast.LENGTH_SHORT).show();
        }
        else
        {
            for (int i = 0; i < sparePartsRequestedList.size(); i++) {
                if (sparePartsRequestedList.get(i).getSparepartName().equals(spComplaintSelectedItem))
                {
                   // projectid=sparePartsRequestedList.get(i).getFkProjectid();
                    sparePartId = sparePartsRequestedList.get(i).getSparepartId();
                }
            }
            submitComplaintToAdmin();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_request_sparepart_by_techpartner);
        mContext = RequestSparePartByTechPartnerActivity.this;
        ButterKnife.bind(this);
        ivBackIcon.setOnClickListener(this);
        complaintId = getIntent().getStringExtra("COMPLAINT_ID");
        tvAppTitle.setText("Request Spare Part");

        setUpRestAdapter();
        getRequestForSparePartsByTechPartner();


    }


    private void setUpRestAdapter() {
        sparePartsRequestByTechPartnerClient = ApiAdapter.createRestAdapter(RetrofitInterface.SparePartsRequestByTechPartnerClient.class, MAIN_BASE_URL, this);
        userSubmitSparePartClient = ApiAdapter.createRestAdapter(RetrofitInterface.UserSubmitSparePartClient.class, MAIN_BASE_URL, this);
    }

    private void getRequestForSparePartsByTechPartner() {
        LoadingDialog.showLoadingDialog(this,"Loading...");
        Call<SparePartsRequestResponse> call = sparePartsRequestByTechPartnerClient.sparePartsRequestBtPartnerList(complaintId,"listtechrequestsparepart");
        if (NetworkUtils.isNetworkConnected(this)) {
            call.enqueue(new Callback<SparePartsRequestResponse>() {

                @Override
                public void onResponse(Call<SparePartsRequestResponse> call, Response<SparePartsRequestResponse> response) {

                    if (response.isSuccessful()) {

                        if (response.body().getSuccess().equals("true")) {
                            Log.e("abhi", "onResponse: .............." +response.body().getSuccess());
                            setSparePartsRequestedByTechPartner(response);
                            LoadingDialog.cancelLoading();

                        }
                        else
                        {
                            LoadingDialog.cancelLoading();

                            Toast.makeText(RequestSparePartByTechPartnerActivity.this,response.body().getSuccess(),Toast.LENGTH_SHORT).show();
                        }



                    }

                }

                @Override
                public void onFailure(Call<SparePartsRequestResponse> call, Throwable t) {
                    Toast.makeText(RequestSparePartByTechPartnerActivity.this,"Error occur",Toast.LENGTH_SHORT).show();
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(RequestSparePartByTechPartnerActivity.this);
        }
    }



    private void submitComplaintToAdmin() {
        LoadingDialog.showLoadingDialog(this,"Loading...");
        Call<SubmitComplaintResponse> call = userSubmitSparePartClient.userSubmitSparePart(sparePartId,PrefUtils.getFkId(RequestSparePartByTechPartnerActivity.this),complaintId,"0","nil","requestsparepartbychnicalpartner");
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


    private void setSparePartsRequestedByTechPartner(Response<SparePartsRequestResponse> response) {
        sparePartsRequestedList = new ArrayList<>();
        showSparePartsRequestedList = new ArrayList<>();
        showSparePartsRequestedList.add(spComplaintSelectedItem);
        Log.e("abhi", "setNewComplaints: "+response.body().getSparepartsrequestList().size() );
        for (int i = 0; i < response.body().getSparepartsrequestList().size(); i++) {
            for (int j = 0; j < response.body().getSparepartsrequestList().get(i).size(); j++) {
                SparepartsrequestList sparepartsrequestList = new SparepartsrequestList();
                sparepartsrequestList.setSparepartUniquid(response.body().getSparepartsrequestList().get(i).get(j).getSparepartUniquid());
                sparepartsrequestList.setSparepartBrand(response.body().getSparepartsrequestList().get(i).get(j).getSparepartBrand());
                sparepartsrequestList.setSparepartName(response.body().getSparepartsrequestList().get(i).get(j).getSparepartName());
                sparepartsrequestList.setSparepartCreatedate(response.body().getSparepartsrequestList().get(i).get(j).getSparepartCreatedate());
                sparepartsrequestList.setSparepartStatus(response.body().getSparepartsrequestList().get(i).get(j).getSparepartStatus());
                sparepartsrequestList.setSparepartPrice(response.body().getSparepartsrequestList().get(i).get(j).getSparepartPrice());
                sparepartsrequestList.setSparepartId(response.body().getSparepartsrequestList().get(i).get(j).getSparepartId());

                sparePartsRequestedList.add(sparepartsrequestList);
                showSparePartsRequestedList.add(response.body().getSparepartsrequestList().get(i).get(j).getSparepartName());
                Log.e("abhi", "onResponse:..new complaint list " + sparePartsRequestedList.get(i).getSparepartCreatedate());

            }
        }

        final ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, showSparePartsRequestedList);
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


