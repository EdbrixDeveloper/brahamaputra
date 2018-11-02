package com.brahamaputra.mahindra.brahamaputra.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;


import android.view.View;

import android.widget.DatePicker;
import android.widget.Toast;

import com.brahamaputra.mahindra.brahamaputra.commons.OfflineStorageWrapper;
import com.brahamaputra.mahindra.brahamaputra.Data.HotoTransactionData;
import com.brahamaputra.mahindra.brahamaputra.Data.LandDetailsData;
import com.brahamaputra.mahindra.brahamaputra.R;
import com.brahamaputra.mahindra.brahamaputra.commons.AlertDialogManager;
import com.brahamaputra.mahindra.brahamaputra.helper.OnSpinnerItemClick;
import com.brahamaputra.mahindra.brahamaputra.helper.SearchableSpinnerDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

public class Land_Details extends AppCompatActivity {

    private static final String TAG = Land_Details.class.getSimpleName();

    private TextView mLandDetailsTextViewTypeOfLand;
    private TextView mLandDetailsTextViewTypeOfLandVal;
    private TextView mLandDetailsTextViewAreaOfLand;
    private EditText mLandDetailsEditTextAreaOfLand;
    private TextView mLandDetailsTextViewRentLeaseInNumber;
    private EditText mLandDetailsEditTextRentLeaseInNumber;
    private TextView mLandDetailsTextViewRentLeaseInWords;
    private EditText mLandDetailsEditTextRentLeaseInWords;
    private TextView mLandDetailsTextViewNameOfOwner;
    private EditText mLandDetailsEditTextNameOfOwner;
    private TextView mLandDetailsTextViewMobileNoOfOwner;
    private EditText mLandDetailsEditTextMobileNoOfOwner;
    private TextView mLandDetailsTextViewLayoutOfLand;
    private ImageView mLandDetailsButtonLayoutOfLand;
    private TextView mLandDetailsTextViewCopyAgreementWithOwner;
    private TextView mLandDetailsTextViewCopyAgreementWithOwnerVal;
    private TextView mLandDetailsTextViewValidityOfAgreement;
    private EditText mLandDetailsEditTextDateOfvalidityOfAgreement;
    private TextView mLandDetailsTextViewValidityOfLand;
    private EditText mLandDetailsEditTextDateOfvalidityOfLand;
    private OfflineStorageWrapper offlineStorageWrapper;
    private String userId = "101";
    private String ticketId = "";
    private String ticketName = "28131";
    private HotoTransactionData hotoTransactionData;
    private LandDetailsData landDetailsData;
    private String base64StringLayoutOfLand = "eji39jjj";

    final Calendar myCalendar = Calendar.getInstance();

    /////////////////////////
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public static final String ALLOW_KEY = "ALLOWED";
    public static final String CAMERA_PREF = "camera_pref";
    public String date_flag = "no";

    private AlertDialogManager alertDialogManager;

    String str_landDetailsTypeOfLandVal;
    String str_copyAgreementWithOwnerVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_details);
        this.setTitle("Land Detail");
        offlineStorageWrapper = OfflineStorageWrapper.getInstance(Land_Details.this, userId, ticketName);
        assignViews();
        initCombo();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        hotoTransactionData = new HotoTransactionData();
//        setInputDetails();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };


        mLandDetailsEditTextDateOfvalidityOfAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Land_Details.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        mLandDetailsButtonLayoutOfLand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(Land_Details.this,
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {

                    if (getFromPref(Land_Details.this, ALLOW_KEY)) {

                        showSettingsAlert();

                    } else if (ContextCompat.checkSelfPermission(Land_Details.this,
                            Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(Land_Details.this,
                                Manifest.permission.CAMERA)) {
                            showAlert();
                        } else {
                            // No explanation needed, we can request the permission.
                            ActivityCompat.requestPermissions(Land_Details.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    MY_PERMISSIONS_REQUEST_CAMERA);
                        }
                    }
                } else {
                    openCamera();
                }

            }
        });


    }

    private void assignViews() {
        mLandDetailsTextViewTypeOfLand = (TextView) findViewById(R.id.landDetails_textView_typeOfLand);
        //mLandDetailsSpinnerTypeOfLand = (SearchableSpinner) findViewById(R.id.landDetails_Spinner_typeOfLand);
        mLandDetailsTextViewTypeOfLandVal = (TextView) findViewById(R.id.landDetails_textView_typeOfLand_val);
        mLandDetailsTextViewAreaOfLand = (TextView) findViewById(R.id.landDetails_textView_areaOfLand);
        mLandDetailsEditTextAreaOfLand = (EditText) findViewById(R.id.landDetails_editText_areaOfLand);
        mLandDetailsTextViewRentLeaseInNumber = (TextView) findViewById(R.id.landDetails_textView_rentLeaseInNumber);
        mLandDetailsEditTextRentLeaseInNumber = (EditText) findViewById(R.id.landDetails_editText_rentLeaseInNumber);
        mLandDetailsTextViewRentLeaseInWords = (TextView) findViewById(R.id.landDetails_textView_rentLeaseInWords);
        mLandDetailsEditTextRentLeaseInWords = (EditText) findViewById(R.id.landDetails_editText_rentLeaseInWords);
        mLandDetailsTextViewNameOfOwner = (TextView) findViewById(R.id.landDetails_textView_nameOfOwner);
        mLandDetailsEditTextNameOfOwner = (EditText) findViewById(R.id.landDetails_editText_nameOfOwner);
        mLandDetailsTextViewMobileNoOfOwner = (TextView) findViewById(R.id.landDetails_textView_mobileNoOfOwner);
        mLandDetailsEditTextMobileNoOfOwner = (EditText) findViewById(R.id.landDetails_editText_mobileNoOfOwner);
        mLandDetailsTextViewLayoutOfLand = (TextView) findViewById(R.id.landDetails_textView_layoutOfLand);
        mLandDetailsButtonLayoutOfLand = (ImageView) findViewById(R.id.landDetails_button_layoutOfLand);
        mLandDetailsTextViewCopyAgreementWithOwner = (TextView) findViewById(R.id.landDetails_textView_copyAgreementWithOwner);
        //mLandDetailsSpinnerCopyAgreementWithOwner = (SearchableSpinner) findViewById(R.id.landDetails_Spinner_copyAgreementWithOwner);
        mLandDetailsTextViewCopyAgreementWithOwnerVal = (TextView) findViewById(R.id.landDetails_textView_copyAgreementWithOwner_val);
        mLandDetailsTextViewValidityOfAgreement = (TextView) findViewById(R.id.landDetails_textView_validityOfAgreement);
        mLandDetailsEditTextDateOfvalidityOfAgreement = (EditText) findViewById(R.id.landDetails_editText_dateOfvalidityOfAgreement);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }

    private void initCombo() {
        mLandDetailsTextViewTypeOfLandVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Land_Details.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_landDetails_LandType))),
                        "Type of Land",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_landDetailsTypeOfLandVal = item.get(position);
                        mLandDetailsTextViewTypeOfLandVal.setText(str_landDetailsTypeOfLandVal);
                    }
                });
            }
        });


        mLandDetailsTextViewCopyAgreementWithOwnerVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Land_Details.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_landDetails_copyAgreementWithOwner))),
                        "Copy of the agreement with the landlord/owner",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_copyAgreementWithOwnerVal = item.get(position);
                        mLandDetailsTextViewCopyAgreementWithOwnerVal.setText(str_copyAgreementWithOwnerVal);
                    }
                });
            }
        });
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mLandDetailsEditTextDateOfvalidityOfAgreement.setText(sdf.format(myCalendar.getTime()));
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
//                submitDetails();
                startActivity(new Intent(this, Tower_Detail.class));
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setInputDetails() {
        try {
            if (offlineStorageWrapper.checkOfflineFileIsAvailable(ticketName + ".txt")) {
                String jsonInString = (String) offlineStorageWrapper.getObjectFromFile(ticketName + ".txt");
               // Toast.makeText(Land_Details.this,"JsonInString :"+ jsonInString,Toast.LENGTH_SHORT).show();

                Gson gson = new Gson();
//                landDetailsData = gson.fromJson(jsonInString, LandDetailsData.class);

                hotoTransactionData = gson.fromJson(jsonInString, HotoTransactionData.class);

                if(hotoTransactionData!=null) {

                    landDetailsData = hotoTransactionData.getLandDetailsData();

                    if (landDetailsData != null) {

//                mLandDetailsSpinnerTypeOfLand.setSelection(1);
                        mLandDetailsEditTextAreaOfLand.setText(landDetailsData.getLandArea());
                        mLandDetailsEditTextRentLeaseInNumber.setText(landDetailsData.getRentLeaseValue());
                        mLandDetailsEditTextRentLeaseInWords.setText(landDetailsData.getRentLeaseValueInWords());
                        mLandDetailsEditTextNameOfOwner.setText(landDetailsData.getLandOwnerName());
                        mLandDetailsEditTextMobileNoOfOwner.setText(landDetailsData.getLandOwnerMob());
                        base64StringLayoutOfLand = landDetailsData.getLandLayout();
//                mLandDetailsSpinnerCopyAgreementWithOwner.setSelection(1);
                        mLandDetailsEditTextDateOfvalidityOfLand.setText(landDetailsData.getLandAgreementValidity());
                    }
                }
            } else {
                Toast.makeText(Land_Details.this, "No previous saved data available", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void submitDetails() {
        try {
            hotoTransactionData.setTicketNo(ticketName);
            String landType = "0";
            String landArea = mLandDetailsEditTextAreaOfLand.getText().toString().trim();
            String rentLeaseValue = mLandDetailsEditTextRentLeaseInNumber.getText().toString().trim();
            String rentLeaseValueInWords = mLandDetailsEditTextRentLeaseInWords.getText().toString().trim();
            String landOwnerName = mLandDetailsEditTextNameOfOwner.getText().toString().trim();
            String landOwnerMob = mLandDetailsEditTextMobileNoOfOwner.getText().toString().trim();
            String landLayout = base64StringLayoutOfLand;
            String landAgreementCopy = "0";
            String landAgreementValidity = mLandDetailsEditTextDateOfvalidityOfLand.getText().toString();

            landDetailsData = new LandDetailsData(landType, landArea, rentLeaseValue, rentLeaseValueInWords, landOwnerName, landOwnerMob, landLayout, landAgreementCopy, landAgreementValidity);

            hotoTransactionData.setLandDetailsData(landDetailsData);

            Gson gson2 = new GsonBuilder().create();
            String jsonString = gson2.toJson(hotoTransactionData);
            //Toast.makeText(Land_Details.this, "Gson to json string :" + jsonString, Toast.LENGTH_SHORT).show();

            offlineStorageWrapper.saveObjectToFile(ticketName + ".txt", jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }

       /*
       JSONObject testJsonObj = new JSONObject(jsonInString);
                Toast.makeText(Land_Details.this,"Json object string :"+ testJsonObj.toString(),Toast.LENGTH_SHORT).show();

        JSONObject jo = new JSONObject();
        try {
            jo.put("landType", landType);
            jo.put("landArea", landArea);
            jo.put("rentLeaseValue", rentLeaseValue);
            jo.put("rentLeaseValueInWords", rentLeaseValueInWords);
            jo.put("landOwnerName",landOwnerName);
            jo.put("landOwnerMob",landOwnerMob);
            jo.put("landLayout",landLayout);
            jo.put("landAgreementCopy",landAgreementCopy);
            jo.put("landAgreementCopy",landAgreementCopy);
            jo.put("landAgreementValidity",landAgreementValidity);

            offlineStorageWrapper.saveObjectToFile(TAG+".txt",jo.toString());

            Toast.makeText(Land_Details.this,"Saved offline successfully..",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }*/


//

    }


    //Camera//

    public static Boolean getFromPref(Context context, String key) {
        SharedPreferences myPrefs = context.getSharedPreferences
                (CAMERA_PREF, Context.MODE_PRIVATE);
        return (myPrefs.getBoolean(key, false));
    }

    private void showSettingsAlert() {

        alertDialogManager.Dialog("Permission", "App needs to access the Camera.", "ok", "cancel", new AlertDialogManager.onSingleButtonClickListner() {
            @Override
            public void onPositiveClick() {

                final EditText taskEditText = new EditText(Land_Details.this);
                android.support.v7.app.AlertDialog dialog = new android.support.v7.app.AlertDialog.Builder(Land_Details.this)
                        .setTitle("Permission")
                        .setMessage("Need Camera Access")
                        .setView(taskEditText)
                        .setPositiveButton("SETTINGS", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                startInstalledAppDetailsActivity(Land_Details.this);
                            }
                        })
                        .setNegativeButton("DONT ALLOW", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create();
                dialog.show();
            }
        }).show();

    }

    public static void startInstalledAppDetailsActivity(final Activity context) {
        if (context == null) {
            return;
        }
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(i);
    }

    private void showAlert() {
        alertDialogManager.Dialog("Permission", "App needs to access the Camera.", "ok", "cancel", new AlertDialogManager.onSingleButtonClickListner() {
            @Override
            public void onPositiveClick() {

                final EditText taskEditText = new EditText(Land_Details.this);
                android.support.v7.app.AlertDialog dialog = new android.support.v7.app.AlertDialog.Builder(Land_Details.this)
                        .setTitle("Permission")
                        .setMessage("Need Camera Access")
                        .setView(taskEditText)
                        .setPositiveButton("ALLOW", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                ActivityCompat.requestPermissions(Land_Details.this,
                                        new String[]{Manifest.permission.CAMERA},
                                        MY_PERMISSIONS_REQUEST_CAMERA);
                            }
                        })
                        .setNegativeButton("DONT ALLOW", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        })
                        .create();
                dialog.show();
            }
        }).show();


    }

    private void openCamera() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult
            (int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                for (int i = 0, len = permissions.length; i < len; i++) {
                    String permission = permissions[i];
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        boolean showRationale =
                                ActivityCompat.shouldShowRequestPermissionRationale
                                        (this, permission);
                        if (showRationale) {
                            showAlert();
                        } else if (!showRationale) {
                            // user denied flagging NEVER ASK AGAIN
                            // you can either enable some fall back,
                            // disable features of your app
                            // or open another dialog explaining
                            // again the permission and directing to
                            // the app setting
                            saveToPreferences(Land_Details.this, ALLOW_KEY, true);
                        }
                    }
                }
            }

            // other 'case' lines to check for other
            // permissions this app might request

        }
    }

    public static void saveToPreferences(Context context, String key,
                                         Boolean allowed) {
        SharedPreferences myPrefs = context.getSharedPreferences
                (CAMERA_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.putBoolean(key, allowed);
        prefsEditor.commit();
    }


}

