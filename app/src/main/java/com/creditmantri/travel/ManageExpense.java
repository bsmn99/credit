package com.creditmantri.travel;

import java.util.ArrayList;

public class ManageExpense {

    private static ManageExpense manageExpense;

    public ArrayList<TripModel> tripModel = new ArrayList<>();

    private int currentPosition;

    private ManageExpense() {
    }

    public static ManageExpense getInstance() {
        if (manageExpense == null)
            manageExpense = new ManageExpense();

        return manageExpense;
    }

    public ArrayList<TripModel> getTripModel() {
        return tripModel;
    }

    public void setTripModel(ArrayList<TripModel> tripModel) {
        this.tripModel = tripModel;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    @Override
    public String toString() {

        return "ManageExpense{" +
                "tripModel=" + tripModel.toString() +
                ", currentPosition=" + currentPosition +
                '}';
    }
}
