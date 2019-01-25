package com.brahamaputra.mahindra.brahamaputra.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.brahamaputra.mahindra.brahamaputra.Data.DiselFillingTransactionList;
import com.brahamaputra.mahindra.brahamaputra.Data.DiselRequestTransactionList;
import com.brahamaputra.mahindra.brahamaputra.R;

import java.util.ArrayList;

public class DieselFillingFundRequestListAdapter extends ArrayAdapter<DiselRequestTransactionList>
        implements View.OnClickListener {

    private ArrayList<DiselRequestTransactionList> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView textView_RequesttTicketNo;
        TextView textView_SiteName;
        TextView textView_DateOfRequest;
        TextView textView_SiteAddress;
        TextView textView_RequestQty;
    }

    public DieselFillingFundRequestListAdapter(ArrayList<DiselRequestTransactionList> data, Context context) {
        super(context, R.layout.item_diesel_trasaction_list, data);
        this.dataSet = data;
        this.mContext = context;
    }

    @Override
    public void onClick(View v) {

        int position = (Integer) v.getTag();
        Object object = getItem(position);
        DiselRequestTransactionList dataModel = (DiselRequestTransactionList) object;
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        DiselRequestTransactionList dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_diesel_filling_request_fund_trasaction_list, parent, false);

            viewHolder.textView_RequesttTicketNo = (TextView) convertView.findViewById(R.id.textView_RequesttTicketNo);
            viewHolder.textView_SiteName = (TextView) convertView.findViewById(R.id.textView_SiteName);
            viewHolder.textView_DateOfRequest = (TextView) convertView.findViewById(R.id.textView_DateOfRequest);
            viewHolder.textView_SiteAddress = (TextView) convertView.findViewById(R.id.textView_SiteAddress);
            viewHolder.textView_RequestQty = (TextView) convertView.findViewById(R.id.textView_RequestQty);

            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.textView_RequesttTicketNo.setText("Ticket No:" + dataModel.getDiselRequesttTicketNo());
        viewHolder.textView_SiteName.setText("Site Name:" + dataModel.getSiteName());
        viewHolder.textView_DateOfRequest.setText("Date:" + dataModel.getDateOfRequest());
        viewHolder.textView_SiteAddress.setText("Site Address:" + dataModel.getSiteAddress());
        viewHolder.textView_RequestQty.setText("Diesel Filling Qty:" + dataModel.getDieselQuantityRequiredinLtrs());

        return convertView;
    }
}