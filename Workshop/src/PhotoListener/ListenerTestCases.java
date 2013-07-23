package PhotoListener;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.junit.Test;

import ActivationManager.ActivationManagerThread;
import Bing.BingServices;
import Bing.SamplePost;
import Common.Photo;
import Common.Point;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;


public class ListenerTestCases {

	@Test
	public void extractionTest() {

		PhotoListenerThread t = new PhotoListenerThread();

		File directory = new File("C:\\Users\\yonatan\\WorkshopRepository\\photos");
		for (File file : directory.listFiles()) {
			Photo p = t.createPhotoFromFile(file);
			if (p != null) {
				ActivationManagerThread.getInstance().addToBuffer(p);
			}
		}

		ActivationManagerThread.getInstance().processPhotoBuffer();
	}
}
