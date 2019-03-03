package com.example.crni.testnotifying;

public class Notification {

    private String _notificationTitle;
    private String _notificationBody;

    public Notification() {
    }

    public Notification(String notificationTitle, String notificationBody) {
        this._notificationTitle = notificationTitle;
        this._notificationBody = notificationBody;
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
