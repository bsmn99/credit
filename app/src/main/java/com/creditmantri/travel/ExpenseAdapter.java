package com.creditmantri.travel;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ExpenseAdapter extends BaseAdapter {

    Context context;

    ArrayList<ExpenseModel> expenseModels;

    int selectedTrip;

    ManageExpense manageExpense;

    TripModel tripModel;

    public ExpenseAdapter(Context context) {

        this.context = context;

        selectedTrip = ManageExpense.getInstance().getCurrentPosition();

        expenseModels = ManageExpense.getInstance().getTripModel().get(selectedTrip).getExpenseModel();

        manageExpense =ManageExpense.getInstance();

        tripModel = manageExpense.getTripModel().get(manageExpense.getCurrentPosition());
    }

    @Override
    public int getCount() {
        return ManageExpense.getInstance().getTripModel().get(selectedTrip).getExpenseModel().size();
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
        View rowView = inflater.inflate(R.layout.expenseadapter, null, false);

        TextView expenseName = rowView.findViewById(R.id.expenseName);
        TextView expenseAmt = rowView.findViewById(R.id.expenseAmt);
        TextView paidBy = rowView.findViewById(R.id.paidBy);
        TextView OwesBy = rowView.findViewById(R.id.OwesBy);

        ExpenseModel expenseModel = expenseModels.get(position);

        expenseName.setText(expenseModel.getExpenseName());
        expenseAmt.setText(String.valueOf(expenseModel.getExpenseAmt()));
        paidBy.setText(expenseModel.getPaidBy());

         String[] oweArray = expenseModel.getOwesBy().split(",");

         ArrayList<String> list = new ArrayList<>();
         for(int i=0;i<oweArray.length;i++) {
             if(!TextUtils.isEmpty(oweArray[i].replace("[","").replace("]","").trim())) {
                 list.add(oweArray[i]);
             }
         }

        int oweCount = list.size();

        int totalTripMembsCount = tripModel.getBuddyList().size();

        float totalExp = expenseModel.getExpenseAmt();

        float diff = totalExp / totalTripMembsCount;

        diff = Math.round(diff);

        String oweText = "";
        for (String name: list) {
            oweText = oweText+" "+name+"= "+String.valueOf(diff);
        }

        OwesBy.setText(oweText);

        return rowView;
    }
}
