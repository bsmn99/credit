package com.creditmantri.travel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TripAdapter extends BaseAdapter {

    Context context;

    ArrayList<TripModel> tripModels;

    public TripAdapter(Context context) {
        this.context = context;
        tripModels = ManageExpense.getInstance().getTripModel();
    }

    @Override
    public int getCount() {
        return ManageExpense.getInstance().getTripModel().size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }



    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.tripadapter, null, false);

        TextView tripName = rowView.findViewById(R.id.tripName);
        TextView tripList = rowView.findViewById(R.id.tripBuddyList);

        tripName.setText(tripModels.get(position).getTripName());
        tripList.setText(tripModels.get(position).getBuddyList().toString().replace("[","").replace("]",""));

        return rowView;
    }
}
