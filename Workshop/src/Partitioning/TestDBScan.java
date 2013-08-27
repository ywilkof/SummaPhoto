package Partitioning;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Timestamp;
import java.util.List;

import android.R.string;

import Common.Photo;

public class TestDBScan {

	
	/*
	 * this function is only for testing issues. it enables us to re-save the
	 * pictures according to clustreing desicion
	 */

	public Boolean savePicturesAccordingToClusters(List<Cluster> clustersList,
			String memoryPathInDevice) {
		String subDirectoryPathString;
		String newFilePathString;
		if ((clustersList == null) || (clustersList.isEmpty()))
			return false;
		Integer i = 1;
		try {
			for (Cluster cluster : clustersList) {
				subDirectoryPathString = memoryPathInDevice + "Cluetser" + i.toString();
				File directoryFile = new File(subDirectoryPathString);
				
				directoryFile.mkdir();
				if ((cluster == null) || (cluster.photosInCluster == null)
						|| (cluster.photosInCluster.isEmpty()))
					return false;
				for (Photo photo : cluster.photosInCluster) {
					if (photo == null)
						return false;
					newFilePathString = subDirectoryPathString + File.separator
							+ photo.getPhotoFile().getName();
					savePhotoToMemory(photo, newFilePathString);
				}
				i++;
				File textFile = new File(subDirectoryPathString + File.separator + "timestamps.txt");
				saveDetialsOfPhotosInClusterToFiler(cluster, textFile);
				
			}
		} catch (Exception c) {
			return false;
		}
		return true;
	}

	/** save one photo to memory. newFilePath is the new path for saving the photo **/
	private Boolean savePhotoToMemory(Photo p, String newFilePath) {
		File originalFile;
		File destionationFile;
		try {

			originalFile = p.getPhotoFile();
			if (originalFile == null)
				return false;
			destionationFile = new File(newFilePath);
			copyFile(originalFile, destionationFile);
			return true;
		} catch (Exception c) {
			return false;
		}
	}

	/** actuall copying of the file **/
	private void copyFile(File src, File dst) throws IOException {
		InputStream in = new FileInputStream(src);
		OutputStream out = new FileOutputStream(dst);

		// Transfer bytes from in to out
		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0) {
			out.write(buf, 0, len);
		}
		in.close();
		out.close();
	}
	/** This function wrties the date and time of each photo in cluster to file **/
	private void saveDetialsOfPhotosInClusterToFiler (Cluster cluster, File file)
	{
		try{
			BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
			String photoDeatils;
			for (Photo p: cluster.photosInCluster)
			{
				photoDeatils = p.getTakenDate().toString() + "         "  ;
				writer.append(photoDeatils);
				writer.newLine();
				writer.write("\r\n" );
			}
			writer.close();
		}
		catch (Exception c)
		{
			return;
		}
	}
}