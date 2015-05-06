/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siva.rabbitmqweb.domain;

import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author siva
 */
@Document(collection = "devices")
public class Device {
    @Id
    private String deviceId;    
    private String deviceName;
    
    private String hubId;
    private String location;
    private Date registeredAt;
    

    public Device() {
    }

    public Device(String deviceId, String deviceName, String hubId, String location,Date registeredAt) {
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.hubId = hubId;
        this.location = location;
        this.registeredAt = registeredAt;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getHubId() {
        return hubId;
    }

    public void setHubId(String hubId) {
        this.hubId = hubId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(Date registeredAt) {
        this.registeredAt = registeredAt;
    }
    
    @Override
    public String toString() {
        return String.format("Device[deviceId=%s, deviceName='%s', hubId='%s', location='%s']", deviceId,
				deviceName, hubId, location);
    }    
}
