package com.minhtan.usermanagement.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtils {
	private static final String jdbcURL = "jdbc:mysql://localhost:3306/jsp_servlet_mysql_crud";
	private static final String jdbcUser = "root";
	private static final String jdbcPass = "root";

	public static Connection getConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}

	public static void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("SQLState: " + ((SQLException) e).getSQLState());
				System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
				System.err.println("Message: " + e.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause: " + t);
					t = t.getCause();
				}
			}
		}
	}
}
