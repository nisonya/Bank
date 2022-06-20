package com.example.bank.passport;

public class PassportData {
    private int Series_passport;
    private String number_passport;
    private String date_of_issue;
    private String passport_information;

    public PassportData(int series_passport, String number_passport, String date_of_issue, String passport_information) {
        Series_passport = series_passport;
        this.number_passport = number_passport;
        this.date_of_issue = date_of_issue;
        this.passport_information = passport_information;
    }

    public int getSeries_passport() {
        return Series_passport;
    }

    public String getNumber_passport() {
        return number_passport;
    }

    public String getDate_of_issue() {
        return date_of_issue;
    }

    public String getPassport_information() {
        return passport_information;
    }
}
