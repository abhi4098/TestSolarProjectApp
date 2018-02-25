
package com.solarprojectapp.generated.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Login {

    public static class LoginDetails {

        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("password")
        @Expose
        private String password;

        @SerializedName("type")
        @Expose
        private String type;


        public LoginDetails(String username, String password,String type) {
            this.username = username;
            this.password = password;
            this.type = type;
        }




    }

    }


