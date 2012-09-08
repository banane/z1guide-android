package com.banane.z1guide;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class VenueInfoActivity extends Activity {
	Venue selectedVenue;
	
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.venueinfo);
	        
	        Bundle b = getIntent().getExtras(); 
		    String venueName = b.getString("venueName");
		    Guide appState = ((Guide)getApplicationContext());
		    ArrayList<Venue> allVenues = appState.getVenuesArray();
		    for(Venue thisVenue:allVenues){
		    	if(thisVenue.getName().equals(venueName)){
		    		selectedVenue = thisVenue;
		    		break;
		    	}
		    }
		    TextView tv = (TextView) findViewById(R.id.venue_name);
		    tv.setText(venueName);
		    TextView description = (TextView) findViewById(R.id.description);
		    description.setText(selectedVenue.getDescription());
		    
	 }
}
