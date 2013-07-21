package ActivationManager;

import java.util.LinkedList;
import java.util.Queue;
import Common.Photo;

public class ActivationManagerThread {

	private static final ActivationManagerThread instance = null;
	
	//states
	private static final int REGULAR_MODE = 0;
	private static final int DEDICATED_MODE = 1;
	
	private Queue<Photo> buffer = new LinkedList<Photo>();
	
	private int currentState = 0;
	
	private ActivationManagerThread() {
	}

	public static ActivationManagerThread getInstance() {
		return instance;
	}
	

}