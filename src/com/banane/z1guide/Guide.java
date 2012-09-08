package com.banane.z1guide;

import java.util.ArrayList;

import android.app.Application;

public class Guide extends Application {


	  private ArrayList<Program> programsArray;
	  private ArrayList<String> programTypesArray;
	  private ArrayList<Artist> artistsArray;
	  private ArrayList<Venue> venuesArray;
	 
	 
	  public void setProgramsArray(ArrayList<Program> progs){
		programsArray = progs;  
	  }
	  
	  public ArrayList<Program> getProgramsArray(){
		  return programsArray;
	  }
	  public ArrayList<String> getProgramTypesArray(){
		  return programTypesArray;
	  }
	  public void setProgramTypesArray(ArrayList<String> types){
		  programTypesArray = types;
	  }
	  public ArrayList<Artist> getArtistsArray(){
		  return artistsArray;
	  }
	  public void setArtistsArray(ArrayList<Artist> artists){
		  artistsArray = artists;
	  }
	  public ArrayList<Venue> getVenuesArray(){
		  return venuesArray;
	  }
	  public void setVenuesArray(ArrayList<Venue> venues){
		  venuesArray = venues;
	  }
}
