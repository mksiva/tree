/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siva.rabbitmqweb.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.siva.rabbitmqweb.workers.PayLoadInTransactionRequest;
import com.siva.rabbitmqweb.workers.PayLoadMQTTTransactionWorker;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author siva
 */
@Controller
public class MonitorController {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private AmqpTemplate amqpTopicTemplate;

    @Autowired
    private Queue iotQueue;

    @Autowired
    ObjectMapper jsonObjectMapper;

    @Autowired
    private PayLoadMQTTTransactionWorker payLoadMQTTTransactionWorker;

    MqttClient myClient = null;
    MqttConnectOptions connOpt;

    final String BROKER_URL_MQTT = "tcp://localhost:1883";
    final String BROKER_URL_AMQP = "localhost";
    final String M2MIO_DOMAIN = "mqtt_topic";
    final String M2MIO_STUFF = "iot";
    final String M2MIO_THING = "admin_vhost";
    final String M2MIO_USERNAME = "/admin_vhost:admin";
    final String M2MIO_PASSWORD_MD5 = "welcome01";
    final String EXCHANGE_NAME = "amq.topic";

    @RequestMapping(value = "/publish/{protocol}", method = RequestMethod.GET)
    @ResponseBody
    public String processRequest(@PathVariable String protocol) {
        String responseMessage = "";
        if ("amqp".equalsIgnoreCase(protocol)) {
            String routingKey = M2MIO_DOMAIN + "." + M2MIO_STUFF + "." + M2MIO_THING;
            String message = "MQTT Test Message from IOT by AMQP publisher.";
            amqpTopicTemplate.convertAndSend(routingKey, message);
            responseMessage = "Published to '" + routingKey + "' : '" + message + "'";
        } else if ("mqtt".equalsIgnoreCase(protocol)) {
            String pubMsg = "{\n"
                + "    \"deviceId\" : \"123\",\n"
                + "    \"hubId\" : \"123\",\n"
                + "    \"temporature\" : 34,\n"
                + "    \"location\" : \"CBE\"   \n"
                + "}";
            publishPayload(pubMsg);
            responseMessage = "Mqtt msg is published.";
        } else {
            responseMessage = "Not Supported protocol [" + protocol + "]";
        }
        return responseMessage;
    }

    @RequestMapping(value = "/subscribe", method = RequestMethod.GET)
    @ResponseBody
    public String subscribeMQTT() {
        subscribeMQTTPayload();
        return "Mqtt msg is received and logged.";
    }

    @RequestMapping(value = "/publish", method = RequestMethod.POST)
    @ResponseBody
    public String sendPayLoad(@RequestBody final PayLoadInTransactionRequest request) {
        String routingKey = M2MIO_DOMAIN + "." + M2MIO_STUFF + "." + M2MIO_THING;
        amqpTopicTemplate.convertAndSend(routingKey, request);
        return "sent";
    }

    @RequestMapping(value = "/post", method = RequestMethod.POST)
     @ResponseBody
     public String postPayLoad(@RequestBody final PayLoadInTransactionRequest request) {     
        try {
            String pubMsg = jsonObjectMapper.writeValueAsString(request);
            publishPayload(pubMsg); 
        } catch (JsonProcessingException ex) {
            Logger.getLogger(MonitorController.class.getName()).log(Level.SEVERE, null, ex);
        }
     return "Object published";
     }
    /*
     @RequestMapping(value = "/publishAMQP", method = RequestMethod.GET)
     @ResponseBody
     public String sendPayLoad1(@RequestBody final PayLoadInTransactionRequest request) {
     amqpTemplate.convertAndSend(iotQueue.getName(), request);
     return "sent";
     }

     @RequestMapping(value = "/publishAMQP", method = RequestMethod.POST)
     @ResponseBody
     public String sendPayLoad(@RequestBody final PayLoadInTransactionRequest request) {
     amqpTemplate.convertAndSend(iotQueue.getName(), request);
     return "sent";
     }
    
     @RequestMapping(value = "/publish2AMQPTopic", method = RequestMethod.GET)
     @ResponseBody
     public String publish2AMQPTopic() {        
     String routingKey = "mqtt_topic.iot.cob_vhost";
     String message = "MQTT Test Message from IOT by AMQP publisher.";
     amqpTopicTemplate.convertAndSend(routingKey, message);      
     return "published to '" + routingKey + "' : '"  + message + "'";
     }
     

     @RequestMapping(value = "/publishMQTT", method = RequestMethod.GET)
     @ResponseBody
     public String publishMQTT() {
     publishPayload();
     return "published";
     } */
    private void publishPayload(String pubMsg){
        //setup MQTT Client
        String clientID = M2MIO_THING + 1;
        connOpt = new MqttConnectOptions();
        connOpt.setCleanSession(false);
        connOpt.setKeepAliveInterval(30);
        connOpt.setUserName(M2MIO_USERNAME);
        connOpt.setPassword(M2MIO_PASSWORD_MD5.toCharArray());
        // Connect to Broker
        try {
            myClient = new MqttClient(BROKER_URL_MQTT, clientID);
            myClient.connect(connOpt);
        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        System.out.println("Connected to " + BROKER_URL_MQTT);

        // setup topic
        // topics on m2m.io are in the form <domain>/<stuff>/<thing>
        String myTopic = M2MIO_DOMAIN + "." + M2MIO_STUFF + "." + M2MIO_THING;
        MqttTopic topic = myClient.getTopic(myTopic);
        int pubQoS = 1;
        //String pubMsg = "MQTT Message from IoTWeb by MQTTPublisher";
//        String pubMsg = "{\n"
//                + "    \"deviceId\" : \"123\",\n"
//                + "    \"hubId\" : \"123\",\n"
//                + "    \"temporature\" : 34,\n"
//                + "    \"location\" : \"CBE\"   \n"
//                + "}";
        MqttMessage message = new MqttMessage(pubMsg.getBytes());
        message.setQos(pubQoS);
        message.setRetained(false);

        // Publish the message
        System.out.println("Publishing to topic \"" + topic + "\" qos " + pubQoS);
        MqttDeliveryToken token = null;
        try {
            // publish message to broker
            token = topic.publish(message);
            // Wait until the message has been delivered to the broker
            token.waitForCompletion();
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Publishing done");
        // disconnect
        try {
            // wait to ensure subscribed messages are delivered            
            Thread.sleep(2000);
            myClient.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void subscribeMQTTPayload() {
        // setup MQTT Client
        String clientID = M2MIO_THING;
        connOpt = new MqttConnectOptions();
        connOpt.setCleanSession(false);
        connOpt.setKeepAliveInterval(30);
        connOpt.setUserName(M2MIO_USERNAME);
        connOpt.setPassword(M2MIO_PASSWORD_MD5.toCharArray());

        // Connect to Broker
        try {
            myClient = new MqttClient(BROKER_URL_MQTT, clientID);
            myClient.setCallback(payLoadMQTTTransactionWorker);

            myClient.connect(connOpt);
        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        System.out.println("Connected to " + BROKER_URL_MQTT);

        // setup topic
        // topics on m2m.io are in the form <domain>/<stuff>/<thing>
        String myTopic = M2MIO_DOMAIN + "." + M2MIO_STUFF + "." + M2MIO_THING;
        MqttTopic topic = myClient.getTopic(myTopic);

        // subscribe to topic if subscriber
        try {
            int subQoS = 1;
            myClient.subscribe(myTopic, subQoS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // disconnect
        try {
            //Thread.sleep(2000);
            //myClient.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
