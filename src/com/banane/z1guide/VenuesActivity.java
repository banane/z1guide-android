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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class VenuesActivity extends Activity {
	ArrayList<Venue> venuesArray;
	ArrayList<String> venuesNameArray;
	String apiUrl = "http://api.zero1.org/v1/venues";
	private ListView myList;
	   
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.venues);

	        getVenues();
	        Log.d("Guide","got venues");
	        Guide appState = ((Guide)getApplicationContext());
	        appState.setVenuesArray(venuesArray);
	        
	        String[] listContent = new String[this.venuesNameArray.size()];
	        this.venuesNameArray.toArray(listContent);
	        Log.d("Guide","venuesnamearray length: "+ this.venuesNameArray.size());
	        myList = (ListView)findViewById(R.id.venuesListView);
	        myList.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listContent ));
	        myList.setOnItemClickListener(new OnItemClickListener(){
	        	@Override
				public void onItemClick(AdapterView<?> parent, View view, int position,
						long id) {

					String venueName = venuesNameArray.get(position);
		         	Bundle b = new Bundle();
		         	b.putString("venueName", venueName);
		         	
					Intent VenueInfoActivity = new Intent (getApplicationContext(), VenueInfoActivity.class);     
					VenueInfoActivity.putExtras(b);
					startActivity(VenueInfoActivity);
				}
			});
	    }
	    
	    public void getVenues(){
	    	Log.d("Guide","in get venues");
	    	venuesNameArray = new ArrayList<String>();
	    	venuesArray = new ArrayList<Venue>();
	    	
	    	try{
	    		JSONObject json = new JSONObject(readApiVenues());
	    		String programString = json.getString("venues");
	    		JSONArray jsonA = new JSONArray(programString);
	    		Log.d("Guide","array length: "+jsonA.length());
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
	        		venuesNameArray.add(name);   
	    		}    		
	    	} catch (JSONException e){
	    		Log.e("Guide error: ",e.getMessage());
	    	}
	    	Collections.sort(venuesNameArray);
	    }
	    
	    public String readApiVenues() {
			StringBuilder builder = new StringBuilder();
			HttpClient client = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(apiUrl );
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
}
