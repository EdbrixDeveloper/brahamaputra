package com.brahamaputra.mahindra.brahamaputra.Activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.brahamaputra.mahindra.brahamaputra.R;
import com.brahamaputra.mahindra.brahamaputra.baseclass.BaseActivity;
import com.brahamaputra.mahindra.brahamaputra.commons.AlertDialogManager;

public class DashboardActivity extends BaseActivity {

    private RelativeLayout mDashboardRelativeLayoutMyHoto;
    private RelativeLayout mDashboardRelativeLayoutMyAsset;
    private RelativeLayout mDashboardRelativeLayoutMyMaster;
    private RelativeLayout mDashboardRelativeLayoutMyEnergy;
    private RelativeLayout mDashboardRelativeLayoutMyPrevitive;
    private RelativeLayout mDashboardRelativeLayoutMyIncident;
    final public int CHECK_PERMISSIONS = 123;
    private AlertDialogManager alertDialogManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        alertDialogManager = new AlertDialogManager(DashboardActivity.this);
        assignViews();
        setListner();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setting the title
        toolbar.setTitle(getResources().getString(R.string.app_name));
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        //placing toolbar in place of actionbar
        setSupportActionBar(toolbar);

    }

    private void setListner() {

        mDashboardRelativeLayoutMyHoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkSystemLocation();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.dashboard_menu, menu);
        return true;
    }


    private void assignViews() {
        mDashboardRelativeLayoutMyHoto = (RelativeLayout) findViewById(R.id.dashboard_relativeLayout_myHoto);
        mDashboardRelativeLayoutMyAsset = (RelativeLayout) findViewById(R.id.dashboard_relativeLayout_myAsset);
        mDashboardRelativeLayoutMyMaster = (RelativeLayout) findViewById(R.id.dashboard_relativeLayout_myMaster);
        mDashboardRelativeLayoutMyEnergy = (RelativeLayout) findViewById(R.id.dashboard_relativeLayout_myEnergy);
        mDashboardRelativeLayoutMyPrevitive = (RelativeLayout) findViewById(R.id.dashboard_relativeLayout_myPrevitive);
        mDashboardRelativeLayoutMyIncident = (RelativeLayout) findViewById(R.id.dashboard_relativeLayout_myIncident);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menuSubmit:
                startActivity(new Intent(DashboardActivity.this,UserProfileActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void checkSystemLocation(){
        LocationManager lm = (LocationManager)DashboardActivity.this.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ex) {}

        if(!gps_enabled && !network_enabled) {
            // notify user
            /*AlertDialog.Builder dialog = new AlertDialog.Builder(DashboardActivity.this);
            dialog.setMessage("Location Not Enabled");
            dialog.setPositiveButton("Open Location Settings", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    DashboardActivity.this.startActivity(myIntent);
                    //get gps
                }
            });
            dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                }
            });
            dialog.show();*/

            alertDialogManager.Dialog("Information", "Location is not enabled. Do you want to enable?", "ok", "cancel",new AlertDialogManager.onSingleButtonClickListner() {
                @Override
                public void onPositiveClick() {
                    Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    DashboardActivity.this.startActivity(myIntent);
                }
            }).show();

        }
        else{
            if(isNetworkConnected()){
                Intent intent = new Intent(DashboardActivity.this, UsersHotoListActivity.class);
                intent.putExtra("isNetworkConnected", isNetworkConnected());
                startActivity(intent);
            }else{
               /* AlertDialog.Builder dialog = new AlertDialog.Builder(DashboardActivity.this);
                dialog.setMessage("Device has no internet connection");
                dialog.setPositiveButton("Application work in offline mode.", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent intent = new Intent(DashboardActivity.this, UsersHotoListActivity.class);
                        intent.putExtra("isNetworkConnected", isNetworkConnected());
                        startActivity(intent);
                        //startActivity(new Intent(DashboardActivity.this,UsersHotoListActivity.class));
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        // TODO Auto-generated method stub
                    }
                });
                dialog.show();*/

                alertDialogManager.Dialog("Information", "Device has no internet connection. Do you want to use offline mode?", "ok", "cancel",  new AlertDialogManager.onSingleButtonClickListner() {
                    @Override
                    public void onPositiveClick() {

                        final EditText taskEditText = new EditText(DashboardActivity.this);
                        AlertDialog dialog = new AlertDialog.Builder(DashboardActivity.this)
                                .setTitle("Information")
                                .setMessage("Enter ticket id")
                                .setView(taskEditText)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //TicketID = String.valueOf(taskEditText.getText());

                                        Intent intent = new Intent(DashboardActivity.this, UserHotoTransactionActivity.class);
                                        intent.putExtra("ticketID", String.valueOf(taskEditText.getText()));
                                        startActivity(intent);
                                    }
                                })
                                .setNegativeButton("cancel", null)
                                .create();
                        dialog.show();


                    }
                }).show();
            }
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

}