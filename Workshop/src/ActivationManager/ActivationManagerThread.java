package ActivationManager;

import java.util.Timer;
import java.util.TimerTask;

public class ActivationManagerThread {

	Timer timer;
	ActivationManager manager;
	private static ActivationManagerThread instance = null;

	public ActivationManagerThread() {
		manager = ActivationManager.getInstance();
		timer = new Timer();  //At this line a new Thread will be created
		timer.schedule(new ActivationManagerTask(), 10*1000); //delay in milliseconds
	}

	public static void start() {
		if (instance == null)
			instance = new ActivationManagerThread();
	}


	private class ActivationManagerTask extends TimerTask {

		@Override
		public void run() {
			// manager.consumeDedictedRequests(request);
			manager.processPhotoBuffer();
		}
	}
}