package com.rn.travelcraft.parseData;

import com.parse.ParseFile;

public class Charity {

    private String mName;
    private String mDescription;
    private ParseFile mLogo;
    private ParseFile mBackground;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public ParseFile getLogo() {
        return mLogo;
    }

    public void setLogo(ParseFile logo) {
        mLogo = logo;
    }

    public ParseFile getBackground() {
        return mBackground;
    }

    public void setBackground(ParseFile background) {
        mBackground = background;
    }
}
