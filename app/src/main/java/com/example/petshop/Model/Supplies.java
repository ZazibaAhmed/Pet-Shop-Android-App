package com.example.petshop.Model;

public class Supplies {

    private String supid;
    private String supPrice;
    private String supName;
    private String supImg;
    private String supDescription;
    private int supQuantity;
    private String supType;

    public Supplies(){

    }

    public Supplies(String supid, String supName,String supPrice, int supQuantity,String supType
            ,String supImg,String supDescription) {
        this.supid = supid;
        this.supPrice = supPrice;
        this.supName = supName;
        this.supQuantity= supQuantity;
        this.supType= supType;
        this.supImg=supImg;
        this.supDescription=supDescription;
    }

    public String getsupid() {
        return supid;
    }

    public String getsupName() {
        return supName;
    }

    public String getsupPrice() {
        return supPrice;
    }

    public int getsupQuantity() {
        return supQuantity;
    }

    public String getsupType() {
        return supType;
    }

    public String getsupDescription() {
        return supDescription;
    }

    public String getsupImg() {
        return supImg;
    }
}

