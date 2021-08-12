package com.example.myapplication.Utils;

import com.example.myapplication.Model.Documento;
import com.example.myapplication.Model.Prospectos;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ProspectosSerivce {

    @GET("listar")
    Call<List<Prospectos>> getProspectos();

    @POST("agregar")
    Call<String>addProspecto(@Body Prospectos prospecto);

    @POST("actualizar/{id}")
    Call<Prospectos>updateProspecto(@Body Prospectos prospecto,@Path("id") int id);

    @POST("eliminar/{id}")
    Call<String>deleteProspecto(@Path("id")int id);

    @GET("listarDocs/{id}")
    Call<List<Documento>>getDocs(@Path("id") int id);

    @POST("setDocumentos")
    Call<String>setDocumento(@Body Documento documento);

    @POST("eliminarDoc/{id}")
    Call<String>deleteDoc(@Path("id")int id);

    @GET("getMaxID")
    Call<String>getMax();

}
