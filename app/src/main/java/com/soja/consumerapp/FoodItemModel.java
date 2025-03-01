package com.soja.consumerapp;

public class FoodItemModel {
    String image, name, price, description;

    FoodItemModel(String n, String i, String p, String d)
    {
        name = n;
        image = i;
        price = p;
        description = d;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }
}
