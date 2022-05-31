package com.StudentRunner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.entity.Student;

public class UpdateStudentData {

	public static void main(String[] args) {

		// create sessionFactory object
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class)
				.buildSessionFactory();

		// create Session object
		Session session = factory.getCurrentSession();

		try {
			// create the student Object
			System.out.println("Updating the  student object >>");
			int studentId = 1;

			// start the transaction
			session.beginTransaction();

			// My code for update the data

			System.out.println("\nUpdate the data is completed : " + studentId);

			Student student2 = session.get(Student.class, studentId);

			// update by name
			student2.setFirstName("Chinchan");

			System.out.println("Get Updated the  data " + student2);
			
			// commit transaction
			session.getTransaction().commit();

			// update all emailId
			session = factory.getCurrentSession();

			session.beginTransaction();

			System.out.println("\nUpdate all emailId : ");
			session.createQuery("Update Student set email='nik@hotmail.com where id=2'").executeUpdate();

			// commit transaction
			session.getTransaction().commit();

			System.out.println("Done!!");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			factory.close();
		}
	}
}
