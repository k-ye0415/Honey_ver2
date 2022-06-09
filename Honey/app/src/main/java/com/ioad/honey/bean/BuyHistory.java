package com.ioad.honey.bean;

public class BuyHistory {

    String buyNm;
    String buyDeliveryPrice;
    String buyDay;
    String buyCancelDay;
    String iName;
    String iCapacity;
    String iUnit;
    String count;

    public BuyHistory(String buyNm, String buyDeliveryPrice, String buyDay, String buyCancelDay, String iName, String iCapacity, String iUnit, String count) {
        this.buyNm = buyNm;
        this.buyDeliveryPrice = buyDeliveryPrice;
        this.buyDay = buyDay;
        this.buyCancelDay = buyCancelDay;
        this.iName = iName;
        this.iCapacity = iCapacity;
        this.iUnit = iUnit;
        this.count = count;
    }


    public String getBuyNm() {
        return buyNm;
    }

    public void setBuyNm(String buyNm) {
        this.buyNm = buyNm;
    }

    public String getBuyDeliveryPrice() {
        return buyDeliveryPrice;
    }

    public void setBuyDeliveryPrice(String buyDeliveryPrice) {
        this.buyDeliveryPrice = buyDeliveryPrice;
    }

    public String getBuyDay() {
        return buyDay;
    }

    public void setBuyDay(String buyDay) {
        this.buyDay = buyDay;
    }

    public String getBuyCancelDay() {
        return buyCancelDay;
    }

    public void setBuyCancelDay(String buyCancelDay) {
        this.buyCancelDay = buyCancelDay;
    }

    public String getiName() {
        return iName;
    }

    public void setiName(String iName) {
        this.iName = iName;
    }

    public String getiCapacity() {
        return iCapacity;
    }

    public void setiCapacity(String iCapacity) {
        this.iCapacity = iCapacity;
    }

    public String getiUnit() {
        return iUnit;
    }

    public void setiUnit(String iUnit) {
        this.iUnit = iUnit;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
