
package com.solarprojectapp.generated.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TotalConsumerListResponse {

    @SerializedName("consumerList")
    @Expose
    private List<List<ConsumerList>> consumerList = null;
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("msg")
    @Expose
    private String msg;

    public List<List<ConsumerList>> getConsumerList() {
        return consumerList;
    }

    public void setConsumerList(List<List<ConsumerList>> consumerList) {
        this.consumerList = consumerList;
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
