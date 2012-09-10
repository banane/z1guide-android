package com.banane.z1guide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Application;
import android.util.Log;

public class Guide extends Application {


	  public ArrayList<Program> programsArray;
	  public ArrayList<String> programTypesArray;
	  private ArrayList<Artist> artistsArray;
	  public ArrayList<Venue> venuesArray;
	  public ArrayList<String> venueNameArray;
	  private  String apiUrl = "http://api.zero1.org/v1";

	 
	 
	  
	  public ArrayList<Program> getProgramsArray(){
		  return programsArray;
	  }
	  public ArrayList<String> getProgramTypesArray(){
		  return programTypesArray;
	  }
	 
	  public ArrayList<Artist> getArtistsArray(){
		  return artistsArray;
	  }
	  public void setArtistsArray(ArrayList<Artist> artists){
		  artistsArray = artists;
	  }
	  public ArrayList<Venue> getVenuesArray(){
		  return venuesArray;
	  }
	  
	  public ArrayList<String> getVenueNameArray(){
		  return venueNameArray;
	  }

	  public void getProgramTypes() {
		  Log.d("Guide","in guide get program types");
	    	programTypesArray = new ArrayList<String>();
	    	programsArray = new ArrayList<Program>();
	    	
	    	try{
	    		JSONObject json = new JSONObject(readApiPrograms());
	    		String programString = json.getString("programs");
	    		JSONArray jsonA = new JSONArray(programString);
	    		Log.d("guide ","array length: "+jsonA.length());
	    		for (int i = 0; i < jsonA.length(); i++) {
	    			JSONObject item = jsonA.getJSONObject(i);
	    			
	    			String programType = item.getString("type");
	    			String programName = item.getString("programname");
	    			String id = item.getString("programid");
	    			String imagePath = item.getString("picture");
	    			//serialize incoming array
	    			String artistId = "1";
	    			String venueId = item.getString("venueid");
	    			String endDate = item.getString("enddate");
	    			String startDate = item.getString("startdate");
	    			String webSite = item.getString("site");
	    			
	    			if(venueId == null || venueId.equals("")|| venueId.length()==0){
	    				venueId = "42"; // zero1 
	    			}

	    			Program thisProgram = new Program(programName, programType, id, imagePath, artistId, venueId, endDate, startDate, webSite );

	    			programsArray.add(thisProgram);
	    			if(!programTypesArray.contains(programType)){
	        			programTypesArray.add(programType);    			
	    			}
	    		}    		
	    	} catch (JSONException e){
	    		Log.e("Guide error: ",e.getMessage());
	    	}
	    	Collections.sort(programTypesArray);
	    }
	    
	    public String readApiPrograms() {
	    	String progUrl = apiUrl + "/programs";
			StringBuilder builder = new StringBuilder();
			HttpClient client = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(progUrl );
			try {
				HttpResponse response = client.execute(httpGet);
				StatusLine statusLine = response.getStatusLine();
				int statusCode = statusLine.getStatusCode();
				if (statusCode == 200) {
					Log.d("Guide","successful http call");
					HttpEntity entity = response.getEntity();
					InputStream content = entity.getContent();
					BufferedReader reader = new BufferedReader(new InputStreamReader(content));
					String line;
					while ((line = reader.readLine()) != null) {
						builder.append(line);
					}
				} else {
					Log.e("Guide", "Failed to download file");
				}
				Log.d("Guide","successful string building");
				
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Log.d("Guide","successful in call");

			return builder.toString();
		}
	    
	    public void getVenues(){
	    	Log.d("Guide","in get venues");
	    	venueNameArray = new ArrayList<String>();
	    	venuesArray = new ArrayList<Venue>();
	    	
	    	try{
	    		JSONObject jsonV = new JSONObject(readApiVenues());
	    		String venuesString = jsonV.getString("venues");
	    		Log.d("Guide","returned from readAPiVenues");
	    		JSONArray jsonA = new JSONArray(venuesString);
	    		Log.d("Guide","venue json array length: "+jsonA.length());
	    		for (int i = 0; i < jsonA.length(); i++) {
	    			JSONObject item = jsonA.getJSONObject(i);
	    			
	    			String name = item.getString("venuename");
	    			String id = item.getString("venueid");
	    			String ImagePath = item.getString("photo");
	    			String website = item.getString("website");
	    			String lat = item.getString("latitude");
	    			String lng = item.getString("longitude");
	    			String address = item.getString("address");
	    			String description = item.getString("description");
	    			


	    			Venue thisVenue = new Venue(name, id, ImagePath, description, lat, lng, address, website);
	    			venuesArray.add(thisVenue);
	        		venueNameArray.add(name);   
	    		}    		
	    	} catch (JSONException e){
	    		Log.e("Guide error: ",e.getMessage());
	    	}
	    	Collections.sort(venueNameArray);
	    }
	    
	    public String readApiVenues() {
	    	Log.d("Guide","in read api venues");
	    	String venUrl = apiUrl + "/venues";

			StringBuilder builder = new StringBuilder();
			HttpClient client = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(venUrl );
			try {
				HttpResponse response = client.execute(httpGet);
				StatusLine statusLine = response.getStatusLine();
				int statusCode = statusLine.getStatusCode();
				if (statusCode == 200) {
					Log.d("Guide","successful http call");
					HttpEntity entity = response.getEntity();
					InputStream content = entity.getContent();
					BufferedReader reader = new BufferedReader(new InputStreamReader(content));
					String line;
					while ((line = reader.readLine()) != null) {
						builder.append(line);
					}
				} else {
					Log.e("Guide", "Failed to download file");
				}
				Log.d("Guide","successful string building");
				
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Log.d("Guide","successful venue api in call");

			return builder.toString();
		}
}
