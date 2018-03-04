package com.creditmantri.travel;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ExpenseActivity extends AppCompatActivity {

    ListView expenseList;
    ManageExpense manageExpense;
    LinearLayout add_expenseLayout;
    Button saveExpense;
    EditText expenseNameEditText, expenseAmtEditText;
    AutoCompleteTextView paidByAutoComplete;
    MultiAutoCompleteTextView OwesByAutoComplete;
    RelativeLayout totalExpenseLayout;
    TextView totalExpense;
    TripModel tripModel;
    Button summaryBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expenseactivity);
        manageExpense = ManageExpense.getInstance();
        expenseList = findViewById(R.id.expenseList);
        add_expenseLayout = findViewById(R.id.add_expenseLayout);
        saveExpense = findViewById(R.id.saveExpense);
        expenseNameEditText = findViewById(R.id.expenseNameEditText);
        expenseAmtEditText = findViewById(R.id.expenseAmtEditText);
        paidByAutoComplete = findViewById(R.id.paidByAutoComplete);
        OwesByAutoComplete = findViewById(R.id.OwesByAutoComplete);
        totalExpenseLayout = findViewById(R.id.totalExpenseLayout);
        totalExpense = findViewById(R.id.totalExpense);
        summaryBtn = findViewById(R.id.summaryBtn);

        tripModel = manageExpense.getTripModel().get(manageExpense.getCurrentPosition());

        if(tripModel.getExpenseModel().size()>0) {
            setAdapterView();

            totalExpenseLayout.setVisibility(View.VISIBLE);
            calculateExpense();
        } else {
            totalExpenseLayout.setVisibility(View.GONE);
            expenseList.setVisibility(View.GONE);
            add_expenseLayout.setVisibility(View.VISIBLE);
            setAutoCompleteAdapters();
        }

        saveExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExpenseModel expenseModel = new ExpenseModel();

                expenseModel.setExpenseName(expenseNameEditText.getText().toString());
                expenseModel.setExpenseAmt(Float.parseFloat(expenseAmtEditText.getText().toString()));
                expenseModel.setPaidBy(paidByAutoComplete.getText().toString());
                expenseModel.setOwesBy(OwesByAutoComplete.getText().toString());

                calculateOweAmt(expenseModel);

                ArrayList<ExpenseModel> expenseModelArrayList = new ArrayList<>();

                expenseModelArrayList = manageExpense.getTripModel().get(manageExpense.getCurrentPosition()).getExpenseModel();
                expenseModelArrayList.add(expenseModel);

                manageExpense.getTripModel().get(manageExpense.getCurrentPosition()).setExpenseModel(expenseModelArrayList);

                setAdapterView();
                calculateExpense();
            }
        });

        summaryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent summaryAcivity = new Intent(ExpenseActivity.this,SummaryActivity.class);
                startActivity(summaryAcivity);
            }
        });
    }

    private void calculateOweAmt(ExpenseModel expenseModel) {
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

        expenseModel.setOweAmount(diff);
    }

    private void calculateExpense() {

        ArrayList<ExpenseModel> arrayList = manageExpense.getTripModel().get(manageExpense.getCurrentPosition()).getExpenseModel();

        if(arrayList.size()> 0) {
            float total = 0.0f;
            for(int i=0;i<arrayList.size();i++) {
                total = total + arrayList.get(i).getExpenseAmt();
            }

            totalExpense.setText(String.valueOf(total));
        } else {
            totalExpense.setText("0.00");
        }

    }

    private void setAdapterView() {
        expenseList.setVisibility(View.VISIBLE);
        add_expenseLayout.setVisibility(View.GONE);
        totalExpenseLayout.setVisibility(View.VISIBLE);

        ExpenseAdapter expenseAdapter = new ExpenseAdapter(ExpenseActivity.this);

        expenseList.setAdapter(expenseAdapter);

    }

    private void setAutoCompleteAdapters() {
        ArrayList<String> paidBy = new ArrayList<>();

        paidBy = manageExpense.getTripModel().get(manageExpense.getCurrentPosition()).getBuddyList();

        ArrayAdapter<String> paidByAdapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, paidBy);

        paidByAutoComplete.setAdapter(paidByAdapter);
        paidByAutoComplete.setThreshold(1);
        paidByAutoComplete.setTextColor(Color.BLACK);

        ArrayAdapter<String> OweByAdapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, paidBy);

        OwesByAutoComplete.setAdapter(OweByAdapter);
        OwesByAutoComplete.setThreshold(1);
        OwesByAutoComplete.setTextColor(Color.BLACK);
        OwesByAutoComplete.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_expense_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                clear();
                expenseList.setVisibility(View.GONE);
                add_expenseLayout.setVisibility(View.VISIBLE);
                setAutoCompleteAdapters();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void clear() {
        expenseNameEditText.setText("");
        expenseAmtEditText.setText("");
        paidByAutoComplete.setText("");
        OwesByAutoComplete.setText("");
    }

}
