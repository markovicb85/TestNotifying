package com.example.crni.testnotifying.Data;

public class MyNotification {

    private String _notificationID;
    private String _notificationTitle;
    private String _notificationBody;

    public MyNotification() {
    }

    public MyNotification(String notificationID, String notificationTitle, String notificationBody) {
        this._notificationID = notificationID;
        this._notificationTitle = notificationTitle;
        this._notificationBody = notificationBody;
    }

    public String get_notificationID() {
        return _notificationID;
    }

    public void set_notificationID(String _notificationID) {
        this._notificationID = _notificationID;
    }

    public String get_notificationTitle() {
        return _notificationTitle;
    }

    public String get_notificationBody() {
        return _notificationBody;
    }

    public void set_notificationTitle(String _notificationTitle) {
        this._notificationTitle = _notificationTitle;
    }

    public void set_notificationBody(String _notificationBody) {
        this._notificationBody = _notificationBody;
    }
}
