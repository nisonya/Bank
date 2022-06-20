package com.example.bank.registr;

public class Register {
    private int id_individual;
    private String login;
    private String pass;

    public Register(int id_individual, String login, String pass) {
        this.id_individual = id_individual;
        this.login = login;
        this.pass = pass;
    }

    public int getId_individual() {
        return id_individual;
    }

    public String getLogin() {
        return login;
    }

    public String getPass() {
        return pass;
    }
}
