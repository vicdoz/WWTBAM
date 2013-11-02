package com.adm.whowantstobeamillionaire;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.R.drawable;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

import com.google.gson.Gson;

public class ScoresActivity extends Activity {
	
	TabHost tabs;
	AsyncTask<Void, Void, InputStream> getScoresServer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
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
		listaPuntuaciones=LeerPuntuacionesDB();
		SimpleAdapter adaptador = new SimpleAdapter(this, listaPuntuaciones, R.layout.puntuacion,
		            new String[] {"Nombre", "score"}, new int[] {R.id.Nombre, R.id.score});
		list.setAdapter(adaptador);
		
	}

	
	private ArrayList<HashMap<String, String>> LeerPuntuacionesDB() {
		MyHelper db=new MyHelper(getApplicationContext());
		//Al crear la tabla ,solo se crea si no existe.Si existe no hace nada
		db.crearBD(getApplicationContext());
		return db.leerPuntuaciones(getApplicationContext());
		
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.scores, menu);
		MenuItem menuItemDeleteScore = menu.findItem(R.id.MenuItemDeleteScore);
		menuItemDeleteScore.setIcon(drawable.ic_menu_call);
		menuItemDeleteScore.setOnMenuItemClickListener(handleMenuItemDeleteScore);
		return true;
	}
	OnMenuItemClickListener handleMenuItemDeleteScore = new OnMenuItemClickListener() {
		@Override
		public boolean onMenuItemClick(MenuItem arg0) {
			borrarBaseDeDatos();
			redibujarTablaPuntuaciones();
			return true;
		}

		private void borrarBaseDeDatos() {
			MyHelper db=new MyHelper(getApplicationContext());
			db.borrarPuntuaciones(getApplicationContext());
			db.close();
		}

		private void redibujarTablaPuntuaciones() {
			ListView list = (ListView) findViewById(R.id.puntuaciones_lista);
			ArrayList<HashMap<String, String>> listaVacia = new ArrayList<HashMap<String, String>>();
			SimpleAdapter adaptador = new SimpleAdapter(getApplicationContext(), listaVacia, R.layout.puntuacion,
			            new String[] {"Nombre", "score"}, new int[] {R.id.Nombre, R.id.score});
			list.setAdapter(adaptador);
			
		}
	};
	
	OnTabChangeListener handlerTabs = new OnTabChangeListener() {

		@Override
		public void onTabChanged(String tabId) {

			int i = tabs.getCurrentTab();
		
			if(i==1){
			
			}
		}
	};
    

	private void getScores(){
		InputStream puntuaciones=recibirPuntuaciones();
		Gson gson = new Gson();
		Reader reader = new InputStreamReader(puntuaciones);
		HighScoreList hsl = gson.fromJson(reader, HighScoreList.class);
		List<HighScore> results =hsl.getScores();
	};
	
	
	private InputStream recibirPuntuaciones(){
		
		getScoresServer = new AsyncTask<Void,Void,InputStream>() {
		@Override
		protected InputStream doInBackground(Void... params) {
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet paquete =
			    new HttpGet("http://wwtbamandroid.appspot.com/rest/highscores"); 
			paquete.setHeader("content-type", "application/json"); 
			try
			{
			        HttpResponse resp = httpClient.execute(paquete);
			        HttpEntity getResponseEntity = resp.getEntity();
			        return getResponseEntity.getContent();
			}
			catch(Exception ex)
			{
			        Log.e("ServicioRest","Error!", ex);
			}
			return null;

		};
		};
		return null;
	}
	
}
