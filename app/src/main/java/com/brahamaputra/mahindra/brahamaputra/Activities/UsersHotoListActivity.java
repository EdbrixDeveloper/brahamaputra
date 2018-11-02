package com.brahamaputra.mahindra.brahamaputra.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;

import com.brahamaputra.mahindra.brahamaputra.Adapters.UserHotoExpListAdapter;
import com.brahamaputra.mahindra.brahamaputra.Adapters.UserHotoListAdapter;
import com.brahamaputra.mahindra.brahamaputra.Data.HotoListHeader;
import com.brahamaputra.mahindra.brahamaputra.Data.HotoListTiketData;
import com.brahamaputra.mahindra.brahamaputra.R;
import com.brahamaputra.mahindra.brahamaputra.baseclass.BaseActivity;
import com.brahamaputra.mahindra.brahamaputra.commons.AlertDialogManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UsersHotoListActivity extends BaseActivity {

    private UserHotoListAdapter mAdapter;
    private UserHotoExpListAdapter userHotoExpListAdapter;
    public ExpandableListView userHotoList_listView_hotoList;
    private List<HotoListHeader> hotoListHeaders;
    private HashMap<Object, List<HotoListTiketData>> hotoListTiketDataMap;
    private AlertDialogManager alertDialogManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_hoto_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        alertDialogManager = new AlertDialogManager(UsersHotoListActivity.this);

        userHotoList_listView_hotoList = (ExpandableListView) findViewById(R.id.userHotoList_listView_hotoList);

        prepareListData();


        userHotoList_listView_hotoList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                userHotoList_listView_hotoList.expandGroup(groupPosition);
                return true;
            }
        });
        userHotoList_listView_hotoList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, final int childPosition, long id) {
                // notify user
                /*AlertDialog.Builder dialog = new AlertDialog.Builder(UsersHotoListActivity.this);
                dialog.setMessage("Open Ticket");
                dialog.setPositiveButton("Do you want to open this ticket ?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        startActivity(new Intent(UsersHotoListActivity.this,UserHotoTransactionActivity.class));
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        // TODO Auto-generated method stub
                    }
                });
                dialog.show();*/


                alertDialogManager.Dialog("Confirmation", "Continue to open this ticket?", "OK", "CANCEL", new AlertDialogManager.onTwoButtonClickListner() {
                    @Override
                    public void onNegativeClick() {

                    }

                    @Override
                    public void onPositiveClick() {
                        Intent intent = new Intent(UsersHotoListActivity.this, UserHotoTransactionActivity.class);
                        intent.putExtra("ticketID", "TKT_"+childPosition);
                        startActivity(intent);
                    }
                }).show();
                return false;
            }
        });
    }


    private void prepareListData() {
        hotoListHeaders = new ArrayList<>();
        hotoListTiketDataMap = new HashMap<Object, List<HotoListTiketData>>();

        // Adding child data
        List<HotoListTiketData> header1  = new ArrayList<HotoListTiketData>();
        header1.add(new HotoListTiketData("TKT_26_121","Closed"));
        header1.add(new HotoListTiketData("TKT_26_122","Closed"));
        header1.add(new HotoListTiketData("TKT_26_123","Open"));
        hotoListHeaders.add(new HotoListHeader("26 Oct 2018", ""+header1.size()));

        List<HotoListTiketData> header2  = new ArrayList<HotoListTiketData>();
        header2.add(new HotoListTiketData("TKT_27_131","Closed"));
        header2.add(new HotoListTiketData("TKT_27_132","Closed"));
        header2.add(new HotoListTiketData("TKT_27_133","Closed"));
        header2.add(new HotoListTiketData("TKT_27_134","Closed"));
        header2.add(new HotoListTiketData("TKT_27_135","Closed"));
        hotoListHeaders.add(new HotoListHeader("27 Oct 2018", ""+header2.size()));

        List<HotoListTiketData> header3  = new ArrayList<HotoListTiketData>();
        header3.add(new HotoListTiketData("TKT_28_121","Closed"));
        header3.add(new HotoListTiketData("TKT_28_122","Closed"));
        header3.add(new HotoListTiketData("TKT_28_123","Closed"));
        hotoListHeaders.add(new HotoListHeader("28 Oct 2018", ""+header3.size()));

        List<HotoListTiketData> header4  = new ArrayList<HotoListTiketData>();
        header4.add(new HotoListTiketData("TKT_29_121","Closed"));
        header4.add(new HotoListTiketData("TKT_29_122","Closed"));
        header4.add(new HotoListTiketData("TKT_29_123","Closed"));
        header4.add(new HotoListTiketData("TKT_29_124","Closed"));
        hotoListHeaders.add(new HotoListHeader("29 Oct 2018", ""+header4.size()));

        hotoListTiketDataMap.put(hotoListHeaders.get(0), header1); // Header, Child data
        hotoListTiketDataMap.put(hotoListHeaders.get(1), header2);
        hotoListTiketDataMap.put(hotoListHeaders.get(2), header3);
        hotoListTiketDataMap.put(hotoListHeaders.get(3), header4);

        userHotoExpListAdapter = new UserHotoExpListAdapter(UsersHotoListActivity.this,hotoListHeaders,hotoListTiketDataMap);

        userHotoList_listView_hotoList.setAdapter(userHotoExpListAdapter);

        for(int i=0; i<hotoListHeaders.size();i++){
            userHotoList_listView_hotoList.expandGroup(i);
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