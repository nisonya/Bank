package com.example.bank.history;

public class HistoryPeriod {
    private String id_client;
    private String first_date;
    private String last_date;

    public HistoryPeriod(String id_client, String first_date, String last_date) {
        this.id_client = id_client;
        this.first_date = first_date;
        this.last_date = last_date;
    }
}
