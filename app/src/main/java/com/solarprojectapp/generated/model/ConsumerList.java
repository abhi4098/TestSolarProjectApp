
package com.solarprojectapp.generated.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConsumerList {

    @SerializedName("consumer_name")
    @Expose
    private String consumerName;
    @SerializedName("flat_no")
    @Expose
    private String flatNo;
    @SerializedName("addres")
    @Expose
    private String addres;
    @SerializedName("geo_address")
    @Expose
    private String geoAddress;
    @SerializedName("mobile_no")
    @Expose
    private String mobileNo;
    @SerializedName("email_id")
    @Expose
    private String emailId;
    @SerializedName("pin_no")
    @Expose
    private String pinNo;
    @SerializedName("image")
    @Expose
    private String image;

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    public String getFlatNo() {
        return flatNo;
    }

    public void setFlatNo(String flatNo) {
        this.flatNo = flatNo;
    }

    public String getAddres() {
        return addres;
    }

    public void setAddres(String addres) {
        this.addres = addres;
    }

    public String getGeoAddress() {
        return geoAddress;
    }

    public void setGeoAddress(String geoAddress) {
        this.geoAddress = geoAddress;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPinNo() {
        return pinNo;
    }

    public void setPinNo(String pinNo) {
        this.pinNo = pinNo;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
