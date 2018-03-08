
package com.solarprojectapp.generated.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdminSummary {

    @SerializedName("Total_Consumer")
    @Expose
    private Integer totalConsumer;
    @SerializedName("Total_Client")
    @Expose
    private Integer totalClient;
    @SerializedName("Total_Complaints")
    @Expose
    private Integer totalComplaints;
    @SerializedName("Total_Opencomplaints")
    @Expose
    private Integer totalOpencomplaints;
    @SerializedName("Total_overduecomplaints")
    @Expose
    private Integer totalOverduecomplaints;
    @SerializedName("Total_Closedcomplaints")
    @Expose
    private Integer totalClosedcomplaints;
    @SerializedName("Total_complaintstobeclosedbytoday")
    @Expose
    private Integer totalComplaintstobeclosedbytoday;
    @SerializedName("Total_spareparts_pending")
    @Expose
    private Integer totalSparepartsPending;
    @SerializedName("Total_spareparts_requested")
    @Expose
    private Integer totalSparepartsRequested;
    @SerializedName("Total_sparepartstobeclosedbytoday")
    @Expose
    private Integer totalSparepartstobeclosedbytoday;

    public Integer getTotalConsumer() {
        return totalConsumer;
    }

    public void setTotalConsumer(Integer totalConsumer) {
        this.totalConsumer = totalConsumer;
    }

    public Integer getTotalClient() {
        return totalClient;
    }

    public void setTotalClient(Integer totalClient) {
        this.totalClient = totalClient;
    }

    public Integer getTotalComplaints() {
        return totalComplaints;
    }

    public void setTotalComplaints(Integer totalComplaints) {
        this.totalComplaints = totalComplaints;
    }

    public Integer getTotalOpencomplaints() {
        return totalOpencomplaints;
    }

    public void setTotalOpencomplaints(Integer totalOpencomplaints) {
        this.totalOpencomplaints = totalOpencomplaints;
    }

    public Integer getTotalOverduecomplaints() {
        return totalOverduecomplaints;
    }

    public void setTotalOverduecomplaints(Integer totalOverduecomplaints) {
        this.totalOverduecomplaints = totalOverduecomplaints;
    }

    public Integer getTotalClosedcomplaints() {
        return totalClosedcomplaints;
    }

    public void setTotalClosedcomplaints(Integer totalClosedcomplaints) {
        this.totalClosedcomplaints = totalClosedcomplaints;
    }

    public Integer getTotalComplaintstobeclosedbytoday() {
        return totalComplaintstobeclosedbytoday;
    }

    public void setTotalComplaintstobeclosedbytoday(Integer totalComplaintstobeclosedbytoday) {
        this.totalComplaintstobeclosedbytoday = totalComplaintstobeclosedbytoday;
    }

    public Integer getTotalSparepartsPending() {
        return totalSparepartsPending;
    }

    public void setTotalSparepartsPending(Integer totalSparepartsPending) {
        this.totalSparepartsPending = totalSparepartsPending;
    }

    public Integer getTotalSparepartsRequested() {
        return totalSparepartsRequested;
    }

    public void setTotalSparepartsRequested(Integer totalSparepartsRequested) {
        this.totalSparepartsRequested = totalSparepartsRequested;
    }

    public Integer getTotalSparepartstobeclosedbytoday() {
        return totalSparepartstobeclosedbytoday;
    }

    public void setTotalSparepartstobeclosedbytoday(Integer totalSparepartstobeclosedbytoday) {
        this.totalSparepartstobeclosedbytoday = totalSparepartstobeclosedbytoday;
    }

}
