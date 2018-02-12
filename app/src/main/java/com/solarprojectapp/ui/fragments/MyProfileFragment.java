package com.solarprojectapp.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.solarprojectapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Abhinandan on 18/8/17.
 */
public class MyProfileFragment extends Fragment {


    //private RetrofitInterface.UserMyProfileClient MyProfileAdapter;
    private String profilePicUrl;


    @BindView(R.id.user_name)
    TextView tvUserName;
    @BindView(R.id.user_phone_num)
    TextView tvUserPhone;
    @BindView(R.id.user_email)
    TextView tvUserEmail;
    @BindView(R.id.user_other_Details)
    TextView tvUserCountry;

    /*@BindView(R.id.person_image)
    de.hdodenhof.circleimageview.CircleImageView personImage;
*/
  /*  @BindView(R.id.progress)
    ProgressBar imageProgressBar;*/



  /*  @OnClick(R.id.edit_Profile)
    public void editProfile() {
        *//*Intent activityChangeIntent = new Intent(getActivity(), EditProfileActivity.class);
        startActivity(activityChangeIntent);*//*
    }*/


    public MyProfileFragment() {
    }


    @Override
    public void onResume(){
        super.onResume();
        //setUpRestAdapter();
       // getMyProfileDetails();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_my_profile, container, false);
        ButterKnife.bind(this,rootView);
       // setUpRestAdapter();
       // getMyProfileDetails();
        return rootView;
    }

   /* private void getMyProfileDetails() {
        LoadingDialog.showLoadingDialog(getActivity(),"Loading...");
        Call<MyProfileResponse> call = MyProfileAdapter.userMyProfile(new MyProfile("profile", PrefUtils.getUserId(getActivity()),"83Ide@$321!"));
        if (NetworkUtils.isNetworkConnected(getActivity())) {
            call.enqueue(new Callback<MyProfileResponse>() {

                @Override
                public void onResponse(Call<MyProfileResponse> call, Response<MyProfileResponse> response) {

                    if (response.isSuccessful()) {
                        PrefUtils.storeEmail(response.body().getProfile().getEmail(),getActivity());
                        PrefUtils.storeFirstName(response.body().getProfile().getFirstName(),getActivity());
                        PrefUtils.storeLastName(response.body().getProfile().getLastName(),getActivity());
                        PrefUtils.storePhone(response.body().getProfile().getPhone(),getActivity());
                        PrefUtils.storeCountry(response.body().getProfile().getCountry(),getActivity());
                        PrefUtils.storeUserCity(response.body().getProfile().getCity(),getActivity());
                        PrefUtils.storeUserPassport(response.body().getProfile().getNibpassport(),getActivity());
                        PrefUtils.storeUserTinNumber(response.body().getProfile().getTinnumber(),getActivity());
                        PrefUtils.storeUserTitle(response.body().getProfile().getTitle(),getActivity());
                        PrefUtils.storeZip(response.body().getProfile().getZip(),getActivity());
                        PrefUtils.storeUserAdd(response.body().getProfile().getAddress(),getActivity());
                        PrefUtils.storeUserBio(response.body().getProfile().getBio(),getActivity());
                        PrefUtils.storeUserState(response.body().getProfile().getState(),getActivity());

                        //tvQrCode.setText(response.body().getProfile().getBio());
                        tvUserName.setText(String.format("%s %s", response.body().getProfile().getFirstName(), response.body().getProfile().getLastName()));
                        tvUserCountry.setText(response.body().getProfile().getCountry());
                        tvUserEmail.setText(response.body().getProfile().getEmail());
                        tvUserPhone.setText(response.body().getProfile().getPhone());
                       // imUserImage.setImageURI(response.body().getProfile().getProfilePic());
                        //profilePicUrl = response.body().getProfile().getProfilePic();

                        if (response.body().getProfile().getProfilePic() != null) {
                            profilePicUrl = response.body().getProfile().getProfilePic();
                            String profilePictureUrlComplete = BASE_URL_FOR_IMAGE + profilePicUrl;
                            PrefUtils.storeUserImage(profilePictureUrlComplete,getActivity());
                            Log.e(TAG, "onResponse: image link............"+ profilePictureUrlComplete);
                            setProfilePicURL(profilePictureUrlComplete);
                        }
                        else {
                            personImage.setBackgroundResource(R.drawable.new_customer_icon);
                        }
                        LoadingDialog.cancelLoading();



                    }
                }

                @Override
                public void onFailure(Call<MyProfileResponse> call, Throwable t) {
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(getActivity());
        }
    }

*/
   /* private void setProfilePicURL(String profilepicUrlComplete) {
        Glide.with(this).load(profilepicUrlComplete).asBitmap().centerCrop().dontAnimate().dontTransform().listener(new RequestListener<String, Bitmap>() {
            @Override
            public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                imageProgressBar.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                imageProgressBar.setVisibility(View.GONE);
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
                        imageProgressBar.setVisibility(View.GONE);

                    }
                });
    }



    private void setUpRestAdapter() {
        MyProfileAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.UserMyProfileClient.class, BASE_URL, getActivity());

    }
*/

}
