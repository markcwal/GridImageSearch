package com.yahoo.markcwal.gridimagesearch.activities;


import com.yahoo.markcwal.gridimagesearch.R;
import com.yahoo.markcwal.gridimagesearch.R.array;
import com.yahoo.markcwal.gridimagesearch.R.id;
import com.yahoo.markcwal.gridimagesearch.R.layout;
import com.yahoo.markcwal.gridimagesearch.models.Settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class SettingsActivity extends Activity {

	private Settings settings;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		settings = (Settings) getIntent().getSerializableExtra("settings");
		loadSettings();
	}
	
	public void saveClick(View v)
	{
		Spinner spSize = (Spinner) findViewById(R.id.spSize);
		settings.setSize(spSize.getSelectedItemPosition());
		
		Spinner spColor = (Spinner) findViewById(R.id.spColor);
		settings.setColor(spColor.getSelectedItemPosition());
		
		Spinner spType = (Spinner) findViewById(R.id.spType);
		settings.setType(spType.getSelectedItemPosition());
		
		EditText etSite = (EditText) findViewById(R.id.etSite);
		settings.setSite(etSite.getText().toString());
		
		// Create result
		Intent i = new Intent();
		i.putExtra("settings", settings);
		
		// Submit my result to parent activity
		setResult(RESULT_OK, i);
		finish();
	}
	
	public void loadSettings()
	{
		//initialize values for image size spinner
		Spinner spSize = (Spinner) findViewById(R.id.spSize);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, 
				R.array.size_array, R.layout.spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spSize.setAdapter(adapter);
		spSize.setSelection(settings.getSizeIndex());
		
		//initialize values for color filter spinner
		Spinner spColor = (Spinner) findViewById(R.id.spColor);
		ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, 
				R.array.color_array, R.layout.spinner_item);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spColor.setAdapter(adapter2);
		spColor.setSelection(settings.getColorIndex());
		
		//initialize values for image type spinner
		Spinner spType = (Spinner) findViewById(R.id.spType);
		ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, 
				R.array.type_array, R.layout.spinner_item);
		adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spType.setAdapter(adapter3);
		spType.setSelection(settings.getTypeIndex());
		
		//initialize value for edit text
		EditText etSite = (EditText) findViewById(R.id.etSite);
		etSite.setText(settings.getSite());
		
	}
}
