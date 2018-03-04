package com.creditmantri.travel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ManageExpense manageExpense;
    EditText buddyEdit, tripName;
    ListView listView;
    Button addTripButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manageExpense = ManageExpense.getInstance();

        buddyEdit = findViewById(R.id.buddy_edit);
        tripName = findViewById(R.id.tripNameEditText);
        listView = findViewById(R.id.tripList);
        addTripButton = findViewById(R.id.addTripButton);

        if(manageExpense.getTripModel().size()>0) {
            listView.setVisibility(View.VISIBLE);

            findViewById(R.id.tripAddView).setVisibility(View.GONE);

            setAdapterView();
        } else {
            listView.setVisibility(View.GONE);

            findViewById(R.id.tripAddView).setVisibility(View.VISIBLE);
        }

        addTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doAdd();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                manageExpense.setCurrentPosition(position);

                Intent expenseintent = new Intent(MainActivity.this, ExpenseActivity.class);
                startActivity(expenseintent);
            }
        });
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
                listView.setVisibility(View.GONE);
                findViewById(R.id.tripAddView).setVisibility(View.VISIBLE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void clear() {
        buddyEdit.setText("");
        tripName.setText("");
    }

    private void doAdd() {
        TripModel tripModel = new TripModel();

        tripModel.setTripName(tripName.getText().toString());
        String[] buddys = buddyEdit.getText().toString().split(",");

        ArrayList<String> buddysArray = new ArrayList<>();
        buddysArray.addAll(Arrays.asList(buddys));

        tripModel.setBuddyList(buddysArray);

        ArrayList<TripModel> tripModelArrayList = manageExpense.getTripModel();

        tripModelArrayList.add(tripModel);

        manageExpense.setTripModel(tripModelArrayList);

        listView.setVisibility(View.VISIBLE);
        findViewById(R.id.tripAddView).setVisibility(View.GONE);

        setAdapterView();
    }

    public void setAdapterView() {
        TripAdapter tripAdapter = new TripAdapter(MainActivity.this);

        listView.setAdapter(tripAdapter);
    }
}
