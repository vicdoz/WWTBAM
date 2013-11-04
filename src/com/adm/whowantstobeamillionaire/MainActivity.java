package com.adm.whowantstobeamillionaire;

import java.io.FileReader;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	Button playButton;
	Button scoresButton;
	Button settingsButton;
	MenuItem creditosItem;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		this.setTitle("Millionaire");
		
		setContentView(R.layout.activity_main);
		
		playButton = (Button)findViewById(R.id.buttonPlay);
		scoresButton = (Button)findViewById(R.id.buttonScores);
		settingsButton = (Button)findViewById(R.id.buttonSettings);
		
		playButton.setOnClickListener(handlerButtonPlay);
		scoresButton.setOnClickListener(handlerButtonScores);
		settingsButton.setOnClickListener(handlerButtonSettings);
		
		if(Integer.parseInt(getBatteryStatus())<15){
			Toast.makeText(getApplicationContext(), R.string.batteryLow, Toast.LENGTH_LONG).show();		        	
        	
		}
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		creditosItem = menu.findItem(R.id.creditosMenuItem);
		creditosItem.setOnMenuItemClickListener(handlerItemCreditos);
		
		return true;
	}
	
	

	// LISTENERS
	
	
	
	View.OnClickListener handlerButtonPlay = new View.OnClickListener() {
	    public void onClick(View v) {
	    	playButton.setBackgroundResource(R.drawable.button_opcion_selected);
	    	playButton.postDelayed(swapSelectedPlay, 500);
	    	startActivity(new Intent(MainActivity.this, PlayActivity.class));
	    }
	};
	
	View.OnClickListener handlerButtonScores = new View.OnClickListener() {
	    public void onClick(View v) {
	    	scoresButton.setBackgroundResource(R.drawable.button_opcion_selected);
	    	startActivity(new Intent(MainActivity.this, ScoresActivity.class));
	    	scoresButton.postDelayed(swapSelectedScores, 500);
	    }
	};

	View.OnClickListener handlerButtonSettings = new View.OnClickListener() {
	    public void onClick(View v) {
	    	settingsButton.setBackgroundResource(R.drawable.button_opcion_selected);
	    	startActivity(new Intent(MainActivity.this, SettingsActivity.class));
	    	settingsButton.postDelayed(swapSelectedSettings, 500);
	    }
	};
	
	OnMenuItemClickListener handlerItemCreditos = new OnMenuItemClickListener() {
		@Override
		public boolean onMenuItemClick(MenuItem arg0) {
			startActivity(new Intent(MainActivity.this, CreditosActivity.class));
			return false;
		}
	};
	
	
	
	// RUNABLES
	
	
	
	Runnable swapSelectedPlay = new Runnable() {
	    @Override
	    public void run() {
	        playButton.setBackgroundResource(R.drawable.button_opcion);
	    }
	};
	
	Runnable swapSelectedScores= new Runnable() {
	    @Override
	    public void run() {
	        scoresButton.setBackgroundResource(R.drawable.button_opcion);
	    }
	};
	
	Runnable swapSelectedSettings = new Runnable() {
	    @Override
	    public void run() {
	        settingsButton.setBackgroundResource(R.drawable.button_opcion);
	    }
	};
	
	 private static final String readSysFsFile(String a_File)throws Exception{
			FileReader a_Reader =new FileReader(a_File);
			int a_Char;
			String a_Content ="";
			while((a_Char = a_Reader.read()) !=-1){
				if (a_Char != 10){
					a_Content += Character.toString((char) a_Char);
				}
			}
			return a_Content;
		}
		/**
		 * Funcion para leer el estado de la bateria
		 * @return
		 */
		public static String getBatteryStatus(){
		try{
			String a_Capacity =readSysFsFile("/sys/class/power_supply/battery/capacity");
			if(a_Capacity.length() == 0){
				a_Capacity ="Error";
		}
		return a_Capacity;
		}
		catch (Exception a_Ex){
			return "Error";
		}
		}

	


}
