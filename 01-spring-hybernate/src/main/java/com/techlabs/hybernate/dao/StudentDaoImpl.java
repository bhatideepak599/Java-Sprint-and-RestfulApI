package com.techlabs.hybernate.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.techlabs.hybernate.entity.Student;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
public class StudentDaoImpl implements StudentDao {

	EntityManager entityManager;

	public StudentDaoImpl(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}

	@Override
	@Transactional

	public void save(Student student) {
		this.entityManager.persist(student);

	}

	@Override
	public List<Student> getAllStudents() {
		TypedQuery<Student> query = entityManager.createQuery("select s from Student s", Student.class);
		List<Student> lst = query.getResultList();

		return lst;
	}

	@Override
	public Student getStudentById(int id) {

		return entityManager.find(Student.class, id);
	}

	@Override
	public List<Student> getStudentByFirstName(String string) {
		TypedQuery<Student> query = entityManager.createQuery("select s from Student s where firstName=?1",
				Student.class);
		query.setParameter(1, string);
		return query.getResultList();
	}

	@Override
	public List<Student> getStudentByFirstNameAndLastName(String string, String string2) {
		TypedQuery<Student> query = entityManager
				.createQuery("select s from Student s where firstName=?1 and lastName=?2", Student.class);
		query.setParameter(1, string);
		query.setParameter(2, string2);

		return query.getResultList();
	}

	@Override
	@Transactional
	public void updateStudent(Student s) {
		Student s2 = entityManager.find(Student.class, s.getId());
		if (s2 != null) {
			entityManager.merge(s);
		} else
			System.out.println("Student id does not exist");

	}

	@Override
	@Transactional
	public void deleteStudent(int id) {
		Student s = entityManager.find(Student.class, id);
		if (s != null) {
			entityManager.remove(s);
		} else
			System.out.println("Student eith id " + id + " does not exist ");

	}

	@Override
	@Transactional
	public void updateWithoutUsingMerge(Student st) {
		
		Student s2 = entityManager.find(Student.class, st.getId());
		if (s2 != null) {
			Query query = entityManager.createQuery(
			"update Student s set s.firstName=?1, s.lastName=?2, s.email=?3 where s.id=?4");
			query.setParameter(1, st.getFirstName());
			query.setParameter(2, st.getLastName());
			query.setParameter(3, st.getEmail());
			query.setParameter(4, st.getId());

			int res = query.executeUpdate();
			System.out.println(res);

		} else
			System.out.println("Student id does not exist");


		
	}

	@Override
	@Transactional
	public void deleteStudentIdLessthan2(int i) {
		//Query query = entityManager.createQuery("delete from Student s  where s.id<=?1");
		Query query= entityManager.createNativeQuery("delete from student where id<?1");
		query.setParameter(1, i);
		int res = query.executeUpdate();
		System.out.println(res);
		
		
	}

	

}
