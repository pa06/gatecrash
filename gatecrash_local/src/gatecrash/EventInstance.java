package gatecrash;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import org.joda.time.DateTime;

public class EventInstance {
	public int ID;
	public int parentEvent;
	public double latitude;
	public double longitude;
	public DateTime timeBegin;
	public DateTime timeEnd;
	public double timeZone;
	public String sponsorStatus;
	public int isActive;
	private boolean isNew = true;
	
	public EventInstance() {
		isActive = 1;
	}
	
	public static EventInstance fromID(int ID) throws Exception {
		DBWrapper db = new DBWrapper();
		
		PreparedStatement st = db.getPreparedStatement("SELECT e.ID as ID, e.ParentEvent as ParentEvent, e.Latitude as Latitude, e.Longitude as Longitude e.TimeBegin as TimeBegin, e.TimeEnd as TimeEnd, e.TimeZone as TimeZone, e.SponsorStatus as SponsorStatus, e.IsActive as IsActive FROM EventInstance e WHERE e.ID=? LIMIT 1");
		st.setInt(0, ID);
		
		ResultSet rs = db.executeThisQuery(st);
		
		if(rs.first()) {
			EventInstance e = new EventInstance();
			e.ID = rs.getInt("ID");
			e.parentEvent = rs.getInt("ParentEvent");
			e.latitude = rs.getDouble("Latitude");
			e.longitude = rs.getDouble("Longitude");
			e.timeBegin = new DateTime((Date)rs.getTimestamp("TimeBegin"));
			e.timeEnd = new DateTime((Date)rs.getTimestamp("TimeEnd"));
			e.timeZone = rs.getDouble("TimeZone");
			e.sponsorStatus = rs.getString("SponsorStatus");
			e.isActive = rs.getInt("IsActive");
			e.isNew = false;
			
			return e;
		}
		
		return null;
	}
	
	public static ArrayList<EventInstance> fromParentEventID(int pID) throws Exception {
		DBWrapper db = new DBWrapper();
		ArrayList<EventInstance> ret = new ArrayList<EventInstance>();
		
		PreparedStatement st = db.getPreparedStatement("SELECT e.ID as ID, e.ParentEvent as ParentEvent, e.Latitude as Latitude, e.Longitude as Longitude e.TimeBegin as TimeBegin, e.TimeEnd as TimeEnd, e.TimeZone as TimeZone, e.SponsorStatus as SponsorStatus, e.IsActive as IsActive FROM EventInstance e WHERE e.ParentEvent=?");
		st.setInt(0, pID);
		ResultSet rs = db.executeThisQuery(st);
		
		while(rs.next()) {
			EventInstance e = new EventInstance();
			e.ID = rs.getInt("ID");
			e.parentEvent = rs.getInt("ParentEvent");
			e.latitude = rs.getDouble("Latitude");
			e.longitude = rs.getDouble("Longitude");
			e.timeBegin = new DateTime((Date)rs.getTimestamp("TimeBegin"));
			e.timeEnd = new DateTime((Date)rs.getTimestamp("TimeEnd"));
			e.timeZone = rs.getDouble("TimeZone");
			e.sponsorStatus = rs.getString("SponsorStatus");
			e.isActive = rs.getInt("IsActive");
			e.isNew = false;
			
			ret.add(e);
		}
		
		return ret;
	}
	
	
	public boolean save() throws Exception {
		DBWrapper db = new DBWrapper();
		
		if(!isNew) {
			PreparedStatement st = db.getPreparedStatement("UPDATE EventInstance SET ParentEvent=? Latitude=? Longitude=? TimeBegin=? TimeEnd=? TimeZone=? SponsorStatus=? IsActive=? WHERE ID=?");
			st.setInt(0, parentEvent);
			st.setDouble(1, latitude);
			st.setDouble(2, longitude);
			st.setTimestamp(3, new Timestamp(timeBegin.getMillis()));
			st.setTimestamp(4, new Timestamp(timeEnd.getMillis()));
			st.setDouble(5, timeZone);
			st.setString(6, sponsorStatus);
			st.setInt(7,  isActive);
			st.setInt(8, ID);
			
			return st.execute();
		} else {
			PreparedStatement st = db.getPreparedStatement("INSERT INTO EventInstance(ParentEvent, Latitude, Longitude, TimeBegin, TimeEnd, TimeZone, SponsorStatus, IsActive) VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
			
			st.setInt(1, parentEvent);
			st.setDouble(2, latitude);
			st.setDouble(3, longitude);
			st.setTimestamp(4, new Timestamp(timeBegin.getMillis()));
			st.setTimestamp(5, new Timestamp(timeEnd.getMillis()));
			st.setDouble(6, timeZone);
			st.setString(7, sponsorStatus);
			st.setInt(8,  isActive);
			
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
			PreparedStatement st = db.getPreparedStatement("DELETE FROM EventInstance WHERE ID=?");
			st.setInt(0, ID);
			if(st.execute()) {
				isNew = true;
				return true;
			}
		}
		return false;
	}
}
