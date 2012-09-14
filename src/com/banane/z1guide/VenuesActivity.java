package com.banane.z1guide;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class VenuesActivity extends Activity {
	ArrayList<Venue> venuesArray;
	ArrayList<String> venueNameArray;
	String apiUrl = "http://api.zero1.org/v1/venues";
	private ListView myList;
	   
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.venues);

	        Guide appState = ((Guide)getApplicationContext());
	        this.venueNameArray = appState.getVenueNameArray();
	        Log.d("Guide","in venue activity, venue name length: "+ this.venueNameArray.size());
	        
	        String[] listContent = new String[this.venueNameArray.size()];
	        this.venueNameArray.toArray(listContent);
	        
	        myList = (ListView)findViewById(R.id.venuesListView);
	        myList.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listContent ));
	        myList.setOnItemClickListener(new OnItemClickListener(){
	        	@Override
				public void onItemClick(AdapterView<?> parent, View view, int position,
						long id) {

					String venueName = venueNameArray.get(position);
		         	Bundle b = new Bundle();
		         	b.putString("venueName", venueName);
		         	
					Intent VenueInfoActivity = new Intent (getApplicationContext(), VenueInfoActivity.class);     
					VenueInfoActivity.putExtras(b);
					startActivity(VenueInfoActivity);
				}
			});
	    }
	    
	   
}
