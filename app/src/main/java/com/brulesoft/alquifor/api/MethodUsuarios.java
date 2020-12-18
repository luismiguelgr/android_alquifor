package com.brulesoft.alquifor.api;

import com.brulesoft.alquifor.data.LoginDataSource;
import com.brulesoft.alquifor.data.Result;
import com.brulesoft.alquifor.models.LoginCredenciales;
import com.brulesoft.alquifor.models.Publicacion;
import com.brulesoft.alquifor.models.RespuestaLogin;
import com.brulesoft.alquifor.models.Usuario;

import java.util.List;

import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MethodUsuarios {

        @GET("/api/usuarios")
        Call<List<Usuario>> getAllUsers();

        @GET("/api/usuario/{id}")
        Call<Usuario> getUsuario(@Path("id") int id);

        @Headers({ "Content-Type: application/json;charset=UTF-8"})
        @POST("/api/usuario/{email}")
        Call<Usuario> getUsuarioEmail(@Header("Authorization") String token, @Path("email") String email);

        @POST("/anadir-usuario")
        Call<Usuario> addUsuario(@Body Usuario usuario);

//        @POST("/login_check")
//        Call<LoginCredenciales> loginIn(@Field("username") String username,
//                                      @Field("password") String password);

        @POST("/api/login_check")
        Call<RespuestaLogin> loginIn(@Body LoginCredenciales data);

}
