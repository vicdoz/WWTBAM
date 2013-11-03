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
		String nombre,puntua;
		int aux;
		ArrayList<HashMap<String, String>> listaPuntuacionesAmigos = new ArrayList<HashMap<String, String>>();
		// Obtenemos un Iterador y recorremos la lista.
		Iterator<HighScore> iter = scores.iterator();
		while (iter.hasNext()){
		HashMap<String, String> map = new HashMap<String, String>();
		nombre = iter.next().getName();
		aux = iter.next().getScoring();
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
