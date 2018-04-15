package com.solarprojectapp.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.solarprojectapp.R;
import com.solarprojectapp.api.ApiAdapter;
import com.solarprojectapp.api.RetrofitInterface;
import com.solarprojectapp.generated.model.ComplaintListsDatum;
import com.solarprojectapp.generated.model.NewComplaintResponse;
import com.solarprojectapp.generated.model.PreventiveDateListsDatum;
import com.solarprojectapp.generated.model.PreventiveMaintainanceResponse;
import com.solarprojectapp.ui.activities.AddComplaintActivity;
import com.solarprojectapp.ui.activities.BreakdownActivity;
import com.solarprojectapp.ui.activities.CustomTecnicalPartnerTabActivity;
import com.solarprojectapp.ui.activities.MyProfileActivity;
import com.solarprojectapp.ui.activities.NavigationalActivity;
import com.solarprojectapp.ui.activities.SolarProjectLoginActivity;
import com.solarprojectapp.utils.LoadingDialog;
import com.solarprojectapp.utils.NetworkUtils;
import com.solarprojectapp.utils.PrefUtils;
import com.solarprojectapp.utils.SnakBarUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.solarprojectapp.api.ApiEndPoints.MAIN_BASE_URL;


public class ProfileCustomerPageFragment extends Fragment implements View.OnClickListener ,OnDateSelectedListener {
    private static final String TAG = "ProfileHomePageFragment";

    private RetrofitInterface.PreventiveMaintainanceClient UsePreventiveMaintainanceAdapter;
    @BindView(R.id.information)
    LinearLayout llInformation;

    @BindView(R.id.maintenance_data)
    LinearLayout llMaintenancedData;

    @BindView(R.id.selected_complaint_name)
    TextView tvSelectedComplaint;



    @BindView(R.id.calendarView)
    com.prolificinteractive.materialcalendarview.MaterialCalendarView mCalendarView;


    @BindView(R.id.text_maintainence)
    TextView tvMaintenancedData;

    @BindView(R.id.text_information)
    TextView tvInformationData;

    @BindView(R.id.preventive_maintainace)
    LinearLayout llImageMaintenance;

   /* @BindView(R.id.breakdown)
    LinearLayout llBreakdown;*/

    Boolean showInformation = true;
    String apiType;
    ArrayList<PreventiveDateListsDatum> preventiveMainatainanceDateLists =null;
    ArrayList<String> setDates =null;


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
            Log.e(TAG, "preventiveMaintaince: ........................................................" );

            llImageMaintenance.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.solar_pink_gradient));
            llInformation.setVisibility(View.GONE);
            tvInformationData.setVisibility(View.GONE);

            llMaintenancedData.setVisibility(View.VISIBLE);
            tvMaintenancedData.setVisibility(View.VISIBLE);
            setUpRestAdapter();
            getPreventiveMaintainanceList();

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

        return view;

    }

    private void setUpRestAdapter() {
        UsePreventiveMaintainanceAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.PreventiveMaintainanceClient.class, MAIN_BASE_URL, getContext());
    }


    private void getPreventiveMaintainanceList() {

        if (PrefUtils.getUserType(getContext()).equals("End Consumer"))
            apiType ="preventivedate";
        else
            apiType ="preventivedatefortechnicalpartner";

        LoadingDialog.showLoadingDialog(getContext(),"Loading...");
        Call<PreventiveMaintainanceResponse> call = UsePreventiveMaintainanceAdapter.preventiveMaintainanceList( PrefUtils.getFkId(getContext()),apiType);
        if (NetworkUtils.isNetworkConnected(getContext())) {
            call.enqueue(new Callback<PreventiveMaintainanceResponse>() {

                @Override
                public void onResponse(Call<PreventiveMaintainanceResponse> call, Response<PreventiveMaintainanceResponse> response) {

                    if (response.isSuccessful()) {

                        if (response.body().getSuccess().equals("true")) {
                           setDateInCalender(response);
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
                public void onFailure(Call<PreventiveMaintainanceResponse> call, Throwable t) {
                    Toast.makeText(getContext(),"Error occur",Toast.LENGTH_SHORT).show();
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(getContext());
        }
    }

    private void setDateInCalender(Response<PreventiveMaintainanceResponse> response) {
        preventiveMainatainanceDateLists = new ArrayList<>();
        setDates = new ArrayList<>();
        for (int i = 0; i < response.body().getPreventiveDateListsData().size(); i++) {
            for (int j = 0; j < response.body().getPreventiveDateListsData().get(i).size();j++) {
               // Log.e(TAG, "setDateInCalender: .......size"+response.body().getPreventiveDateListsData().get(i).size() );

                PreventiveDateListsDatum preventiveDateListsDatum = new PreventiveDateListsDatum();
                preventiveDateListsDatum.setComplaint(response.body().getPreventiveDateListsData().get(i).get(j).getComplaint());
                preventiveDateListsDatum.setInstallationDate(response.body().getPreventiveDateListsData().get(i).get(j).getInstallationDate());
                preventiveDateListsDatum.setCreateDate(response.body().getPreventiveDateListsData().get(i).get(j).getCreateDate());
                preventiveDateListsDatum.setComplainCloseDate(response.body().getPreventiveDateListsData().get(i).get(j).getComplainCloseDate());
                preventiveDateListsDatum.setComplainCloseDate(response.body().getPreventiveDateListsData().get(i).get(j).getComplainCloseDate());

                preventiveMainatainanceDateLists.add(preventiveDateListsDatum);
                setDates.add(response.body().getPreventiveDateListsData().get(i).get(j).getComplainCloseDate());


            }
        }
        setClosingDateInCalendar(setDates);
    }

    private void setClosingDateInCalendar(ArrayList<String> s) {

        for (int i = 0; i < s.size(); i++) {
            if (!s.get(i).equals("") && s.get(i) != null) {

                String date = parseTodaysDate(s.get(i));
                Log.e(TAG, "setClosingDateInCalendar: ............."+date );
                String parts[] = date.split("/");
                int day = Integer.parseInt(parts[0]);
                int month = Integer.parseInt(parts[1])-1;
                int year = Integer.parseInt(parts[2]);

                /*Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);*/

               // long milliTime = calendar.getTimeInMillis();

                mCalendarView.setDateSelected(CalendarDay.from(year, month, day), true);
            }


        }
    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(getContext(), AddComplaintActivity.class);
        startActivity(i);

    }

    public static String parseTodaysDate(String time) {



        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        String outputPattern = "dd/MM/yyyy";

        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);

            Log.i("mini", "Converted Date Today:" + str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }


    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        Log.e(TAG, "onDateSelected: ......................." );
          tvSelectedComplaint.setText("---------------Got it---------------");
    }
}