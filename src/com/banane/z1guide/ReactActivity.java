package com.banane.z1guide;

import java.io.InputStream;
import java.net.URL;
import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class ReactActivity extends Activity {
	RelativeLayout artwork_container;
	private int currentX;
	private int currentY;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.react);
		
/*	    Guide appState = ((Guide)getApplicationContext());

		artwork_container = (RelativeLayout)findViewById(R.id.artwork_container);		
		int top = 0;
	    int left = 0;
	    int i = 0;
	    	Log.d("Guide","size of nearby progs: " + appState.nearbyPrograms.size());
	    for(Program thisProgram:appState.nearbyPrograms){
	    	Button btn = new Button(this);
	    	btn.setOnClickListener(myhandler1);
	    	btn.setId(i);
	    
	    	btn.setBackgroundDrawable(getDrawableFromWebOperation(thisProgram.getImagePath()));
	    	RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
	    	layoutParams.setMargins(left, top, 0, 0);               
	    	artwork_container.addView(btn, layoutParams);
	    	top += 120;
	    	i++;
	    }
	    */
	}
	View.OnClickListener myhandler1 = new View.OnClickListener() {
	    public void onClick(View v) {
	      // it was the 1st button
/*	    	int id = v.getId();
	    	Log.d("Guide","id: "+id + "");
		    Guide appState = ((Guide)getApplicationContext());

	    	Program selectedProgram = appState.nearbyPrograms.get(id);
	    	
         	Bundle b = new Bundle();
         	b.putString("programId", selectedProgram.getId());
         	
			Intent ReactColorActivity = new Intent (getApplicationContext(), ReactColorActivity.class);     
			ReactColorActivity.putExtras(b);
			startActivity(ReactColorActivity);*/
	    	
	    	
	    }
	 };
	 
	@Override 
	  public boolean onTouchEvent(MotionEvent event) {
	    switch (event.getAction()) {
	        case MotionEvent.ACTION_DOWN: {
	            currentX = (int) event.getRawX();
	            currentY = (int) event.getRawY();
	            break;
	        }

	        case MotionEvent.ACTION_MOVE: {
	            int x2 = (int) event.getRawX();
	            int y2 = (int) event.getRawY();
	            artwork_container.scrollBy(currentX - x2 , currentY - y2);
	            currentX = x2;
	            currentY = y2;
	            break;
	        }   
	        case MotionEvent.ACTION_UP: {
	            break;
	        }
	    }
	    return true;
	 }
	 
	 public static Drawable getDrawableFromWebOperation(String url) {
		    try {
		      InputStream is = (InputStream) new URL(url).getContent();
		      Drawable d = Drawable.createFromStream(is, url);
		      return d;
		    } catch (Exception e) {
		      Log.e("Guide", e.getMessage());
		      return null;
		    }
	}

}
