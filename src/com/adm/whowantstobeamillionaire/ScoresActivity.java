//===================== Autores =====================
//
// Ahuir Dominguez, Victor		vicahdo@fiv.upv.es
// Tomas Sendra, Josep Maria	jotosen@fiv.upv.es
//
//===================== ADM-2013 ====================

package com.adm.whowantstobeamillionaire;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.R.drawable;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.Window;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.Toast;

import com.google.gson.Gson;

public class ScoresActivity extends Activity {
	
	TabHost tabs;
	ListView list_friends;
	SimpleAdapter adaptadorAmigos;
	//Utiles utiles;
	// Progress Dialog
    private ProgressDialog pDialog;
    // Progress dialog type (0 - for Horizontal progress bar)
    public static final int progress_bar_type = 0; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_scores);
		ScoresActivity.this.setProgressBarIndeterminateVisibility(false);
		
		
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
		/*Para cargar las puntuaciones de los amigos*/
		tabs.setCurrentTab(1);
		list_friends = (ListView) findViewById(R.id.puntuacionesListaAmigos);


		
		
	}
	public void onResume(){
		super.onResume();
		getScores();
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
		if(isConnected()) new recibirPuntuaciones().execute();
		else Toast.makeText(getApplicationContext(), "Sin conexión", Toast.LENGTH_LONG).show();		        	
    	
		
	};
	
	
	private class  recibirPuntuaciones extends AsyncTask<Void,Void,String>{
		
		String result;
		InputStream a;
		StringBuilder sb;
		protected String doInBackground(Void...params) {
			ScoresActivity.this.setProgressBarIndeterminateVisibility(true);
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet paquete =new HttpGet(getResources().getString(R.string.url_highScores)+"?name="+loadPreferencesName()); 	
			try
			{
			        HttpResponse resp = httpClient.execute(paquete);
			        HttpEntity getResponseEntity = resp.getEntity();
			        a=getResponseEntity.getContent();
			        BufferedReader reader = new BufferedReader(new InputStreamReader(a,"iso-8859-1"),8);
			         sb = new StringBuilder();
			         String line = null;
			         while ((line = reader.readLine()) != null) {
			             sb.append(line + "\n");
			         }
			         //a.close();
			         result=sb.toString();
			         
			        // publishProgress(""+(int)((total*100)/lenghtOfFile));
			         return result;
			        
			         
			}
			catch(Exception ex)
			{
			        Log.e("ServicioRest","Error!", ex);
			}
			return null;
		}
		
		
	    /**
	     * Before starting background thread
	     * Show Progress Bar Dialog
	     * */
	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	        showDialog(progress_bar_type);
	    }
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			Gson gson = new Gson();
			
			HighScoreList hsl = gson.fromJson(result, HighScoreList.class);
			ArrayList<HashMap<String, String>> results=hsl.getScoresAsArrayList();
			SimpleAdapter adaptadorAmigos = new SimpleAdapter(getApplicationContext(), results, R.layout.puntuacion,
		            new String[] {"Nombre", "score"}, new int[] {R.id.Nombre, R.id.score});
			list_friends.setAdapter(adaptadorAmigos);
			ScoresActivity.this.setProgressBarIndeterminateVisibility(false);
			dismissDialog(progress_bar_type);
			
		}
		
		private String loadPreferencesName() {
	    	SharedPreferences preferences =
	    			getSharedPreferences("Settings", Context.MODE_PRIVATE);
	    	String nombre=preferences.getString("nombre", "");
	    	return nombre;
		};
	};
	public  boolean isConnected() {
		/**
		 * Comprueba si hay conexión a la red.
		 * @return True o false dependiendo si hay conexión.
		 */
		ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getActiveNetworkInfo();
		return ((info != null) && (info.isConnected()));
	}
	/**
	 * Showing Dialog
	 * */
	@Override
	protected Dialog onCreateDialog(int id) {
	    switch (id) {
	    case progress_bar_type:
	        pDialog = new ProgressDialog(this);
	        pDialog.setMessage(getResources().getString(R.string.downloadScores));
	        pDialog.setIndeterminate(false);
	        pDialog.setMax(100);
	        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        pDialog.setCancelable(true);
	        pDialog.show();
	        return pDialog;
	    default:
	        return null;
	    }
	}
}

