package com.brahamaputra.mahindra.brahamaputra.Activities;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.app.progresviews.ProgressWheel;
import com.brahamaputra.mahindra.brahamaputra.Adapters.PmSiteExpListAdapter;

import com.brahamaputra.mahindra.brahamaputra.Application;
import com.brahamaputra.mahindra.brahamaputra.Data.HotoTicketList;
import com.brahamaputra.mahindra.brahamaputra.Data.PreventiveMaintanceSiteTransactionDetails;
import com.brahamaputra.mahindra.brahamaputra.Data.SitePMTicketsList;
import com.brahamaputra.mahindra.brahamaputra.R;
import com.brahamaputra.mahindra.brahamaputra.Utils.Conditions;
import com.brahamaputra.mahindra.brahamaputra.Utils.Constants;
import com.brahamaputra.mahindra.brahamaputra.Utils.SessionManager;
import com.brahamaputra.mahindra.brahamaputra.Volley.GsonRequest;
import com.brahamaputra.mahindra.brahamaputra.baseclass.BaseActivity;
import com.brahamaputra.mahindra.brahamaputra.commons.AlertDialogManager;
import com.brahamaputra.mahindra.brahamaputra.commons.GPSTracker;

import org.json.JSONException;
import org.json.JSONObject;

import static com.brahamaputra.mahindra.brahamaputra.Utils.Constants.hototicket_Selected_SiteType;
import static com.brahamaputra.mahindra.brahamaputra.Utils.Constants.hototicket_sourceOfPower;
import static com.brahamaputra.mahindra.brahamaputra.Utils.Constants.hototicket_nameOfSupplyCompany;

public class PreventiveMaintenanceDashboard extends BaseActivity {

    private ProgressWheel wheelprogress;
    private TextView acPreventiveMaintenanceSection_textView_openTickets;
    private TextView acPreventiveMaintenanceSection_textView_allTickets;
    private PmSiteExpListAdapter pmSiteExpListAdapter;
    public ExpandableListView pmSiteList_listView_siteList;
    private AlertDialogManager alertDialogManager;
    private SessionManager sessionManager;
    private SitePMTicketsList sitePMTicketsList;
    public GPSTracker gpsTracker;

    /////
    public static final int RESULT_PM_SITE_SUBMIT = 257;
    private TextView txtNoTicketFound;


    /*private LinearLayout mLinearLayoutStatus;
    private ProgressWheel mWheelprogress;
    private LinearLayout mLinearLayoutContainer1;
    private LinearLayout mPriventiveMaintenanceSiteLinearLayoutTicket1;
    private LinearLayout mPriventiveMaintenanceSiteLinearLayoutTicket2;
    private TextView mAcPreventiveMaintenanceSectionTextViewName;
    private TextView mAcPreventiveMaintenanceSectionTextViewNo;
    private ImageView mAcPreventiveMaintenanceSectionImageViewStatus;
    private LinearLayout mLinearLayoutContainer2;
    private TextView mAcPreventiveMaintenanceSectionTextViewName2;
    private TextView mAcPreventiveMaintenanceSectionTextViewNo2;
    private ImageView mAcPreventiveMaintenanceSectionImageViewStatus2;
    private TextView mTextViewHotoName;
    private TextView mTextViewSiteID;
    private TextView mTextViewSiteName;
    private TextView mTextViewSiteSSA;
    private TextView mTextViewSiteAddress;
    private TextView mTextViewHotoName1;
    private TextView mTextViewSiteID1;
    private TextView mTextViewSiteName1;
    private TextView mTextViewSiteSSA1;
    private TextView mTextViewSiteAddress1;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preventive_maintenance_dashboard);
        this.setTitle("Site PM");

        sitePMTicketsList = new SitePMTicketsList();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        wheelprogress = (ProgressWheel) findViewById(R.id.wheelprogress);
        acPreventiveMaintenanceSection_textView_openTickets = (TextView) findViewById(R.id.acPreventiveMaintenanceSection_textView_openTickets);
        acPreventiveMaintenanceSection_textView_allTickets = (TextView) findViewById(R.id.acPreventiveMaintenanceSection_textView_allTickets);
        pmSiteList_listView_siteList = (ExpandableListView) findViewById(R.id.pmSiteList_listView_siteList);
        txtNoTicketFound = (TextView) findViewById(R.id.txtNoTicketFound);
        txtNoTicketFound.setVisibility(View.GONE);

        alertDialogManager = new AlertDialogManager(PreventiveMaintenanceDashboard.this);
        sessionManager = new SessionManager(PreventiveMaintenanceDashboard.this);
        gpsTracker = new GPSTracker(PreventiveMaintenanceDashboard.this);
        if (gpsTracker.canGetLocation()) {
            Log.e(PreventiveMaintenanceDashboard.class.getName(), "Lat : " + gpsTracker.getLatitude() + "\n Long : " + gpsTracker.getLongitude());
        }

        prepareListData();


        pmSiteList_listView_siteList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                pmSiteList_listView_siteList.expandGroup(groupPosition);
                return true;
            }
        });
        pmSiteList_listView_siteList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, final int childPosition, long id) {
                // notify user

                LocationManager lm = (LocationManager) PreventiveMaintenanceDashboard.this.getSystemService(Context.LOCATION_SERVICE);
                boolean gps_enabled = false;
                boolean network_enabled = false;

                try {
                    gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
                    network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                } catch (Exception ex) {
                }

                if (!gps_enabled && !network_enabled) {
                    // notify user
                    alertDialogManager.Dialog("Information", "Location is not enabled. Do you want to enable?", "ok", "cancel", new AlertDialogManager.onSingleButtonClickListner() {
                        @Override
                        public void onPositiveClick() {
                            Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            PreventiveMaintenanceDashboard.this.startActivity(myIntent);
                        }
                    }).show();
                } else {
                    if (gpsTracker.getLongitude() > 0 && gpsTracker.getLongitude() > 0) {
                        if (sitePMTicketsList != null) {
                            final String sitePMTicketId = sitePMTicketsList.getSitePMTicketsDates().get(groupPosition).getSitePMTickets().get(childPosition).getId().toString();
                            final String sitePMTicketNo = sitePMTicketsList.getSitePMTicketsDates().get(groupPosition).getSitePMTickets().get(childPosition).getSitePMTicketNo().toString();

                            final String sitePMTicketDate = sitePMTicketsList.getSitePMTicketsDates().get(groupPosition).getSitePMTickets().get(childPosition).getSitePMTicketDate().toString();
                            final String siteId = sitePMTicketsList.getSitePMTicketsDates().get(groupPosition).getSitePMTickets().get(childPosition).getSiteId().toString();
                            final String siteName = sitePMTicketsList.getSitePMTicketsDates().get(groupPosition).getSitePMTickets().get(childPosition).getSiteName().toString();
                            final String siteAddress = sitePMTicketsList.getSitePMTicketsDates().get(groupPosition).getSitePMTickets().get(childPosition).getSiteAddress().toString();
                            final String status = sitePMTicketsList.getSitePMTicketsDates().get(groupPosition).getSitePMTickets().get(childPosition).getStatus().toString();
                            final String siteType = sitePMTicketsList.getSitePMTicketsDates().get(groupPosition).getSitePMTickets().get(childPosition).getSiteType().toString();
                            final String stateName = sitePMTicketsList.getSitePMTicketsDates().get(groupPosition).getSitePMTickets().get(childPosition).getStateName().toString();
                            final String customerName = sitePMTicketsList.getSitePMTicketsDates().get(groupPosition).getSitePMTickets().get(childPosition).getCustomerName().toString();
                            final String circleName = sitePMTicketsList.getSitePMTicketsDates().get(groupPosition).getSitePMTickets().get(childPosition).getCircleName().toString();
                            final String ssaName = sitePMTicketsList.getSitePMTicketsDates().get(groupPosition).getSitePMTickets().get(childPosition).getSSAName().toString();
                            final String sourceOfPower = sitePMTicketsList.getSitePMTicketsDates().get(groupPosition).getSitePMTickets().get(childPosition).getSourceOfPower().toString();
                            final String sitePmScheduledDate= sitePMTicketsList.getSitePMTicketsDates().get(groupPosition).getSitePMTickets().get(childPosition).getSitePMScheduledDate().toString();
                            hototicket_Selected_SiteType = siteType;
                            hototicket_sourceOfPower=sourceOfPower;
                            String sitePMTickStatus = sitePMTicketsList.getSitePMTicketsDates().get(groupPosition).getSitePMTickets().get(childPosition).getStatus().toString();
                            //hototicket_nameOfSupplyCompany = sitePMTicketsList.getSitePMTicketsDates().get(groupPosition).getSitePMTickets().get(childPosition).getNameOfSupplyCompany().toString();

                            if (sitePMTickStatus.equals("Open") || sitePMTickStatus.equals("WIP") || sitePMTickStatus.equals("Reassigned")) {
                                if (sitePMTickStatus.equals("Open")) {

                                    alertDialogManager.Dialog("Information", "Do you want to proceed.", "Yes", "No", new AlertDialogManager.onTwoButtonClickListner() {
                                        @Override
                                        public void onPositiveClick() {
                                            checkSystemLocation(sitePMTicketNo, sitePMTicketId, sitePMTicketDate, siteId, siteName, siteAddress, status, siteType,
                                                    stateName, customerName, circleName, ssaName,sitePmScheduledDate);
                                        }

                                        @Override
                                        public void onNegativeClick() {

                                        }
                                    }).show();

                                } else {
                                    checkSystemLocation(sitePMTicketNo, sitePMTicketId, sitePMTicketDate, siteId, siteName, siteAddress, status, siteType,
                                            stateName, customerName, circleName, ssaName,sitePmScheduledDate);
                                }

                            }
                        }

                    } else {
                        alertDialogManager.Dialog("Information", "Could not get your location. Please try again.", "ok", "cancel", new AlertDialogManager.onSingleButtonClickListner() {
                            @Override
                            public void onPositiveClick() {
                                if (gpsTracker.canGetLocation()) {
                                    //showToast("Lat : "+gpsTracker.getLatitude()+"\n Long : "+gpsTracker.getLongitude()); comment By 008 on 10-11-2018
                                    Log.e(PriventiveMaintenanceSiteTransactionActivity.class.getName(), "Lat : " + gpsTracker.getLatitude() + "\n Long : " + gpsTracker.getLongitude());
                                }
                            }
                        }).show();
                    }
                }


                return false;
            }
        });

        ///////////////////////////


        //this.setTitle("Site PM");
        //assignViews();
        //sessionManager = new SessionManager(PreventiveMaintenanceDashboard.this);
        //setListner();
    }

    private void prepareListData() {
        try {
            showBusyProgress();
            JSONObject jo = new JSONObject();


            jo.put("UserId", sessionManager.getSessionUserId());
            jo.put("AccessToken", sessionManager.getSessionDeviceToken());

            Log.i(PreventiveMaintenanceDashboard.class.getName(), Constants.hototTicketList + "\n\n" + jo.toString());

            GsonRequest<SitePMTicketsList> getAssignAvailabilityLearnersListRequest = new GsonRequest<>(Request.Method.POST, Constants.sitePmTicketList, jo.toString(), SitePMTicketsList.class,
                    new Response.Listener<SitePMTicketsList>() {
                        @Override
                        public void onResponse(@NonNull SitePMTicketsList response) {
                            hideBusyProgress();
                            if (response.getError() != null) {
                                showToast(response.getError().getErrorMessage());
                            } else {
                                if (response.getSuccess() == 1) {
                                    sitePMTicketsList = response;
                                    if (sitePMTicketsList.getSitePMTicketSummary() != null) {
                                        acPreventiveMaintenanceSection_textView_openTickets.setText(sitePMTicketsList.getSitePMTicketSummary().getOpenTickets() == null || sitePMTicketsList.getSitePMTicketSummary().getOpenTickets().isEmpty() ? "0" : sitePMTicketsList.getSitePMTicketSummary().getOpenTickets().toString());
                                        acPreventiveMaintenanceSection_textView_allTickets.setText(sitePMTicketsList.getSitePMTicketSummary().getTotalTickets() == null || sitePMTicketsList.getSitePMTicketSummary().getTotalTickets().isEmpty() ? "0" : sitePMTicketsList.getSitePMTicketSummary().getTotalTickets().toString());

                                        int per=0;
                                        double p=0.0;
                                        per=sitePMTicketsList.getSitePMTicketSummary().getPercentage() == null ? 0 : sitePMTicketsList.getSitePMTicketSummary().getPercentage();
                                        p=(3.6)*Double.valueOf(per);
                                        per=(int)Math.round(p);

                                        wheelprogress.setPercentage(per);
                                        //wheelprogress.setPercentage(sitePMTicketsList.getSitePMTicketSummary().getPercentage() == null ? 0 : sitePMTicketsList.getSitePMTicketSummary().getPercentage());
                                        wheelprogress.setStepCountText(sitePMTicketsList.getSitePMTicketSummary().getPercentage().toString() == null ? "0" : sitePMTicketsList.getSitePMTicketSummary().getPercentage().toString());
                                    }
                                    if (sitePMTicketsList.getSitePMTicketsDates() != null && sitePMTicketsList.getSitePMTicketsDates().size() > 0) {
                                        txtNoTicketFound.setVisibility(View.GONE);
                                        pmSiteList_listView_siteList.setVisibility(View.VISIBLE);
                                        pmSiteExpListAdapter = new PmSiteExpListAdapter(PreventiveMaintenanceDashboard.this, sitePMTicketsList);
                                        pmSiteList_listView_siteList.setAdapter(pmSiteExpListAdapter);
                                        for (int i = 0; i < sitePMTicketsList.getSitePMTicketsDates().size(); i++) {
                                            pmSiteList_listView_siteList.expandGroup(i);
                                        }
                                    } else {
                                        pmSiteList_listView_siteList.setVisibility(View.GONE);
                                        txtNoTicketFound.setVisibility(View.VISIBLE);
                                    }
                                }
                            }
                        }
                    }, new Response.ErrorListener()

            {
                @Override
                public void onErrorResponse(VolleyError error) {
                    hideBusyProgress();

                }
            });
            getAssignAvailabilityLearnersListRequest.setRetryPolicy(Application.getDefaultRetryPolice());
            getAssignAvailabilityLearnersListRequest.setShouldCache(false);
            Application.getInstance().

                    addToRequestQueue(getAssignAvailabilityLearnersListRequest, "assignavailabilitylearnerslist");

        } catch (JSONException e) {
            hideBusyProgress();
            showToast("Something went wrong. Please try again later.");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.refresh_icon_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.menuRefresh:
                prepareListData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_PM_SITE_SUBMIT && resultCode == RESULT_OK) {
            prepareListData();
        }
    }

    public void checkSystemLocation(final String sitePMTicketNo,
                                    final String sitePMTicketId, String sitePMTicketDate, String siteId,
                                    String siteName, String siteAddress, String status, String siteType, String
                                            stateName,
                                    String customerName, String circleName, String ssaName,String sitePmScheduledDate) {

        LocationManager lm = (LocationManager) PreventiveMaintenanceDashboard.this.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        if (!gps_enabled && !network_enabled) {
            // notify user
            alertDialogManager.Dialog("Information", "Location is not enabled. Do you want to enable?", "ok", "cancel", new AlertDialogManager.onSingleButtonClickListner() {
                @Override
                public void onPositiveClick() {
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    PreventiveMaintenanceDashboard.this.startActivity(myIntent);
                }
            }).show();
        } else {
            if (Conditions.isNetworkConnected(PreventiveMaintenanceDashboard.this)) {
                //if (gpsTracker.getLongitude()>0 && gpsTracker.getLongitude()>0){

                Intent intent = new Intent(PreventiveMaintenanceDashboard.this, PriventiveMaintenanceSiteTransactionActivity.class);
                intent.putExtra("isNetworkConnected", Conditions.isNetworkConnected(PreventiveMaintenanceDashboard.this));
                intent.putExtra("Id", sitePMTicketId);

                intent.putExtra("ticketNO", sitePMTicketNo);

                intent.putExtra("sitePMTicketDate", sitePMTicketDate);
                intent.putExtra("sitePmScheduledDate",sitePmScheduledDate);
                intent.putExtra("siteId", siteId);
                intent.putExtra("siteName", siteName);
                intent.putExtra("siteAddress", siteAddress);
                intent.putExtra("status", status);
                intent.putExtra("siteType", siteType);
                intent.putExtra("stateName", stateName);
                intent.putExtra("customerName", customerName);
                intent.putExtra("circleName", circleName);
                intent.putExtra("ssaName", ssaName);
                intent.putExtra("latitude", String.valueOf(gpsTracker.getLatitude()));
                intent.putExtra("longitude", String.valueOf(gpsTracker.getLongitude()));

                sessionManager.updateSessionUserTicketId(sitePMTicketId);
                sessionManager.updateSessionUserTicketName(sitePMTicketNo);
                startActivityForResult(intent, RESULT_PM_SITE_SUBMIT);

                //}else{
                //    showToast("Sorry could not detect location");
                //    finish();
                //}

            } else {
                alertDialogManager.Dialog("Information", "Device has no internet connection. Do you want to use offline mode?", "ok", "cancel", new AlertDialogManager.onSingleButtonClickListner() {
                    @Override
                    public void onPositiveClick() {
                        Intent intent = new Intent(PreventiveMaintenanceDashboard.this, PriventiveMaintenanceSiteTransactionActivity.class);
                        intent.putExtra("isNetworkConnected", Conditions.isNetworkConnected(PreventiveMaintenanceDashboard.this));
                        intent.putExtra("ticketNO", sitePMTicketNo);
                        sessionManager.updateSessionUserTicketId(sitePMTicketId);
                        sessionManager.updateSessionUserTicketName(sitePMTicketNo);
                        startActivityForResult(intent, RESULT_PM_SITE_SUBMIT);
                    }
                }).show();
            }
        }
    }


}
