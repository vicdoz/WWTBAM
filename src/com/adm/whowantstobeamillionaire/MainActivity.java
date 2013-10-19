package com.adm.whowantstobeamillionaire;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	
	Button playButton;
	Button scoresButton;
	Button settingsButton;
	MenuItem creditosItem;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.setTitle("Millionaire");
		
		setContentView(R.layout.activity_main);
		
		playButton = (Button)findViewById(R.id.buttonPlay);
		scoresButton = (Button)findViewById(R.id.buttonScores);
		settingsButton = (Button)findViewById(R.id.buttonSettings);
		
		playButton.setOnClickListener(handlerButtonPlay);
		scoresButton.setOnClickListener(handlerButtonScores);
		settingsButton.setOnClickListener(handlerButtonSettings);
		
		
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
	
	


}
