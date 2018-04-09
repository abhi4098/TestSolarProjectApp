
package com.solarprojectapp.generated.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SparePartAdminAproveResponse {

    @SerializedName("SparePartRequestApproved")
    @Expose
    private String sparePartRequestApproved;
    @SerializedName("success")
    @Expose
    private String success;

    public String getSparePartRequestApproved() {
        return sparePartRequestApproved;
    }

    public void setSparePartRequestApproved(String sparePartRequestApproved) {
        this.sparePartRequestApproved = sparePartRequestApproved;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

}
