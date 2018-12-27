package com.brahamaputra.mahindra.brahamaputra.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.brahamaputra.mahindra.brahamaputra.Adapters.DieselTrasactionAdapter;
import com.brahamaputra.mahindra.brahamaputra.Adapters.NotificationListAdapter;
import com.brahamaputra.mahindra.brahamaputra.Application;
import com.brahamaputra.mahindra.brahamaputra.Data.Notification;
import com.brahamaputra.mahindra.brahamaputra.R;
import com.brahamaputra.mahindra.brahamaputra.Utils.Constants;
import com.brahamaputra.mahindra.brahamaputra.Utils.SessionManager;
import com.brahamaputra.mahindra.brahamaputra.Volley.GsonRequest;
import com.brahamaputra.mahindra.brahamaputra.baseclass.BaseActivity;
import com.brahamaputra.mahindra.brahamaputra.commons.AlertDialogManager;
import com.brahamaputra.mahindra.brahamaputra.commons.OfflineStorageWrapper;
import com.brahamaputra.mahindra.brahamaputra.helper.DatabaseHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NotificationList extends BaseActivity {

    private OfflineStorageWrapper offlineStorageWrapper;
    private String userId = "";
    private String ticketName = "";
    private String ticketId = "";
    private Notification notification;
    private SessionManager sessionManager;

    private ListView mNotificationListView;
    private TextView mTxtNoNotificationFound;
    private AlertDialogManager alertDialogManager;
    private NotificationListAdapter notificationListAdapter;
    private DatabaseHelper databaseHelper;
    public static final int RESULT_TRAN_SUBMIT = 259;

    private void assignViews() {
        mNotificationListView = (ListView) findViewById(R.id.listViewNotification);
        mTxtNoNotificationFound = (TextView) findViewById(R.id.txtNoNotificationFound);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_list);
        this.setTitle("Notification List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        alertDialogManager = new AlertDialogManager(NotificationList.this);
        sessionManager = new SessionManager(NotificationList.this);
        userId = sessionManager.getSessionUserId();
        //offlineStorageWrapper = OfflineStorageWrapper.getInstance(NotificationList.this, userId, ticketName);
        notification = new Notification();
        databaseHelper = new DatabaseHelper(getApplicationContext());
        assignViews();
        prepareListData();

        // ListView on item selected listener.
        mNotificationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Notification dataModel = dd.get(position);
                int b4 = dataModel.getIsRead();
                databaseHelper = new DatabaseHelper(getApplicationContext());
                databaseHelper.updateNotification(dataModel);

                //dd.set(position,dataModel);
                notificationListAdapter.notifyDataSetChanged();
               prepareListData();
            }
        });

    }

    ArrayList<Notification> dd;

    private void prepareListData() {
        if (databaseHelper.getAllNotification() != null && databaseHelper.getAllNotification().size() > 0) {
            dd = new ArrayList<Notification>(databaseHelper.getAllNotification().size());
            dd.addAll(databaseHelper.getAllNotification());
            notificationListAdapter = new NotificationListAdapter(dd, NotificationList.this);
            mNotificationListView.setAdapter(notificationListAdapter);
        } else {
            mNotificationListView.setVisibility(View.GONE);
            mTxtNoNotificationFound.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.notification_icon_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.menuRefresh:

                prepareListData();
                return true;

            case R.id.menuDeleteAll:
                deleteNotificationConfirmBox();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }

    private void deleteNotificationConfirmBox() {
        alertDialogManager = new AlertDialogManager(NotificationList.this);
        alertDialogManager.Dialog("Confirmation", "Do you want to delete all notification?", "Yes", "No", new AlertDialogManager.onTwoButtonClickListner() {
            @Override
            public void onPositiveClick() {
                //showSettingsAlert();
                databaseHelper.deleteAllNotification();
                prepareListData();
            }

            @Override
            public void onNegativeClick() {

            }
        }).show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            prepareListData();
        }
    }

}
