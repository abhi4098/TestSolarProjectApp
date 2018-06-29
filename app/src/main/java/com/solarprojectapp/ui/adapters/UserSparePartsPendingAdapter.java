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
import com.solarprojectapp.generated.model.SparePartAdminAproveResponse;
import com.solarprojectapp.generated.model.SparepartsrequestList;
import com.solarprojectapp.ui.activities.NewSparePartsPendingActivity;
import com.solarprojectapp.ui.activities.ShowNewComplaintDetailsActivity;
import com.solarprojectapp.ui.activities.ShowSparePartsPendingDetailsActivity;
import com.solarprojectapp.ui.activities.SparePartsRequestedActivity;
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

public class UserSparePartsPendingAdapter extends ArrayAdapter<SparepartsrequestList> {

    int groupid;
    ArrayList<SparepartsrequestList> sparePartsList;
    FragmentActivity context;
    String priorityName,statusName;
    private RetrofitInterface.AdminApprovesparePartClient adminApprovesparePartClient;
    public UserSparePartsPendingAdapter(FragmentActivity navigationalActivity, int layout_spare_parts_pending, int complaint_id, ArrayList<SparepartsrequestList> sparePartsList)
    {
        super(navigationalActivity,layout_spare_parts_pending,complaint_id,sparePartsList);
        groupid=layout_spare_parts_pending;
        this.context = navigationalActivity;
        this.sparePartsList = sparePartsList;

    }


    // Hold views of the ListView to improve its scrolling performance
    static class ViewHolder {
        public TextView sparePartId;
        public TextView sparePartName;
        public TextView sparePartStatusName;
        public TextView sparePartBrand;
        public TextView sparePartPrice;
        public TextView viewDetailsBtn;
        public TextView approveBtn;








    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        Log.e("abhi", "getView:------------------------- " );

        if(rowView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView= inflater.inflate(groupid, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.sparePartId= (TextView) rowView.findViewById(R.id.spare_part_id);
            viewHolder.sparePartName= (TextView) rowView.findViewById(R.id.spare_part_name);
            viewHolder.sparePartBrand= (TextView) rowView.findViewById(R.id.spare_part_brand);
            viewHolder.sparePartStatusName= (TextView) rowView.findViewById(R.id.spare_part_status_name);
            viewHolder.sparePartPrice= (TextView) rowView.findViewById(R.id.spare_part_price);
            viewHolder.viewDetailsBtn= (TextView) rowView.findViewById(R.id.view_detail_button);
            viewHolder.approveBtn= (TextView) rowView.findViewById(R.id.approve_button);




            rowView.setTag(viewHolder);

        }
        // Set text to each TextView of ListView item
        final SparepartsrequestList sparepartsrequestList = getItem(position);
        final ViewHolder holder = (ViewHolder) rowView.getTag();

        if (sparepartsrequestList !=null) {
            holder.sparePartName.setText(sparepartsrequestList.getSparepartName());
            holder.sparePartId.setText(sparepartsrequestList.getSparepartId());
            holder.sparePartBrand.setText(sparepartsrequestList.getSparepartBrand());
            holder.sparePartStatusName.setText(sparepartsrequestList.getSparepartStatus());
            holder.sparePartPrice.setText(sparepartsrequestList.getSparepartPrice());
            if (PrefUtils.getUserFrag(getContext()).equals("Client")) {
                holder.viewDetailsBtn.setVisibility(View.GONE);
                holder.approveBtn.setVisibility(View.GONE);
            } else {
                holder.viewDetailsBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getContext(), ShowSparePartsPendingDetailsActivity.class);
                        i.putExtra("SPARE_PART_NAME", sparepartsrequestList.getSparepartName());
                        i.putExtra("SPARE_PART_ID", sparepartsrequestList.getSparepartId());
                        i.putExtra("SPARE_PART_STATUS", sparepartsrequestList.getSparepartStatus());
                        i.putExtra("SPARE_PART_REQUESTED_PRICE", sparepartsrequestList.getSparepartRequestPrice());
                        i.putExtra("SPARE_PART_PRICE", sparepartsrequestList.getSparepartPrice());
                        i.putExtra("SPARE_PART_REQUESTED_QUANTITY", sparepartsrequestList.getSparepartRequestQuantity());
                        i.putExtra("SPARE_PART_CREATED_DATE", sparepartsrequestList.getSparepartCreatedate());
                        i.putExtra("SPARE_PART_BRAND", sparepartsrequestList.getSparepartBrand());
                        i.putExtra("SPARE_PART_IMAGE", sparepartsrequestList.getImagePath());

                        //i.putExtra("INTENT_FROM","EditButton");

                        getContext().startActivity(i);

                    }
                });
                holder.approveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setUpRestAdapter();
                        getApproval(v, sparepartsrequestList);

                    }
                });
            }
        }



        return rowView;
    }
    private void setUpRestAdapter() {
        adminApprovesparePartClient = ApiAdapter.createRestAdapter(RetrofitInterface.AdminApprovesparePartClient.class, MAIN_BASE_URL, getContext());
    }
    private void getApproval(View v, final SparepartsrequestList complaintListsDatum) {
        LoadingDialog.showLoadingDialog(getContext(),"Loading...");
        Call<SparePartAdminAproveResponse> call = adminApprovesparePartClient.AdminSparePartApproval(complaintListsDatum.getSparepartRequestId(),"approvesparepartbyadmin");
        if (NetworkUtils.isNetworkConnected(getContext())) {
            call.enqueue(new Callback<SparePartAdminAproveResponse>() {

                @Override
                public void onResponse(Call<SparePartAdminAproveResponse> call, Response<SparePartAdminAproveResponse> response) {

                    if (response.isSuccessful()) {

                        if (response.body().getSuccess().equals("true")) {
                            Log.e("abhi", "onResponse: ..............pending................................" +response.body().getSparePartRequestApproved());
                           /* Intent i = new Intent(((NewComplaintListActivity) getContext()), TechnicalPartenerListActivity.class);
                            i.putExtra("COMPLAINT_ID", complaintListsDatum.getComplainId());*/
                            Toast.makeText(getContext(),response.body().getSparePartRequestApproved(),Toast.LENGTH_SHORT).show();
                            ((NewSparePartsPendingActivity) getContext()).finish();
                            LoadingDialog.cancelLoading();

                        }
                        else
                        {
                            LoadingDialog.cancelLoading();

                            Toast.makeText(getContext(),response.body().getSparePartRequestApproved(),Toast.LENGTH_SHORT).show();
                        }



                    }

                }

                @Override
                public void onFailure(Call<SparePartAdminAproveResponse> call, Throwable t) {
                    Toast.makeText(getContext(),"Error occur",Toast.LENGTH_SHORT).show();
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(getContext());
        }
    }

}
