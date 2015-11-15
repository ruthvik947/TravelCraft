package com.rn.travelcraft.parseData;

import com.parse.ParseFile;

public class Product {

    private String mName;
    private double mCost;
    private double mWeight;
    private ParseFile mImage;

    public Product() {

    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public double getWeight() {
        return mWeight;
    }

    public void setWeight(double weight) {
        this.mWeight = weight;
    }

    public ParseFile getImage() {
        return mImage;
    }

    public void setImage(ParseFile image) {
        this.mImage = image;
    }

    public double getCost() {
        return mCost;
    }

    public void setCost(double cost) {
        this.mCost = cost;
    }

}
