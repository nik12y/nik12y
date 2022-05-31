package com.StudentRunner;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.DateFormating.DateUtils;
import com.entity.Student;

public class createDateFormate {

	public static void main(String[] args) {

		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class)
				.buildSessionFactory();
		Session session = factory.getCurrentSession();

		try {
			//Date formate
			System.out.println("Creating the date object");
			
			String theDateOfBirth= "04/24/2003";
			
			Date theDate=DateUtils.parseDate(theDateOfBirth);
                     
			//create student object 
			Student myStudent=new Student("nikhil", "Yadav" ,"nik@gmail.com",theDate );
			
			session.beginTransaction();
			
			session.save(myStudent);
			
			session.getTransaction().commit();
			
			System.out.println("success");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			factory.close();
		}
	}
}
