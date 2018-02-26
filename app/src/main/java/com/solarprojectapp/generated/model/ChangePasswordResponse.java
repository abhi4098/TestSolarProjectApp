
package com.solarprojectapp.generated.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangePasswordResponse {

    @SerializedName("changepassword")
    @Expose
    private String changepassword;
    @SerializedName("success")
    @Expose
    private String success;

    public String getChangepassword() {
        return changepassword;
    }

    public void setChangepassword(String changepassword) {
        this.changepassword = changepassword;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

}
