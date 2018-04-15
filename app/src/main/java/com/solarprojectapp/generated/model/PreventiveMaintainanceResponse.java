
package com.solarprojectapp.generated.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PreventiveMaintainanceResponse {

    @SerializedName("PreventiveDateListsData")
    @Expose
    private List<List<PreventiveDateListsDatum>> preventiveDateListsData = null;
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("msg")
    @Expose
    private String msg;

    public List<List<PreventiveDateListsDatum>> getPreventiveDateListsData() {
        return preventiveDateListsData;
    }

    public void setPreventiveDateListsData(List<List<PreventiveDateListsDatum>> preventiveDateListsData) {
        this.preventiveDateListsData = preventiveDateListsData;
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
