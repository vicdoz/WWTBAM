package com.adm.whowantstobeamillionaire;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class SettingsActivity extends Activity {
	
	Spinner spinnerAyudas;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_settings);
		spinnerAyudas = (Spinner) findViewById(R.id.spinnerAyudas);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.array_ayudas, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerAyudas.setAdapter(adapter);
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		restoreData();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		saveData();
		super.onPause();
	}
	
	
	public void restoreData() {
		SharedPreferences preferences =
		getSharedPreferences("Settings", Context.MODE_PRIVATE);
		EditText text = (EditText) findViewById(R.id.nombre);
		text.setText(preferences.getString("nombre", ""));
		Spinner spinner = (Spinner) findViewById(R.id.spinnerAyudas);
		spinner.setSelection(preferences.getInt("ayudas",0));
	}
	
	public String getName(){
		SharedPreferences preferences =
		getSharedPreferences("Settings", Context.MODE_PRIVATE);
		return preferences.getString("nombre", "");

	}
	public int nAyudas(){
		SharedPreferences preferences =
		getSharedPreferences("Settings", Context.MODE_PRIVATE);
		return preferences.getInt("ayudas", 0);
	}
	public void saveData() {
		SharedPreferences preferences =
		getSharedPreferences("Settings", Context.MODE_PRIVATE);
		EditText text = (EditText) findViewById(R.id.nombre);
		Editor editor = preferences.edit();
		editor.putString("nombre", text.getText().toString());
		Spinner spinner = (Spinner) findViewById(R.id.spinnerAyudas);
		editor.putInt("ayudas", spinner.getSelectedItemPosition());
		editor.commit();
	}

}
