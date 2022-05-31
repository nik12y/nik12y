package com.OneToOneMainOperationBi;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.entity.Instructor;
import com.entity.InstructorDetail;

public class CreateInstructorMain {

	public static void main(String[] args) {

		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Instructor.class)
				.addAnnotatedClass(InstructorDetail.class).buildSessionFactory();

		Session session = factory.getCurrentSession();

		try {

			// create the Instructor object
			Instructor theInstructor = new Instructor("Nayan", "Kumar", "Nayan@gmail.com");

			// create the Instructor Details object
			InstructorDetail instructorDetai = new InstructorDetail("Kurla Near Black Well", 112233);

			// add the InstructorDetail data into instructor Object
			theInstructor.setInstructorDetail(instructorDetai);

			// start the session
			session.beginTransaction();
			
			// save the data
			session.save(theInstructor);

			// commit the session
			session.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			factory.close();
		}
	}
}
