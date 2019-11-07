package com.example.assignment2;

import android.app.AlertDialog;

import java.util.Date;

public class BloodPressure {

    public String userID;
    public String readingDate;
    public String readingTime;
    public int systolicReading;
    public int diastolicReading;
    public String condition;

    public BloodPressure(){

    }

    public BloodPressure(String userId, String readingDate, String readingTime, int systolicReading, int diastolicReading){
        this.userID = userId;
        this.readingDate = readingDate;
        this.readingTime = readingTime;
        this.systolicReading = systolicReading;
        this.diastolicReading = diastolicReading;

        if (systolicReading < 120 && diastolicReading < 180){
            condition = "Normal";
        } else if (systolicReading >= 120 && systolicReading <= 129 && diastolicReading < 80 ){
            condition = "Elevated";
        } else if ((systolicReading >= 130 && systolicReading <= 139) || (diastolicReading >= 80 && diastolicReading <= 89)){
            condition = "High blood pressure: Stage 1";
        } else if (systolicReading > 180 && diastolicReading > 120) {
            condition = "Hypertensive: See Doctor immediately!";
        } else {
            condition = "High blood pressure: Stage 2";
        }
    }


}
