package com.example.assignment2;

public class BloodPressureAverages {
    public String userID;
    public String userName;
    public String readingDate;
    public String readingTime;
    public int systolicReadingTotal;
    public int diastolicReadingTotal;
    public String condition;

    public BloodPressureAverages(String name, int sysTotal, int diaTotal){
        this.userName = name;
        this.diastolicReadingTotal = diaTotal;
        this.systolicReadingTotal = sysTotal;

        if (diastolicReadingTotal< 120 && systolicReadingTotal < 80){
            condition = "Normal";
        } else if (systolicReadingTotal >= 120 && systolicReadingTotal <= 129 && diastolicReadingTotal < 80 ){
            condition = "Elevated";
        } else if ((systolicReadingTotal >= 130 && systolicReadingTotal <= 139) || (diastolicReadingTotal >= 80 && diastolicReadingTotal <= 89)){
            condition = "High blood pressure: Stage 1";
        } else if (systolicReadingTotal > 180 || diastolicReadingTotal> 120) {
            condition = "Hypertensive: See Doctor immediately!";
        } else {
            condition = "High blood pressure: Stage 2";
        }
    }

    public String toString(){
        String value = "username: " + userName + ", systolicReadingTotal: " + systolicReadingTotal + ", diastolic: " + diastolicReadingTotal;
        return value;
    }
}
