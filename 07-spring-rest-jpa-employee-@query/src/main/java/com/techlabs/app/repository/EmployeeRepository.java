package com.techlabs.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.techlabs.app.dto.ResponseDto;
import com.techlabs.app.entity.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
	@Query("select e from Employee e where e.name= :n")
	public List<Employee> findEmployeeByName(@Param("n") String name);
	
	@Query("select e from Employee e where e.email= :e")
	public List<Employee> findEmployeeByEmail(@Param("e")String email);
	
	@Query("select e from Employee e where e.active= :e")
	public List<Employee> findEmployeeByActive(@Param("e") boolean active);
	
	@Query("SELECT e FROM Employee e WHERE e.name LIKE :name%")
	public List<Employee> findByNameStartingWith(@Param("name") String name);
	
	@Query("SELECT e FROM Employee e WHERE e.salary > :salary AND e.designation = :designation")
	List<Employee> findBySalaryGreaterThanAndDesignation(@Param("salary") double salary, @Param("designation") String designation);

	
	@Query("SELECT e FROM Employee e WHERE e.salary BETWEEN :start AND :end")
	List<Employee> findBySalaryBetween(@Param("start") double start, @Param("end") double end);

	
	@Query("SELECT COUNT(e) FROM Employee e WHERE e.active = true")
	public int countByActiveTrue();

	
	@Query("SELECT e FROM Employee e WHERE e.salary > :salary AND e.active = true")
	List<Employee> findBySalaryGreaterThanAndActiveTrue(@Param("salary") double salary);

	
	@Query("SELECT COUNT(e) FROM Employee e WHERE e.designation = :designation AND e.active = true")
	public int countByDesignationAndActiveTrue(@Param("designation") String designation);
	
	@Query("select e from Employee e where e.id= :id")
	public Employee getById(@Param("id") int id);

	@Query("DELETE FROM Employee e WHERE e.id = :id")
	void deleteEmployeeById(@Param("id") int id);
	
	@Modifying
	@Query("UPDATE Employee e SET e.name = :name, e.salary = :salary, e.designation = :designation WHERE e.id = :id")
	public Employee update(@Param("id") int id, @Param("name") String name, @Param("salary") double salary, @Param("designation") String designation);
	
	@Query("select e from Employee e")
	public List<Employee> getAll();
	
	@Query(value="select * from Employees", nativeQuery = true)
	public List<Employee> getAllByNative();

	//@Modifying
	@Query("INSERT INTO Employee (id, name, salary, designation, active) VALUES (:id, :name, :salary, :designation, :active)")
	 public void saveEmployee(@Param("id") int id, @Param("name") String name, @Param("salary") double salary, @Param("designation") String designation, @Param("active") boolean active);

	
	
}
