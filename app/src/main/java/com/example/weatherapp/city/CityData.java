package com.example.weatherapp.city;

public class CityData {
    private int id;
    private String city;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public CityData(int id, String city) {
        this.id = id;
        this.city = city;
    }

}
