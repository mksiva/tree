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
@Document(collection = "payloads")
public class PayLoad {
    @Id
    private String id;       
    private String deviceId;       
    private String hubId;
    private double temporature;
    private String location;
    private Date createdAt;

    public PayLoad() {
    }

    public PayLoad(String id,String deviceId, String hubId, double temporature, String location, Date createdAt) {
        this.id = id;
        this.deviceId = deviceId;
        this.hubId = hubId;
        this.temporature = temporature;
        this.location = location;
        this.createdAt = createdAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    
    @Override
    public String toString() {
         return String.format("PayLoad[deviceId=%s, hubId=%s, temporature='%s', location='%s', createdAt='%s']", deviceId,
				hubId, temporature, location, createdAt);
    }
}
