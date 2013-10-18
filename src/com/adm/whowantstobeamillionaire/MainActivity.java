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
	
	View.OnClickListener handlerButtonPlay = new View.OnClickListener() {
	    public void onClick(View v) {
	    	playButton.setBackgroundResource(R.drawable.button_opcion_selected);
	    }
	};
	
	View.OnClickListener handlerButtonScores = new View.OnClickListener() {
	    public void onClick(View v) {
	    	scoresButton.setBackgroundResource(R.drawable.button_opcion_selected);
	    }
	};

	View.OnClickListener handlerButtonSettings = new View.OnClickListener() {
	    public void onClick(View v) {
	    	settingsButton.setBackgroundResource(R.drawable.button_opcion_selected);
	    }
	};
	
	OnMenuItemClickListener handlerItemCreditos = new OnMenuItemClickListener() {
		@Override
		public boolean onMenuItemClick(MenuItem arg0) {
			startActivity(new Intent(MainActivity.this, CreditosActivity.class));
			return false;
		}
	};


}
