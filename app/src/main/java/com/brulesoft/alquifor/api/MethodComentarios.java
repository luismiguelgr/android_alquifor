package com.brulesoft.alquifor.api;

import com.brulesoft.alquifor.models.Comentario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MethodComentarios {

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("/api/comentarios/{id}")
    Call<List<Comentario>> getComentariosUsuario(@Header("Authorization") String token, @Path("id") int id);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @DELETE("/api/comentario/{id}")
    Call<Comentario> deleteComentario(@Header("Authorization") String token, @Path("id") int id);
}
