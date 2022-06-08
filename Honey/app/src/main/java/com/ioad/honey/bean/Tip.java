package com.ioad.honey.bean;

public class Tip {
    String tipCode;
    String cId;
    String cName;
    String tipContent;

    String menuCode;
    String menuName;
    String tipAddDay;

    public Tip(String tipCode, String cId, String cName, String tipContent) {
        this.tipCode = tipCode;
        this.cId = cId;
        this.cName = cName;
        this.tipContent = tipContent;
    }

    public Tip(String cId, String menuCode, String menuName, String tipContent, String tipAddDay) {
        this.cId = cId;
        this.tipContent = tipContent;
        this.menuCode = menuCode;
        this.menuName = menuName;
        this.tipAddDay = tipAddDay;
    }

    public String getTipCode() {
        return tipCode;
    }

    public void setTipCode(String tipCode) {
        this.tipCode = tipCode;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getTipContent() {
        return tipContent;
    }

    public void setTipContent(String tipContent) {
        this.tipContent = tipContent;
    }


    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getTipAddDay() {
        return tipAddDay;
    }

    public void setTipAddDay(String tipAddDay) {
        this.tipAddDay = tipAddDay;
    }
}
