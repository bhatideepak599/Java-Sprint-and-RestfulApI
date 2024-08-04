package com.techlabs.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.techlabs.app.entity.Address;
import com.techlabs.app.entity.Employee;
import com.techlabs.app.repository.AddressRepository;
import com.techlabs.app.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private EmployeeRepository employeeRepository;
	private AddressRepository addressRepository;

	public EmployeeServiceImpl(EmployeeRepository employeeRepository, AddressRepository addressRepository) {
		super();
		this.employeeRepository = employeeRepository;
		this.addressRepository = addressRepository;
	}

	@Override
	public Employee saveEmployee(Employee employee) {

		return employeeRepository.save(employee);
	}

	@Override
	public List<Employee> findAllEmployees() {

		return employeeRepository.findAll();
	}

	@Override
	public Employee getEmployeeById(int id) {
		Optional<Employee> byId = employeeRepository.findById(id);
		return byId.get();
	}

	@Override
	public void deleteEmployee(Employee employee) {
		employeeRepository.delete(employee);

	}

	@Override
	public Employee saveAndUpdateEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}

	@Override
	public Employee getEmployeeByAddressId(int id) {
		Optional<Address> byId = addressRepository.findById(id);
		Employee employeeByAddress_id = byId.get().getEmployee();
		return employeeByAddress_id;
	}

}
