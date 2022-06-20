package com.example.bank;

public class Cards {
    private String card_number;
    private Double Balance ;
    private int id_card_type;
    private int id_card;
    private String card_type;
    private int id_payment_system;

    public Cards(String card_number, Double balance, int id_card_type, int id_card, int id_payment_system) {
        this.card_number = card_number;
        Balance = balance;
        this.id_card_type = id_card_type;
        this.id_card = id_card;
        this.id_payment_system = id_payment_system;
    }

    public int getId_payment_system() {
        return id_payment_system;
    }

    public String getCard_number() {
        return card_number;
    }

    public Double getBalance() {
        return Balance;
    }

    public int getId_card_type() {
        return id_card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public String getCard_type() {
        return card_type;
    }

    public int getId_card() {
        return id_card;
    }

    public void setId_card(int id_card) {
        this.id_card = id_card;
    }
}
