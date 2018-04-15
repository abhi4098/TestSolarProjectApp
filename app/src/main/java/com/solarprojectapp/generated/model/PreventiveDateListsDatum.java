
package com.solarprojectapp.generated.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PreventiveDateListsDatum {

    @SerializedName("complaint")
    @Expose
    private String complaint;
    @SerializedName("create_date")
    @Expose
    private String createDate;
    @SerializedName("complain_close_date")
    @Expose
    private String complainCloseDate;
    @SerializedName("installation_date")
    @Expose
    private String installationDate;

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getComplainCloseDate() {
        return complainCloseDate;
    }

    public void setComplainCloseDate(String complainCloseDate) {
        this.complainCloseDate = complainCloseDate;
    }

    public String getInstallationDate() {
        return installationDate;
    }

    public void setInstallationDate(String installationDate) {
        this.installationDate = installationDate;
    }

}
