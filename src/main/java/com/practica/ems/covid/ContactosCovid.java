package com.practica.ems.covid;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.practica.excecption.EmsDuplicateLocationException;
import com.practica.excecption.EmsDuplicatePersonException;
import com.practica.excecption.EmsInvalidNumberOfDataException;
import com.practica.excecption.EmsInvalidTypeException;
import com.practica.excecption.EmsLocalizationNotFoundException;
import com.practica.excecption.EmsPersonNotFoundException;
import com.practica.genericas.Constantes;
import com.practica.genericas.Coordenada;
import com.practica.genericas.Persona;
import com.practica.genericas.DatosPersonales;
import com.practica.genericas.FechaHora;
import com.practica.genericas.DatosUbicacion;
import com.practica.genericas.PosicionPersona;
import com.practica.lista.ListaContactos;

public class ContactosCovid {
	private Poblacion poblacion;
	private Localizacion localizacion;
	private ListaContactos listaContactos;

	public ContactosCovid() {
		this.poblacion = new Poblacion();
		this.localizacion = new Localizacion();
		this.listaContactos = new ListaContactos();
	}

	public Poblacion getPoblacion() {
		return poblacion;
	}

	public void setPoblacion(Poblacion poblacion) {
		this.poblacion = poblacion;
	}

	public Localizacion getLocalizacion() {
		return localizacion;
	}

	public void setLocalizacion(Localizacion localizacion) {
		this.localizacion = localizacion;
	}
	
	

	public ListaContactos getListaContactos() {
		return listaContactos;
	}

	public void setListaContactos(ListaContactos listaContactos) {
		this.listaContactos = listaContactos;
	}

	public void loadData(String data, boolean reset) throws EmsInvalidTypeException, EmsInvalidNumberOfDataException,
			EmsDuplicatePersonException, EmsDuplicateLocationException {
		// borro información anterior
		if (reset) {
			this.poblacion = new Poblacion();
			this.localizacion = new Localizacion();
			this.listaContactos = new ListaContactos();
		}
		String datas[] = dividirEntrada(data);
		insertarDatos(datas);
	}

	public void loadDataFile(String fichero, boolean reset) {
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;
		String datas[] = null, data = null;
		loadDataFile(fichero, reset, archivo, fr, br, datas, data);
		
	}

	@SuppressWarnings("resource")
	public void loadDataFile(String fichero, boolean reset, File archivo, FileReader fr, BufferedReader br, String datas[], String data ) {
		try {
			// Apertura del fichero y creacion de BufferedReader para poder
			// hacer una lectura comoda (disponer del metodo readLine()).
			archivo = new File(fichero);
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);
			if (reset) {
				this.poblacion = new Poblacion();
				this.localizacion = new Localizacion();
				this.listaContactos = new ListaContactos();
			} 
			/**
			 * Lectura del fichero	línea a línea. Compruebo que cada línea 
			 * tiene el tipo PERSONA o LOCALIZACION y cargo la línea de datos en la 
			 * lista correspondiente. Sino viene ninguno de esos tipos lanzo una excepción
			 */
			while ((data = br.readLine()) != null) {
				datas = dividirEntrada(data.trim());
				insertarDatos(datas);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// En el finally cerramos el fichero, para asegurarnos
			// que se cierra tanto si todo va bien como si salta
			// una excepcion.
			try {
				if (null != fr) {
					fr.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	public int findPersona(String documento) throws EmsPersonNotFoundException {
		int pos;
		try {
			pos = this.poblacion.findPersona(documento);
			return pos;
		} catch (EmsPersonNotFoundException e) {
			throw new EmsPersonNotFoundException();
		}
	}

	public int findLocalizacion(String documento, String fecha, String hora) throws EmsLocalizationNotFoundException {

		int pos;
		try {
			pos = localizacion.findLocalizacion(documento, fecha, hora);
			return pos;
		} catch (EmsLocalizationNotFoundException e) {
			throw new EmsLocalizationNotFoundException();
		}
	}

	public List<PosicionPersona> localizacionPersona(String documento) throws EmsPersonNotFoundException {
		int cont = 0;
		List<PosicionPersona> lista = new ArrayList<PosicionPersona>();
		Iterator<PosicionPersona> it = this.localizacion.getLista().iterator();
		while (it.hasNext()) {
			PosicionPersona pp = it.next();
			if (pp.getDocumento().equals(documento)) {
				cont++;
				lista.add(pp);
			}
		}
		if (cont == 0)
			throw new EmsPersonNotFoundException();
		else
			return lista;
	}

	public boolean delPersona(String documento) throws EmsPersonNotFoundException {
		int cont = 0, pos = -1;
		Iterator<Persona> it = this.poblacion.getLista().iterator();
		while (it.hasNext()) {
			Persona persona = it.next();
			if (persona.getDocumento().equals(documento)) {
				pos = cont;
			}
			cont++;
		}
		if (pos == -1) {
			throw new EmsPersonNotFoundException();
		}
		this.poblacion.getLista().remove(pos);
		return false;
	}

	private String[] dividirEntrada(String input) {
		String cadenas[] = input.split("\\n");
		return cadenas;
	}

	private String[] dividirLineaData(String data) {
		String cadenas[] = data.split("\\;");
		return cadenas;
	}

	private Persona crearPersona(String[] data) {
		DatosPersonales personalData = crearDatosPersonales(data);
		DatosUbicacion ubicationData = crearDatosUbicacion(data);
		String email= data[4];
		return new Persona(email,personalData,ubicationData);
	}
	private DatosPersonales crearDatosPersonales(String[]data) {
		String documento = data[1];
		String nombre = data[2];
		String apellidos = data[3];
		FechaHora fechaNacimiento = Utilidades.parsearFecha(data[7]);
		return new DatosPersonales(documento,nombre,apellidos,fechaNacimiento);
	}
	private DatosUbicacion crearDatosUbicacion(String[]data) {
		String direccion = data[5];
		String cp = data[6];
		return new DatosUbicacion(direccion,cp);
	}

	private PosicionPersona crearPosicionPersona(String[] data) {
		PosicionPersona posicionPersona = new PosicionPersona();
		String fecha = null, hora;
		float latitud = 0, longitud;
		for (int i = 1; i < Constantes.MAX_DATOS_LOCALIZACION; i++) {
			String s = data[i];
			switch (i) {
			case 1:
				posicionPersona.setDocumento(s);
				break;
			case 2:
				fecha = data[i];
				break;
			case 3:
				hora = data[i];
				posicionPersona.setFechaPosicion(Utilidades.parsearFecha(fecha, hora));
				break;
			case 4:
				latitud = Float.parseFloat(s);
				break;
			case 5:
				longitud = Float.parseFloat(s);
				posicionPersona.setCoordenada(new Coordenada(latitud, longitud));
				break;
			}
		}
		return posicionPersona;
	}

	private void insertarDatos(String[] datas) throws EmsDuplicateLocationException, EmsInvalidTypeException, EmsInvalidNumberOfDataException, EmsDuplicatePersonException {
		for (String linea : datas) {
			String datos[] = this.dividirLineaData(linea);
			if (!esPersona(datos[0]) && !esLocalizacion(datos[0])) {
				throw new EmsInvalidTypeException();
			}
			if (esPersona(datos[0])) {
				insertarDatosPersona(datos);
			}
			if (esLocalizacion(datos[0])) {
				insertarDatosLocalizacion(datos);
			}
		}
	}
	private boolean esPersona(String nombre) {
		return nombre.equals("PERSONA");
	}
	private boolean esLocalizacion(String nombre) {
		return nombre.equals("LOCALIZACION");
	}
	private void insertarDatosLocalizacion(String[] datos) throws EmsInvalidNumberOfDataException, EmsDuplicateLocationException {
		if (datos.length != Constantes.MAX_DATOS_LOCALIZACION) {
			throw new EmsInvalidNumberOfDataException("El número de datos para LOCALIZACION es menor de 6");
		}
		PosicionPersona pp = this.crearPosicionPersona(datos);
		this.localizacion.addLocalizacion(pp);
		this.listaContactos.insertarNodoTemporal(pp);
	}
	private void insertarDatosPersona(String[] datos) throws EmsInvalidNumberOfDataException, EmsDuplicatePersonException {
		if (datos.length != Constantes.MAX_DATOS_PERSONA) {
			throw new EmsInvalidNumberOfDataException("El número de datos para PERSONA es menor de 8");
		}
		this.poblacion.addPersona(this.crearPersona(datos));
	}

}
