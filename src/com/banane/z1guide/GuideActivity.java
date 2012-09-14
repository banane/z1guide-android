package com.banane.z1guide;

import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import android.content.Intent;

public class GuideActivity extends Activity{
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guide);
		
		
	}
	
	 
	    public void viewLocations(View v){
	    	Intent venueActivity = new Intent (getApplicationContext(), VenuesActivity.class);     
            startActivity(venueActivity);
	    	
	    }
	    
	    public void viewPrograms(View v){
	    	Intent progActivity = new Intent (getApplicationContext(), ProgramsActivity.class);     
            startActivity(progActivity);
	    }
	    public void viewArtists(View v){
	    	Intent artActivity = new Intent (getApplicationContext(), ArtistActivity.class);     
            startActivity(artActivity);
	    }
	    
	    public void viewAbout(View v){
	    	Intent aboutActivity = new Intent (getApplicationContext(), AboutActivity.class);     
            startActivity(aboutActivity);
	    }



}
