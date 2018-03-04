package com.creditmantri.travel;

import java.util.ArrayList;

public class TripModel {

    private String TripName;
    private ArrayList<String> buddyList = new ArrayList<>();
    private ArrayList<ExpenseModel> expenseModel = new ArrayList<>();

    public String getTripName() {
        return TripName;
    }

    public void setTripName(String tripName) {
        TripName = tripName;
    }

    public ArrayList<String> getBuddyList() {
        return buddyList;
    }

    public void setBuddyList(ArrayList<String> buddyList) {
        this.buddyList = buddyList;
    }

    public ArrayList<ExpenseModel> getExpenseModel() {
        return expenseModel;
    }

    public void setExpenseModel(ArrayList<ExpenseModel> expenseModel) {
        this.expenseModel = expenseModel;
    }

}
