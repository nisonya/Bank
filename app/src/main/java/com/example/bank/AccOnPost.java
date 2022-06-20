package com.example.bank;

public class AccOnPost {
    private int id_individual;
    private String open_date;
    private String frst_order_balance;
    private String second_order_balance;
    private String currency_cade;
    private String control_digit;
    private String bank_division_code;
    private String bank_account_number;

    public AccOnPost(int id_individual, String open_date, String frst_order_balance, String second_order_balance, String currency_cade, String control_digit, String bank_division_code, String bank_account_number) {
        this.id_individual = id_individual;
        this.open_date = open_date;
        this.frst_order_balance = frst_order_balance;
        this.second_order_balance = second_order_balance;
        this.currency_cade = currency_cade;
        this.control_digit = control_digit;
        this.bank_division_code = bank_division_code;
        this.bank_account_number = bank_account_number;
    }
}
