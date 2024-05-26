package com.practica.genericas;

public class DatosUbicacion {
    private String direccion;
    private String cp;
    public DatosUbicacion(String direccion, String cp) {
        this.direccion=direccion;
        this.cp=cp;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getCp() {
        return cp;
    }

}
