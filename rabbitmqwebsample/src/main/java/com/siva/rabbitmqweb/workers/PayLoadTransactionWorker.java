/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siva.rabbitmqweb.workers;


import com.siva.rabbitmqweb.domain.Device;
import com.siva.rabbitmqweb.domain.PayLoad;
import com.siva.rabbitmqweb.repository.DeviceRepository;
import com.siva.rabbitmqweb.repository.PayloadRepository;
import java.util.Date;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author siva
 */
public class PayLoadTransactionWorker implements MessageListener {

    @Autowired
    Jackson2JsonMessageConverter amqpJson;

    @Autowired
    DeviceRepository deviceRepository;

    @Autowired
    PayloadRepository payloadRepository;

    public void onMessage(Message message) { 
        /*
        System.out.println("--->" + message.getMessageProperties().getConsumerQueue() + "  " 
                + message.getMessageProperties().getReceivedExchange() 
                + "---" + message.getMessageProperties().getContentType());
        System.out.println("Message received---->" + message.getClass().getName() + "--" + message.getClass().getCanonicalName());
                */ 
        
        PayLoadInTransactionRequest payLoadInTransactionRequest = (PayLoadInTransactionRequest) amqpJson.fromMessage(message);
        System.out.println("Message received." + payLoadInTransactionRequest.getDeviceId() + "---" + payLoadInTransactionRequest.getLocation());

        PayLoad payLoad = new PayLoad();
        payLoad.setDeviceId(payLoadInTransactionRequest.getDeviceId());
        payLoad.setHubId(payLoadInTransactionRequest.getHubId());
        payLoad.setLocation(payLoadInTransactionRequest.getLocation());
        payLoad.setTemporature(payLoadInTransactionRequest.getTemporature());
        payLoad.setCreatedAt(new Date());

        Device existing = deviceRepository.findOne(payLoadInTransactionRequest.getDeviceId());
        if (existing != null && existing.getDeviceId().equals(payLoadInTransactionRequest.getDeviceId())) {
            System.out.println("Device already registered.");
            payloadRepository.save(payLoad);
        } else {
            Device device = new Device();
            device.setDeviceId(payLoadInTransactionRequest.getDeviceId());
            device.setHubId(payLoadInTransactionRequest.getHubId());
            device.setLocation(payLoadInTransactionRequest.getLocation());
            device.setRegisteredAt(new Date());
            deviceRepository.save(device);
            payloadRepository.save(payLoad);
            System.out.println("Successfully registered the device.");
        }
    }
}
