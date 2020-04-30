package com.example.petshop.Model;

public class Orders {
    private String pid, name, phone, address, price;

    public Orders(String pid, String name, String phone, String address, String price) {
        this.pid = pid;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.price = price;
    }

    public Orders() {
    }

    public String getPid() {
        return pid;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getPrice() {
        return price;
    }


}



