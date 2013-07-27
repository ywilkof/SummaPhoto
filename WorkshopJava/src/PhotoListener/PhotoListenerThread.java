package PhotoListener;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.GpsDirectory;
import com.drew.metadata.jpeg.JpegDirectory;

import Common.Photo;
import Common.Point;

public class PhotoListenerThread {

	public PhotoListenerThread() {
	}

	public Photo createPhotoFromFile(File path) throws ImageProcessingException {

		// extract photo metadata
		Metadata metadata = null;
		try {
			metadata = ImageMetadataReader.readMetadata(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//get location
		GpsDirectory directory1 = metadata.getDirectory(GpsDirectory.class);

		GeoLocation location = directory1.getGeoLocation();
		if (location == null) { // photo has not location
			return null;
		}

		//get time
		ExifSubIFDDirectory directory2 = metadata.getDirectory(ExifSubIFDDirectory.class);

		Date date = directory2.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);

		Photo photo = null;

		//get dimensions
		JpegDirectory jpgDirectory = metadata.getDirectory(JpegDirectory.class);
		try {
			int width = jpgDirectory.getImageWidth();
			int	height = jpgDirectory.getImageHeight();

			photo = new Photo(
					date,
					width,
					height,
					new Point(location.getLongitude(),location.getLatitude()),
					true,
					path.getPath());
		} catch (MetadataException e) {
			// TODO ERROR reading EXIF details of photo
			e.printStackTrace();
		}

		return photo;
	}

	/**
	 * use EXIFInterface from android to decide orientation
	 * @param width
	 * @param height
	 * @return
	 */
	// TODO: implement
	private static boolean isHorizontal(int width, int height) {
		return false;

	}
}