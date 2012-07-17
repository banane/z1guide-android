package com.banane.z1guide;


import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import android.content.Intent;

public class GuideActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guide);
	}
    
    
	 public void viewAbout(View v){
	    	
	    }
	    
	    public void viewLocations(View v){
	    	
	    }
	    
	    public void viewPrograms(View v){
	    	Intent progActivity = new Intent (getApplicationContext(), ProgramsActivity.class);     
            startActivity(progActivity);
	    }
}
