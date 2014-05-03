package gatecrash;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
	
	public int ID;
	public String userName;
	public String email;
	public String passHash;
	public String salt;
	public int isActive;
	public int isDeleted;
	private boolean isNew = true;
	
	public User() {
		isActive = 1;
		isDeleted = 0;
	}
	
	public static User fromUsername(String uName) throws SQLException {
		DBWrapper db = new DBWrapper();
		
		PreparedStatement st = db.getPreparedStatement("SELECT u.ID as ID u.Username as Username, u.Email as Email, u.PassHash as PassHash, u.IsActive as IsActive, u.IsDeleted as IsDeleted FROM User u WHERE u.Username = ? LIMIT 1");
		st.setString(1, uName);
		
		ResultSet rs = db.executeThisQuery(st);
		
		if(rs.first()) {
			User u = new User();
			
			u.ID = rs.getInt("ID");
			u.userName = rs.getString("Username");
			u.email = rs.getString("Email");
			u.passHash = rs.getString("PassHash");
			u.isActive = rs.getInt("IsActive");
			u.isDeleted = rs.getInt("IsDeleted");
			u.isNew = false;
			
			rs.close();
			return u;
		}
		
		rs.close();
		return null;
	}
	
	public int lastInsertID() throws SQLException {
		DBWrapper db = new DBWrapper();
		
		PreparedStatement st = db.getPreparedStatement("SELECT LAST_INSERT_ID() as ID");
		
		ResultSet rs = db.executeThisQuery(st);
		
		if(rs.first()) {
			int ret = rs.getInt("ID");
			rs.close();
			return ret;
		}
		
		rs.close();
		return -1;
	}
	
}
