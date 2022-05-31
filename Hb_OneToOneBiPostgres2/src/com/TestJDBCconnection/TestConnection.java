package com.TestJDBCconnection;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestConnection {

	public static void main(String[] args) {

		String jdbcUrl = "jdbc:postgresql://localhost:5432/hb_02_Bi_OneToOne?useSSL=false";
		String username = "postgres";
		String pwd = "test";

		try {
			
			System.out.println("Connecting to data Base : " + jdbcUrl);
			
			Connection con = DriverManager.getConnection(jdbcUrl, username, pwd);
			
			System.out.println("Connection is successfull :" + con);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
