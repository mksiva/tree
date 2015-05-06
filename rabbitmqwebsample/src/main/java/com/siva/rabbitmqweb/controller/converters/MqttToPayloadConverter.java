/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siva.rabbitmqweb.controller.converters;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;

/**
 *
 * @author siva
 */
public class MqttToPayloadConverter extends DefaultPahoMessageConverter {

    @Override
    public Object mqttBytesToPayload(MqttMessage mqttMessage) throws Exception {
        return mqttMessage.getPayload(); //To change body of generated methods, choose Tools | Templates.
    }
    
}
