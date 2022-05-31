package com.TestJDBCconnection;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestConnection {

	public static void main(String[] args) {

		String jdbcUrl = "jdbc:postgresql://localhost:5432/hb03OneToMany?useSSl=false";
		String username = "postgres";

		String pwd = "test";

		try {
			System.out.println("Connecting to data base : " + jdbcUrl);
			Connection con = DriverManager.getConnection(jdbcUrl, username, pwd);

			System.out.println("Connection successFully Done : " + con);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
