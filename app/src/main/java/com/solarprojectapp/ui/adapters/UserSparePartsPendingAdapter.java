package com.solarprojectapp.ui.adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.solarprojectapp.R;
import com.solarprojectapp.generated.model.ComplaintListsDatum;
import com.solarprojectapp.generated.model.SparepartsrequestList;

import java.util.ArrayList;

/**
 * Created by Abhinandan on 26/12/17.
 */

public class UserSparePartsPendingAdapter extends ArrayAdapter<SparepartsrequestList> {

    int groupid;
    ArrayList<SparepartsrequestList> sparePartsList;
    FragmentActivity context;
    String priorityName,statusName;

    public UserSparePartsPendingAdapter(FragmentActivity navigationalActivity, int layout_spare_parts_pending, int complaint_id, ArrayList<SparepartsrequestList> sparePartsList)
    {
        super(navigationalActivity,layout_spare_parts_pending,complaint_id,sparePartsList);
        groupid=layout_spare_parts_pending;
        this.context = navigationalActivity;
        this.sparePartsList = sparePartsList;

    }


    // Hold views of the ListView to improve its scrolling performance
    static class ViewHolder {
        public TextView complaintId;
        public TextView projectOwner;
        public TextView endConsumer;
        public TextView projectType;









    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        Log.e("abhi", "getView:------------------------- " );

        if(rowView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView= inflater.inflate(groupid, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.complaintId= (TextView) rowView.findViewById(R.id.complaint_id);
            viewHolder.endConsumer= (TextView) rowView.findViewById(R.id.account_num);
            viewHolder.projectOwner= (TextView) rowView.findViewById(R.id.account_type);
            viewHolder.projectType= (TextView) rowView.findViewById(R.id.bank_name);





            rowView.setTag(viewHolder);

        }
        // Set text to each TextView of ListView item
        final SparepartsrequestList sparepartsrequestList = getItem(position);
        final ViewHolder holder = (ViewHolder) rowView.getTag();

        if (sparepartsrequestList !=null) {
            holder.complaintId.setText(sparepartsrequestList.getSparepartName());
            holder.endConsumer.setText(sparepartsrequestList.getSparepartId());
            holder.projectType.setText(sparepartsrequestList.getSparepartBrand());
            holder.projectOwner.setText(sparepartsrequestList.getStatusCreatedate());

        }



        return rowView;
    }

}
