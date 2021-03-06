package com.nikWebDemo.DAO;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nikWebDemo.entity.Customer;

@Repository
public class CustomerDaoImpl implements CustomerDao {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	@Transactional
	public List<Customer> getCustomers() {

		// create getCurrentSession
		Session currentSession = sessionFactory.openSession();

		// write the query
		Query<Customer> query = currentSession.createQuery(" from Customer", Customer.class);

		//get result list
		List<Customer> customer = query.getResultList();
		
		return customer;
	}

}
