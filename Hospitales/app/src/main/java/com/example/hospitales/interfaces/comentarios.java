package com.example.hospitales.interfaces;

import com.example.hospitales.models.Comentarios;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface comentarios {

    @FormUrlEncoded
    @POST("?controller=Comentarios&action=Mostrar")
    Call<Comentarios>mostrarComentarios(@Field("Id_sede") int Id_Sede);

    @FormUrlEncoded
    @POST("?controller=Comentarios&action=Create")
    Call<Comentarios>comentar(@Field("Comentario") String Comentario, @Field("Calificacion") int Calificacion, @Field("Id_sede") int Id_sede, @Field("Id_usuario") int Id_usuario);
}
