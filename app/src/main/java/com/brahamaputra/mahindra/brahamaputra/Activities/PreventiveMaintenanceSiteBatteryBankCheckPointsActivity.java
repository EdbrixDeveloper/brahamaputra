package com.brahamaputra.mahindra.brahamaputra.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.brahamaputra.mahindra.brahamaputra.R;
import com.brahamaputra.mahindra.brahamaputra.helper.OnSpinnerItemClick;
import com.brahamaputra.mahindra.brahamaputra.helper.SearchableSpinnerDialog;

import java.util.ArrayList;
import java.util.Arrays;

public class PreventiveMaintenanceSiteBatteryBankCheckPointsActivity extends AppCompatActivity {
    private TextView mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewNoOfBatteryBankAvailableAtSite;
    private TextView mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewNoOfBatteryBankAvailableAtSiteVal;
    private LinearLayout mLinearLayoutContainer;
    private TextView mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBattreyBankNumber;
    private TextView mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewDetailsOfBatteryBankQRCodeScan;
    private ImageView mPreventiveMaintenanceSiteBatteryBankCheckPointsButtonDetailsOfBatteryBankQRCodeScan;
    private ImageView mPreventiveMaintenanceSiteBatteryBankCheckPointsButtonDetailsOfBatteryBankQRCodeScanView;
    private ImageView mButtonClearDetailsOfBatteryBankQRCodeScanView;
    private TextView mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBatteryBankDischargeTest;
    private TextView mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBatteryBankDischargeTestVal;
    private TextView mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewStripBoltTightnessAsPerTorque;
    private TextView mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewStripBoltTightnessAsPerTorqueVal;
    private TextView mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewPetroleumJellyApplied;
    private TextView mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewPetroleumJellyAppliedVal;
    private TextView mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBatteryVentPlugStatus;
    private TextView mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBatteryVentPlugStatusVal;
    private TextView mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBbEarthingStatus;
    private TextView mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBbEarthingStatusVal;
    private TextView mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewRegisterFault;
    private TextView mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewRegisterFaultVal;
    private TextView mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewTypeOfFault;
    private TextView mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewTypeOfFaultVal;
    private Button mPreventiveMaintenanceSiteBatteryBankCheckPointsButtonPreviousReading;
    private Button mPreventiveMaintenanceSiteBatteryBankCheckPointsButtonNextReading;

    String str_noOfBatteryBankAvailableAtSiteVal = "";
    String str_pmSiteBbcpBatteryBankDischargeTestVal = "";
    String str_pmSiteBbcpStripBoltTightnessAsPerTorque = "";
    String str_pmSiteBbcpPetroleumJellyAppliedVal = "";
    String str_pmSiteBbcpBatteryVentPlugStatusVal = "";
    String str_pmSiteBbcpBBEarthingStatusVal = "";
    String str_pmSiteBbcpRegisterFaultVal = "";
    String str_pmSiteBbcpTypeOfFaultVal = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preventive_maintenance_site_battery_bank_check_points);
        this.setTitle("Battery Bank Check Points");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        assignViews();
        initCombo();
    }

    private void assignViews() {
        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewNoOfBatteryBankAvailableAtSite = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_textView_noOfBatteryBankAvailableAtSite);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewNoOfBatteryBankAvailableAtSiteVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_textView_noOfBatteryBankAvailableAtSiteVal);
        mLinearLayoutContainer = (LinearLayout) findViewById(R.id.linearLayout_container);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBattreyBankNumber = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_textView_battreyBankNumber);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewDetailsOfBatteryBankQRCodeScan = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_textView_detailsOfBatteryBankQRCodeScan);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsButtonDetailsOfBatteryBankQRCodeScan = (ImageView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_button_detailsOfBatteryBankQRCodeScan);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsButtonDetailsOfBatteryBankQRCodeScanView = (ImageView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_button_detailsOfBatteryBankQRCodeScanView);
        mButtonClearDetailsOfBatteryBankQRCodeScanView = (ImageView) findViewById(R.id.button_ClearDetailsOfBatteryBankQRCodeScanView);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBatteryBankDischargeTest = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_textView_batteryBankDischargeTest);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBatteryBankDischargeTestVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_textView_batteryBankDischargeTestVal);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewStripBoltTightnessAsPerTorque = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_textView_stripBoltTightnessAsPerTorque);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewStripBoltTightnessAsPerTorqueVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_textView_stripBoltTightnessAsPerTorqueVal);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewPetroleumJellyApplied = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_textView_petroleumJellyApplied);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewPetroleumJellyAppliedVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_textView_petroleumJellyAppliedVal);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBatteryVentPlugStatus = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_textView_batteryVentPlugStatus);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBatteryVentPlugStatusVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_textView_batteryVentPlugStatusVal);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBbEarthingStatus = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_textView_bbEarthingStatus);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBbEarthingStatusVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_textView_bbEarthingStatusVal);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewRegisterFault = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_textView_registerFault);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewRegisterFaultVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_textView_registerFaultVal);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewTypeOfFault = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_textView_typeOfFault);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewTypeOfFaultVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_textView_typeOfFaultVal);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsButtonPreviousReading = (Button) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_button_previousReading);
        mPreventiveMaintenanceSiteBatteryBankCheckPointsButtonNextReading = (Button) findViewById(R.id.preventiveMaintenanceSiteBatteryBankCheckPoints_button_nextReading);
    }


    private void initCombo() {
        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewNoOfBatteryBankAvailableAtSiteVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteBatteryBankCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteBatteryBankCheckPoints_noOfBatteryBank))),
                        "No of Battery Bank available at Site",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_noOfBatteryBankAvailableAtSiteVal = item.get(position);
                        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewNoOfBatteryBankAvailableAtSiteVal.setText(str_noOfBatteryBankAvailableAtSiteVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBatteryBankDischargeTestVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteBatteryBankCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteBatteryBankCheckPoints_batteryBankDischargeTest))),
                        "Battery Bank Discharge Test",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteBbcpBatteryBankDischargeTestVal = item.get(position);
                        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBatteryBankDischargeTestVal.setText(str_pmSiteBbcpBatteryBankDischargeTestVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewStripBoltTightnessAsPerTorqueVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteBatteryBankCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteBatteryBankCheckPoints_stripTightnessAsPerTorque))),
                        "Strip Bolt tightness as per torquet",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteBbcpStripBoltTightnessAsPerTorque = item.get(position);
                        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewStripBoltTightnessAsPerTorqueVal.setText(str_pmSiteBbcpStripBoltTightnessAsPerTorque);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewPetroleumJellyAppliedVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteBatteryBankCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteBatteryBankCheckPoints_petroleumJellyApplied))),
                        "Petroleum Jelly Applied",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteBbcpPetroleumJellyAppliedVal = item.get(position);
                        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewPetroleumJellyAppliedVal.setText(str_pmSiteBbcpPetroleumJellyAppliedVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBatteryVentPlugStatusVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteBatteryBankCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteBatteryBankCheckPoints_batteryVentPlugStatus))),
                        "Battery Vent plug Status",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteBbcpBatteryVentPlugStatusVal = item.get(position);
                        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBatteryVentPlugStatusVal.setText(str_pmSiteBbcpBatteryVentPlugStatusVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBbEarthingStatusVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteBatteryBankCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteBatteryBankCheckPoints_bbEarthingStatus))),
                        "BB Earthing Status",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteBbcpBBEarthingStatusVal = item.get(position);
                        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewBbEarthingStatusVal.setText(str_pmSiteBbcpBBEarthingStatusVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewRegisterFaultVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteBatteryBankCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteBatteryBankCheckPoints_registerFault))),
                        "Register Fault",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteBbcpRegisterFaultVal = item.get(position);
                        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewRegisterFaultVal.setText(str_pmSiteBbcpRegisterFaultVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewTypeOfFaultVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteBatteryBankCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteBatteryBankCheckPoints_typeOfFault))),
                        "Type of Fault",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmSiteBbcpTypeOfFaultVal = item.get(position);
                        mPreventiveMaintenanceSiteBatteryBankCheckPointsTextViewTypeOfFaultVal.setText(str_pmSiteBbcpTypeOfFaultVal);
                    }
                });
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
                onBackPressed();
                return true;

            case R.id.menuSubmit:
                //submitDetails();
                startActivity(new Intent(this, PreventiveMaintenanceSiteEarthingCheckPointsActivity.class));
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }
}
