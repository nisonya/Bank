package com.example.bank;

public class CardOnPost {
    private int id_individual;
    private String card_number;
    private String expriration_date;
    private String release_date;
    private String owners_name;
    private String owners_surname;
    private String CVC_code;
    private int id_account;
    private int id_payment_system;
    private int id_card_type;

    public CardOnPost(int id_individual, String card_number, String expriration_date, String release_date, String owners_name, String owners_surname, String CVC_code, int id_account, int id_payment_system, int id_card_type) {
        this.id_individual = id_individual;
        this.card_number = card_number;
        this.expriration_date = expriration_date;
        this.release_date = release_date;
        this.owners_name = owners_name;
        this.owners_surname = owners_surname;
        this.CVC_code = CVC_code;
        this.id_account = id_account;
        this.id_payment_system = id_payment_system;
        this.id_card_type = id_card_type;
    }
}
