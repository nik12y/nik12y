package com.OneToManyOperation;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.entity.Course;
import com.entity.Instructor;
import com.entity.InstructorDetail;
import com.entity.Review;

public class CreateCourseAndReviews_Uni {

				public static void main(String[] args) {
			
					SessionFactory factory = new Configuration().configure("hibernate.cfg.xml")
																			.addAnnotatedClass(Instructor.class)
																			.addAnnotatedClass(InstructorDetail.class)
																			.addAnnotatedClass(Course.class)
																			.addAnnotatedClass(Review.class)
																			.buildSessionFactory();
			
					Session session = factory.getCurrentSession();
			
					try {
						// start the session
						session.beginTransaction();
			
						// create the course object
						Course course = new Course("Java learring for Beginner");
			
						// add the review in course
						course.add(new Review("Great Session took by Chad darby ..............."));
						course.add(new Review("Hi Darby course is cool but which tooth paste you use ..............."));
						course.add(new Review("I want teeth like you eeeee eee ..............."));
			
						// save the session
						System.out.println(course);
						System.out.println(course.getReviews());
			
						session.save(course);
			
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
