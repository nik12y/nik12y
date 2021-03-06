package com.OneToManyOperation;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.entity.Course;
import com.entity.Instructor;
import com.entity.InstructorDetail;
import com.entity.Review;

public class RetrieveCourseAndReviews_Uni {

	public static void main(String[] args) {

		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Instructor.class)
				.addAnnotatedClass(InstructorDetail.class).addAnnotatedClass(Course.class)
				.addAnnotatedClass(Review.class).buildSessionFactory();

		Session session = factory.getCurrentSession();

		try {
			// start the session
			session.beginTransaction();
			// get the course id
			int courseId = 3;
			Course course = session.get(Course.class, courseId);

			System.out.println(course);
			System.out.println("course is printed ");
			System.out.println(course.getReviews());

			// commit session
			session.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
			factory.close();
		}
	}
}
