package com.ioad.honey.bean;

import android.util.Log;

public class UserInfo {
    String userId;
    String userPw;
    String userNm;
    String userPhone;
    String userEmail;

    String userPostNum;
    String userAddr;
    String userAddrDetail;
    String cartCount;

    public UserInfo(String userId, String userPw, String userNm, String userPhone, String userEmail) {
        this.userId = userId;
        this.userPw = userPw;
        this.userNm = userNm;
        this.userPhone = userPhone;
        this.userEmail = userEmail;
    }

    public UserInfo(String userId, String userPw, String userNm, String userPhone, String userPostNum, String userAddr, String userAddrDetail, String userEmail, String cartCount) {
        this.userId = userId;
        this.userPw = userPw;
        this.userNm = userNm;
        this.userPhone = userPhone;
        this.userPostNum = userPostNum;
        this.userAddr = userAddr;
        this.userAddrDetail = userAddrDetail;
        this.userEmail = userEmail;
        this.cartCount = cartCount;
    }

    public UserInfo(String userId) {
        this.userId = userId;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPw() {
        return userPw;
    }

    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }

    public String getUserNm() {
        return userNm;
    }

    public void setUserNm(String userNm) {
        this.userNm = userNm;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPostNum() {
        return userPostNum;
    }

    public void setUserPostNum(String userPostNum) {
        this.userPostNum = userPostNum;
    }

    public String getUserAddr() {
        return userAddr;
    }

    public void setUserAddr(String userAddr) {
        this.userAddr = userAddr;
    }

    public String getUserAddrDetail() {
        return userAddrDetail;
    }

    public void setUserAddrDetail(String userAddrDetail) {
        this.userAddrDetail = userAddrDetail;
    }

    public String getCartCount() {
        return cartCount;
    }

    public void setCartCount(String cartCount) {
        this.cartCount = cartCount;
    }
}
