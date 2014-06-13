package com.timothy.loancalculator.initializers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TestDb {

	public static void main(String[] args) {
		
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			DriverManager.getConnection("jdbc:mysql://localhost:3306/");
			conn = DriverManager.getConnection (args[0], args[1], args[2]);
			stmt = conn.createStatement();
			
			int re = stmt.executeUpdate("CREATE SCHEMA IF NOT EXISTS `test`");
			
			//System.out.println("retur = "+re);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try{
				stmt.close();
			}catch(Exception e){}
			try{
				conn.close();
			}catch(Exception e){}
		}

	}
	
	
	
}
