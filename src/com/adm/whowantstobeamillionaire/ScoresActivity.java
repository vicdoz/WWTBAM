package com.adm.whowantstobeamillionaire;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TabHost;

public class ScoresActivity extends Activity {
	
	TabHost tabs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scores);
		
		/*Resources res = getResources();
 
TabHost tabs=(TabHost)findViewById(android.R.id.tabhost);
tabs.setup();
 
TabHost.TabSpec spec=tabs.newTabSpec("mitab1");
spec.setContent(R.id.tab1);
spec.setIndicator("",
    res.getDrawable(android.R.drawable.ic_btn_speak_now));
tabs.addTab(spec);
 
spec=tabs.newTabSpec("mitab2");
spec.setContent(R.id.tab2);
spec.setIndicator("TAB2",
    res.getDrawable(android.R.drawable.ic_dialog_map));
tabs.addTab(spec);
 
tabs.setCurrentTab(0);*/
		
		tabs = (TabHost)findViewById(android.R.id.tabhost);
		tabs.setup();
		
		TabHost.TabSpec spec = tabs.newTabSpec(getResources().getString(R.string.tab_local));
		spec.setContent(R.id.tabLocal);
		spec.setIndicator(getResources().getString(R.string.tab_local));
		tabs.addTab(spec);
		
		spec = tabs.newTabSpec(getResources().getString(R.string.tab_friends));
		spec.setContent(R.id.tabFriends);
		spec.setIndicator(getResources().getString(R.string.tab_friends));
		tabs.addTab(spec);
		
		tabs.setCurrentTab(0);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.scores, menu);
		return true;
	}

}