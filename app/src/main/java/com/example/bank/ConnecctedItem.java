package com.example.bank;

public class ConnecctedItem {
    private String nameOfType;
    private int iconImg;


    public ConnecctedItem(String nameOfType, int iconImg) {
        this.nameOfType = nameOfType;
        this.iconImg = iconImg;
    }

    public String getNameOfType() {
        return nameOfType;
    }

    public int getIconImg() {
        return iconImg;
    }
}
