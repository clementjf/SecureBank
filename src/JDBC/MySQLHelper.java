package JDBC;

import java.sql.*;

public class MySQLHelper {
	public static final String url = "jdbc:mysql://localhost:3306/atm"; 
	public static final String name = "com.mysql.cj.jdbc.Driver"; 
	public static final String user = "root"; 
	public static final String password = ""; 

	public Connection connection = null; 
	public PreparedStatement preparedStatement = null; 

	public MySQLHelper()
	{
		try
		{
			Class.forName(name);
			connection = DriverManager.getConnection(url, user, password);
			System.out.println("Database connection completed..");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			this.connection.close();
			this.preparedStatement.close();
		} catch (SQLException e) {
			System.out.println("Database connection failed!!");
			e.printStackTrace();
		}
	}

	
	public ResultSet query(String sql) {
		ResultSet resultSet = null;

		try {
			preparedStatement = connection.prepareStatement(sql); 
			resultSet = preparedStatement.executeQuery();

		} catch (Exception e) {
			System.out.println("Query error!!");
			e.printStackTrace();
		}
		return resultSet;
	}

	
	public boolean update(String sql) {
		boolean flag = false;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.executeUpdate();
			flag = true;

		} catch (Exception e) {
			System.out.println("Update error!!");
			e.printStackTrace();
		}
		return flag;
	}

	public int getCount(String sql) {
		int count = 0;
		try {
			preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.last();
			count = resultSet.getRow();
			resultSet.close();

		} catch (Exception e) {
			System.out.println("Get total result counts failed!");
			e.printStackTrace();
		}
		return count;
	}
}
