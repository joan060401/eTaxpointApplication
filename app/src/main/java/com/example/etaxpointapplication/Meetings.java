package com.example.etaxpointapplication;

import java.util.Date;

public class Meetings {

    String title_m,location_m ,des_m, fromdate_m, todate_m,id_m;

    public String getTitle_m() {
        return title_m;
    }

    public void setTitle_m(String title_m) {
        this.title_m = title_m;
    }

    public String getLocation_m() {
        return location_m;
    }

    public void setLocation_m(String location_m) {
        this.location_m = location_m;
    }

    public String getDes_m() {
        return des_m;
    }

    public void setDes_m(String des_m) {
        this.des_m = des_m;
    }

    public String getFromdate_m() {
        return fromdate_m;
    }

    public void setFromdate_m(String fromdate_m) {
        this.fromdate_m = fromdate_m;
    }

    public String getTodate_m() {
        return todate_m;
    }

    public void setTodate_m(String todate_m) {
        this.todate_m = todate_m;
    }

    public String getId_m() {
        return id_m;
    }

    public void setId_m(String id_m) {
        this.id_m = id_m;
    }

    public Meetings(String title_m, String location_m, String des_m , String fromdate_m, String todate_m, String id_m){
        this.title_m =title_m;
        this.location_m= location_m;
        this.des_m=des_m;
        this.todate_m=todate_m;
        this.fromdate_m=fromdate_m;
        this.id_m=id_m;

    }
}