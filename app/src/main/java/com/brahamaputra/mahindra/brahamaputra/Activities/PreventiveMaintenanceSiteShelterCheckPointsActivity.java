package com.brahamaputra.mahindra.brahamaputra.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.brahamaputra.mahindra.brahamaputra.Data.PreventiveMaintanceSiteTransactionDetails;
import com.brahamaputra.mahindra.brahamaputra.Data.ShelterCheckPoints;
import com.brahamaputra.mahindra.brahamaputra.R;
import com.brahamaputra.mahindra.brahamaputra.Utils.SessionManager;
import com.brahamaputra.mahindra.brahamaputra.baseclass.BaseActivity;
import com.brahamaputra.mahindra.brahamaputra.commons.GlobalMethods;
import com.brahamaputra.mahindra.brahamaputra.commons.OfflineStorageWrapper;
import com.brahamaputra.mahindra.brahamaputra.helper.OnSpinnerItemClick;
import com.brahamaputra.mahindra.brahamaputra.helper.SearchableSpinnerDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;

public class PreventiveMaintenanceSiteShelterCheckPointsActivity extends BaseActivity {
    private TextView mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterCleaning;
    private TextView mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterCleaningVal;
    private TextView mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterLeakage;
    private TextView mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterLeakageVal;
    private TextView mPreventiveMaintenanceSiteShelterCheckPointsTextViewHatchPlateEntrySealed;
    private TextView mPreventiveMaintenanceSiteShelterCheckPointsTextViewHatchPlateEntrySealedVal;
    private TextView mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterFloorStatus;
    private TextView mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterFloorStatusVal;
    private TextView mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterEarthingStatus;
    private TextView mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterEarthingStatusVal;
    private TextView mPreventiveMaintenanceSiteShelterCheckPointsTextViewRegisterFault;
    private TextView mPreventiveMaintenanceSiteShelterCheckPointsTextViewRegisterFaultVal;
    private TextView mPreventiveMaintenanceSiteShelterCheckPointsTextViewTypeOfFault;
    private TextView mPreventiveMaintenanceSiteShelterCheckPointsTextViewTypeOfFaultVal;

    String str_shelterCleaningVal;
    String str_shelterLeakageVal;
    String str_hatchPlateEntrySealedVal;
    String str_shelterFloorStatusVal;
    String str_shelterEarthingStatusVal;
    String str_registerFaultVal;
    String str_typeOfFaultVal;

    private String userId = "";
    private String ticketId = "";
    private String ticketName = "";

    private SessionManager sessionManager;
    private OfflineStorageWrapper offlineStorageWrapper;

    PreventiveMaintanceSiteTransactionDetails preventiveMaintanceSiteTransactionDetails;
    ShelterCheckPoints shelterCheckPoints;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preventive_maintenance_site_shelter_check_points);
        this.setTitle("Shelter Check Points");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        assignViews();
        initCombo();

        sessionManager = new SessionManager(PreventiveMaintenanceSiteShelterCheckPointsActivity.this);
        ticketId = sessionManager.getSessionUserTicketId();
        ticketName = GlobalMethods.replaceAllSpecialCharAtUnderscore(sessionManager.getSessionUserTicketName());
        userId = sessionManager.getSessionUserId();
        offlineStorageWrapper = OfflineStorageWrapper.getInstance(PreventiveMaintenanceSiteShelterCheckPointsActivity.this, userId, ticketName);

        preventiveMaintanceSiteTransactionDetails = new PreventiveMaintanceSiteTransactionDetails();
        setInputDetails();
    }

    private void assignViews() {
        mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterCleaning = (TextView) findViewById(R.id.preventiveMaintenanceSiteShelterCheckPoints_textView_shelterCleaning);
        mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterCleaningVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteShelterCheckPoints_textView_shelterCleaningVal);
        mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterLeakage = (TextView) findViewById(R.id.preventiveMaintenanceSiteShelterCheckPoints_textView_shelterLeakage);
        mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterLeakageVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteShelterCheckPoints_textView_shelterLeakageVal);
        mPreventiveMaintenanceSiteShelterCheckPointsTextViewHatchPlateEntrySealed = (TextView) findViewById(R.id.preventiveMaintenanceSiteShelterCheckPoints_textView_hatchPlateEntrySealed);
        mPreventiveMaintenanceSiteShelterCheckPointsTextViewHatchPlateEntrySealedVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteShelterCheckPoints_textView_hatchPlateEntrySealedVal);
        mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterFloorStatus = (TextView) findViewById(R.id.preventiveMaintenanceSiteShelterCheckPoints_textView_shelterFloorStatus);
        mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterFloorStatusVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteShelterCheckPoints_textView_shelterFloorStatusVal);
        mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterEarthingStatus = (TextView) findViewById(R.id.preventiveMaintenanceSiteShelterCheckPoints_textView_shelterEarthingStatus);
        mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterEarthingStatusVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteShelterCheckPoints_textView_shelterEarthingStatusVal);
        mPreventiveMaintenanceSiteShelterCheckPointsTextViewRegisterFault = (TextView) findViewById(R.id.preventiveMaintenanceSiteShelterCheckPoints_textView_registerFault);
        mPreventiveMaintenanceSiteShelterCheckPointsTextViewRegisterFaultVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteShelterCheckPoints_textView_registerFaultVal);
        mPreventiveMaintenanceSiteShelterCheckPointsTextViewTypeOfFault = (TextView) findViewById(R.id.preventiveMaintenanceSiteShelterCheckPoints_textView_typeOfFault);
        mPreventiveMaintenanceSiteShelterCheckPointsTextViewTypeOfFaultVal = (TextView) findViewById(R.id.preventiveMaintenanceSiteShelterCheckPoints_textView_typeOfFaultVal);


    }

    private void initCombo() {

        mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterCleaningVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteShelterCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteShelterCheckPoints_shelterCleaning))),
                        "Shelter Cleaning",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_shelterCleaningVal = item.get(position);
                        mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterCleaningVal.setText(str_shelterCleaningVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterLeakageVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteShelterCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteShelterCheckPoints_shelterLeakage))),
                        "Shelter Leakage",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_shelterLeakageVal = item.get(position);
                        mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterLeakageVal.setText(str_shelterLeakageVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteShelterCheckPointsTextViewHatchPlateEntrySealedVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteShelterCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteShelterCheckPoints_hatchPlateEntrySealed))),
                        "Hatch Plate Entry sealed",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_hatchPlateEntrySealedVal = item.get(position);
                        mPreventiveMaintenanceSiteShelterCheckPointsTextViewHatchPlateEntrySealedVal.setText(str_hatchPlateEntrySealedVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterFloorStatusVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteShelterCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteShelterCheckPoints_shelterFloorStatus))),
                        "Shleter Floor Status",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_shelterFloorStatusVal = item.get(position);
                        mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterFloorStatusVal.setText(str_shelterFloorStatusVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterEarthingStatusVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteShelterCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteShelterCheckPoints_shelterEarthingStatus))),
                        "Shelter Earthing Status",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_shelterEarthingStatusVal = item.get(position);
                        mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterEarthingStatusVal.setText(str_shelterEarthingStatusVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteShelterCheckPointsTextViewRegisterFaultVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteShelterCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteShelterCheckPoints_registerFault))),
                        "Register Fault",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_registerFaultVal = item.get(position);
                        mPreventiveMaintenanceSiteShelterCheckPointsTextViewRegisterFaultVal.setText(str_registerFaultVal);
                    }
                });
            }
        });

        mPreventiveMaintenanceSiteShelterCheckPointsTextViewTypeOfFaultVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(PreventiveMaintenanceSiteShelterCheckPointsActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteShelterCheckPoints_typeOfFault))),
                        "Type of Fault",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_typeOfFaultVal = item.get(position);
                        mPreventiveMaintenanceSiteShelterCheckPointsTextViewTypeOfFaultVal.setText(str_typeOfFaultVal);
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
                submitDetails();
                startActivity(new Intent(this, PreventiveMaintenanceSiteOtherElectricalCheckPointsActivity.class));
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void setInputDetails() {
        try {
                 if(offlineStorageWrapper.checkOfflineFileIsAvailable(ticketName + ".txt"))
                 {
                     String jsonInString = (String) offlineStorageWrapper.getObjectFromFile(ticketName + ".txt");
                     Gson gson = new Gson();
                     preventiveMaintanceSiteTransactionDetails = gson.fromJson(jsonInString,PreventiveMaintanceSiteTransactionDetails.class);

                     if(preventiveMaintanceSiteTransactionDetails != null)
                     {
                         shelterCheckPoints = preventiveMaintanceSiteTransactionDetails.getShelterCheckPoints();
                         if(shelterCheckPoints != null)
                         {
                             mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterCleaningVal.setText(shelterCheckPoints.getShelterCleaning());
                             mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterLeakageVal.setText(shelterCheckPoints.getShelterLeakage());
                             mPreventiveMaintenanceSiteShelterCheckPointsTextViewHatchPlateEntrySealedVal.setText(shelterCheckPoints.getHatchPlateEntrysealed());
                             mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterFloorStatusVal.setText(shelterCheckPoints.getShleterFloorStatus());
                             mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterEarthingStatusVal.setText(shelterCheckPoints.getShelterEarthingStatus());
                             mPreventiveMaintenanceSiteShelterCheckPointsTextViewRegisterFaultVal.setText(shelterCheckPoints.getRegisterFault());
                             mPreventiveMaintenanceSiteShelterCheckPointsTextViewTypeOfFaultVal.setText(shelterCheckPoints.getTypeOfFault());
                         }
                     }
                 }
                 else
                 {
                     Toast.makeText(PreventiveMaintenanceSiteShelterCheckPointsActivity.this, "No previous saved data available", Toast.LENGTH_SHORT).show();
                 }
        }catch (Exception e){
                e.printStackTrace();
        }
    }

    private void submitDetails() {
        try {
               String shelterCleaning =  mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterCleaningVal.getText().toString().trim();
               String shelterLeakage =  mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterLeakageVal.getText().toString().trim();
               String hatchPlateEntrySealed =  mPreventiveMaintenanceSiteShelterCheckPointsTextViewHatchPlateEntrySealedVal.getText().toString().trim();
               String shelterFloorStatus = mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterFloorStatusVal.getText().toString().trim();
               String shelterEarthingStatus = mPreventiveMaintenanceSiteShelterCheckPointsTextViewShelterEarthingStatusVal.getText().toString().trim();
               String resultFault = mPreventiveMaintenanceSiteShelterCheckPointsTextViewRegisterFaultVal.getText().toString().trim();
               String typeOfFault = mPreventiveMaintenanceSiteShelterCheckPointsTextViewTypeOfFaultVal.getText().toString().trim();

               shelterCheckPoints = new ShelterCheckPoints(shelterCleaning,shelterLeakage,hatchPlateEntrySealed,shelterFloorStatus,shelterEarthingStatus,resultFault,typeOfFault);
               preventiveMaintanceSiteTransactionDetails.setShelterCheckPoints(shelterCheckPoints);
               Gson gson2 = new GsonBuilder().create();
               String jsonString = gson2.toJson(preventiveMaintanceSiteTransactionDetails);
               offlineStorageWrapper.saveObjectToFile(ticketName + ".txt", jsonString);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }
}