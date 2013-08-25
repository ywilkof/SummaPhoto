package Generator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Environment;

public class MapCollageBuilder {
	

	/**
	 * Saves a map collage on disk
	 * @param template - the template with the photos already arranged in Slots
	 * @return
	 */
	public static File BuildCollage(MapTemplate template) {

		Canvas canvas = null;
		FileOutputStream fos = null;
		Bitmap bmpBase = null;

		bmpBase = Bitmap.createBitmap(3264, 2448, Bitmap.Config.RGB_565);
		canvas = new Canvas(bmpBase);

		// draw images saved in Template onto canvas
		for (int slot = 0; slot < template.getNumberOfSlots(); slot ++) {
			try {
				addSlotImageToCanvas(canvas, template.getSlot(slot));
			}
			catch (NullPointerException exception) {
				// TODO: deal with error
			}
		}

		// draw Bing map into output
		try {
			addSlotImageToCanvas(canvas, template.getMapSlot());
		}
		catch (NullPointerException exception) {
			//TODO: deal with error
		}

		// add lines
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(android.graphics.Color.MAGENTA);
		paint.setStrokeWidth(3f);
		canvas.drawLine(642, 2080, 2448, 642, paint);

		File externalStorageDir = new File(Environment.getExternalStorageDirectory(), "Pictures");
		File testsDir = new File(externalStorageDir.getAbsolutePath() + File.separator + "Output");
		File file = null;

		// Save Bitmap to File
		try	{
			file = new File(testsDir, "output.jpg");

			fos = new FileOutputStream(file);
			bmpBase.compress(Bitmap.CompressFormat.JPEG, 100, fos);

			fos.flush();
			fos.close();
			fos = null;
		}
		catch (IOException e) {
			// TODO: deal with error
			e.printStackTrace();
		}
		finally {
			if (fos != null) {
				try {
					fos.close();
					fos = null;
				}
				catch (IOException e) {
					// TODO: deal with error
					e.printStackTrace();
				}
			}

		}

		return file;
	}

	private static void addSlotImageToCanvas(Canvas canvas, Slot slot) {

		// get Image bitmap
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		Bitmap bitmap = BitmapFactory.decodeFile(slot.getPhotoPath(), options);

		// draw bitmap onto canvas
		PixelPoint topleftPixelPoint = slot.getTopLeft();
		PixelPoint bottomRightPixelPoint = slot.getBottomRight();

		canvas.drawBitmap(bitmap,
				new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), // take all source photo
				new Rect(topleftPixelPoint.getX(), // place in output photo
						topleftPixelPoint.getY(),
						bottomRightPixelPoint.getX(), 
						bottomRightPixelPoint.getY()), 
						null);
	}

}
