package Common;

public class BoundingBox {

	private Point topLeft;
	private Point bottomRight;
	
	public Point getTopLeft() {
		return topLeft;
	}
	public Point getBottomRight() {
		return bottomRight;
	}
	
	public BoundingBox(Point topLeft,Point bottomRight) {
		this.topLeft = topLeft;
		this.bottomRight = bottomRight;
	}
	
	public double getWidth() {
		return Math.abs(bottomRight.getLatitude()-topLeft.getLatitude());
	}
	
	public double getHeight() {
		return Math.abs(topLeft.getLongitude()-bottomRight.getLongitude());
	}
	
}