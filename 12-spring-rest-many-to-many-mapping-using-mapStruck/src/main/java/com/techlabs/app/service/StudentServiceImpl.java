
package com.techlabs.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techlabs.app.dtos.StudentDto;
import com.techlabs.app.entities.Course;
import com.techlabs.app.entities.Student;
import com.techlabs.app.mapping.StudentMapper;
import com.techlabs.app.repository.CourseRepository;
import com.techlabs.app.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {
	private StudentRepository studentRepository;
	private CourseRepository courseRepository;
	@Autowired
	private StudentMapper studentMapper;

	public StudentServiceImpl(StudentRepository studentRepository, CourseRepository courseRepository) {
		super();
		this.studentRepository = studentRepository;
		this.courseRepository = courseRepository;

	}

	@Override
	public StudentDto addStudent(StudentDto studentDto) {
		Student student = studentMapper.studentDtoToStudent(studentDto);

		Student savedStudent = studentRepository.save(student);

		return studentMapper.studentToStudentDto(savedStudent);
	}

	
	@Override
	public List<StudentDto> getAllStudents() {
	    List<Student> students = studentRepository.findAll();
	    return students.stream()
	            .map(student -> {
	                if (student == null) {
	                    // Handle null case or log an error
	                    return new StudentDto();
	                }
	                return studentMapper.studentToStudentDto(student);
	            })
	            .collect(Collectors.toList());
	}


	private List<StudentDto> getList(List<Student> students) {
		List<StudentDto> list=new ArrayList<>();
		for(Student s:students) {
			list.add(studentMapper.studentToStudentDto(s));
		}
		return list;
	}

	@Override
	public StudentDto getStudentById(int id) {
		Optional<Student> byId = studentRepository.findById(id);
		if (byId.isPresent()) {
			Student student = byId.get();

			return studentMapper.studentToStudentDto(student);
		}
		return null;
	}

	@Override
	public String deleteSttudentById(int id) {
		Optional<Student> byId = studentRepository.findById(id);
		if (byId.isPresent()) {
			Student student = byId.get();
			List<Course> courses = student.getCourses();
			for (Course c : courses) {
				c.getStudents().remove(student);

				courseRepository.save(c);
			}
			student.setCourses(null);
			studentRepository.save(student);
			return "Student deleted Successfully";
		}
		return "No Student Found";
	}

	@Override
	public StudentDto addStudentToACourse(int studentId, int courseId) {
		Optional<Student> studentOpt = studentRepository.findById(studentId);
		Optional<Course> courseOpt = courseRepository.findById(courseId);

		if (studentOpt.isPresent() && courseOpt.isPresent()) {
			Student student = studentOpt.get();
			Course course = courseOpt.get();

			if (!student.getCourses().contains(course)) {
				student.getCourses().add(course);
				course.getStudents().add(student);

				studentRepository.save(student);
				courseRepository.save(course);
			}

			return studentMapper.studentToStudentDto(student);
		} else {
			return null;
		}
	}

	@Override
	public StudentDto removeStudentFromACourse(int studentId, int courseId) {
		Optional<Student> studentOpt = studentRepository.findById(studentId);
		Optional<Course> courseOpt = courseRepository.findById(courseId);

		if (studentOpt.isPresent() && courseOpt.isPresent()) {
			Student student = studentOpt.get();
			Course course = courseOpt.get();

			if (student.getCourses().contains(course)) {
				student.getCourses().remove(course);
				course.getStudents().remove(student);

				studentRepository.save(student);
				courseRepository.save(course);
			}

			return studentMapper.studentToStudentDto(student);
		} else {
			return null;
		}
	}

}
