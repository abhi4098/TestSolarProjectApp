package com.solarprojectapp.api;




import com.solarprojectapp.generated.model.ApproveComplaintResponse;
import com.solarprojectapp.generated.model.AssignComplaintResponse;
import com.solarprojectapp.generated.model.ChangePasswordResponse;
import com.solarprojectapp.generated.model.ClientConsumerCount;
import com.solarprojectapp.generated.model.ComplaintTypeDropdown;
import com.solarprojectapp.generated.model.ConsumerCountClientResponse;
import com.solarprojectapp.generated.model.DashboardDataResponse;
import com.solarprojectapp.generated.model.LoginResponse;
import com.solarprojectapp.generated.model.NewComplaintResponse;
import com.solarprojectapp.generated.model.PreventiveMaintainanceResponse;
import com.solarprojectapp.generated.model.ProfileResponse;
import com.solarprojectapp.generated.model.RejectedComplaintListResponse;
import com.solarprojectapp.generated.model.SendTokenToServerResponse;
import com.solarprojectapp.generated.model.SparePartAdminAproveResponse;
import com.solarprojectapp.generated.model.SparePartsPendingResponse;
import com.solarprojectapp.generated.model.SparePartsRequestResponse;
import com.solarprojectapp.generated.model.SubmitComplaintResponse;
import com.solarprojectapp.generated.model.TechnicalPartnerFunctionResponse;
import com.solarprojectapp.generated.model.TechnicalPartnerListResponse;
import com.solarprojectapp.generated.model.TotalConsumerListResponse;
import com.solarprojectapp.generated.model.UploadPhotoResponse;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

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


    public interface UserTechCompaintListClient {
        @FormUrlEncoded
        @POST("query.php")
        public Call<NewComplaintResponse> userTechComplaintList(
                @Field("type") String type,
                @Field("tech_userid") String tech_userid);
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


    public interface SparePartsRequestByTechPartnerClient {
        @FormUrlEncoded
        @POST("query.php")
        public Call<SparePartsRequestResponse> sparePartsRequestBtPartnerList(
                @Field("complaintstypeid") String complaintstypeid,
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

    public interface AdminApprovesparePartClient {
        @FormUrlEncoded
        @POST("query.php")
        public Call<SparePartAdminAproveResponse> AdminSparePartApproval(
                @Field("sparepart_requestid") String sparepart_requestid,
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

    public interface TotalConsumerCountForClient {
        @FormUrlEncoded
        @POST("query.php")
        public Call<ConsumerCountClientResponse> totalConsumerCountClient(
                @Field("id") String id,
                @Field("type") String type);
    }

    public interface AssignTechPartnerClient {
        @FormUrlEncoded
        @POST("query.php")
        public Call<AssignComplaintResponse> AssignTechPartner(
                @Field("tech_userid") String tech_userid,
                @Field("complaintstypeid") String complaintstypeid ,
                @Field("admin_id") String admin_id,
                @Field("type") String type);
    }

    public interface TechnicalPartnerFunctionClient {
        @FormUrlEncoded
        @POST("query.php")
        public Call<TechnicalPartnerFunctionResponse> technicalPartnerFunction(
                @Field("tech_userid") String tech_userid,
                @Field("complaintstypeid") String complaintstypeid,
                @Field("type") String type);
    }

    public interface UserSubmitSparePartClient {
        @Multipart
        @POST("query.php")
        public Call<SubmitComplaintResponse> userSubmitSparePart(
                @Part("sparepartid") RequestBody sparepartid,
                @Part("technicalpartnerid") RequestBody technicalpartnerid,
                @Part("complainid") RequestBody complainid,
                @Part("quantity") RequestBody quantity,
                @Part MultipartBody.Part request_image,
                //@Part MultipartBody.Part requestimage,
                @Part("type") RequestBody type);
    }

    public interface PreventiveMaintainanceClient {
        @FormUrlEncoded
        @POST("query.php")
        public Call<PreventiveMaintainanceResponse> preventiveMaintainanceList(
                @Field("userid") String userid,
                @Field("type") String type);
    }

    public interface SendTokenToServerClient {
        @FormUrlEncoded
        @POST("query.php")
        public Call<SendTokenToServerResponse> sendTokenToServer(
                @Field("userid") String userid,
                @Field("token_id") String token_id,
                @Field("type") String type);
    }

    public interface updateProfilePicClient {
        @Multipart
        @POST("query.php")
        Call<ResponseBody> uploadImageData(@Part MultipartBody.Part profilepic ,
                                           @Part("request_image") RequestBody name);

    }
}
