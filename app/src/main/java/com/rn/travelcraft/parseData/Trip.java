package com.rn.travelcraft.parseData;

import com.parse.ParseObject;
import com.parse.ParseUser;

import java.lang.reflect.Field;
import java.util.Date;

public class Trip {

    private ParseUser mTraveller;
    private String mFromCity;
    private String mToCity;
    private String mFreeBaggageSpace;
    private boolean mValidated;
    private Date mArrivalDate;
    private Date mDepartureDate;

    public Trip() {
        mTraveller = null;
        mFromCity = null;
        mToCity = null;
        mFreeBaggageSpace = null;
        mValidated = false;
        mArrivalDate = null;
        mDepartureDate = null;
    }

    public ParseUser getTraveller() {
        return mTraveller;
    }

    public void setTraveller(ParseUser traveller) {
        mTraveller = traveller;
    }

    public String getFromCity() {
        return mFromCity;
    }

    public void setFromCity(String fromCity) {
        mFromCity = fromCity;
    }

    public String getToCity() {
        return mToCity;
    }

    public void setToCity(String toCity) {
        mToCity = toCity;
    }

    public String getFreeBaggageSpace() {
        return mFreeBaggageSpace;
    }

    public void setFreeBaggageSpace(String freeBaggageSpace) {
        mFreeBaggageSpace = freeBaggageSpace;
    }

    public boolean isValidated() {
        return mValidated;
    }

    public void setValidated(boolean validated) {
        mValidated = validated;
    }

    public Date getArrivalDate() {
        return mArrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        mArrivalDate = arrivalDate;
    }

    public Date getDepartureDate() {
        return mDepartureDate;
    }

    public void setDepartureDate(Date departureDate) {

        mDepartureDate = departureDate;
    }

    public boolean isDataComplete() {
        for (Field field : getClass().getDeclaredFields()) {
            //if(field.getType().equals(String) && field.get)
        }
        return true;
    }

    public void parseUpload() {
        ParseObject trip = new ParseObject("Trips");
        trip.put("traveller", mTraveller);
        trip.put("fromCity", mFromCity);
        trip.put("toCity", mToCity);
        trip.put("freeBaggageSpace", mFreeBaggageSpace);
        trip.put("validated", mValidated);
        trip.put("departureDate", mDepartureDate);
        trip.put("arrivalDate", mArrivalDate);
        trip.saveInBackground();
    }
}
