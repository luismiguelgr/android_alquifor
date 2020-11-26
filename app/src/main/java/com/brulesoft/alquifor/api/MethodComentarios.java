package com.brulesoft.alquifor.api;

import com.brulesoft.alquifor.models.Comentario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MethodComentarios {

    @POST("/api/comentarios/{id}")
    Call<List<Comentario>> getComentariosUsuario(@Path("id") int id);

    @DELETE("/api/comentario/{id}")
    Call<Comentario> deleteComentario(@Path("id") int id);
}
