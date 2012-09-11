package com.banane.z1guide;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class VenueInfoActivity extends Activity {
	Venue selectedVenue;
	
	@Override
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
		    
		    TextView address = (TextView) findViewById(R.id.address);
		    address.setText(selectedVenue.getAddress());
		    
		    
	 }
	
	 public void viewMap(View v){
      	Bundle b = new Bundle();
      	b.putString("venueAddress", selectedVenue.getAddress());
      	b.putString("venueName",selectedVenue.getName());
      	b.putString("venueLat", selectedVenue.getLat());
      	b.putString("venueLong", selectedVenue.getLng());
      	Log.d("Guide","in venueinfo in view map");
      	
		Intent vma = new Intent (getApplicationContext(), VenueMapActivity.class);     
		vma.putExtras(b);
		startActivity(vma);
	 }

}
