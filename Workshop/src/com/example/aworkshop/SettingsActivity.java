package com.example.aworkshop;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import ActivationManager.ScheduledModeService;
import ActivationManager.SmartModeService;
import Common.Photo;
import Common.PhotoFilter;
import Partitioning.Cluster;
import Partitioning.DBScan;
import Partitioning.TestDBScan;
import PhotoListener.PhotoListenerThread;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;

public class SettingsActivity extends FragmentActivity { // Extends FragmentActivity to support < Android 3.0

	// static final fields
	public static Context CONTEXT = null;
	public static final File ROOT = new File(Environment.getExternalStorageDirectory(), "DCIM");
	//		File dataDirectory = new File(root + "/DCIM/Camera/");
	private static final String  PHOTO_DIR = ROOT + File.separator + "Tests" + File.separator;
	//	private static final String  PHOTO_DIR = ROOT + File.separator + "copy" + File.separator;


	// global fields
	PhotoListenerThread observer;

	// private fields
	//private TimePickerDialog timePickerDialog;
	private RadioButton dailyRadioBtn;
	private RadioGroup modeGroup;
	private RadioButton lastCheckedButton;

	private int pickerHour = 20;
	private int pickerMin = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		CONTEXT = this;

		//		// 		Yonatan's code
		//		//
		//
		observer = new PhotoListenerThread(PHOTO_DIR); // observer over the gallery directory
		observer.startWatching();

		dailyRadioBtn = (RadioButton) findViewById(R.id.radioDaily);
		modeGroup = (RadioGroup) findViewById(R.id.radioMode);
		lastCheckedButton = (RadioButton) findViewById(R.id.radioOff);

		OnClickListener listener = new ScheduledModeListener(); // use same listener every time
		dailyRadioBtn.setOnClickListener(listener);
		
		PhotoFilter.filter();

		

		//		//		Omri's code
		//
					File directory = new File(PHOTO_DIR);
						if (!directory.exists())
							return;
						File[] arrayOfPic =  directory.listFiles();
						Photo tempPhoho = null;
						List<Photo> photosToCluster = new LinkedList<Photo>(); 
						for (File file : arrayOfPic)
						{
							try
							{
								tempPhoho = PhotoListenerThread.createPhotoFromFile(file.getAbsolutePath());
							}
							catch (Exception ex)
							{
							}
							if (tempPhoho != null)
								photosToCluster.add(tempPhoho);
						}
						DBScan algorithmDbScan = new DBScan(photosToCluster);
						List<Cluster> clusterts = algorithmDbScan.runAlgorithmClusters();
						Cluster tempClusterForSotrting = clusterts.get(0);
						tempClusterForSotrting.sortPhotosInClusterByData();
						
		//				TestDBScan dbScanTester = new TestDBScan();
		//				dbScanTester.savePicturesAccordingToClusters(clusterts, PHOTO_DIR);
		//				return;
		//		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

	public void onRadioButtonClicked(View view) {

		lastCheckedButton = (RadioButton) view;
		boolean checked = lastCheckedButton.isChecked();

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
				offButtonClicked();
			}

		}
	}

	/**
	 * when off button is pressed, services need to be turned off
	 */
	private void offButtonClicked() {

		// turn off active modes
		if (SmartModeService.isServiceRunning()) {
			turnOffSmartMode();
		}
		if (ScheduledModeService.isServiceRunning()) {
			turnOffDailyMode();
		}

	}

	private void dailyButtonClicked() {

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

	private class ScheduledModeListener implements View.OnClickListener { 


		@Override
		public void onClick(View v) {

			final TimePicker timePickerDialog = new TimePicker(v.getContext());
			timePickerDialog.setIs24HourView(true);
			timePickerDialog.setCurrentHour(pickerHour);
			timePickerDialog.setCurrentMinute(pickerMin);

			// creating AlertDialog because of no cancel button in TimePickerDialog
			new AlertDialog.Builder(v.getContext())
			.setTitle("Choose Time...")
			.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					SettingsActivity.this.pickerHour = timePickerDialog.getCurrentHour();
					SettingsActivity.this.pickerMin = timePickerDialog.getCurrentMinute();
					dailyButtonClicked();
				}
			})
			.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog,	int which) {
					modeGroup.check(lastCheckedButton.getId());
				}
			}).setView(timePickerDialog).show();
		}
	}

	@Override
	protected void onDestroy() {
		offButtonClicked(); // shutdown all services
		super.onDestroy();
	}

}
