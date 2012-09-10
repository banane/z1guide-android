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
        
        // kick off programs query
        appState.getProgramTypes();
        appState.getVenues();

        
        TabHost tabHost = getTabHost();  // The activity TabHost
        TabHost.TabSpec spec;  // Resusable TabSpec for each tab
        Intent intent;  // Reusable Intent for each tab
        
        intent = new Intent().setClass(getApplicationContext(), GuideActivity.class); 
        spec = tabHost.newTabSpec("Guide").setIndicator("Guide")
                      .setContent(intent);
        tabHost.addTab(spec);
        
        intent = new Intent().setClass(this, ReactActivity.class);
        spec = tabHost.newTabSpec("React").setIndicator("React")
                      .setContent(intent);
        tabHost.addTab(spec);
        tabHost.setCurrentTab(0);
        for (int i = 0; i < tabHost.getTabWidget().getTabCount(); i++) {
            tabHost.getTabWidget().getChildAt(i).getLayoutParams().height = 50;
        }  
    }
    
   
}

