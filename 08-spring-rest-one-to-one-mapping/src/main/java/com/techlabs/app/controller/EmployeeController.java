package com.techlabs.app.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
  
import com.techlabs.app.entity.Employee;
import com.techlabs.app.service.EmployeeService;



@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
	Logger logger=LoggerFactory.getLogger(EmployeeController.class);
	private EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		super();
		this.employeeService = employeeService;
	}
	
	@GetMapping
	public List<Employee> getAllEmployees(){
		logger.trace("get All employees method has started");
		return employeeService.findAllEmployees();
	}
	
	@PostMapping
	public Employee addNewEmployee(@RequestBody Employee employee) {
		logger.trace("Add new employees method has started");
		employee.setId(0);
		return employeeService.saveEmployee(employee);
	}
	
	@GetMapping("/{id}")
	public Employee getEmployeeById(@PathVariable(name = "id") int id) {
		logger.trace("get  Employee by id method has started");
		return employeeService.getEmployeeById(id);
	}
	
	@GetMapping("/address/{id}")
	public Employee getEmployeeByAddressId(@PathVariable(name = "id") int id) {
		logger.trace("get All employees by address id method has started");
		return employeeService.getEmployeeByAddressId(id);
	}
	
	@DeleteMapping("/{sid}")
	public void deleteEmployee(@PathVariable(name = "sid") int id) {
		logger.trace("Delete  employee method has started");
		Employee employee= employeeService.getEmployeeById(id);
		//if(employee)
		employeeService.deleteEmployee(employee);

	}
	
	@PutMapping()
	public Employee addEmployee( @RequestBody Employee employee) {
		logger.trace("Add employee method has started");
		if(employee.getId()==0) return null;

		return  employeeService.saveAndUpdateEmployee(employee);
		
	}
}
