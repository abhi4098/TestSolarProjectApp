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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.solarprojectapp.R;
import com.solarprojectapp.api.ApiAdapter;
import com.solarprojectapp.api.RetrofitInterface;
import com.solarprojectapp.generated.model.ComplaintListsDatum;
import com.solarprojectapp.generated.model.NewComplaintResponse;
import com.solarprojectapp.generated.model.TechnicalPartnerList;
import com.solarprojectapp.generated.model.TechnicalPartnerListResponse;
import com.solarprojectapp.ui.adapters.ComplaintAdapter;
import com.solarprojectapp.ui.adapters.TechnicalPartenerAdapter;
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


public class TechnicalPartenerListActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;
    private RetrofitInterface.TechnicalPartnerClient TechnicalPartnerAdapter;
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

    ArrayList<TechnicalPartnerList> technicalPartnerLists = null;
    TechnicalPartenerAdapter technicalPartenersAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_new_complaint);
        mContext = TechnicalPartenerListActivity.this;
        ButterKnife.bind(this);
        ivBackIcon.setOnClickListener(this);
        tvAppTitle.setText("TECHNICAL PARTNER LIST");

        setUpRestAdapter();
        getTechPartnerList();

    }

    private void setUpRestAdapter() {
        TechnicalPartnerAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.TechnicalPartnerClient.class, MAIN_BASE_URL, this);
    }


    private void getTechPartnerList() {
        LoadingDialog.showLoadingDialog(this,"Loading...");
        Call<TechnicalPartnerListResponse> call = TechnicalPartnerAdapter.TechPartnerList("technicalpartnerlist","2");
        if (NetworkUtils.isNetworkConnected(this)) {
            call.enqueue(new Callback<TechnicalPartnerListResponse>() {

                @Override
                public void onResponse(Call<TechnicalPartnerListResponse> call, Response<TechnicalPartnerListResponse> response) {

                    if (response.isSuccessful()) {

                        if (response.body().getSuccess().equals("true")) {
                            setTechnicalPartenerList(response);
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
                public void onFailure(Call<TechnicalPartnerListResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"Error occur",Toast.LENGTH_SHORT).show();
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(this);
            LoadingDialog.cancelLoading();
        }
    }

    private void setTechnicalPartenerList(Response<TechnicalPartnerListResponse> response) {
        technicalPartnerLists = new ArrayList<>();

        for (int i = 0; i < response.body().getTechnicalPartnerList().size(); i++) {
            for (int j = 0; j < response.body().getTechnicalPartnerList().get(i).size();j++) {
                //Log.e("abhi", "setNewComplaints: "+response.body().getTechnicalPartnerList().get(i).size() );
                TechnicalPartnerList technicalPartnerList = new TechnicalPartnerList();
                technicalPartnerList.setTechnicalpartenerEmailid(response.body().getTechnicalPartnerList().get(i).get(j).getTechnicalpartenerEmailid());
                technicalPartnerList.setTechnicalpartenerId(response.body().getTechnicalPartnerList().get(i).get(j).getTechnicalpartenerId());
                technicalPartnerList.setTechnicalpartenerName(response.body().getTechnicalPartnerList().get(i).get(j).getTechnicalpartenerName());
                technicalPartnerList.setUsername(response.body().getTechnicalPartnerList().get(i).get(j).getUsername());
                technicalPartnerList.setAdharnumber(response.body().getTechnicalPartnerList().get(i).get(j).getAdharnumber());
                technicalPartnerList.setPannumber(response.body().getTechnicalPartnerList().get(i).get(j).getPannumber());
                technicalPartnerList.setTechnicalpartenerPhone(response.body().getTechnicalPartnerList().get(i).get(j).getTechnicalpartenerPhone());
                technicalPartnerList.setTechnicalpartenerGeoadress(response.body().getTechnicalPartnerList().get(i).get(j).getTechnicalpartenerGeoadress());

                technicalPartnerLists.add(technicalPartnerList);
                Log.e("abhi", "onResponse:..new complaint list " + technicalPartnerLists.get(i).getTechnicalpartenerName());

            }
        }

        technicalPartenersAdapter = new TechnicalPartenerAdapter(this, R.layout.layout_tech_partner_list, R.id.tech_name, technicalPartnerLists);
        listview.setAdapter(technicalPartenersAdapter);
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


