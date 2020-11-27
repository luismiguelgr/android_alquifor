package com.brulesoft.alquifor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import com.brulesoft.alquifor.ui.login.LoginActivity;
import com.squareup.picasso.Picasso;

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
                    if (response.isSuccessful()) {
                        loadDataList(response.body());
                    } else {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuopciones, menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.salir) {
            finish();
            System.exit(0);
        }
        if (id == R.id.mis_comentarios) {
            Intent intent = new Intent(this, MisComentariosActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Mis comentarios", Toast.LENGTH_LONG).show();
            return true;
        }
        if (id == R.id.mis_publicaciones) {
            Intent intent = new Intent(this, MisPublicacionesActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Mis publicaciones", Toast.LENGTH_LONG).show();
            return true;
        }
        if (id == R.id.opcionMenuiniciarSesion) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Login", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
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