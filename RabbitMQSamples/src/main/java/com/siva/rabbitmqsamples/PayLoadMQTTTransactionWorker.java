/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siva.rabbitmqsamples;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author siva
 */
public class PayLoadMQTTTransactionWorker implements MqttCallback {

    @Override
    public void connectionLost(Throwable thrwbl) {
        System.out.println("connectionLost");        
    }

    private ObjectMapper jsonObjectMapper = new ObjectMapper();
    
    @Override
    public void messageArrived(String string, MqttMessage mqttMessage) throws Exception {             
        try {
             PayLoadInTransactionRequest request = jsonObjectMapper.readValue(mqttMessage.getPayload(), 
                PayLoadInTransactionRequest.class);                  
             System.out.println("Payload received from the device:" + request.getDeviceId());
        } catch (Exception e) {
            //System.out.println(e.getMessage());
            System.out.println("Unknown message format received.");
        }
       
        System.out.println("MQTT Message received.----> " + new String(mqttMessage.getPayload()));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken imdt) {        
       // System.out.println("deliveryComplete");
    }
    
}
