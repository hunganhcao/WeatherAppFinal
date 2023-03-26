package com.example.weatherapp.mainweather;

public class MainWeather {
    private String imgMW;

    private String dayMW;

    private String tempMW;

    public String getImgMW() {
        return imgMW;
    }

    public void setImgMW(String imgMW) {
        this.imgMW = imgMW;
    }

    public String getDayMW() {
        return dayMW;
    }

    public void setDayMW(String dayMW) {
        this.dayMW = dayMW;
    }

    public String getTempMW() {
        return tempMW;
    }

    public void setTempMW(String tempMW) {
        this.tempMW = tempMW;
    }

    public MainWeather(String imgMW, String dayMW, String tempMW) {
        this.imgMW = imgMW;
        this.dayMW = dayMW;
        this.tempMW = tempMW;
    }
}
