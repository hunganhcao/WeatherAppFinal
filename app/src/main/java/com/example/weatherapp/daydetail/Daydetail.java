package com.example.weatherapp.daydetail;

public class Daydetail {

    private String date;
    private String maxtemp;
    private String mintemp;
    private String windspeed;
    private String uv;
    private String rainchance;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMaxtemp() {
        return maxtemp;
    }

    public void setMaxtemp(String maxtemp) {
        this.maxtemp = maxtemp;
    }

    public String getMintemp() {
        return mintemp;
    }

    public void setMintemp(String mintemp) {
        this.mintemp = mintemp;
    }

    public String getWindspeed() {
        return windspeed;
    }

    public void setWindspeed(String windspeed) {
        this.windspeed = windspeed;
    }

    public String getUv() {
        return uv;
    }

    public void setUv(String uv) {
        this.uv = uv;
    }

    public String getRainchance() {
        return rainchance;
    }

    public void setRainchance(String rainchance) {
        this.rainchance = rainchance;
    }

    public Daydetail(String date, String maxtemp, String mintemp, String windspeed, String uv, String rainchance) {
        this.date = date;
        this.maxtemp = maxtemp;
        this.mintemp = mintemp;
        this.windspeed = windspeed;
        this.uv = uv;
        this.rainchance = rainchance;
    }
}
