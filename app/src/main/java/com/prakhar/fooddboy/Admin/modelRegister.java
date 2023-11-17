package com.prakhar.fooddboy.Admin;

public class modelRegister
{
    String name,designation,mobile,email,password,address,purl;
    modelRegister()
    {

    }
    public modelRegister(String name, String designation, String mobile,String password , String email, String address, String purl) {
        this.name = name;
        this.designation = designation;
        this.mobile = mobile;
        this.password = password;
        this.email = email;
        this.address = address;
        this.purl = purl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }
}
