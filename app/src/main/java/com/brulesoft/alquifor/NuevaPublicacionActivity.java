package com.brulesoft.alquifor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.brulesoft.alquifor.api.MethodPublicaciones;
import com.brulesoft.alquifor.api.RetrofitClient;
import com.brulesoft.alquifor.models.Publicacion;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NuevaPublicacionActivity extends AppCompatActivity {

    Button botonCrearNuevaPublicacion;
    EditText tituloAnadirNuevaPublicacion, descripcionAnadirNuevaPublicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_publicacion);

        botonCrearNuevaPublicacion = (Button) findViewById(R.id.botonCrearNuevaPublicacion);
        tituloAnadirNuevaPublicacion = (EditText) findViewById(R.id.tituloAnadirNuevaPublicacion);
        descripcionAnadirNuevaPublicacion = (EditText) findViewById(R.id.descripcionAnadirNuevaPublicacion);

        botonCrearNuevaPublicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Publicacion nuevaPublicacion = new Publicacion();
                nuevaPublicacion.setTitulo(tituloAnadirNuevaPublicacion.getText().toString().trim());
                nuevaPublicacion.setDescripcion(descripcionAnadirNuevaPublicacion.getText().toString().trim());
                nuevaPublicacion.setId_usuario(1);
                MethodPublicaciones service = RetrofitClient.getRetrofitInstance().create(MethodPublicaciones.class);
                Call<Publicacion> call = service.addPublicacion(nuevaPublicacion);
                call.enqueue(new Callback<Publicacion>() {

                    @Override
                    public void onResponse(Call<Publicacion> call, Response<Publicacion> response) {
                        Log.e("ERRRORR", ""+response.raw());
                        Log.e("ERRRORR", ""+response.body());
                        Log.e("ERRRORR", ""+response.errorBody());
                        if(response.isSuccessful()){
                            Toast.makeText(NuevaPublicacionActivity.this, "Publicación creada con éxito", Toast.LENGTH_SHORT).show();
                        }else{
                            Log.e("ERRRORR", ""+response.errorBody());
                        }
//                        Toast.makeText(NuevaPublicacionActivity.this, "BIEN", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(v.getContext(), MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Publicacion> call, Throwable t) {
                        Toast.makeText(NuevaPublicacionActivity.this, "MAL", Toast.LENGTH_SHORT).show();
                    }

                });
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }


}