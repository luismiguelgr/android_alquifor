package com.brulesoft.alquifor.api;

import com.brulesoft.alquifor.models.Publicacion;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AddPublicacion {
    @POST("/api/anadir-publicacion")
    Call<Publicacion> addPublicacion(@Body Publicacion publicacion);
}
