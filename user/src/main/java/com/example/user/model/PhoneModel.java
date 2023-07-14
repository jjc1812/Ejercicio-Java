package com.example.user.model;

import javax.persistence.*;

@Entity
public class PhoneModel {    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "phone_number")
    private long number;
    @Column(name = "phone_city_code")
    private int city_code;
    @Column(name = "phone_country_code")
    private String country_code;

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public int getCityCode() {
        return city_code;
    }

    public void setCityCode(int city_code) {
        this.city_code = city_code;
    }

    public String getCountryCode() {
        return country_code;
    }

    public void setCountryCode(String country_code) {
        this.country_code = country_code;
    }
}
