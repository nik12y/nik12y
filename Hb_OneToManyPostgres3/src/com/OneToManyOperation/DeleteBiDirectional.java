package com.OneToManyOperation;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.entity.Course;
import com.entity.Instructor;
import com.entity.InstructorDetail;

public class DeleteBiDirectional {

	public static void main(String[] args) {

		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(Instructor.class)
				.addAnnotatedClass(InstructorDetail.class)
				.addAnnotatedClass(Course.class)//(if we not add the addAnnotatedClass Then it give this error) Use of @OneToMany or @ManyToMany targeting an unmapped class: com.entity.Instructor.courses[com.entity.Course]
				.buildSessionFactory();

		Session session = factory.getCurrentSession();

		try {
			// start the session
			session.beginTransaction();

			// Take the course id from where we have to delete the details
			int courseId = 2;

			// get Course for instructor
			Course course = session.get(Course.class, courseId);

			// delete the course
			System.out.println("Deleting the course : " + course);
			session.delete(course);

			session.getTransaction().commit();
			//
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			factory.close();
		}

	}
}
