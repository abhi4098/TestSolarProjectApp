
package com.solarprojectapp.generated.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginDatum {

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("usertype")
    @Expose
    private String usertype;
    @SerializedName("fk_loggedid")
    @Expose
    private String fkLoggedid;
    @SerializedName("user_status")
    @Expose
    private String userStatus;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getFkLoggedid() {
        return fkLoggedid;
    }

    public void setFkLoggedid(String fkLoggedid) {
        this.fkLoggedid = fkLoggedid;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

}
