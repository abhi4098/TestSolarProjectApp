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

import java.util.ArrayList;

/**
 * Created by Abhinandan on 26/12/17.
 */

public class NewComplaintAdapter extends ArrayAdapter<ComplaintListsDatum> {

    int groupid;
    ArrayList<ComplaintListsDatum> newComplaintList;
    FragmentActivity context;
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
        final ComplaintListsDatum complaintListsDatum = getItem(position);
        final ViewHolder holder = (ViewHolder) rowView.getTag();

        if (complaintListsDatum !=null) {
            holder.complaintId.setText(complaintListsDatum.getComplainId());
            holder.endConsumer.setText(complaintListsDatum.getEndConsumer());
            holder.projectType.setText(complaintListsDatum.getProjectType());
            holder.projectOwner.setText(complaintListsDatum.getProjectOwner());

        }



        return rowView;
    }

}
