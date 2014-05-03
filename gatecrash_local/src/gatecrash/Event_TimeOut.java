package gatecrash;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.joda.time.DateTime;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.*;
import org.jsoup.select.Elements;
public class Event_TimeOut {

	
	
private static final String USER_AGENT = "Mozilla/5.0";
	
	// HTTP GET request
		static int total_size =10;
	
	private static int sendGet(String url) throws Exception {
		
 
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
		//System.out.println(response);
		Document doc = Jsoup.parse(resp);
		//<div class="totalResults"><strong>178</strong> results found.</div>
		Elements li = doc.getElementsByClass("totalResults");
		//Elements l = doc.getElementsByTag("strong");
		for (Element a:li)
		{
			//Elements e = a.getElementsByTag("strong");
			
			//System.out.println(e.text());
			
			total_size = Integer.parseInt(a.getElementsByTag("strong").text());
			
			//System.out.println(a.toString());
		//System.out.println(li.attr("strong"));
		}
		//Element content = doc.getElementById("tiles roundBox module");
		return(total_size);
		
//		Gson gson=new Gson();
//		JsonElement element = gson.fromJson (response.toString(), JsonElement.class);
//		//Event_TimeOut ev = g.fromJson(response.toString(), Event_TimeOut.class);
//		JsonObject jObject = element.getAsJsonObject();
//		//JsonArray jArray = element.getAsJsonArray();
//		//System.out.println(jObject.toString());
//		//JsonArray jArray = jObject.getAsJsonArray("events");
//		Integer page_count = Integer.parseInt(jObject.get("page_count").getAsString());
////		for ( int pg =0; pg < page_count;pg++)
////		{
//		JsonObject jobj = jObject.getAsJsonObject("events");
//		JsonArray ja = jobj.getAsJsonArray("event");
//		for(int i=0;i<ja.size();i++)
//		{
//		String id = ja.get(i).getAsJsonObject().get("id").getAsString();
//		System.out.println(id);
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
//		}
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
	
	public static void sendGetwithPagesize(String url)throws Exception
	{
		
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
		//System.out.println(response);
		Document doc = Jsoup.parse(resp);
	Elements links = doc.getElementsByClass("topSection");
	//System.out.println(links.toString());
	//int count=0;
	for (Element link : links) {
		//link.getElementsByAttribute("href").attr("href");
		getEventInfo(link.getElementsByAttribute("href").attr("href"));
		//System.out.println(link.toString());
		
//	  String linkHref = link.attr("h3");
//	  System.out.println(linkHref);
//	  String linka = link.attr("a");
//	  System.out.println(linka);
//	  System.out.println(link.attr("href"));
//	  String linkText = link.text();
	}
	}
	
	public static void getEventInfo(String url)throws Exception
	{
		
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
		//System.out.println(response);
		Document doc = Jsoup.parse(resp);
		
//		Pattern regex = Pattern.compile("class[ \t\n]*=[ \t\n]*\"(.*)\"");
//		
//		regex.ma
//		
//		resp.replaceAll(regex, "class="+)
		
		//resp = resp.replaceAll("class\s*=\s*\"\"", "")
		
		//System.out.println(doc.getElementById("headerWrapper").toString());
		//System.out.println(doc.getElementsByClass("name").text());
		try
		{
			String x = doc.select("div.expandable.module").select("p").last().text();
			String suffix = "By Time Out Partner";
			if(x.endsWith("By Time Out Partner"))
			{
				x = x.substring(0, x.length() - suffix.length());
			}
			Event ev =  new Event();
			ev.name = doc.getElementsByClass("name").text();
			ev.description = x;
			ev.createdByID = 2;
			ev.ownedByID = 2;
			ev.save();
			int lastInsertId = ev.lastInsertID();
			System.out.println(lastInsertId);
			
			EventInstance ei = new EventInstance();
			ei.parentEvent = lastInsertId;
			ei.sponsorStatus = " ";
			
			String coordinates = doc.getElementsByClass("coordinates").text();

			String[] coods = new String[2];
			
			if(coordinates.trim() != "") {
				coods = coordinates.split(",");
				
				if(coods.length < 2) {
					coods = new String[2];
					coods[0] = "40.0";
					coods[1] = "-73.0";
				}
				
			} else {
				coods[0] = "40.0";
				coods[1] = "-73.0";
			}
			//System.out.println(coods[0]+" "+coods[1]);
			ei.latitude = Double.parseDouble(coods[0]);
			ei.longitude = Double.parseDouble(coods[1]);
			ei.timeBegin = DateTime.now();
			ei.timeEnd = DateTime.now();
			ei.timeZone = -4.0;
			ei.save();
			//System.out.println(x);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			//System.out.println(doc.getElementsByClass("address").toString());
		}
		
	}
	
	
	public static void main(String args[])throws Exception
	{
		int page_size = sendGet("http://www.timeout.com/newyork/search?_source=global&_dd=&page_zone=events-festivals&keyword=&section=events-festivals&on=period&s_date=2014-04-26&e_date=2014-05-31&location=&_section_search=events-festivals&_route=search&_route_params%5Bsite%5D=newyork&_route_params%5B_locale%5D=en_US&type=event");
		
		sendGetwithPagesize("http://www.timeout.com/newyork/search?_source=global&_dd=&page_zone=events-festivals&keyword=&section=events-festivals&on=period&s_date=2014-04-26&e_date=2014-05-31&location=&_section_search=events-festivals&_route=search&_route_params%5Bsite%5D=newyork&_route_params%5B_locale%5D=en_US&type=event&page_size="+page_size);
		//for(int i=0;i<10;i++)
		//sendGet("http://www.timeout.com/newyork/search?_source=global&_dd=&page_zone=events-festivals&keyword=&section=events-festivals&on=period&s_date=2014-04-26&e_date=2014-05-31&location=&_section_search=events-festivals&_route=search&_route_params%5Bsite%5D=newyork&_route_params%5B_locale%5D=en_US&type=event&page_number="+i);
	}
	
}
