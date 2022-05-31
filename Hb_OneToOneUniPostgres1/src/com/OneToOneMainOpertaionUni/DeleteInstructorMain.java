package com.OneToOneMainOpertaionUni;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.entity.Instructor;
import com.entity.InstructorDetail;

public class DeleteInstructorMain {

	public static void main(String[] args) {

		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Instructor.class)
				.addAnnotatedClass(InstructorDetail.class).buildSessionFactory();

		Session session = factory.getCurrentSession();

		try {
			// start the session
			session.beginTransaction();

			// take id from table for deleting the value
			int theId = 2;
			Instructor temDelete = session.get(Instructor.class, theId);

			System.out.println("found the Instructor :" + temDelete);

			if (temDelete != null) {

				System.out.println("Deleting the Instructor : " + temDelete);

				session.delete(temDelete);
			}
			// commit the session
			session.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			factory.close();
		}
	}
}
