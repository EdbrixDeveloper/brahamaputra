package com.brahamaputra.mahindra.brahamaputra.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.brahamaputra.mahindra.brahamaputra.R;
import com.brahamaputra.mahindra.brahamaputra.baseclass.BaseActivity;

public class UserHotoTransactionActivity extends BaseActivity {


    private EditText mUserHotoTransEditTextCustomerName;
    private EditText mUserHotoTransEditTextState;
    private EditText mUserHotoTransEditTextNameOfCircle;
    private EditText mUserHotoTransEditTextNameOfssa;
    private EditText mUserHotoTransEditTextNameOfsite;
    private EditText mUserHotoTransEditTextSiteAddress;
    private EditText mUserHotoTransEditTextSiteID;
    private TextView mUserHotoTransTextViewTypeOfTower;

    private EditText mUserHotoTransEditTextTypeOfSites;

    private TextView mUserHotoTransTextViewSourceOfPower;
    private Spinner mUserHotoTransSpinnerSourceOfPower;
    private Button mUserHotoTransButtonSubmitHotoTrans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_hoto_transaction);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String id = intent.getStringExtra("ticketID");
        this.setTitle(id);

        assignViews();
        disableInput();
        checkNetworkConnection();

        mUserHotoTransButtonSubmitHotoTrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserHotoTransactionActivity.this,HotoSectionsListActivity.class));
            }
        });
    }

    public void checkNetworkConnection(){
        if(!isNetworkConnected()){
            mUserHotoTransEditTextCustomerName.setHint("Offline");
            mUserHotoTransEditTextState.setHint("Offline");
            mUserHotoTransEditTextNameOfCircle.setHint("Offline");
            mUserHotoTransEditTextNameOfssa.setHint("Offline");
            mUserHotoTransEditTextNameOfsite.setHint("Offline");
            mUserHotoTransEditTextSiteID.setHint("Offline");
            mUserHotoTransEditTextTypeOfSites.setHint("Offline");
        }
    }

    private void disableInput() {
        mUserHotoTransEditTextCustomerName.setKeyListener(null);
        mUserHotoTransEditTextState.setKeyListener(null);
        mUserHotoTransEditTextNameOfCircle.setKeyListener(null);
        mUserHotoTransEditTextNameOfssa.setKeyListener(null);
        mUserHotoTransEditTextNameOfsite.setKeyListener(null);
        mUserHotoTransEditTextTypeOfSites.setKeyListener(null);
        mUserHotoTransEditTextSiteID.setKeyListener(null);
    }

    private void assignViews() {
        mUserHotoTransEditTextCustomerName = (EditText) findViewById(R.id.userHotoTrans_editText_customerName);
        mUserHotoTransEditTextState = (EditText) findViewById(R.id.userHotoTrans_editText_state);
        mUserHotoTransEditTextNameOfCircle = (EditText) findViewById(R.id.userHotoTrans_editText_nameOfCircle);
        mUserHotoTransEditTextNameOfssa = (EditText) findViewById(R.id.userHotoTrans_editText_nameOfssa);
        mUserHotoTransEditTextNameOfsite = (EditText) findViewById(R.id.userHotoTrans_editText_nameOfsite);
        mUserHotoTransEditTextSiteAddress = (EditText) findViewById(R.id.userHotoTrans_editText_siteAddress);
        mUserHotoTransEditTextSiteID = (EditText) findViewById(R.id.userHotoTrans_editText_siteID);
        mUserHotoTransTextViewTypeOfTower = (TextView) findViewById(R.id.userHotoTrans_textView_typeOfTower);

        mUserHotoTransEditTextTypeOfSites = (EditText) findViewById(R.id.userHotoTrans_editText_typeOfSite);

        mUserHotoTransTextViewSourceOfPower = (TextView) findViewById(R.id.userHotoTrans_textView_sourceOfPower);
        mUserHotoTransSpinnerSourceOfPower = (Spinner) findViewById(R.id.userHotoTrans_spinner_sourceOfPower);
        mUserHotoTransButtonSubmitHotoTrans = (Button)findViewById(R.id.userHotoTrans_button_submitHotoTrans);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.userhoto_transaction_menu, menu);
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

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

}
