package gatecrash;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.joda.time.DateTime;

public class Event {
	
	public int ID;
	public String name;
	public String description;
	public int createdByID;
	public int ownedByID;
	public DateTime createdOn;
	public DateTime lastModifiedOn;
	public int isPrivate;
	public int isActive;
	public int isDeleted;
	private boolean isNew = true;
	
	public Event() {
		isPrivate = 0;
		isActive = 1;
		isDeleted = 0;
	}
	
	public static Event fromName(String name) throws Exception {
		DBWrapper db = new DBWrapper();
		
		PreparedStatement st = db.getPreparedStatement("SELECT e.ID as ID, e.Name as Name, e.Description as Description, e.CreatedBy as CreatedBy e.OwnedBy as OwnedBy, e.CreatedOn as CreatedOn, e.LastModifiedOn as LastModifiedOn, e.IsPrivate as IsPrivate, e.IsActive as IsActive, e.IsDeleted as IsDeleted FROM Event e WHERE e.Name=? LIMIT 1");
		st.setString(0, name);
		
		ResultSet rs = db.executeThisQuery(st);
		
		if(rs.first()) {
			Event e = new Event();
			e.ID = rs.getInt("ID");
			e.name = rs.getString("Name");
			e.description = rs.getString("Description");
			e.createdByID = rs.getInt("CreatedBy");
			e.ownedByID = rs.getInt("OwnedBy");
			e.createdOn = new DateTime((Date)rs.getTimestamp("CreatedOn"));
			e.lastModifiedOn = new DateTime((Date)rs.getTimestamp("LastModifiedOn"));
			e.isPrivate = rs.getInt("IsPrivate");
			e.isActive = rs.getInt("IsActive");
			e.isDeleted = rs.getInt("IsDeleted");
			e.isNew = false;
			
			return e;
		}
		return null;
	}
	
	public static Event fromID(int ID) throws Exception {
		DBWrapper db = new DBWrapper();
		
		PreparedStatement st = db.getPreparedStatement("SELECT e.ID as ID, e.Name as Name, e.Description as Description, e.CreatedBy as CreatedBy e.OwnedBy as OwnedBy, e.CreatedOn as CreatedOn, e.LastModifiedOn as LastModifiedOn, e.IsPrivate as IsPrivate, e.IsActive as IsActive, e.IsDeleted as IsDeleted FROM Event e WHERE e.ID=? LIMIT 1");
		st.setInt(0, ID);
		
		ResultSet rs = db.executeThisQuery(st);
		
		if(rs.first()) {
			Event e = new Event();
			e.ID = rs.getInt("ID");
			e.name = rs.getString("Name");
			e.description = rs.getString("Description");
			e.createdByID = rs.getInt("CreatedBy");
			e.ownedByID = rs.getInt("OwnedBy");
			e.createdOn = new DateTime((Date)rs.getTimestamp("CreatedOn"));
			e.lastModifiedOn = new DateTime((Date)rs.getTimestamp("LastModifiedOn"));
			e.isPrivate = rs.getInt("IsPrivate");
			e.isActive = rs.getInt("IsActive");
			e.isDeleted = rs.getInt("IsDeleted");
			e.isNew = false;
			
			return e;
		}
		
		return null;
	}
	
	public int lastInsertID() throws Exception {
		DBWrapper db = new DBWrapper();
		
		PreparedStatement st = db.getPreparedStatement("SELECT LAST_INSERT_ID() as ID");
		
		ResultSet rs = db.executeThisQuery(st);
		
		if(rs.first()) {
			return rs.getInt("ID");
		}
		
		return -1;
	}
	
	public boolean save() throws Exception {
		DBWrapper db = new DBWrapper();
		
		if(!isNew) {
			PreparedStatement st = db.getPreparedStatement("UPDATE Event SET Name=? Description=? CreatedBy=? OwnedBy=? LastModifiedOn=? IsPrivate=? IsActive=? IsDeleted=? WHERE ID=?");
			st.setString(1, name);
			st.setString(2, description);
			st.setInt(3, createdByID);
			st.setInt(4, ownedByID);
			
			st.setTimestamp(4, new Timestamp((new DateTime()).getMillis()));
			st.setInt(6, isPrivate);
			st.setInt(7, isActive);
			st.setInt(8, isDeleted);
			st.setInt(9, ID);
			return st.execute();
		} else {
			PreparedStatement st = db.getPreparedStatement("INSERT INTO Event(Name, Description, CreatedBy, OwnedBy, CreatedOn, LastModifiedOn, IsPrivate, IsActive, IsDeleted) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)");
			st.setString(1, name);
			st.setString(2, description);
			st.setInt(3, createdByID);
			st.setInt(4, ownedByID);
			st.setTimestamp(5, new Timestamp((new DateTime()).getMillis()));
			st.setTimestamp(6, new Timestamp((new DateTime()).getMillis()));
			st.setInt(7, isPrivate);
			st.setInt(8, isActive);
			st.setInt(9, isDeleted);
			if(st.execute()) {
				isNew = false;
				return true;
			}
		}
		return false;
	}
	
	public boolean delete() throws Exception {
		if(!isNew) {
			DBWrapper db = new DBWrapper();
			PreparedStatement st = db.getPreparedStatement("DELETE FROM Event WHERE ID=?");
			st.setInt(0, ID);
			if(st.execute()) {
				isNew = true;
				return true;
			}
		}
		return false;
	}
	
}
