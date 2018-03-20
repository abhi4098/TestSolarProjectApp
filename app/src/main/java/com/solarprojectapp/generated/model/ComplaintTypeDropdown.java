
package com.solarprojectapp.generated.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ComplaintTypeDropdown {

    @SerializedName("Complaintstypelist")
    @Expose
    private List<List<Complaintstypelist>> complaintstypelist = null;
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("msg")
    @Expose
    private String msg;

    public List<List<Complaintstypelist>> getComplaintstypelist() {
        return complaintstypelist;
    }

    public void setComplaintstypelist(List<List<Complaintstypelist>> complaintstypelist) {
        this.complaintstypelist = complaintstypelist;
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
