package com.StudentRunner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.entity.Instructor;
import com.entity.InstructorDetail;

public class OneToOneUniMain {

	public static void main(String[] args) {

		// create sessionFactory
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(Instructor.class)
				.addAnnotatedClass(InstructorDetail.class)
				.buildSessionFactory();

		Session session = factory.getCurrentSession();

		try {
            //create the Instructor object
			Instructor theInstructor=new Instructor("Mohan","Naik" ,"mohan@gmail.com");
			
			InstructorDetail instructorDetail=new InstructorDetail("http://Twitter.in", "Carrom");
			
			//save instructorDetail into instructor object 
			System.out.println("Data is saved in db.......");
			theInstructor.setInstructorDetail(instructorDetail);
			
			session.beginTransaction();
			
			//save the  object of Instructor
			session.save(theInstructor);
			
			//commit the transaction
			session.getTransaction().commit();
			
			System.out.println("Done OneToOne!!");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			factory.close();
		}

	}
}
