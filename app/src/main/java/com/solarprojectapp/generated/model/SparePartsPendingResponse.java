
package com.solarprojectapp.generated.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SparePartsPendingResponse {

    @SerializedName("SparepartsrequestList")
    @Expose
    private List<List<SparepartsrequestList>> sparepartsrequestList = null;
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("msg")
    @Expose
    private String msg;

    public List<List<SparepartsrequestList>> getSparepartsrequestList() {
        return sparepartsrequestList;
    }

    public void setSparepartsrequestList(List<List<SparepartsrequestList>> sparepartsrequestList) {
        this.sparepartsrequestList = sparepartsrequestList;
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
