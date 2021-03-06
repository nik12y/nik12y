package com.OneToManyOperation;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.entity.Course;
import com.entity.Instructor;
import com.entity.InstructorDetail;

public class CreateInstructor {

	public static void main(String[] args) {

		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(Instructor.class)
				.addAnnotatedClass(InstructorDetail.class)
				.addAnnotatedClass(Course.class).buildSessionFactory();

		Session session = factory.getCurrentSession();

		try {
			// create the Instructor object
			Instructor instructor = new Instructor("Sagar", "Nayar", "sagar@gmail.com");

			InstructorDetail instructorDetail=new InstructorDetail("Ambegoan Amaravati ", 115478);
			
			instructor.setInstructorDetail(instructorDetail);
			
			session.beginTransaction();
			
			session.save(instructor);
			
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
			factory.close();
		}
	}
}
