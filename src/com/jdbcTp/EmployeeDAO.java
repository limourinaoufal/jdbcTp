package com.jdbcTp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class EmployeeDAO {
	
	private Connection connection = null;

	public EmployeeDAO() throws FileNotFoundException, IOException, SQLException {
		Properties properties = new Properties();
		properties.load(new FileInputStream(new File("demo.properties")));
		String url = properties.getProperty("url");
		String login = properties.getProperty("login");
		String pasword = properties.getProperty("password");
		connection = DriverManager.getConnection(url,login,pasword);
		System.out.println(".#Open JDBC Connetion ... OK. ");
	}
	
	private static void closeConnextion(Connection connection)throws SQLException{
		if(connection != null) 
			{	connection.close();
				System.out.println(".#Close JDBC Connection ... OK ( END.)");
			}
	}
	
	public List<Employee> getAllEmployees() throws SQLException{
		List<Employee> listEmployees = new ArrayList<Employee>();
		Statement statement = connection.createStatement(); 
		ResultSet resultSet = statement.executeQuery("Select * from employees");
		while(resultSet.next()){
			Employee employee = generateEmployeeFromResultSet(resultSet);
			listEmployees.add(employee);
		}
		closeResultAndStatement(resultSet, statement);
		
		return listEmployees;
	}

	private Employee generateEmployeeFromResultSet(ResultSet resultSet)
			throws SQLException {
		return new Employee(
				resultSet.getInt("id"),
				resultSet.getString("last_name"),
				resultSet.getString("first_name"),
				resultSet.getString("email"),
				resultSet.getString("department"),
				resultSet.getDouble("salary")
				);
	}
	
	public List<Employee> searchEmployees(String lastName) throws SQLException{
		List<Employee> listEmployees = new ArrayList<Employee>();
		
		PreparedStatement preparedStatement = connection.prepareStatement("Select * from employees where last_name like ?");
		preparedStatement.setString(1, "%"+lastName+"%");
		ResultSet resultSet = preparedStatement.executeQuery();
		while(resultSet.next()){
			Employee employee = generateEmployeeFromResultSet(resultSet);
			listEmployees.add(employee);
		}
		closeResultAndStatement(resultSet, preparedStatement);
		
		return listEmployees;
	}
	
	private static void closeResultAndStatement(ResultSet res , Statement stat) throws SQLException{
		if(res!=null) res.close();
		if(stat != null ) stat.close();
	}
	
	public void closeConnextion() throws SQLException{
		if(this.connection != null) closeConnextion(connection);
	}
	
	public void addNewEmployee(Employee e) throws SQLException{
		PreparedStatement pStatement = connection.prepareStatement(""
				+ "INSERT INTO employees (last_name,first_name,email,department,salary) "
				+ "VALUES (?,?,?,?,?)");
		pStatement.setString(1,e.getLastName());
		pStatement.setString(2, e.getFirstName());
		pStatement.setString(3, e.getEmail());
		pStatement.setString(4, e.getDepartement());
		pStatement.setDouble(5, e.getSalary());
		
		pStatement.executeUpdate();
		
		closeResultAndStatement(null, pStatement);
	}
	
	
	
	public static void main(String[] args) throws SQLException, FileNotFoundException, IOException {
		 EmployeeDAO employeeDAO = new EmployeeDAO();
		 System.out.println("\t-> "+employeeDAO.getAllEmployees());
		 System.out.println("\t-> "+employeeDAO.searchEmployees("bli"));
		 closeConnextion(employeeDAO.connection);
		
	}
}
