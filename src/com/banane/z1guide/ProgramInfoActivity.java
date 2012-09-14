package com.banane.z1guide;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
		    tv.setText("Program: "+ programName);
		    TextView venue_tv = (TextView) findViewById(R.id.venue_tv);
		    venue_tv.setText("Location: "+ associatedVenue.getName());
		    
/*		    String [] artists = selectedProgram.getArtists().split(",");
 			String [] cleanedArtists = new String[artists.length];
 			int i = 0;
 			StringBuilder artistsString = new StringBuilder();
 			StringUtils.join();

 			for(String artist:artists){
		    	artist = artist.replace("[", "");
	    		artist = artist.replace("]", "");
	    		artist = artist.replace("\"", "");
	    		artist = artist.replace("\"", "");
	    		 artistsString.append(artist);
	    		
	    		i++;
		    }*/

		    TextView artists_tv = (TextView) findViewById(R.id.artists_tv);
		    artists_tv.setText("Artists: "+ selectedProgram.getArtists());
		    
		    ImageView im = (ImageView)findViewById(R.id.program_picture);
		    im.setImageDrawable(getDrawableFromWebOperation(selectedProgram.getImagePath()));
		    
		    Log.d("Guide","imagepath: "+selectedProgram.getImagePath());
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
	 
	 public static Drawable getDrawableFromWebOperation(String url) {
		    try {
		      InputStream is = (InputStream) new URL(url).getContent();
		      Drawable d = Drawable.createFromStream(is, url);
		      return d;
		    } catch (Exception e) {
		      Log.e("Guide", e.getMessage());
		      return null;
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
