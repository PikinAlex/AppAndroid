package com.example.myapplication.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Prospectos {
    @SerializedName("id_prospecto")
    @Expose
    private int id_prospecto;

    @SerializedName("nombre")
    @Expose
    private String nombre;

    @SerializedName("apellido_p")
    @Expose
    private String apellido_p;

    @SerializedName("apellido_m")
    @Expose
    private String apellido_m;

    @SerializedName("calle")
    @Expose
    private String calle;

    @SerializedName("numero")
    @Expose
    private String numero;

    @SerializedName("colonia")
    @Expose
    private String colonia;

    @SerializedName("codigo_postal")
    @Expose
    private String codigo_postal;

    @SerializedName("telefono")
    @Expose
    private String telefono;

    @SerializedName("rfc")
    @Expose
    private String rfc;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("observaciones")
    @Expose
    private String observaciones;

    public  Prospectos(){
    }

    public Prospectos(int id_prospecto, String nombre, String apellido_p, String apellido_m, String calle, String numero, String colonia, String codigo_postal, String telefono, String rfc, String status, String observaciones) {
        this.id_prospecto = id_prospecto;
        this.nombre = nombre;
        this.apellido_p = apellido_p;
        this.apellido_m = apellido_m;
        this.calle = calle;
        this.numero = numero;
        this.colonia = colonia;
        this.codigo_postal = codigo_postal;
        this.telefono = telefono;
        this.rfc = rfc;
        this.status = status;
        this.observaciones = observaciones;
    }

    public int getId_prospecto() {
        return id_prospecto;
    }

    public void setId_prospecto(int id_prospecto) {
        this.id_prospecto = id_prospecto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido_p() {
        return apellido_p;
    }

    public void setApellido_p(String apellido_p) {
        this.apellido_p = apellido_p;
    }

    public String getApellido_m() {
        return apellido_m;
    }

    public void setApellido_m(String apellido_m) {
        this.apellido_m = apellido_m;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCodigo_postal() {
        return codigo_postal;
    }

    public void setCodigo_postal(String codigo_postal) {
        this.codigo_postal = codigo_postal;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
