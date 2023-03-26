package com.example.weatherapp;

public class Weather {
    private String City;
    private String detail;
    private String Temp;

    public Weather(String city, String detail, String temp) {
        City = city;
        this.detail = detail;
        Temp = temp;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTemp() {
        return Temp;
    }

    public void setTemp(String temp) {
        Temp = temp;
    }
}
