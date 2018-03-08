
package com.solarprojectapp.generated.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DashboardDataResponse {

    @SerializedName("AdminSummary")
    @Expose
    private List<AdminSummary> adminSummary = null;
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("msg")
    @Expose
    private String msg;

    public List<AdminSummary> getAdminSummary() {
        return adminSummary;
    }

    public void setAdminSummary(List<AdminSummary> adminSummary) {
        this.adminSummary = adminSummary;
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
