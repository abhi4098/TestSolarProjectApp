package com.solarprojectapp.ui.activities;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.solarprojectapp.R;
import com.solarprojectapp.utils.LoadingDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ShowSparePartsPendingDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;

    @BindView(R.id.back_icon)
    ImageView ivBackIcon;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_app_title)
    TextView tvAppTitle;

    @BindView(R.id.spare_part_name)
    TextView tvSparePartName;
    @BindView(R.id.spare_part_brand)
    TextView tvSparePartBrand;
    @BindView(R.id.spare_part_created_date)
    TextView tvSparePartCreatedDate;
    @BindView(R.id.spare_part_id)
    TextView tvSparePartId;
    @BindView(R.id.spare_part_price)
    TextView tvSparePartPrice;
    @BindView(R.id.spare_part_requested_price)
    TextView tvSparePartRequestedPrice;
    @BindView(R.id.spare_part_requested_quantity)
    TextView tvSparePartRequestedQuantity;
    @BindView(R.id.spare_part_status_name)
    TextView tvSparePartStatusName;
    @BindView(R.id.spare_part_requested_Image)
    ImageView personImage;





    //private EditText result;
   String  sparePartImage,sparePartName,sparePartCreatedDate,sparePartBrand,sparePartId,sparePartPrice,sparePartRequestedPrice,sparePartRequestedQuantity,sparePartStatusName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_show_spare_parts_pending_details);
        mContext = ShowSparePartsPendingDetailsActivity.this;
        ButterKnife.bind(this);
        ivBackIcon.setOnClickListener(this);
        tvAppTitle.setText("SPARE PARTS REQUESTS PENDING DETAILS");
        LoadingDialog.showLoadingDialog(this,"Loading...");

        sparePartName = getIntent().getExtras().getString("SPARE_PART_NAME");
        sparePartId = getIntent().getExtras().getString("SPARE_PART_ID");
        sparePartStatusName = getIntent().getExtras().getString("SPARE_PART_STATUS");
        sparePartRequestedPrice = getIntent().getExtras().getString("SPARE_PART_REQUESTED_PRICE");
        sparePartPrice = getIntent().getExtras().getString("SPARE_PART_PRICE");
        sparePartRequestedQuantity = getIntent().getExtras().getString("SPARE_PART_REQUESTED_QUANTITY");
        sparePartCreatedDate = getIntent().getExtras().getString("SPARE_PART_CREATED_DATE");
        sparePartBrand = getIntent().getExtras().getString("SPARE_PART_BRAND");
        sparePartImage = getIntent().getExtras().getString("SPARE_PART_IMAGE");

        tvSparePartName.setText(sparePartName);
        tvSparePartId.setText(sparePartId);
        tvSparePartStatusName.setText(sparePartStatusName);
        tvSparePartRequestedPrice.setText(sparePartRequestedPrice);
        tvSparePartPrice.setText(sparePartPrice);
        tvSparePartRequestedQuantity.setText(sparePartRequestedQuantity);
        if (!sparePartCreatedDate.equals("")&&sparePartCreatedDate !=null) {
            tvSparePartCreatedDate.setText(parseTodaysDate(sparePartCreatedDate));
        }


        tvSparePartBrand.setText(sparePartBrand);

        Log.e("abhi", "onCreate: .................image spare part link"+sparePartImage );

        //setSparePartImage(sparePartImage);
        Glide.with(this).load(sparePartImage).into(personImage);

        LoadingDialog.cancelLoading();

    }


    public static String parseTodaysDate(String time) {



        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        String outputPattern = "dd-MM-yyyy";

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
    public void onClick(View view) {

        super.onBackPressed();

    }
}


