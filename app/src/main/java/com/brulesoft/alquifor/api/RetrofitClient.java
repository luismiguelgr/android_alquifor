package com.brulesoft.alquifor.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit;
//    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";
//    private static final String BASE_URL = "http://192.168.56.101/";
//    private static final String BASE_URL = "https://127.0.0.1:8000/";
//    private static final String BASE_URL = "http://192.168.1.35:8000/";
//    private static final String BASE_URL = "http://10.0.2.2/";
//    private static final String BASE_URL = "https://10.0.2.2:8080/";
//    private static final String BASE_URL = "https://192.168.31.128:8000/";
//    private static final String BASE_URL = "https://10.0.2.2:80/";
//    private static final String BASE_URL = "http://192.168.31.128/"; // Wifi Casa_mi
    private static final String BASE_URL = "http://192.168.1.35/"; // Wifi Movistar





    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    .create();
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
