package com.solarprojectapp.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.solarprojectapp.R;
import com.solarprojectapp.api.ApiAdapter;
import com.solarprojectapp.api.RetrofitInterface;
import com.solarprojectapp.generated.model.ConsumerCountClientResponse;
import com.solarprojectapp.generated.model.DashboardDataResponse;
import com.solarprojectapp.generated.model.NewComplaintResponse;
import com.solarprojectapp.ui.activities.ClosureComplaintListActivity;
import com.solarprojectapp.ui.activities.ComplaintsToBeClosedTodayActivity;
import com.solarprojectapp.ui.activities.NewComplaintListActivity;
import com.solarprojectapp.ui.activities.NewSparePartsPendingActivity;
import com.solarprojectapp.ui.activities.OpenComplaintListActivity;
import com.solarprojectapp.ui.activities.OverDueComplaintListActivity;
import com.solarprojectapp.ui.activities.RejectedComplaintListActivity;
import com.solarprojectapp.ui.activities.SparePartsRequestedActivity;
import com.solarprojectapp.ui.activities.SparePartsToBeClosedTodayActivity;
import com.solarprojectapp.ui.activities.TotalConsumerListActivity;
import com.solarprojectapp.utils.LoadingDialog;
import com.solarprojectapp.utils.NetworkUtils;
import com.solarprojectapp.utils.PrefUtils;
import com.solarprojectapp.utils.SnakBarUtils;

import java.util.ArrayList;
import java.util.List;

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
    private RetrofitInterface.TotalConsumerCountForClient TotalConsumerCountForClientDataAdapter;
    int openComplaints,overdueComplaints,closureComplaints;

    @BindView(R.id.progress_bar_blue)
    ProgressBar progressBlue;
    @BindView(R.id.progress_bar_pink)
    ProgressBar progressPink;
    @BindView(R.id.progress_bar_yellow)
    ProgressBar progressYellow;

    @BindView(R.id.total_consumers)
    TextView tvTotalConsumers;

    @BindView(R.id.admingraph)
    com.github.mikephil.charting.charts.BarChart bcAdminGraph ;


    @BindView(R.id.open_complaints)
    TextView tvOpenComplaints;
    @BindView(R.id.overdue_complaint)
    TextView tvOverDueComplaints;
    @BindView(R.id.closure_complaint)
    TextView tvClosureComplaints;

    @BindView(R.id.new_complaints)
    TextView tvNewComplaints;

    @BindView(R.id.rejected_complaints_count)
    TextView tvRejectedComplaints;

    @BindView(R.id.complaints_to_be_closed_today)
    TextView tvToBeClosedToday;

    @BindView(R.id.spare_parts_requested)
    TextView tvSparePartsRequested;
    @BindView(R.id.spare_parts_pending)
    TextView tvSparePartsPending;
    @BindView(R.id.spare_parts_to_be_closed_today)
    TextView tvSparePartsToBeClosedToday;

    String consumerCount;





    private int blueprogressStatus = 0;
    private int pinkprogressStatus = 0;
    private int yellowprogressStatus = 0;

    private int openprogressStatus = 0;
    private int overdueprogressStatus = 0;
    private int closureprogressStatus = 0;


    private Handler handler = new Handler();
    @OnClick(R.id.ll_new_complaint)
    public void newComplaintList()
    {
      Intent i = new Intent(getActivity(), NewComplaintListActivity.class);
        getActivity().startActivity(i);
    }

    @OnClick(R.id.ll_total_consumers)
    public void totalConsumers()
    {
        Intent i = new Intent(getActivity(), TotalConsumerListActivity.class);
        getActivity().startActivity(i);
    }

    @OnClick(R.id.ll_rejected_complaints)
    public void rejectedComplaints()
    {
        Intent i = new Intent(getActivity(), RejectedComplaintListActivity.class);
        getActivity().startActivity(i);
    }

    @OnClick(R.id.ll_spare_pats_pendingg)
    public void sparePartsPending()
    {
        Intent i = new Intent(getActivity(), NewSparePartsPendingActivity.class);
        getActivity().startActivity(i);
    }

    @OnClick(R.id.ll_spare_part_request)
    public void sparePartsRequested()
    {
        Intent i = new Intent(getActivity(), SparePartsRequestedActivity.class);
        getActivity().startActivity(i);
    }

    @OnClick(R.id.ll_overdue)
    public void overDueList()
    {
        Intent i = new Intent(getActivity(), OverDueComplaintListActivity.class);
        getActivity().startActivity(i);
    }

    @OnClick(R.id.ll_open)
    public void openList()
    {
        Intent i = new Intent(getActivity(), OpenComplaintListActivity.class);
        getActivity().startActivity(i);
    }


    @OnClick(R.id.ll_closure)
    public void closureList()
    {
        Intent i = new Intent(getActivity(), ClosureComplaintListActivity.class);
        getActivity().startActivity(i);
    }



    @OnClick(R.id.ll_spare_parts_to_be_closed_today)
    public void sparePartsToBeClosed()
    {
        Intent i = new Intent(getActivity(), SparePartsToBeClosedTodayActivity.class);
        getActivity().startActivity(i);
    }

    @OnClick(R.id.ll_complaints_to_be_closed_today)
    public void complaintsToBeClosed()
    {
        Intent i = new Intent(getActivity(), ComplaintsToBeClosedTodayActivity.class);
        getActivity().startActivity(i);
    }



    public ProfileHomePageFragment() {
        // Required empty public constructor
    }


    private void setUpRestAdapter() {
        AdminDataAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.AdminDataClient.class, MAIN_BASE_URL, getActivity());
        TotalConsumerCountForClientDataAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.TotalConsumerCountForClient.class, MAIN_BASE_URL, getActivity());

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

                                if (PrefUtils.getUserType(getContext()).equals("Admin"))
                                {
                                    tvTotalConsumers.setText(String.valueOf(response.body().getAdminSummary().get(i).getTotalConsumer()));

                                }
                                 tvRejectedComplaints.setText(String.valueOf(response.body().getAdminSummary().get(i).getTotalRejectedcomplaints()));
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

    private void getConsumerCountClient() {
        LoadingDialog.showLoadingDialog(getActivity(),"Loading...");
        Call<ConsumerCountClientResponse> call = TotalConsumerCountForClientDataAdapter.totalConsumerCountClient(PrefUtils.getFkId(getContext()),"consumercount");
        if (NetworkUtils.isNetworkConnected(getActivity())) {
            call.enqueue(new Callback<ConsumerCountClientResponse>() {

                @Override
                public void onResponse(Call<ConsumerCountClientResponse> call, Response<ConsumerCountClientResponse> response) {

                    if (response.isSuccessful()) {

                        if (response.body().getSuccess().equals("true")) {
                            Log.e(TAG, "onResponse: ..............admin data" +response.body().getClientConsumerCount().size() );
                            for (int i=0; i<response.body().getClientConsumerCount().size(); i++) {
                                tvTotalConsumers.setText(String.valueOf(response.body().getClientConsumerCount().get(i).getTotalConsumer()));

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
                public void onFailure(Call<ConsumerCountClientResponse> call, Throwable t) {
                    Toast.makeText(getActivity(),"Error occur",Toast.LENGTH_SHORT).show();
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(getActivity());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpRestAdapter();
        getDashboardData();
    }

    private void setProgressBar(Response<DashboardDataResponse> response) {
         if (openComplaints>overdueComplaints && openComplaints>closureComplaints)
         {
             if(openComplaints == 0)
             {
                 openComplaints =1;
                 openprogressStatus =0;
             }
             else
                 openprogressStatus =100;

             overdueprogressStatus = (overdueComplaints*100)/openComplaints;
             closureprogressStatus = (closureComplaints*100)/openComplaints;

         }
         else if (overdueComplaints>openComplaints && overdueComplaints>closureComplaints)
         {
             if (overdueComplaints ==0)
             {
                 overdueComplaints =1;
                 overdueprogressStatus =0;
             }
             else
             overdueprogressStatus =100;

             openprogressStatus = (openComplaints*100)/overdueComplaints;
             Log.e(TAG, "setProgressBar: open"+openprogressStatus );
             closureprogressStatus = (closureComplaints*100)/overdueComplaints;
             Log.e(TAG, "setProgressBar: closure"+closureprogressStatus );
         }
         else {
             if (closureComplaints ==0)
             {
                 closureComplaints =1;
                 closureprogressStatus =0;
             }
             else
             closureprogressStatus =100;

             overdueprogressStatus = (overdueComplaints*100)/closureComplaints;
             Log.e(TAG, "setProgressBar: overdue"+overdueprogressStatus );
             openprogressStatus = (openComplaints*100)/closureComplaints;
             Log.e(TAG, "setProgressBar: open"+openprogressStatus );
         }

        new Thread(new Runnable() {
            public void run() {
                 while (blueprogressStatus <= closureprogressStatus) {
                    blueprogressStatus += 5;
                    //Update progress bar with completion of operation
                    handler.post(new Runnable() {
                        public void run() {
                            Log.e(TAG, "run: ...............closure" +closureprogressStatus );
                            if (closureprogressStatus!=0) {
                                progressBlue.setProgress(blueprogressStatus);
                            }

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
                    pinkprogressStatus += 5;
                    //Update progress bar with completion of operation
                    handler.post(new Runnable() {
                        public void run() {
                            Log.e(TAG, "run: ...............open" +openprogressStatus );
                            if (openprogressStatus!=0) {
                                progressPink.setProgress(pinkprogressStatus);
                            }

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
                    yellowprogressStatus += 5;
                    //Update progress bar with completion of operation
                    handler.post(new Runnable() {
                        public void run() {
                            Log.e(TAG, "run: ...............overdue" +overdueprogressStatus );
                            if (overdueprogressStatus !=0) {
                                progressYellow.setProgress(yellowprogressStatus);
                            }

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
        if (PrefUtils.getUserType(getContext()).equals("Client"))
        {
            getConsumerCountClient();
        }

        if (PrefUtils.getUserType(getContext()).equals("Admin"))
        {
            bcAdminGraph.setVisibility(View.VISIBLE);
            List<BarEntry> entries = new ArrayList<>();
            entries.add(new BarEntry( 5f, 2f));
            entries.add(new BarEntry(6f, 1f));
            entries.add(new BarEntry( 7f, 3f));
            entries.add(new BarEntry( 8f, 3f));
            entries.add(new BarEntry( 9f, 1f));
            entries.add(new BarEntry(10f, 2f));
            entries.add(new BarEntry(11f, 1f));

            BarDataSet set = new BarDataSet(entries, "Yield(MWh)");
            BarData data = new BarData(set);
           // data.setBarWidth(0.9f); // set custom bar width
            bcAdminGraph.setData(data);
            bcAdminGraph.setBackgroundResource(R.color.white);
            bcAdminGraph.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
            YAxis rightYAxis = bcAdminGraph.getAxisRight();
            bcAdminGraph.getDescription().setEnabled(false);
            bcAdminGraph.getLegend().setEnabled(true);
            rightYAxis.setEnabled(false);
            bcAdminGraph.setFitBars(true); // make the x-axis fit exactly all bars
            bcAdminGraph.invalidate(); // refresh
        }

        return view;

    }


}