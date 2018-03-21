
package com.solarprojectapp.generated.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TechnicalPartnerListResponse {

    @SerializedName("TechnicalPartnerList")
    @Expose
    private List<List<TechnicalPartnerList>> technicalPartnerList = null;
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("msg")
    @Expose
    private String msg;

    public List<List<TechnicalPartnerList>> getTechnicalPartnerList() {
        return technicalPartnerList;
    }

    public void setTechnicalPartnerList(List<List<TechnicalPartnerList>> technicalPartnerList) {
        this.technicalPartnerList = technicalPartnerList;
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
