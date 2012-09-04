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

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.app.Activity;
import android.content.Intent;

public class ProgramsActivity extends Activity {
    // write data pull from API getting diff. programs

    private ListView myList;
    ArrayList<Program> programsArray;
    ArrayList<String> programTypesArray;
    
    
    String apiUrl = "http://api.zero1.org/v1/programs";
	
    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.programs);

	        getProgramTypes();
	        
	        Guide appState = ((Guide)getApplicationContext());
	        appState.setProgramsArray(programsArray);
	        appState.setProgramTypesArray(programTypesArray);
	        
	        String[] listContent = new String[this.programTypesArray.size()];
	        this.programTypesArray.toArray(listContent);
	        
	        myList = (ListView)findViewById(R.id.programsListView);
	        myList.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listContent ));
	        myList.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position,
						long id) {

					// carry value through to profile page, display on top textview, store in stream
					String programType = programTypesArray.get(position);
		         	Bundle b = new Bundle();
		         	b.putString("programType", programType);
		         	
					Intent ProgramsOfTypeActivity = new Intent (getApplicationContext(), ProgramsOfTypeActivity.class);     
					ProgramsOfTypeActivity.putExtras(b);
					startActivity(ProgramsOfTypeActivity);
					}
				});
	    }
    
    public void getProgramTypes() {
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

    			Program thisProgram = new Program(programName, programType, id, imagePath, artistId, venueId, endDate, startDate, webSite );

    			programsArray.add(thisProgram);
    			if(!programTypesArray.contains(programType)){
        			programTypesArray.add(programType);    			
    			}
    			Log.d("Guide",thisProgram.getName());
    		}    		
    	} catch (JSONException e){
    		Log.e("Guide error: ",e.getMessage());
    	}
    	Collections.sort(programTypesArray);
    }
    
    public String readApiPrograms() {
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
