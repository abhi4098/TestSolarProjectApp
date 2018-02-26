package com.solarprojectapp.ui.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.solarprojectapp.R;
import com.solarprojectapp.api.ApiAdapter;
import com.solarprojectapp.api.RetrofitInterface;
import com.solarprojectapp.generated.model.ChangePasswordResponse;
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


public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;
    String userOldPassword,userNewPassword;
    private RetrofitInterface.UserChangePasswordClient UserChangePasswordAdapter;

    @BindView(R.id.back_icon)
    ImageView ivBackIcon;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_app_title)
    TextView tvAppTitle;

    @BindView(R.id.old_password)
    EditText etOldPassword;

    @BindView(R.id.new_password)
    EditText etNewPassword;

    @OnClick(R.id.change_password_btn)
    public void changePassword()
    {
        userOldPassword = etOldPassword.getText().toString();
        userNewPassword = etNewPassword.getText().toString();
        if (isRegistrationValid()) {
           getChangePasswordDetails();
        }
    }

    private boolean isRegistrationValid() {

        if (userNewPassword == null || userNewPassword.equals("") || userOldPassword == null || userOldPassword.equals(""))

        {

            if (userOldPassword == null || userOldPassword.equals("") )
                etOldPassword.setError(getString(R.string.error_compulsory_field));

            if (userNewPassword == null || userNewPassword.equals(""))
                etNewPassword.setError(getString(R.string.error_compulsory_field));



            return false;
        } else
            return true;

    }


    private void getChangePasswordDetails() {

        LoadingDialog.showLoadingDialog(this,"Loading...");
        Call<ChangePasswordResponse> call = UserChangePasswordAdapter.userChangePassword( PrefUtils.getUserName(ChangePasswordActivity.this),userOldPassword,userNewPassword,"changepassword");
        if (NetworkUtils.isNetworkConnected(this)) {
            call.enqueue(new Callback<ChangePasswordResponse>() {

                @Override
                public void onResponse(Call<ChangePasswordResponse> call, Response<ChangePasswordResponse> response) {

                    if (response.isSuccessful()) {

                        if (response.body().getSuccess().equals("true")) {

                            Toast.makeText(getApplicationContext(),response.body().getChangepassword(),Toast.LENGTH_SHORT).show();
                            finish();
                            LoadingDialog.cancelLoading();
                        }
                        else
                        {
                            LoadingDialog.cancelLoading();

                            Toast.makeText(getApplicationContext(),response.body().getChangepassword(),Toast.LENGTH_SHORT).show();
                        }



                    }

                }

                @Override
                public void onFailure(Call<ChangePasswordResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"Error occur",Toast.LENGTH_SHORT).show();
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(this);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_change_password);
        mContext = ChangePasswordActivity.this;
        ButterKnife.bind(this);
        ivBackIcon.setOnClickListener(this);
        tvAppTitle.setText("CHANGE PASSWORD");
        setUpRestAdapter();


    }

    private void setUpRestAdapter() {
        UserChangePasswordAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.UserChangePasswordClient.class, MAIN_BASE_URL, this);
    }




    @Override
    public void onClick(View view) {

        super.onBackPressed();

    }
}


