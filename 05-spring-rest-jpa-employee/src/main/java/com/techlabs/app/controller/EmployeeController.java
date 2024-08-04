package com.techlabs.app.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techlabs.app.dto.RequestDto;
import com.techlabs.app.dto.ResponseDto;
import com.techlabs.app.entity.Employee;
import com.techlabs.app.exception.EmployeeNotFoundException;
import com.techlabs.app.service.EmployeeService;
import com.techlabs.app.util.PageResponse;

import jakarta.validation.Valid;

@RestController
public class EmployeeController {

	private EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		super();
		this.employeeService = employeeService;
	}

	@GetMapping("/employees")
	public ResponseEntity<PageResponse<ResponseDto>> getAllEmployees(@RequestParam(name="page",defaultValue = "0") int page ,
			@RequestParam(name="size",defaultValue = "2") int size ,
			@RequestParam(name="sortBy",defaultValue = "id") String sortBy,
			@RequestParam(name="direction",defaultValue = "asc") String direction) {
		
		return new ResponseEntity<PageResponse<ResponseDto>>(employeeService.getAllEmployees(page,size,sortBy,direction), HttpStatus.OK);
	}

	
	@GetMapping("/employees/{sid}")
	public ResponseEntity<ResponseDto> getEmployeeById(@Valid @PathVariable(name = "sid") int id) {

		return null;
	}

	@DeleteMapping("/employees/{sid}")
	public void deleteEmployee(@PathVariable(name = "sid") int id) {
		employeeService.getEmployeeById(id);
		employeeService.deleteEmployee(id);

	}

	@GetMapping("/employees/name/{empName}")
	public List<ResponseDto> findEmployeeByName(@PathVariable(name = "empName") String name) {
		List<ResponseDto> emp = employeeService.findEmployeeByFirstName(name);

		return emp;

	}

	@GetMapping("/employees/email/{empEmail}")
	public List<ResponseDto> findEmployeeByEmail(@PathVariable(name = "empEmail") String email) {
		List<ResponseDto> emp = employeeService.findEmployeeByEmail(email);

		return emp;

	}

	@GetMapping("/employees/active/{empActive}")
	public List<ResponseDto> findAllActiveEmployee(@PathVariable(name = "empActive") boolean active) {
		List<ResponseDto> emp = employeeService.findAllActiveEmployee(active);

		return emp;

	}

	@GetMapping("/employees/starting/{start}")
	public List<ResponseDto> findEmployeeNameStartingWith(@PathVariable(name = "start") String s) {
		List<ResponseDto> emp = employeeService.findEmployeeNameStartingWith(s);

		return emp;

	}

	@GetMapping("/employees/salaryAndDept/{salary}/{dept}")
	public List<ResponseDto> findBySalaryGreaterThanAndDesignation(@PathVariable(name = "salary") double salary,
			@PathVariable(name = "dept") String designation) {
		List<ResponseDto> emp = employeeService.findBySalaryGreaterThanAndDesignation(salary, designation);

		return emp;
	}

	@GetMapping("/employees/salary/{start}/{end}")
	public List<ResponseDto> findEmployeeSalaryBetween(@PathVariable(name = "start") double start,
			@PathVariable(name = "end") double end) {
		List<ResponseDto> emp = employeeService.findEmployeeSalaryBetween(start, end);

		return emp;

	}

	@GetMapping("/employees/active/salary/{start}")
	public List<ResponseDto> findBySalaryGreaterThanAndActiveTrue(@PathVariable(name = "start") double start) {
		List<ResponseDto> emp = employeeService.findBySalaryGreaterThanAndActiveTrue(start);

		return emp;

	}

	@GetMapping("/employees/count/active")
	public int countActiveEmployees() {
		int count = employeeService.countActiveEmployees();

		return count;

	}

	@GetMapping("/employees/designation/active/{empDesig}")
	public int countByDesignationAndActiveTrue(@PathVariable(name = "empDesig") String s) {
		int count = employeeService.countByDesignationAndActiveTrue(s);

		return count;

	}

	@PostMapping("/employees")

	public ResponseDto addEmployee(@Valid @RequestBody RequestDto employee) {
		employee.setId(0);

		ResponseDto dto = employeeService.saveAndUpdateEmployee(employee);
		return dto;
	}

	@PutMapping("/employees")
	public ResponseEntity<ResponseDto> updateEmployee(@Valid @RequestBody RequestDto employee) {
		ResponseDto tempemployee = employeeService.getEmployeeById(employee.getId());

		return new ResponseEntity<ResponseDto>(employeeService.saveAndUpdateEmployee(employee), HttpStatus.OK);

	}

}
