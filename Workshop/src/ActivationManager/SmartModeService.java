package ActivationManager;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import Bing.BingServices;
import Bing.StaticMap;
import Generator.BlockCollageBuilder;
import Generator.BlockTemplate;
import Generator.MapCollageBuilder;
import Generator.MapTemplate;

public class SmartModeService {
	private static ScheduledExecutorService scheduler = null;
	private static ActivationManager manager = ActivationManager.getInstance();  
	private static final int INTERVAL_IN_SECONDS = 30;

	private SmartModeService() {
	}

	public static void startService() {
		if (scheduler == null) {
			scheduler =  Executors.newScheduledThreadPool(1);
			// waits INTERVAL_IN_SECONDS seconds after end of last execution
			scheduler.scheduleWithFixedDelay(new Runnable() {
				@Override
				public void run() {
					manager.consumeDedictedRequests(); 
					boolean collageNeeded = manager.processPhotoBuffer();

					if (collageNeeded) {
//						MapTemplate template = MapTemplate.getTemplate(1);
//						StaticMap map = BingServices.getStaticMap(BingServices.getImagesPointsList(),890,523);
						//File collageFile = MapCollageBuilder.BuildCollage(template);
						BlockTemplate template = BlockTemplate.getTemplate(1); 
						BlockCollageBuilder builder = new BlockCollageBuilder(template, EventCandidateContainer.getInstance().getAllEventsInContainer());
						builder.populateTemplate();
						builder.BuildCollage();
						
					}
				}
			},
			20,
			INTERVAL_IN_SECONDS,
			TimeUnit.SECONDS);	
		}
	}

	public static void stopService() {
		scheduler.shutdown();
		scheduler = null;
	}

	public static boolean isServiceRunning() {
		return (scheduler != null);
	}
}
