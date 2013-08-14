package Generator;

import org.hamcrest.core.Is;

import Bing.StaticMap;
import android.R.integer;

public class Template {

	//static fields
	public static final int TEMPLATE_1 = 1;
	public static final int TEMPLATE_2 = 2;
	public static final int TEMPLATE_3 = 3;

	//private instance fields
	private Slot[] slots = null; // slots[0] == Top Left Photo
	private StaticMap map = null; // map in center of collage
	private int filledSlotsCounter = 0; // current number of slots filled

	/**
	 * Template can only be created by static methods
	 * @param slotsInTemplate
	 * @param width map pixel width 
	 * @param height map pixel height
	 */
	private Template(int slotsInTemplate, int width, int height) {
		slots = new Slot[slotsInTemplate];
		map = new StaticMap(width, height); // empty static map object with only dimensions known
	}

	public int getCurrentNumberOfSlots() {
		return filledSlotsCounter;
	}

	public int getFullNumberOfSlots() {
		return slots.length;
	}

	public boolean isTemplateFull() {
		return (slots.length == filledSlotsCounter);
	}
	
	public boolean setMap(StaticMap newMap) {
		if (newMap.getPixelWidth() == this.map.getPixelWidth() && // only add map if it meets the planned map dimension
				newMap.getPixelHeight() == this.map.getPixelHeight()) {
			map = newMap;
			return true;
		}
		else {
			return false;
		}
	}
	public static Template getTemplate(int num) {
		switch (num) {
		case 1:
			return getTemplate1();
		default:
			break;
		}
		return null;
	}

	/**
	 * Constructing hard-coded template1
	 * @return
	 */
	private static Template getTemplate1() {
		Template template = new Template(4, 700,600);
		// building slots
		
//		template.slots[0] = new Slot(topLeft, bottomRight);
//		template.slots[1] = new Slot(topLeft, bottomRight);
				
		return template;
	}

}
