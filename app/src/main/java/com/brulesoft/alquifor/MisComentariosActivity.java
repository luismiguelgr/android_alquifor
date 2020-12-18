package com.brulesoft.alquifor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.brulesoft.alquifor.api.MethodComentarios;
import com.brulesoft.alquifor.api.MethodPublicaciones;
import com.brulesoft.alquifor.api.MisComentariosAdapter;
import com.brulesoft.alquifor.api.MisPublicacionesAdapter;
import com.brulesoft.alquifor.api.RetrofitClient;
import com.brulesoft.alquifor.models.Comentario;
import com.brulesoft.alquifor.models.Publicacion;
import com.brulesoft.alquifor.ui.login.LoginActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MisComentariosActivity extends AppCompatActivity {

    private RecyclerView myRecyclerView;
    private MisComentariosAdapter myAdapter;
    private RecyclerView.LayoutManager lManager;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_comentarios);
    }

    @Override
    protected void onStart() {
        super.onStart();

        preferences = MisComentariosActivity.this.getSharedPreferences("ALQUIFOR", Context.MODE_PRIVATE);
        String token  = preferences.getString("TOKEN",null);

        MethodComentarios service = RetrofitClient.getRetrofitInstance().create(MethodComentarios.class);
        Call<List<Comentario>> call = service.getComentariosUsuario("Bearer "+token,2);
        call.enqueue(new Callback<List<Comentario>>() {

            @Override
            public void onResponse(Call<List<Comentario>> call, Response<List<Comentario>> response) {
                loadDataList(response.body());
            }

            @Override
            public void onFailure(Call<List<Comentario>> call, Throwable t) {
                Toast.makeText(MisComentariosActivity.this, "MAL", Toast.LENGTH_SHORT).show();
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
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
        if (id == R.id.opcionMenuDesconectarse) {
            preferences = getSharedPreferences("ALQUIFOR", Context.MODE_PRIVATE);
            SharedPreferences.Editor borrarToken = preferences.edit();
            borrarToken.clear();
            borrarToken.commit();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Desconectado", Toast.LENGTH_LONG).show();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void loadDataList(List<Comentario> comentariosList) {

        // Obtener el Recycler
        myRecyclerView = (RecyclerView) findViewById(R.id.recicladorMisComentarios);
        myRecyclerView.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(MisComentariosActivity.this);
        myRecyclerView.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        myAdapter = new MisComentariosAdapter(comentariosList, MisComentariosActivity.this);
        myRecyclerView.setAdapter(myAdapter);
    }
}