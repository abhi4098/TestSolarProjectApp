
package com.solarprojectapp.generated.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RejectedComplaintListResponse {

    @SerializedName("RejectedComplaintListsData")
    @Expose
    private List<List<RejectedComplaintListsDatum>> rejectedComplaintListsData = null;
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("msg")
    @Expose
    private String msg;

    public List<List<RejectedComplaintListsDatum>> getRejectedComplaintListsData() {
        return rejectedComplaintListsData;
    }

    public void setRejectedComplaintListsData(List<List<RejectedComplaintListsDatum>> rejectedComplaintListsData) {
        this.rejectedComplaintListsData = rejectedComplaintListsData;
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
