package com.example.wear;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class CheckupHistory {
    private float Temperature;
    private int BloodPressure;
    private int HeartRate;
    private String Date;
    private String Time;

    public CheckupHistory(float temperature, int bloodPressure, int heartRate) {
        Temperature = temperature;
        BloodPressure = bloodPressure;
        HeartRate = heartRate;
        Date = getCurrentDate();
        Time = getCurrentTime();
    }

    public CheckupHistory(){

    }

    public float getTemperature() {
        return Temperature;
    }

    public void setTemperature(float temperature) {
        Temperature = temperature;
    }

    public int getBloodPressure() {
        return BloodPressure;
    }

    public void setBloodPressure(int bloodPressure) {
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

    public static String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_2);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date today = Calendar.getInstance().getTime();
        return dateFormat.format(today);
    }

    public static final String DATE_FORMAT_1 = "hh:mm a";

    public static String getCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_1);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date today = Calendar.getInstance().getTime();
        return dateFormat.format(today);
    }
}
