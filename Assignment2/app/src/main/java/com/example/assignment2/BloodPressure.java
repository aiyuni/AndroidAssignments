package com.example.assignment2;

import android.app.AlertDialog;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;

public class BloodPressure {

    public String userID;
    public String userName;
    public String readingDate;
    public String readingTime;
    public int systolicReading;
    public int diastolicReading;
    public String condition;
    public int month;

    public BloodPressure(){

    }

    public BloodPressure(String userId, String userName, String readingDate, String readingTime, int systolicReading, int diastolicReading){
        this.userID = userId;
        this.userName = userName;
        this.readingDate = readingDate;
        this.readingTime = readingTime;
        this.systolicReading = systolicReading;
        this.diastolicReading = diastolicReading;

        /*try {
            Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(readingDate);
            month = date1.getMonth();
        } catch (Exception e){
            System.out.println("something went wrong: " + e.toString());
        } */

        if (systolicReading < 120 && diastolicReading < 80){
            condition = "Normal";
        } else if (systolicReading >= 120 && systolicReading <= 129 && diastolicReading < 80 ){
            condition = "Elevated";
        } else if ((systolicReading >= 130 && systolicReading <= 139) || (diastolicReading >= 80 && diastolicReading <= 89)){
            condition = "High blood pressure: Stage 1";
        } else if (systolicReading > 180 || diastolicReading > 120) {
            condition = "Hypertensive: See Doctor immediately!";
        } else {
            condition = "High blood pressure: Stage 2";
        }
    }


}
