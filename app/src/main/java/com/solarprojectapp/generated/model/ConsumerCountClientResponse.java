
package com.solarprojectapp.generated.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConsumerCountClientResponse {

    @SerializedName("ClientConsumerCount")
    @Expose
    private List<ClientConsumerCount> clientConsumerCount = null;
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("msg")
    @Expose
    private String msg;

    public List<ClientConsumerCount> getClientConsumerCount() {
        return clientConsumerCount;
    }

    public void setClientConsumerCount(List<ClientConsumerCount> clientConsumerCount) {
        this.clientConsumerCount = clientConsumerCount;
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
