package com.banane.z1guide;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ProgramInfoActivity extends Activity {
	Program selectedProgram;
	Venue associatedVenue;
	
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.programinfo);
	        
	        Bundle b = getIntent().getExtras(); 
		    String programName = b.getString("programName");
		    Guide appState = ((Guide)getApplicationContext());
		    ArrayList<Program> allPrograms = appState.getProgramsArray();
		    for(Program thisProgram:allPrograms){
		    	if(thisProgram.getName().equals(programName)){
		    		selectedProgram = thisProgram;
		    		break;
		    	}
		    
		    }
		    Log.d("Guide","Found selected program");
	        findVenue();
		    TextView tv = (TextView) findViewById(R.id.program_name_tv);
		    tv.setText(programName);
		    TextView venue_tv = (TextView) findViewById(R.id.venue_tv);
		    venue_tv.setText(associatedVenue.getName());
	 }
	 public void findVenue(){

		 Guide appState = ((Guide)getApplicationContext());
		 ArrayList<Venue> allVenues = appState.getVenuesArray();
		 String venueId = selectedProgram.getVenueId();
		 Log.d("Guide","programinfo Venueid: "+venueId);
		 if(venueId.equals("-1") || venueId == null || venueId.length() ==0 ){
			 Log.d("Guide","no venue id available");
			 this.associatedVenue = allVenues.get(41); // default is zero1 garage
		 } else {
		   
		   for(Venue thisVenue:allVenues){
			 
			  if(thisVenue.getId().equals(venueId)){
				   this.associatedVenue = thisVenue;				 
				   Log.d("Guide","found matching venue:"+associatedVenue.getName());
				   break;
		      }
		   }
		}
	 
	 }
	 
	 public void viewVenue(View v){
		 Log.d("Guide","in venue view");
	     Bundle b = new Bundle();
         b.putString("venueName", this.associatedVenue.getName());
     	
	     Intent venueActivity = new Intent (getApplicationContext(), VenueInfoActivity.class);     
	     venueActivity.putExtras(b);
	     startActivity(venueActivity);
	 }
}
