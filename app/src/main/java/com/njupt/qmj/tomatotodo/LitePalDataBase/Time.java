package com.njupt.qmj.tomatotodo.LitePalDataBase;

import org.litepal.crud.LitePalSupport;

public class Time extends LitePalSupport {
    private String todoName;
    private int todoTime;

    public Time(String todoName, int todoTime){
        this.todoName = todoName;
        this.todoTime = todoTime;
    }

    public String getTodoName() {
        return todoName;
    }

    public void setTodoName(String todoName) {
        this.todoName = todoName;
    }

    public int getTodoTime() {
        return todoTime;
    }

    public void setTodoTime(int todoTime) {
        this.todoTime = todoTime;
    }
}
