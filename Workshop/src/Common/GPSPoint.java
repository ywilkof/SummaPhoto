package Common;

import android.location.Location;

public class GPSPoint  {

	private double longitude;
	private double latitude;

	public double getLongitude() {
		return longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	
	public GPSPoint(double latitude, double longitude) {
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	public float distanceFrom(GPSPoint p) {
		float[] results = new float[3]; 
		Location.distanceBetween(this.latitude, this.longitude, p.getLatitude(), p.getLongitude(), results);
		return results[0];
	}
	
	@Override
	public String toString() {
		return (latitude + "," + longitude);
	}
}
