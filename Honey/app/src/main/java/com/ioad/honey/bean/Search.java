package com.ioad.honey.bean;

public class Search {

    String searchSeq;
    String searchCategory;
    String searchKeyword;
    String searchImg;
    String searchDate;
    String searchImg2;
    String searchMenu;

    public Search() {
    }

    public Search(String searchSeq, String searchKeyword, String searchDate) {
        this.searchSeq = searchSeq;
        this.searchKeyword = searchKeyword;
        this.searchDate = searchDate;
    }

    public Search(String searchMenu, String searchImg) {
        this.searchMenu = searchMenu;
        this.searchImg = searchImg;
    }

    public Search(String searchSeq, String searchCategory, String searchKeyword, String searchImg, String searchDate, String searchImg2) {
        this.searchSeq = searchSeq;
        this.searchCategory = searchCategory;
        this.searchKeyword = searchKeyword;
        this.searchImg = searchImg;
        this.searchDate = searchDate;
        this.searchImg2 = searchImg2;
    }

    public String getSearchSeq() {
        return searchSeq;
    }

    public void setSearchSeq(String searchSeq) {
        this.searchSeq = searchSeq;
    }

    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    public String getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(String searchDate) {
        this.searchDate = searchDate;
    }

    public String getSearchMenu() {
        return searchMenu;
    }

    public void setSearchMenu(String searchMenu) {
        this.searchMenu = searchMenu;
    }

    public String getSearchImg() {
        return searchImg;
    }

    public void setSearchImg(String searchImg) {
        this.searchImg = searchImg;
    }

    public String getSearchCategory() {
        return searchCategory;
    }

    public void setSearchCategory(String searchCategory) {
        this.searchCategory = searchCategory;
    }

    public String getSearchImg2() {
        return searchImg2;
    }

    public void setSearchImg2(String searchImg2) {
        this.searchImg2 = searchImg2;
    }
}
