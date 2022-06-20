package com.example.bank.adress;

public class Adress {
    private int id_adress;
    private String contry;
    private String region;
    private String city;
    private String street;
    private String house;
    private String corps;
    private String flat;
    private String postcode;

    public Adress(int id_adress, String contry, String region, String city, String street, String house, String corps, String flat, String postcode) {
        this.id_adress = id_adress;
        this.contry = contry;
        this.region = region;
        this.city = city;
        this.street = street;
        this.house = house;
        this.corps = corps;
        this.flat = flat;
        this.postcode = postcode;
    }

    public int getId_adress() {
        return id_adress;
    }

    public String getContry() {
        return contry;
    }

    public String getRegion() {
        return region;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getHouse() {
        return house;
    }

    public String getCorps() {
        return corps;
    }

    public String getFlat() {
        return flat;
    }

    public String getPostcode() {
        return postcode;
    }
}
