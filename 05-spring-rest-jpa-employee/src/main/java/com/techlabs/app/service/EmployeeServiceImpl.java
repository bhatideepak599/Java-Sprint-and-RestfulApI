package com.techlabs.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.techlabs.app.dto.RequestDto;
import com.techlabs.app.dto.ResponseDto;
import com.techlabs.app.entity.Employee;
import com.techlabs.app.exception.EmployeeNotFoundException;
import com.techlabs.app.repository.EmployeeRepository;
import com.techlabs.app.util.PageResponse;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	private EmployeeRepository employeeRepository;

	public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
		super();
		this.employeeRepository = employeeRepository;
	}

	@Override
	public PageResponse<ResponseDto> getAllEmployees(int page, int size,String sortBy,String direction) {
		
		Sort sort=direction.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(page, size,sort);
		Page<Employee> all = employeeRepository.findAll(pageable);

		if (all.getContent().isEmpty()) {
			throw new EmployeeNotFoundException("No Employee Found");
		}

		List<ResponseDto> list = getList(all.getContent());

		return new PageResponse<ResponseDto>(list, all.getNumber(), all.getNumberOfElements(), all.getTotalElements(),
				all.getTotalPages(), all.isLast());
	}

	@Override
	public ResponseDto getEmployeeById(int id) {
		Optional<Employee> byId = employeeRepository.findById(id);

		if (!byId.isPresent()) {
			throw new RuntimeException("No Employee with id = " + id + " Found");

		}

		ResponseDto dto = employeeObjectToResponseDto(byId.get());
		return dto;

	}

	@Override
	public ResponseDto saveAndUpdateEmployee(RequestDto requestDto) {

		Employee employee = requestDtoToEmployeeObject(requestDto);
		ResponseDto dto = employeeObjectToResponseDto(employeeRepository.save(employee));
		return dto;

	}

	@Override
	public void deleteEmployee(int id) {
		employeeRepository.deleteById(id);

	}

	@Override
	public List<ResponseDto> findEmployeeByFirstName(String name) {
		List<Employee> employees = employeeRepository.findEmployeeByName(name);
		if (employees == null || employees.isEmpty())
			throw new EmployeeNotFoundException("No Employee with name = " + name + " Found");
		return getList(employees);
	}

	@Override
	public List<ResponseDto> findEmployeeByEmail(String email) {
		List<Employee> employees = employeeRepository.findEmployeeByEmail(email);
		if (employees == null || employees.isEmpty())
			throw new EmployeeNotFoundException("No Employee with email = " + email + " Found");
		return getList(employees);

	}

	@Override
	public List<ResponseDto> findAllActiveEmployee(boolean active) {
		List<Employee> employees = employeeRepository.findEmployeeByActive(active);
		if (employees == null || employees.isEmpty())
			throw new EmployeeNotFoundException("No Active Employees Found");
		return getList(employees);

	}

	@Override
	public List<ResponseDto> findEmployeeNameStartingWith(String s) {
		List<Employee> employees = employeeRepository.findByNameStartingWith(s);
		if (employees == null || employees.isEmpty())
			throw new EmployeeNotFoundException("No Employee Present having Name Start with " + s);
		return getList(employees);

	}

	@Override
	public List<ResponseDto> findEmployeeSalaryBetween(double start, double end) {
		List<Employee> employees = employeeRepository.findBySalaryBetween(start, end);
		if (employees == null || employees.isEmpty())
			throw new EmployeeNotFoundException("No Employee having Salary between " + start + " and " + end);
		return getList(employees);
	}

	@Override
	public int countActiveEmployees() {
		int count = employeeRepository.countByActiveTrue();
		if (count == 0)
			throw new EmployeeNotFoundException("No Active Employee Found ");
		return count;
	}

	@Override
	public int countByDesignationAndActiveTrue(String s) {
		int count = employeeRepository.countByDesignationAndActiveTrue(s);
		if (count == 0)
			throw new EmployeeNotFoundException("No Active Employee with Designation " + s + " Found ");
		return count;
	}

	@Override
	public List<ResponseDto> findBySalaryGreaterThanAndActiveTrue(double start) {
		List<Employee> employees = employeeRepository.findBySalaryGreaterThanAndActiveTrue(start);
		if (employees == null || employees.isEmpty())
			throw new EmployeeNotFoundException("No Active Employee having Salary " + start);
		return getList(employees);
	}

	@Override
	public List<ResponseDto> findBySalaryGreaterThanAndDesignation(double salary, String designation) {
		List<Employee> employees = employeeRepository.findBySalaryGreaterThanAndDesignation(salary, designation);
		if (employees == null || employees.isEmpty()) {
			throw new EmployeeNotFoundException(
					"No Employee found with salary > " + salary + " and Designation = " + designation);
		}

		return getList(employees);
	}

	private Employee requestDtoToEmployeeObject(RequestDto dto) {
		Employee employee = new Employee();
		if (employee.getId() != 0) {
			employee.setId(dto.getId());
		}
		employee.setName(dto.getName());
		employee.setEmail(dto.getEmail());
		employee.setActive(dto.isActive());
		employee.setDesignation(dto.getDesignation());
		employee.setSalary(dto.getSalary());
		return employee;
	}

	private ResponseDto employeeObjectToResponseDto(Employee employee) {
		ResponseDto dto = new ResponseDto();
		dto.setName(employee.getName());
		dto.setDesignation(employee.getDesignation());
		dto.setEmail(employee.getEmail());
		dto.setId(employee.getId());
		return dto;
	}

	private List<ResponseDto> getList(List<Employee> employees) {
		List<ResponseDto> lst = new ArrayList<ResponseDto>();
		for (Employee e : employees) {
			lst.add(employeeObjectToResponseDto(e));
		}
		return lst;
	}

	@Override
	public List<ResponseDto> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
