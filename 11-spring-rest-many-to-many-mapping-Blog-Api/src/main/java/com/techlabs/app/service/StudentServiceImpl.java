package com.techlabs.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.techlabs.app.dto.CourseDto;
import com.techlabs.app.dto.StudentDto;
import com.techlabs.app.entity.Course;
import com.techlabs.app.entity.Student;
import com.techlabs.app.repository.CourseRepository;
import com.techlabs.app.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {
	private StudentRepository studentRepository;
	private CourseRepository courseRepository;

	public StudentServiceImpl(StudentRepository studentRepository, CourseRepository courseRepository) {
		super();
		this.studentRepository = studentRepository;
		this.courseRepository = courseRepository;
	}

	@Override
	public StudentDto addStudent(StudentDto studentdto) {
		Student student = studentDtoToStudent(studentdto);
		Student save = studentRepository.save(student);
		return studentToStudentDto(save);
	}

	@Override
	public List<StudentDto> getAllStudents() {
		List<Student> all = studentRepository.findAll();
		List<StudentDto> lst = new ArrayList<>();
		for (Student s : all) {
			lst.add(studentToStudentDto(s));
		}
		return lst;
	}

	@Override
	public Student getStudentById(int id) {
		Optional<Student> byId = studentRepository.findById(id);
		if (byId.isPresent()) {
			return byId.get();
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
	public Student addStudentToACourse(int studentId, int courseId) {
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

			return student;
		} else {
			return null;
		}
	}

	public static Student studentDtoToStudent(StudentDto studentDto) {
		if (studentDto == null) {
			return null;
		}

		Student student = new Student();
		student.setId(studentDto.getId());
		student.setName(studentDto.getName());
		student.setEmail(studentDto.getEmail());

		List<Course> courses = studentDto.getCourses().stream().map(courseDto -> {
			Course course = new Course();
			course.setId(courseDto.getId());
			course.setTitle(courseDto.getTitle());

			return course;
		}).toList();

		student.setCourses(courses);
		for (Course c : courses) {
			c.getStudents().add(student);
		}

		return student;
	}

	public static StudentDto studentToStudentDto(Student student) {
		if (student == null) {
			return null;
		}

		StudentDto studentDto = new StudentDto();
		studentDto.setId(student.getId());
		studentDto.setName(student.getName());
		studentDto.setEmail(student.getEmail());

		List<CourseDto> courses = student.getCourses().stream().map(course -> {
			CourseDto courseDto = new CourseDto();
			courseDto.setId(course.getId());
			courseDto.setTitle(course.getTitle());

			return courseDto;
		}).toList();

		studentDto.setCourses(courses);
		for (CourseDto c : courses) {
			c.getStudents().add(studentDto);
		}

		return studentDto;
	}

	public Course courseDtoToCourse(CourseDto courseDto) {
		if (courseDto == null) {
			return null;
		}

		Course course = new Course();
		course.setId(courseDto.getId());
		course.setTitle(courseDto.getTitle());

		List<Student> lst = courseDto.getStudents().stream().map(dto -> {
			Student s = new Student();
			s.setId(dto.getId());
			s.setEmail(dto.getEmail());
			s.setName(dto.getName());
			return s;

		}).toList();
		course.setStudents(lst);
		for (Student s : lst) {
			s.getCourses().add(course);
		}

		return course;
	}

	public static CourseDto courseToCourseDto(Course course) {
		if (course == null) {
			return null;
		}

		CourseDto courseDto = new CourseDto();
		courseDto.setId(course.getId());
		courseDto.setTitle(course.getTitle());

		List<StudentDto> lst = course.getStudents().stream().map(student -> {
			StudentDto dto = new StudentDto();
			dto.setId(student.getId());
			dto.setName(student.getName());
			dto.setEmail(student.getEmail());
			return dto;

		}).toList();

		courseDto.setStudents(lst);
////		
//		for(StudentDto s:lst) {
//			s.getCourses().add(courseDto);
//		}

		return courseDto;
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

			return studentToStudentDto(student);
		} else {
			return null;
		}
	}

}
