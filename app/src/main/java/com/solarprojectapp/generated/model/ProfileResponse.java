
package com.solarprojectapp.generated.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileResponse {

    @SerializedName("ProfileDetailsData")
    @Expose
    private List<List<ProfileDetailsDatum>> profileDetailsData = null;
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("msg")
    @Expose
    private String msg;

    public List<List<ProfileDetailsDatum>> getProfileDetailsData() {
        return profileDetailsData;
    }

    public void setProfileDetailsData(List<List<ProfileDetailsDatum>> profileDetailsData) {
        this.profileDetailsData = profileDetailsData;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
