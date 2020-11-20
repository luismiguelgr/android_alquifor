package com.brulesoft.alquifor.api;

import com.brulesoft.alquifor.models.Comentario;
import com.brulesoft.alquifor.models.Publicacion;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MethodPublicaciones {

    @GET("/api/publicaciones")
    Call<List<Publicacion>> getAllPublicaciones();

    @GET("/api/publicacion/{id}")
    Call<Publicacion> getPublicacion(@Path("id") int id);

    @GET("/api/publicacion/{id}/comentarios")
    Call<List<Comentario>> getComentariosPublicacion(@Path("id") int id);

    @POST("/api/anadir-publicacion")
    Call<Publicacion> addPublicacion(@Body Publicacion publicacion);
}
