package com.adm.whowantstobeamillionaire;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class HighScoreList {

	private List<HighScore> scores;

	public List<HighScore> getScores() {
		return scores;
	}
	public ArrayList<HashMap<String, String>> getScoresAsArrayList() {
		/**
		 * Devuelve un arrayList con las puntuaciones de los amigos excepto la propia.
		 */
		ArrayList<HashMap<String, String>> listaPuntuacionesAmigos = new ArrayList<HashMap<String, String>>();
		// Obtenemos un Iterador y recorremos la lista.
		Iterator<HighScore> iter = scores.iterator();
		while (iter.hasNext()){
			HighScore h=iter.next();
			String nombre,puntua;
			int aux;
			HashMap<String, String> map = new HashMap<String, String>();
			nombre=h.getName();
			aux=h.getScoring();
			puntua=String.valueOf(aux);
			map.put("Nombre", nombre);
			map.put("score", puntua);
			//los vamos insertando en el arraylist
			listaPuntuacionesAmigos.add(map);
			
		}
		return listaPuntuacionesAmigos;
		
	}

	public void setScores(List<HighScore> scores) {
		this.scores = scores;
	}
	
	
}
