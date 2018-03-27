package com.solarprojectapp.api;




import com.solarprojectapp.generated.model.ApproveComplaintResponse;
import com.solarprojectapp.generated.model.AssignComplaintResponse;
import com.solarprojectapp.generated.model.ChangePasswordResponse;
import com.solarprojectapp.generated.model.ComplaintTypeDropdown;
import com.solarprojectapp.generated.model.DashboardDataResponse;
import com.solarprojectapp.generated.model.LoginResponse;
import com.solarprojectapp.generated.model.NewComplaintResponse;
import com.solarprojectapp.generated.model.ProfileResponse;
import com.solarprojectapp.generated.model.RejectedComplaintListResponse;
import com.solarprojectapp.generated.model.SparePartsPendingResponse;
import com.solarprojectapp.generated.model.SparePartsRequestResponse;
import com.solarprojectapp.generated.model.SubmitComplaintResponse;
import com.solarprojectapp.generated.model.TechnicalPartnerListResponse;
import com.solarprojectapp.generated.model.TotalConsumerListResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public class RetrofitInterface {

    public interface UserLoginClient {
        @FormUrlEncoded
        @POST("query.php")
        public Call<LoginResponse> userLogIn(
                @Field("username") String username,
                @Field("password") String password,
                @Field("type") String type);
    }

    public interface UserProfileClient {
        @FormUrlEncoded
        @POST("query.php")
        public Call<ProfileResponse> userProfile(
                @Field("username") String username,
                @Field("user_type") String userType,
                @Field("id") String id,
                @Field("type") String type);
    }

    public interface UserChangePasswordClient {
        @FormUrlEncoded
        @POST("query.php")
        public Call<ChangePasswordResponse> userChangePassword(
                @Field("username") String username,
                @Field("oldpass") String oldpass,
                @Field("newpass") String newpass,
                @Field("type") String type);
    }

    public interface UserSubmitComplaintClient {
        @FormUrlEncoded
        @POST("query.php")
        public Call<SubmitComplaintResponse> userSubmitComplaint(
                @Field("userid") String userId,
                @Field("projectid") String projectid,
                @Field("complaintstypeid") String complaintstypeid,
                @Field("remarks") String remarks,
                @Field("type") String type);
    }

    public interface UserCompaintListClient {
        @FormUrlEncoded
        @POST("query.php")
        public Call<NewComplaintResponse> userNewComplaintList(
                @Field("type") String type);
    }

    public interface UserRejectedCompaintListClient {
        @FormUrlEncoded
        @POST("query.php")
        public Call<RejectedComplaintListResponse> userRejectedComplaintList(
                @Field("type") String type);
    }

    public interface UserCompaintTypeClient {
        @FormUrlEncoded
        @POST("query.php")
        public Call<ComplaintTypeDropdown> userComplaintTypeList(
                @Field("type") String type);
    }

    public interface SparePartsPendingClient {
        @FormUrlEncoded
        @POST("query.php")
        public Call<SparePartsPendingResponse> sparePartsPendingList(
                @Field("type") String type);
    }

    public interface SparePartsRequestClient {
        @FormUrlEncoded
        @POST("query.php")
        public Call<SparePartsRequestResponse> sparePartsRequestList(
                @Field("type") String type);
    }

    public interface SparePartsToBeClosedTodayClient {
        @FormUrlEncoded
        @POST("query.php")
        public Call<SparePartsRequestResponse> sparePartsRequestList(
                @Field("type") String type);
    }

    public interface AdminDataClient {
        @FormUrlEncoded
        @POST("query.php")
        public Call<DashboardDataResponse> DashboardDataList(
                @Field("type") String type);
    }

    public interface TechnicalPartnerClient {
        @FormUrlEncoded
        @POST("query.php")
        public Call<TechnicalPartnerListResponse> TechPartnerList(
                @Field("type") String type,
                 @Field("id") String id);
    }

    public interface AdminApproveCompaintClient {
        @FormUrlEncoded
        @POST("query.php")
        public Call<ApproveComplaintResponse> AdminApproval(
                @Field("id") String id,
                @Field("admin_status") String admin_status,
                @Field("type") String type);
    }

    public interface TotalConsumerClient {
        @FormUrlEncoded
        @POST("query.php")
        public Call<TotalConsumerListResponse> totalConsumer(
                @Field("id") String id,
                @Field("user_type") String user_type,
                @Field("type") String type);
    }

    public interface AssignTechPartnerClient {
        @FormUrlEncoded
        @POST("query.php")
        public Call<AssignComplaintResponse> AssignTechPartner(
                @Field("userid") String userid,
                @Field("tech_userid") String tech_userid,
                @Field("complaintstypeid") String complaintstypeid ,
                @Field("admin_id") String admin_id,
                @Field("type") String type);
    }
}
