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

import com.solarprojectapp.R;
import com.solarprojectapp.generated.model.SparepartsrequestList;
import com.solarprojectapp.ui.activities.ShowSparePartsPendingDetailsActivity;
import com.solarprojectapp.ui.activities.ShowSparePartsRequestedDetailsActivity;
import com.solarprojectapp.utils.PrefUtils;

import java.util.ArrayList;

/**
 * Created by Abhinandan on 26/12/17.
 */

public class SparePartRequestededAdapter extends ArrayAdapter<SparepartsrequestList> {

    int groupid;
    ArrayList<SparepartsrequestList> sparePartsList;
    FragmentActivity context;
    String priorityName,statusName;

    public SparePartRequestededAdapter(FragmentActivity navigationalActivity, int layout_spare_parts_pending, int complaint_id, ArrayList<SparepartsrequestList> sparePartsList)
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
        public Button approveBtn;
        public Button viewDetailsBtn;









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
            viewHolder.viewDetailsBtn= (Button) rowView.findViewById(R.id.view_detail_button);
            viewHolder.approveBtn= (Button) rowView.findViewById(R.id.approve_button);





            rowView.setTag(viewHolder);

        }
        // Set text to each TextView of ListView item
        final SparepartsrequestList sparepartsrequestList = getItem(position);
        final ViewHolder holder = (ViewHolder) rowView.getTag();

        if (sparepartsrequestList !=null) {
            holder.sparePartName.setText(sparepartsrequestList.getSparepartName());
            holder.sparePartId.setText(sparepartsrequestList.getSparepartId());
            holder.sparePartBrand.setText(sparepartsrequestList.getSparepartBrand());
            holder.sparePartStatusName.setText(sparepartsrequestList.getStatusName());
            holder.sparePartPrice.setText(sparepartsrequestList.getSparepartPrice());

            if (PrefUtils.getUserFrag(getContext()).equals("Client")) {
                holder.viewDetailsBtn.setVisibility(View.GONE);
                holder.approveBtn.setVisibility(View.GONE);
            } else {
                holder.viewDetailsBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getContext(), ShowSparePartsRequestedDetailsActivity.class);
                        i.putExtra("SPARE_PART_NAME", sparepartsrequestList.getSparepartName());
                        i.putExtra("SPARE_PART_ID", sparepartsrequestList.getSparepartId());
                        i.putExtra("SPARE_PART_STATUS", sparepartsrequestList.getStatusName());
                        i.putExtra("SPARE_PART_REQUESTED_PRICE", sparepartsrequestList.getSparepartRequestPrice());
                        i.putExtra("SPARE_PART_PRICE", sparepartsrequestList.getSparepartPrice());
                        i.putExtra("SPARE_PART_REQUESTED_QUANTITY", sparepartsrequestList.getSparepartRequestQuantity());
                        i.putExtra("SPARE_PART_CREATED_DATE", sparepartsrequestList.getSparepartCreatedate());
                        i.putExtra("SPARE_PART_BRAND", sparepartsrequestList.getSparepartBrand());
                        //i.putExtra("INTENT_FROM","EditButton");

                        getContext().startActivity(i);

                    }
                });

            }
        }


        return rowView;
    }

}
