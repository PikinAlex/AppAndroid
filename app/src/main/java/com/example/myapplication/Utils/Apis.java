package com.example.myapplication.Utils;

public class Apis {

    public static final String URL_001="http://192.168.1.66:8080/prospectos/";

    public static  ProspectosSerivce getPropectosSerivice(){
        return Cliente.getCliente(URL_001).create(ProspectosSerivce.class);
    }
}
