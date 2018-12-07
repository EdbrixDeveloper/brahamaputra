package com.brahamaputra.mahindra.brahamaputra.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.InputFilter;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.brahamaputra.mahindra.brahamaputra.Application;
import com.brahamaputra.mahindra.brahamaputra.BuildConfig;
import com.brahamaputra.mahindra.brahamaputra.Data.EBBillUploadReceipt;
import com.brahamaputra.mahindra.brahamaputra.Data.EBlSubmitResposeData;
import com.brahamaputra.mahindra.brahamaputra.R;
import com.brahamaputra.mahindra.brahamaputra.Utils.Constants;
import com.brahamaputra.mahindra.brahamaputra.Utils.DecimalDigitsInputFilter;
import com.brahamaputra.mahindra.brahamaputra.Utils.SessionManager;
import com.brahamaputra.mahindra.brahamaputra.Volley.GsonRequest;
import com.brahamaputra.mahindra.brahamaputra.baseclass.BaseActivity;
import com.brahamaputra.mahindra.brahamaputra.commons.AlertDialogManager;
import com.brahamaputra.mahindra.brahamaputra.commons.GlobalMethods;
import com.brahamaputra.mahindra.brahamaputra.commons.OfflineStorageWrapper;
import com.brahamaputra.mahindra.brahamaputra.helper.OnSpinnerItemClick;
import com.brahamaputra.mahindra.brahamaputra.helper.SearchableSpinnerDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UploadEBPaymentDetailsActivity extends BaseActivity {

    private static final String TAG = UploadEBPaymentDetailsActivity.class.getSimpleName();

    private TextView mUploadEbPaymentEditTextTicketNumber;
    private TextView mUploadEbPaymentEditTextSiteId;
    private TextView mUploadEbPaymentEditTextSiteName;
    private TextView mUploadEbPaymentTextViewPaymentTypeVal;
    private ImageView mUploadEbPaymentButtonUploadPhoto;
    private ImageView mUploadEbPaymentButtonUploadPhotoView;
    private EBBillUploadReceipt ebBillUploadReceipt;

    private TextView mUploadEbPaymentTextViewPaymentReceiptNumber;
    private EditText mUploadEbPaymentEditTextPaymentReceiptNumber;
    private TextView mUploadEbPaymentTextViewReceiptPaymentDate;
    private EditText mUploadEbPaymentEditTextReceiptPaymentDate;
    private TextView mUploadEbPaymentTextViewPaymentAmount;
    private EditText mUploadEbPaymentEditTextPaymentAmount;


    private LinearLayout mUploadEbPaymentLinearLayout_paymentReceiptNumber;
    private LinearLayout mUploadEbPaymentLinearLayout_receiptPaymentDate;
    private LinearLayout mUploadEbPaymentLinearLayout_paymentAmount;
    private LinearLayout mUploadEbPaymentLinearLayout_uploadPhoto;

    /*uploadEbPayment_linearLayout_paymentReceiptNumber;
    uploadEbPayment_linearLayout_receiptPaymentDate;
    uploadEbPayment_linearLayout_paymentAmount;
    uploadEbPayment_linearLayout_uploadPhoto;*/


    private OfflineStorageWrapper offlineStorageWrapper;
    private SessionManager sessionManager;
    private String userId = "";
    private String ticketId = "";
    private String ticketName = "";

    private String imageFileName;
    private Uri imageFileNameUri = null;
    private String base64String = "";
    String request_id;
    String ticket_no;
    String site_id;
    String site_name;
    private AlertDialogManager alertDialogManager;

    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 101;

    Calendar myCalendar1 = Calendar.getInstance();
    final DatePickerDialog.OnDateSetListener dateBillfrom = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar1.set(Calendar.YEAR, year);
            myCalendar1.set(Calendar.MONTH, monthOfYear);
            myCalendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabelPaymentDate();
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_eb_payment_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle("Upload Payment Details");

        Intent intent = getIntent();
        request_id = intent.getStringExtra("request_id");
        ticket_no = intent.getStringExtra("ticket_no");
        site_id = intent.getStringExtra("site_id");
        site_name = intent.getStringExtra("site_name");
        assignViews();
        setListners();
        alertDialogManager = new AlertDialogManager(UploadEBPaymentDetailsActivity.this);
        sessionManager = new SessionManager(UploadEBPaymentDetailsActivity.this);
        ticketId = sessionManager.getSessionUserTicketId();
        ticketName = GlobalMethods.replaceAllSpecialCharAtUnderscore(sessionManager.getSessionUserTicketName());
        userId = sessionManager.getSessionUserId();
        offlineStorageWrapper = OfflineStorageWrapper.getInstance(UploadEBPaymentDetailsActivity.this, userId, ticketName);

        mUploadEbPaymentEditTextTicketNumber.setText(ticket_no);
        mUploadEbPaymentEditTextSiteId.setText(site_id);
        mUploadEbPaymentEditTextSiteName.setText(site_name);

        mUploadEbPaymentTextViewPaymentTypeVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(UploadEBPaymentDetailsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_payment_type))),
                        "Type of Payment",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {
                        visibilityOfLayout();
                        mUploadEbPaymentTextViewPaymentTypeVal.setText(item.get(position));
                    }
                });
            }
        });

        mUploadEbPaymentButtonUploadPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageFileNameUri != null) {
                    GlobalMethods.showImageDialog(UploadEBPaymentDetailsActivity.this, imageFileNameUri);
                } else {
                    Toast.makeText(UploadEBPaymentDetailsActivity.this, "Image not available...!", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.submit_icon_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            case R.id.menuSubmit:
                if (checkValidation()) {
                    showSettingsAlert();
                    //finish();
                }
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private boolean checkValidation() {
        String payment_type = mUploadEbPaymentTextViewPaymentTypeVal.getText().toString();

        String ebPaymentReceiptNumber = mUploadEbPaymentEditTextPaymentReceiptNumber.getText().toString();
        String ebPaymentDate = mUploadEbPaymentEditTextReceiptPaymentDate.getText().toString();
        String ebPaymentAmount = mUploadEbPaymentEditTextPaymentAmount.getText().toString();

        if (request_id.isEmpty() || request_id == null) {
            showToast("Invalid Request ID ");
            return false;
        } else if (ticket_no.isEmpty() || ticket_no == null) {
            showToast("Invalid Ticket ");
            return false;
        } else if (site_id.isEmpty() || site_id == null) {
            showToast("Invalid Site ID ");
            return false;
        } else if (site_name.isEmpty() || site_name == null) {
            showToast("Invalid Site Name ");
            return false;
        } else if (payment_type.isEmpty() || payment_type == null) {
            showToast("Select Payment Type ");
            return false;
        } else if (ebPaymentReceiptNumber.isEmpty() || ebPaymentReceiptNumber == null) {
            showToast("Enter Payment Receipt Number ");
            return false;
        } else if (ebPaymentDate.isEmpty() || ebPaymentDate == null) {
            showToast("Select Payment Date ");
            return false;
        } else if (ebPaymentAmount.isEmpty() || ebPaymentAmount == null) {
            showToast("Enter Payment Amount ");
            return false;
        } else if (base64String.isEmpty() || base64String == null) {
            showToast("Upload Receipt ");
            return false;
        } else return true;
    }

    private void showSettingsAlert() {

        //alertDialogManager = new AlertDialogManager(uploadEbPaymentActivity.this);
        alertDialogManager.Dialog("Confirmation", "Do you want to Upload Receipt?", "Yes", "No", new AlertDialogManager.onTwoButtonClickListner() {
            @Override
            public void onPositiveClick() {
                submitDetails();
            }

            @Override
            public void onNegativeClick() {

            }
        }).show();

    }

    private void submitDetails() {

        try {
            showBusyProgress();
            String userId = sessionManager.getSessionUserId();
            String accessToken = sessionManager.getSessionDeviceToken();
            String paymentMode = mUploadEbPaymentTextViewPaymentTypeVal.getText().toString();

            String ebPaymentReceiptNumber = mUploadEbPaymentEditTextPaymentReceiptNumber.getText().toString();
            String ebPaymentDate = mUploadEbPaymentEditTextReceiptPaymentDate.getText().toString();
            String ebPaymentAmount = mUploadEbPaymentEditTextPaymentAmount.getText().toString();


            ebBillUploadReceipt = new EBBillUploadReceipt(userId, accessToken, request_id, paymentMode, ebPaymentReceiptNumber, ebPaymentDate, ebPaymentAmount, base64String);

            Gson gson2 = new GsonBuilder().create();
            String jsonString = gson2.toJson(ebBillUploadReceipt);

            //offlineStorageWrapper.saveObjectToFile(ticketName + ".txt", jsonString);

            GsonRequest<EBlSubmitResposeData> eBlSubmitResposeDataGsonRequest = new GsonRequest<>(Request.Method.POST, Constants.SubmitEbfillingPaymentEeceipt, jsonString, EBlSubmitResposeData.class,
                    new Response.Listener<EBlSubmitResposeData>() {
                        @Override
                        public void onResponse(EBlSubmitResposeData response) {
                            hideBusyProgress();
                            if (response.getError() != null) {
                                showToast(response.getError().getErrorMessage());
                            } else {
                                if (response.getSuccess() == 1) {

                                    setResult(RESULT_OK);
                                    showToast("Receipt Uploaded successfully.");
                                    finish();
                                } else {

                                    showToast("Something went wrong");
                                }
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            hideBusyProgress();
                            Log.e("D100", error.toString());
                        }
                    });
            eBlSubmitResposeDataGsonRequest.setRetryPolicy(Application.getDefaultRetryPolice());
            eBlSubmitResposeDataGsonRequest.setShouldCache(false);
            Application.getInstance().addToRequestQueue(eBlSubmitResposeDataGsonRequest, "eBlSubmitResposeDataGsonRequest");


        } catch (Exception e)

        {
            e.printStackTrace();
        }

    }


    private void assignViews() {
        mUploadEbPaymentEditTextTicketNumber = (TextView) findViewById(R.id.uploadEbPayment_textView_ticketNumber);
        mUploadEbPaymentEditTextSiteId = (TextView) findViewById(R.id.uploadEbPayment_textView_siteId);
        mUploadEbPaymentEditTextSiteName = (TextView) findViewById(R.id.uploadEbPayment_textView_siteName);
        mUploadEbPaymentTextViewPaymentTypeVal = (TextView) findViewById(R.id.uploadEbPayment_textView_paymentType_val);
        mUploadEbPaymentButtonUploadPhoto = (ImageView) findViewById(R.id.uploadEbPayment_button_uploadPhoto);
        mUploadEbPaymentButtonUploadPhotoView = (ImageView) findViewById(R.id.uploadEbPayment_button_uploadPhotoView);

        mUploadEbPaymentTextViewPaymentReceiptNumber = (TextView) findViewById(R.id.uploadEbPayment_textView_paymentReceiptNumber);
        mUploadEbPaymentEditTextPaymentReceiptNumber = (EditText) findViewById(R.id.uploadEbPayment_editText_paymentReceiptNumber);
        mUploadEbPaymentTextViewReceiptPaymentDate = (TextView) findViewById(R.id.uploadEbPayment_textView_receiptPaymentDate);
        mUploadEbPaymentEditTextReceiptPaymentDate = (EditText) findViewById(R.id.uploadEbPayment_editText_receiptPaymentDate);
        mUploadEbPaymentTextViewPaymentAmount = (TextView) findViewById(R.id.uploadEbPayment_textView_paymentAmount);
        mUploadEbPaymentEditTextPaymentAmount = (EditText) findViewById(R.id.uploadEbPayment_editText_paymentAmount);

        mUploadEbPaymentLinearLayout_paymentReceiptNumber = (LinearLayout) findViewById(R.id.uploadEbPayment_linearLayout_paymentReceiptNumber);
        mUploadEbPaymentLinearLayout_receiptPaymentDate = (LinearLayout) findViewById(R.id.uploadEbPayment_linearLayout_receiptPaymentDate);
        mUploadEbPaymentLinearLayout_paymentAmount = (LinearLayout) findViewById(R.id.uploadEbPayment_linearLayout_paymentAmount);
        mUploadEbPaymentLinearLayout_uploadPhoto = (LinearLayout) findViewById(R.id.uploadEbPayment_linearLayout_uploadPhoto);

        mUploadEbPaymentEditTextPaymentAmount.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(15, 2)});
    }

    private void updateLabelPaymentDate() {
        String myFormat = "dd/MMM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mUploadEbPaymentEditTextReceiptPaymentDate.setText(sdf.format(myCalendar1.getTime()));


    }

    private void setListners() {
        mUploadEbPaymentEditTextReceiptPaymentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(UploadEBPaymentDetailsActivity.this, dateBillfrom, myCalendar1
                        .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                        myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        mUploadEbPaymentButtonUploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });
    }

    private void takePhoto() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            imageFileName = "IMG_" + ticketName + "_" + sdf.format(new Date()) + "_site.jpg";

            File file = new File(offlineStorageWrapper.getOfflineStorageFolderPath(TAG), imageFileName);
            imageFileNameUri = FileProvider.getUriForFile(UploadEBPaymentDetailsActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileNameUri);
            startActivityForResult(pictureIntent, MY_PERMISSIONS_REQUEST_CAMERA);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void visibilityOfLayout() {
        mUploadEbPaymentLinearLayout_paymentReceiptNumber.setVisibility(View.VISIBLE);
        mUploadEbPaymentLinearLayout_receiptPaymentDate.setVisibility(View.VISIBLE);
        mUploadEbPaymentLinearLayout_paymentAmount.setVisibility(View.VISIBLE);
        mUploadEbPaymentLinearLayout_uploadPhoto.setVisibility(View.VISIBLE);

        mUploadEbPaymentEditTextPaymentReceiptNumber.setText("");
        mUploadEbPaymentEditTextReceiptPaymentDate.setText("");
        mUploadEbPaymentEditTextPaymentAmount.setText("");
        imageFileName = "";
        imageFileNameUri = null;
        mUploadEbPaymentButtonUploadPhotoView.setVisibility(View.GONE);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA:
                if (resultCode == RESULT_OK) {
                    if (imageFileNameUri != null) {
                        try {
                            Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageFileNameUri);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                            byte[] bitmapDataArray = stream.toByteArray();
                            base64String = Base64.encodeToString(bitmapDataArray, Base64.DEFAULT);
                            mUploadEbPaymentButtonUploadPhotoView.setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    imageFileName = "";
                    imageFileNameUri = null;
                    mUploadEbPaymentButtonUploadPhotoView.setVisibility(View.GONE);
                }
                break;
        }
    }
}
