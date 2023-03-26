package com.example.weatherapp.detail;

public class Detail {
    private String hours;
    private String img;
    private String title;

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Detail(String hours, String img, String title) {
        this.hours = hours;
        this.img = img;
        this.title = title;
    }
}
