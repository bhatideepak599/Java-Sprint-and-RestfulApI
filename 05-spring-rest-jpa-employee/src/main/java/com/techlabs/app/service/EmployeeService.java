package com.techlabs.app.service;

import java.util.List;

import com.techlabs.app.dto.RequestDto;
import com.techlabs.app.dto.ResponseDto;
import com.techlabs.app.entity.Employee;

public interface EmployeeService {
	public List<ResponseDto> getAllEmployees();

	public ResponseDto getEmployeeById(int id);

	public ResponseDto saveAndUpdateEmployee(RequestDto student);

	public void deleteEmployee(int id);

	public List<ResponseDto> findEmployeeByFirstName(String name);

	public List<ResponseDto> findEmployeeByEmail(String email);

	public List<ResponseDto> findAllActiveEmployee(boolean active);

	public List<ResponseDto> findEmployeeNameStartingWith(String s);

	public List<ResponseDto> findEmployeeSalaryBetween(double start, double end);

	public int countActiveEmployees();

	public int countByDesignationAndActiveTrue(String s);

	public List<ResponseDto> findBySalaryGreaterThanAndActiveTrue(double start);

	public List<ResponseDto> findBySalaryGreaterThanAndDesignation(double salary, String designation);
}
