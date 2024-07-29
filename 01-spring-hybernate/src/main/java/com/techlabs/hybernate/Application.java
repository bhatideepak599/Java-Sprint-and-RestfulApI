package com.techlabs.hybernate;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.techlabs.hybernate.dao.StudentDao;
import com.techlabs.hybernate.entity.Student;

@SpringBootApplication
public class Application implements CommandLineRunner {
	private StudentDao studentDao;
	

	public Application(StudentDao studentDao) {
		super();
		this.studentDao = studentDao;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		addStudent();
		
		//getStudentById();
		//getStudentByFirstName();
		//getStudentByFirstNameAndLastName();
		//updateStudent();
//		deleteStudent();
		//getAllStudents();
		//updateWithoutUsingMerge();
		//deleteStudentIdLessthan2();
		
		
	}

	private void deleteStudentIdLessthan2() {
		studentDao.deleteStudentIdLessthan2(2);
		
	}

	private void updateWithoutUsingMerge() {
		Student st=new Student(4,"Rahul","Kumar","Rahul@gmail.com");
		
		studentDao.updateWithoutUsingMerge(st);
		
	}

	private void deleteStudent() {
		studentDao.deleteStudent(3);
		
	}

	private void updateStudent() {
		Student s=new Student("Deepak","Sahu","deepak@gmail.com");
		s.setId(2);
		studentDao.updateStudent(s);
		
		
	}

	private void getStudentByFirstNameAndLastName() {
		
		List<Student>students=  studentDao.getStudentByFirstNameAndLastName("Deepak","bhati");
		System.out.println(students);
	}

	private void getStudentByFirstName() {
		List<Student>students=  studentDao.getStudentByFirstName("Deepak");
		System.out.println(students);
		
	}

	private void getStudentById() {
		Student student=studentDao.getStudentById(1);
		System.out.println(student);
		
	}

	private void getAllStudents() {
		List<Student> lst=studentDao.getAllStudents();
		System.out.println(lst);
		
	}

	private void addStudent() {
		Student st=new Student("Deepak","Bhati","deepak@gmail.com");
		studentDao.save(st);
		
	}

}
