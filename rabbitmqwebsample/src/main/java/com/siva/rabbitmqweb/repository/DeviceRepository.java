/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siva.rabbitmqweb.repository;


import com.siva.rabbitmqweb.domain.Device;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author siva
 */
@Repository
public interface DeviceRepository extends MongoRepository<Device, String>{    
}
