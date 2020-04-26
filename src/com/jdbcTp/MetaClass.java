package com.jdbcTp;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Scanner;

import javax.sql.DataSource;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class MetaClass {
	

	
	public static void main(String[] args) {
		Connection connextion = null;
		Statement statement =null;
		ResultSet resultSet = null;
		PreparedStatement preparestatement = null;
		CallableStatement callableStatement = null;
		DatabaseMetaData databaseMetaData = null;
		ResultSetMetaData resultSetMetaData = null;
		
		Scanner sc = null;
		try {
			// connextion = DriverManager.getConnection(JDBC_URL, JDBC_USER_NAME, JDBC_USER_PASSWORD);
			
			connextion = DataSourceManager.getMysqlDataSource();
			databaseMetaData = connextion.getMetaData();
			System.out.println("DatabaseProductName: "+databaseMetaData.getDatabaseProductName());
			System.out.println("DatabaseProductVersion: "+databaseMetaData.getDatabaseProductVersion());
			System.out.println("DriverName: "+databaseMetaData.getDriverName());
			System.out.println("DriverVersion: "+databaseMetaData.getDriverVersion());
			System.out.println("Schema : "+connextion.getSchema());
			
			resultSet  = databaseMetaData.getTables(null, null, null, null);
			System.out.println("--Tables--");
			while(resultSet.next()) System.out.println("\t"+resultSet.getString("TABLE_NAME"));
			resultSet = databaseMetaData.getColumns(null, null,"employees", null);
			System.out.println("--Colums Employees--");
			while(resultSet.next()) System.out.println("\t"+resultSet.getString("COLUMN_NAME"));
			
			preparestatement = connextion.prepareStatement("Select * from employees Where id=?");
			preparestatement.setInt(1, 3);
			resultSet = preparestatement.executeQuery();
			if(resultSet.next()) System.out.println("-Result preparestatement : "+resultSet.getString(4)
											+" -> "+resultSet.getString(5));
			
			callableStatement = connextion.prepareCall("Select * from employees where id = ?");
			callableStatement.setInt(1, 4);
			resultSet = callableStatement.executeQuery();
			if(resultSet.next()) System.out.println("-Result callableStatement : "+resultSet.getString(4)
										+" -> "+resultSet.getString(5));
			
			
			connextion.setAutoCommit(false);
			statement = connextion.createStatement();
				// 55000.00 $
				int nbChanges = statement.executeUpdate("update employees set salary=999999.99 where id=1");
				if(nbChanges > 0){
					System.out.print("would you like to comit the chnages ? yes /no : ");
					sc = new Scanner(System.in);  
						String val = sc.next();
					if(val.equalsIgnoreCase("yes")){ connextion.commit(); System.out.println("\t Comite suscess !");}
					else{connextion.rollback(); System.out.println("\t rollback value !");}
				}else{
					System.out.println("No Changes !");
				}
			
				connextion.setAutoCommit(true);
				
				resultSet = statement.executeQuery("Select * from employees ");
				
				resultSetMetaData = resultSet.getMetaData();
				int nbOfColumns = resultSetMetaData.getColumnCount();
				System.out.println("######################################");
				System.out.println("Nb of Columns : "+nbOfColumns);
				System.out.println("View data infos :");
				for(int i=1;i<=nbOfColumns;i++)
					System.out.println("-> \n"
							+" \n\t#TableName: "+resultSetMetaData.getTableName(i)
							+" \n\t#ColumnName: "+resultSetMetaData.getColumnName(i)
							+" \n\t#ColumnType: "+resultSetMetaData.getColumnType(i)
							+" \n\t#ColumnTypeName: "+resultSetMetaData.getColumnTypeName(i)
							+" \n\t#ColumnLabel: "+resultSetMetaData.getColumnLabel(i)
							+" \n\t#ColumnDisplaySize: "+resultSetMetaData.getColumnDisplaySize(i)
							+" \n\t#isAutoInc: "+resultSetMetaData.isAutoIncrement(i)
							+" \n\t#isNullable: "+resultSetMetaData.isNullable(i)
							+" \n\t#isReadOnly: "+resultSetMetaData.isReadOnly(i)
							);
				
				
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			closeConnextion(connextion);
			if(sc != null) sc.close();
		}
	}
	
	public static void closeConnextion(Connection connextion){
		try {
			if(connextion != null) connextion.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
