/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siva.webapp1.dao;

import com.siva.webapp1.domain.Employee;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ski8cob
 */
@Repository
public class EmployeeDAOImpl implements EmployeeDAO {

    @Autowired
    private SessionFactory sessionFactory;
    
    public void addEmployee(Employee employee) {
        //try {
            sessionFactory.getCurrentSession().save(employee);
//        } catch (Exception e) {
//            System.err.println("Exception while storing employee" + e.getClass());
//        }
        
    }

    public List<Employee> listEmployee() {
        return sessionFactory.getCurrentSession().createQuery("from Employee")
				.list();
    }

    public void removeEmployee(Integer id) {
        Employee employee = (Employee) sessionFactory.getCurrentSession().load(
				Employee.class, id);
		if (null != employee) {
			sessionFactory.getCurrentSession().delete(employee);
		}
    }
    
}
