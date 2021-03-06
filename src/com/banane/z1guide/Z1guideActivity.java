package com.banane.z1guide;

import android.os.Bundle;
import android.app.TabActivity;
import android.content.Intent;
import android.widget.TabHost;


public class Z1guideActivity extends TabActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        
        Guide appState = ((Guide)getApplicationContext());
        appState.getDeviceID();

        
        // kick off programs query
        appState.getProgramTypes();
        appState.getVenues();
        
        appState.setupLocation();
        appState.getDeviceID();
        
        TabHost tabHost = getTabHost();  // The activity TabHost
        TabHost.TabSpec spec;  // Resusable TabSpec for each tab
        Intent intent;  // Reusable Intent for each tab
        
        intent = new Intent().setClass(getApplicationContext(), GuideActivity.class); 
        spec = tabHost.newTabSpec("Guide").setIndicator("Guide")
                      .setContent(intent);
        tabHost.addTab(spec);
        
        intent = new Intent().setClass(this, ReactColorActivity.class);
//        intent = new Intent().setClass(this, ReactInputActivity.class);
        spec = tabHost.newTabSpec("Living Map").setIndicator("Living Map")
                      .setContent(intent);
        tabHost.addTab(spec);
        tabHost.setCurrentTab(0);
        for (int i = 0; i < tabHost.getTabWidget().getTabCount(); i++) {
            tabHost.getTabWidget().getChildAt(i).getLayoutParams().height = 40;
        }  
    }
    
   
}

