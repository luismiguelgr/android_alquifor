package com.brulesoft.alquifor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.brulesoft.alquifor.api.AddPublicacion;
import com.brulesoft.alquifor.api.GetPublicaciones;
import com.brulesoft.alquifor.api.RetrofitClient;
import com.brulesoft.alquifor.models.Publicacion;

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

        Publicacion nuevaPublicacion = new Publicacion();
        nuevaPublicacion.setTitulo(tituloAnadirNuevaPublicacion.getText().toString());
        nuevaPublicacion.setTitulo(descripcionAnadirNuevaPublicacion.getText().toString());
        nuevaPublicacion.setId_usuario(1);




        botonCrearNuevaPublicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddPublicacion service = RetrofitClient.getRetrofitInstance().create(AddPublicacion.class);
                Call<Publicacion> call = service.addPublicacion(nuevaPublicacion);
                call.enqueue(new Callback<Publicacion>() {

                    @Override
                    public void onResponse(Call<Publicacion> call, Response<Publicacion> response) {
                        Toast.makeText(NuevaPublicacionActivity.this, "BIEN", Toast.LENGTH_SHORT).show();
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