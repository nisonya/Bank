package com.example.bank;


public class ClientOnPost {

    private String First_name;
    private String Surname;
    private String Last_name;
    private String Phone_code;
    private String Phone_number;
    private String Birth_day;
    private String id_gender;
    private String email;

    public ClientOnPost(String first_name, String surname, String last_name, String phone_code, String phone_number, String birth_day, String id_gender, String email) {
        this.First_name = first_name;
        this.Surname = surname;
        this.Last_name = last_name;
        this.Phone_code = phone_code;
        this.Phone_number = phone_number;
        this.Birth_day = birth_day;
        this.id_gender = id_gender;
        this.email = email;
    }
}
