/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siva.rabbitmq;

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

    @Override
    public void messageArrived(String string, MqttMessage mm) throws Exception {
        System.out.println("MQTT Message received.----> " + new String(mm.getPayload()));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken imdt) {        
       // System.out.println("deliveryComplete");
    }
    
}
