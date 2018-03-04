package com.creditmantri.travel;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class SummaryActivity extends AppCompatActivity {

    LinearLayout summaryContainer;
    ManageExpense manageExpense;
    TextView totalExpense;
    TripModel tripModel;
    ArrayList<ExpenseModel> expenseModels;
    float totalExpenseValue;
    HashMap<String, Float> balanceSummary = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.summaryactivity);

        manageExpense = ManageExpense.getInstance();

        summaryContainer = findViewById(R.id.summaryContainer);

        totalExpense = findViewById(R.id.totalExpense);

        tripModel = manageExpense.getTripModel().get(manageExpense.getCurrentPosition());

        expenseModels = tripModel.getExpenseModel();

        calculateExpense();

        calculateBalance();

    }

    private void calculateBalance() {
        ArrayList<String> buddyNames = tripModel.getBuddyList();

        for(int i=0;i<buddyNames.size();i++) {
            float balance = 0;
            for(int j=0;j<expenseModels.size();j++) {

                String[] owesList = expenseModels.get(j).getOwesBy().split(",");

                for(int x=0;x<owesList.length;x++) {

                    String oweBy = owesList[x].replace("[","").replace("]","").trim();

                    if(buddyNames.get(i).equalsIgnoreCase(oweBy)) {
                        balance = balance + expenseModels.get(j).getOweAmount();
                    }
                }

                balanceSummary.put(buddyNames.get(i),balance);
            }
        }

        for(int y=0;y<buddyNames.size();y++) {
            setSummaryValues(buddyNames.get(y));
        }
    }

    private void setSummaryValues(String buddy) {
        LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = vi.inflate(R.layout.summaryadapter, null);

        TextView buddyName = view.findViewById(R.id.buddyName);
        TextView totalPaid = view.findViewById(R.id.totalPaid);
        TextView totalBalance = view.findViewById(R.id.totalBalance);
        TextView oweTo = view.findViewById(R.id.oweTo);

        buddyName.setText(buddy);
        float diffpaid = totalExpenseValue - balanceSummary.get(buddy);
        totalPaid.setText(String.valueOf(diffpaid));
        totalBalance.setText(String.valueOf(balanceSummary.get(buddy)));

        HashMap<String, Float> owehash = new HashMap<>();

        for(int j=0;j<expenseModels.size();j++) {

            String[] owesList = expenseModels.get(j).getOwesBy().split(",");

            for(int x=0;x<owesList.length;x++) {

                String oweBy = owesList[x].replace("[","").replace("]","").trim();

                if(buddy.equalsIgnoreCase(oweBy)) {
                    float balance = expenseModels.get(j).getOweAmount();

                    if(owehash.get(expenseModels.get(j).getPaidBy())!=null) {
                        float amt = owehash.get(buddy);
                        amt = amt + balance;

                        owehash.put(expenseModels.get(j).getPaidBy() , amt);
                    } else {
                        owehash.put(expenseModels.get(j).getPaidBy(), balance);
                    }
                }
            }
        }

        if(owehash.size() > 0) {
            Set<String> keySet = owehash.keySet();
            String oweToText = "";
            for(int i=0;i<keySet.size();i++) {
                oweToText = oweToText+" "+keySet.iterator().next()+"= "+owehash.get(keySet.iterator().next());
            }

            oweTo.setText(oweToText);
        }

        summaryContainer.addView(view);
    }

    private void calculateExpense() {

        ArrayList<ExpenseModel> arrayList = manageExpense.getTripModel().get(manageExpense.getCurrentPosition()).getExpenseModel();

        if(arrayList.size()> 0) {
            float total = 0.0f;
            for(int i=0;i<arrayList.size();i++) {
                total = total + arrayList.get(i).getExpenseAmt();
            }
            totalExpenseValue = total;
            totalExpense.setText(String.valueOf(total));
        } else {
            totalExpenseValue = 0.00f;
            totalExpense.setText("0.00");
        }

    }
}
