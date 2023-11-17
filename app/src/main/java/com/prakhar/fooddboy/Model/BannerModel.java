package com.prakhar.fooddboy.Model;

public class BannerModel
{
    String purl;
    BannerModel()
    {

    }
    public BannerModel(String purl) {

        this.purl = purl;
    }


    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }
}
