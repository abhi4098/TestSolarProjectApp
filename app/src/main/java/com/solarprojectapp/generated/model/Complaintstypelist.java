
package com.solarprojectapp.generated.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Complaintstypelist {

    @SerializedName("complaintstype_id")
    @Expose
    private String complaintstypeId;
    @SerializedName("fk_projectid")
    @Expose
    private String fkProjectid;
    @SerializedName("complaintstype_name")
    @Expose
    private String complaintstypeName;
    @SerializedName("complaintstype_status")
    @Expose
    private String complaintstypeStatus;
    @SerializedName("complaintstype_order")
    @Expose
    private String complaintstypeOrder;
    @SerializedName("complaintstype_adddate")
    @Expose
    private String complaintstypeAdddate;
    @SerializedName("complaintstype_modifieddate")
    @Expose
    private String complaintstypeModifieddate;

    public String getComplaintstypeId() {
        return complaintstypeId;
    }

    public void setComplaintstypeId(String complaintstypeId) {
        this.complaintstypeId = complaintstypeId;
    }

    public String getFkProjectid() {
        return fkProjectid;
    }

    public void setFkProjectid(String fkProjectid) {
        this.fkProjectid = fkProjectid;
    }

    public String getComplaintstypeName() {
        return complaintstypeName;
    }

    public void setComplaintstypeName(String complaintstypeName) {
        this.complaintstypeName = complaintstypeName;
    }

    public String getComplaintstypeStatus() {
        return complaintstypeStatus;
    }

    public void setComplaintstypeStatus(String complaintstypeStatus) {
        this.complaintstypeStatus = complaintstypeStatus;
    }

    public String getComplaintstypeOrder() {
        return complaintstypeOrder;
    }

    public void setComplaintstypeOrder(String complaintstypeOrder) {
        this.complaintstypeOrder = complaintstypeOrder;
    }

    public String getComplaintstypeAdddate() {
        return complaintstypeAdddate;
    }

    public void setComplaintstypeAdddate(String complaintstypeAdddate) {
        this.complaintstypeAdddate = complaintstypeAdddate;
    }

    public String getComplaintstypeModifieddate() {
        return complaintstypeModifieddate;
    }

    public void setComplaintstypeModifieddate(String complaintstypeModifieddate) {
        this.complaintstypeModifieddate = complaintstypeModifieddate;
    }

}
