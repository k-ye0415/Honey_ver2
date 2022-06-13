package com.ioad.honey.bean;

public class BuyDetail {

    private String ingredientName;
    private String ingredientCapacity;
    private String ingredientUnit;
    private String ingredientPrice;
    private String menuName;
    private String menuImage;
    private String buyPostNum;
    private String buyAddr;
    private String buyAddrDetail;
    private String buyRequest;
    private String buyDeliveryPrice;
    private int buyEA;
    private String buyDate;
    private String buyCancelDate;
    private String count;

    public BuyDetail(String ingredientName, String ingredientCapacity, String ingredientUnit, String buyPostNum,
                     String buyAddr, String buyAddrDetail, String buyRequest, String buyDeliveryPrice, String count) {
        this.ingredientName = ingredientName;
        this.ingredientCapacity = ingredientCapacity;
        this.ingredientUnit = ingredientUnit;
        this.buyPostNum = buyPostNum;
        this.buyAddr = buyAddr;
        this.buyAddrDetail = buyAddrDetail;
        this.buyRequest = buyRequest;
        this.buyDeliveryPrice = buyDeliveryPrice;
        this.count = count;
    }


    public BuyDetail(String ingredientName, String ingredientCapacity, String ingredientUnit, String ingredientPrice, String menuName, String menuImage, String buyPostNum, String buyAddr, String buyAddrDetail, String buyRequest, String buyDeliveryPrice, int buyEA, String buyDate, String buyCancelDate) {
        this.ingredientName = ingredientName;
        this.ingredientCapacity = ingredientCapacity;
        this.ingredientUnit = ingredientUnit;
        this.ingredientPrice = ingredientPrice;
        this.menuName = menuName;
        this.menuImage = menuImage;
        this.buyPostNum = buyPostNum;
        this.buyAddr = buyAddr;
        this.buyAddrDetail = buyAddrDetail;
        this.buyRequest = buyRequest;
        this.buyDeliveryPrice = buyDeliveryPrice;
        this.buyEA = buyEA;
        this.buyDate = buyDate;
        this.buyCancelDate = buyCancelDate;
    }


    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public String getIngredientCapacity() {
        return ingredientCapacity;
    }

    public void setIngredientCapacity(String ingredientCapacity) {
        this.ingredientCapacity = ingredientCapacity;
    }

    public String getIngredientUnit() {
        return ingredientUnit;
    }

    public void setIngredientUnit(String ingredientUnit) {
        this.ingredientUnit = ingredientUnit;
    }

    public String getIngredientPrice() {
        return ingredientPrice;
    }

    public void setIngredientPrice(String ingredientPrice) {
        this.ingredientPrice = ingredientPrice;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuImage() {
        return menuImage;
    }

    public void setMenuImage(String menuImage) {
        this.menuImage = menuImage;
    }

    public String getBuyPostNum() {
        return buyPostNum;
    }

    public void setBuyPostNum(String buyPostNum) {
        this.buyPostNum = buyPostNum;
    }

    public String getBuyAddr() {
        return buyAddr;
    }

    public void setBuyAddr(String buyAddr) {
        this.buyAddr = buyAddr;
    }

    public String getBuyAddrDetail() {
        return buyAddrDetail;
    }

    public void setBuyAddrDetail(String buyAddrDetail) {
        this.buyAddrDetail = buyAddrDetail;
    }

    public String getBuyRequest() {
        return buyRequest;
    }

    public void setBuyRequest(String buyRequest) {
        this.buyRequest = buyRequest;
    }

    public String getBuyDeliveryPrice() {
        return buyDeliveryPrice;
    }

    public void setBuyDeliveryPrice(String buyDeliveryPrice) {
        this.buyDeliveryPrice = buyDeliveryPrice;
    }

    public int getBuyEA() {
        return buyEA;
    }

    public void setBuyEA(int buyEA) {
        this.buyEA = buyEA;
    }

    public String getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(String buyDate) {
        this.buyDate = buyDate;
    }

    public String getBuyCancelDate() {
        return buyCancelDate;
    }

    public void setBuyCancelDate(String buyCancelDate) {
        this.buyCancelDate = buyCancelDate;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
