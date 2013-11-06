//===================== Autores =====================
//
// Ahuir Dominguez, Victor		vicahdo@fiv.upv.es
// Tomas Sendra, Josep Maria	jotosen@fiv.upv.es
//
//===================== ADM-2013 ====================

package com.adm.whowantstobeamillionaire;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.Window;

public class CreditosActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_creditos);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.creditos, menu);
		return true;
	}

}
