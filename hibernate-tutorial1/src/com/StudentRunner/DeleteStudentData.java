package com.StudentRunner;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.entity.Student;

public class DeleteStudentData {

	public static void main(String[] args) {
		
		//create session factory
		SessionFactory factory=new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(Student.class)
				.buildSessionFactory();
		
		Session session = factory.getCurrentSession();
		
		try {
			//delete by student id
		//	int studentId=3;
			//begin transaction 
			session.beginTransaction();
			
			//create student object
	//		Student myStudent=session.get(Student.class, studentId);
			
	//		session.delete(myStudent);
	//		System.out.println("\nStudent Data get Deleted : "+myStudent);
	//		session.getTransaction().commit();
			
			//delete by id
			session= factory.getCurrentSession();
			
			session.createQuery("Delete From Student where id='2'").executeUpdate();
			session.getTransaction().commit();
			System.out.println("Done!");
		} catch (Exception e) {
                 e.printStackTrace();
		}finally {
			factory.close();
		}
	}
}
