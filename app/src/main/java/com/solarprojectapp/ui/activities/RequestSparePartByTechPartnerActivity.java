package com.solarprojectapp.ui.activities;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.solarprojectapp.R;
import com.solarprojectapp.api.ApiAdapter;
import com.solarprojectapp.api.RetrofitInterface;
import com.solarprojectapp.generated.model.SparePartsRequestResponse;
import com.solarprojectapp.generated.model.SparepartsrequestList;
import com.solarprojectapp.generated.model.SubmitComplaintResponse;
import com.solarprojectapp.utils.LoadingDialog;
import com.solarprojectapp.utils.NetworkUtils;
import com.solarprojectapp.utils.PrefUtils;
import com.solarprojectapp.utils.SnakBarUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.solarprojectapp.api.ApiEndPoints.MAIN_BASE_URL;


public class RequestSparePartByTechPartnerActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;
    String complaintId;
    String imgDecodableString;
    private RetrofitInterface.UserCompaintTypeClient userCompaintTypeAdapter;
    private RetrofitInterface.UserSubmitSparePartClient userSubmitSparePartClient;
    ArrayList<SparepartsrequestList> sparePartsRequestedList = null;
    ArrayList<String> showSparePartsRequestedList = null;


    @BindView(R.id.back_icon)
    ImageView ivBackIcon;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_app_title)
    TextView tvAppTitle;
    String spComplaintSelectedItem = "Select Spare Part";
    private RetrofitInterface.SparePartsRequestByTechPartnerClient sparePartsRequestByTechPartnerClient;
    private int PICK_FROM_GALLERY = 1;
    private static final int REQUEST_WRITE_STORAGE = 112;
    private final String[] requiredPermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};


    @BindView(R.id.spinner_complaint)
    Spinner spinner;
    String projectid,sparePartId;
    @OnClick(R.id.add_spare_part_image)
    public void addSparePartImage()
    {
        Log.e("abhi", "addSparePartImage: .........." );
        Checkpermission();
    }

    private void Checkpermission() {

        if (getPermissions()) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {
                Log.e("abhi", "Checkpermission:..........if " );
                makeRequest();
            } else {
                Log.e("abhi", "Checkpermission:..........else " );
                makeRequest();
            }
        } else {
            Log.e("abhi", "Checkpermission:..........set dialog for image" );
          setDialogForImage();
        }
    }


    private void setDialogForImage() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_select_from_source);
        ImageView btnCamera = (ImageView) dialog.findViewById(R.id.btnCamera);
        ImageView btnDocs = (ImageView) dialog.findViewById(R.id.btnDoc);
        TextView txtDoc = (TextView) dialog.findViewById(R.id.txtDoc);
        btnDocs.setVisibility(View.GONE);
        txtDoc.setVisibility(View.GONE);
        ImageView btnGallery = (ImageView) dialog.findViewById(R.id.btnGallery);

        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.TOP;
        wmlp.x = 0;   //x position
        Resources r = getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, r.getDisplayMetrics());
        wmlp.y = (int) px; //y position
        Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        dialog.getWindow().setLayout((6 * width) / 10, Toolbar.LayoutParams.WRAP_CONTENT);
        dialog.show();


        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);

                dialog.cancel();
            }
        });

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(Intent.createChooser(galleryIntent, "Select Picture"), PICK_FROM_GALLERY);
                dialog.cancel();

            }
        });

    }

    private boolean getPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
            return true;
        else if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
            return true;
        return false;
    }
    protected void makeRequest() {
        ActivityCompat.requestPermissions(this,
                requiredPermissions,
                REQUEST_WRITE_STORAGE);
    }


    //private EditText result;
    @OnClick(R.id.submit_add_complaint)
    public void submitComplaint()
    {
        if (spComplaintSelectedItem.equals("Select Spare Part"))
        {
            Toast.makeText(getApplicationContext(),"Select Spare Part",Toast.LENGTH_SHORT).show();
        }
        else
        {
            for (int i = 0; i < sparePartsRequestedList.size(); i++) {
                if (sparePartsRequestedList.get(i).getSparepartName().equals(spComplaintSelectedItem))
                {
                   // projectid=sparePartsRequestedList.get(i).getFkProjectid();
                    sparePartId = sparePartsRequestedList.get(i).getSparepartId();
                }
            }
            submitComplaintToAdmin();
        }
    }



    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private String getRealPathFromURI(Uri tempUri) {
        Cursor cursor = getContentResolver().query(tempUri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == 0 && resultCode == RESULT_OK) {


            Bitmap bp = (Bitmap) data.getExtras().get("data");
            Log.e("abhi", "onActivityResult: bp---------"+bp );
            if (bp !=null) {

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bp.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                byte[] byteFormat = stream.toByteArray();
                // get the base 64 string
                String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);
                Log.e("abhi", "onActivityResult:.......... camera" + imgString);


            }


        } else if (requestCode == PICK_FROM_GALLERY && resultCode == RESULT_OK) {
            if(data.getClipData() != null) {
                int count = data.getClipData().getItemCount();
                int currentItem = 0;
                while(currentItem < count) {
                    Uri imageUri = data.getClipData().getItemAt(currentItem).getUri();

                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(imageUri, filePathColumn, null, null, null);
                    if (cursor != null) {
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        imgDecodableString = cursor.getString(columnIndex);
                        cursor.close();
                        // sendImagesToServerFromCamera(imgDecodableString);
                    }

                    Bitmap bm = BitmapFactory.decodeFile(imgDecodableString);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.JPEG, 70, baos); //bm is the bitmap object
                    byte[] b = baos.toByteArray();
                    String imgString = Base64.encodeToString(b, Base64.NO_WRAP);
                    Log.e("abhi", "onActivityResult: .........image uri"+imgString );
                    currentItem = currentItem + 1;
                }
            } else if(data.getData() != null) {
                String imagePath = data.getData().getPath();
                Log.e("abhi", "onActivityResult:.... image path"+imagePath );
                //do something with the image (save it to some directory or whatever you need to do with it here)
            }


        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_request_sparepart_by_techpartner);
        mContext = RequestSparePartByTechPartnerActivity.this;
        ButterKnife.bind(this);
        ivBackIcon.setOnClickListener(this);
        complaintId = getIntent().getStringExtra("COMPLAINT_ID");
        tvAppTitle.setText("Request Spare Part");

        setUpRestAdapter();
        getRequestForSparePartsByTechPartner();


    }


    private void setUpRestAdapter() {
        sparePartsRequestByTechPartnerClient = ApiAdapter.createRestAdapter(RetrofitInterface.SparePartsRequestByTechPartnerClient.class, MAIN_BASE_URL, this);
        userSubmitSparePartClient = ApiAdapter.createRestAdapter(RetrofitInterface.UserSubmitSparePartClient.class, MAIN_BASE_URL, this);
    }

    private void getRequestForSparePartsByTechPartner() {
        LoadingDialog.showLoadingDialog(this,"Loading...");
        Call<SparePartsRequestResponse> call = sparePartsRequestByTechPartnerClient.sparePartsRequestBtPartnerList(complaintId,"listtechrequestsparepart");
        if (NetworkUtils.isNetworkConnected(this)) {
            call.enqueue(new Callback<SparePartsRequestResponse>() {

                @Override
                public void onResponse(Call<SparePartsRequestResponse> call, Response<SparePartsRequestResponse> response) {

                    if (response.isSuccessful()) {

                        if (response.body().getSuccess().equals("true")) {
                            Log.e("abhi", "onResponse: .............." +response.body().getSuccess());
                            setSparePartsRequestedByTechPartner(response);
                            LoadingDialog.cancelLoading();

                        }
                        else
                        {
                            LoadingDialog.cancelLoading();

                            Toast.makeText(RequestSparePartByTechPartnerActivity.this,response.body().getSuccess(),Toast.LENGTH_SHORT).show();
                        }



                    }

                }

                @Override
                public void onFailure(Call<SparePartsRequestResponse> call, Throwable t) {
                    Toast.makeText(RequestSparePartByTechPartnerActivity.this,"Error occur",Toast.LENGTH_SHORT).show();
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(RequestSparePartByTechPartnerActivity.this);
        }
    }



    private void submitComplaintToAdmin() {
        LoadingDialog.showLoadingDialog(this,"Loading...");
        Call<SubmitComplaintResponse> call = userSubmitSparePartClient.userSubmitSparePart(sparePartId,PrefUtils.getFkId(RequestSparePartByTechPartnerActivity.this),complaintId,"0","nil","requestsparepartbychnicalpartner");
        if (NetworkUtils.isNetworkConnected(this)) {
            call.enqueue(new Callback<SubmitComplaintResponse>() {

                @Override
                public void onResponse(Call<SubmitComplaintResponse> call, Response<SubmitComplaintResponse> response) {

                    if (response.isSuccessful()) {

                        if (response.body().getSuccess().equals("true")) {

                            Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
                            finish();
                            LoadingDialog.cancelLoading();
                        }
                        else
                        {
                            LoadingDialog.cancelLoading();

                            Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        }



                    }

                }

                @Override
                public void onFailure(Call<SubmitComplaintResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"Error occur",Toast.LENGTH_SHORT).show();
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(this);
        }
    }


    private void setSparePartsRequestedByTechPartner(Response<SparePartsRequestResponse> response) {
        sparePartsRequestedList = new ArrayList<>();
        showSparePartsRequestedList = new ArrayList<>();
        showSparePartsRequestedList.add(spComplaintSelectedItem);
        Log.e("abhi", "setNewComplaints: "+response.body().getSparepartsrequestList().size() );
        for (int i = 0; i < response.body().getSparepartsrequestList().size(); i++) {
            for (int j = 0; j < response.body().getSparepartsrequestList().get(i).size(); j++) {
                SparepartsrequestList sparepartsrequestList = new SparepartsrequestList();
                sparepartsrequestList.setSparepartUniquid(response.body().getSparepartsrequestList().get(i).get(j).getSparepartUniquid());
                sparepartsrequestList.setSparepartBrand(response.body().getSparepartsrequestList().get(i).get(j).getSparepartBrand());
                sparepartsrequestList.setSparepartName(response.body().getSparepartsrequestList().get(i).get(j).getSparepartName());
                sparepartsrequestList.setSparepartCreatedate(response.body().getSparepartsrequestList().get(i).get(j).getSparepartCreatedate());
                sparepartsrequestList.setSparepartStatus(response.body().getSparepartsrequestList().get(i).get(j).getSparepartStatus());
                sparepartsrequestList.setSparepartPrice(response.body().getSparepartsrequestList().get(i).get(j).getSparepartPrice());
                sparepartsrequestList.setSparepartId(response.body().getSparepartsrequestList().get(i).get(j).getSparepartId());

                sparePartsRequestedList.add(sparepartsrequestList);
                showSparePartsRequestedList.add(response.body().getSparepartsrequestList().get(i).get(j).getSparepartName());
                Log.e("abhi", "onResponse:..new complaint list " + sparePartsRequestedList.get(i).getSparepartCreatedate());

            }
        }

        final ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, showSparePartsRequestedList);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(categoryAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spComplaintSelectedItem = spinner.getSelectedItem().toString();
                Log.e("abhi", "onItemSelected: " +spComplaintSelectedItem );

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });






    }


    @Override
    public void onClick(View view) {

        super.onBackPressed();

    }
}


