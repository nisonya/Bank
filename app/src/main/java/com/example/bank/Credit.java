package com.example.bank;

public class Credit {
    private String term_date;
    private Double total;
    private Double paid;
    private Double monthly_payment;

    public Credit(String term_date, Double total, Double paid, Double monthly_payment) {
        this.term_date = term_date;
        this.total = total;
        this.paid = paid;
        this.monthly_payment = monthly_payment;
    }

    public String getTerm_date() {
        return term_date;
    }

    public Double getTotal() {
        return total;
    }

    public Double getPaid() {
        return paid;
    }

    public Double getMonthly_payment() {
        return monthly_payment;
    }
}
