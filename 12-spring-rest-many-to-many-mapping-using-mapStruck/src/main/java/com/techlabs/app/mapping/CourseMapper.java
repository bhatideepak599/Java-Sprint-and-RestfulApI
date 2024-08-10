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
public interface CourseMapper {

    CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);

    @Mapping(target = "students", source = "students", qualifiedByName = "mapStudents")
    CourseDto courseToCourseDto(Course course);

    @Mapping(target = "students", source = "students", qualifiedByName = "mapStudentDtos")
    Course courseDtoToCourse(CourseDto courseDto);

    @Named("mapStudents")
    default List<StudentDto> mapStudents(List<Student> students) {
        return students.stream()
            .map(StudentMapper.INSTANCE::studentToStudentDto)
            .collect(Collectors.toList());
    }

    @Named("mapStudentDtos")
    default List<Student> mapStudentDtos(List<StudentDto> studentDtos) {
        return studentDtos.stream()
            .map(StudentMapper.INSTANCE::studentDtoToStudent)
            .collect(Collectors.toList());
    }
}

