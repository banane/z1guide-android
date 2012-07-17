package com.banane.z1guide;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ProgramInfoActivity extends Activity {
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.programinfo);
	        Bundle b = getIntent().getExtras(); 
		    String programName = b.getString("programName");
		    TextView tv = (TextView) findViewById(R.id.program_name_tv);
		    tv.setText(programName);
	 }

}
