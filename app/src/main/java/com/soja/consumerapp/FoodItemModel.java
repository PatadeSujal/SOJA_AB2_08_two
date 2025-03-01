package com.soja.consumerapp;

import java.io.Serializable;

public class FoodItemModel implements Serializable {
    String image, name, price, description,seller;

    FoodItemModel(String n, String d, String p, String i, String s)
    {
        name = n;
        image = i;
        price = p;
        description = d;
        seller = s;
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
    public String getSeller() {return seller;}
}
