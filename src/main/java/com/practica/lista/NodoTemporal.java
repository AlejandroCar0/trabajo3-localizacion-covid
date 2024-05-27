package com.practica.lista;

import com.practica.genericas.FechaHora;

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
}
