package com.practica.genericas;


public class Persona {
	private String email;
	private DatosPersonales datosPersonales;
	private DatosUbicacion datosUbicacion;

	public Persona(String email, DatosPersonales datosPersonales, DatosUbicacion datosUbicacion) {
		this.email=email;
		this.datosPersonales=datosPersonales;
		this.datosUbicacion=datosUbicacion;
	}

	public String getNombre() {
		return this.datosPersonales.getNombre();
	}
	public String getApellidos() {
		return this.datosPersonales.getApellidos();
	}


	public String getDocumento() {
		return this.datosPersonales.getDocumento();
	}

	public String getEmail() {
		return email;
	}


	public String getDireccion() {
		return this.datosUbicacion.getDireccion();
	}

	public String getCp() {
		return this.datosUbicacion.getCp();
	}

	public FechaHora getFechaNacimiento() {
		return this.datosPersonales.getFechaNacimiento();
	}

	@Override
	public String toString() {
		FechaHora fecha = getFechaNacimiento();
		String cadena = "";
		// Documento
		cadena += String.format("%s;", getDocumento());
		// Nombre y apellidos
		cadena += String.format("%s,%s;", getApellidos(), getNombre());
		// correo electrónico
		cadena += String.format("%s;", getEmail());
        // Direccion y código postal
		cadena += String.format("%s,%s;", getDireccion(), getCp());
        // Fecha de nacimiento
		cadena+=String.format("%02d/%02d/%04d\n", fecha.getFecha().getDia(), 
        		fecha.getFecha().getMes(), 
        		fecha.getFecha().getAnio());

		return cadena;
	}
}
