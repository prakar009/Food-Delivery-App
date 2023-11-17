package com.prakhar.fooddboy.Marketing;


public class modelUpload {
    private  String purl;
    private String userNameEditText;


    modelUpload() {


    }

    public modelUpload(String purl, String name) {
        this.purl = purl;
        this.userNameEditText = name;
    }

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }

    public String getName() {
        return userNameEditText;
    }

    public void setName(String name) {
        this.userNameEditText = name;
    }
}