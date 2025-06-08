package com.ttknp.restapicontroller.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Address {

    private String aid;
    @JsonIgnore // ignore response
    private String eid;
    private String country;
    private String city;
    private String details; // varchar(300) can be null

    // in jdbc you have to create default constructor
    public Address() {
    }

    public Address(String aid, String country, String city, String details) {
        this.aid = aid;
        this.country = country;
        this.city = city;
        this.details = details;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    @Override
    public String toString() {
        return "Address{" +
                "aid='" + aid + '\'' +
                ", eid='" + eid + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
