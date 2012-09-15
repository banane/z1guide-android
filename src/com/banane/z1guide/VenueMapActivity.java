package com.banane.z1guide;

import java.util.List;

import android.graphics.drawable.Drawable;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class VenueMapActivity extends MapActivity {
	
	MapController mapController; 
	Geocoder geocoder;
	String venueAddress;
	String venueLat;
	String venueLong;
	String venueName;
	Venue theVenue;
	GeoPoint point;
	
	double lat=0,lng=0;
	
	@Override
	 public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.venuemap);
        
        Bundle b = getIntent().getExtras(); 
	    venueAddress = b.getString("venueAddress");
	    venueLat = b.getString("venueLat");
	    venueLong = b.getString("venueLong");
	    venueName = b.getString("venueName");
	    
	    try{
	      lat = Double.parseDouble(venueLat);
	      lng = Double.parseDouble(venueLong);
	      point = new GeoPoint((int)(lat * 1e6),(int)(lng * 1e6));
	    } catch (NumberFormatException e){
	    	Log.e("Guide", "Couldn't parse latitude and/or longitude: " + e.getMessage());
	    }
        
        MapView mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        
        mapController = mapView.getController();
        mapController.setZoom(16);
        
        geocoder = new Geocoder(this);
        
     // Create our "tiny" marker icon
        List<Overlay> mapOverlays = mapView.getOverlays();
        Drawable drawable = this.getResources().getDrawable(R.drawable.blue_dot);
        veneuMapOverlay itemizedoverlay = new veneuMapOverlay(drawable,this);
        OverlayItem overlayitem = new OverlayItem(point, venueName, venueAddress);
        itemizedoverlay.addOverlay(overlayitem);
        mapController.animateTo(point);
        mapOverlays.add(itemizedoverlay);
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
