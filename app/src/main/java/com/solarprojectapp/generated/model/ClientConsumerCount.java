
package com.solarprojectapp.generated.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClientConsumerCount {

    @SerializedName("Total_Consumer")
    @Expose
    private Integer totalConsumer;

    public Integer getTotalConsumer() {
        return totalConsumer;
    }

    public void setTotalConsumer(Integer totalConsumer) {
        this.totalConsumer = totalConsumer;
    }

}
