package com.OneToManyOperation;

import org.hibernate.query.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.entity.Course;
import com.entity.Instructor;
import com.entity.InstructorDetail;

public class EagerVsLazyDemo {

	public static void main(String[] args) {

		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Instructor.class)
				.addAnnotatedClass(InstructorDetail.class).addAnnotatedClass(Course.class).buildSessionFactory();

		Session session = factory.getCurrentSession();

		try {
			// start the session
			session.beginTransaction();

			// take the instructor id in which we have to add the course detail
			int instructorId = 1;
//			Instructor temInstructor = session.get(Instructor.class, instructorId);
//			
//			System.out.println("Instructor : " + temInstructor);

	//		System.out.println("Retreive the data  Courses : " + temInstructor.getCourse());
			//hibernate with HQL
			Query<Instructor> query=session.createQuery("select i from Instructor i "
					                                                                                  +"join fetch i.courses "
					                                                                                  +"where i.id=:theInstructorId" ,Instructor.class);
			//set parameter 
			query.setParameter("theInstructorId", instructorId);
			
			//execute the query
			Instructor temInstructor = query.getSingleResult();
			session.getTransaction().commit();
			
			session.close();
			// get course for instructor
			System.out.println("Instructor : " + temInstructor);

			System.out.println("Retreive the data  Courses : " + temInstructor.getCourse());

		} catch (Exception e) {
			
			e.printStackTrace();
			
		} finally {
			
			session.close();
			
			factory.close();
		}
	}
}
