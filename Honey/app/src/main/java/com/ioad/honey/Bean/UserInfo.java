package com.ioad.honey.Bean;

public class UserInfo {
    String userId;
    String userPw;
    String userNm;
    String userPhone;
    String userEmail;

    public UserInfo(String userId, String userPw, String userNm, String userPhone, String userEmail) {
        this.userId = userId;
        this.userPw = userPw;
        this.userNm = userNm;
        this.userPhone = userPhone;
        this.userEmail = userEmail;
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
}
