package com.solarprojectapp.ui.activities;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.solarprojectapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ShowSparePartsRequestedDetailsActivity extends AppCompatActivity implements View.OnClickListener {

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
        mContext = ShowSparePartsRequestedDetailsActivity.this;
        ButterKnife.bind(this);
        ivBackIcon.setOnClickListener(this);
        tvAppTitle.setText("SPARE PARTS REQUESTS DETAILS");
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
      //  tvSparePartCreatedDate.setText(sparePartCreatedDate);
        tvSparePartBrand.setText(sparePartBrand);
        if (!sparePartCreatedDate.equals("")&&sparePartCreatedDate !=null) {
            tvSparePartCreatedDate.setText(parseTodaysDate(sparePartCreatedDate));
        }

        Log.e("abhi", "onCreate: .................image spare part link"+sparePartImage );

          //setSparePartImage(sparePartImage);
        Glide.with(this).load(sparePartImage).into(personImage);


    }


   /* private void setSparePartImage(String profilepicUrlComplete) {
        Glide.with(this).load(profilepicUrlComplete).asBitmap().centerCrop().dontAnimate().dontTransform().listener(new RequestListener<String, Bitmap>() {
            @Override
            public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                // imageProgressBar.setVisibility(View.GONE);
               // personImage.setBackgroundResource(R.drawable.pic_uploader);
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                //imageProgressBar.setVisibility(View.GONE);
                //personImage.setBackgroundResource(R.drawable.pic_uploader);
                return false;
            }
        })
                .into(new BitmapImageViewTarget(personImage) {
                    @Override
                    protected void setResource(Bitmap bitmap) {
                        Bitmap output;

                        if (bitmap.getWidth() > bitmap.getHeight()) {
                            output = Bitmap.createBitmap(bitmap.getHeight(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
                        } else {
                            output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getWidth(), Bitmap.Config.ARGB_8888);
                        }

                       *//* Canvas canvas = new Canvas(output);

                        final int color = 0xff424242;
                        final Paint paint = new Paint();
                        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

                        float r = 0;

                        if (bitmap.getWidth() > bitmap.getHeight()) {
                            r = bitmap.getHeight() / 2;
                        } else {
                            r = bitmap.getWidth() / 2;
                        }

                        paint.setAntiAlias(true);
                        canvas.drawARGB(0, 0, 0, 0);
                        paint.setColor(color);
                        canvas.drawCircle(r, r, r, paint);
                        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                        canvas.drawBitmap(bitmap, rect, rect, paint);*//*
                        personImage.setImageBitmap(output);
                        // imageProgressBar.setVisibility(View.GONE);

                    }
                });
    }*/

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


