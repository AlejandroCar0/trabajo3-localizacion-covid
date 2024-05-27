package com.practica.lista;

import com.practica.genericas.FechaHora;
import com.practica.genericas.PosicionPersona;

import java.util.ArrayList;
import java.util.List;


/**
 * Nodo para guardar un instante de tiempo. Además guardamos una lista con las coordeandas
 * y las personas (solo número) que en ese instante están en una coordeanda en concreto  
 *
 */
public class NodoTemporal {
	private List<NodoPosicion> listaCoordenadas;
	private FechaHora fecha;
	
	
	public NodoTemporal() {
		super();
		listaCoordenadas= new ArrayList<>();
	}
	public List<NodoPosicion> getListaCoordenadas() {
		return listaCoordenadas;
	}
	public void setListaCoordenadas(List<NodoPosicion> listaCoordenadas) {
		this.listaCoordenadas = listaCoordenadas;
	}
	public FechaHora getFecha() {
		return fecha;
	}
	public void setFecha(FechaHora fecha) {
		this.fecha = fecha;
	}

	public static NodoTemporal getNodoFromPosicion(PosicionPersona p) {
		NodoTemporal resultado = new NodoTemporal();
		resultado.fecha = p.getFechaPosicion();
		NodoPosicion nodoPosicion = NodoPosicion.getNodoFromPosicion(p);
		resultado.insertarNodo(nodoPosicion);
		return resultado;
	}
	private void insertarNodo(NodoPosicion nodo) {
		int posicionNodo = listaCoordenadas.indexOf(nodo);
		if(posicionNodo>=0) {
			aumentarPersonas(listaCoordenadas.get(posicionNodo));
		}
		listaCoordenadas.add(nodo);
	}
	private void aumentarPersonas(NodoPosicion nodo){
		nodo.aumentarPersonas();
	}
}
