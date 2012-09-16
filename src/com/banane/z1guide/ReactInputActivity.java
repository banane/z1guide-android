package com.banane.z1guide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ReactInputActivity extends Activity {
	String programId;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input);
        
        Bundle b = getIntent().getExtras(); 
	    programId = b.getString("programId");
	}
	
	public void selectWriteInput(View v){
		Bundle b = new Bundle();
        b.putString("programId", this.programId);    	
	     Intent rwA = new Intent (getApplicationContext(), ReactWriteActivity.class);     
	     rwA.putExtras(b);
	     startActivity(rwA);
	}
	public void selectDrawInput(View v){
		Bundle b = new Bundle();
        b.putString("programId", this.programId);    	
	     Intent rdA = new Intent (getApplicationContext(), ReactDrawActivity.class);     
	     rdA.putExtras(b);
	     startActivity(rdA);
		
	}
}
