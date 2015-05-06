/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siva.rabbitmqweb.repository;


import com.siva.rabbitmqweb.domain.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author siva
 */
@Repository
public interface UsersRepository extends MongoRepository<Users, String>{
   
    Users findByFirstname(@Param ("firstname") String firstName);
}
