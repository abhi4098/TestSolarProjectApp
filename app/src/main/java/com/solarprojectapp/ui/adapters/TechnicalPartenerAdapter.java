package com.solarprojectapp.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.solarprojectapp.R;
import com.solarprojectapp.api.ApiAdapter;
import com.solarprojectapp.api.RetrofitInterface;
import com.solarprojectapp.generated.model.ApproveComplaintResponse;
import com.solarprojectapp.generated.model.AssignComplaintResponse;
import com.solarprojectapp.generated.model.ComplaintListsDatum;
import com.solarprojectapp.generated.model.TechnicalPartnerList;
import com.solarprojectapp.ui.activities.NewComplaintListActivity;
import com.solarprojectapp.ui.activities.ShowNewComplaintDetailsActivity;
import com.solarprojectapp.ui.activities.TechnicalPartenerListActivity;
import com.solarprojectapp.utils.LoadingDialog;
import com.solarprojectapp.utils.NetworkUtils;
import com.solarprojectapp.utils.PrefUtils;
import com.solarprojectapp.utils.SnakBarUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.solarprojectapp.api.ApiEndPoints.MAIN_BASE_URL;

/**
 * Created by Abhinandan on 26/12/17.
 */

public class TechnicalPartenerAdapter extends ArrayAdapter<TechnicalPartnerList> {

    int groupid;
    ArrayList<TechnicalPartnerList> techPartnerList;
    FragmentActivity context;
    String complainId;
    private RetrofitInterface.AssignTechPartnerClient assignTechPartnerAdapter;
    String priorityName,statusName;

    public TechnicalPartenerAdapter(FragmentActivity navigationalActivity, int layout_bank_details, int complaint_id, ArrayList<TechnicalPartnerList> techPartnerList, String complaintId)
    {
        super(navigationalActivity,layout_bank_details,complaint_id,techPartnerList);
        groupid=layout_bank_details;
        this.context = navigationalActivity;
        this.techPartnerList = techPartnerList;
        this.complainId = complaintId;

    }


    // Hold views of the ListView to improve its scrolling performance
    static class ViewHolder {
        public TextView techName;
        public TextView techEmail;
        public TextView techGeoAdd;
        public TextView techContactNum;
        //public Button viewDetailsBtn;
        public TextView approveBtn;
        public LinearLayout llLineColor;








    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        Log.e("abhi", "getView:------------------------- " );

        if(rowView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView= inflater.inflate(groupid, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.techName= (TextView) rowView.findViewById(R.id.tech_name);
            viewHolder.techEmail= (TextView) rowView.findViewById(R.id.tech_email);
            viewHolder.techGeoAdd= (TextView) rowView.findViewById(R.id.tech_user_add);
            viewHolder.techContactNum= (TextView) rowView.findViewById(R.id.tech_contact_num);
            //viewHolder.viewDetailsBtn= (Button) rowView.findViewById(R.id.view_detail_button);
           viewHolder.approveBtn= (TextView) rowView.findViewById(R.id.approve_button);
            viewHolder.llLineColor= (LinearLayout) rowView.findViewById(R.id.ll_line_design);







            rowView.setTag(viewHolder);

        }
        // Set text to each TextView of ListView item
        final TechnicalPartnerList technicalPartnerList = getItem(position);
        final ViewHolder holder = (ViewHolder) rowView.getTag();

        if (technicalPartnerList !=null) {
            holder.techName.setText(technicalPartnerList.getTechnicalpartenerName());
            holder.techEmail.setText(technicalPartnerList.getTechnicalpartenerEmailid());
            holder.techGeoAdd.setText(technicalPartnerList.getTechnicalpartenerGeoadress());
            holder.techContactNum.setText(technicalPartnerList.getTechnicalpartenerPhone());
            holder.approveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    setUpRestAdapter();
                    getApproval(v,technicalPartnerList);

                }
            });


        }


        if (position%4 == 0){
            holder.llLineColor.setBackgroundColor(Color.parseColor("#e84c3d"));
        } else if (position%4 == 1){
            holder.llLineColor.setBackgroundColor(Color.parseColor("#3598db"));
        } else if (position%4 == 2){
            holder.llLineColor.setBackgroundColor(Color.parseColor("#4c6279"));
        } else if (position%4 == 3){
            holder.llLineColor.setBackgroundColor(Color.parseColor("#dc8329"));
        }
        return rowView;
    }

    private void setUpRestAdapter() {
        assignTechPartnerAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.AssignTechPartnerClient.class, MAIN_BASE_URL, getContext());
    }


    private void getApproval(View v, TechnicalPartnerList technicalPartnerList) {
        LoadingDialog.showLoadingDialog(getContext(),"Loading...");
        Call<AssignComplaintResponse> call = assignTechPartnerAdapter.AssignTechPartner(technicalPartnerList.getTechnicalpartenerId(),complainId, PrefUtils.getFkId(getContext()),"assigntechnicalpartner");
        if (NetworkUtils.isNetworkConnected(getContext())) {
            call.enqueue(new Callback<AssignComplaintResponse>() {

                @Override
                public void onResponse(Call<AssignComplaintResponse> call, Response<AssignComplaintResponse> response) {

                    if (response.isSuccessful()) {

                        if (response.body().getSuccess().equals("true")) {
                            Log.e("abhi", "onResponse: ..............admin data" +response.body().getMessage());
                            Toast.makeText(getContext(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
                            ((TechnicalPartenerListActivity)getContext()).finish();
                            LoadingDialog.cancelLoading();

                        }
                        else
                        {
                            LoadingDialog.cancelLoading();

                            Toast.makeText(getContext(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        }



                    }

                }

                @Override
                public void onFailure(Call<AssignComplaintResponse> call, Throwable t) {
                    Toast.makeText(getContext(),"Error occur",Toast.LENGTH_SHORT).show();
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(getContext());
        }
    }

}
