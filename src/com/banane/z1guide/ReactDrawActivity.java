package com.banane.z1guide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.graphics.*;


public class ReactDrawActivity extends Activity {

    static String imageName;
    static String imagePath;
    static String awsPath;
    String programId;

	
    /** Background color. */
    static final int BACKGROUND_COLOR = Color.WHITE;

    /** The view responsible for drawing the window. */
    DrawingView mView;

    /** Is fading mode enabled? */
    boolean mFading;

    enum PaintMode {
        Draw,
        Splat,
        Erase,
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawing);
    	Guide appState = ((Guide)getApplicationContext());

        Bundle b = getIntent().getExtras(); 
	    programId = b.getString("programId");
	    this.imageName = "guide_"+appState.deviceid+"_"+programId+".png";

        // Create and attach the view that is responsible for painting.
        mView = new DrawingView(this);
        
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.drawingLayout);
        mView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));        
        layout.addView(mView);
        		
        mView.requestFocus();
      
    }
    
    public void submitDrawing(View v){
    	// somehow make the image uploaded to amzon
    	writeImage();
    	awsUpload();
    	
    	Bundle b = new Bundle();
     	b.putString("programId", programId);
     	b.putString("reactionType", "drawing");
     	b.putString("content",awsPath);

     	Intent rva = new Intent (getApplicationContext(), ReactVoteActivity.class);     
		rva.putExtras(b);
		startActivity(rva);	
    }
    public void writeImage(){
    	Log.d("Guide","in write image");
    	ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    	
    	mView.mBitmap.compress(Bitmap.CompressFormat.PNG, 40, bytes);

    	imagePath = Environment.getExternalStorageDirectory()
                + File.separator + imageName;
    	File f = new File(imagePath);
    	try{
    		f.createNewFile();
    	//write the bytes in file
    		FileOutputStream fo = new FileOutputStream(f);
    		fo.write(bytes.toByteArray());
    		Log.d("Guide", "image created: "+ imagePath);
    	} catch (Exception e){
    		Log.e("Guide","error creating image file: "+ e.getMessage());
    	}
    }
    
    
    public static Boolean awsUpload(){
    	Log.d("Guide","in aws upload");
	   AmazonS3Client s3Client = new AmazonS3Client( new BasicAWSCredentials( awsconstants.ACCESS_KEY_ID, awsconstants.SECRET_KEY ) );


     try {
     	s3Client.createBucket( awsconstants.BUCKET );
     	
     	PutObjectRequest por = new PutObjectRequest( awsconstants.BUCKET, imageName, new java.io.File( imagePath) );  
     	por.setCannedAcl(CannedAccessControlList.PublicRead);
     	s3Client.putObject( por );
     	awsPath = "https://s3.amazonaws.com/reactcontent/"+por.getKey();
      	Log.d("Guide","file uploaded: "+awsPath);
        return true;

     }
     catch ( Exception exception ) {
     	Log.e("Guide", "Upload Failure:"+ exception.getMessage() );
     	return false;
     }

    }
    public void clickBlack(View v){
    	mView.mColorIndex = 7;
    }
    
    public void clickBlue(View v){
    	mView.mColorIndex = 5;
    }
    public void clickYellow(View v){
    	mView.mColorIndex = 2;
    }
    
    public void clickRed(View v){
    	mView.mColorIndex = 1;
    }
    public void clickGreen(View v){
    	mView.mColorIndex = 3;
    }




}
