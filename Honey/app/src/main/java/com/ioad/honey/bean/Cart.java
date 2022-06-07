package com.ioad.honey.bean;

public class Cart {

    private int cartCode;
    private int cartEA;
    private String iName;
    private String iCapacity;
    private String iUnit;
    private int iPrice;
    private String mName;
    private String mImagePath;

    public Cart(int cartCode, int cartEA, String iName, String iCapacity, String iUnit, int iPrice, String mName, String mImagePath) {
        this.cartCode = cartCode;
        this.cartEA = cartEA;
        this.iName = iName;
        this.iCapacity = iCapacity;
        this.iUnit = iUnit;
        this.iPrice = iPrice;
        this.mName = mName;
        this.mImagePath = mImagePath;
    }

    public int getCartCode() {
        return cartCode;
    }

    public void setCartCode(int cartCode) {
        this.cartCode = cartCode;
    }

    public int getCartEA() {
        return cartEA;
    }

    public void setCartEA(int cartEA) {
        this.cartEA = cartEA;
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

    public int getiPrice() {
        return iPrice;
    }

    public void setiPrice(int iPrice) {
        this.iPrice = iPrice;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmImagePath() {
        return mImagePath;
    }

    public void setmImagePath(String mImagePath) {
        this.mImagePath = mImagePath;
    }
}
