/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siva.iot.domain.dto;

public class UsersDTO {

    private String firstname;
    private String lastname;
 
    public UsersDTO() {}
 
    public UsersDTO(String firstName, String lastName) {
        this.firstname = firstName;
        this.lastname = lastName;
    }
 
 
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstname;
	}
 
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstname = firstName;
	}
 
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastname;
	}
 
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastname = lastName;
	}
 
}