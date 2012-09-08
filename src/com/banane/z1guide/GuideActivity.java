package com.banane.z1guide;


import java.io.IOException;
import java.util.Random;
import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONStringer;

import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

public class GuideActivity extends Activity  implements LocationListener {
	private LocationManager locationManager;
    private String provider;
    private String deviceid;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guide);
		getDeviceID();
     	locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
     	 Criteria criteria = new Criteria();
         provider = locationManager.getBestProvider(criteria, false);
         Location location = locationManager.getLastKnownLocation(provider);
         if (location != null) {
             System.out.println("Provider " + provider + " has been selected.");
             Log.d("Guide","Provider " + provider + " has been selected.");
             onLocationChanged(location);
             
         } else {
        	 Log.d("Guide","Location not available.");
         }
	}
	
	 public void onLocationChanged(Location location) {
		 Log.d("guide","in location changed");
		    int lat = (int) (location.getLatitude());
		    int lng = (int) (location.getLongitude());

		    // send to helen's app
		    String moodColor = "100";
		    String userUUID = "123456";
		    String altitude = "100";
		    String jsonstring = "";
		    
		    JSONStringer json;

		    try{
		    	json = new JSONStringer()
		    
		    	.object()   
		    	.key("latitude").value(lat)
		    	.key("longitude").value(lng)
		    	.key("moodColor").value(moodColor)
		    	.key("altitude").value(altitude)
		    	.key("userUUID").value(deviceid)
		    	.endObject();

		    	 jsonstring = json.toString();
		    } catch (JSONException e){
		    	Log.e("Guide","error creating json obj for location ");
		    }
		    
		    String apiurl = "http://festival.projection.ajptechnical.com?report="+jsonstring;
		    Log.d("Guide",apiurl);

/*		    HttpClient httpclient = new DefaultHttpClient();
		    HttpGet httpget = new HttpGet(apiurl);

		    try {
		        // Execute HTTP Post Request
		        HttpResponse response = httpclient.execute(httpget);
		        
		    } catch (ClientProtocolException e) {
		        // TODO Auto-generated catch block
		    } catch (IOException e) {
		        // TODO Auto-generated catch block
		    }*/
		  }
    
    
	 public void viewAbout(View v){
	    	
	    }
	    
	    public void viewLocations(View v){
	    	Intent venueActivity = new Intent (getApplicationContext(), VenuesActivity.class);     
            startActivity(venueActivity);
	    	
	    }
	    
	    public void viewPrograms(View v){
	    	Intent progActivity = new Intent (getApplicationContext(), ProgramsActivity.class);     
            startActivity(progActivity);
	    }
	    public void viewArtists(View v){
	    	Intent artActivity = new Intent (getApplicationContext(), ArtistActivity.class);     
            startActivity(artActivity);
	    }

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
		
		public void getDeviceID(){
			try{
				final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);

			    final String tmDevice, tmSerial, androidId;
			    tmDevice = "" + tm.getDeviceId();
			    tmSerial = "" + tm.getSimSerialNumber();
			    androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

			    UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
			    deviceid = deviceUuid.toString();
			} catch (Exception e){
				Log.d("Guide","no telephony manager");
				deviceid = new Random().toString();
			}
		}

}
