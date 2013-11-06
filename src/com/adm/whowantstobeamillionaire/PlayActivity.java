package com.adm.whowantstobeamillionaire;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.R.drawable;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PlayActivity extends Activity {
	
	MenuItem menuItemPhone;
	MenuItem menuItem50;
	MenuItem menuItemAudience;
	MenuItem menuItemEnd;
	Button opcionA,opcionB,opcionC,opcionD;
	Button header;
	TextView pregunta, dinero;
	int actual;
	int nAyudasDisponibles=3;
	int fifty1,fifty2,correcta,audiencia,telefono;

	String[] puntuacion;
	
	boolean bUsadoTelef=false,bUsadoAudience=false,bUsado50=false;
	
	int statusTelefono=0;
	int statusAudience=0;
	int status50=0;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    	String nombre=loadPreferencesName();
    	if(nombre.length()==0){
    		finish();
    		startActivity(new Intent(PlayActivity.this, SettingsActivity.class));
    	}
    	else{
			 this.requestWindowFeature(Window.FEATURE_NO_TITLE);
			 setContentView(R.layout.activity_play);
			 puntuacion = getResources().getStringArray(R.array.array_puntuaciones);
			 pregunta=(TextView)findViewById(R.id.textViewQuestion);
			 dinero=(TextView)findViewById(R.id.textViewMoney);
			 header=(Button)findViewById(R.id.buttonHeaderPlay);
			 opcionA=(Button)findViewById(R.id.buttonOpcionA);
			 opcionB=(Button)findViewById(R.id.buttonOpcionB);
			 opcionC=(Button)findViewById(R.id.buttonOpcionC);
			 opcionD=(Button)findViewById(R.id.buttonOpcionD);
			 opcionA.setOnClickListener(handlerOpcionA);
			 opcionB.setOnClickListener(handlerOpcionB);
			 opcionC.setOnClickListener(handlerOpcionC);
			 opcionD.setOnClickListener(handlerOpcionD);
			 actual=1;
    	}
	}
	@Override
	protected void onPause() {
		super.onPause();
		saveData();
	}

	@Override
	protected void onResume() {
		super.onResume();
		restoreData();
		String nombre=loadPreferencesName();
		if(nombre.length()==0){

    	}
		else{
			try {
				lectura_pregunta();
			} catch (XmlPullParserException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			header.setText(getResources().getString(R.string.titulo_question)+actual+"  ");
			dinero.setText(puntuacion[actual]+getResources().getString(R.string.local_currency));
			
			if(status50==actual) aplicarComodin50();
			if(statusTelefono==actual) aplicarComodinTelefono();
			if(statusAudience==actual) aplicarComodinAudience();
	
			SharedPreferences preferences =
			getSharedPreferences("Settings", Context.MODE_PRIVATE);
			nAyudasDisponibles= preferences.getInt("ayudas", 0)+1;
		}
		
	}
	
	//acciones a realizar dependiendo del boton de la respuesta que pulsemos
			View.OnClickListener handlerOpcionA = new View.OnClickListener() {
	            public void onClick(View v) {
	            	opcionA.setBackgroundResource(R.drawable.button_opcion_selected);
	            	opcionA.postDelayed(checkOpcionA, 500);
	            }
	        };
	        
	        View.OnClickListener handlerOpcionB = new View.OnClickListener() {
	            public void onClick(View v) {
	            	opcionB.setBackgroundResource(R.drawable.button_opcion_selected);
	            	opcionB.postDelayed(checkOpcionB, 500);
	            }
	        };
	        
	        View.OnClickListener handlerOpcionC = new View.OnClickListener() {
	            public void onClick(View v) {
	            	opcionC.setBackgroundResource(R.drawable.button_opcion_selected);
	            	opcionC.postDelayed(checkOpcionC, 500);
	            }
	        };
	        
	        View.OnClickListener handlerOpcionD = new View.OnClickListener() {
	        	 public void onClick(View v) {
	        		 	opcionD.setBackgroundResource(R.drawable.button_opcion_selected);
		            	opcionD.postDelayed(checkOpcionD, 500);
		         }
		    };
		    
	        private void fin_partida(boolean retirarse) {
	        	//Si se retira se queda con la puntuacion que llevaba.
	        	//Si es que ha fallado se calcula la puntuacion en función de la pregunta.
	        	String[] puntuaciones=getResources().getStringArray(R.array.array_puntuaciones);
	        	String aux;
	        	int puntuacion=0;
	        	if(actual-1>0){ 
	        		aux=puntuaciones[actual-1];
	        		 puntuacion=Integer.valueOf(aux);
	        	}
	        	
	        	if(!retirarse){
	        		if(actual-1<5)puntuacion=0;
	        		else if(actual-1>=5 && actual-1<10)puntuacion=Integer.valueOf(puntuaciones[5]);
	        		else if(actual-1>=10 && actual-1<15)puntuacion=Integer.valueOf(puntuaciones[10]);
	        		else if(actual-1==15)puntuacion=Integer.valueOf(puntuaciones[15]);
	        	}

	        	
	        	MyHelper db=new MyHelper(getApplicationContext());
	        	
	        	String nombre=loadPreferencesName();
	        	db.insertarPuntuacion(nombre, puntuacion);
	        	Toast.makeText(getApplicationContext(),getResources().getString(R.string.finPartida) +nombre+getResources().getString(R.string.score)+": "+puntuacion, Toast.LENGTH_LONG).show();		        	
	        	//Borrado de preferencias
	        	actual=1;
	        	borrarSharedPrefsVar();
	        	
	        	//Actualiza puntuación en el servidor
	        	new updateScore().execute(String.valueOf(puntuacion));
	        	
	        	// Acaba la actividad play y muestra las puntuaciones
	        	startActivity(new Intent(PlayActivity.this, ScoresActivity.class));
				finish();
			}
	        
			private void borrarSharedPrefsVar() {
		    	SharedPreferences settings = getApplicationContext().getSharedPreferences("var", Context.MODE_PRIVATE);
		    	settings.edit().clear().commit();	
				}
			@Override
			public boolean onCreateOptionsMenu(Menu menu) {
				// Inflate the menu; this adds items to the action bar if it is present.
				getMenuInflater().inflate(R.menu.play, menu);
				
				menuItemPhone = menu.findItem(R.id.menuItemPhone);
				menuItemPhone.setIcon(drawable.ic_menu_call);
				menuItemPhone.setOnMenuItemClickListener(handlermenuItemPhone);
				
				menuItem50 = menu.findItem(R.id.menuItem50);
				menuItem50.setIcon(drawable.ic_partial_secure);
				menuItem50.setOnMenuItemClickListener(handlermenuItem50);
				
				menuItemAudience = menu.findItem(R.id.menuItemAudience);
				menuItemAudience.setIcon(drawable.ic_menu_agenda);
				menuItemAudience.setOnMenuItemClickListener(handlermenuItemAudience);	
				
				menuItemEnd = menu.findItem(R.id.menuItemEnd);
				menuItemEnd.setIcon(drawable.ic_input_delete);
				menuItemEnd.setOnMenuItemClickListener(handlermenuItemEnd);	
				
				
				return true;
			}
			@Override
			public boolean onPrepareOptionsMenu(Menu menu) {
			    
				menuItemPhone = menu.findItem(R.id.menuItemPhone);
				menuItemPhone.setIcon(drawable.ic_menu_call);
				menuItemPhone.setOnMenuItemClickListener(handlermenuItemPhone);
				
				menuItem50 = menu.findItem(R.id.menuItem50);
				menuItem50.setIcon(drawable.ic_partial_secure);
				menuItem50.setOnMenuItemClickListener(handlermenuItem50);
				
				menuItemAudience = menu.findItem(R.id.menuItemAudience);
				menuItemAudience.setIcon(drawable.ic_menu_agenda);
				menuItemAudience.setOnMenuItemClickListener(handlermenuItemAudience);	
				
				menuItemEnd = menu.findItem(R.id.menuItemEnd);
				menuItemEnd.setIcon(drawable.ic_input_delete);
				menuItemEnd.setOnMenuItemClickListener(handlermenuItemEnd);	
				
				if(statusTelefono==0)menuItemPhone.setEnabled(true);
				if(statusAudience==0)menuItemAudience.setEnabled(true);
				if(status50==0)menuItem50.setEnabled(true);
				
			    return true;
			}
		
		
			OnMenuItemClickListener handlermenuItemPhone = new OnMenuItemClickListener() {
				@Override
				public boolean onMenuItemClick(MenuItem arg0) {
					nAyudasDisponibles--;
					//bUsadoTelef=true;
					statusTelefono=actual;
					menuItemPhone.setEnabled(false);
					if(nAyudasDisponibles>0){
						aplicarComodinTelefono();
					}else{
						Toast.makeText(getApplicationContext(), R.string.NoHelp, Toast.LENGTH_LONG).show();		        	
					}
					return true;
				}
			};
			OnMenuItemClickListener handlermenuItem50 = new OnMenuItemClickListener() {
				@Override
				public boolean onMenuItemClick(MenuItem arg0) {
					nAyudasDisponibles--;
					menuItem50.setEnabled(false);
					//bUsado50=true;
					status50=actual;
					if(nAyudasDisponibles>0){
						aplicarComodin50();
					}else{ 
						Toast.makeText(getApplicationContext(), R.string.NoHelp, Toast.LENGTH_LONG).show();
					}
						return true;
				}
			};
			OnMenuItemClickListener handlermenuItemAudience = new OnMenuItemClickListener() {
				@Override
				public boolean onMenuItemClick(MenuItem arg0) {
					nAyudasDisponibles--;
					statusAudience=actual;
					menuItemAudience.setEnabled(false);
					if(nAyudasDisponibles>0){
						aplicarComodinAudience();
					}else{
						Toast.makeText(getApplicationContext(), R.string.NoHelp, Toast.LENGTH_LONG).show();		        	
					}
					return true;
				}
			};
			OnMenuItemClickListener handlermenuItemEnd = new OnMenuItemClickListener() {
				@Override
				public boolean onMenuItemClick(MenuItem arg0) {
					fin_partida(true);
					return true;
				}
			};
			@Override
			public boolean onKeyDown(int keyCode, KeyEvent event) {
			    if (keyCode == KeyEvent.KEYCODE_BACK) {
			    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
			    	builder.setMessage(R.string.titleWarningExit)
			    	        .setTitle(R.string.warning)
			    	        .setCancelable(false)
			    	        
			    	        .setNegativeButton(R.string.backAndNotSave,
			    	                new DialogInterface.OnClickListener() {
			    	                    public void onClick(DialogInterface dialog, int id) {
			    	                    	 actual=1;
			    	                    	 borrarSharedPrefsVar();
			    	                    	 finish();
			    	                       
			    	                    }
			    	                })
			    	        .setNeutralButton(R.string.backAndContinue,
			    	                new DialogInterface.OnClickListener() {
			    	                    public void onClick(DialogInterface dialog, int id) {
			    	                       
			    	                    }
			    	                })	    	                
			    	        .setPositiveButton(R.string.backAndPause,
			    	                new DialogInterface.OnClickListener() {
			    	                    public void onClick(DialogInterface dialog, int id) {
			    	                    	saveData();
			    	                    	finish();
			    	                    }
			    	                });
			    	AlertDialog alert = builder.create();
			    	alert.show();
			        return true;
			    }
			    return super.onKeyDown(keyCode, event);
			}
		
			private void parsear_preguntas(XmlPullParser parser){
				/**
				 * Parsea el XML de una pregunta devolviendo todos sus atributos.
				 */
				String question=parser.getAttributeValue(null, "text");
				String answer1=parser.getAttributeValue(null, "answer1");
				String answer2=parser.getAttributeValue(null, "answer2");
				String answer3=parser.getAttributeValue(null, "answer3");
				String answer4=parser.getAttributeValue(null, "answer4");
				String audience=parser.getAttributeValue(null, "audience");audiencia=Integer.valueOf(audience);	
				String fif1=parser.getAttributeValue(null, "fifty1");fifty1=Integer.valueOf(fif1);
				String fif2=parser.getAttributeValue(null, "fifty2");fifty2=Integer.valueOf(fif2);
				String correc=parser.getAttributeValue(null, "right");correcta=Integer.valueOf(correc);
				String respLlamada=parser.getAttributeValue(null, "phone");telefono=Integer.valueOf(respLlamada);
				String NumPregunta=parser.getAttributeValue(null, "number");actual=Integer.valueOf(NumPregunta);
				pregunta.setText( question);		
				opcionA.setText(answer1);
				opcionB.setText(answer2);
				opcionC.setText(answer3);
				opcionD.setText(answer4);
				
			}
			private void lectura_pregunta() throws XmlPullParserException, IOException{
				/**
				 * Lee la siguiente pregunta del fichero y dejando todos los elementos preparados
				 */
				InputStream inputStream;
				String idioma = Locale.getDefault().getDisplayLanguage();
				
				if(idioma == "EN"){
					inputStream = this.getResources().openRawResource(R.raw.questions0001);
				}
				else{
					inputStream = this.getResources().openRawResource(R.raw.questions0001_es);
				}
				XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
				parser.setInput(inputStream, null);
				 int eventType = XmlPullParser.START_DOCUMENT;
				//Posiciona en la 1º pregunta
				 int i=1;
				 while(i<=actual){
					i++;
					eventType = parser.next();
					eventType = parser.next();
					eventType = parser.next();
				}
				if (eventType == XmlPullParser.START_TAG) {
					parsear_preguntas(parser);
					//Pasa a la siguiente pregunta;
					eventType = parser.next();
					eventType = parser.next();
					eventType = parser.next();
				}
				inputStream.close();
				reinicio_fondos();
			}	
			private void reinicio_fondos(){
				/**
				 * Reinicia todos los elementos gráficos a su estado original.
				 */
				opcionA.setBackgroundResource(R.drawable.button_opcion);
				opcionB.setBackgroundResource(R.drawable.button_opcion);
				opcionC.setBackgroundResource(R.drawable.button_opcion);
				opcionD.setBackgroundResource(R.drawable.button_opcion);
				opcionA.setVisibility(Button.VISIBLE);opcionA.setClickable(true);
				opcionB.setVisibility(Button.VISIBLE);opcionB.setClickable(true);
				opcionC.setVisibility(Button.VISIBLE);opcionC.setClickable(true);
				opcionD.setVisibility(Button.VISIBLE);opcionD.setClickable(true);
				header.setText(getResources().getString(R.string.titulo_question)+actual+"  ");
				dinero.setText(puntuacion[actual]+getResources().getString(R.string.local_currency));
			}
			private void restoreData() {
				SharedPreferences preferences =
				getSharedPreferences("var", Context.MODE_PRIVATE);
				actual=preferences.getInt("actual",1);
				statusTelefono = preferences.getInt("statusTelefono", 0);
				statusAudience = preferences.getInt("statusAudience", 0);
				status50 = preferences.getInt("status50", 0);
			}
		
		
			private void saveData()
			{
				SharedPreferences preferences =
				getSharedPreferences("var", Context.MODE_PRIVATE);
				Editor editor = preferences.edit();
				editor.putInt("actual",actual);
				editor.putInt("statusTelefono", statusTelefono);
				editor.putInt("statusAudience", statusAudience);
				editor.putInt("status50", status50);
				editor.commit();
			}
			
			private void aplicarComodin50()
			{
				switch(fifty1){
				case 1:opcionA.setVisibility(Button.INVISIBLE);opcionA.setClickable(false);break;
				case 2:opcionB.setVisibility(Button.INVISIBLE);opcionB.setClickable(false);break;
				case 3:opcionC.setVisibility(Button.INVISIBLE);opcionC.setClickable(false);break;
				case 4:opcionD.setVisibility(Button.INVISIBLE);opcionD.setClickable(false);break;
				}
				switch(fifty2){
				case 1:opcionA.setVisibility(Button.INVISIBLE);opcionA.setClickable(false);break;
				case 2:opcionB.setVisibility(Button.INVISIBLE);opcionB.setClickable(false);break;
				case 3:opcionC.setVisibility(Button.INVISIBLE);opcionC.setClickable(false);break;
				case 4:opcionD.setVisibility(Button.INVISIBLE);opcionD.setClickable(false);break;
				}
			}
			
			private void aplicarComodinTelefono()
			{
				switch(telefono){
				case 1:opcionA.setBackgroundResource(R.drawable.button_opcion_selected);break;
				case 2:opcionB.setBackgroundResource(R.drawable.button_opcion_selected);break;
				case 3:opcionC.setBackgroundResource(R.drawable.button_opcion_selected);break;
				case 4:opcionD.setBackgroundResource(R.drawable.button_opcion_selected);break;
				}
			}
			
			private void aplicarComodinAudience()
			{
				switch(audiencia){
				case 1:opcionA.setBackgroundResource(R.drawable.button_opcion_selected);break;
				case 2:opcionB.setBackgroundResource(R.drawable.button_opcion_selected);break;
				case 3:opcionC.setBackgroundResource(R.drawable.button_opcion_selected);break;
				case 4:opcionD.setBackgroundResource(R.drawable.button_opcion_selected);break;
				}
			}
			
			private void mostrarRespuestaIncorrecta(int eleccion, int correcta)
			{
				switch(eleccion)
				{
				case 1: opcionA.setBackgroundResource(R.drawable.button_opcion_wrong); break;
				case 2: opcionB.setBackgroundResource(R.drawable.button_opcion_wrong); break;
				case 3: opcionC.setBackgroundResource(R.drawable.button_opcion_wrong); break;
				case 4: opcionD.setBackgroundResource(R.drawable.button_opcion_wrong); break;
				default:break;
				}
				
				switch(correcta)
				{
				case 1: opcionA.setBackgroundResource(R.drawable.button_opcion_correct); break;
				case 2: opcionB.setBackgroundResource(R.drawable.button_opcion_correct); break;
				case 3: opcionC.setBackgroundResource(R.drawable.button_opcion_correct); break;
				case 4: opcionD.setBackgroundResource(R.drawable.button_opcion_correct); break;
				default:break;
				}
			}
			
			Runnable checkOpcionA= new Runnable() {
			    @Override
			    public void run() {
			    	if(correcta == 1)
			    	{
			    		opcionA.setBackgroundResource(R.drawable.button_opcion_correct);
			    		if(actual == 15)
			    		{
			    			actual++;
			    			opcionA.postDelayed(RunnableFinPartida, 1000);
			    		}
			    		else
			    		{
			    			opcionA.postDelayed(SiguientePregunta, 500);
			    		}
			    	}else{
			    		mostrarRespuestaIncorrecta(1, correcta);
			    		opcionA.postDelayed(RunnableFinPartida, 1000);
			    	}
			    }
			};
			
			Runnable checkOpcionB= new Runnable() {
			    @Override
			    public void run() {
			    	if(correcta == 2)
			    	{
			    		opcionB.setBackgroundResource(R.drawable.button_opcion_correct);
			    		if(actual == 15)
			    		{
			    			actual++;
			    			opcionB.postDelayed(RunnableFinPartida, 1000);
			    		}
			    		else
			    		{
			    			opcionB.postDelayed(SiguientePregunta, 500);
			    		}
			    	}
			    	else
			    	{
			    		mostrarRespuestaIncorrecta(2, correcta);
			    		opcionB.postDelayed(RunnableFinPartida, 1000);
			    	}
			    }
			};
			
			Runnable checkOpcionC= new Runnable() {
			    @Override
			    public void run() {
			    	if(correcta == 3)
			    	{
			    		opcionC.setBackgroundResource(R.drawable.button_opcion_correct);
			    		if(actual == 15)
			    		{
			    			actual++;
			    			opcionC.postDelayed(RunnableFinPartida, 1000);
			    		}
			    		else
			    		{
			    			opcionC.postDelayed(SiguientePregunta, 500);
			    		}
			    	}
			    	else
			    	{
			    		mostrarRespuestaIncorrecta(3, correcta);
			    		opcionC.postDelayed(RunnableFinPartida, 1000);
			    	}
			    }
			};
			
			Runnable checkOpcionD= new Runnable() {
			    @Override
			    public void run() {
			    	if(correcta == 4)
			    	{
			    		opcionD.setBackgroundResource(R.drawable.button_opcion_correct);
			    		if(actual == 15)
			    		{
			    			actual++;
			    			opcionD.postDelayed(RunnableFinPartida, 1000);
			    		}
			    		else
			    		{
			    			opcionD.postDelayed(SiguientePregunta, 500);
			    		}
			    	}
			    	else
			    	{
			    		mostrarRespuestaIncorrecta(4, correcta);
			    		opcionD.postDelayed(RunnableFinPartida, 1000);
			    	}
			    }
			};
			
			Runnable SiguientePregunta= new Runnable()
			{
				@Override
				public void run(){
					try {
						actual++;
						lectura_pregunta();
					} catch (XmlPullParserException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			};
			
			Runnable RunnableFinPartida= new Runnable() {
			    @Override
			    public void run() {
			    	fin_partida(false);
			    }
			};
			private class updateScore extends AsyncTask<String, Void, Boolean>{
				protected Boolean doInBackground(String... params) {
		        	HttpClient client = new DefaultHttpClient();
		        	HttpPut request = new HttpPut(getResources().getString(R.string.url_highScores));
		        	
		        	
		
		        	    		
		        	String nombre=loadPreferencesName();
		        	List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		        	pairs.add(new BasicNameValuePair("name", nombre));
		        	pairs.add(new BasicNameValuePair("score", params[0]));
		        	
		        	
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
		
		
			};
			private String loadPreferencesName() {
    	SharedPreferences preferences =
    			getSharedPreferences("Settings", Context.MODE_PRIVATE);
    	String nombre=preferences.getString("nombre", "");
    	return nombre;
	};

}
