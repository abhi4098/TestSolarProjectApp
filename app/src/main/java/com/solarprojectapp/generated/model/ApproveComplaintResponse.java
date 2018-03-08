
package com.solarprojectapp.generated.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApproveComplaintResponse {

    @SerializedName("ComplaintsApproved")
    @Expose
    private String complaintsApproved;
    @SerializedName("success")
    @Expose
    private String success;

    public String getComplaintsApproved() {
        return complaintsApproved;
    }

    public void setComplaintsApproved(String complaintsApproved) {
        this.complaintsApproved = complaintsApproved;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

}
