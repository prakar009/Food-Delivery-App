package com.prakhar.fooddboy.Model;

public class CompanyInfo {
    private String canme;
    private String veg;
    private String north_indian;
    private String rating1;
    private String rating2;


    public CompanyInfo(){

    }

    public CompanyInfo(String canme, String veg, String north_indian, String rating1, String rating2) {
        this.canme = canme;
        this.veg = veg;
        this.north_indian = north_indian;
        this.rating1 = rating1;
        this.rating2 = rating2;
    }

    public String getCanme() {
        return canme;
    }

    public void setCanme(String canme) {
        this.canme = canme;
    }

    public String getVeg() {
        return veg;
    }

    public void setVeg(String veg) {
        this.veg = veg;
    }

    public String getNorth_indian() {
        return north_indian;
    }

    public void setNorth_indian(String north_indian) {
        this.north_indian = north_indian;
    }

    public String getRating1() {
        return rating1;
    }

    public void setRating1(String rating1) {
        this.rating1 = rating1;
    }

    public String getRating2() {
        return rating2;
    }

    public void setRating2(String rating2) {
        this.rating2 = rating2;
    }
}
