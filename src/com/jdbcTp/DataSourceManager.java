package com.jdbcTp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class DataSourceManager {
	
	static Connection conx = null;
	
	public static Connection getMysqlDataSource() throws FileNotFoundException, IOException, SQLException{
		if(conx == null){
			Properties p =  new Properties();
			p.load(new FileInputStream( new File("jdbcConfig.properties")));
			MysqlDataSource ds = new MysqlDataSource();
			ds.setUser(p.getProperty("JDBC_USER_NAME"));
			ds.setPassword(p.getProperty("JDBC_USER_PASSWORD"));
			ds.setUrl(p.getProperty("JDBC_URL"));
			conx = ds.getConnection();
		}
		return conx;
	}

}
