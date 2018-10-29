package com.brahamaputra.mahindra.brahamaputra.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
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
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.brahamaputra.mahindra.brahamaputra.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Air_Conditioners extends AppCompatActivity {

    private TextView mAirConditionersTextViewNoOfAirConditionersACprovided;
    private Spinner mAirConditionersSpinnerNoOfAirConditionersACprovided;
    private TextView mAirConditionersTextViewNumberOfACInWorkingCondition;
    private Spinner mAirConditionersSpinnerNumberOfACInWorkingCondition;
    private TextView mAirConditionersTextViewQRCodeScan;
    private ImageView mAirConditionersButtonQRCodeScan;
    private TextView mAirConditionersTextViewAssetOwner;
    private Spinner mAirConditionersSpinnerAssetOwner;
    private TextView mAirConditionersTextViewTypeOfAcSpliWindow;
    private Spinner mAirConditionersSpinnerTypeOfAcSpliWindow;
    private TextView mAirConditionersTextViewManufacturerMakeModel;
    private EditText mAirConditionersEditTextManufacturerMakeModel;
    private TextView mAirConditionersTextViewAcSerialNumber;
    private EditText mAirConditionersEditTextAcSerialNumber;
    private TextView mAirConditionersTextViewCapacityTr;
    private EditText mAirConditionersEditTextCapacityTr;
    private TextView mAirConditionersTextViewDateOfInstallation;
    private EditText mAirConditionersEditTextDateOfInstallation;
    private TextView mAirConditionersTextViewAmcYesNo;
    private Spinner mAirConditionersSpinnerAmcYesNo;
    private TextView mAirConditionersTextViewAlidityOfAmc;
    private EditText mAirConditionersEditTextDateOfvalidityOfAmc;
    private TextView mAirConditionersTextViewWorkingCondition;
    private Spinner mAirConditionersSpinnerWorkingCondition;
    private TextView mAirConditionersTextViewNatureOfProblem;
    private EditText mAirConditionersEditTextNatureOfProblem;

    final Calendar myCalendar = Calendar.getInstance();

    /////////////////////////
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public static final String ALLOW_KEY = "ALLOWED";
    public static final String CAMERA_PREF = "camera_pref";
    public String date_flag = "no";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air_conditioners);

        this.setTitle("Air Conditioners");
        assignViews();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        mAirConditionersButtonQRCodeScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(Air_Conditioners.this,
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {

                    if (getFromPref(Air_Conditioners.this, ALLOW_KEY)) {

                        showSettingsAlert();

                    } else if (ContextCompat.checkSelfPermission(Air_Conditioners.this,
                            Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(Air_Conditioners.this,
                                Manifest.permission.CAMERA)) {
                            showAlert();
                        } else {
                            // No explanation needed, we can request the permission.
                            ActivityCompat.requestPermissions(Air_Conditioners.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    MY_PERMISSIONS_REQUEST_CAMERA);
                        }
                    }
                } else {
                    openCamera();
                }

            }
        });


        mAirConditionersEditTextDateOfInstallation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date_flag = "install";
                new DatePickerDialog(Air_Conditioners.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        mAirConditionersEditTextDateOfvalidityOfAmc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date_flag = "valid";
                new DatePickerDialog(Air_Conditioners.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


    }


    private void assignViews() {
        mAirConditionersTextViewNoOfAirConditionersACprovided = (TextView) findViewById(R.id.airConditioners_textView_noOfAirConditionersACprovided);
        mAirConditionersSpinnerNoOfAirConditionersACprovided = (Spinner) findViewById(R.id.airConditioners_spinner_noOfAirConditionersACprovided);
        mAirConditionersTextViewNumberOfACInWorkingCondition = (TextView) findViewById(R.id.airConditioners_textView_numberOfACInWorkingCondition);
        mAirConditionersSpinnerNumberOfACInWorkingCondition = (Spinner) findViewById(R.id.airConditioners_spinner_numberOfACInWorkingCondition);
        mAirConditionersTextViewQRCodeScan = (TextView) findViewById(R.id.airConditioners_textView_QRCodeScan);
        mAirConditionersButtonQRCodeScan = (ImageView) findViewById(R.id.airConditioners_button_QRCodeScan);
        mAirConditionersTextViewAssetOwner = (TextView) findViewById(R.id.airConditioners_textView_assetOwner);
        mAirConditionersSpinnerAssetOwner = (Spinner) findViewById(R.id.airConditioners_Spinner_assetOwner);
        mAirConditionersTextViewTypeOfAcSpliWindow = (TextView) findViewById(R.id.airConditioners_textView_typeOfAcSpliWindow);
        mAirConditionersSpinnerTypeOfAcSpliWindow = (Spinner) findViewById(R.id.airConditioners_Spinner_typeOfAcSpliWindow);
        mAirConditionersTextViewManufacturerMakeModel = (TextView) findViewById(R.id.airConditioners_textView_manufacturerMakeModel);
        mAirConditionersEditTextManufacturerMakeModel = (EditText) findViewById(R.id.airConditioners_editText_manufacturerMakeModel);
        mAirConditionersTextViewAcSerialNumber = (TextView) findViewById(R.id.airConditioners_textView_acSerialNumber);
        mAirConditionersEditTextAcSerialNumber = (EditText) findViewById(R.id.airConditioners_editText_acSerialNumber);
        mAirConditionersTextViewCapacityTr = (TextView) findViewById(R.id.airConditioners_textView_capacityTr);
        mAirConditionersEditTextCapacityTr = (EditText) findViewById(R.id.airConditioners_editText_capacityTr);
        mAirConditionersTextViewDateOfInstallation = (TextView) findViewById(R.id.airConditioners_textView_dateOfInstallation);
        mAirConditionersEditTextDateOfInstallation = (EditText) findViewById(R.id.airConditioners_editText_dateOfInstallation);
        mAirConditionersTextViewAmcYesNo = (TextView) findViewById(R.id.airConditioners_textView_amcYesNo);
        mAirConditionersSpinnerAmcYesNo = (Spinner) findViewById(R.id.airConditioners_Spinner_amcYesNo);
        mAirConditionersTextViewAlidityOfAmc = (TextView) findViewById(R.id.airConditioners_textView_alidityOfAmc);
        mAirConditionersEditTextDateOfvalidityOfAmc = (EditText) findViewById(R.id.airConditioners_editText_dateOfvalidityOfAmc);
        mAirConditionersTextViewWorkingCondition = (TextView) findViewById(R.id.airConditioners_textView_workingCondition);
        mAirConditionersSpinnerWorkingCondition = (Spinner) findViewById(R.id.airConditioners_Spinner_workingCondition);
        mAirConditionersTextViewNatureOfProblem = (TextView) findViewById(R.id.airConditioners_textView_natureOfProblem);
        mAirConditionersEditTextNatureOfProblem = (EditText) findViewById(R.id.airConditioners_editText_natureOfProblem);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        if (date_flag.equals("install")) {
            mAirConditionersEditTextDateOfInstallation.setText(sdf.format(myCalendar.getTime()));
        } else if (date_flag.equals("valid")) {
            mAirConditionersEditTextDateOfvalidityOfAmc.setText(sdf.format(myCalendar.getTime()));
        } else {

        }
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
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    //////////////////////
    //Camera//

    public static Boolean getFromPref(Context context, String key) {
        SharedPreferences myPrefs = context.getSharedPreferences
                (CAMERA_PREF, Context.MODE_PRIVATE);
        return (myPrefs.getBoolean(key, false));
    }

    private void showSettingsAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs to access the Camera.");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DONT ALLOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //finish();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "SETTINGS",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startInstalledAppDetailsActivity(Air_Conditioners.this);

                    }
                });
        alertDialog.show();
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
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs to access the Camera.");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DONT ALLOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "ALLOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ActivityCompat.requestPermissions(Air_Conditioners.this,
                                new String[]{Manifest.permission.CAMERA},
                                MY_PERMISSIONS_REQUEST_CAMERA);

                    }
                });
        alertDialog.show();
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
                            saveToPreferences(Air_Conditioners.this, ALLOW_KEY, true);
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
