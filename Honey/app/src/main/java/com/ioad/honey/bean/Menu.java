package com.ioad.honey.bean;

public class Menu {

    String code;
    String category;
    String name;
    String image1;
    String image2;

    public Menu(String code, String category, String name, String image1) {
        this.code = code;
        this.category = category;
        this.name = name;
        this.image1 = image1;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }
}
