package com.practica.lista;

import com.practica.genericas.Coordenada;
import com.practica.genericas.PosicionPersona;

/**
 * Nodo para la lista de coordenadas. En el guardamos cuántas personas  están
 * en una coordenada  en un momento temporal. 
 * También guardaremos la lista de personas que están en esa coordenada en un 
 * momento en concreto
 */
public class NodoPosicion {
	private Coordenada coordenada;	
	private int numPersonas;
	
	
	public NodoPosicion() {
		super();
	}

	
	
	
	public NodoPosicion(Coordenada coordenada,  int numPersonas) {
		super();
		this.coordenada = coordenada;		
		this.numPersonas = numPersonas;
	}




	public Coordenada getCoordenada() {
		return coordenada;
	}

	public void setCoordenada(Coordenada coordenada) {
		this.coordenada = coordenada;
	}

	public int getNumPersonas() {
		return numPersonas;
	}

	public void setNumPersonas(int numPersonas) {
		this.numPersonas = numPersonas;
	}
	public static NodoPosicion getNodoFromPosicion(PosicionPersona p) {
		return new NodoPosicion(p.getCoordenada(),1);
	}
	public void aumentarPersonas() {
		this.numPersonas ++;
	}
	@Override
	public boolean equals(Object nodo){
		if(nodo == null || nodo.getClass()!=this.getClass())
			return false;
		NodoPosicion nodoPosicion = (NodoPosicion)nodo;
		return nodoPosicion.getCoordenada().equals(this.coordenada);
	}
	
}
