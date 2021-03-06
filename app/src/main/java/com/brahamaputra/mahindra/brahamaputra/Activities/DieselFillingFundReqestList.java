package com.brahamaputra.mahindra.brahamaputra.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.brahamaputra.mahindra.brahamaputra.Adapters.DieselFillingFundRequestListAdapter;
import com.brahamaputra.mahindra.brahamaputra.Application;
import com.brahamaputra.mahindra.brahamaputra.Data.DieselFillingFundRequestTransaction;
import com.brahamaputra.mahindra.brahamaputra.Data.DiselRequestTransactionList;
import com.brahamaputra.mahindra.brahamaputra.R;
import com.brahamaputra.mahindra.brahamaputra.Utils.Constants;
import com.brahamaputra.mahindra.brahamaputra.Utils.SessionManager;
import com.brahamaputra.mahindra.brahamaputra.Volley.GsonRequest;
import com.brahamaputra.mahindra.brahamaputra.baseclass.BaseActivity;
import com.brahamaputra.mahindra.brahamaputra.commons.EndlessScrollListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DieselFillingFundReqestList extends BaseActivity {

    private DieselFillingFundRequestTransaction dieselFillingFundRequestTransaction;
    private SessionManager sessionManager;

    private ListView mDieselFillingReqListListViewTickets;
    private TextView mTxtNoTicketFound;
    private DieselFillingFundRequestListAdapter dieselFillingFundRequestListAdapter;
    public static final int RESULT_TRAN_SUBMIT = 300;

    ArrayList<DiselRequestTransactionList> diselRequestTransactionList;
    private int requestCount = 1;
    private boolean loading = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diesel_filling_fund_reqest_list);
        this.setTitle("Diesel Request");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sessionManager = new SessionManager(DieselFillingFundReqestList.this);
        dieselFillingFundRequestTransaction = new DieselFillingFundRequestTransaction();
        assignViews();

        diselRequestTransactionList = new ArrayList<DiselRequestTransactionList>();
        //timelineScrollItem();

        if (requestCount < 2) {
            if (loading) {
                getTimeLineData(String.valueOf(requestCount), 0);
            }
        }
        mDieselFillingReqListListViewTickets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount, int firstVisibleItem) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                if (loading) {
                    getTimeLineData(String.valueOf(page), firstVisibleItem);
                }
                // or loadNextDataFromApi(totalItemsCount);
                return true; // ONLY if more data is actually being loaded; false otherwise.
            }
        });

        //

    }

    //https://stackoverflow.com/questions/45409210/how-to-add-pagination-in-listview
    // https://benjii.me/2010/08/endless-scrolling-listview-in-android/
    // https://github.com/codepath/android_guides/wiki/Endless-Scrolling-with-AdapterViews-and-RecyclerView      ref link

    public void getTimeLineData(final String page, final int currentFirstVisibleItem) {
        try {
            showBusyProgress();
            JSONObject jo = new JSONObject();

            jo.put("UserId", sessionManager.getSessionUserId());
            jo.put("AccessToken", sessionManager.getSessionDeviceToken());
            jo.put("PageNo", page);


            GsonRequest<DieselFillingFundRequestTransaction> dieselFillingFundRequestTransactionRequest = new GsonRequest<>(Request.Method.POST, Constants.getuserdieselrequestticketlist, jo.toString(), DieselFillingFundRequestTransaction.class,
                    new Response.Listener<DieselFillingFundRequestTransaction>() {
                        @Override
                        public void onResponse(@NonNull DieselFillingFundRequestTransaction response) {
                            hideBusyProgress();
                            if (response.getError() != null) {
                                showToast(response.getError().getErrorMessage());
                            } else {
                                if (response.getSuccess() == 1) {
                                    dieselFillingFundRequestTransaction = response;
                                    if (dieselFillingFundRequestTransaction.getDiselRequestTransactionList() != null && dieselFillingFundRequestTransaction.getDiselRequestTransactionList().size() > 0) {
                                        mTxtNoTicketFound.setVisibility(View.GONE);
                                        mDieselFillingReqListListViewTickets.setVisibility(View.VISIBLE);
                                        if (dieselFillingFundRequestTransaction.getDiselRequestTransactionList().size() < 15) {
                                            loading = false;
                                        }
                                        diselRequestTransactionList.addAll(dieselFillingFundRequestTransaction.getDiselRequestTransactionList());
                                        dieselFillingFundRequestListAdapter = new DieselFillingFundRequestListAdapter(diselRequestTransactionList, DieselFillingFundReqestList.this);
                                        mDieselFillingReqListListViewTickets.setAdapter(dieselFillingFundRequestListAdapter);
                                        mDieselFillingReqListListViewTickets.setSelectionFromTop(currentFirstVisibleItem, 0);
                                        dieselFillingFundRequestListAdapter.notifyDataSetChanged();

                                    } else {
                                        if (diselRequestTransactionList.size() < 1) {
                                            mDieselFillingReqListListViewTickets.setVisibility(View.GONE);
                                            mTxtNoTicketFound.setVisibility(View.VISIBLE);
                                        }
                                    }
                                }
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error.getMessage().contains("java.net.UnknownHostException")) {
                        showToast("No Internet Connection.");
                    }
                    hideBusyProgress();

                }
            });
            dieselFillingFundRequestTransactionRequest.setRetryPolicy(Application.getDefaultRetryPolice());
            dieselFillingFundRequestTransactionRequest.setShouldCache(false);
            Application.getInstance().addToRequestQueue(dieselFillingFundRequestTransactionRequest, "dieselFillingFundRequestTransactionRequest");

        } catch (JSONException e) {
            hideBusyProgress();
            showToast("Something went wrong. Please try again later.");
        }
    }


    private void assignViews() {
        mDieselFillingReqListListViewTickets = (ListView) findViewById(R.id.listViewDieselReq);
        mTxtNoTicketFound = (TextView) findViewById(R.id.txtNoTicketFound);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_item_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.menuAdd:
                Intent intent = new Intent(DieselFillingFundReqestList.this, DieselFillingFundRequest.class);
                startActivityForResult(intent, RESULT_TRAN_SUBMIT);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            //prepareListData();
            diselRequestTransactionList = new ArrayList<DiselRequestTransactionList>();
            getTimeLineData("1", 0);
        }
    }

    private void prepareListData() {
        try {
            showBusyProgress();
            JSONObject jo = new JSONObject();

            jo.put("UserId", sessionManager.getSessionUserId());
            jo.put("AccessToken", sessionManager.getSessionDeviceToken());


            GsonRequest<DieselFillingFundRequestTransaction> dieselFillingFundRequestTransactionRequest = new GsonRequest<>(Request.Method.POST, Constants.getuserdieselrequestticketlist, jo.toString(), DieselFillingFundRequestTransaction.class,
                    new Response.Listener<DieselFillingFundRequestTransaction>() {
                        @Override
                        public void onResponse(@NonNull DieselFillingFundRequestTransaction response) {
                            hideBusyProgress();
                            //showToast(""+response.getSuccess().toString());
                            if (response.getError() != null) {
                                showToast(response.getError().getErrorMessage());
                            } else {
                                if (response.getSuccess() == 1) {
                                    dieselFillingFundRequestTransaction = response;
                                    if (dieselFillingFundRequestTransaction.getDiselRequestTransactionList() != null && dieselFillingFundRequestTransaction.getDiselRequestTransactionList().size() > 0) {
                                        mTxtNoTicketFound.setVisibility(View.GONE);
                                        mDieselFillingReqListListViewTickets.setVisibility(View.VISIBLE);
                                        ArrayList<DiselRequestTransactionList> dd = new ArrayList<DiselRequestTransactionList>(dieselFillingFundRequestTransaction.getDiselRequestTransactionList().size());
                                        dd.addAll(dieselFillingFundRequestTransaction.getDiselRequestTransactionList());
                                        dieselFillingFundRequestListAdapter = new DieselFillingFundRequestListAdapter(dd, DieselFillingFundReqestList.this);
                                        mDieselFillingReqListListViewTickets.setAdapter(dieselFillingFundRequestListAdapter);

                                    } else {
                                        mDieselFillingReqListListViewTickets.setVisibility(View.GONE);
                                        mTxtNoTicketFound.setVisibility(View.VISIBLE);
                                    }
                                }
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error.getMessage().contains("java.net.UnknownHostException")) {
                        showToast("No Internet Connection.");
                    }
                    hideBusyProgress();

                }
            });
            dieselFillingFundRequestTransactionRequest.setRetryPolicy(Application.getDefaultRetryPolice());
            dieselFillingFundRequestTransactionRequest.setShouldCache(false);
            Application.getInstance().addToRequestQueue(dieselFillingFundRequestTransactionRequest, "dieselFillingFundRequestTransactionRequest");

        } catch (JSONException e) {
            hideBusyProgress();
            showToast("Something went wrong. Please try again later.");
        }


    }

}
