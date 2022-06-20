package com.example.bank;

public class Deposits {
    private double total;
    private double Replenishment;
    private String Term_date;

    public Deposits(double total, double replenishment, String term_date) {
        this.total = total;
        Replenishment = replenishment;
        Term_date = term_date;
    }

    public double getTotal() {
        return total;
    }

    public double getReplenishment() {
        return Replenishment;
    }

    public String getTerm_date() {
        return Term_date;
    }
}
