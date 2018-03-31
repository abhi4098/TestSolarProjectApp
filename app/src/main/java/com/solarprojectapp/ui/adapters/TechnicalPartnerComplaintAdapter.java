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
import com.solarprojectapp.generated.model.ComplaintListsDatum;
import com.solarprojectapp.generated.model.TechnicalPartnerFunctionResponse;
import com.solarprojectapp.ui.activities.CustomTecnicalPartnerTabActivity;
import com.solarprojectapp.ui.activities.NewComplaintListActivity;
import com.solarprojectapp.ui.activities.ShowNewComplaintDetailsActivity;
import com.solarprojectapp.ui.activities.TechnicalPartenerListActivity;
import com.solarprojectapp.ui.fragments.OpenTabFragment;
import com.solarprojectapp.utils.LoadingDialog;
import com.solarprojectapp.utils.NetworkUtils;
import com.solarprojectapp.utils.PrefUtils;
import com.solarprojectapp.utils.SnakBarUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.solarprojectapp.api.ApiEndPoints.MAIN_BASE_URL;


public class TechnicalPartnerComplaintAdapter extends ArrayAdapter<ComplaintListsDatum> {

    int groupid;
    ArrayList<ComplaintListsDatum> newTechPartnerComplaintList;
    FragmentActivity context;
    private RetrofitInterface.TechnicalPartnerFunctionClient technicalPartnerFunctionAdapter;
    String apiType;

    public TechnicalPartnerComplaintAdapter(FragmentActivity navigationalActivity, int layout_complaint, int complaint_id, ArrayList<ComplaintListsDatum> newTechPartnerComplaintList)
    {
        super(navigationalActivity,layout_complaint,complaint_id,newTechPartnerComplaintList);
        groupid=layout_complaint;
        this.context = navigationalActivity;
        this.newTechPartnerComplaintList = newTechPartnerComplaintList;

    }


    // Hold views of the ListView to improve its scrolling performance
    static class ViewHolder {
        public TextView complaintName;
        public TextView projectOwner;
        public TextView endConsumer;
        public TextView projectType;
        public Button viewDetailsBtn;
        public Button acceptBtn;
        public Button closeBtn;
        public Button rejectBtn;
        public Button requestSparePartBtn;








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
            viewHolder.acceptBtn= (Button) rowView.findViewById(R.id.accept_button);
            viewHolder.rejectBtn= (Button) rowView.findViewById(R.id.reject_button);
            viewHolder.closeBtn= (Button) rowView.findViewById(R.id.close_button);
            viewHolder.requestSparePartBtn= (Button) rowView.findViewById(R.id.request_spare_part_button);






            rowView.setTag(viewHolder);

        }
        // Set text to each TextView of ListView item
        final ComplaintListsDatum complaintListsDatum = getItem(position);
        final ViewHolder holder = (ViewHolder) rowView.getTag();

        if (complaintListsDatum !=null) {
            Log.e("abhi", "getView: complaint id"+complaintListsDatum.getComplaint() );
           holder.complaintName.setText(complaintListsDatum.getComplaint());
           holder.endConsumer.setText(complaintListsDatum.getEndConsumer());
           holder.projectType.setText(complaintListsDatum.getProjectType());
           holder.projectOwner.setText(complaintListsDatum.getProjectOwner());
           if (complaintListsDatum.getComplainTechnicalpartnerstatus().equals("Inprogress"))
           {
               holder.viewDetailsBtn.setVisibility(View.VISIBLE);
               holder.acceptBtn.setVisibility(View.VISIBLE);
               holder.closeBtn.setVisibility(View.GONE);
               holder.rejectBtn.setVisibility(View.VISIBLE);
               holder.requestSparePartBtn.setVisibility(View.GONE);

               holder.viewDetailsBtn.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       Intent i = new Intent(getContext(), ShowNewComplaintDetailsActivity.class);
                       i.putExtra("COMPLAINT_DESC", complaintListsDatum.getComplainDescription());
                       i.putExtra("COMPLAINT_ID", complaintListsDatum.getComplainId());
                       i.putExtra("COMPLAINT", complaintListsDatum.getComplaint());
                       i.putExtra("COMPLAINT_END_CONSUMER", complaintListsDatum.getEndConsumer());
                       i.putExtra("COMPLAINT_PROJECT_OWNER", complaintListsDatum.getProjectOwner());
                       i.putExtra("COMPLAINT_PROJECT_TYPE", complaintListsDatum.getProjectType());
                       i.putExtra("COMPLAINT_STATE", complaintListsDatum.getState());
                       i.putExtra("COMPLAINT_CONTACT", complaintListsDatum.getEndConsumerContactno());
                       //i.putExtra("INTENT_FROM","EditButton");

                       getContext().startActivity(i);

                   }
               });

               holder.acceptBtn.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {

                       Toast.makeText(getContext(),"accept",Toast.LENGTH_SHORT).show();
                       apiType = "acceptcomplainbychnicalpartner";
                       setUpRestAdapter();
                       getTechnicalPartnerFun(v,complaintListsDatum);

                   }
               });


               holder.rejectBtn.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {

                       Toast.makeText(getContext(),"reject",Toast.LENGTH_SHORT).show();
                       apiType = "rejectcomplainbychnicalpartner";
                       setUpRestAdapter();
                       getTechnicalPartnerFun(v,complaintListsDatum);
                   }
               });


           }
           else if (complaintListsDatum.getComplainTechnicalpartnerstatus().equals("Accept"))
           {
               holder.viewDetailsBtn.setVisibility(View.VISIBLE);
               holder.acceptBtn.setVisibility(View.GONE);
               holder.closeBtn.setVisibility(View.VISIBLE);
               holder.rejectBtn.setVisibility(View.GONE);
               holder.requestSparePartBtn.setVisibility(View.VISIBLE);
               holder.viewDetailsBtn.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       Intent i = new Intent(getContext(), ShowNewComplaintDetailsActivity.class);
                       i.putExtra("COMPLAINT_DESC", complaintListsDatum.getComplainDescription());
                       i.putExtra("COMPLAINT_ID", complaintListsDatum.getComplainId());
                       i.putExtra("COMPLAINT", complaintListsDatum.getComplaint());
                       i.putExtra("COMPLAINT_END_CONSUMER", complaintListsDatum.getEndConsumer());
                       i.putExtra("COMPLAINT_PROJECT_OWNER", complaintListsDatum.getProjectOwner());
                       i.putExtra("COMPLAINT_PROJECT_TYPE", complaintListsDatum.getProjectType());
                       i.putExtra("COMPLAINT_STATE", complaintListsDatum.getState());
                       i.putExtra("COMPLAINT_CONTACT", complaintListsDatum.getEndConsumerContactno());
                       //i.putExtra("INTENT_FROM","EditButton");

                       getContext().startActivity(i);

                   }
               });

               holder.requestSparePartBtn.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {

                       //setUpRestAdapter();
                       //getApproval(v,complaintListsDatum);

                   }
               });


               holder.closeBtn.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {

                       Toast.makeText(getContext(),"close",Toast.LENGTH_SHORT).show();
                       apiType ="closecomplainbychnicalpartner";
                       setUpRestAdapter();
                       getTechnicalPartnerFun(v,complaintListsDatum);
                   }
               });

           }
           else
           {
               holder.viewDetailsBtn.setVisibility(View.VISIBLE);
               holder.acceptBtn.setVisibility(View.GONE);
               holder.closeBtn.setVisibility(View.GONE);
               holder.rejectBtn.setVisibility(View.GONE);
               holder.requestSparePartBtn.setVisibility(View.GONE);
               holder.viewDetailsBtn.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       Intent i = new Intent(getContext(), ShowNewComplaintDetailsActivity.class);
                       i.putExtra("COMPLAINT_DESC", complaintListsDatum.getComplainDescription());
                       i.putExtra("COMPLAINT_ID", complaintListsDatum.getComplainId());
                       i.putExtra("COMPLAINT", complaintListsDatum.getComplaint());
                       i.putExtra("COMPLAINT_END_CONSUMER", complaintListsDatum.getEndConsumer());
                       i.putExtra("COMPLAINT_PROJECT_OWNER", complaintListsDatum.getProjectOwner());
                       i.putExtra("COMPLAINT_PROJECT_TYPE", complaintListsDatum.getProjectType());
                       i.putExtra("COMPLAINT_STATE", complaintListsDatum.getState());
                       i.putExtra("COMPLAINT_CONTACT", complaintListsDatum.getEndConsumerContactno());
                       //i.putExtra("INTENT_FROM","EditButton");

                       getContext().startActivity(i);

                   }
               });
           }

        }



        return rowView;
    }

    private void setUpRestAdapter() {
        technicalPartnerFunctionAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.TechnicalPartnerFunctionClient.class, MAIN_BASE_URL, getContext());
    }


    private void getTechnicalPartnerFun(View v, ComplaintListsDatum complaintListsDatum) {
        LoadingDialog.showLoadingDialog(getContext(),"Loading...");
        Call<TechnicalPartnerFunctionResponse> call = technicalPartnerFunctionAdapter.technicalPartnerFunction(PrefUtils.getFkId(getContext()),complaintListsDatum.getComplainId(),apiType);
        if (NetworkUtils.isNetworkConnected(getContext())) {
            call.enqueue(new Callback<TechnicalPartnerFunctionResponse>() {

                @Override
                public void onResponse(Call<TechnicalPartnerFunctionResponse> call, Response<TechnicalPartnerFunctionResponse> response) {

                    if (response.isSuccessful()) {

                        if (response.body().getSuccess().equals("true")) {
                            Log.e("abhi", "onResponse: .............." +response.body().getMessage());
                            Intent intent = ((CustomTecnicalPartnerTabActivity)getContext()).getIntent();
                            ((CustomTecnicalPartnerTabActivity)getContext()).finish();
                            getContext().startActivity(intent);
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
                public void onFailure(Call<TechnicalPartnerFunctionResponse> call, Throwable t) {
                    Toast.makeText(getContext(),"Error occur",Toast.LENGTH_SHORT).show();
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(getContext());
        }
    }

}
