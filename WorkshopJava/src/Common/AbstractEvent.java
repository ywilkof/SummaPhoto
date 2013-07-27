package Common;

import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

public abstract class AbstractEvent {
	
	private UUID eventID;
	protected Set<Photo> eventPhotos = new TreeSet<Photo>();
	
	public AbstractEvent() {
		eventID = UUID.randomUUID();
	}
	
	public boolean isEmpty() {
		return (this.eventPhotos.size() == 0); 
	}

	public UUID getEventID() {
		return this.eventID;	
	}

	public int getEventSize() {
		return this.eventPhotos.size();
	}

	public boolean isPhotoInEvent(Photo photo) {
		return eventPhotos.contains(photo);
	}
	
	public abstract void addPhoto(Photo photo);
}