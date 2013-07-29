package ActivationManager;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;

import android.util.Log;
import Common.Photo;

public class ActivationManager {

	private static final ActivationManager instance = new ActivationManager();

	//states
	private static final int REGULAR_MODE = 0;
	private static final int DEDICATED_MODE = 1;

	private static final int BUFFER_SIZE = 100;

	// TODO: maybe do this by number of photos for collage
	private static final int CANDIDATE_EVENTS_FOR_COLLAGE = 5;
	private static final int NEW_CANDIDATE_THRESHOLD_DELTA = 30;

	//instance fields
	private BlockingQueue<Photo> buffer = new LinkedBlockingQueue<Photo>();
	private int currentState = 0; // start in REGULAR_MODE;
	private int remainingEvents = CANDIDATE_EVENTS_FOR_COLLAGE;
	private int remainingHorizontal = 0;
	private int remainingVertical = 0;


	private ActivationManager() {

	}

	public static ActivationManager getInstance() {
		return instance;
	}

	private boolean isNewEventCandidate(Photo newPhoto) {
		Photo lastPhoto = EventCandidateContainer.getInstance().getLastAddedEvent().getLastAddedPhoto();
		int delta = lastPhoto.timeDeltaInSecondsFrom(newPhoto);
		return (delta > NEW_CANDIDATE_THRESHOLD_DELTA) ? true : false;
	}

	private boolean isCollageNeeded() {
		return (((currentState == DEDICATED_MODE || currentState == REGULAR_MODE)) && (remainingEvents == 0) || // currentState == DEDICATED_MODE || currentState == REGULAR_MODE
				(currentState == DEDICATED_MODE && 
				((remainingHorizontal == 0) ||
						(remainingVertical == 0)))); 
	}
	/**
	 * 
	 * @param photo
	 * @return TRUE if next module should be awakened
	 */
	private boolean processPhoto(Photo photo) {
		EventCandidate event = null;
		if (EventCandidateContainer.getInstance().isEmpty() || isNewEventCandidate(photo)) {  // new event
			event = new EventCandidate(photo);
			EventCandidateContainer.getInstance().addEvent(event);

			if (remainingEvents > 0) { 
				remainingEvents--;
			}
		}
		else  { // add photo to last added event in container
			event = EventCandidateContainer.getInstance().getLastAddedEvent();
			if (!event.isPhotoInEvent(photo)) {
				event.addPhoto(photo);
			}
			else {
				// TODO: handle this situation that should not happen
			}
		}

		if (currentState == DEDICATED_MODE && photo.isHorizontal()) {
			remainingHorizontal--;
		}
		if ((currentState == DEDICATED_MODE && !photo.isHorizontal())) {
			remainingVertical--;
		}

		if (isCollageNeeded()) {
			setToRegularMode(); // upon decision to create collage, resume REGULAR_MODE
			return true;
		}
		else 
			return false;
	}

	public void processPhotoBuffer() {

		// empty the buffer
		Photo photo = null;
		while (!buffer.isEmpty()) { 
			photo = buffer.remove();
			if (photo != null) {
				processPhoto(photo);
			}
		}
	}

	public void addToBuffer(Photo p) {
		//		synchronized (buffer) {
		//			while (buffer.size() >= BUFFER_SIZE) {
		//				try {
		//					wait();
		//				} catch (InterruptedException e) {
		//					// TODO Auto-generated catch block
		//					e.printStackTrace();
		//				}
		//			}
		try {
			buffer.put(p);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//
		//			buffer.notify();
		//		}
	}

	public boolean consumeDedictedRequest(DedicatedRequest request) {
		// make sure dedicated request has information
		if ((request.getEventsNeeded() != 0 || request.getVerticalNeeded() !=0 || request.getHorizontalNeeded() != 0)) {
			setToDedicatedMode(request);
		}
		return true;
	}

	private void setMode(int newState, DedicatedRequest request) {
		switch (newState) {
		case DEDICATED_MODE: {
			if (request != null) {
				this.remainingEvents = request.getEventsNeeded();
				this.remainingHorizontal = request.getHorizontalNeeded();
				this.remainingVertical = request.getVerticalNeeded();
				currentState = DEDICATED_MODE;
			}
			break;
		}
		case REGULAR_MODE: {
			remainingEvents = CANDIDATE_EVENTS_FOR_COLLAGE;
			remainingHorizontal = 0;
			remainingVertical = 0;
			currentState = REGULAR_MODE;
		}
		}
	}

	private synchronized void setToRegularMode() {
		setMode(REGULAR_MODE, null);
	}

	private synchronized void setToDedicatedMode(DedicatedRequest request) {
		setMode(DEDICATED_MODE, request);
	}
}
