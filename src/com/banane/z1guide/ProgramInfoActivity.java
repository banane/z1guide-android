package com.banane.z1guide;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ProgramInfoActivity extends Activity {
	Program selectedProgram;
	
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
		    TextView tv = (TextView) findViewById(R.id.program_name_tv);
		    tv.setText(programName);
//		    TextView venue_tv = (TextView) findViewById(R.id.);
//		    venue_tv.setText(selectedProgram.getVenueId());
	 }

}
