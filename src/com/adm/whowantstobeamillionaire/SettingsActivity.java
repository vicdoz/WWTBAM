//===================== Autores =====================
//
// Ahuir Dominguez, Victor		vicahdo@fiv.upv.es
// Tomas Sendra, Josep Maria	jotosen@fiv.upv.es
//
//===================== ADM-2013 ====================

package com.adm.whowantstobeamillionaire;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SettingsActivity extends Activity {
	
	Spinner spinnerAyudas;
	Button buttonAdd;
	EditText editTextNombre;
	EditText editTextFriend;
	AsyncTask<Void, Void, Boolean> register;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_settings);
		spinnerAyudas = (Spinner) findViewById(R.id.spinnerAyudas);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.array_ayudas, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerAyudas.setAdapter(adapter);
		
		buttonAdd = (Button) findViewById(R.id.buttonAddFriend);
		buttonAdd.setOnClickListener(handlerAddFriend);
		
		editTextNombre = (EditText) findViewById(R.id.nombre);
		editTextFriend = (EditText) findViewById(R.id.editTextFriend);
		// Color del texto segun la API
		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
		if (currentapiVersion <= android.os.Build.VERSION_CODES.GINGERBREAD){
		   editTextNombre.setTextColor(Color.BLACK);
		   editTextFriend.setTextColor(Color.BLACK);
		}
		
	}

	@Override
	protected void onResume() {
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
	
	// LISTENERS
	
	View.OnClickListener handlerAddFriend = new View.OnClickListener() {

        public void onClick(View v) {
        	new register_friend().execute();
        }
};

		
	private class register_friend extends AsyncTask<Void, Void, Boolean>{
		/**
		 * Registra a un amigo
		 */
		protected Boolean doInBackground(Void... params) {
			

        	HttpClient client = new DefaultHttpClient();
        	HttpPost request = new HttpPost(getResources().getString(R.string.url_friend));
        	
        	// Obtener nombre del amigo a partir del EditTextFriend
        	String friend = editTextFriend.getText().toString();
        	
        	// Obtener el nombre del usuario a partir del EditTextNombre
        	EditText text = (EditText) findViewById(R.id.nombre);
    		String user = text.getText().toString();
        	
        	List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        	pairs.add(new BasicNameValuePair("name", user));
        	pairs.add(new BasicNameValuePair("friend_name",friend));
        	
        	try {
				request.setEntity(new UrlEncodedFormEntity(pairs));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return false;
			}	
        	try {
        		client.execute(request);
				} catch (ClientProtocolException e) {
				e.printStackTrace();
				return false;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}

        	return true;
        }

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Toast.makeText(getApplicationContext(), "R.string.userAdded", Toast.LENGTH_LONG).show();		        	
        	
		};
        
	};
}

