package com.brahamaputra.mahindra.brahamaputra.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.app.progresviews.ProgressWheel;
import com.brahamaputra.mahindra.brahamaputra.Adapters.WrmsUptimeReportExpListAdapter;
import com.brahamaputra.mahindra.brahamaputra.Application;
import com.brahamaputra.mahindra.brahamaputra.Data.WrmsUptimeReportListData;
import com.brahamaputra.mahindra.brahamaputra.R;
import com.brahamaputra.mahindra.brahamaputra.Utils.Constants;
import com.brahamaputra.mahindra.brahamaputra.Utils.SessionManager;
import com.brahamaputra.mahindra.brahamaputra.Volley.GsonRequest;
import com.brahamaputra.mahindra.brahamaputra.baseclass.BaseActivity;
import com.brahamaputra.mahindra.brahamaputra.commons.AlertDialogManager;
import com.brahamaputra.mahindra.brahamaputra.commons.GPSTracker;
import com.brahamaputra.mahindra.brahamaputra.helper.OnSpinnerItemClick;
import com.brahamaputra.mahindra.brahamaputra.helper.SearchableSpinnerDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class WrmsUptimeReportDashboardActivity extends BaseActivity {

    private LinearLayout mWrmsUptimeReportLinearLayoutFiltersMonthYear;
    private TextView mWrmsUptimeReportTextViewFiltersMonth;
    private TextView mWrmsUptimeReportTextViewFiltersYear;
    private Button mWrmsUptimeReportButtonFiltersMonthYear;
    private LinearLayout mLinearLayoutStatus;
    private ProgressWheel mWheelprogress;
    private LinearLayout mLinearLayoutContainer1;
    private TextView mWrmsUptimeReportTextViewSsaUptimePercentage;
    private TextView mWrmsUptimeReportTextViewSsaUptimeText;
    private LinearLayout mLinearLayoutContainer2;
    private TextView mWrmsUptimeReportTextViewCircleUptimePercentage;
    private TextView mWrmsUptimeReportTextViewCircleUptimeText;
    private LinearLayout mLinearLayoutContainer3;
    private TextView mWrmsUptimeReportTextViewMyUptimePercentage;
    private TextView mWrmsUptimeReportTextViewMyUptimeText;
    private LinearLayout mLinearLayoutContainer4;
    private TextView mWrmsUptimeReportTextViewTargetUptimePercentage;
    private TextView mWrmsUptimeReportTextViewTargetUptimeText;
    private LinearLayout mLinearLayoutTitleNames;
    private TextView mTextViewWrmsUptimeReportTitle;
    private ExpandableListView mWrmsUptimeReportListViewUptimeReportList;
    private TextView mTxtNoTicketFound;

    private LinearLayout mWrmsUptimeReportLinearLayoutTicket1;
    private TextView mTextViewReportSiteName;
    private TextView mTextViewReportSiteId;
    private TextView mTextViewReportSiteSSA;
    private TextView mTextViewReportSiteUptime;
    private TextView mTextViewReportSiteDeviation;
    private LinearLayout mWrmsUptimeReportLinearLayoutTicket2;
    private TextView mTextViewReportSiteName1;
    private TextView mTextViewReportSiteId1;
    private TextView mTextViewReportSiteSSA1;
    private TextView mTextViewReportSiteUptime1;
    private TextView mTextViewReportSiteDeviation1;

    private ArrayList<String> monthNames;
    private WrmsUptimeReportListData wrmsUptimeReportListData;

    String str_pmAcMonthVal = "";
    String str_pmAcYearVal = "";

    private AlertDialogManager alertDialogManager;
    private SessionManager sessionManager;
    public GPSTracker gpsTracker;
    public static final int RESULT_PM_SITE_SUBMIT = 257;

    WrmsUptimeReportExpListAdapter wrmsUptimeReportExpListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrms_uptime_report_dashboard);
        this.setTitle("WRMS Uptime Report");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        monthNames = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteReportDashboard_monthName)));

        assignViews();
        initCombo();

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);

        mWrmsUptimeReportTextViewFiltersMonth.setText(monthNames.get(month));
        mWrmsUptimeReportTextViewFiltersYear.setText(String.valueOf(year));


        wrmsUptimeReportListData = new WrmsUptimeReportListData();

        mWheelprogress = (ProgressWheel) findViewById(R.id.wheelprogress);
        mWrmsUptimeReportListViewUptimeReportList = (ExpandableListView) findViewById(R.id.wrmsUptimeReport_listView_uptimeReportList);
        mWrmsUptimeReportTextViewSsaUptimePercentage = (TextView) findViewById(R.id.wrmsUptimeReport_textView_ssaUptime_percentage);
        mWrmsUptimeReportTextViewCircleUptimePercentage = (TextView) findViewById(R.id.wrmsUptimeReport_textView_circleUptime_percentage);
        mWrmsUptimeReportTextViewMyUptimePercentage = (TextView) findViewById(R.id.wrmsUptimeReport_textView_myUptime_percentage);
        mWrmsUptimeReportTextViewTargetUptimePercentage = (TextView) findViewById(R.id.wrmsUptimeReport_textView_targetUptime_percentage);


        mTxtNoTicketFound = (TextView) findViewById(R.id.txtNoTicketFound);
        mTxtNoTicketFound.setVisibility(View.GONE);
        mLinearLayoutTitleNames = (LinearLayout) findViewById(R.id.linearLayout_titleNames);
        mTextViewWrmsUptimeReportTitle = (TextView) findViewById(R.id.textView_wrmsUptimeReportTitle);

        alertDialogManager = new AlertDialogManager(WrmsUptimeReportDashboardActivity.this);
        sessionManager = new SessionManager(WrmsUptimeReportDashboardActivity.this);
        gpsTracker = new GPSTracker(WrmsUptimeReportDashboardActivity.this);
        if (gpsTracker.canGetLocation()) {
            Log.e(PreventiveMaintenanceSiteReportDashboard.class.getName(), "Lat : " + gpsTracker.getLatitude() + "\n Long : " + gpsTracker.getLongitude());
        }

        /*prepareSitePmReportListData();

        //default calling first time
        mLinearLayoutTitleNames.setVisibility(View.VISIBLE);
        mTextViewWrmsUptimeReportTitle.setText("Current Month Plan");
        prepareListDataOnChangedAndSelection("4");

        mWrmsUptimeReportButtonFiltersMonthYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mLinearLayoutTitleNames.setVisibility(View.GONE);
                mTextViewWrmsUptimeReportTitle.setText("Title");
                prepareSitePmReportListData();

                ///////////////
                mLinearLayoutTitleNames.setVisibility(View.VISIBLE);
                mTextViewWrmsUptimeReportTitle.setText("Current Month Plan");
                prepareListDataOnChangedAndSelection("4");


            }
        });

        mWheelprogress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLinearLayoutTitleNames.setVisibility(View.VISIBLE);
                mTextViewWrmsUptimeReportTitle.setText("Total Sites");
                sitePmReportType = "1";
                prepareListDataOnChangedAndSelection("1");
                //showToast("Clicked on total site filter");
            }
        });

        mLinearLayoutContainer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLinearLayoutTitleNames.setVisibility(View.VISIBLE);
                mTextViewWrmsUptimeReportTitle.setText("Done");
                sitePmReportType = "2";
                prepareListDataOnChangedAndSelection("2");

                //showToast("Clicked on done site filter");
            }
        });

        mLinearLayoutContainer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLinearLayoutTitleNames.setVisibility(View.VISIBLE);
                mTextViewWrmsUptimeReportTitle.setText("Pending");
                sitePmReportType = "3";
                prepareListDataOnChangedAndSelection("3");
                //showToast("Clicked on pending site filter");
            }
        });

        mLinearLayoutContainer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLinearLayoutTitleNames.setVisibility(View.VISIBLE);
                mTextViewWrmsUptimeReportTitle.setText("Current Month Plan");
                sitePmReportType = "4";
                prepareListDataOnChangedAndSelection("4");

                //showToast("Clicked on total site pm filter");
            }
        });

        mWrmsUptimeReportListViewUptimeReportList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                mWrmsUptimeReportListViewUptimeReportList.expandGroup(groupPosition);
                return true;
            }
        });

        mWrmsUptimeReportListViewUptimeReportList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, final int childPosition, long id) {
                // notify user

                return false;
            }
        });*/
    }

    private void initCombo() {

        mWrmsUptimeReportTextViewFiltersMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(WrmsUptimeReportDashboardActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteReportDashboard_monthName))),
                        "Select Month",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmAcMonthVal = item.get(position);
                        mWrmsUptimeReportTextViewFiltersMonth.setText(str_pmAcMonthVal);
                    }
                });
            }
        });

        mWrmsUptimeReportTextViewFiltersYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchableSpinnerDialog searchableSpinnerDialog = new SearchableSpinnerDialog(WrmsUptimeReportDashboardActivity.this,
                        new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.array_pmSiteReportDashboard_yearName))),
                        "Select Year",
                        "close", "#000000");
                searchableSpinnerDialog.showSearchableSpinnerDialog();

                searchableSpinnerDialog.bindOnSpinerListener(new OnSpinnerItemClick() {
                    @Override
                    public void onClick(ArrayList<String> item, int position) {

                        str_pmAcYearVal = item.get(position);
                        mWrmsUptimeReportTextViewFiltersYear.setText(str_pmAcYearVal);
                    }
                });
            }
        });
    }

    private void assignViews() {
        mWrmsUptimeReportLinearLayoutFiltersMonthYear = (LinearLayout) findViewById(R.id.wrmsUptimeReport_linearLayout_filtersMonthYear);
        mWrmsUptimeReportTextViewFiltersMonth = (TextView) findViewById(R.id.wrmsUptimeReport_textView_filtersMonth);
        mWrmsUptimeReportTextViewFiltersYear = (TextView) findViewById(R.id.wrmsUptimeReport_textView_filtersYear);
        mWrmsUptimeReportButtonFiltersMonthYear = (Button) findViewById(R.id.wrmsUptimeReport_Button_filtersMonthYear);
        mLinearLayoutStatus = (LinearLayout) findViewById(R.id.linearLayout_Status);
        mWheelprogress = (ProgressWheel) findViewById(R.id.wheelprogress);
        mLinearLayoutContainer1 = (LinearLayout) findViewById(R.id.linearLayout_container1);
        mWrmsUptimeReportTextViewSsaUptimePercentage = (TextView) findViewById(R.id.wrmsUptimeReport_textView_ssaUptime_percentage);
        mWrmsUptimeReportTextViewSsaUptimeText = (TextView) findViewById(R.id.wrmsUptimeReport_textView_ssaUptime_text);
        mLinearLayoutContainer2 = (LinearLayout) findViewById(R.id.linearLayout_container2);
        mWrmsUptimeReportTextViewCircleUptimePercentage = (TextView) findViewById(R.id.wrmsUptimeReport_textView_circleUptime_percentage);
        mWrmsUptimeReportTextViewCircleUptimeText = (TextView) findViewById(R.id.wrmsUptimeReport_textView_circleUptime_text);
        mLinearLayoutContainer3 = (LinearLayout) findViewById(R.id.linearLayout_container3);
        mWrmsUptimeReportTextViewMyUptimePercentage = (TextView) findViewById(R.id.wrmsUptimeReport_textView_myUptime_percentage);
        mWrmsUptimeReportTextViewMyUptimeText = (TextView) findViewById(R.id.wrmsUptimeReport_textView_myUptime_text);
        mLinearLayoutContainer4 = (LinearLayout) findViewById(R.id.linearLayout_container4);
        mWrmsUptimeReportTextViewTargetUptimePercentage = (TextView) findViewById(R.id.wrmsUptimeReport_textView_targetUptime_percentage);
        mWrmsUptimeReportTextViewTargetUptimeText = (TextView) findViewById(R.id.wrmsUptimeReport_textView_targetUptime_text);
        mLinearLayoutTitleNames = (LinearLayout) findViewById(R.id.linearLayout_titleNames);
        mTextViewWrmsUptimeReportTitle = (TextView) findViewById(R.id.textView_wrmsUptimeReportTitle);
        mWrmsUptimeReportListViewUptimeReportList = (ExpandableListView) findViewById(R.id.wrmsUptimeReport_listView_uptimeReportList);
        mTxtNoTicketFound = (TextView) findViewById(R.id.txtNoTicketFound);
        mWrmsUptimeReportLinearLayoutTicket1 = (LinearLayout) findViewById(R.id.wrmsUptimeReport_linearLayout_ticket1);
        mTextViewReportSiteName = (TextView) findViewById(R.id.textView_reportSiteName);
        mTextViewReportSiteId = (TextView) findViewById(R.id.textView_reportSiteId);
        mTextViewReportSiteSSA = (TextView) findViewById(R.id.textView_reportSiteSSA);
        mTextViewReportSiteUptime = (TextView) findViewById(R.id.textView_reportSiteUptime);
        mTextViewReportSiteDeviation = (TextView) findViewById(R.id.textView_reportSiteDeviation);
        mWrmsUptimeReportLinearLayoutTicket2 = (LinearLayout) findViewById(R.id.wrmsUptimeReport_linearLayout_ticket2);
        mTextViewReportSiteName1 = (TextView) findViewById(R.id.textView_reportSiteName1);
        mTextViewReportSiteId1 = (TextView) findViewById(R.id.textView_reportSiteId1);
        mTextViewReportSiteSSA1 = (TextView) findViewById(R.id.textView_reportSiteSSA1);
        mTextViewReportSiteUptime1 = (TextView) findViewById(R.id.textView_reportSiteUptime1);
        mTextViewReportSiteDeviation1 = (TextView) findViewById(R.id.textView_reportSiteDeviation1);
    }

    /* Count Summory*/
    private void prepareSitePmReportListData() {

        try {
            showBusyProgress();
            JSONObject jo = new JSONObject();

            jo.put("UserId", sessionManager.getSessionUserId());
            jo.put("UserTypeId", sessionManager.getSessionUserTypeId());
            jo.put("AccessToken", sessionManager.getSessionDeviceToken());
            int i = monthNames.indexOf(mWrmsUptimeReportTextViewFiltersMonth.getText().toString().trim()) + 1;
            jo.put("Month", String.valueOf(i));
            jo.put("Year", mWrmsUptimeReportTextViewFiltersYear.getText().toString().trim());


            Log.i(PreventiveMaintenanceSiteReportDashboard.class.getName(), Constants.acPmReportAcList + "\n\n" + jo.toString());

            GsonRequest<WrmsUptimeReportListData> getWrmsUptimeReportListData = new GsonRequest<>(Request.Method.POST, Constants.acPmReportAcList, jo.toString(), WrmsUptimeReportListData.class,
                    new Response.Listener<WrmsUptimeReportListData>() {
                        @Override
                        public void onResponse(@NonNull WrmsUptimeReportListData response) {
                            hideBusyProgress();
                            if (response.getError() != null) {
                                showToast(response.getError().getErrorMessage());
                            } else {
                                if (response.getSuccess() == 1) {
                                    wrmsUptimeReportListData = response;
                                    if (wrmsUptimeReportListData.getWrmsUptimeReportSummary() != null) {
                                        mWrmsUptimeReportTextViewSsaUptimePercentage.setText(wrmsUptimeReportListData.getWrmsUptimeReportSummary().getSSAUptime() == null || wrmsUptimeReportListData.getWrmsUptimeReportSummary().getSSAUptime().isEmpty() ? "0" : wrmsUptimeReportListData.getWrmsUptimeReportSummary().getSSAUptime().toString());
                                        mWrmsUptimeReportTextViewCircleUptimePercentage.setText(wrmsUptimeReportListData.getWrmsUptimeReportSummary().getCircleUptime() == null || wrmsUptimeReportListData.getWrmsUptimeReportSummary().getCircleUptime().isEmpty() ? "0" : wrmsUptimeReportListData.getWrmsUptimeReportSummary().getCircleUptime().toString());
                                        mWrmsUptimeReportTextViewMyUptimePercentage.setText(wrmsUptimeReportListData.getWrmsUptimeReportSummary().getMyUptime() == null || wrmsUptimeReportListData.getWrmsUptimeReportSummary().getMyUptime().isEmpty() ? "0" : wrmsUptimeReportListData.getWrmsUptimeReportSummary().getMyUptime().toString());
                                        mWrmsUptimeReportTextViewTargetUptimePercentage.setText(wrmsUptimeReportListData.getWrmsUptimeReportSummary().getTargetUptime() == null || wrmsUptimeReportListData.getWrmsUptimeReportSummary().getTargetUptime().isEmpty() ? "0" : wrmsUptimeReportListData.getWrmsUptimeReportSummary().getTargetUptime().toString());
                                        mWheelprogress.setPercentage(360);
                                        mWheelprogress.setStepCountText(String.valueOf(wrmsUptimeReportListData.getWrmsUptimeReportSummary().getTotalSites()));//per
                                    } else {
                                        mWrmsUptimeReportTextViewSsaUptimePercentage.setText("0%");
                                        mWrmsUptimeReportTextViewCircleUptimePercentage.setText("0%");
                                        mWrmsUptimeReportTextViewMyUptimePercentage.setText("0%");
                                        mWrmsUptimeReportTextViewTargetUptimePercentage.setText("0%");
                                        mWheelprogress.setPercentage(0);
                                        mWheelprogress.setStepCountText("0");//per
                                        mWrmsUptimeReportListViewUptimeReportList.setVisibility(View.GONE);
                                        mTxtNoTicketFound.setVisibility(View.VISIBLE);
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
            getWrmsUptimeReportListData.setRetryPolicy(Application.getDefaultRetryPolice());
            getWrmsUptimeReportListData.setShouldCache(false);
            Application.getInstance().

                    addToRequestQueue(getWrmsUptimeReportListData, "WrmsUptimeReportListData");

        } catch (JSONException e) {
            hideBusyProgress();
            showToast("Something went wrong. Please try again later.");
        }
    }

    private void prepareListDataOnChangedAndSelection(String type) {
        try {
            showBusyProgress();
            JSONObject jo = new JSONObject();

            jo.put("UserId", sessionManager.getSessionUserId());
            jo.put("UserTypeId", sessionManager.getSessionUserTypeId());
            jo.put("AccessToken", sessionManager.getSessionDeviceToken());

            int i = monthNames.indexOf(mWrmsUptimeReportTextViewFiltersMonth.getText().toString().trim()) + 1;
            jo.put("Month", String.valueOf(i));
            jo.put("Year", mWrmsUptimeReportTextViewFiltersYear.getText().toString().trim());
            jo.put("Type", type);

            Log.i(PreventiveMaintenanceSiteReportDashboard.class.getName(), Constants.acPmReportDashboardData + "\n\n" + jo.toString());

            GsonRequest<WrmsUptimeReportListData> getWrmsUptimeReportListData = new GsonRequest<>(Request.Method.POST, Constants.acPmReportDashboardData, jo.toString(), WrmsUptimeReportListData.class,
                    new Response.Listener<WrmsUptimeReportListData>() {
                        @Override
                        public void onResponse(@NonNull WrmsUptimeReportListData response) {
                            hideBusyProgress();
                            if (response.getError() != null) {
                                showToast(response.getError().getErrorMessage());
                            } else {
                                if (response.getSuccess() == 1) {
                                    wrmsUptimeReportListData = response;
                                    if (wrmsUptimeReportListData.getWrmsUptimeReportTicketsDates() != null && wrmsUptimeReportListData.getWrmsUptimeReportTicketsDates().size() > 0) {
                                        mTxtNoTicketFound.setVisibility(View.GONE);
                                        mWrmsUptimeReportListViewUptimeReportList.setVisibility(View.VISIBLE);
                                        wrmsUptimeReportExpListAdapter = new WrmsUptimeReportExpListAdapter(WrmsUptimeReportDashboardActivity.this, wrmsUptimeReportListData);
                                        mWrmsUptimeReportListViewUptimeReportList.setAdapter(wrmsUptimeReportExpListAdapter);
                                        for (int i = 0; i < wrmsUptimeReportListData.getWrmsUptimeReportTicketsDates().size(); i++) {
                                            mWrmsUptimeReportListViewUptimeReportList.expandGroup(i);
                                        }
                                    } else {
                                        mWrmsUptimeReportListViewUptimeReportList.setVisibility(View.GONE);
                                        mTxtNoTicketFound.setVisibility(View.VISIBLE);
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
            getWrmsUptimeReportListData.setRetryPolicy(Application.getDefaultRetryPolice());
            getWrmsUptimeReportListData.setShouldCache(false);
            Application.getInstance().

                    addToRequestQueue(getWrmsUptimeReportListData, "WRMSUptimeReportListData");

        } catch (JSONException e) {
            hideBusyProgress();
            showToast("Something went wrong. Please try again later.");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
