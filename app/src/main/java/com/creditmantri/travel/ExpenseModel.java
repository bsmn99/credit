package com.creditmantri.travel;

import java.util.ArrayList;

public class ExpenseModel {

    private String ExpenseName;
    private float ExpenseAmt;
    private String PaidBy;
    private String owesBy;
    private float oweAmount;

    public String getExpenseName() {
        return ExpenseName;
    }

    public void setExpenseName(String expenseName) {
        ExpenseName = expenseName;
    }

    public float getExpenseAmt() {
        return ExpenseAmt;
    }

    public void setExpenseAmt(float expenseAmt) {
        ExpenseAmt = expenseAmt;
    }

    public String getPaidBy() {
        return PaidBy;
    }

    public void setPaidBy(String paidBy) {
        PaidBy = paidBy;
    }

    public String getOwesBy() {
        return owesBy;
    }

    public void setOwesBy(String owesBy) {
        this.owesBy = owesBy;
    }

    public float getOweAmount() {
        return oweAmount;
    }

    public void setOweAmount(float oweAmount) {
        this.oweAmount = oweAmount;
    }

}
