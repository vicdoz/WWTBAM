package com.adm.whowantstobeamillionaire;

import java.util.HashMap;
import java.util.Vector;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Database {
	  private MyHelper dbHelper;
      private SQLiteDatabase db;


      public void vaciarDatabase() {
          // TODO Auto-generated method stub
           db=dbHelper.getWritableDatabase();
           db.execSQL("DROP TABLE IF EXISTS score");
           db.close();
  }
      public void insertarPuntuacion(String name, int score){
    	  db = dbHelper.getWritableDatabase();  
          ContentValues cv=new ContentValues();
          cv.put("name", name);
          cv.put("score", String.valueOf(score));
          db.insert("score", null, cv);//REVISAR NULL
          db.close();
      }
      public  HashMap<String, String> leerPuntuaciones(Context context){
    	  String[] campos = new String[] {"name", "score"};
    	  HashMap<String, String> map = new HashMap<String, String>();
    	 
	    	  db = dbHelper.getReadableDatabase();  
	    	  Cursor c = db.rawQuery("SELECT id,nombre, score FROM score", null);	    	 
	    		if (c.moveToFirst()) {
	    			//Recorremos el cursor hasta que no haya más registros
	    			do {
	    				int id=c.getInt(0);
	    				System.out.println(id);
		    			String nombre = c.getString(1);System.out.println(nombre);
		    			int aux = c.getInt(2);System.out.println(aux);
		    			String p=String.valueOf(aux);
		    			map.put(nombre, p);
	    			} while(c.moveToNext());
	    		}
	    	db.close();
    		return map;
    	}
	public void crearDatabase(Context context) {
		dbHelper=new MyHelper(context, "puntuaciones", null, 1);
	}
      


}
