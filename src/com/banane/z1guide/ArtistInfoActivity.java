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
	String programName = "";
	
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.artistinfo);
	        
	        Bundle b = getIntent().getExtras(); 
		    String artistName = b.getString("artistName");
		    Guide appState = ((Guide)getApplicationContext());
		    ArrayList<Artist> allArtists = appState.getArtistsArray();
		    for(Artist thisArtist:allArtists){
		    	if(thisArtist.getName().equals(artistName)){
		    		selectedArtist = thisArtist;
		    		break;
		    	}
		    }
	        
	        findProject();

		    TextView tv = (TextView) findViewById(R.id.artist_name);
		    tv.setText(artistName);
		    TextView bio_tv = (TextView) findViewById(R.id.bio_tv);
		    bio_tv.setText(selectedArtist.getBiography());
		    bio_tv.setMovementMethod(new ScrollingMovementMethod());
	 }
	 
	 public void findProject(){
		 String projectId = selectedArtist.getProjectId();
		 if(projectId.equals("-1")){
			 Log.d("Guide","no artist project available");
		 } else {
		   Guide appState = ((Guide)getApplicationContext());
		   ArrayList<Program> allPrograms = appState.getProgramsArray();
		   
		   Log.d("Guide","artist project id: "+projectId);
		   for(Program thisProgram:allPrograms){
			 
			  Log.d("Guide","in program id:"+thisProgram.getId());
			  if(thisProgram.getId().equals(projectId)){
				 this.programName = thisProgram.getName();
				 Log.d("Guide","found matching program:"+programName);
				 break;
			  }
		   }
		 }
	 }
	 
	 public void viewProject(View v){
		 Log.d("Guide","in project view");
	     Bundle b = new Bundle();
         b.putString("programName", this.programName);
     	
	     Intent progActivity = new Intent (getApplicationContext(), ProgramInfoActivity.class);     
	     progActivity.putExtras(b);
	     startActivity(progActivity);
	 }
		 
	
}
