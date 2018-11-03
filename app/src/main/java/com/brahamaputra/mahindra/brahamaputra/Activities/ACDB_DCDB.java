package com.brahamaputra.mahindra.brahamaputra.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.brahamaputra.mahindra.brahamaputra.Data.ACDB_DCDB_Data;
import com.brahamaputra.mahindra.brahamaputra.Data.HotoTransactionData;
import com.brahamaputra.mahindra.brahamaputra.Data.LandDetailsData;
import com.brahamaputra.mahindra.brahamaputra.R;
import com.brahamaputra.mahindra.brahamaputra.Utils.SessionManager;
import com.brahamaputra.mahindra.brahamaputra.baseclass.BaseActivity;
import com.brahamaputra.mahindra.brahamaputra.commons.AlertDialogManager;
import com.brahamaputra.mahindra.brahamaputra.commons.OfflineStorageWrapper;
import com.brahamaputra.mahindra.brahamaputra.helper.OnSpinnerItemClick;
import com.brahamaputra.mahindra.brahamaputra.helper.SearchableSpinnerDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

public class ACDB_DCDB extends BaseActivity {


    String str_NumberofACDB;
    String str_NumberofDCDB;
    String str_FreeCoolingDeviseStausFCU;

    private OfflineStorageWrapper offlineStorageWrapper;
    private String userId = "101";
    private String ticketName = "28131";
    private String ticketId = "28131";
    private HotoTransactionData hotoTransactionData;
    private ACDB_DCDB_Data acdb_dcdb_data;
    private SessionManager sessionManager;

    private TextView mAcdbDcdbTextViewNumberofACDB;
    private TextView mAcdbDcdbTextViewNumberofACDBVal;
    private TextView mAcdbDcdbTextViewACDBRatingAMP;
    private EditText mAcdbDcdbEditTextACDBRatingAMP;
    private TextView mAcdbDcdbTextViewNumberofDCDB;
    private TextView mAcdbDcdbTextViewNumberofDCDBVal;
    private TextView mAcdbDcdbTextViewFreeCoolingDeviseStausFCU;
    private TextView mAcdbDcdbTextViewFreeCoolingDeviseStausFCUVal;


   /* mAcdbDcdbTextViewNumberofACDBVal;
    mAcdbDcdbEditTextACDBRatingAMP;
    mAcdbDcdbTextViewNumberofDCDBVal;
    mAcdbDcdbTextViewFreeCoolingDeviseStausFCUVal;*/


    private void assignViews() {
        mAcdbDcdbTextViewNumberofACDB = (TextView) findViewById(R.id.acdb_dcdb_textView_NumberofACDB);
        mAcdbDcdbTextViewNumberofACDBVal = (TextView) findViewById(R.id.acdb_dcdb_textView_NumberofACDB_val);
        mAcdbDcdbTextViewACDBRatingAMP = (TextView) findViewById(R.id.acdb_dcdb_textView_ACDBRatingAMP);
        mAcdbDcdbEditTextACDBRatingAMP = (EditText) findViewById(R.id.acdb_dcdb_editText_ACDBRatingAMP);
        mAcdbDcdbTextViewNumberofDCDB = (TextView) findViewById(R.id.acdb_dcdb_textView_NumberofDCDB);
        mAcdbDcdbTextViewNumberofDCDBVal = (TextView) findViewById(R.id.acdb_dcdb_textView_NumberofDCDB_val);
        mAcdbDcdbTextViewFreeCoolingDeviseStausFCU = (TextView) findViewById(R.id.acdb_dcdb_textView_FreeCoolingDeviseStausFCU);
        mAcdbDcdbTextViewFreeCoolingDeviseStausFCUVal = (TextView) findViewById(R.id.acdb_dcdb_textView_FreeCoolingDeviseStausFCU_val);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

    }

    private void initCombo() {
        mAcdbDcdbTextViewNumberofDCDBVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(ACDB_DCDB.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_acdb_dcdb_NumberofDCDB))),
                        "Number of DCDB",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_NumberofDCDB = item.get(position);
                        mAcdbDcdbTextViewNumberofDCDBVal.setText(str_NumberofDCDB);
                    }
                });

            }
        });
        mAcdbDcdbTextViewNumberofACDBVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(ACDB_DCDB.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_acdb_dcdb_NumberofACDB))),
                        "Number of ACDB",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_NumberofACDB = item.get(position);
                        mAcdbDcdbTextViewNumberofACDBVal.setText(str_NumberofACDB);
                    }
                });

            }
        });
        mAcdbDcdbTextViewFreeCoolingDeviseStausFCUVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(ACDB_DCDB.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_acdb_dcdb_FreeCoolingDeviseStausFCU))),
                        "Free Cooling Devise Staus FCU",
                        "Close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_FreeCoolingDeviseStausFCU = item.get(position);
                        mAcdbDcdbTextViewFreeCoolingDeviseStausFCUVal.setText(str_FreeCoolingDeviseStausFCU);
                    }
                });

            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acdb_dcdb);
        this.setTitle("ACDB/DCDB");

        sessionManager = new SessionManager(ACDB_DCDB.this);
        ticketId = sessionManager.getSessionUserTicketId();
        ticketName = sessionManager.getSessionUserTicketId();
        userId = sessionManager.getSessionUserId();
        offlineStorageWrapper = OfflineStorageWrapper.getInstance(ACDB_DCDB.this, userId, ticketId);

        assignViews();
        initCombo();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        hotoTransactionData = new HotoTransactionData();
        setInputDetails();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.dropdown_details_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                //  startActivity(new Intent(this, HotoSectionsListActivity.class));
                return true;
            case R.id.menuDone:
                submitDetails();
                finish();
                startActivity(new Intent(this, ServoStabilizer.class));
                return true;


        }
        return super.onOptionsItemSelected(item);
    }

    private void setInputDetails() {
        try {
            if (offlineStorageWrapper.checkOfflineFileIsAvailable(ticketId + ".txt")) {
                String jsonInString = (String) offlineStorageWrapper.getObjectFromFile(ticketId + ".txt");

                Gson gson = new Gson();

                hotoTransactionData = gson.fromJson(jsonInString, HotoTransactionData.class);
                acdb_dcdb_data = hotoTransactionData.getAcdb_dcdb_data();



                mAcdbDcdbTextViewNumberofACDBVal.setText(acdb_dcdb_data.getNumberofACDB());;
                mAcdbDcdbEditTextACDBRatingAMP.setText(acdb_dcdb_data.getAcdbRatingAMP());;
                mAcdbDcdbTextViewNumberofDCDBVal.setText(acdb_dcdb_data.getNumberofDCDB());;
                mAcdbDcdbTextViewFreeCoolingDeviseStausFCUVal.setText(acdb_dcdb_data.getFreeCoolingDeviseStausFCU());;


            } else {
                Toast.makeText(ACDB_DCDB.this, "No previous saved data available", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void submitDetails() {
        try {
            hotoTransactionData.setTicketNo(ticketId);


            String numberofACDB = mAcdbDcdbTextViewNumberofACDBVal.getText().toString().trim();
            String acdbRatingAMP = mAcdbDcdbEditTextACDBRatingAMP.getText().toString().trim();
            String numberofDCDB = mAcdbDcdbTextViewNumberofDCDBVal.getText().toString().trim();
            String freeCoolingDeviseStausFCU = mAcdbDcdbTextViewFreeCoolingDeviseStausFCUVal.getText().toString().trim();

            acdb_dcdb_data = new ACDB_DCDB_Data(numberofACDB, acdbRatingAMP, numberofDCDB, freeCoolingDeviseStausFCU);

            hotoTransactionData.setAcdb_dcdb_data(acdb_dcdb_data);

            Gson gson2 = new GsonBuilder().create();
            String jsonString = gson2.toJson(hotoTransactionData);
            offlineStorageWrapper.saveObjectToFile(ticketId + ".txt", jsonString);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
