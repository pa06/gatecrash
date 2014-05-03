package gatecrash;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBWrapper {
	
	private static Connection connect = null;
	
	public DBWrapper() {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			if(connect == null) {
				connect = DriverManager.getConnection("jdbc:mysql://gatecrash.ckx8ns18wd6j.us-west-2.rds.amazonaws.com/gatecrash?" + "user=gateyroots&password=GateyRoots!cbd");
			}
		} catch (Exception e) {
			
		}
	}
	
	public PreparedStatement getPreparedStatement(String sql) {
		if(connect != null) {
			try {
				return connect.prepareStatement(sql);
			} catch (SQLException e) {
			}
		}
		return null;
	}
	
	public ResultSet executeThisQuery(PreparedStatement query) {
		Statement statement = null;
		ResultSet resultSet = null;
		
		try {

			Class.forName("com.mysql.jdbc.Driver");

			if(connect == null) {
				connect = DriverManager.getConnection("jdbc:mysql://gatecrash.ckx8ns18wd6j.us-west-2.rds.amazonaws.com/gatecrash?" + "user=gateyroots&password=GateyRoots!cbd");
			}
			
			statement = connect.createStatement();

			query.execute();
			
			resultSet = query.getResultSet();
			return resultSet;
			
		} catch (Exception e) {
			//TODO
		} finally {
			try {
				statement.close();
			} catch (Exception e) {
				
			}
		}
		return resultSet;
	}
	
	

}
