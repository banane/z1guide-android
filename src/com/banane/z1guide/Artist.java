package com.banane.z1guide;

import java.util.ArrayList;

public class Artist {

	private String Name;
	private String Id;
	private String ImagePath;
	private String Biography;
	private String ProjectId;
	

	public Artist(String Name, String Id, String ImagePath, String Biography, String ProjectId) {
	        this.Name = Name;
	        this.Id = Id;
	        this.ImagePath = ImagePath;
	        this.Biography = Biography;
	        this.ProjectId = ProjectId;
	}

	public String getProjectId() {
	    return ProjectId;
	}

	public String getName() {
		return Name;
	}
	
	public String getId() {
		return Id;
	}
	public String getImagePath() {
		return ImagePath;
	}
	public String getBiography(){
		return Biography;
	}
	
	 
}
