package gatecrash;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.joda.time.DateTime;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


 
public class Events_EventFul {
	
	private static final String USER_AGENT = "Mozilla/5.0";
	
	// HTTP GET request
	private static void sendGet(String url) throws Exception {
 
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		// optional default is GET
		con.setRequestMethod("GET");
 
		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);
 
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
 
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		String resp = response.toString();
		Gson gson=new Gson();
		JsonElement element = gson.fromJson (response.toString(), JsonElement.class);
		//Events_TimeOut ev = g.fromJson(response.toString(), Events_TimeOut.class);
		JsonObject jObject = element.getAsJsonObject();
		//JsonArray jArray = element.getAsJsonArray();
		//System.out.println(jObject.toString());
		//JsonArray jArray = jObject.getAsJsonArray("events");
		Integer page_count = Integer.parseInt(jObject.get("page_count").getAsString());
//		for ( int pg =0; pg < page_count;pg++)
//		{
		JsonObject jobj = jObject.getAsJsonObject("events");
		JsonArray ja = jobj.getAsJsonArray("event");
		for(int i=0;i<ja.size();i++)
		{
			Event ev =  new Event();
			ev.name = ja.get(i).getAsJsonObject().get("title").getAsString();
			try
			{
			ev.description = ja.get(i).getAsJsonObject().get("description").getAsString();
			}
			catch(Exception e)
			{
				ev.description = " ";
			}
			ev.createdByID = 1;
			ev.ownedByID = 1;
			ev.save();
			int lastInsertId = ev.lastInsertID();
			System.out.println(lastInsertId);
			
			EventInstance ei = new EventInstance();
			ei.parentEvent = lastInsertId;
			ei.sponsorStatus = " ";
			ei.latitude = Double.parseDouble(ja.get(i).getAsJsonObject().get("latitude").getAsString());
			ei.longitude = Double.parseDouble(ja.get(i).getAsJsonObject().get("longitude").getAsString());
			ei.timeBegin = DateTime.now();
			ei.timeEnd = DateTime.now();
			ei.timeZone = -4.0;
			ei.save();
//		String id = ja.get(i).getAsJsonObject().get("id").getAsString();
//		System.out.println(id);
//		System.out.println(ja.get(i).getAsJsonObject().get("description").getAsString());
//		System.out.println(ja.get(i).getAsJsonObject().get("title").getAsString());
//		System.out.println(ja.get(i).getAsJsonObject().get("owner").getAsString());
//		System.out.println(ja.get(i).getAsJsonObject().get("created").getAsString());
//		System.out.println(ja.get(i).getAsJsonObject().get("modified").getAsString());
//		System.out.println(ja.get(i).getAsJsonObject().get("latitude").getAsString());
//		System.out.println(ja.get(i).getAsJsonObject().get("longitude").getAsString());
//		System.out.println(ja.get(i).getAsJsonObject().get("start_time").getAsString());
//		try{
//		System.out.println(ja.get(0).getAsJsonObject().get("stop_time").getAsString());
//		}
//		catch(Exception e)
//		{
//			System.out.println("null");
//		}
		}
//		}
		//System.out.println(jObject.getAsJsonObject("events").getAsString());
		//getAsJsonObject().get("id").getAsString());
		//print result
		//System.out.println(response.toString());
//		for (int i = 0; i < jArray.size(); i++) {
//	        JsonObject jObj = jArray.getAsJsonObject();
//	        System.out.println(i + " id : " + jObj.getAsString()("id"));
//	        //System.out.println(i + " att1 : " + jObj.getDouble("att1"));
//	        //System.out.println(i + " att2 : " + jObj.getBoolean("att2"));
//	}
	}
	
	
	
	public static void main(String args[])throws Exception
	{
		for(int i=1;i<10;i++)
		sendGet("http://api.eventful.com/json/events/search?&app_key=rgtcWVhPhS9pZtWs&l=new+york+city&page_size=100&page_number="+i);
	}
}