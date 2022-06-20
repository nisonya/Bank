package com.example.bank;

public class ExtractItem {
    private String date_of_change;
    private Double balance_after;
    private Double diff;
    private String Surname;
    private String First_name;
    private String Last_name;
    private String card_number;

    public ExtractItem(String date_of_change, Double balance_after, Double diff, String card_number, String surname, String first_name, String last_name) {
        this.date_of_change = date_of_change;
        this.balance_after = balance_after;
        this.diff = diff;
        this.card_number = card_number;
        Surname = surname;
        First_name = first_name;
        Last_name = last_name;
    }

    public String getDate_of_change() {
        return date_of_change;
    }

    public Double getBalance_after() {
        return balance_after;
    }

    public Double getDiff() {
        return diff;
    }

    public String getCard_number() {
        return card_number;
    }

    public String getSurname() {
        return Surname;
    }

    public String getFirst_name() {
        return First_name;
    }

    public String getLast_name() {
        return Last_name;
    }
}
