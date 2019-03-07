package com.example.crni.testnotifying.Data;

public class MyAlarm {
    private  String alarmID;
    private int imageResource;
    private String textTitle;
    private String textMsg;

    public MyAlarm(String id, int imgRes, String title, String msg) {
        alarmID = id;
        imageResource = imgRes;
        textTitle = title;
        textMsg = msg;
    }

    public String getId() {
        return alarmID;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getText1() {
        return textTitle;
    }

    public String getText2() {
        return textMsg;
    }

    public void setId(String id) {
        this.alarmID = id;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public void setTextTitle(String textTitle) {
        this.textTitle = textTitle;
    }

    public void setTextMsg(String textMsg) {
        this.textMsg = textMsg;
    }
}
