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
import com.solarprojectapp.generated.model.ComplaintListsDatum;
import com.solarprojectapp.ui.activities.ShowNewComplaintDetailsActivity;
import com.solarprojectapp.ui.activities.SparePartsRequestedActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        public TextView complaintName;
        public TextView projectOwner;
        public TextView endConsumer;
        public TextView projectType;
        public Button viewDetailsBtn;








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



        }



        return rowView;
    }

}
