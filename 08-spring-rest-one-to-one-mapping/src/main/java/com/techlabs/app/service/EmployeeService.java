package com.techlabs.app.service;

import java.util.List;

import com.techlabs.app.entity.Address;
import com.techlabs.app.entity.Employee;

public interface EmployeeService {
	public Employee saveEmployee(Employee employee);

	public List<Employee> findAllEmployees();

	public Employee getEmployeeById(int id);

	public void deleteEmployee(Employee employee);

	public Employee saveAndUpdateEmployee(Employee employee);

	public Employee getEmployeeByAddressId(int id);
	
	}
