package com.adm.whowantstobeamillionaire;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyHelper extends SQLiteOpenHelper {
	/**
	 * Clase para gestionar la base de datos de la aplicación.
	 * 
	 */
	//Sentencia SQL para crear la tabla de Usuarios
    String sqlCreate = "CREATE TABLE if not exists  score (id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT not null,score INTEGER not null)";
    
		public MyHelper(Context contexto) {
		super(contexto, "millonarios", null, 1);
	}
	 @Override
	  	public void onUpgrade(SQLiteDatabase db, int versionAnterior,
	                          int versionNueva) {
	        //NOTA: Por simplicidad del ejemplo aquí utilizamos directamente
	        // la opción de eliminar la tabla anterior y crearla de nuevo
	        // vacía con el nuevo formato.
	        // Sin embargo lo normal será que haya que migrar datos de la
	        // tabla antigua a la nueva, por lo que este método debería
	        // ser más elaborado.
	 
	        //Se elimina la versión anterior de la tabla
	        db.execSQL("DROP TABLE IF EXISTS score");
	 
	        //Se crea la nueva versión de la tabla
	        db.execSQL(sqlCreate);
	        db.close();
	    }
	@Override
     	public void onCreate(SQLiteDatabase db) {
        //Se ejecuta la sentencia SQL de creación de la tabla
        db.execSQL(sqlCreate);
    }
	 	public void insertarPuntuacion(String name, int score){
	  SQLiteDatabase db = getWritableDatabase();
	    if(db != null){ 
	     ContentValues cv=new ContentValues();
	     cv.put("name", name);
	     cv.put("score", String.valueOf(score));
	     db.insert("score", null, cv);
	     db.close();
	    }
	 }
	 	public  ArrayList<HashMap<String, String>> leerPuntuaciones(Context context){
		 /**
		  * Lee las puntuaciones de la base de datos y las devuelve en un ArrayList
		  * @argument Context contexto
		  * @return puntuaciones
		  * 
		  */
		 //Creamos el ArrayList que contendra todos los pares-valor de la base de datos
		ArrayList<HashMap<String, String>> listaPuntuaciones = new ArrayList<HashMap<String, String>>();
		String nombre;
		int aux;
		String puntua;
		SQLiteDatabase db = getReadableDatabase(); 
		//Seleccionamos los datos que nos interesan:Nombre y puntuacion
		Cursor c = db.rawQuery("select name,score from score ORDER BY score  DESC ", null);    	
		
		if(c.moveToFirst()){ 	
			if(c!=null){
			//Recorremos el cursor hasta que no haya más registros
				do {
					HashMap<String, String> map = new HashMap<String, String>();
					nombre = c.getString(0);
					aux = c.getInt(1);
					puntua=String.valueOf(aux);
					map.put("Nombre", nombre);
					map.put("score", puntua);
					//los vamos insertando en el arraylist
					listaPuntuaciones.add(map);
				} while(c.moveToNext());
			}
		}
		db.close();
		c.close();
		return listaPuntuaciones;
	}
	 	public void borrarPuntuaciones(Context context){
		/**
		 * Borra las puntuaciones de la base de datos
		 * @arguments context Contexto
		 */
		//Borramos la tabla y la volvemos a crear.
		SQLiteDatabase db = getWritableDatabase(); 
		 db.execSQL("DROP TABLE IF EXISTS score");
		 db.execSQL(sqlCreate);
		 db.close();
	}
	 	public void crearBD(Context context){
    	
    	SQLiteDatabase db = getWritableDatabase(); 
   	 	db.execSQL(sqlCreate);
   	 	db.close();
    }
    

}