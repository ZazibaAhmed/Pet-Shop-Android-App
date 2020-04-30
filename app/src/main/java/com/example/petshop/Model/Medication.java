package com.example.petshop.Model;

public class Medication {
    private String medid;
    private String medPrice;
    private String medName;
    private String medImg;
    private String medDescription;
    private int medQuantity;
    private String medType;

    public Medication(){

    }

    public Medication(String medid, String medName,String medPrice, int medQuantity,String medType
            ,String medImg,String medDescription) {
        this.medid = medid;
        this.medPrice = medPrice;
        this.medName = medName;
        this.medQuantity= medQuantity;
        this.medType= medType;
        this.medImg=medImg;
        this.medDescription=medDescription;
    }

    public String getmedid() {
        return medid;
    }

    public String getmedName() {
        return medName;
    }

    public String getmedPrice() {
        return medPrice;
    }

    public int getmedQuantity() {
        return medQuantity;
    }

    public String getmedType() {
        return medType;
    }

    public String getmedDescription() {
        return medDescription;
    }

    public String getmedImg() {
        return medImg;
    }
}
