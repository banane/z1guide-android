package com.banane.z1guide;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

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
    ArrayList<String> programArray;
    
    String apiUrl = "http://zero1.liftprojects.com/v1/programs";
	
    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.programs);

	        getPrograms();
	        
	        String[] listContent = new String[this.programArray.size()];
	        this.programArray.toArray(listContent);
	        
	        myList = (ListView)findViewById(R.id.programsListView);
	        myList.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listContent ));
	        myList.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position,
						long id) {

					// carry value through to profile page, display on top textview, store in stream
					String programName = programArray.get(position);
		         	Bundle b = new Bundle();
		         	// only increment on incoming songs not words
		         	b.putString("programName",programName);
					Intent programInfoActivity = new Intent (getApplicationContext(), ProgramInfoActivity.class);     
					programInfoActivity.putExtras(b);
					startActivity(programInfoActivity);
					      
					}
				});
	    }
    
    public void getPrograms() {
    	programArray=new ArrayList<String>();
    	try{
    		JSONObject json = new JSONObject(readApiPrograms());
    		String programString = json.getString("programs");
    		Log.d("Guide","programstring: "+programString);
    		JSONArray jsonA = new JSONArray(programString);
    		Log.d("guide ","array length: "+jsonA.length());
    		for (int i = 0; i < jsonA.length(); i++) {
    			JSONObject item = jsonA.getJSONObject(i);
    			String programName = item.getString("programname");
    			programArray.add(programName);
    			Log.d("Guide","programname: "+ programName);
    		}    		
    	} catch (JSONException e){
    		Log.e("Guide error: ",e.getMessage());
    	}
    }
    
    public String readApiPrograms() {
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(apiUrl);
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
