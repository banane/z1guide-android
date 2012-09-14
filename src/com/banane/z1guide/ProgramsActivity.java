package com.banane.z1guide;


import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.app.Activity;
import android.content.Intent;

public class ProgramsActivity extends Activity {
    // write data pull from API getting diff. programs

    private ListView myList;
    ArrayList<Program> programsArray;
    ArrayList<String> programTypesArray;
    
    
	
    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
			setContentView(R.layout.programs);

	        
	        Guide appState = ((Guide)getApplicationContext());
	        this.programsArray = appState.getProgramsArray();
	        this.programTypesArray = appState.getProgramTypesArray();
	        
	        String[] listContent = new String[this.programTypesArray.size()];
	        this.programTypesArray.toArray(listContent);
	        
	        myList = (ListView)findViewById(R.id.programsListView);
	        myList.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listContent ));
	        myList.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position,
						long id) {

					// carry value through to profile page, display on top textview, store in stream
					String programType = programTypesArray.get(position);
		         	Bundle b = new Bundle();
		         	b.putString("programType", programType);
		         	
					Intent ProgramsOfTypeActivity = new Intent (getApplicationContext(), ProgramsOfTypeActivity.class);     
					ProgramsOfTypeActivity.putExtras(b);
					startActivity(ProgramsOfTypeActivity);
					}
				});
	    }
    
    

}
