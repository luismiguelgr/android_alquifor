package com.brulesoft.alquifor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.brulesoft.alquifor.api.GetPublicaciones;
import com.brulesoft.alquifor.api.MyAdapter;
import com.brulesoft.alquifor.api.MyAdapterComentario;
import com.brulesoft.alquifor.api.RetrofitClient;
import com.brulesoft.alquifor.models.Comentario;
import com.brulesoft.alquifor.models.Publicacion;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PublicacionActivity extends AppCompatActivity {

    TextView tituloPublicacionSeleccionada;
    TextView descripcionPublicacionSeleccionada;
    private RecyclerView myRecyclerView;
    private MyAdapterComentario myAdapter;
    private RecyclerView.LayoutManager lManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicacion);
        tituloPublicacionSeleccionada = (TextView) findViewById(R.id.tituloPublicacionSeleccionada);
        descripcionPublicacionSeleccionada = (TextView) findViewById(R.id.descripcionPublicacionSeleccionada);
        Integer id = getIntent().getIntExtra("id", 0);

        GetPublicaciones service = RetrofitClient.getRetrofitInstance().create(GetPublicaciones.class);
        Call<Publicacion> call = service.getPublicacion(id);

        call.enqueue(new Callback<Publicacion>() {

            @Override
            public void onResponse(Call<Publicacion> call, Response<Publicacion> response) {
//                Toast.makeText(PublicacionActivity.this, "BIEN", Toast.LENGTH_SHORT).show();
//               loadDataList(response.body());
                descripcionPublicacionSeleccionada.setText(response.body().getDescripcion());
                tituloPublicacionSeleccionada.setText(response.body().getDescripcion());

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