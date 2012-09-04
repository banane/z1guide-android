package com.banane.z1guide;

import java.util.ArrayList;

import android.app.Application;

public class Guide extends Application {


	  private ArrayList<Program> programsArray;
	  private ArrayList<String> programTypesArray;

	 
	 
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
}
