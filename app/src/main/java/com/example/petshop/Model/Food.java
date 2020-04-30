package com.example.petshop.Model;

public class Food {

    private String foodid;
    private String foodPrice;
    private String foodName;
    private String foodImg;
    private String foodDescription;
    private int foodQuantity;
    private String foodType;

    public Food(){

    }

    public Food(String foodid, String foodName,String foodPrice, int foodQuantity,String foodType
            ,String foodImg,String foodDescription) {
        this.foodid = foodid;
        this.foodPrice = foodPrice;
        this.foodName = foodName;
        this.foodQuantity= foodQuantity;
        this.foodType= foodType;
        this.foodImg=foodImg;
        this.foodDescription=foodDescription;
    }

    public String getfoodid() {
        return foodid;
    }

    public String getfoodName() {
        return foodName;
    }

    public String getfoodPrice() {
        return foodPrice;
    }

    public int getfoodQuantity() {
        return foodQuantity;
    }

    public String getfoodType() {
        return foodType;
    }

    public String getfoodDescription() {
        return foodDescription;
    }

    public String getfoodImg() {
        return foodImg;
    }
}
