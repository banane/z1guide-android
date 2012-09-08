package com.banane.z1guide;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ArtistInfoActivity extends Activity {
	Artist selectedArtist;
	
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.artistinfo);
	        
	        Bundle b = getIntent().getExtras(); 
		    String artistsName = b.getString("artistsName");
		    Guide appState = ((Guide)getApplicationContext());
		    ArrayList<Artist> allArtists = appState.getArtistsArray();
		    for(Artist thisArtist:allArtists){
		    	if(thisArtist.getName().equals(artistsName)){
		    		selectedArtist = thisArtist;
		    		break;
		    	}
		    }
		    TextView tv = (TextView) findViewById(R.id.artists_name);
		    tv.setText(artistsName);
		    TextView bio_tv = (TextView) findViewById(R.id.bio_tv);
		    bio_tv.setText(selectedArtist.getBiography());
		    bio_tv.setMovementMethod(new ScrollingMovementMethod());
	 }
	 
	 public void viewProject(View v){
		 Guide appState = ((Guide)getApplicationContext());
		 ArrayList<Program> allPrograms = appState.getProgramsArray();
		 
		 String [] projects = selectedArtist.getProjectId().split(",");
		 String firstProject = projects[0];
		 String projectId = firstProject.replace("[","");
		 Log.d("Guide","replacement: "+projectId);
		 String programName;

		 for(Program thisProgram:allPrograms){
			 
			 Log.d("Guide","in program:"+thisProgram.getName());
			 if(thisProgram.getId().equals(projectId)){
				 programName = thisProgram.getName();
				 Log.d("Guide","found matching program:"+programName);
				 break;
			 }
		 }
		 
		 Bundle b = new Bundle();
//         b.putString("programName", programName);
         	
//		Intent progActivity = new Intent (getApplicationContext(), ProgramInfoActivity.class);     
//		progActivity.putExtras(b);
//		startActivity(progActivity);
	 }
		 
	
}
