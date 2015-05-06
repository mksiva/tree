/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siva.rabbitmqweb.repository;


import com.siva.rabbitmqweb.domain.PayLoad;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author siva
 */
public interface PayloadRepository extends MongoRepository<PayLoad, String>{
    
}
