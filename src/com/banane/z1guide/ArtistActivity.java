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

public class ArtistActivity extends Activity {
	ArrayList<Artist> artistsArray;
	ArrayList<String> artistsNameArray;

    String apiUrl = "http://api.zero1.org/v1/artists";
    private ListView myList;
    
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.artists);

        getArtists();

        Guide appState = ((Guide)getApplicationContext());
        appState.setArtistsArray(artistsArray);
        
        String[] listContent = new String[this.artistsNameArray.size()];
        this.artistsNameArray.toArray(listContent);
        
        myList = (ListView)findViewById(R.id.artistsListView);
        myList.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listContent ));
        myList.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {

				// carry value through to profile page, display on top textview, store in stream
				String artistName = artistsNameArray.get(position);
	         	Bundle b = new Bundle();
	         	b.putString("artistName", artistName);
	         	
				Intent ArtistInfoActivity = new Intent (getApplicationContext(), ArtistInfoActivity.class);     
				ArtistInfoActivity.putExtras(b);
				startActivity(ArtistInfoActivity);
				}
			});
	}
	
	 public void getArtists() {
	    	artistsArray = new ArrayList<Artist>();
	    	artistsNameArray = new ArrayList<String>();
	    	
	    	try{
	    		JSONObject json = new JSONObject(readApiArtists());
	    		String programString = json.getString("artists");
	    		JSONArray jsonA = new JSONArray(programString);
	    		Log.d("guide ","array length: "+jsonA.length());
	    		for (int i = 0; i < jsonA.length(); i++) {
	    			JSONObject item = jsonA.getJSONObject(i);
	    			
	    			String name = item.getString("name");
	    			String id = item.getString("artistid");
	    			String bio = item.getString("bio");
	    			String imagepath = "";
	    			
	    			String [] projects = item.getString("programs").split(",");
	    			String projectId = projects[0];
	    			projectId = projectId.replace("[", "");
	    			projectId = projectId.replace("]", "");
	    			if(projectId == null || projectId.equals("")){
	    				projectId = "-1";
	    			}
//	    			Log.d("Guide","cleaned proj: "+projectId);
	    			
	    			
	    			Artist thisArtist = new Artist(name, id, imagepath, bio, projectId);
	    			artistsArray.add(thisArtist);
	        		artistsNameArray.add(name);    			
	    		}    		
	    	} catch (JSONException e){
	    		Log.e("Guide error: ",e.getMessage());
	    	}
	    	Collections.sort(artistsNameArray);
	    }
	    
	    public String readApiArtists() {
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
