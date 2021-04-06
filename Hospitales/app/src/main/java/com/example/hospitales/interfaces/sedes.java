package com.example.hospitales.interfaces;

import com.example.hospitales.models.Sedes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface sedes {
    @FormUrlEncoded
    @POST("?controller=Sedes&action=Mostrar")
    Call<List<Sedes>>Mostrar(@Field("Nombre")String Nombre);

    @FormUrlEncoded
    @POST("?controller=Sedes&action=buscarSede")
    Call<Sedes>buscarS(@Field("IdSede") int IdSede);
}