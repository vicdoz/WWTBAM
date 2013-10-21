package com.adm.whowantstobeamillionaire;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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
		ListView list = (ListView) findViewById(R.id.puntuaciones_lista);

		ArrayList<HashMap<String, String>> listaPuntuaciones = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map = new HashMap<String, String>();

		//Ejemplo de como a�adir
		map = new HashMap<String, String>();          
		map.put("Nombre", "victor");
		map.put("score", "200");
		listaPuntuaciones.add(map);
		//Database db=new Database();
		//map=db.leerPuntuaciones(this);
		//listaPuntuaciones.add(map);
		

		SimpleAdapter adaptador = new SimpleAdapter(this, listaPuntuaciones, R.layout.puntuacion,
		            new String[] {"Nombre", "score"}, new int[] {R.id.Nombre, R.id.score});
		list.setAdapter(adaptador);
		
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.scores, menu);
		return true;
	}
	
	
}
