/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siva.rabbitmqsamples;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttTopic;

public class MqttSubscriberOrClient {

    MqttClient myClient;
    MqttConnectOptions connOpt;

    static final String BROKER_URL = "tcp://localhost:1883";
    static final String M2MIO_DOMAIN = "mqtt_topic";
    static final String M2MIO_STUFF = "iot";
    static final String M2MIO_THING = "admin_vhost";
    static final String M2MIO_USERNAME = "/admin_vhost:admin";
    static final String M2MIO_PASSWORD_MD5 = "welcome01";

    /**
     *
     * MAIN
     *     
*/
    public static void main(String[] args) {
        MqttSubscriberOrClient smc = new MqttSubscriberOrClient();
     
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
            myClient.setCallback(new PayLoadMQTTTransactionWorker());
           
            myClient.connect(connOpt);
        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        System.out.println("Connected to " + BROKER_URL);

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
// wait to ensure subscribed messages are delivered
            
             //   Thread.sleep(3000);
            
            myClient.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
