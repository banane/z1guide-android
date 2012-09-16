package com.banane.z1guide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ReactVoteActivity extends Activity {
	String programId;
	
	String reactionType;
	String content;
	
	Reaction reaction1;
	Reaction reaction2;
	
	String baseurl = "http://freezing-samurai-9107.herokuapp.com";
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vote);
        
        Bundle b = getIntent().getExtras(); 
	    programId = b.getString("programId");
        reactionType = b.getString("reactionType");
        content = b.getString("content");

        // get reactions for this programid
       	doContentUpload();
        
        
	}
	
	public void drawButtons(){
		Log.d("Guide","reaction1: "+reaction1.getReactionType());
		Log.d("Guide","reaction2: "+reaction2.getReactionType());
		
    	Button btn2 = (Button)findViewById(R.id.vote_button_2);
    	Button btn1 = (Button)findViewById(R.id.vote_button_1);

    	if(reaction1.reactionType.equalsIgnoreCase("string")){
        	btn1.setText(reaction1.getTextReaction());
        } else {
        	btn1.setBackgroundDrawable(getDrawableFromWebOperation(reaction1.getImageReaction()));

        }
        if(reaction2.reactionType.equalsIgnoreCase("string")){
        	btn2.setText(reaction2.getTextReaction());
        } else {
        	btn2.setBackgroundDrawable(getDrawableFromWebOperation(reaction2.getImageReaction()));
        }
	}
	
	public void voteButton1(View v){
		//vote
		vote(reaction1.getReactionId());
		viewGallery(reaction1.getReactionId());
	}
	
	public void voteButton2(View v){
		vote(reaction2.getReactionId());
		viewGallery(reaction2.getReactionId());
		
	}
	
	public void vote(String reactionId){
		String url1 = baseurl + "/artwork/" + programId + "/reaction/"+reactionId+"/upvote";
		String url2 = baseurl + "/artwork/" + programId + "/reaction/"+reactionId+"/downvote";
		
		Log.d("Guide",url1);
		
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url1);
		HttpGet httpget2 = new HttpGet(url2);

		try{
			HttpResponse response  = httpclient.execute(httpget);
		
		// do the next one
			response = httpclient.execute(httpget2);
		}catch (Exception e){
			Log.e("Guide","error: "+e.getMessage());
		}
	}
	
	public void viewGallery(String reactionId){
		Bundle b = new Bundle();
     	b.putString("programId", programId);
     	b.putString("reactionId", reactionId);
		Intent rga = new Intent (getApplicationContext(), ReactGalleryActivity.class);     
		rga.putExtras(b);
		startActivity(rga);
	}
	
	public void getVotingReactions(String responseString){
		if(responseString.length() > 0)
		{		
			try{
				Log.d("Guide","In getvotingreactions");
				JSONObject jsonV = new JSONObject(responseString);
				String reaction1String = jsonV.getString("reaction1");
				JSONObject jsonObj1 = new JSONObject(reaction1String);
				reaction1 = createReaction(jsonObj1);
			
				String reaction2String = jsonV.getString("reaction2");
				JSONObject jsonObj2 = new JSONObject(reaction2String);
				reaction2 = createReaction(jsonObj2);
			
				drawButtons();
			
			} catch (JSONException e){
    			Log.e("Guide",e.getMessage());
    		}
		} else {
			Log.e("Guide","No results from exchange.");
		}
	}
	
	public Reaction createReaction(JSONObject jsonObj){
		String reactionId = "";
		String reactionType = "";
		String content = "";
		String url = "";
		try{
			reactionId = jsonObj.getString("reaction_id");
			reactionType = jsonObj.getString("reaction_type");
			content = jsonObj.getString("content");
			url = jsonObj.getString("url");
			

		} catch (JSONException e) {
			Log.e("Guide","error: "+e.getMessage());
		}
		Reaction newReaction = new Reaction(
				reactionId, this.programId, reactionType, content, content );
		return newReaction;		
	}
	
	
	    public String parseResult(HttpResponse response) throws IOException {
			Log.d("Guide","In parseresult");

			StringBuilder builder = new StringBuilder();
			StatusLine statusLine = response.getStatusLine();
			Log.d("Guide","statusline: "+statusLine);
				
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				Log.d("Guide","successful http call ");
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
			return builder.toString();
		}
	    
	    public void doContentUpload(){
			Log.d("Guide","In docontentupload");

	    	String progUrl = baseurl + "/artwork/" + this.programId + "/reaction/";
	    	Log.d("Guide",progUrl);
	    	
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(progUrl );
			
	    	Guide appState = ((Guide)getApplicationContext());
	    	JSONObject postObj = new JSONObject();
	    	
			try {
				postObj.put("user_id",appState.deviceid);
				postObj.put("reaction_type", reactionType);
				postObj.put("content", content);
				postObj.put("aws_url", "");
			} catch (Exception e){
				Log.e("Guide","Json encode error: "+e.getMessage());
			}
			
			try {
				String jsonStr = postObj.toString();
		        
				httppost.setEntity(new StringEntity(jsonStr, "UTF8"));
				httppost.setHeader("Content-type", "application/json");
				
				HttpResponse response = httpclient.execute(httppost);
				Log.d("Guide","response: "+response.toString());
				String resultString = parseResult(response);
				getVotingReactions(resultString);
			} catch (Exception e){
				Log.e("Guide","http req error"+e.getMessage());
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
	
}
