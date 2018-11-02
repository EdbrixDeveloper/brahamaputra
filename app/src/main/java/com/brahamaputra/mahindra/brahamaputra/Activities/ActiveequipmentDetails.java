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

import com.brahamaputra.mahindra.brahamaputra.R;
import com.brahamaputra.mahindra.brahamaputra.baseclass.BaseActivity;
import com.brahamaputra.mahindra.brahamaputra.helper.OnSpinnerItemClick;
import com.brahamaputra.mahindra.brahamaputra.helper.SearchableSpinnerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

public class ActiveequipmentDetails extends BaseActivity {


    final Calendar myCalendar = Calendar.getInstance();

    String str_typeofBTS;
    String str_importanceOfSite;
    String str_numberOfDependantSites;

    private TextView mActiveEquipmentDetailsTextViewTypeofBTS;
    private TextView mActiveEquipmentDetailsTextViewTypeofBTSVal;
    private TextView mActiveEquipmentDetailsTextViewImportanceOfSite;
    private TextView mActiveEquipmentDetailsTextViewImportanceOfSiteVal;
    private TextView mActiveEquipmentDetailsTextViewNumberOfDependantSites;
    private TextView mActiveEquipmentDetailsTextViewNumberOfDependantSitesVal;
    private TextView mActiveEquipmentDetailsTextViewMake;
    private EditText mActiveEquipmentDetailsEditTextMake;
    private TextView mActiveEquipmentDetailsTextViewDCLoadofBTSequipment;
    private EditText mActiveEquipmentDetailsEditTextDCLoadofBTSequipment;
    private TextView mActiveEquipmentDetailsTextViewYearofInstallationatsite;
    private EditText mActiveEquipmentDetailsEditTextYearofInstallationatsite;
    private TextView mActiveEquipmentDetailsTextViewPositionoftheantennaatTowerinMtrs;
    private EditText mActiveEquipmentDetailsEditTextPositionoftheantennaatTowerinMtrs;

    private void assignViews() {
        mActiveEquipmentDetailsTextViewTypeofBTS = (TextView) findViewById(R.id.activeEquipmentDetails_textView_TypeofBTS);
        mActiveEquipmentDetailsTextViewTypeofBTSVal = (TextView) findViewById(R.id.activeEquipmentDetails_textView_TypeofBTS_val);
        mActiveEquipmentDetailsTextViewImportanceOfSite = (TextView) findViewById(R.id.activeEquipmentDetails_textView_importance_of_Site);
        mActiveEquipmentDetailsTextViewImportanceOfSiteVal = (TextView) findViewById(R.id.activeEquipmentDetails_textView_importance_of_Site_val);
        mActiveEquipmentDetailsTextViewNumberOfDependantSites = (TextView) findViewById(R.id.activeEquipmentDetails_textView_numberOfDependantSites);
        mActiveEquipmentDetailsTextViewNumberOfDependantSitesVal = (TextView) findViewById(R.id.activeEquipmentDetails_textView_numberOfDependantSites_val);
        mActiveEquipmentDetailsTextViewMake = (TextView) findViewById(R.id.activeEquipmentDetails_textView_Make);
        mActiveEquipmentDetailsEditTextMake = (EditText) findViewById(R.id.activeEquipmentDetails_editText_Make);
        mActiveEquipmentDetailsTextViewDCLoadofBTSequipment = (TextView) findViewById(R.id.activeEquipmentDetails_textView_DCLoadofBTSequipment);
        mActiveEquipmentDetailsEditTextDCLoadofBTSequipment = (EditText) findViewById(R.id.activeEquipmentDetails_editText_DCLoadofBTSequipment);
        mActiveEquipmentDetailsTextViewYearofInstallationatsite = (TextView) findViewById(R.id.activeEquipmentDetails_textView_YearofInstallationatsite);
        mActiveEquipmentDetailsEditTextYearofInstallationatsite = (EditText) findViewById(R.id.activeEquipmentDetails_editText_YearofInstallationatsite);
        mActiveEquipmentDetailsTextViewPositionoftheantennaatTowerinMtrs = (TextView) findViewById(R.id.activeEquipmentDetails_textView_PositionoftheantennaatTowerinMtrs);
        mActiveEquipmentDetailsEditTextPositionoftheantennaatTowerinMtrs = (EditText) findViewById(R.id.activeEquipmentDetails_editText_PositionoftheantennaatTowerinMtrs);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

    }

    private void initCombo()
    {

        mActiveEquipmentDetailsTextViewTypeofBTSVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(ActiveequipmentDetails.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_activeEquipmentDetails_TypeofBTS))),
                        "Type of BTS",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_typeofBTS = item.get(position);
                        mActiveEquipmentDetailsTextViewTypeofBTSVal.setText(str_typeofBTS);
                    }
                });

            }
        });

        mActiveEquipmentDetailsTextViewImportanceOfSiteVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(ActiveequipmentDetails.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_activeEquipmentDetails_importance_of_Site))),
                        "Importance of Site",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_importanceOfSite = item.get(position);
                        mActiveEquipmentDetailsTextViewImportanceOfSiteVal.setText(str_importanceOfSite);
                    }
                });

            }
        });

        mActiveEquipmentDetailsTextViewNumberOfDependantSitesVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(ActiveequipmentDetails.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_activeEquipmentDetails_numberOfDependantSites))),
                        "Number of Dependant Sites",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_numberOfDependantSites = item.get(position);
                        mActiveEquipmentDetailsTextViewNumberOfDependantSitesVal.setText(str_numberOfDependantSites);
                    }
                });

            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_equipment_details);
        this.setTitle("Active equipment Details");
        assignViews();
        initCombo();

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

        mActiveEquipmentDetailsEditTextYearofInstallationatsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ActiveequipmentDetails.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.dropdown_details_menu, menu);
        return true;
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mActiveEquipmentDetailsEditTextYearofInstallationatsite.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                //startActivity(new Intent(this, HotoSectionsListActivity.class));
                return true;
            case R.id.menuDone:
                finish();
                startActivity(new Intent(this, PowerManagementSystem.class));
                return true;


        }
        return super.onOptionsItemSelected(item);
    }
}