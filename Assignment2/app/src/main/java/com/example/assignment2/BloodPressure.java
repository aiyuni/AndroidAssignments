package com.example.assignment2;

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
        condition = "hard coded";
    }


}
