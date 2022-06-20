package com.example.bank.history;

public class HistoryItem {
    private int id_change;
    private double diff;
    private double Balance_after;
    private String date_of_change;
    private int id_operation_type;

    public HistoryItem(Integer id_change, double diff, double balance_after, String date_of_change, int id_operation_type) {
        this.id_change = id_change;
        this.diff = diff;
        Balance_after = balance_after;
        this.date_of_change = date_of_change;
        this.id_operation_type = id_operation_type;
    }

    public Integer getId_change() {
        return id_change;
    }

    public int getTrans_type() {
        return id_operation_type;
    }

    public void setTrans_type(int trans_type) {
        this.id_operation_type = trans_type;
    }

    public double getDiff() {
        return diff;
    }

    public void setDiff(double diff) {
        this.diff = diff;
    }

    public double getBalance_after() {
        return Balance_after;
    }

    public void setBalance_after(double balance_after) {
        Balance_after = balance_after;
    }

    public String getDate_of_change() {
        return date_of_change;
    }

    public void setDate_of_change(String date_of_change) {
        this.date_of_change = date_of_change;
    }
}
