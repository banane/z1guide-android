package com.banane.z1guide;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class LivingMapActivity extends Activity {
	private WebView webview;
	
	public void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.livingmap);
		

		String url = "http://www.livingmapproject.com/ios.html";

		webview = (WebView) findViewById(R.id.livingMap);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.loadUrl(url);
	}
}
