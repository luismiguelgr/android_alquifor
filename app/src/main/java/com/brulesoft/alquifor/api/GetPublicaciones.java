package com.brulesoft.alquifor.api;

import com.brulesoft.alquifor.models.Publicacion;
import com.brulesoft.alquifor.models.Usuario;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GetPublicaciones {

    @GET("/api/publicaciones")
    Call<List<Publicacion>> getAllPublicaciones();

    @GET("/api/publicacion/{id}")
    Call<Publicacion> getPublicacion(@Path("id") int id);
}
