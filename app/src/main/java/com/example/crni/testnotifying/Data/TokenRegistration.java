package com.example.crni.testnotifying.Data;

public class TokenRegistration {

    private String deviceName;
    private String tokenID;

    public TokenRegistration() {
    }

    public TokenRegistration(String deviceName, String tokenID) {
        this.deviceName = deviceName;
        this.tokenID = tokenID;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getTokenID() {
        return tokenID;
    }
}
