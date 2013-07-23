package Common;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Seconds;

public class Photo implements Comparable<Photo> {

	private Date takenDate;
	private Point location;


	private ActualEvent parentActualEvent;
	private boolean isHorizontal;
	private int height;
	private int width;
	
	//TODO private Image photo;

	public Photo(Date date, int width, int height, Point location, boolean horizontal) {
		this.takenDate = date;
		this.location = location;
		this.height = height;
		this.width = width;
		// TODO: get isHorizontal argument when using the EXIFInterface from android, and not calculate it
		this.isHorizontal = (width > height);
		
	}
		
	public boolean isHorizontal() {
		return isHorizontal;
	}
	
	public Point getLocation() {
		return location;
	}
	
	public int getHeight() {
		return height;
	}
	public int getWidth() {
		return width;
	}
	
	public Date getTakenDate() {
		return this.takenDate;
	}
	
	public void attachToEvent(ActualEvent event) {
		if (event == null)
			parentActualEvent = event;
	}
	
	// TODO: Use google Location DistanceTO
	public double distanceFrom(Photo otherPhoto) {
		return this.getLocation().distanceFrom(otherPhoto.getLocation());
	}
	
	public int timeDeltaInSecondsFrom(Photo otherPhoto) {
		DateTime thisTime = new DateTime(takenDate);
		DateTime otherTime = new DateTime(otherPhoto.getTakenDate());
		
		if (thisTime.isAfter(otherTime)) {
			return Seconds.secondsBetween(otherTime,thisTime).getSeconds();
		}
		else {
			return Seconds.secondsBetween(thisTime, otherTime).getSeconds();
		}
	}

	@Override
	public int compareTo(Photo o) {
		return this.takenDate.compareTo(o.takenDate);
	}
	
	
}
