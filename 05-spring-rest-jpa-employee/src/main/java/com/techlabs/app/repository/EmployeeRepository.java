package com.techlabs.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.app.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	public List<Employee> findEmployeeByName(String name);
	public List<Employee> findEmployeeByEmail(String email);
	public List<Employee> findEmployeeByActive(boolean active);
	public List<Employee> findByNameStartingWith(String name);
	List<Employee> findBySalaryGreaterThanAndDesignation(double salary, String designation);
	List<Employee> findBySalaryBetween(double salary, double end);
	public int countByActiveTrue();
	List<Employee> findBySalaryGreaterThanAndActiveTrue(double salary);
	public int countByDesignationAndActiveTrue(String designation);
	
	
}
