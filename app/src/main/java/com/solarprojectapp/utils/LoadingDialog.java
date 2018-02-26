package com.solarprojectapp.utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Abhinandan on 04/01/16.
 */
public class LoadingDialog {

    static ProgressDialog progressDialog;

    public static void showLoadingDialog(Context context, String message) {
        if (progressDialog == null || !progressDialog.isShowing()) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage(message);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

    }

    public static void cancelLoading() {
        try {
            if (progressDialog != null && progressDialog.isShowing())
                progressDialog.cancel();
        } catch (Exception e) {
            e.printStackTrace();
        } // TODO make this better window leak error on progress dialog.cancel()
    }
}