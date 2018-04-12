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
import com.solarprojectapp.generated.model.ConsumerList;
import com.solarprojectapp.ui.activities.NewComplaintListActivity;
import com.solarprojectapp.ui.activities.ShowNewComplaintDetailsActivity;
import com.solarprojectapp.ui.activities.TechnicalPartenerListActivity;
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

public class TotalConsumerAdapter extends ArrayAdapter<ConsumerList> {

    int groupid;
    ArrayList<ConsumerList> totalConsumerList;
    FragmentActivity context;
    String priorityName,statusName;

    public TotalConsumerAdapter(FragmentActivity navigationalActivity, int layout_bank_details, int complaint_id, ArrayList<ConsumerList> totalConsumerList)
    {
        super(navigationalActivity,layout_bank_details,complaint_id,totalConsumerList);
        groupid=layout_bank_details;
        this.context = navigationalActivity;
        this.totalConsumerList = totalConsumerList;

    }


    // Hold views of the ListView to improve its scrolling performance
    static class ViewHolder {
        public TextView consumerName;
        public TextView consumerEmailId;
        public TextView consumerPhoneNum;
        public TextView consumerAdd;
        public TextView consumerProjectOwner;
        public TextView consumerProjectType;










    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        Log.e("abhi", "getView:------------------------- " );

        if(rowView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView= inflater.inflate(groupid, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.consumerName= (TextView) rowView.findViewById(R.id.consumer_name);
            viewHolder.consumerEmailId= (TextView) rowView.findViewById(R.id.consumer_email_id);
            viewHolder.consumerPhoneNum= (TextView) rowView.findViewById(R.id.consumer_contact_num);
            viewHolder.consumerAdd= (TextView) rowView.findViewById(R.id.consumer_add);
            viewHolder.consumerProjectOwner= (TextView) rowView.findViewById(R.id.consumer_project_owner);
            viewHolder.consumerProjectType= (TextView) rowView.findViewById(R.id.consumer_project_type);







            rowView.setTag(viewHolder);

        }
        // Set text to each TextView of ListView item
        final ConsumerList consumerList = getItem(position);
        final ViewHolder holder = (ViewHolder) rowView.getTag();

        if (consumerList !=null) {
            holder.consumerName.setText(consumerList.getConsumerName());
            holder.consumerEmailId.setText(consumerList.getEmailId());
            holder.consumerPhoneNum.setText(consumerList.getMobileNo());
            holder.consumerAdd.setText(consumerList.getGeoAddress());
            holder.consumerProjectType.setText(consumerList.getProjectType());
            holder.consumerProjectOwner.setText(consumerList.getOwnerName());



        }



        return rowView;
    }



}
