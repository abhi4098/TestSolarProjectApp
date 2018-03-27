package com.solarprojectapp.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.solarprojectapp.R;
import com.solarprojectapp.ui.activities.AddComplaintActivity;
import com.solarprojectapp.ui.activities.BreakdownActivity;
import com.solarprojectapp.ui.activities.CustomTecnicalPartnerTabActivity;
import com.solarprojectapp.ui.activities.MyProfileActivity;
import com.solarprojectapp.ui.activities.NavigationalActivity;
import com.solarprojectapp.ui.activities.SolarProjectLoginActivity;
import com.solarprojectapp.utils.PrefUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ProfileCustomerPageFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "ProfileHomePageFragment";


    @BindView(R.id.information)
    LinearLayout llInformation;

    @BindView(R.id.maintenance_data)
    LinearLayout llMaintenancedData;

    @BindView(R.id.text_maintainence)
    TextView tvMaintenancedData;

    @BindView(R.id.text_information)
    TextView tvInformationData;

    @BindView(R.id.preventive_maintainace)
    LinearLayout llImageMaintenance;

   /* @BindView(R.id.breakdown)
    LinearLayout llBreakdown;*/

    Boolean showInformation = true;

    @OnClick(R.id.breakdown)
    public void breakdown() {
        if (PrefUtils.getUserFrag(getContext()).equals("Technical Partner"))
        {
            Intent intent = new Intent(getActivity(), CustomTecnicalPartnerTabActivity.class);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(getActivity(), BreakdownActivity.class);
            startActivity(intent);
        }
    }


    @OnClick(R.id.preventive_maintainace)
    public void preventiveMaintaince() {
        //
        if (showInformation)
        {
            llImageMaintenance.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.solar_pink_gradient));
            llInformation.setVisibility(View.GONE);
            tvInformationData.setVisibility(View.GONE);

            llMaintenancedData.setVisibility(View.VISIBLE);
            tvMaintenancedData.setVisibility(View.VISIBLE);

            showInformation =false;
        }
        else {
            llImageMaintenance.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.solar_pink));
            llInformation.setVisibility(View.VISIBLE);
            tvInformationData.setVisibility(View.VISIBLE);

            llMaintenancedData.setVisibility(View.GONE);
            tvMaintenancedData.setVisibility(View.GONE);
            showInformation =true;
        }


    }

    public ProfileCustomerPageFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_customer_page, container, false);
        ButterKnife.bind(this, view);
        //addComplaintBtn.setOnClickListener(this);
       /* setUpRestAdapter();
        getUserTransactions();
        getReceivedMoneyRequests();*/
        return view;

    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(getContext(), AddComplaintActivity.class);
        startActivity(i);

    }


}