/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siva.rabbitmq;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/**
 * 
 * @author siva 
 */
public class MqttMsgPublisher {

    MqttClient myClient;
    MqttConnectOptions connOpt;

    static final String BROKER_URL = "tcp://localhost:1883";
    static final String M2MIO_DOMAIN = "mqtt_topic";
    static final String M2MIO_STUFF = "iot";
    static final String M2MIO_THING = "admin_vhost";
    static final String M2MIO_USERNAME = "/admin_vhost:admin";
    static final String M2MIO_PASSWORD_MD5 = "welcome01";

// the following two flags control whether this example is a publisher, a subscriber or both
    static Boolean subscriber = false;
    static Boolean publisher = true;

    /**
     *
     * MAIN
     *     
*/
    public static void main(String[] args) {
        MqttMsgPublisher smc = new MqttMsgPublisher();
        smc.runClient();
    }

    /**
     *
     * runClient The main functionality of this simple example. Create a MQTT
     * client, connect to broker, pub/sub, disconnect.
     *     
*/
    public void runClient() {
// setup MQTT Client
        String clientID = M2MIO_THING;
        connOpt = new MqttConnectOptions();
        connOpt.setCleanSession(false);
        connOpt.setKeepAliveInterval(30);
        connOpt.setUserName(M2MIO_USERNAME);
        connOpt.setPassword(M2MIO_PASSWORD_MD5.toCharArray());

// Connect to Broker
        try {
            myClient = new MqttClient(BROKER_URL, clientID);
            //myClient.setCallback(new PayLoadMQTTTransactionWorker3());
            myClient.connect(connOpt);
        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        System.out.println("Connected to " + BROKER_URL);

// setup topic
// topics on m2m.io are in the form <domain>.<stuff>.<thing>
        String myTopic = M2MIO_DOMAIN + "." + M2MIO_STUFF + "." + M2MIO_THING;
        MqttTopic topic = myClient.getTopic(myTopic);

// subscribe to topic if subscriber
        if (subscriber) {
            try {
                int subQoS = 1;
                myClient.subscribe(myTopic, subQoS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //publisher = false;
// publish messages if publisher
        if (publisher) {
            for (int i = 1; i <= 10; i++) {
                String pubMsg = "{\"pubmsgeee\":" + i + "}";
                int pubQoS = 1;
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
            }
        }
// disconnect
        try {
// wait to ensure subscribed messages are delivered
            if (subscriber) {
                Thread.sleep(3000);
            }
            myClient.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
