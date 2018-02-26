package com.solarprojectapp.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.solarprojectapp.R;
import com.solarprojectapp.api.ApiAdapter;
import com.solarprojectapp.api.RetrofitInterface;
import com.solarprojectapp.generated.model.LoginResponse;
import com.solarprojectapp.generated.model.ProfileResponse;
import com.solarprojectapp.utils.LoadingDialog;
import com.solarprojectapp.utils.NetworkUtils;
import com.solarprojectapp.utils.PrefUtils;
import com.solarprojectapp.utils.SnakBarUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.solarprojectapp.api.ApiEndPoints.BASE_URL_FOR_IMAGE;
import static com.solarprojectapp.api.ApiEndPoints.MAIN_BASE_URL;


public class MyProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private RetrofitInterface.UserProfileClient UserProfileAdapter;
    Context mContext;
    private String profilePicUrl;
    @BindView(R.id.back_image)
    ImageView backImage;

    @BindView(R.id.app_user_name)
    TextView tvAppUserName;

    @BindView(R.id.user_type)
    TextView tvUserType;

    @BindView(R.id.user_name)
    TextView tvUserName;

    @BindView(R.id.user_email)
    TextView tvUserEmail;

    @BindView(R.id.user_phone_num)
    TextView tvUserPhoneNum;
    @BindView(R.id.user_other_Details)
    TextView tvUserOtherDetails;

    @BindView(R.id.person_image)
    de.hdodenhof.circleimageview.CircleImageView personImage;


    @OnClick(R.id.change_password_button)
    public void changePass()
    {
        Intent i = new Intent(MyProfileActivity.this, ChangePasswordActivity.class);
        startActivity(i);
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_my_profile);
        mContext = MyProfileActivity.this;
        ButterKnife.bind(this);
        backImage.setOnClickListener(this);

        setUpRestAdapter();
        getProfileDetails();
    }

    private void setUpRestAdapter() {
        UserProfileAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.UserProfileClient.class, MAIN_BASE_URL, this);
    }


    private void getProfileDetails() {
        LoadingDialog.showLoadingDialog(this,"Loading...");
        Call<ProfileResponse> call = UserProfileAdapter.userProfile( PrefUtils.getUserName(MyProfileActivity.this),PrefUtils.getUserType(MyProfileActivity.this),PrefUtils.getFkId(MyProfileActivity.this),"userprofiledetails");
        if (NetworkUtils.isNetworkConnected(this)) {
            call.enqueue(new Callback<ProfileResponse>() {

                @Override
                public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {

                    if (response.isSuccessful()) {

                        if (response.body().getSuccess().equals("true")) {
                            for (int i = 0; i < response.body().getProfileDetailsData().size(); i++) {

                                tvAppUserName.setText(response.body().getProfileDetailsData().get(i).get(i).getName());
                                tvUserEmail.setText(response.body().getProfileDetailsData().get(i).get(i).getEmailId());
                                tvUserName.setText(response.body().getProfileDetailsData().get(i).get(i).getName());
                                tvUserPhoneNum.setText(response.body().getProfileDetailsData().get(i).get(i).getContactDetails());
                                tvUserOtherDetails.setText(response.body().getProfileDetailsData().get(i).get(i).getOtherDetails());
                                tvUserType.setText(response.body().getProfileDetailsData().get(i).get(i).getUserType());
                                if (response.body().getProfileDetailsData().get(i).get(i).getImage() != null) {
                                    profilePicUrl = response.body().getProfileDetailsData().get(i).get(i).getImage() ;
                                    String profilePictureUrlComplete = BASE_URL_FOR_IMAGE + profilePicUrl;
                                    PrefUtils.storeUserImage(profilePictureUrlComplete,MyProfileActivity.this);
                                    Log.e("abhi", "onResponse: image link............"+ profilePictureUrlComplete);
                                    setProfilePicURL(profilePictureUrlComplete);
                                }
                                else {
                                    personImage.setBackgroundResource(R.drawable.pic_uploader);
                                }
                            }

                            LoadingDialog.cancelLoading();
                        }
                        else
                        {
                            LoadingDialog.cancelLoading();

                            Toast.makeText(getApplicationContext(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
                        }



                    }

                }

                @Override
                public void onFailure(Call<ProfileResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"Error occur",Toast.LENGTH_SHORT).show();
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(this);
        }
    }

    private void setProfilePicURL(String profilepicUrlComplete) {
        Glide.with(this).load(profilepicUrlComplete).asBitmap().centerCrop().dontAnimate().dontTransform().listener(new RequestListener<String, Bitmap>() {
            @Override
            public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
               // imageProgressBar.setVisibility(View.GONE);
                personImage.setBackgroundResource(R.drawable.pic_uploader);
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                //imageProgressBar.setVisibility(View.GONE);
                personImage.setBackgroundResource(R.drawable.pic_uploader);
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

                        Canvas canvas = new Canvas(output);

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
                        canvas.drawBitmap(bitmap, rect, rect, paint);
                        personImage.setImageBitmap(output);
                       // imageProgressBar.setVisibility(View.GONE);

                    }
                });
    }

    @Override
    public void onClick(View view) {

        super.onBackPressed();

    }
}


