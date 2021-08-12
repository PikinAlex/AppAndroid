package com.example.myapplication.Model;

public class Documento {

    private int idDocumento;
    private String nombre;
    private int id_prospecto;
    private String documento;

    public Documento() {
    }

    public Documento(int idDocumento, String nombre, int id_prospecto, String documento) {
        this.idDocumento = idDocumento;
        this.nombre = nombre;
        this.id_prospecto = id_prospecto;
        this.documento = documento;
    }

    public int getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(int idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId_prospecto() {
        return id_prospecto;
    }

    public void setId_prospecto(int id_prospecto) {
        this.id_prospecto = id_prospecto;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }
}
