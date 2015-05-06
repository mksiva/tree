package com.siva.webapp1.service;

import com.siva.webapp1.domain.Employee;
import java.util.List;


public interface EmployeeService {
	
	public void addEmployee(Employee employee);
	public List<Employee> listEmployee();
	public void removeEmployee(Integer id);
}
