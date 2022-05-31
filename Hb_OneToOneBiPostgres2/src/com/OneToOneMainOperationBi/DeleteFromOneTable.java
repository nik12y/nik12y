package com.OneToOneMainOperationBi;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.entity.Instructor;
import com.entity.InstructorDetail;

public class DeleteFromOneTable {

	
	public static void main(String[] args) {
		
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Instructor.class)
				.addAnnotatedClass(InstructorDetail.class).buildSessionFactory();

		Session session = factory.getCurrentSession();

		
		try {
			//start the session 
			 session.beginTransaction();
			 
			 int insDeId=2;
			 
			 //create the InstructorDetail to get the id
			 InstructorDetail theInDeId=session.get(InstructorDetail.class, insDeId);
			 
			 System.out.println("Fonding the Instructor Details :" +theInDeId);
			 
			System.out.println("the Associate InstructorDetails is  :"+theInDeId.getInstructor());
			
			//delete the instructor details
			System.out.println("the InstructorDetail getDeleted");
			
			//we break the bi_directional Deletion here 
			theInDeId.getInstructor().setInstructorDetail(null);
			
			session.delete(theInDeId);
			
			session.getTransaction().commit();
			 
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			
			factory.close();
		}
	}
}
