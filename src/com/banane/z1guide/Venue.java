package com.banane.z1guide;

public class Venue {
	private String Name;
	private String Id;
	private String Description;
	private String Address;
	private String Lat;
	private String Long;
	private String ImagePath;
	private String WebSite;

	public Venue(String Name, String Id, String ImagePath, String Description, String Lat, String Long, String Address, String WebSite) {
		this.Name = Name;
		this.Id = Id;
		this.ImagePath = ImagePath;
		this.Description = Description;
		this.Address = Address;
		this.Lat = Lat;
		this.Long = Long;
		this.WebSite = WebSite;
	}

	public String getName() {
		return Name;
	}
	public String getId() {
		return Id;
	}
	public String getWebSite() {
		return WebSite;
	}
	public String getDescription() {
		return Description;
	}
	public String getAddress() {
		return Address;
	}
	public String getLat() {
		return Lat;
	}
	public String getLong() {
		return Long;
	}
	public String getImagePath() {
		return ImagePath;
	}
}