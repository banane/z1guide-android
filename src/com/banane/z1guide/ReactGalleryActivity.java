package com.banane.z1guide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;

public class ReactGalleryActivity extends Activity {
	public String programId;
	public RelativeLayout container;
	public ArrayList<Reaction> topReactions;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery);
        Log.d("Guide","oncreate gallery");

        Bundle b = getIntent().getExtras(); 
	    programId = b.getString("programId");
	    Log.d("Guide", "ProgramId: "+programId);
        
        topReactions = new ArrayList<Reaction>();
        getTopReactions();
	    
        // start drawing reactions
        container = (RelativeLayout)findViewById(R.id.container);		

    	
    	int top = 10;

	    for(Reaction thisReaction:topReactions){
	    	// image or textview

	        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
	    	layoutParams.setMargins(10, top, 10 , 5);   
	    	if(thisReaction.getReactionType().equalsIgnoreCase("drawing")){
	    		Log.d("Guide",thisReaction.getImageReaction());
	    		ImageView im = new ImageView(this);
			    im.setImageDrawable(getDrawableFromWebOperation(thisReaction.getImageReaction()));
			    
	    		container.addView(im, layoutParams);
			    im.getLayoutParams().height=150;
			    im.getLayoutParams().width=200;
			    im.setBackgroundColor(Color.WHITE);
	    		
	    	} else {
	    		TextView tv = new TextView(this);
	    		tv.setText(thisReaction.getTextReaction());
	    		tv.setBackgroundColor(Color.WHITE);
	    		tv.setPadding(30, 10, 10, 30);

	    		container.addView(tv, layoutParams);
	    	}
		    top += 160;
	    }
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
	 
	public void getTopReactions(){
		Log.d("Guide","In top reactions");
		try{
    		JSONArray jsonA = new JSONArray(readApiReactions());
    		Log.d("Guide","number of top reactions: "+ jsonA.length());
    		for (int i = 0; i < jsonA.length(); i++) {
    			JSONObject item = jsonA.getJSONObject(i);
    			
    			String reactionType = item.getString("reaction_type");
    			String content = item.getString("content");
    			String url = item.getString("url");
    			String reactionId = item.getString("reaction_id");
    			
    			Reaction thisReaction = new Reaction(
    					reactionId,
    					this.programId, reactionType, content, content );

    			topReactions.add(thisReaction);
    		}    		
    	} catch (JSONException e){
    		Log.e("Guide",e.getMessage());
    	}
	}
	
		 
	    public String readApiReactions() {
	    	String progUrl = "http://freezing-samurai-9107.herokuapp.com/artwork/" + this.programId + "/top?number=6";
	    	Log.d("Guide",progUrl);
			StringBuilder builder = new StringBuilder();
			HttpClient client = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(progUrl );
			try {
				HttpResponse response = client.execute(httpGet);
				StatusLine statusLine = response.getStatusLine();
				int statusCode = statusLine.getStatusCode();
				if (statusCode == 200) {
					Log.d("Guide","successful http call for top reactions");
					HttpEntity entity = response.getEntity();
					InputStream content = entity.getContent();
					BufferedReader reader = new BufferedReader(new InputStreamReader(content));
					String line;
					while ((line = reader.readLine()) != null) {
						builder.append(line);
					}
				} else {
					Log.e("Guide", "Failed to get response from url");
				}
				Log.d("Guide","successful string building");
				
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return builder.toString();
		}
	    
	    public void startAgain(View v){
	    	Bundle b = new Bundle();
	     	b.putString("programId", programId);

	     	Intent rva = new Intent (getApplicationContext(), Z1guideActivity.class);     
			rva.putExtras(b);
			startActivity(rva);	
	    }
}
