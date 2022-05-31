package com.StudentRunner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.entity.Student;

public class ReadStudentData {

	public static void main(String[] args) {

		// create sessionFactory object
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class)
				.buildSessionFactory();

		// create Session object
		Session session = factory.getCurrentSession();

		try {
			// create the student Object
			System.out.println("creating the  student object >>");
		//	Student student = new Student("Aditya", "Godhane", "adi@gmail.com");
	//		System.out.println("Student is saved : Generated :" + student.getId());
			// start the transaction
			session.beginTransaction();

			// save the student object
		//	session.save(student);

			// commit transaction
			session.getTransaction().commit();

			// My code for retrieved the data

			// create new session for retrieved data
			session = factory.getCurrentSession();
			session.beginTransaction();
	//		System.out.println("\nGenerating the data is completed : " + student.getId());

	//		Student student2 = session.get(Student.class, student.getId());
	//		System.out.println("Get Complete  data " + student2);
			session.getTransaction().commit();
			System.out.println("Done!!");

		} finally {
			factory.close();
		}
	}
}
