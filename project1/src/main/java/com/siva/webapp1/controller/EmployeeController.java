package com.siva.webapp1.controller;

import com.siva.webapp1.domain.Employee;
import com.siva.webapp1.service.EmployeeService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.exception.ConstraintViolationException;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@RequestMapping("/index")
	public String listEmployees(Map<String, Object> map) {

		map.put("employee", new Employee());
		map.put("empList", employeeService.listEmployee());

		return "employee";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addEmployee(@ModelAttribute("employee")
	Employee employee, BindingResult result) {

		employeeService.addEmployee(employee);

		return "redirect:/index";
	}
        
       @RequestMapping(value = "/addEmp/{startrange}/{endrange}", method = RequestMethod.GET)
	public String addEmployeeGET(@PathVariable("startrange") int startrange, @PathVariable("endrange") int endrange ) {
                List<Employee> empList = new ArrayList<Employee>();
                Employee employee = null;
                for (int i = startrange; i <= endrange; i++) {
                   employee = new Employee();
                   employee.setName("Name" + i);
                   employee.setEmployeeId("xxx" + i + "ggg");
                   employee.setDepartment("dept" + i);
                   empList.add(employee);
                }
                for (Employee employee1 : empList) {
                    try {
                        employeeService.addEmployee(employee1);
                        try { 
                            Thread.sleep(5000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } catch (ConstraintViolationException e) {
                        System.err.println("Exception while storing employee --------------------------------------");
                        continue;
                    }
                  
                }
		return "redirect:/index";
	}


	@RequestMapping("/delete/{employeeId}")
	public String deleteContact(@PathVariable("employeeId")
	Integer employeeId) {

		employeeService.removeEmployee(employeeId);

		return "redirect:/index";
	}
}
