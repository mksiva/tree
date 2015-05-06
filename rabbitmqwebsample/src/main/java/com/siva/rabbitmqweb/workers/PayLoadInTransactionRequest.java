package com.siva.rabbitmqweb.workers;

public class PayLoadInTransactionRequest {

    private String deviceId;       
    private String hubId;
    private double temporature;
    private String location;

    public PayLoadInTransactionRequest() {
    }

    public PayLoadInTransactionRequest(String deviceId, String hubId, double temporature, String location) {
        this.deviceId = deviceId;
        this.hubId = hubId;
        this.temporature = temporature;
        this.location = location;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getHubId() {
        return hubId;
    }

    public void setHubId(String hubId) {
        this.hubId = hubId;
    }

    public double getTemporature() {
        return temporature;
    }

    public void setTemporature(double temporature) {
        this.temporature = temporature;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    

}
