package com.banane.z1guide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ReactWriteActivity extends Activity {
	String programId;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inputwrite);
        
        Bundle b = getIntent().getExtras(); 
	    programId = b.getString("programId");
//        programId = "46";
	}
	
	public void submitText(View v){
		EditText et = (EditText)findViewById(R.id.input);
		
		Bundle b = new Bundle();
		
		b.putString("reactionType", "string");
     	b.putString("content", et.getText().toString());
     	b.putString("programId", programId);
     	
		Intent rva = new Intent (getApplicationContext(), ReactVoteActivity.class);     
		rva.putExtras(b);
		startActivity(rva);	
		
	}
}
