package com.app.project115.Model;

public class GoldenStock extends  Stock {
    private String date;
    private  int threeDigit;
    private int twoDigit;

    public GoldenStock(String name, String date, int threeDigit, int twoDigit) {
        this.name = name;
        this.date = date;
        this.threeDigit = threeDigit;
        this.twoDigit = twoDigit;
    }

    public int getThreeDigit() {
        return threeDigit;
    }

    public int getTwoDigit() {
        return twoDigit;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }
}
