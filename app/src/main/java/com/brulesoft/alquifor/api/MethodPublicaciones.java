package com.brulesoft.alquifor.api;

import com.brulesoft.alquifor.models.Comentario;
import com.brulesoft.alquifor.models.Publicacion;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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

    @Multipart
    @POST("/api/anadir-publicacion")
    Call<Publicacion> addPublicacionConImagen(@Part MultipartBody.Part imagen,
                                     @Part("titulo") RequestBody titulo,
                                     @Part("descripcion") RequestBody descripcion,
                                     @Part("id_usuario") RequestBody id_usuario,
                                     @Part("foto") RequestBody foto,
                                     @Field("pros") RequestBody pros,
                                     @Field("contras") RequestBody contras);
}
