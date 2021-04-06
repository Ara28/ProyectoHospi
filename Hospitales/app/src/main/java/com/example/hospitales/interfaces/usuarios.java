package com.example.hospitales.interfaces;

import com.example.hospitales.models.Usuarios;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface usuarios {
    @FormUrlEncoded
    @POST("?controller=Usuarios&action=acceder")
    Call<Usuarios>acceder(@Field("Correo")String Correo, @Field("Contrasenia")String Contrasenia);

    @FormUrlEncoded
    @POST("?controller=Usuarios&action=Create")
    Call<Usuarios>crearUsuario(@Field("Nombre")String Nombre, @Field("Ap_paterno")String Ap_paterno, @Field("Ap_materno")String Ap_materno, @Field("Correo")String Correo, @Field("Contrasenia")String Contrasenia);
}
