package com.rn.travelcraft.parseData;

import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.Date;

public class Trip {

    private ParseUser mTraveller;
    private String mFromCity;
    private String mToCity;
    private float mFreeBaggageSpace;
    private boolean mValidated;
    private Date mArrivalDate;
    private Date mDepartureDate;

    private int mFieldSetCount;

    public Trip() {
        mTraveller = null;
        mFromCity = null;
        mToCity = null;
        mFreeBaggageSpace = 0.0f;
        mValidated = false;
        mArrivalDate = null;
        mDepartureDate = null;

        mFieldSetCount = 0;
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
        mFieldSetCount++;
    }

    public String getToCity() {
        return mToCity;
    }

    public void setToCity(String toCity) {
        mToCity = toCity;
        mFieldSetCount++;
    }

    public float getFreeBaggageSpace() {
        return mFreeBaggageSpace;
    }

    public void setFreeBaggageSpace(float freeBaggageSpace) {
        mFreeBaggageSpace = freeBaggageSpace;
        mFieldSetCount++;
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
        mFieldSetCount++;
    }

    public Date getDepartureDate() {
        return mDepartureDate;
    }

    public void setDepartureDate(Date departureDate) {
        mDepartureDate = departureDate;
        mFieldSetCount++;
    }

    //Very hackish I know. shush
    public boolean isDataComplete() {
        if (mFieldSetCount > 5)
            return true;
        else
            return false;
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
