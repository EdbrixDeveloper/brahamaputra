package com.brahamaputra.mahindra.brahamaputra.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.brahamaputra.mahindra.brahamaputra.Data.EarthResistanceTowerData;
import com.brahamaputra.mahindra.brahamaputra.Data.HotoTransactionData;
import com.brahamaputra.mahindra.brahamaputra.Data.TowerDetailsData;
import com.brahamaputra.mahindra.brahamaputra.R;
import com.brahamaputra.mahindra.brahamaputra.Utils.SessionManager;
import com.brahamaputra.mahindra.brahamaputra.baseclass.BaseActivity;
import com.brahamaputra.mahindra.brahamaputra.commons.GlobalMethods;
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

public class Earth_Resistance_Tower extends BaseActivity {


    private TextView mEarthResistanceTowerTextViewTypeOfEarth;
    private TextView mEarthResistanceTowerTextViewTypeOfEarthVal;
    private TextView mEarthResistanceTowerTextViewEarthResistance;
    private EditText mEarthResistanceTowerEditTextEarthResistance;
    private TextView mEarthResistanceTowerTextViewDateOfearthResistanceMeasured;
    private EditText mEarthResistanceTowerEditTextDateOfearthResistanceMeasured;

    private static final String TAG = Earth_Resistance_Tower.class.getSimpleName();

    String str_typeOfEarth;

    private OfflineStorageWrapper offlineStorageWrapper;
    private String userId = "101";
    private String ticketId = "28131";
    private String ticketName = "28131";
    private HotoTransactionData hotoTransactionData;
    private EarthResistanceTowerData earthResistanceTowerData;
    private SessionManager sessionManager;

    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earth_resistance_tower);

        this.setTitle("Earth Resistance (Tower)");
        assignViews();
        initCombo();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        hotoTransactionData = new HotoTransactionData();

        sessionManager = new SessionManager(Earth_Resistance_Tower.this);
        ticketId = sessionManager.getSessionUserTicketId();
        ticketName = GlobalMethods.replaceAllSpecialCharAtUnderscore(sessionManager.getSessionUserTicketName());
        userId = sessionManager.getSessionUserId();
        offlineStorageWrapper = OfflineStorageWrapper.getInstance(Earth_Resistance_Tower.this, userId, ticketName);

        setInputDetails();


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

        mEarthResistanceTowerEditTextDateOfearthResistanceMeasured.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Earth_Resistance_Tower.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void assignViews() {
        mEarthResistanceTowerTextViewTypeOfEarth = (TextView) findViewById(R.id.earthResistanceTower_textView_typeOfEarth);
        mEarthResistanceTowerTextViewTypeOfEarthVal = (TextView) findViewById(R.id.earthResistanceTower_textView_typeOfEarth_val);
        mEarthResistanceTowerTextViewEarthResistance = (TextView) findViewById(R.id.earthResistanceTower_textView_earthResistance);
        mEarthResistanceTowerEditTextEarthResistance = (EditText) findViewById(R.id.earthResistanceTower_editText_earthResistance);
        mEarthResistanceTowerTextViewDateOfearthResistanceMeasured = (TextView) findViewById(R.id.earthResistanceTower_textView_dateOfearthResistanceMeasured);
        mEarthResistanceTowerEditTextDateOfearthResistanceMeasured = (EditText) findViewById(R.id.earthResistanceTower_editText_dateOfearthResistanceMeasured);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }

    private void initCombo() {
        mEarthResistanceTowerTextViewTypeOfEarthVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(Earth_Resistance_Tower.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_earthResistanceTower_typeOfEarth))),
                        "Type of Earth",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_typeOfEarth = item.get(position);
                        mEarthResistanceTowerTextViewTypeOfEarthVal.setText(str_typeOfEarth);
                    }
                });
            }
        });
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mEarthResistanceTowerEditTextDateOfearthResistanceMeasured.setText(sdf.format(myCalendar.getTime()));
    }


    private void setInputDetails() {
        try {
            if (offlineStorageWrapper.checkOfflineFileIsAvailable(ticketName + ".txt")) {
                String jsonInString = (String) offlineStorageWrapper.getObjectFromFile(ticketName + ".txt");

                Gson gson = new Gson();

                hotoTransactionData = gson.fromJson(jsonInString, HotoTransactionData.class);
                earthResistanceTowerData = hotoTransactionData.getEarthResistanceTowerData();

                mEarthResistanceTowerTextViewTypeOfEarthVal.setText(earthResistanceTowerData.getEarthType());
                mEarthResistanceTowerEditTextEarthResistance.setText(earthResistanceTowerData.getEarthResistanceInOhms());
                mEarthResistanceTowerEditTextDateOfearthResistanceMeasured.setText(earthResistanceTowerData.getEarthResistanceMeasuredDate());


            } else {
                Toast.makeText(Earth_Resistance_Tower.this, "No previous saved data available", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void submitDetails() {
        try {

            //hotoTransactionData.setTicketNo(ticketName);

            String earthType = mEarthResistanceTowerTextViewTypeOfEarthVal.getText().toString().trim();
            String earthResistanceInOhms = mEarthResistanceTowerEditTextEarthResistance.getText().toString().trim();
            String earthResistanceMeasuredDate = mEarthResistanceTowerEditTextDateOfearthResistanceMeasured.getText().toString().trim();

            earthResistanceTowerData = new EarthResistanceTowerData(earthType, earthResistanceInOhms, earthResistanceMeasuredDate);

            hotoTransactionData.setEarthResistanceTowerData(earthResistanceTowerData);

            Gson gson2 = new GsonBuilder().create();
            String jsonString = gson2.toJson(hotoTransactionData);

            offlineStorageWrapper.saveObjectToFile(ticketName + ".txt", jsonString);
        } catch (Exception e) {
            e.printStackTrace();
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
                //startActivity(new Intent(this, HotoSectionsListActivity.class));
                return true;

            case R.id.menuSubmit:
                submitDetails();
                startActivity(new Intent(this, Earth_Resistance_Equipment.class));
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
