/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siva.rabbitmqweb.workers;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author siva
 */
public class PayLoadMQTTTransactionWorker implements MqttCallback {

    @Autowired
    ObjectMapper jsonObjectMapper;
     
    @Override
    public void connectionLost(Throwable thrwbl) {
        System.out.println("connectionLost");        
    }

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
       
        
        System.out.println("MQTT Message received @ PayLoadMQTTTransactionWorker --->" + new String(mqttMessage.getPayload()));
    }
    
    @Override
    public void deliveryComplete(IMqttDeliveryToken imdt) {        
        //System.out.println("PayLoadMQTTTransactionWorker--> deliveryComplete");
    }
    
}
