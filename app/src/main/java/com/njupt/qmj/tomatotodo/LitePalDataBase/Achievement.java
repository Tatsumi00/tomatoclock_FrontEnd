package com.njupt.qmj.tomatotodo.LitePalDataBase;

import org.litepal.crud.LitePalSupport;

public class Achievement extends LitePalSupport {
    private String achievementName;
    private String achievementTime;
    private int achievementPoint;
    private int achievementImageid;
    private String achievementIntro;

    public Achievement(String achievementName, String achievementTime, int achievementPoint, int achievementImageid,String achievementIntro){
        this.achievementName = achievementName;
        this.achievementTime = achievementTime;
        this.achievementPoint = achievementPoint;
        this.achievementImageid = achievementImageid;
        this.achievementIntro = achievementIntro;
    }

    public int getAchievementPoint() {
        return achievementPoint;
    }

    public String getAchievementName() {
        return achievementName;
    }

    public String getAchievementTime() {
        return achievementTime;
    }

    public void setAchievementName(String achievementName) {
        this.achievementName = achievementName;
    }

    public void setAchievementPoint(int achievementPoint) {
        this.achievementPoint = achievementPoint;
    }

    public void setAchievementTime(String achievementTime) {
        this.achievementTime = achievementTime;
    }

    public int getAchievementImageid() {
        return achievementImageid;
    }

    public void setAchievementImageid(int achievementImageid) {
        this.achievementImageid = achievementImageid;
    }

    public void setAchievementIntro(String achievementIntro) {
        this.achievementIntro = achievementIntro;
    }

    public String getAchievementIntro() {
        return achievementIntro;
    }
}
