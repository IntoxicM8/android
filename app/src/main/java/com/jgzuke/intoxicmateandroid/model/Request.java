package com.jgzuke.intoxicmateandroid.model;


public class Request extends UberModel {
	String request_id;
	public String getRequestId() { return request_id; }
	
	String status;
	public String getStatus() { return status;}

	Driver driver;
	public Driver getDriver() { return driver; }

	int eta;
	public int getEta() { return eta; }
	
	Vehicle vehicle;
	public int getVehicle() { return vehicle; }

	Location location;
	public Location getLocation() { return location; }
	
	float surge_multiplier;
	public float getSurgeMultiplier() { return surge_multiplier; }
}
