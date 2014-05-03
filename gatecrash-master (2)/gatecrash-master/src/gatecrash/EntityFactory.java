package gatecrash;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EntityFactory {
	
	DBWrapper db;
	
	public EntityFactory() {
		if(db == null) {
			db = new DBWrapper();
		}
	}
	
	public Event eventFromID(int ID) {
		return null; //TODO
	}
	
	public RegularUser regularUserFromUserName(String uName) throws Exception {
		
		try {
			PreparedStatement query = db.getPreparedStatement("SELECT * FROM User WHERE Username = ?");
			query.setString(0,  uName);
			
			ResultSet returnVals = query.executeQuery();
			
			if(returnVals.first()) {
				if(returnVals.getInt("IsDeleted") != 1) {
					User userDetails = new User();
					userDetails.ID = returnVals.getInt("ID");
					userDetails.userName = returnVals.getString("Username");
					userDetails.email = returnVals.getString("Email");
					userDetails.salt = returnVals.getString("Salt");
					userDetails.passHash = returnVals.getString("PassHash");
					userDetails.isActive = returnVals.getInt("IsActive");
					
					PreparedStatement query2 = db.getPreparedStatement("SELECT * FROM RegularUser WHERE UserDetails = ?");
					query2.setInt(0, returnVals.getInt("ID"));
					
					ResultSet returnVals2 = query2.executeQuery();
					
					if(returnVals2.first()) {
						RegularUser u = new RegularUser();
						u.city = returnVals2.getString("City");
						u.facebookID = returnVals2.getString("FacebookID");
						u.yearOfBirth = returnVals2.getInt("YearOfBirth");
						u.ID = returnVals2.getInt("ID");
						u.positveKarma = returnVals2.getInt("PositiveKarma");
						u.negativeKarma = returnVals2.getInt("NegativeKarma");
						
						return u;
					}
				}
			}
		} catch(Exception e) {
			
		}
		
		return null;
		
	}
	
	public boolean saveRegularUser(RegularUser u) {
		
		try {
			PreparedStatement query = db.getPreparedStatement("UPDATE RegularUser SET YearOfBirth = ?, City = ?, PositiveKarma = ?, NegativeKarma = ?, FacebookID = ? WHERE ID = ?");
			query.setInt(0, u.yearOfBirth);
			query.setString(0, u.city);
			query.setInt(2,  u.positveKarma);
			query.setInt(3, u.negativeKarma);
			query.setString(4,  u.facebookID);
			query.setInt(5, u.ID);
			
			if(query.execute()) {
				
				PreparedStatement query2 = db.getPreparedStatement("UPDATE User SET Username = ?, Email = ?, Salt = ?, PassHash = ?, IsActive = ?, IsDeleted = ? WHERE ID = ?");
				query2.setString(0,  u.userDetails.userName);
				query2.setString(1,  u.userDetails.email);
				query2.setString(2,  u.userDetails.salt);
				query2.setString(3,  u.userDetails.passHash);
				query2.setInt(4,  u.userDetails.isActive);
				query2.setInt(5,  u.userDetails.isDeleted);
				query2.setInt(6,  u.userDetails.ID);
				
				return query2.execute();
			}
			
			return false;
			
		} catch (Exception e) {
			
		}
		
		return false;
	}
	
	
}
