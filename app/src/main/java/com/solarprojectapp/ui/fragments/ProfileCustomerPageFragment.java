package com.solarprojectapp.ui.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.icu.text.RelativeDateTimeFormatter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.solarprojectapp.R;
import com.solarprojectapp.api.ApiAdapter;
import com.solarprojectapp.api.RetrofitInterface;
import com.solarprojectapp.generated.model.PreventiveDateListsDatum;
import com.solarprojectapp.generated.model.PreventiveMaintainanceResponse;
import com.solarprojectapp.ui.activities.AddComplaintActivity;
import com.solarprojectapp.ui.activities.BreakdownActivity;
import com.solarprojectapp.ui.activities.CustomTecnicalPartnerTabActivity;
import com.solarprojectapp.utils.LoadingDialog;
import com.solarprojectapp.utils.NetworkUtils;
import com.solarprojectapp.utils.PrefUtils;
import com.solarprojectapp.utils.SnakBarUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.solarprojectapp.api.ApiEndPoints.MAIN_BASE_URL;


public class ProfileCustomerPageFragment extends Fragment implements View.OnClickListener ,OnDateSelectedListener,OnChartGestureListener{
    private static final String TAG = "ProfileHomePageFragment";

    private RetrofitInterface.PreventiveMaintainanceClient UsePreventiveMaintainanceAdapter;
    //private final OneDayDecorator oneDayDecorator = new OneDayDecorator();
    private TextView complaintName = null;


    @BindView(R.id.information)
    LinearLayout llInformation;

    @BindView(R.id.maintenance_data)
    LinearLayout llMaintenancedData;

    /*@BindView(R.id.ll_complaintList)
    LinearLayout llComplaintListItems;*/



    @BindView(R.id.calendarView)
    com.prolificinteractive.materialcalendarview.MaterialCalendarView mCalendarView;


    @BindView(R.id.rl_project_owner)
    RelativeLayout rlProjectOwner;

    @BindView(R.id.text_maintainence)
    TextView tvMaintenancedData;

    @BindView(R.id.text_information)
    TextView tvInformationData;

    @BindView(R.id.project_name)
    TextView tvProjectName;

    @BindView(R.id.project_type)
    TextView tvProjectType;

    @BindView(R.id.project_owner)
    TextView tvProjectOwner;

    @BindView(R.id.graphdatatext)
    TextView tvGraphDtaText;

    @BindView(R.id.test_image)
    ImageView ivTestImage;

    @BindView(R.id.endconsumeregraph)
    LineChart ivEndConsumerGraph;

    String loginType;


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

         //  llImageMaintenance.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.solar_pink_gradient));
           // llImageMaintenance.setCardBackgroundColor(ContextCompat.getColor(getActivity(), R.color.solar_pink_gradient));
            llInformation.setVisibility(View.GONE);
            tvInformationData.setVisibility(View.GONE);

            llMaintenancedData.setVisibility(View.VISIBLE);
            tvMaintenancedData.setVisibility(View.VISIBLE);
            setUpRestAdapter();
            getPreventiveMaintainanceList();

            showInformation =false;
        }
        else {
           // llImageMaintenance.setCardBackgroundColor(ContextCompat.getColor(getActivity(), R.color.solar_pink));
            //llImageMaintenance.setBackgroundColor(ContextCompat.getDrawable(getActivity(), R.drawable.circular_overdue_complaints_background));

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
       if( PrefUtils.getUserType(getContext()).equals("End Consumer"))
        {

            tvProjectName.setVisibility(View.VISIBLE);
            tvProjectType.setVisibility(View.VISIBLE);
            tvProjectOwner.setVisibility(View.VISIBLE);
            rlProjectOwner.setVisibility(View.VISIBLE);
            ivEndConsumerGraph.setVisibility(View.VISIBLE);
            tvGraphDtaText.setVisibility(View.VISIBLE);
            ivTestImage.setVisibility(View.VISIBLE);

            tvProjectType.setText(PrefUtils.getProject(getContext()));
            tvProjectName.setText(PrefUtils.getProjectOwner(getContext()));
            ivEndConsumerGraph.setOnChartGestureListener(this);
           // ivEndConsumerGraph.setOnChartValueSelectedListener(this);
            ivEndConsumerGraph.setDrawGridBackground(false);

            // add data
            setData();

            // get the legend (only possible after setting data)
            Legend l = ivEndConsumerGraph.getLegend();

            // modify the legend ...
            // l.setPosition(LegendPosition.LEFT_OF_CHART);
            l.setForm(Legend.LegendForm.LINE);

            // no description text
            ivEndConsumerGraph.setDescription("Time");
            ivEndConsumerGraph.setNoDataTextDescription("You need to provide data for the chart.");

            // enable touch gestures
            ivEndConsumerGraph.setTouchEnabled(false);

            // enable scaling and dragging
            ivEndConsumerGraph.setDragEnabled(true);
            ivEndConsumerGraph.setScaleEnabled(true);
            YAxis leftAxis = ivEndConsumerGraph.getAxisLeft();
            leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines

            leftAxis.setAxisMaxValue(220f);
            leftAxis.setAxisMinValue(0f);
            //leftAxis.setYOffset(20f);
            leftAxis.enableGridDashedLine(10f, 10f, 0f);
            leftAxis.setDrawZeroLine(false);

            // limit lines are drawn behind data (and not on top)
            leftAxis.setDrawLimitLinesBehindData(true);

            ivEndConsumerGraph.getAxisRight().setEnabled(false);
            ivEndConsumerGraph.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

            ivEndConsumerGraph.getXAxis().setSpaceBetweenLabels(1);

            ivEndConsumerGraph.animateX(2500, Easing.EasingOption.EaseInOutQuart);

            //  dont forget to refresh the drawing
            ivEndConsumerGraph.invalidate();





        }


        return view;

    }




    // This is used to store x-axis values
    private ArrayList<String> setXAxisValues(){
        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("09:00");
        xVals.add("10:00");
        xVals.add("11:00");
        xVals.add("12:00");
        xVals.add("13:00");
        xVals.add("14:00");
        xVals.add("15:00");
        xVals.add("16:00");
        xVals.add("17:00");
        xVals.add("18:00");


        return xVals;
    }

    // This is used to store Y-axis values
    private ArrayList<Entry> setYAxisValues(){
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        yVals.add(new Entry(0f, 0));
        yVals.add(new Entry(28, 1));
        yVals.add(new Entry(50.5f, 2));
        yVals.add(new Entry(80, 3));
        yVals.add(new Entry(160.9f, 4));
        yVals.add(new Entry(80f, 5));
        yVals.add(new Entry(50.5f, 6));
        yVals.add(new Entry(28f, 7));
        yVals.add(new Entry(40f, 8));
        yVals.add(new Entry(0f, 9));

        return yVals;
    }

    private ArrayList<Entry> setYAxisValues1(){
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        yVals.add(new Entry(0f, 0));
        yVals.add(new Entry(55, 1));
        yVals.add(new Entry(80.5f, 2));
        yVals.add(new Entry(110, 3));
        yVals.add(new Entry(190.9f, 4));
        yVals.add(new Entry(110, 5));
        yVals.add(new Entry(80.5f, 6));
        yVals.add(new Entry(55f, 7));
        yVals.add(new Entry(65f, 8));
        yVals.add(new Entry(0f, 9));

        return yVals;
    }


    private void setData() {
        ArrayList<String> xVals = setXAxisValues();

        ArrayList<Entry> yValseEstimated = setYAxisValues1();

        LineDataSet set1;
        set1 = new LineDataSet(yValseEstimated, "Estimated Power(kW)");
        set1.setFillAlpha(110);
        set1.setFillColor(Color.LTGRAY);

        // set the line to be drawn like this "- - - - - -"
        // set1.enableDashedLine(10f, 5f, 0f);
        // set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.setColor(Color.LTGRAY);
        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(7f);
        set1.setDrawFilled(true);
        // create a dataset and give it a type

        ArrayList<Entry> yVals = setYAxisValues();

        LineDataSet set2;
        set2 = new LineDataSet(yVals, "Current Power(kW)");
        set2.setFillAlpha(110);
         //set2.setFillColor(Color.RED);

        // set the line to be drawn like this "- - - - - -"
        // set1.enableDashedLine(10f, 5f, 0f);
        // set1.enableDashedHighlightLine(10f, 5f, 0f);
        //set2.setColor(Color.BLACK);
        set2.setCircleColor(Color.BLACK);
        set2.setLineWidth(1f);
        set2.setCircleRadius(3f);
        set2.setDrawCircleHole(false);
        set2.setValueTextSize(7f);
        set2.setDrawFilled(true);

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set1);
        dataSets.add(set2);// add the datasets

        // create a data object with the datasets
       LineData data = new LineData( xVals,dataSets);

        ivEndConsumerGraph.setData(data);




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
                /*if (!response.body().getPreventiveDateListsData().get(i).get(j).getComplaint().equals("")&&response.body().getPreventiveDateListsData().get(i).get(j).getComplaint() !=null
                        &&!response.body().getPreventiveDateListsData().get(i).get(j).getComplainCloseDate().equals("")&&response.body().getPreventiveDateListsData().get(i).get(j).getComplainCloseDate() !=null) {

                    complaintName = new TextView(getContext());
                    complaintName.setText(response.body().getPreventiveDateListsData().get(i).get(j).getComplaint().concat(" ").concat(parseTodaysDate(response.body().getPreventiveDateListsData().get(i).get(j).getComplainCloseDate())));
                    llComplaintListItems.addView(complaintName);
                }*/
                PreventiveDateListsDatum preventiveDateListsDatum = new PreventiveDateListsDatum();
                preventiveDateListsDatum.setComplaint(response.body().getPreventiveDateListsData().get(i).get(j).getComplaint());
              /*  if (!response.body().getPreventiveDateListsData().get(i).get(j).getInstallationDate().equals("")&&response.body().getPreventiveDateListsData().get(i).get(j).getInstallationDate() !=null)
                {
                    String installationDate =parseTodaysDate(response.body().getPreventiveDateListsData().get(i).get(j).getInstallationDate());
                    Log.e(TAG, "setDateInCalender: .......installation date"+installationDate );
                    String parts[] = installationDate.split("/");
                    int day = Integer.parseInt(parts[0]);
                    int month = Integer.parseInt(parts[1])-1;
                    int year = Integer.parseInt(parts[2]);

                    mCalendarView.setDateSelected(CalendarDay.from(year, month, day), true);
                }*/
                preventiveDateListsDatum.setInstallationDate(response.body().getPreventiveDateListsData().get(i).get(j).getInstallationDate());
                Log.e(TAG, "installation date: ............"+response.body().getPreventiveDateListsData().get(i).get(j).getInstallationDate() );
                preventiveDateListsDatum.setCreateDate(response.body().getPreventiveDateListsData().get(i).get(j).getCreateDate());
                preventiveDateListsDatum.setComplainCloseDate(response.body().getPreventiveDateListsData().get(i).get(j).getComplainCloseDate());
               // preventiveDateListsDatum.setComplainCloseDate(response.body().getPreventiveDateListsData().get(i).get(j).getComplainCloseDate());

                preventiveMainatainanceDateLists.add(preventiveDateListsDatum);
                setDates.add(response.body().getPreventiveDateListsData().get(i).get(j).getComplainCloseDate());
                setDates.add(response.body().getPreventiveDateListsData().get(i).get(j).getInstallationDate());


            }
        }

        setClosingDateInCalendar(setDates);
    }

    private void setClosingDateInCalendar(ArrayList<String> s) {

        for (int i = 0; i < s.size(); i++) {
            if (!s.get(i).equals("") && s.get(i) != null) {

                String date = parseTodaysDate(s.get(i));
                //Log.e(TAG, "setClosingDateInCalendar: ............."+date );
                String parts[] = date.split("/");
                int day = Integer.parseInt(parts[0]);
                int month = Integer.parseInt(parts[1])-1;
                int year = Integer.parseInt(parts[2]);

                mCalendarView.setDateSelected(CalendarDay.from(year, month, day), true);
               mCalendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_NONE);




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
       /* Log.e(TAG, "onDateSelected: ......................." );
          tvSelectedComplaint.setText("---------------Got it---------------");*/
    }



    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "START, x: " + me.getX() + ", y: " + me.getY());
    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "END, lastGesture: " + lastPerformedGesture);

        // un-highlight values after the gesture is finished and no single-tap
        if(lastPerformedGesture != ChartTouchListener.ChartGesture.SINGLE_TAP)
            ivEndConsumerGraph.highlightValues(null); // or highlightTouch(null) for callback to onNothingSelected(...)
    }

    @Override
    public void onChartLongPressed(MotionEvent me) {
        Log.i("LongPress", "Chart longpressed.");
    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {
        Log.i("DoubleTap", "Chart double-tapped.");
    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
        Log.i("SingleTap", "Chart single-tapped.");
    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
        Log.i("Fling", "Chart flinged. VeloX: " + velocityX + ", VeloY: " + velocityY);
    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        Log.i("Scale / Zoom", "ScaleX: " + scaleX + ", ScaleY: " + scaleY);
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
        Log.i("Translate / Move", "dX: " + dX + ", dY: " + dY);
    }



}