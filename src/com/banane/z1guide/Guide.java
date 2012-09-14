package com.banane.z1guide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.UUID;

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
import org.json.JSONStringer;

import android.app.Application;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

public class Guide extends Application  {


	  public ArrayList<Program> programsArray;
	  public ArrayList<String> programTypesArray;
	  private ArrayList<Artist> artistsArray;
	  public ArrayList<Venue> venuesArray;
	  public ArrayList<String> venueNameArray;
	  private  String apiUrl = "http://api.zero1.org/v1";
	  public int moodColor;
      public ArrayList<Program> nearbyPrograms;
      public ArrayList<Venue> nearbyVenues;
      public ArrayList<Program> currentPrograms;
      
      private boolean gps_enabled = false;
      private boolean network_enabled = false;
      private LocationListener locListener = new MyLocationListener();
      public LocationManager locationManager;
	  public String deviceid;
      public Location latestLocation;

	 
	  public void setMoodColor(int color){
		  this.moodColor = color;
	  }
	  public int getMoodColor(){
		  return moodColor;
	  }
	  public ArrayList<Program> getNearbyPrograms(){
		  return nearbyPrograms;
	  }
	  
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
	    			String artists = item.getString("artists");
	    			String venueId = item.getString("venueid");
	    			String endDate = item.getString("enddate");
	    			String startDate = item.getString("startdate");
	    			String webSite = item.getString("site");
	    			
	    			if(venueId == null || venueId.equals("")|| venueId.length()==0){
	    				venueId = "42"; // zero1 
	    			}

	    			Program thisProgram = new Program(programName, programType, id, imagePath, artists, venueId, endDate, startDate, webSite );

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
	    
	    
	    public void setupLocation(){
	    	Log.d("Guide","setting up location");
	    	// Acquire a reference to the system Location Manager
	    	locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
	    	try {
	    		
	    		gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	    	} catch (Exception ex) {
	    		Log.e("Guide","exception: "+ex.getMessage());
	    	}
	    	try {
	    		network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
	    	} catch (Exception ex) {
	    		Log.e("Guide","exception: "+ex.getMessage());
	    	}
	    	
	    	if(gps_enabled){
	    		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locListener);
	    	}
	    	if(network_enabled){
	    		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locListener);
	    	}
	    }
	    
	    class MyLocationListener implements LocationListener {
	    	@Override
	    	public void onLocationChanged(Location location){
	    		if(location != null){
	    	    	Log.d("Guide","in onlocation changed");
	    			updateLivingMap(location);
	    		}
	    	}

			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// TODO Auto-generated method stub
				
			}
	    }
	    
	    
	    public void updateLivingMap(Location location){

	    	 this.latestLocation = location;
			 Log.d("guide","in update living map");
			 int lat = (int) (location.getLatitude());
			 int lng = (int) (location.getLongitude());

			 Guide appState = ((Guide)getApplicationContext());

			 int moodColor = appState.getMoodColor();
			 if(moodColor == 0){
				 moodColor = 1005;
			 }
			 String altitude = "100";
			 String jsonstring = "";
			    
			 JSONStringer json;

			 try{
			    	json = new JSONStringer()
			    
			    	.object()   
			    	.key("latitude").value(lat)
			    	.key("longitude").value(lng)
			    	.key("moodColor").value(moodColor)
			    	.key("altitude").value(altitude)
			    	.key("userUUID").value(appState.deviceid)
			    	.endObject();

			    	 jsonstring = URLEncoder.encode(json.toString());
			  } catch (JSONException e){
			    	Log.e("Guide","error creating json obj for location ");
			  }
			    
			  String apiurl = "http://festival.projection.ajptechnical.com?report="+jsonstring;
			  Log.d("Guide","Location change: "+apiurl);

			  HttpClient httpclient = new DefaultHttpClient();
			  HttpGet httpget = new HttpGet(apiurl);

			  try {
			        // Execute HTTP Post Request
			        HttpResponse response = httpclient.execute(httpget);
			        
			  } catch (ClientProtocolException e) {
			    	Log.e("Guide","client protocol exception with sending location"+e.getMessage());
			  } catch (IOException e) {
			    	Log.e("Guide","exception with sending location"+e.getMessage());
			  }
	    }
	    
	    public void getDeviceID(){
			try{
				final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);

			    final String tmDevice, tmSerial, androidId;
			    tmDevice = "" + tm.getDeviceId();
			    tmSerial = "" + tm.getSimSerialNumber();
			    androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

			    UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
			    deviceid = deviceUuid.toString();
			} catch (Exception e){
				Log.d("Guide","determining deviceid, no telephony, build randomly");
				int min = 1000000;
				int max = 10000000;

				Random r = new Random();
				int i1 = r.nextInt(max - min + 1) + min;
				deviceid = i1 + "";
				Log.d("Guide","deviceid: "+deviceid);
			}
		}
	    
		
		public void getCurrentPrograms(){
			currentPrograms = new ArrayList<Program>();
			for(Program thisProgram:programsArray){
				Date today = new Date(System.currentTimeMillis());
				SimpleDateFormat  format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");  
				Date startDate = null;
				
				try {
					startDate  = format.parse(thisProgram.getStartDate());
				} catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
				if(startDate.before(today)){
					this.currentPrograms.add(thisProgram);
				}
			}
		     Log.d("Guide","size of current programs: " + currentPrograms.size());

		}
		
		public void getNearbyVenues(){
			nearbyVenues = new ArrayList<Venue>();
		     // get local venues
			
	    	 Log.d("Guide","size of venues: "+this.venuesArray.size());
		     for(Venue thisVenue:this.venuesArray){
		    	 Log.d("Guide","the lat: " + thisVenue.getLat() + ", the long: "+ thisVenue.getLng());
		    	 if(thisVenue.getLat().length() > 0 && thisVenue.getLng().length() > 0){
		    		 double lat = Double.parseDouble(thisVenue.getLat());
		    		 double lng = Double.parseDouble(thisVenue.getLng());
		    		 Location venueLocation = new Location("");
		    		 venueLocation.setLatitude(lat);
		    		 Log.d("Guide","latest location:  lat:"+ latestLocation.getLatitude() + " long: " + latestLocation.getLongitude()); 		    		 venueLocation.setLongitude(lng);
		    		 float distance = latestLocation.distanceTo(venueLocation)/1000;
		    		 if(distance < 100){
		    			 this.nearbyVenues.add(thisVenue);
		    		 }
		    	 }
			 }
		     Log.d("Guide","size of nearby venues: " + nearbyVenues.size());
		}

	    public void buildNearbyPrograms(){
	    	nearbyPrograms = new ArrayList<Program>();
	    	if(currentPrograms.size() > 0 && nearbyVenues.size()>0){
	    		for(Program thisProgram:currentPrograms){
	    			for(Venue thisVenue:nearbyVenues){
	    				if(thisVenue.getId().equalsIgnoreCase(thisProgram.getVenueId())){
	    					this.nearbyPrograms.add(thisProgram);
	    				}
	    			}
	    		}
	    	}
		    Log.d("Guide","size of nearby venues: " + nearbyVenues.size());

	    }
}
