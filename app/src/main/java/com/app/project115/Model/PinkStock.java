package com.app.project115.Model;

public class PinkStock extends Stock {
    private String date;
    private String winingNumberType;
    private String winingNumber;
    private int threeDigits;
    private int twoDigtis;
    private String colorCode;


    public PinkStock(String date, String name, int threeDigits, int twoDigtis,String winingNumberType,String colorCode) {
        this.date = date;
        this.name = name;
        this.threeDigits = threeDigits;
        this.twoDigtis = twoDigtis;
        this.winingNumberType=winingNumberType;
        this.colorCode=colorCode;
    }

    public void setWiningNumber(String winingNumber) {
        this.winingNumber = winingNumber;
    }

    public String getColorCode() {
        return colorCode;
    }

    public String getDate() {
        return date;
    }

    public String getWiningNumberType() {
        return winingNumberType;
    }

    public String getWiningNumber() {
        return winingNumber;
    }

    public int getThreeDigits() {
        return threeDigits;
    }

    public int getTwoDigtis() {
        return twoDigtis;
    }
    public String getName(){
        return this.name;
    }
}
