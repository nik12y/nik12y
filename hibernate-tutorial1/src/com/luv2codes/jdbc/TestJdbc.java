package com.luv2codes.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestJdbc {

	public static void main(String[] args) {
		
		String jdbcUrl="jdbc:mysql://localhost:3306/hb_student_tracker?useSSL=false";
		String username="root";
		String pwd=  "root";
		
		try {
			 System.out.println("connecting to the dataBase : " + jdbcUrl);
			   Connection con = DriverManager.getConnection(jdbcUrl , username , pwd);
			   
			   System.out.println("connection is successful : "+con);
			    
			   System.out.println();
			   
		} catch (Exception e) {
            e.printStackTrace();
		}
	}
}





