package com.rn.travelcraft.parseData;

import com.parse.ParseFile;

public class Product {

    private String mName;
    private double mCost;
    private double mWeight;
    private ParseFile mImage;
    private String parseId;

    public Product(String name, double cost, double weight, ParseFile image) {
        mName = name;
        mCost = cost;
        mWeight = weight;
        mImage = image;
    }

    public String getParseId() { return parseId; }

    public void setParseId(String id) { parseId = id; }

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
