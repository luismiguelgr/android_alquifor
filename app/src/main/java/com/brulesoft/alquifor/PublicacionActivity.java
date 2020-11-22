package com.brulesoft.alquifor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.brulesoft.alquifor.api.MethodPublicaciones;
import com.brulesoft.alquifor.api.MyAdapterComentario;
import com.brulesoft.alquifor.api.RetrofitClient;
import com.brulesoft.alquifor.models.Comentario;
import com.brulesoft.alquifor.models.Publicacion;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PublicacionActivity extends AppCompatActivity {

    TextView tituloPublicacionSeleccionada;
    TextView descripcionPublicacionSeleccionada;
    ListView prosPublicacion, contrasPublicacion;
    ImageView imagenPublicacionSeleccionada;
    private RecyclerView myRecyclerView;
    private MyAdapterComentario myAdapter;
    private RecyclerView.LayoutManager lManager;
    TextView estiloTextoPros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicacion);
        tituloPublicacionSeleccionada = (TextView) findViewById(R.id.tituloPublicacionSeleccionada);
        tituloPublicacionSeleccionada.setMovementMethod(new ScrollingMovementMethod());
        descripcionPublicacionSeleccionada = (TextView) findViewById(R.id.descripcionPublicacionSeleccionada);
        descripcionPublicacionSeleccionada.setMovementMethod(new ScrollingMovementMethod());
        prosPublicacion = (ListView) findViewById(R.id.prosPublicacion);
        contrasPublicacion = (ListView) findViewById(R.id.contrasPublicacion);
        imagenPublicacionSeleccionada = (ImageView) findViewById(R.id.imagenPublicacionSeleccionada);
        Integer id = getIntent().getIntExtra("id", 0);

        MethodPublicaciones service = RetrofitClient.getRetrofitInstance().create(MethodPublicaciones.class);
        Call<Publicacion> call = service.getPublicacion(id);

        call.enqueue(new Callback<Publicacion>() {

            @Override
            public void onResponse(Call<Publicacion> call, Response<Publicacion> response) {
                tituloPublicacionSeleccionada.setText(response.body().getTitulo());
                descripcionPublicacionSeleccionada.setText(response.body().getDescripcion());
                ArrayAdapter<String> adaptadorPros = new ArrayAdapter<String>(PublicacionActivity.this, R.layout.texto_pros_publicacion, response.body().getPros());
                prosPublicacion.setAdapter(adaptadorPros);
                ArrayAdapter<String> adaptadorContras = new ArrayAdapter<String>(PublicacionActivity.this, R.layout.texto_contras_publicacion, response.body().getContras());
                contrasPublicacion.setAdapter(adaptadorContras);
//                Picasso.get().load("http://10.0.2.2:80/uploads/otra_casa.jpg").into(imagenPublicacionSeleccionada);
                Picasso.get().load(response.body().getFoto()).error(PublicacionActivity.this.getResources().getDrawable(R.drawable.sin_imagen)).into(imagenPublicacionSeleccionada);

            }

            @Override
            public void onFailure(Call<Publicacion> call, Throwable t) {
                Toast.makeText(PublicacionActivity.this, "MAL", Toast.LENGTH_SHORT).show();
            }

        });

        Call<List<Comentario>> callComentarios = service.getComentariosPublicacion(id);
        callComentarios.enqueue(new Callback<List<Comentario>>() {

            @Override
            public void onResponse(Call<List<Comentario>> call, Response<List<Comentario>> response) {
//                Toast.makeText(PublicacionActivity.this, "BIEN", Toast.LENGTH_SHORT).show();
                    if (response.isSuccessful()) {
                        loadDataList(response.body());
                    } else {
                        Log.d("error message", "No existen comentarios");
                        Toast.makeText(PublicacionActivity.this, "No existen comentarios en esta publicación", Toast.LENGTH_SHORT).show();
                    }
            }

            @Override
            public void onFailure(Call<List<Comentario>> call, Throwable t) {
                Log.e("ERRRORRRR", ""+t.getMessage());
                Toast.makeText(PublicacionActivity.this, "MAL", Toast.LENGTH_SHORT).show();
            }


        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    private void loadDataList(List<Comentario> usersList) {

        // Obtener el Recycler
        myRecyclerView = (RecyclerView) findViewById(R.id.recicladorComentarios);
        myRecyclerView.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(PublicacionActivity.this);
        myRecyclerView.setLayoutManager(lManager);


        // Crear un nuevo adaptador
        myAdapter = new MyAdapterComentario(usersList, PublicacionActivity.this);
        myRecyclerView.setAdapter(myAdapter);

    }




}