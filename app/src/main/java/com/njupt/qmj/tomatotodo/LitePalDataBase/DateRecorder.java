package com.njupt.qmj.tomatotodo.LitePalDataBase;

import org.litepal.crud.LitePalSupport;

public class DateRecorder extends LitePalSupport {
    private int year;
    private int month;
    private int day;
    private int OnceTime;
    private int repeatTimes;
    private String name;

    public DateRecorder(int year, int month, int day, int OnceTIme, int repeatTime, String name){
        this.year= year;
        this.month = month;
        this.day = day;
        this.OnceTime = OnceTIme;
        this.repeatTimes = repeatTime;
        this.name = name;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    public int getOnceTime() {
        return OnceTime;
    }

    public int getRepeatTimes() {
        return repeatTimes;
    }

    public void setOnceTime(int onceTime) {
        OnceTime = onceTime;
    }

    public void setRepeatTimes(int repeatTimes) {
        this.repeatTimes = repeatTimes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
