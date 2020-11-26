package com.brulesoft.alquifor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.brulesoft.alquifor.api.MethodPublicaciones;
import com.brulesoft.alquifor.api.MyAdapter;
import com.brulesoft.alquifor.api.RetrofitClient;
import com.brulesoft.alquifor.models.Publicacion;
import com.brulesoft.alquifor.ui.login.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView myRecyclerView;
    private MyAdapter myAdapter;
    private RecyclerView.LayoutManager lManager;
    FloatingActionButton botonAddPublicacion;
    Button botonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onStart() {
        super.onStart();

        botonAddPublicacion = (FloatingActionButton) findViewById(R.id.botonAddPublicacion);
        botonLogin = (Button) findViewById(R.id.botonLogin);

        MethodPublicaciones service = RetrofitClient.getRetrofitInstance().create(MethodPublicaciones.class);
        Call<List<Publicacion>> call = service.getAllPublicaciones();
        call.enqueue(new Callback<List<Publicacion>>() {

            @Override
            public void onResponse(Call<List<Publicacion>> call, Response<List<Publicacion>> response) {
//                Toast.makeText(MainActivity.this, "Bienvenid@", Toast.LENGTH_SHORT).show();
                loadDataList(response.body());
            }

            @Override
            public void onFailure(Call<List<Publicacion>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "MAL", Toast.LENGTH_SHORT).show();
            }

        });

        botonAddPublicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), NuevaPublicacionActivity.class);
                startActivity(intent);
            }
        });

        botonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuopciones, menu);
        return true;
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
        return super.onOptionsItemSelected(item);
    }

    private void loadDataList(List<Publicacion> usersList) {

        // Obtener el Recycler
        myRecyclerView = (RecyclerView) findViewById(R.id.reciclador);
        myRecyclerView.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(MainActivity.this);
        myRecyclerView.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        myAdapter = new MyAdapter(usersList, MainActivity.this);
        myRecyclerView.setAdapter(myAdapter);

//        myRecyclerView = findViewById(R.id.myRecyclerView);
//        myAdapter = new MyAdapter(usersList);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
//        myRecyclerView.setLayoutManager(layoutManager);
//        myRecyclerView.setAdapter(myAdapter);
    }


}