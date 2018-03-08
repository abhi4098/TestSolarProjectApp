package com.solarprojectapp.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.solarprojectapp.R;
import com.solarprojectapp.api.ApiAdapter;
import com.solarprojectapp.api.RetrofitInterface;
import com.solarprojectapp.generated.model.DashboardDataResponse;
import com.solarprojectapp.generated.model.NewComplaintResponse;
import com.solarprojectapp.ui.activities.NewComplaintListActivity;
import com.solarprojectapp.ui.activities.NewSparePartsPendingActivity;
import com.solarprojectapp.ui.activities.SparePartsRequestedActivity;
import com.solarprojectapp.utils.LoadingDialog;
import com.solarprojectapp.utils.NetworkUtils;
import com.solarprojectapp.utils.SnakBarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.solarprojectapp.api.ApiEndPoints.MAIN_BASE_URL;


public class ProfileHomePageFragment extends Fragment {
    private static final String TAG = "ProfileHomePageFragment";
    private RetrofitInterface.AdminDataClient AdminDataAdapter;
    int openComplaints,overdueComplaints,closureComplaints;

    @BindView(R.id.progress_bar_blue)
    ProgressBar progressBlue;
    @BindView(R.id.progress_bar_pink)
    ProgressBar progressPink;
    @BindView(R.id.progress_bar_yellow)
    ProgressBar progressYellow;

    @BindView(R.id.total_consumers)
    TextView tvTotalConsumers;

    @BindView(R.id.open_complaints)
    TextView tvOpenComplaints;
    @BindView(R.id.overdue_complaint)
    TextView tvOverDueComplaints;
    @BindView(R.id.closure_complaint)
    TextView tvClosureComplaints;

    @BindView(R.id.new_complaints)
    TextView tvNewComplaints;
    @BindView(R.id.complaints_to_be_closed_today)
    TextView tvToBeClosedToday;

    @BindView(R.id.spare_parts_requested)
    TextView tvSparePartsRequested;
    @BindView(R.id.spare_parts_pending)
    TextView tvSparePartsPending;
    @BindView(R.id.spare_parts_to_be_closed_today)
    TextView tvSparePartsToBeClosedToday;





    private int blueprogressStatus = 0;
    private int pinkprogressStatus = 0;
    private int yellowprogressStatus = 0;

    private int openprogressStatus = 0;
    private int overdueprogressStatus = 0;
    private int closureprogressStatus = 0;


    private Handler handler = new Handler();
    @OnClick(R.id.new_complaints_button)
    public void newComplaintList()
    {
      Intent i = new Intent(getActivity(), NewComplaintListActivity.class);
        getActivity().startActivity(i);
    }


    @OnClick(R.id.ll_spare_pats_pendingg)
    public void sparePartsPending()
    {
        Intent i = new Intent(getActivity(), NewSparePartsPendingActivity.class);
        getActivity().startActivity(i);
    }

    @OnClick(R.id.spare_part_button)
    public void sparePartsRequested()
    {
        Intent i = new Intent(getActivity(), SparePartsRequestedActivity.class);
        getActivity().startActivity(i);
    }



    public ProfileHomePageFragment() {
        // Required empty public constructor
    }


    private void setUpRestAdapter() {
        AdminDataAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.AdminDataClient.class, MAIN_BASE_URL, getActivity());
    }


    private void getDashboardData() {
        LoadingDialog.showLoadingDialog(getActivity(),"Loading...");
        Call<DashboardDataResponse> call = AdminDataAdapter.DashboardDataList("adminsummary");
        if (NetworkUtils.isNetworkConnected(getActivity())) {
            call.enqueue(new Callback<DashboardDataResponse>() {

                @Override
                public void onResponse(Call<DashboardDataResponse> call, Response<DashboardDataResponse> response) {

                    if (response.isSuccessful()) {

                        if (response.body().getSuccess().equals("true")) {
                            Log.e(TAG, "onResponse: ..............admin data" +response.body().getAdminSummary().size() );
                            for (int i=0; i<response.body().getAdminSummary().size(); i++) {

                                openComplaints =response.body().getAdminSummary().get(i).getTotalOpencomplaints();
                                overdueComplaints =response.body().getAdminSummary().get(i).getTotalOverduecomplaints();
                                closureComplaints =response.body().getAdminSummary().get(i).getTotalClosedcomplaints();

                                tvTotalConsumers.setText(String.valueOf(response.body().getAdminSummary().get(i).getTotalConsumer()));

                                tvOpenComplaints.setText(String.valueOf(response.body().getAdminSummary().get(i).getTotalOpencomplaints()));
                                tvOverDueComplaints.setText(String.valueOf(response.body().getAdminSummary().get(i).getTotalOverduecomplaints()));
                                tvClosureComplaints.setText(String.valueOf(response.body().getAdminSummary().get(i).getTotalClosedcomplaints()));

                                tvNewComplaints.setText(String.valueOf(response.body().getAdminSummary().get(i).getTotalComplaints()));
                                tvToBeClosedToday.setText(String.valueOf(response.body().getAdminSummary().get(i).getTotalComplaintstobeclosedbytoday()));

                                tvSparePartsRequested.setText(String.valueOf(response.body().getAdminSummary().get(i).getTotalSparepartsRequested()));
                                tvSparePartsPending.setText(String.valueOf(response.body().getAdminSummary().get(i).getTotalSparepartsPending()));
                                tvSparePartsToBeClosedToday.setText(String.valueOf(response.body().getAdminSummary().get(i).getTotalSparepartstobeclosedbytoday()));
                                setProgressBar(response);

                                LoadingDialog.cancelLoading();
                            }
                        }
                        else
                        {
                            LoadingDialog.cancelLoading();

                            Toast.makeText(getActivity(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
                        }



                    }

                }

                @Override
                public void onFailure(Call<DashboardDataResponse> call, Throwable t) {
                    Toast.makeText(getActivity(),"Error occur",Toast.LENGTH_SHORT).show();
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(getActivity());
        }
    }

    private void setProgressBar(Response<DashboardDataResponse> response) {
         if (openComplaints>overdueComplaints && openComplaints>closureComplaints)
         {
             Log.e(TAG, "setProgressBar: " +overdueComplaints  + openComplaints + closureComplaints );
             openprogressStatus =100;
             overdueprogressStatus = (overdueComplaints*100)/openComplaints;
             closureprogressStatus = (closureComplaints*100)/openComplaints;

         }
         else if (overdueComplaints>openComplaints && overdueComplaints>closureComplaints)
         {
             overdueprogressStatus =100;
             openprogressStatus = (openComplaints*100)/overdueComplaints;
             Log.e(TAG, "setProgressBar: open"+openprogressStatus );
             closureprogressStatus = (closureComplaints*100)/overdueComplaints;
             Log.e(TAG, "setProgressBar: closure"+closureprogressStatus );
         }
         else {
             closureprogressStatus =100;
             overdueprogressStatus = (overdueComplaints*100)/closureComplaints;
             Log.e(TAG, "setProgressBar: overdue"+overdueprogressStatus );
             openprogressStatus = (openComplaints*100)/closureComplaints;
             Log.e(TAG, "setProgressBar: open"+openprogressStatus );
         }

        new Thread(new Runnable() {
            public void run() {
                while (blueprogressStatus <= closureprogressStatus) {
                    blueprogressStatus += 1;
                    //Update progress bar with completion of operation
                    handler.post(new Runnable() {
                        public void run() {
                            Log.e(TAG, "run: ...............closure" +blueprogressStatus );
                            progressBlue.setProgress(blueprogressStatus);

                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        //Just to display the progress slowly
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                while ( pinkprogressStatus<= openprogressStatus) {
                    pinkprogressStatus += 1;
                    //Update progress bar with completion of operation
                    handler.post(new Runnable() {
                        public void run() {
                            Log.e(TAG, "run: ...............open" +pinkprogressStatus );
                            progressPink.setProgress(pinkprogressStatus);

                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        //Just to display the progress slowly
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


        new Thread(new Runnable() {
            public void run() {
                while (yellowprogressStatus <= overdueprogressStatus) {
                    yellowprogressStatus += 1;
                    //Update progress bar with completion of operation
                    handler.post(new Runnable() {
                        public void run() {
                            Log.e(TAG, "run: ...............overdue" +yellowprogressStatus );
                            progressYellow.setProgress(yellowprogressStatus);

                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        //Just to display the progress slowly
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_home_page, container, false);
        ButterKnife.bind(this, view);
        setUpRestAdapter();
        getDashboardData();
        // Start long running operation in a background thread
       // progressBlue.setMax(100); // 100 maximum value for the progress value
       // progressBlue.setProgress(50); // 50 default progress value for the progress bar


        return view;

    }


}