package com.StudentRunner;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.entity.Student;

public class QueryStudentData {

	public static void main(String[] args) {

		// create sessionFactory object
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class)
				.buildSessionFactory();

		// create Session object
		Session session = factory.getCurrentSession();

		try {
			// start the transaction
			session.beginTransaction();

			// Query for retrieving the data
			List<Student> theStudent = session.createQuery("from Student").getResultList();

			// display the student records
			for (Student myStudent : theStudent) {
				System.out.println(myStudent);
			}

			// Query for OR operation
			System.out.println("\n\nStudent  who have last name is  godhane");
			theStudent = session.createQuery("from Student s where s.lastName='Godhane'").list();

			for (Student orStudent : theStudent) {
				System.out.println(orStudent);
			}

			System.out.println("\n\nStudent  who have last name OR firstName is  godhane");
			theStudent = session.createQuery("from Student s where s.lastName='Godhane' "+ " OR s.firstName='Rehan' ").list();

			for (Student oRStudent : theStudent) {
				System.out.println(oRStudent);
			}

			// commit transaction
			session.getTransaction().commit();

			System.out.println("Done!!");

		} finally {
			factory.close();
		}
	}
}
