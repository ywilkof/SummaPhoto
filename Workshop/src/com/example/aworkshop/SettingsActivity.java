package com.example.aworkshop;

import java.io.File;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import org.joda.time.DateTime;


import ActivationManager.ScheduledModeService;
import ActivationManager.SmartModeService;
import Common.Photo;
import Partitioning.Cluster;
import Partitioning.DBScan;
import PhotoListener.PhotoListenerThread;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TimePicker;

public class SettingsActivity extends FragmentActivity { // Extends FragmentActivity to support < Android 3.0

	// static final fields
	public static final File ROOT = new File(Environment.getExternalStorageDirectory(), "DCIM");
	//		File dataDirectory = new File(root + "/DCIM/Camera/");
	private static final String  PHOTO_DIR = ROOT + File.separator + "Camera" + File.separator;

	// global fields
	PhotoListenerThread observer;

	// private fields
	private TimePicker timePicker;
	private int pickerHour = 20;
	private int pickerMin = 0;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		// 		Yonatan's code
		//

		observer = new PhotoListenerThread(PHOTO_DIR); // observer over the gallery directory
		observer.startWatching();

		// disable time picker
		timePicker = (TimePicker) findViewById(R.id.timePicker);
		timePicker.setIs24HourView(true);
		timePicker.setCurrentHour(20);
		timePicker.setCurrentMinute(0);
		timePicker.setEnabled(false);
		timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				SettingsActivity.this.pickerHour = hourOfDay;
				SettingsActivity.this.pickerMin = minute;
				dailyButtonClicked();
			}
		});


		//		Omri's code

		//		File directory = new File(PHOTO_DIR);
		//		if (!directory.exists())
		//			return;
		//		File[] arrayOfPic =  directory.listFiles();
		//		Photo tempPhoho = null;
		//		List<Photo> photosToCluster = new LinkedList<Photo>(); 
		//		for (File file : arrayOfPic)
		//		{
		//			try
		//			{
		//				tempPhoho = PhotoListenerThread.createPhotoFromFile(file.getAbsolutePath());
		//			}
		//			catch (Exception ex)
		//			{
		//			}
		//			if (tempPhoho != null)
		//				photosToCluster.add(tempPhoho);
		//		}
		//		DBScan algorithmDbScan = new DBScan(photosToCluster);
		//		List<Cluster> clusterts = algorithmDbScan.runAlgorithmClusters();
		//		return;
		//

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

	public void onRadioButtonClicked(View view) {

		boolean checked = ((RadioButton) view).isChecked();

		// Check which radio button was clicked
		switch(view.getId()) {
		case R.id.radioSmart:
			if (checked) {
				smartButtonClicked();
			}
			break;
		case R.id.radioDaily:
			if (checked) {
				dailyButtonClicked();
			}
			break;
		case R.id.radioOff: 
			if (checked) {

			}

		}
	}

	/**
	 * when off button is pressed, services need to be turned off
	 */
	private void offButtonClicked() {

		timePicker.setEnabled(false);

		// turn off active mode
		turnOffSmartMode();
		turnOffDailyMode();

	}

	private void dailyButtonClicked() {

		if (!timePicker.isEnabled())
			timePicker.setEnabled(true);

		if (SmartModeService.isServiceRunning())
			turnOffSmartMode();

		Thread thread = new Thread() {

			@Override
			public void run() {
				ScheduledModeService.startService(SettingsActivity.this.pickerHour, SettingsActivity.this.pickerMin);
			}
		};

		thread.run();
	}

	private void smartButtonClicked() {

		timePicker.setEnabled(false);
		turnOffDailyMode();

		Thread thread = new Thread() {

			@Override
			public void run() {
				SmartModeService.startService(); 
			}
		};

		thread.run();

	}

	private void turnOffSmartMode() {
		if (SmartModeService.isServiceRunning())
			SmartModeService.stopService();
	}

	private void turnOffDailyMode() {
		if (ScheduledModeService.isServiceRunning())
			ScheduledModeService.stopService();
	}

	//	public class TimePickerFragment extends DialogFragment implements OnTimeSetListener {
	//
	//		@Override
	//		public Dialog onCreateDialog(Bundle savedInstanceState) {
	//			// Use the current time as the default values for the picker
	//			final Calendar c = Calendar.getInstance();
	//			int hour = c.get(Calendar.HOUR_OF_DAY);
	//			int minute = c.get(Calendar.MINUTE);
	//
	//			// Create a new instance of TimePickerDialog and return it
	//			return new TimePickerDialog(getActivity(), this, hour, minute,
	//					DateFormat.is24HourFormat(getActivity()));
	//		}
	//
	//		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
	//			SettingsActivity.this.pickerHour = hourOfDay;
	//			SettingsActivity.this.pickerMin = minute; 
	//		}
	//	}
	//
	//	public void showTimePickerDialog(View v) {
	//		TimePickerFragment newFragment = new TimePickerFragment();
	//		newFragment.show(getFragmentManager(), "timePicker"); 
	//	}

//	private class ServiceAsyncTask extends AsyncTask<Integer, Integer, Exception> {
//
//		@Override
//		protected Exception doInBackground(Integer... params) {
//			Exception ret = null;
//			try {
//				if (params[0] == 1)
//
//
//					if (params[0] == 2)
//			}
//			catch (Exception exception) {
//				ret = exception;
//			}
//			return ret;
//		}
//
//	}

}