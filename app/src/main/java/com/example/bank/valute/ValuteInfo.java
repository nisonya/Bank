package com.example.bank.valute;

public class ValuteInfo {
    public String disclaimer;
    public int timestamp;
    public String date;
    public String RUB;
    public ValuteRate rates;

    public ValuteInfo(String disclaimer, int timestamp, String date, String RUB, ValuteRate rates) {
        this.disclaimer = disclaimer;
        this.timestamp = timestamp;
        this.date = date;
        this.RUB = RUB;
        this.rates = rates;
    }

    public String getDisclaimer() {
        return disclaimer;
    }

    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRUB() {
        return RUB;
    }

    public void setRUB(String RUB) {
        this.RUB = RUB;
    }

    public ValuteRate getRates() {
        return rates;
    }

    public void setRates(ValuteRate rates) {
        this.rates = rates;
    }
}
