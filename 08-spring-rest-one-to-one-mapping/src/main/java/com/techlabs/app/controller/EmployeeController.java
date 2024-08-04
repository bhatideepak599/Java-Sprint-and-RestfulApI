package com.techlabs.app.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techlabs.app.entity.Address;
import com.techlabs.app.entity.Employee;
import com.techlabs.app.service.EmployeeService;



@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
	
	private EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		super();
		this.employeeService = employeeService;
	}
	
	@GetMapping
	public List<Employee> getAllEmployees(){
		return employeeService.findAllEmployees();
	}
	
	@PostMapping
	public Employee addNewEmployee(@RequestBody Employee employee) {
		employee.setId(0);
		return employeeService.saveEmployee(employee);
	}
	
	@GetMapping("/{id}")
	public Employee getEmployeeById(@PathVariable(name = "id") int id) {
		return employeeService.getEmployeeById(id);
	}
	
	@GetMapping("/address/{id}")
	public Employee getEmployeeByAddressId(@PathVariable(name = "id") int id) {
		return employeeService.getEmployeeByAddressId(id);
	}
	
	@DeleteMapping("/{sid}")
	public void deleteEmployee(@PathVariable(name = "sid") int id) {
		Employee employee= employeeService.getEmployeeById(id);
		//if(employee)
		employeeService.deleteEmployee(employee);

	}
	
	@PutMapping()
	public Employee addEmployee( @RequestBody Employee employee) {
		if(employee.getId()==0) return null;

		return  employeeService.saveAndUpdateEmployee(employee);
		
	}
}
