package com.TestJDBCconnection;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.entity.Course;
import com.entity.Instructor;
import com.entity.InstructorDetail;

public class CreateCourse {

	public static void main(String[] args) {

		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Instructor.class)
				.addAnnotatedClass(InstructorDetail.class).addAnnotatedClass(Course.class).buildSessionFactory();

		Session session = factory.getCurrentSession();

		try {
			//start the session
			session.beginTransaction();
			
			//take the instructor id in which we have to add the course detail
			int instructorId=1;
			Instructor temInstructor= session.get(Instructor.class, instructorId);
		
			//create the course object
			Course course = new Course("java Training from scratch");
			Course course1 = new Course("Spring Training from scratch");

			//add the course object into Instructor object (temInstructor)
			temInstructor.add(course);
			temInstructor.add(course1);
               
			//save the session object of course
			session.save(course);
			session.save(course1);

			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
			factory.close();
		}
	}
}
