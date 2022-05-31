package com.OneToOneMainOpertaionUni;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.entity.Instructor;
import com.entity.InstructorDetail;

public class CreateInstructorBiMain {

	public static void main(String[] args) {

		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Instructor.class)
				.addAnnotatedClass(InstructorDetail.class).buildSessionFactory();

		Session session = factory.getCurrentSession();

		try {

			// create the Instructor object
			Instructor theInstructor = new Instructor("Nikhil", "Yadav", "nik@gmail.com");

			// create the Instructor Details object
			InstructorDetail instructorDetail = new InstructorDetail("New Delhi Apartment-8", 559874);

			// add the InstructorDetail data into instructor Object
			theInstructor.setInstructorDetail(instructorDetail);

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
