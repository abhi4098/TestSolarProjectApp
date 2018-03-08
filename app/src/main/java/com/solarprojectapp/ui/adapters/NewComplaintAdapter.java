package com.solarprojectapp.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.solarprojectapp.R;
import com.solarprojectapp.api.ApiAdapter;
import com.solarprojectapp.api.RetrofitInterface;
import com.solarprojectapp.generated.model.ApproveComplaintResponse;
import com.solarprojectapp.generated.model.ComplaintListsDatum;
import com.solarprojectapp.generated.model.DashboardDataResponse;
import com.solarprojectapp.ui.activities.NewComplaintListActivity;
import com.solarprojectapp.ui.activities.ShowNewComplaintDetailsActivity;
import com.solarprojectapp.ui.activities.SparePartsRequestedActivity;
import com.solarprojectapp.utils.LoadingDialog;
import com.solarprojectapp.utils.NetworkUtils;
import com.solarprojectapp.utils.SnakBarUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.solarprojectapp.api.ApiEndPoints.MAIN_BASE_URL;

/**
 * Created by Abhinandan on 26/12/17.
 */

public class NewComplaintAdapter extends ArrayAdapter<ComplaintListsDatum> {

    int groupid;
    ArrayList<ComplaintListsDatum> newComplaintList;
    FragmentActivity context;
    private RetrofitInterface.AdminApproveCompaintClient adminApproveCompaintClient;
    String priorityName,statusName;

    public NewComplaintAdapter(FragmentActivity navigationalActivity, int layout_bank_details, int complaint_id, ArrayList<ComplaintListsDatum> newComplaintList)
    {
        super(navigationalActivity,layout_bank_details,complaint_id,newComplaintList);
        groupid=layout_bank_details;
        this.context = navigationalActivity;
        this.newComplaintList = newComplaintList;

    }


    // Hold views of the ListView to improve its scrolling performance
    static class ViewHolder {
        public TextView complaintName;
        public TextView projectOwner;
        public TextView endConsumer;
        public TextView projectType;
        public Button viewDetailsBtn;
        public Button approveBtn;








    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        Log.e("abhi", "getView:------------------------- " );

        if(rowView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView= inflater.inflate(groupid, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.complaintName= (TextView) rowView.findViewById(R.id.complaint_name);
            viewHolder.endConsumer= (TextView) rowView.findViewById(R.id.end_consumer);
            viewHolder.projectOwner= (TextView) rowView.findViewById(R.id.project_owner);
            viewHolder.projectType= (TextView) rowView.findViewById(R.id.project_type);
            viewHolder.viewDetailsBtn= (Button) rowView.findViewById(R.id.view_detail_button);
            viewHolder.approveBtn= (Button) rowView.findViewById(R.id.approve_button);






            rowView.setTag(viewHolder);

        }
        // Set text to each TextView of ListView item
        final ComplaintListsDatum complaintListsDatum = getItem(position);
        final ViewHolder holder = (ViewHolder) rowView.getTag();

        if (complaintListsDatum !=null) {
            holder.complaintName.setText(complaintListsDatum.getComplaint());
            holder.endConsumer.setText(complaintListsDatum.getEndConsumer());
            holder.projectType.setText(complaintListsDatum.getProjectType());
            holder.projectOwner.setText(complaintListsDatum.getProjectOwner());
            holder.viewDetailsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getContext(), ShowNewComplaintDetailsActivity.class);
                    i.putExtra("COMPLAINT_DESC",complaintListsDatum.getComplainDescription());
                    i.putExtra("COMPLAINT_ID",complaintListsDatum.getComplainId());
                    i.putExtra("COMPLAINT",complaintListsDatum.getComplaint());
                    i.putExtra("COMPLAINT_END_CONSUMER",complaintListsDatum.getEndConsumer());
                    i.putExtra("COMPLAINT_PROJECT_OWNER",complaintListsDatum.getProjectOwner());
                    i.putExtra("COMPLAINT_PROJECT_TYPE",complaintListsDatum.getProjectType());
                    i.putExtra("COMPLAINT_STATE",complaintListsDatum.getState());
                    i.putExtra("COMPLAINT_CONTACT",complaintListsDatum.getEndConsumerContactno());
                    //i.putExtra("INTENT_FROM","EditButton");

                    getContext().startActivity(i);

                }
            });

            holder.approveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    setUpRestAdapter();
                    getApproval(v,complaintListsDatum);
                }
            });


        }



        return rowView;
    }

    private void setUpRestAdapter() {
        adminApproveCompaintClient = ApiAdapter.createRestAdapter(RetrofitInterface.AdminApproveCompaintClient.class, MAIN_BASE_URL, getContext());
    }


    private void getApproval(View v, ComplaintListsDatum complaintListsDatum) {
        LoadingDialog.showLoadingDialog(getContext(),"Loading...");
        Call<ApproveComplaintResponse> call = adminApproveCompaintClient.AdminApproval(complaintListsDatum.getComplainId(),"Approved","complaintsapproved");
        if (NetworkUtils.isNetworkConnected(getContext())) {
            call.enqueue(new Callback<ApproveComplaintResponse>() {

                @Override
                public void onResponse(Call<ApproveComplaintResponse> call, Response<ApproveComplaintResponse> response) {

                    if (response.isSuccessful()) {

                        if (response.body().getSuccess().equals("true")) {
                            Log.e("abhi", "onResponse: ..............admin data" +response.body().getComplaintsApproved());
                            ((NewComplaintListActivity)getContext()).finish();
                            LoadingDialog.cancelLoading();

                        }
                        else
                        {
                            LoadingDialog.cancelLoading();

                            Toast.makeText(getContext(),response.body().getComplaintsApproved(),Toast.LENGTH_SHORT).show();
                        }



                    }

                }

                @Override
                public void onFailure(Call<ApproveComplaintResponse> call, Throwable t) {
                    Toast.makeText(getContext(),"Error occur",Toast.LENGTH_SHORT).show();
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(getContext());
        }
    }

}
