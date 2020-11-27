package com.brulesoft.alquifor.api;

import com.brulesoft.alquifor.models.Publicacion;
import com.brulesoft.alquifor.models.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MethodUsuarios {

        @GET("/api/usuarios")
        Call<List<Usuario>> getAllUsers();

        @GET("/api/usuario/{id}")
        Call<Usuario> getUsuario(@Path("id") int id);

        @POST("/api/anadir-usuario")
        Call<Usuario> addUsuario(@Body Usuario usuario);
}
