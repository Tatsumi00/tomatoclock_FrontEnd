package com.njupt.qmj.tomatotodo.LitePalDataBase;

import org.litepal.crud.LitePalSupport;

public class TodoDataBase extends LitePalSupport {
    private String name;
    private String time;
    private int backgorund;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getBackgorund() {
        return backgorund;
    }

    public void setBackgorund(int backgorund) {
        this.backgorund = backgorund;
    }

}
