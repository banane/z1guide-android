package com.banane.z1guide;


import java.util.ArrayList;
import java.util.Collections;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.app.Activity;
import android.content.Intent;

public class ProgramsOfTypeActivity extends Activity {
    // write data pull from API getting diff. programs

    private ListView myList;
    ArrayList<String> programNameArray;
    String programType;
    
    public void onCreate(Bundle savedInstanceState) {
    	Log.d("Guide","in create");
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.programs);
	        
	        Bundle b = getIntent().getExtras(); 
		    programType = b.getString("programType");

	        getProgramNames();
	
	        String[] listContent = new String[this.programNameArray.size()];
	        this.programNameArray.toArray(listContent);
	        
	        myList = (ListView)findViewById(R.id.programsListView);
	        myList.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listContent ));
	        myList.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position,
						long id) {

					String selectedName = programNameArray.get(position);
		         	Bundle b = new Bundle();
		         	b.putString("programName",selectedName);
					Intent programInfoActivity = new Intent (getApplicationContext(), ProgramInfoActivity.class);     
					programInfoActivity.putExtras(b);
					startActivity(programInfoActivity);
		         	
					      
					}
				});
	    }
    
    public void getProgramNames() {
    	 Guide appState = ((Guide)getApplicationContext());
	     ArrayList<Program> programs =   appState.getProgramsArray();
	     programNameArray = new ArrayList<String>();
	     
	     for(Program thisProgram:programs){
	    	 String typeName = thisProgram.getProgramType();
	    	 if(typeName.equals(programType)){
	    		 programNameArray.add(thisProgram.getName());
	    	 }

	     }
	     Collections.sort(programNameArray);
	     
	     
    }
    
  

}
