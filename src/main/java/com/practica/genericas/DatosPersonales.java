package com.practica.genericas;

public class DatosPersonales {
    private String nombre;
    private String apellidos;
    private String documento;
    private FechaHora fechaNacimiento;
    public DatosPersonales(String documento,String nombre,String apellidos,FechaHora fechaNacimiento) {
        this.nombre=nombre;
        this.apellidos=apellidos;
        this.fechaNacimiento=fechaNacimiento;
        this.documento=documento;
    }
    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getDocumento() {
        return documento;
    }
    public FechaHora getFechaNacimiento() {
        return this.fechaNacimiento;
    }
}
