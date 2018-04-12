package com.solarprojectapp.ui.fragments;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.solarprojectapp.R;
import com.solarprojectapp.api.ApiAdapter;
import com.solarprojectapp.api.RetrofitInterface;
import com.solarprojectapp.generated.model.ComplaintListsDatum;
import com.solarprojectapp.generated.model.NewComplaintResponse;
import com.solarprojectapp.ui.adapters.TechnicalPartnerComplaintAdapter;
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


public class InProgressTabFragment extends Fragment {

    private RetrofitInterface.UserTechCompaintListClient UserTabAdapter;
    ArrayList<ComplaintListsDatum> newTabComplaintList = null;
    TechnicalPartnerComplaintAdapter technicalPartnerComplaintAdapter;
    @BindView(R.id.listview)
    ListView listview;

    public InProgressTabFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_in_progress, container, false);
        ButterKnife.bind(this,rootView);
        setUpRestAdapter();
        getTabList();
        return rootView;
    }

    private void setUpRestAdapter() {
        UserTabAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.UserTechCompaintListClient.class, MAIN_BASE_URL, getContext());
    }


    private void getTabList() {
        LoadingDialog.showLoadingDialog(getContext(),"Loading...");
        Call<NewComplaintResponse> call = UserTabAdapter.userTechComplaintList("techinprogresscomplaintslist", PrefUtils.getFkId(getContext()));
        if (NetworkUtils.isNetworkConnected(getContext())) {
            call.enqueue(new Callback<NewComplaintResponse>() {

                @Override
                public void onResponse(Call<NewComplaintResponse> call, Response<NewComplaintResponse> response) {

                    if (response.isSuccessful()) {

                        if (response.body().getSuccess().equals("true")) {
                            setTabList(response);
                            LoadingDialog.cancelLoading();
                        }
                        else
                        {
                            LoadingDialog.cancelLoading();

                            Toast.makeText(getContext(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
                        }



                    }

                }

                @Override
                public void onFailure(Call<NewComplaintResponse> call, Throwable t) {
                    Toast.makeText(getContext(),"Error occur",Toast.LENGTH_SHORT).show();
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(getContext());
        }
    }

    private void setTabList(Response<NewComplaintResponse> response) {
        newTabComplaintList = new ArrayList<>();

        for (int i = 0; i < response.body().getComplaintListsData().size(); i++) {
            for (int j = 0; j < response.body().getComplaintListsData().get(i).size();j++) {
                //Log.e("abhi", "setNewComplaints: "+response.body().getComplaintListsData().get(i).size() );
                ComplaintListsDatum complaintListsDatum = new ComplaintListsDatum();
                if (response.body().getComplaintListsData().get(i).get(j).getComplainTechnicalpartnerstatus().equals("Accept")) {
                    complaintListsDatum.setComplainId(response.body().getComplaintListsData().get(i).get(j).getComplainId());
                    complaintListsDatum.setComplainDescription(response.body().getComplaintListsData().get(i).get(j).getComplainDescription());
                    complaintListsDatum.setComplaint(response.body().getComplaintListsData().get(i).get(j).getComplaint());
                    complaintListsDatum.setEndConsumer(response.body().getComplaintListsData().get(i).get(j).getEndConsumer());
                    complaintListsDatum.setProjectOwner(response.body().getComplaintListsData().get(i).get(j).getProjectOwner());
                    complaintListsDatum.setProjectType(response.body().getComplaintListsData().get(i).get(j).getProjectType());
                    complaintListsDatum.setState(response.body().getComplaintListsData().get(i).get(j).getState());
                    complaintListsDatum.setCreateDate(response.body().getComplaintListsData().get(i).get(j).getCreateDate());
                    complaintListsDatum.setComplainCloseDate(response.body().getComplaintListsData().get(i).get(j).getComplainCloseDate());

                    complaintListsDatum.setEndConsumerContactno(response.body().getComplaintListsData().get(i).get(j).getEndConsumerContactno());
                    complaintListsDatum.setComplainTechnicalpartnerstatus(response.body().getComplaintListsData().get(i).get(j).getComplainTechnicalpartnerstatus());
                    newTabComplaintList.add(complaintListsDatum);
                    Log.e("abhi", "onResponse:..new complaint list " + newTabComplaintList.get(i).getComplaint());
                }

            }
        }

        technicalPartnerComplaintAdapter = new TechnicalPartnerComplaintAdapter(getActivity(), R.layout.layout_tech_partner_complaint_list, R.id.complaint_name, newTabComplaintList);
        listview.setAdapter(technicalPartnerComplaintAdapter);
        LoadingDialog.cancelLoading();
        listview.setDivider(new ColorDrawable(Color.TRANSPARENT));  //hide the divider
        listview.setClipToPadding(false);
        listview.setDividerHeight(50);
        listview.setTextFilterEnabled(true);


    }

}
