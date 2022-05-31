package com.StudentRunner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.entity.Student;

public class CreateStudentData {

	public static void main(String[] args) {

		// create sessionFactory object
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class)
				.buildSessionFactory();

		// create Session object
		Session session = factory.getCurrentSession();

		try {
			// create the student Object
			/*
			 * System.out.println("creating the  student object >>"); Student student = new
			 * Student("Nihal", "Mehta", "nik@gmail.com"); Student student1 = new
			 * Student("Ram", "Kumar", "ram@gmail.com"); Student student2 = new
			 * Student("Rehan", "Ali", "rehan@gmail.com");
			 */
			// start the transaction
			session.beginTransaction();

			// save the student object
			/*
			 * session.save(student); session.save(student1); session.save(student2);
			 */
			// commit transaction
			session.getTransaction().commit();

			System.out.println("Done!!");

		} finally {
			factory.close();
		}
	}
}
