package com.example.bank.client;

import java.util.Date;

public class Client {
    private int id_individual;
    private String First_name;
    private String Surname;
    private String Last_name;
    private String Phone_code;
    private String Phone_number;
    private Date Birth_day;
    private int id_Adress;
    private String id_gender;
    private String email;
    private String profile_pic_url;


    public Client(int id_individual, String first_name, String surname, String last_name, String phone_code, String phone_number, Date birth_day, int id_Adress, String id_gender, String email, String profile_pic_url) {
        this.id_individual = id_individual;
        First_name = first_name;
        Surname = surname;
        Last_name = last_name;
        Phone_code = phone_code;
        Phone_number = phone_number;
        Birth_day = birth_day;
        this.id_Adress = id_Adress;
        this.id_gender = id_gender;
        this.email = email;
        this.profile_pic_url = profile_pic_url;
    }
    public String getProfile_pic() {return profile_pic_url; }

    public void setProfile_pic(String profile_pic) { this.profile_pic_url = profile_pic;   }

    public int getId_individual() {
        return id_individual;
    }

    public void setId_individual(int id_individual) {
        this.id_individual = id_individual;
    }

    public String getFirst_name() {
        return First_name;
    }

    public void setFirst_name(String first_name) {
        First_name = first_name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public String getLast_name() {
        return Last_name;
    }

    public void setLast_name(String last_name) {
        Last_name = last_name;
    }

    public String getPhone_code() {
        return Phone_code;
    }

    public void setPhone_code(String phone_code) {
        Phone_code = phone_code;
    }

    public String getPhone_number() {
        return Phone_number;
    }

    public void setPhone_number(String phone_number) {
        Phone_number = phone_number;
    }

    public Date getBirth_day() {
        return Birth_day;
    }

    public void setBirth_day(Date birth_day) {
        Birth_day = birth_day;
    }

    public int getId_Adress() {
        return id_Adress;
    }

    public void setId_Adress(int id_Adress) {
        this.id_Adress = id_Adress;
    }

    public String getId_gender() {
        return id_gender;
    }

    public void setId_gender(String id_gender) {
        this.id_gender = id_gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
