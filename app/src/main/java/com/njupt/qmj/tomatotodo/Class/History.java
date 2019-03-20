package com.njupt.qmj.tomatotodo.Class;

public class History {

    private String name;

    private String intro;

    public History(String name, String intro){
        this.name = name;
        this.intro = intro;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
}
