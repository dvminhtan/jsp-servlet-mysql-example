package com.minhtan.usermanagement.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.minhtan.usermanagement.model.User;
import com.minhtan.usermanagement.utils.JDBCUtils;
import com.mysql.cj.protocol.Resultset;

public class UserDao {

	// define sql statements
	private static final String INSERT_USERS_SQL = "INSERT INTO Users" + "(name, email,country) values" + "(?,?,?)";
	private static final String SELECT_USER_BY_ID = "select id,name,email,country from user where id=?";
	private static final String SELECT_ALL_USERS = "select * from user";
	private static final String DELETE_USER_SQL = "delete from user where id=?";
	private static final String UPDATE_USER_SQL = "update user set name = ?,email = ?,coountry = ? where id=?";

	// insert record in database
	public void insertUser(User user) {
		// try-with-resource statement here
		try (Connection connection = JDBCUtils.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL);) {
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getEmail());
			preparedStatement.setString(3, user.getCountry());
			preparedStatement.executeUpdate();
		}catch (SQLException e) {
            printSQLException(e);
        }
	}

	// select user from database
	public User selectUser(int id) {
		User user = null;
		try (Connection connection = JDBCUtils.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);) {
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				String name = resultSet.getString("name");
				String email = resultSet.getString("email");
				String country = resultSet.getString("country");
				user = new User(id,name, email, country);
			}
		} catch (SQLException e) {
            printSQLException(e);
        }
		return user;
	}

	// select all users from databse
	   public List < User > selectAllUsers() {

	        // using try-with-resources to avoid closing resources (boiler plate code)
	        List < User > users = new ArrayList < > ();
	        // Step 1: Establishing a Connection
	        try (Connection connection = JDBCUtils.getConnection();

	            // Step 2:Create a statement using connection object
	            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);) {
	            System.out.println(preparedStatement);
	            // Step 3: Execute the query or update query
	            ResultSet rs = preparedStatement.executeQuery();

	            // Step 4: Process the ResultSet object.
	            while (rs.next()) {
	                int id = rs.getInt("id");
	                String name = rs.getString("name");
	                String email = rs.getString("email");
	                String country = rs.getString("country");
	                users.add(new User(id, name, email, country));
	            }
	        } catch (SQLException e) {
	            printSQLException(e);
	        }
	        return users;
	    }

	// delete user from database
	public boolean deleteUser(int id){
		boolean rowDelete = false;
		try (Connection connection = JDBCUtils.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_SQL);) {
			preparedStatement.setInt(1, id);
			rowDelete = preparedStatement.executeUpdate() > 0;
		}catch (SQLException e) {
            printSQLException(e);
        }
		return rowDelete;
	}

	// update user in database
	public boolean updateUser(User user) {
		boolean rowUpdate = false;
		try (Connection connection = JDBCUtils.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_SQL);) {
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getEmail());
			preparedStatement.setString(3, user.getCountry());
			preparedStatement.setInt(4, user.getId());
			rowUpdate = preparedStatement.executeUpdate() > 0;
		}catch (SQLException e) {
            printSQLException(e);
        }
		return rowUpdate;
	}
	
    private void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
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
