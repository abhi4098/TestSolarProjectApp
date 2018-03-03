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
import com.solarprojectapp.ui.activities.NewComplaintListActivity;
import com.solarprojectapp.ui.activities.NewSparePartsPendingActivity;
import com.solarprojectapp.ui.activities.SparePartsRequestedActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ProfileHomePageFragment extends Fragment {
    private static final String TAG = "ProfileHomePageFragment";


    @BindView(R.id.progress_bar_blue)
    ProgressBar progressBlue;
    @BindView(R.id.progress_bar_pink)
    ProgressBar progressPink;
    @BindView(R.id.progress_bar_yellow)
    ProgressBar progressYellow;
    private int progressStatus = 0;

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


    /*private RetrofitInterface.UserWalletClient UserWalletAdapter;
    private RetrofitInterface.UserTransactionsClient MyTransactionAdapter;
    private RetrofitInterface.UserReceivedMoneyRequestClient UserReceivedMoneyRequestAdapter;


    @BindView(R.id.transactions)
    TextView tvTransaction;

    @BindView(R.id.received_request)
    TextView tvReceivedRequests;

    @OnClick(R.id.ll_pay_money)
    public void payMoney() {

        Toast.makeText(getApplicationContext(),"Feature will be implemented soon",Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.ll_request_money)
    public void requestMoney() {

        Intent activityChangeIntent = new Intent(getActivity(), RequestMoneyActivity.class);
        startActivity(activityChangeIntent);
    }

    @OnClick(R.id.ll_add_money)
    public void addMoney() {

        Intent activityChangeIntent = new Intent(getActivity(), AddMoneyActivity.class);
        activityChangeIntent.putExtra("PATH", "homeFragment");
        startActivity(activityChangeIntent);
    }

    @OnClick(R.id.ll_send_money)
    public void sendMoney() {

        Intent activityChangeIntent = new Intent(getActivity(), RecievedMoneyRequestActivity.class);
        startActivity(activityChangeIntent);
    }
*/
    public ProfileHomePageFragment() {
        // Required empty public constructor
    }


   /* @Override
    public void onResume() {
        super.onResume();
      *//*  QUERY_NOTIFICATION_COUNT = 0;
        PrefUtils.setActiveConsultationScreenVisible(getActivity(), true);
        getActivity().registerReceiver(networkReceiver, new IntentFilter("internet_connectivity_check"));
        getActivity().registerReceiver(queryReceiver, new IntentFilter(DocFirebaseMessagingService.INTENT_FILTER));
        getActivity().registerReceiver(chatMessageReceiver, new IntentFilter(ChatBroadCastReceiver.CHAT_FILTER));
        if (NetworkUtils.isNetworkConnected(getActivity())) {
            getOpenQuerynumber();
        } else {
            setScreenFromDatabse();
            if (getActivity() != null)
                SnakBarUtils.networkConnected(getActivity());
        }*//*
    }
*/

    /*@Override
    public void onPause() {
        super.onPause();
       *//* PrefUtils.setActiveConsultationScreenVisible(getActivity(), false);
        getActivity().unregisterReceiver(queryReceiver);
        getActivity().unregisterReceiver(chatMessageReceiver);
        getActivity().unregisterReceiver(networkReceiver);*//*
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_home_page, container, false);
        ButterKnife.bind(this, view);
        // Start long running operation in a background thread
       // progressBlue.setMax(100); // 100 maximum value for the progress value
       // progressBlue.setProgress(50); // 50 default progress value for the progress bar
        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 100) {
                    progressStatus += 5;
                    //Update progress bar with completion of operation
                    handler.post(new Runnable() {
                        public void run() {
                            progressBlue.setProgress(progressStatus);

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
                while (progressStatus < 50) {
                    progressStatus += 5;
                    //Update progress bar with completion of operation
                    handler.post(new Runnable() {
                        public void run() {
                            progressPink.setProgress(progressStatus);

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
                while (progressStatus < 20) {
                    progressStatus += 2;
                    //Update progress bar with completion of operation
                    handler.post(new Runnable() {
                        public void run() {
                            progressYellow.setProgress(progressStatus);

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


       /* setUpRestAdapter();
        getUserTransactions();
        getReceivedMoneyRequests();*/
        return view;

    }

/*
    private void getWalletBalance() {
        LoadingDialog.showLoadingDialog(getActivity(),"Loading...");
        Call<MyProfileResponse> call = UserWalletAdapter.userWallet(new MyProfile("walletbalance", PrefUtils.getUserId(getActivity()),"83Ide@$321!"));
        if (NetworkUtils.isNetworkConnected(getActivity())) {
            call.enqueue(new Callback<MyProfileResponse>() {

                @Override
                public void onResponse(Call<MyProfileResponse> call, Response<MyProfileResponse> response) {

                    if (response.isSuccessful()) {
                        Log.e(TAG, "onResponse: " +response.body() );
                        //tvWalletBalance.setText(response.body().toString());
                        LoadingDialog.cancelLoading();



                    }
                }

                @Override
                public void onFailure(Call<MyProfileResponse> call, Throwable t) {
                    Log.e(TAG, "onFailure: ------------" +t.toString());
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(getActivity());
            LoadingDialog.cancelLoading();
        }
    }

    private void setUpRestAdapter() {
        UserWalletAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.UserWalletClient.class, ApiEndPoints.BASE_URL, getActivity());
       // QueryNotificationAdapterForHome = ApiAdapter.createRestAdapter(RetrofitInterface.QueryNotificationClient.class, ApiEndPoints.BASE_URL, getActivity());
    }
*/


  /*  private void getUserTransactions() {
        LoadingDialog.showLoadingDialog(getActivity(),"Loading...");
        Call<MyTransactionsResponse> call = MyTransactionAdapter.userTransactions(new MyTransactions("usertransactions", PrefUtils.getUserId(getActivity()),"83Ide@$321!"));
        if (NetworkUtils.isNetworkConnected(getActivity())) {
            call.enqueue(new Callback<MyTransactionsResponse>() {

                @Override
                public void onResponse(Call<MyTransactionsResponse> call, Response<MyTransactionsResponse> response) {

                    if (response.isSuccessful()) {
                        Log.e(TAG, "onResponse: " +response.body().getUsertransactions().size() );
                        String transactions = String.valueOf(response.body().getUsertransactions().size());
                        tvTransaction.setText(transactions);
                        //setUserTransaction(response,view);
                        LoadingDialog.cancelLoading();



                    }
                }

                @Override
                public void onFailure(Call<MyTransactionsResponse> call, Throwable t) {
                    Log.e("abhi", "onFailure: my transactions------------" +t.toString());
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(getActivity());
            LoadingDialog.cancelLoading();
        }
    }
    private void setUpRestAdapter() {
        MyTransactionAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.UserTransactionsClient.class, BASE_URL, getActivity());
        UserReceivedMoneyRequestAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.UserReceivedMoneyRequestClient.class, ApiEndPoints.BASE_URL, getActivity());
    }

    private void getReceivedMoneyRequests() {
        LoadingDialog.showLoadingDialog(getActivity(),"Loading...");
        Call<ReceiveMoneyRequestsResponse> call = UserReceivedMoneyRequestAdapter.receiveMoneyRequestData(new ReceiveMoneyRequests("usermoneyrequests", PrefUtils.getUserId(getActivity()),"83Ide@$321!"));
        if (NetworkUtils.isNetworkConnected(getActivity())) {
            call.enqueue(new Callback<ReceiveMoneyRequestsResponse>() {

                @Override
                public void onResponse(Call<ReceiveMoneyRequestsResponse> call, Response<ReceiveMoneyRequestsResponse> response) {

                    if (response.isSuccessful()) {
                          if (response.body().getMsg().equals("success"))
                          {
                            String receivedRequests = String.valueOf(response.body().getReceivedrequests().size());
                            tvReceivedRequests.setText(receivedRequests);
                        }
                        LoadingDialog.cancelLoading();



                    }
                }

                @Override
                public void onFailure(Call<ReceiveMoneyRequestsResponse> call, Throwable t) {
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(getActivity());
            LoadingDialog.cancelLoading();
        }
    }
*/
}