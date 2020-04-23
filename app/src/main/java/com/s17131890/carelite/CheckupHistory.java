package com.s17131890.carelite;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class CheckupHistory {
    private float Temperature;
    private String BloodPressure;
    private int HeartRate;
    private String Date;
    private String Time;

    public CheckupHistory(float temperature, String bloodPressure, int heartRate, String date , String time) {
        Temperature = temperature;
        BloodPressure = bloodPressure;
        HeartRate = heartRate;
        Date = date;
        Time = time;
    }

    public CheckupHistory(){

    }

    public float getTemperature() {
        return Temperature;
    }

    public void setTemperature(float temperature) {
        Temperature = temperature;
    }

    public String getBloodPressure() {
        return BloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        BloodPressure = bloodPressure;
    }

    public int getHeartRate() {
        return HeartRate;
    }

    public void setHeartRate(int heartRate) {
        HeartRate = heartRate;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public static final String DATE_FORMAT_2 = "dd-MMM-yyyy";


}
