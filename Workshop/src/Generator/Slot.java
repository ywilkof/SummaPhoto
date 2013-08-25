package Generator;

import org.joda.time.tz.UTCProvider;
import org.junit.experimental.max.MaxCore;

import android.R.integer;
import Common.Photo;

/**
 * Represents a fixed empty space for a photo in a template
 * @author yonatan
 *
 */
public class Slot{

	private PixelPoint topLeft;
	private PixelPoint bottomRight;
	private PixelPoint topRight;
	private PixelPoint bottomLeft;

	private boolean horizontal;	
	private String path = null; // the photo that fills the slot

	public Slot(PixelPoint topLeft, PixelPoint bottomRight) {
		this.topLeft = topLeft;
		this.bottomRight = bottomRight;
		this.topRight = new PixelPoint(bottomRight.getX(), topLeft.getY());
		this.bottomLeft = new PixelPoint(topLeft.getX(), bottomRight.getY());
		this.horizontal = (getSlotWidth() > getSlotHeight()); 
	}

	public void assignToPhoto(Photo photo) {
		if (photo != null) {
			this.path = photo.getFilePath();
		}
	}

	public String getPhotoPath() {
		return this.path;
	}
	public boolean isAssignedToPhoto() {
		return (path != null);
	}

	public PixelPoint getTopLeft() {
		return this.topLeft;
	}

	public PixelPoint getTopRight() {
		return this.topRight;
	}

	public PixelPoint getBottomLeft() {
		return this.bottomLeft;
	}

	public PixelPoint getBottomRight() {
		return this.bottomRight;
	}

	public boolean isHorizontal() {
		return this.horizontal;
	}
	
	public double getSlotWidth() {
		return Math.abs(bottomRight.distanceFrom(new PixelPoint(topLeft.getX(), bottomRight.getY())));
	}

	public double getSlotHeight() {
		return Math.abs(bottomRight.distanceFrom(new PixelPoint(bottomRight.getX(), topLeft.getY())));
	}
	
	/**
	 * calculate minimum image size that respects original ratio AND bigger than slot dimensions
	 * Important: Assuming that Slot dimensions are always smaller than p dimensions
	 * @param p - photo to calculate new dimensions for
	 * @return [0] == width, [1] == height, 
	 * s.t. width>=slot.getWidth() && height>=slot.getHeight && (width/height) == (p.getWidth()/p.getheight)
	 */
	public int[] getProportionateDimensions(int sourceWidth, int sourceHeight) {
	
		int newWidth;
		int newHeight;
		
		if (horizontal) { 
			newWidth = (int) getSlotWidth();
			newHeight = (int) (sourceHeight * (getSlotWidth() / sourceWidth));
			}
		else {
			newHeight = (int) getSlotHeight();
			newWidth = (int) (sourceWidth * (getSlotHeight() / sourceHeight));
		}
		
		int[] ret = {newWidth, newHeight};
		return ret;
		
	}



}
