package com.prakhar.fooddboy.Admin;

public class model
{
    static String filename;
    static String fileurl;
    static String name;

    static int nod;
    static int nol;
    static int nov;
    private String title;
    private String downloadUrl;
    private String userId;

    public model(String s, String toString, int i, int i1, int i2) {
    }

    public model(String filename, String fileurl,String name, int nod, int nol, int nov) {
        this.filename = filename;
        this.fileurl = fileurl;
        this.name = name;
        this.nod = nod;
        this.nol = nol;
        this.nov = nov;
        this.title = title;
        this.downloadUrl = downloadUrl;
        this.userId = userId;
    }

    public static String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public static String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public static String getFileurl() {
        return fileurl;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
    }

    public static int getNod() {
        return nod;
    }

    public void setNod(int nod) {
        this.nod = nod;
    }

    public static int getNol() {
        return nol;
    }

    public void setNol(int nol) {
        this.nol = nol;
    }

    public static int getNov() {
        return nov;
    }

    public void setNov(int nov) {
        this.nov = nov;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
