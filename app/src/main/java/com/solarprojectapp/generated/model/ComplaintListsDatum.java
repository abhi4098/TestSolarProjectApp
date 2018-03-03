
package com.solarprojectapp.generated.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ComplaintListsDatum {

    @SerializedName("complain_id")
    @Expose
    private String complainId;
    @SerializedName("complaint")
    @Expose
    private String complaint;
    @SerializedName("project_owner")
    @Expose
    private String projectOwner;
    @SerializedName("project_type")
    @Expose
    private String projectType;
    @SerializedName("end_consumer")
    @Expose
    private String endConsumer;
    @SerializedName("end_consumer_contactno")
    @Expose
    private String endConsumerContactno;
    @SerializedName("complain_description")
    @Expose
    private String complainDescription;
    @SerializedName("state")
    @Expose
    private String state;

    public String getComplainId() {
        return complainId;
    }

    public void setComplainId(String complainId) {
        this.complainId = complainId;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public String getProjectOwner() {
        return projectOwner;
    }

    public void setProjectOwner(String projectOwner) {
        this.projectOwner = projectOwner;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getEndConsumer() {
        return endConsumer;
    }

    public void setEndConsumer(String endConsumer) {
        this.endConsumer = endConsumer;
    }

    public String getEndConsumerContactno() {
        return endConsumerContactno;
    }

    public void setEndConsumerContactno(String endConsumerContactno) {
        this.endConsumerContactno = endConsumerContactno;
    }

    public String getComplainDescription() {
        return complainDescription;
    }

    public void setComplainDescription(String complainDescription) {
        this.complainDescription = complainDescription;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}