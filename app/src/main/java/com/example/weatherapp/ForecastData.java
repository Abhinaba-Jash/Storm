
package com.example.weatherapp;
public class ForecastData {

    String time;
    String code;
    String description;
    String temp_max;
    String temp_min;
    ForecastData(String time, String code, String description,String temp_max,String temp_min){
        this.time=time;
        this.code=code;
        this.description=description;
        this.temp_max=temp_max;
        this.temp_min=temp_min;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(String temp_max) {
        this.temp_max = temp_max;
    }

    public String getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(String temp_min) {
        this.temp_min = temp_min;
    }
}

