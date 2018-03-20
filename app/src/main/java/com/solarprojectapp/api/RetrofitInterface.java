package com.solarprojectapp.api;




import com.solarprojectapp.generated.model.ApproveComplaintResponse;
import com.solarprojectapp.generated.model.ChangePasswordResponse;
import com.solarprojectapp.generated.model.ComplaintTypeDropdown;
import com.solarprojectapp.generated.model.DashboardDataResponse;
import com.solarprojectapp.generated.model.LoginResponse;
import com.solarprojectapp.generated.model.NewComplaintResponse;
import com.solarprojectapp.generated.model.ProfileResponse;
import com.solarprojectapp.generated.model.SparePartsPendingResponse;
import com.solarprojectapp.generated.model.SparePartsRequestResponse;
import com.solarprojectapp.generated.model.SubmitComplaintResponse;

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

    public interface AdminApproveCompaintClient {
        @FormUrlEncoded
        @POST("query.php")
        public Call<ApproveComplaintResponse> AdminApproval(
                @Field("id") String id,
                @Field("admin_status") String admin_status,
                @Field("type") String type);
    }
     /*   Call<LoginResponse> userLogIn(@Body Login.LoginDetails loginDetails);

    }*/

   /* public interface UserLoginClient {

        @POST("login.html")
        Call<Login.LoginResponse> userLogIn(@Body Login.LoginDetails loginDetails);

    }


    public interface UserRegistrationClient {
        @POST("register.html")
        Call<RegistrationResponse> userRegistration(@Body Registration registration);

    }

    public interface UserMyProfileClient {
        @POST("getProfile")
        Call<MyProfileResponse> userMyProfile(@Body MyProfile myProfile);

    }

    public interface UserWalletClient {
        @POST("walletbalance")
        Call<MyWalletResponse> userWallet(@Body MyProfile myProfile);

    }

    public interface UserForgotPasswordClient {
        @POST("forgot.html")
        Call<ForgotPasswordResponse> userForgotPassword(@Body ForgotPassword forgotPassword);

    }

    public interface UserTransactionsClient {
        @POST("getusertransactions")
        Call<MyTransactionsResponse> userTransactions(@Body MyTransactions myTransactions);

    }

    public interface UserMyProductClient {
        @POST("getmyproducts")
        Call<MerchantProductResponse> merchantsData(@Body MerchantData merchantData);

    }

    public interface UserMyInvoicesClient {
        @POST("getmyinvoices")
        Call<MerchantInvoicesResponse> merchantsData(@Body MerchantData merchantData);

    }
    public interface UserMySubscriptionClient {
        @POST("getmysubscriptions")
        Call<MerchantSubscriptionsResponse> merchantsData(@Body MerchantData merchantData);

    }
    public interface UserMyTicketsClient {
        @POST("getmytickets")
        Call<MerchantTicketsResponse> merchantsData(@Body MerchantData merchantData);

    }
    public interface UserMyDonationstClient {
        @POST("getmydonations")
        Call<MerchantDonationResponse> merchantsData(@Body MerchantData merchantData);

    }

    public interface UserRequestMoneyClient {
        @POST("requestmoney.html")
        Call<RequestMoneyResponse> requestMoneyData(@Body RequestMoney requestMoney);

    }

    public interface UserAddMoneyClient {
        @POST("add_money.html")
        Call<AddMoneyResponse> addMoneyData(@Body AddMoney addMoney);

    }


    public interface UserReceivedMoneyRequestClient {
        @POST("receivedrequest")
        Call<ReceiveMoneyRequestsResponse> receiveMoneyRequestData(@Body ReceiveMoneyRequests receiveMoneyRequests);

    }

    public interface getOTPClient {
        @POST("generateSendOTP.html")
        Call<GetOTPResponse> getOTPData(@Body GetOTP getOTP);

    }

    public interface sendMoneyVerifiedClient {
        @POST("paymoneyrequest.html")
        Call<SendMoneyVerifiedReponse> sendMoneyVerifiedData(@Body SendMoneyVerified sendMoneyVerified);

    }

    public interface referFriendClient {
        @POST("referfriend.html")
        Call<ReferFriendResponse> referFriendData(@Body ReferFriend referFriend);

    }

    public interface editProfileClient {
        @POST("updatemyprofile")
        Call<EditProfileResponse> editProfileData(@Body EditProfile editProfile);

    }


    public interface getCountryListClient {
        @POST("getcountryjson")
        Call<CountryListResponse> countryListData(@Body CountryList countryList);

    }


    public interface getStateListClient {
        @POST("getstatesjson")
        Call<StateListResponse> stateListData(@Body StateList stateList);

    }

    public interface getSentMoneyClient {
        @POST("sentrequest")
        Call<SentMoneyResponse> sentMoneyData(@Body SentMoney sentMoney);

    }

    public interface getMyBankAccountClient {
        @POST("getUserBankAccounts")
        Call<BankAccountResponse> myBankAccountData(@Body BankAccount bankAccount);

    }

    public interface getNotificationClient {
        @POST("getUserActivity")
        Call<NotificationResponse> notificationData(@Body Notification notification);

    }

    public interface updateProfilePicClient {
        @Multipart
        @POST("profileimage")
        Call<UploadPhotoResponse> uploadImageData(@Part MultipartBody.Part profilepic, @Part("profilepic") RequestBody name);

    }

    public interface facebookLoginClient {
        @POST("fblogin.html")
        Call<FacebookLoginResponse> facebookLoginData(@Body FacebookLogin facebookLogin);

    }

    public interface googleLoginClient {
        @POST("glogin.html")
        Call<GoogleLoginResponse> googleLoginData(@Body GoogleLogin googleLogin);

    }

    public interface imageNameServerClient {
        @POST("updateImageName")
        Call<ImageNameUpdateResponse> imageNameServerClientData(@Body ImageNameUpdate imageNameUpdate);

    }

    public interface getUserDetailsClient {
        @POST("getUser.html")
        Call<GetUserResponse> userData(@Body GetUser getUser);

    }

    public interface getClientDetailsClient {
        @POST("getClient.html")
        Call<GetClientResponse> clientData(@Body GetClient getClient);

    }

    public interface getLocalBanksClient {
        @POST("getuserlocalaccounts")
        Call<LocalBankAccountResponse> localBankDataData(@Body LocalBankAccount localBankAccount);

    }

    public interface withdrawMoneyClient {
        @POST("withdraw.html")
        Call<WithdrawalResponse> withdrawMoneyData(@Body Withdrawal withdrawal);

    }

    public interface addProductClient {
        @POST("addproduct")
        Call<AddProductResponse> addProductData(@Body AddProduct addProduct);

    }

    public interface editProductClient {
        @POST("editproduct")
        Call<EditProductResponse> editProductData(@Body EditProduct editProduct);

    }

    public interface addDonationClient {
        @POST("adddonation")
        Call<AddDonationResponse> addDonationData(@Body AddDonation addDonation);

    }

    public interface editDonationClient {
        @POST("editdonation")
        Call<EditDonationResponse> editDonationData(@Body EditDonation editDonation);

    }

    public interface addInvoiceClient {
        @POST("addinvoice")
        Call<AddInvoiceResponse> addInvoiceData(@Body AddInvoice addInvoice);

    }

    public interface generateCodeClient {
        @POST("processor.html?product=193&member=markbrosnon@hotmail.com&action=product&send=yes")
        Call<AddProductResponse> genData();

    }


    public interface addSubscriptionClient {
        @POST("addsubscription")
        Call<AddSubscriptionResponse> addSubscriptionData(@Body AddSubscription addSubscription);

    }

    public interface editSubsClient {
        @POST("editsubscription")
        Call<EditSubscriptionResponse> editSubsData(@Body EditSubscription editSubscription);

    }

    public interface addTicketClient {
        @POST("addticket")
        Call<AddTicketResponse> addTicketData(@Body AddTicket addTicket);

    }

    public interface editTicketClient {
        @POST("editticket")
        Call<EditTicketResponse> editTicketsData(@Body EditTicket editTicket);

    }

   *//* public interface addProductClient {
        @POST("addproduct")
        Call<AddProductResponse> addProductData(@Body AddProduct addProduct);

    }

    public interface addProductClient {
        @POST("addproduct")
        Call<AddProductResponse> addProductData(@Body AddProduct addProduct);

    }*/
}
