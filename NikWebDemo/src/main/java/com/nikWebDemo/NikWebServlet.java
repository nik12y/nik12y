package com.nikWebDemo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/NikWebServlet")
public class NikWebServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NikWebServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	 

		String jdbcUrl = "jdbc:postgresql://localhost:5432/nikWebDemo?useSSL=false";
		String username = "postgres";
		String pwd = "postgres";

		try {
			PrintWriter out = response.getWriter();

			out.println("Connecting to DataBase : " + jdbcUrl);

			Class.forName("org.postgresql.Driver");
			System.out.println("Running upto here ");
			Connection con = DriverManager.getConnection(jdbcUrl, pwd, username);

			out.println("\nsuccess!!"+con);
			System.out.println("success!!");

			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
