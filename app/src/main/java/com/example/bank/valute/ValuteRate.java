package com.example.bank.valute;

public class ValuteRate {
    private float GBP;
    private float USD;
    private float EUR;

    public ValuteRate(float GBP, float USD, float EUR) {
        this.GBP = GBP;
        this.USD = USD;
        this.EUR = EUR;
    }

    public float getGBP() {
        return GBP;
    }

    public void setGBP(float GBP) {
        this.GBP = GBP;
    }

    public float getUSD() {
        return USD;
    }

    public void setUSD(float USD) {
        this.USD = USD;
    }

    public float getEUR() {
        return EUR;
    }

    public void setEUR(float EUR) {
        this.EUR = EUR;
    }
}
