package com.banane.z1guide;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ReactColorActivity extends Activity{
	
	public void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.colortag);
		
/*		String [] colors = new String[6];
		colors[0] = "0_0_0";
		colors[1] = "252_227_1";
		colors[2] = "254_145_29";
		colors[3] = "255_28_2";
		colors[4] = "189_1_40";
		colors[5] = "99_14_42";
		colors[6] = "202_229_55";*/
		
		Button btn1 = (Button)findViewById(R.id.btn1);
		btn1.setId(1);
		btn1.setBackgroundColor(Color.rgb(252,227,1));
		
		Button btn2 = (Button)findViewById(R.id.btn2);
		btn2.setId(2);
		btn2.setBackgroundColor(Color.rgb(254,145,29));
		
		Button btn3 = (Button)findViewById(R.id.btn3);
		btn3.setId(3);
		btn3.setBackgroundColor(Color.rgb(255,28,2));
		
		Button btn4 = (Button)findViewById(R.id.btn4);
		btn4.setId(4);
		btn4.setBackgroundColor(Color.rgb(189,1,40));
		
		Button btn5 = (Button)findViewById(R.id.btn5);
		btn5.setId(5);
		btn5.setBackgroundColor(Color.rgb(99,13,42));
		
		Button btn6 = (Button)findViewById(R.id.btn6);
		btn6.setId(6);
		btn6.setBackgroundColor(Color.rgb(202,229,55));

		Button btn7 = (Button)findViewById(R.id.btn7);
		btn7.setId(7);
		btn7.setBackgroundColor(Color.rgb(236,120,169));

		Button btn8 = (Button)findViewById(R.id.btn8);
		btn8.setId(8);
		btn8.setBackgroundColor(Color.rgb(255,177,126));

		Button btn9 = (Button)findViewById(R.id.btn9);
		btn9.setId(9);
		btn9.setBackgroundColor(Color.rgb(236,120,169));

		Button btn10 = (Button)findViewById(R.id.btn10);
		btn10.setId(10);
		btn10.setBackgroundColor(Color.rgb(211,0,130));
		
		//11,192,137
		Button btn11 = (Button)findViewById(R.id.btn11);
		btn11.setId(11);
		btn11.setBackgroundColor(Color.rgb(11,192,137));
		//130,172,56
		Button btn12 = (Button)findViewById(R.id.btn12);
		btn12.setId(12);
		btn12.setBackgroundColor(Color.rgb(130,172,56));
		//152,181,145
		Button btn13 = (Button)findViewById(R.id.btn13);
		btn13.setId(13);
		btn13.setBackgroundColor(Color.rgb(152,181,145));
		//155,97, 115
		Button btn14 = (Button)findViewById(R.id.btn14);
		btn14.setId(14);
		btn14.setBackgroundColor(Color.rgb(155,97, 115));
		//153,50,155
		Button btn15 = (Button)findViewById(R.id.btn15);
		btn15.setId(15);
		btn15.setBackgroundColor(Color.rgb(153,50,155));
		//23,97,164
		Button btn16 = (Button)findViewById(R.id.btn16);
		btn16.setId(16);
		btn16.setBackgroundColor(Color.rgb(23,97,164));

		Button btn17 = (Button)findViewById(R.id.btn17);
		btn17.setId(16);
		btn17.setBackgroundColor(Color.rgb(60,204,192));

		Button btn18 = (Button)findViewById(R.id.btn18);
		btn18.setId(18);
		btn18.setBackgroundColor(Color.rgb(0,153,71));
		
		Button btn19 = (Button)findViewById(R.id.btn19);
		btn19.setId(19);
		btn19.setBackgroundColor(Color.rgb(126,131,87));

		Button btn20 = (Button)findViewById(R.id.btn20);
		btn20.setId(20);
		btn20.setBackgroundColor(Color.rgb(66,22,82));
	}
	
	public void updateLocation(View v){
		TextView status = (TextView)findViewById(R.id.status);
		status.setText( "Mood color selected.");
		Guide appState = ((Guide)getApplicationContext());
    	appState.setMoodColor(v.getId());
    	if(appState.latestLocation != null){
    		appState.updateLivingMap(appState.latestLocation);
    	}
	}
	
	public void viewLivingMap(View v){
		Intent LivingMapActivity = new Intent (getApplicationContext(), LivingMapActivity.class);     
		startActivity(LivingMapActivity);
	}
	
	
}
