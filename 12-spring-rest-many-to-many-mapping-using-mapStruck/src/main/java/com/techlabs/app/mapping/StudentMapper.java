package com.techlabs.app.mapping;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.techlabs.app.dtos.CourseDto;
import com.techlabs.app.dtos.StudentDto;
import com.techlabs.app.entities.Course;
import com.techlabs.app.entities.Student;

@Mapper(componentModel = "spring")
public interface StudentMapper {
	
    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

    @Mapping(target = "courses", source = "courses", qualifiedByName = "mapCourses")
    StudentDto studentToStudentDto(Student student);

    @Mapping(target = "courses", source = "courses", qualifiedByName = "mapCourseDtos")
    Student studentDtoToStudent(StudentDto studentDto);

    @Named("mapCourses")
    default List<CourseDto> mapCourses(List<Course> courses) {
        return courses.stream()
            .map(CourseMapper.INSTANCE::courseToCourseDto)
            .collect(Collectors.toList());
    }

    @Named("mapCourseDtos")
    default List<Course> mapCourseDtos(List<CourseDto> courseDtos) {
        return courseDtos.stream()
            .map(CourseMapper.INSTANCE::courseDtoToCourse)
            .collect(Collectors.toList());
    }
}

