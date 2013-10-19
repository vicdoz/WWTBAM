package com.adm.whowantstobeamillionaire;

import android.R.drawable;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;

public class PlayActivity extends Activity {
	
	MenuItem menuItemPhone;
	MenuItem menuItem50;
	MenuItem menuItemAudience;
	MenuItem menuItemEnd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.play, menu);
		
		menuItemPhone = menu.findItem(R.id.menuItemPhone);
		menuItemPhone.setIcon(drawable.ic_menu_call);
		
		menuItem50 = menu.findItem(R.id.menuItem50);
		menuItem50.setIcon(drawable.ic_partial_secure);
		
		menuItemAudience = menu.findItem(R.id.menuItemAudience);
		menuItemAudience.setIcon(drawable.ic_menu_agenda);
		
		menuItemEnd = menu.findItem(R.id.menuItemEnd);
		menuItemEnd.setIcon(drawable.ic_input_delete);
		
		return true;
	}

}
