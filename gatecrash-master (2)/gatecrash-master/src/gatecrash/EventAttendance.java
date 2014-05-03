package gatecrash;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import org.joda.time.DateTime;

public class EventAttendance {
	public int ID;
	public int eventInstanceID;
	public int userID;
	public String referralMode;
	private boolean isNew = true;
	
	public EventAttendance() {
		
	}
	
	public static EventAttendance fromID(int ID) throws SQLException {
		DBWrapper db = new DBWrapper();
		
		PreparedStatement st = db.getPreparedStatement("SELECT e.ID as ID, e.EventInstance as EventInstance, e.User as User, e.ReferralMode as ReferralMode FROM EventAttendance e WHERE e.ID=? LIMIT 1");
		st.setInt(1, ID);
		
		ResultSet rs = db.executeThisQuery(st);
		
		if(rs.first()) {
			EventAttendance e = new EventAttendance();
			e.ID = rs.getInt("ID");
			e.eventInstanceID = rs.getInt("EventInstance");
			e.userID = rs.getInt("User");
			e.referralMode = rs.getString("ReferralMode");
			e.isNew = false;
			
			rs.close();
			return e;
		}
		
		rs.close();
		return null;
	}
	
	public static ArrayList<EventAttendance> fromUserID(int uID) throws SQLException {
		DBWrapper db = new DBWrapper();
		ArrayList<EventAttendance> ret = new ArrayList<EventAttendance>();
		
		PreparedStatement st = db.getPreparedStatement("SELECT e.ID as ID, e.EventInstance as EventInstance, e.User as User, e.ReferralMode as ReferralMode FROM EventAttendance e WHERE e.User=?");
		st.setInt(1, uID);
		ResultSet rs = db.executeThisQuery(st);
		
		while(rs.next()) {
			EventAttendance e = new EventAttendance();
			e.ID = rs.getInt("ID");
			e.eventInstanceID = rs.getInt("EventInstance");
			e.userID = rs.getInt("User");
			e.referralMode = rs.getString("ReferralMode");
			e.isNew = false;
			
			ret.add(e);
		}
		
		rs.close();
		return ret;
	}
	
	public static ArrayList<EventAttendance> fromEventInstanceID(int eiID) throws SQLException {
		DBWrapper db = new DBWrapper();
		ArrayList<EventAttendance> ret = new ArrayList<EventAttendance>();
		
		PreparedStatement st = db.getPreparedStatement("SELECT e.ID as ID, e.EventInstance as EventInstance, e.User as User, e.ReferralMode as ReferralMode FROM EventAttendance e WHERE e.EventInstance=?");
		st.setInt(1, eiID);
		ResultSet rs = db.executeThisQuery(st);
		
		while(rs.next()) {
			EventAttendance e = new EventAttendance();
			e.ID = rs.getInt("ID");
			e.eventInstanceID = rs.getInt("EventInstance");
			e.userID = rs.getInt("User");
			e.referralMode = rs.getString("ReferralMode");
			e.isNew = false;
			
			ret.add(e);
		}
		
		rs.close();
		return ret;
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
	
	public boolean save() throws SQLException {
		DBWrapper db = new DBWrapper();
		
		if(!isNew) {
			PreparedStatement st = db.getPreparedStatement("UPDATE EventAttendance SET User=? ReferralMode=? EventInstance=? WHERE ID=?");
			st.setInt(1, userID);
			st.setString(2, referralMode);
			st.setInt(3, eventInstanceID);
			st.setInt(4, ID);
						
			return st.execute();
		} else {
			PreparedStatement st = db.getPreparedStatement("INSERT INTO EventInstance(User, ReferralMode, EventInstance) VALUES(?, ?, ?)");
			
			st.setInt(1, userID);
			st.setString(2, referralMode);
			st.setInt(3, eventInstanceID);
			
			if(st.execute()) {
				isNew = false;
				return true;
			}
		}
		return false;
	}
	
	public boolean delete() throws SQLException {
		if(!isNew) {
			DBWrapper db = new DBWrapper();
			PreparedStatement st = db.getPreparedStatement("DELETE FROM EventInstance WHERE ID=?");
			st.setInt(1, ID);
			if(st.execute()) {
				isNew = true;
				return true;
			}
		}
		return false;
	}
	
}
