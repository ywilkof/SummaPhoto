


public class Template {

	//static fields
	public static final int TEMPLATE_1 = 1;
	public static final int TEMPLATE_2 = 2;
	public static final int TEMPLATE_3 = 3;

	//private instance fields
	private Slot[] slots = null; // slots[0] == Top Left Photo
	//private StaticMap map = null; // map in center of collage
	private int filledSlotsCounter = 0; // current number of slots filled
	private Slot mapSlot;
	/**
	 * Template can only be created by static methods
	 * @param slotsInTemplate
	 * @param width map pixel width 
	 * @param height map pixel height
	 */
	private Template(int slotsInTemplate) {
		slots = new Slot[slotsInTemplate];
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

	public double getMapPixelWidth() {
		return mapSlot.getWidth();
	}

	public Slot getMapSlot() {
		return this.mapSlot;
	}
	public double getMapPixelHeight() {
		return mapSlot.getHeight();
	}

//	public boolean setMap(StaticMap newMap) {
//		if (newMap.getPixelWidth() == this.getMapPixelWidth() && // only add map if it meets the planned map dimension
//				newMap.getPixelHeight() == this.getMapPixelHeight()) {
//			map = newMap;
//			return true;
//		}
//		else {
//			return false;
//		}
//	}
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
		Template template = new Template(8);

		// building slots - Counter Clockwise, starting with topLeft
		template.slots[0] = new Slot(new PixelPoint(0, 367), new PixelPoint(642, 1224));
		template.slots[1] = new Slot(new PixelPoint(0, 1224), new PixelPoint(642, 2080));
		template.slots[2] = new Slot(new PixelPoint(775, 1805), new PixelPoint(1632, 2448));
		template.slots[3] = new Slot(new PixelPoint(1632, 1805), new PixelPoint(2488, 2448));
		template.slots[4] = new Slot(new PixelPoint(2621, 1224), new PixelPoint(3264, 2080));
		template.slots[5] = new Slot(new PixelPoint(2621, 367), new PixelPoint(3264, 1224));
		template.slots[6] = new Slot(new PixelPoint(1632, 0), new PixelPoint(2488, 642));
		template.slots[5] = new Slot(new PixelPoint(775, 0), new PixelPoint(1632, 642));

		template.mapSlot = new Slot(new PixelPoint(642, 642), new PixelPoint(2621, 1805));

		return template;
	}

}
