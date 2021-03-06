package Common;

public class GeoBoundingBox {

	private GPSPoint topLeft;
	private GPSPoint bottomRight;
	
	public GPSPoint getTopLeft() {
		return topLeft;
	}
	public GPSPoint getBottomRight() {
		return bottomRight;
	}
	
	public GeoBoundingBox(GPSPoint topLeft,GPSPoint bottomRight) {
		this.topLeft = topLeft;
		this.bottomRight = bottomRight;
	}
	
	public double getWidth() {
		return Math.abs(bottomRight.distanceFrom(new GPSPoint(topLeft.getLatitude(), bottomRight.getLongitude())));
	}
	
	public double getHeight() {
		return Math.abs(bottomRight.distanceFrom(new GPSPoint(bottomRight.getLatitude(), topLeft.getLongitude())));
	}
	
}
