package com.banane.z1guide;

import java.sql.Date;
import java.text.SimpleDateFormat;

import android.net.ParseException;


public class Program {
	private String Name;
	private String ProgramType;
	private String Id;
	private String ImagePath;
	private String VenueId;
	private String Artists;
	private String EndDate;
	private String StartDate;
	private String WebSite;
	private Date startDateObject;
	

	public Program(String Name, String ProgramType, String Id, String ImagePath, String Artists, String VenueId, String EndDate, String StartDate, String WebSite) {
	        this.Name = Name;
	        this.ProgramType = ProgramType;
	        this.Id = Id;
	        this.ImagePath = ImagePath;
	        this.Artists = Artists;
	        this.VenueId = VenueId;
	        this.EndDate = EndDate;
	        this.StartDate = StartDate;
	        this.WebSite = WebSite;
	    		
	}

	public String getName() {
	    return Name;
	}

	public String getProgramType() {
		return ProgramType;
	}
	
	public String getId() {
		return Id;
	}
	
	
	
	public String getImagePath() {
		return ImagePath;
	}
	public String getVenueId() {
		return VenueId;
	}
	public String getArtists() {
		return Artists;
	}
	public String getEndDate() {
		return EndDate;
	}
	public String getStartDate() {
		return EndDate;
	}
	public String getWebSite() {
		return EndDate;
	}
	 
}
