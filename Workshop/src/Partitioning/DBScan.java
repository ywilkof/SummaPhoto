package Partitioning;

import java.util.LinkedList;
import java.util.List;
import Common.*;


public class DBScan {
	
	/*Parameters for deciding weather two pictures are "close" to each other as part
	 * of the DBScan algorithm
	 */
	private final double MaxSecondsInterval = 600;	
	private final double MaxMetersInterval = 50; 
	private final int minNumberOfPointsInCluster = 2;
	
	private List<Photo> photosForClustering = null;
	
	public DBScan (List<Photo> photosData)
	{
		this.photosForClustering = photosData;
	}
	
	private boolean isEpsilonDistanced (Photo p1, Photo p2)
	{
		if ((p1.distanceFrom(p2) < MaxMetersInterval) && (p1.timeDeltaInSecondsFrom(p2) < MaxSecondsInterval))
				return true;
		return false;
	}
	
	private List<Photo> regionQueryList (Photo p)
	{
		List<Photo> photosEpsilonClose = new LinkedList<Photo>();
		for (Photo photoCandidate : photosForClustering) {
			if (isEpsilonDistanced(p, photoCandidate))
			{
				photosEpsilonClose.add(photoCandidate);
			}
			
		}
		return photosEpsilonClose;
	}
	
	private class PhotoObjectForClustering 
	{
		private boolean isVisited = false;
		Photo photo = null;
		
		public PhotoObjectForClustering(Photo p)
		{
			this.photo = p;
		}	
	}

}