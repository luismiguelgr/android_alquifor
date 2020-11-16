package com.brulesoft.alquifor.api;

import com.brulesoft.alquifor.models.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetUsers {

        @GET("/api/usuarios")
        Call<List<Usuario>> getAllUsers();

        @GET("/api/usuario/{id}")
        Call<Usuario> getUsuario(@Path("id") int id);
}
