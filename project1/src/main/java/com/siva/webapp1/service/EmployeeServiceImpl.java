/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siva.webapp1.service;

import com.siva.webapp1.dao.EmployeeDAO;
import com.siva.webapp1.domain.Employee;
import java.util.List;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ski8cob
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDAO employeeDAO;
    
    @Transactional
    public void addEmployee(Employee employee) {
        employeeDAO.addEmployee(employee);
    }
    
    @Transactional
    public List<Employee> listEmployee() {
        return employeeDAO.listEmployee();
    }

    @Transactional
    public void removeEmployee(Integer id) {
        employeeDAO.removeEmployee(id);
    }
    
}
