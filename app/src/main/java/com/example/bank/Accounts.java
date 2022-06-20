package com.example.bank;

public class Accounts {
    private int id_account;
    private  String frst_order_balance;
    private  String second_order_balance;
    private  String currency_cade;
    private  String control_digit;
    private  String bank_division_code;
    private  String bank_account_number;
    private  double account_balance;

    public Accounts(int id_account, String frst_order_balance, String second_order_balance, String currency_cade, String control_digit, String bank_division_code, String bank_account_number, double account_balance) {
        this.id_account = id_account;
        this.frst_order_balance = frst_order_balance;
        this.second_order_balance = second_order_balance;
        this.currency_cade = currency_cade;
        this.control_digit = control_digit;
        this.bank_division_code = bank_division_code;
        this.bank_account_number = bank_account_number;
        this.account_balance = account_balance;
    }

    public int getId_account() {
        return id_account;
    }

    public String getFrst_order_balance() {
        return frst_order_balance;
    }

    public String getSecond_order_balance() {
        return second_order_balance;
    }

    public String getCurrency_cade() {
        return currency_cade;
    }

    public String getControl_digit() {
        return control_digit;
    }

    public String getBank_division_code() {
        return bank_division_code;
    }

    public String getBank_account_number() {
        return bank_account_number;
    }

    public double getAccount_balance() {
        return account_balance;
    }

}
