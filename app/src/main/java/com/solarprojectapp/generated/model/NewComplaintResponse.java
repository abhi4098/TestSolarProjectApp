
package com.solarprojectapp.generated.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewComplaintResponse {

    @SerializedName("ComplaintListsData")
    @Expose
    private List<List<ComplaintListsDatum>> complaintListsData = null;
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("msg")
    @Expose
    private String msg;

    public List<List<ComplaintListsDatum>> getComplaintListsData() {
        return complaintListsData;
    }

    public void setComplaintListsData(List<List<ComplaintListsDatum>> complaintListsData) {
        this.complaintListsData = complaintListsData;
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
